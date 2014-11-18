/*
 * infosend.c
 *
 *  Created on: Jul 1, 2014
 *      Author: hxs
 */
#include <stdlib.h>
#include <stdbool.h>
#include <pthread.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <semaphore.h>
#include <signal.h>
#include "ring_buffer.h"
#include "infosend.h"
#include "log.h"

static long BUFFER_SIZE = (1024 * 1024);
long send_threshold  = (1024 * 1024)/4;
struct ring_buffer * ring_buf = NULL;
pthread_t send_thread;
int isSendThreadRun;
sem_t send_sem;

void * info_send_thread(void);
int socket_init(char * ip, unsigned short port);
long socket_write(int sfd, void * buf, long num);
void socket_thread_sig_int(int signo);

char server_ip[24];
unsigned short server_port;

int infoSender_init(char * ip, unsigned short port, long buffer_size)
{
	if(buffer_size>0){
		BUFFER_SIZE = buffer_size;
	}
	send_threshold = (BUFFER_SIZE / 4);		// buffer send threshold
	strcpy(server_ip, ip);
	server_port = port;

	pthread_mutex_t *f_lock = NULL;
	void * buffer = NULL;

	f_lock = (pthread_mutex_t *) malloc(sizeof(pthread_mutex_t));
	if (pthread_mutex_init(f_lock, NULL) != 0) {
		logE("info_send_init error: mutex init fail");
		return -1;
	}

	buffer = (void *) malloc(BUFFER_SIZE);
	if (buffer == NULL) {
		logE("info_send_init error: malloc buffer fail");
		return -2;
	}
	ring_buf = ring_buffer_init(buffer, BUFFER_SIZE, f_lock);
	if (ring_buf == NULL) {
		logE("info_send_init error: ring_buf init fail");
		return -3;
	}

	sem_init(&send_sem, 0, 0);
	isSendThreadRun = true;
	pthread_create(&send_thread, NULL, (void *) info_send_thread, NULL);

	log("info sender started, buffer_size=%ld", BUFFER_SIZE);
	return 0;
}

void infoSender_close()
{
	isSendThreadRun = false;
	infoSender_flush();
	pthread_join(send_thread, NULL);
	ring_buffer_free(ring_buf);
	ring_buf = NULL;
	log("info sender close");
}

#define SEND_BUF_MAX 1024
char send_buf[SEND_BUF_MAX];

/**
 * time:  real time in ns from 1970
 * unit:  mJ/cpuPower
 */
void infoSender_write(long long time, int cpu_num, double * cpu_power, double power_wifi, double power_3G, double pid_power)
{
	int n, i;
	n = 0;
	n += sprintf(send_buf+n, "%lld ", time);
	n += sprintf(send_buf+n, "%d ", cpu_num);
	for(i=0; i<cpu_num; i++){
		n += sprintf(send_buf+n, "%lf ", cpu_power[i]);
	}
	n += sprintf(send_buf+n, "%lf ", pid_power);
	n += sprintf(send_buf+n, "%lf ", power_wifi);
	n += sprintf(send_buf+n, "%lf ", power_3G);
	n += sprintf(send_buf+n, "\n");

	int sendNum = 0;
	while (n > sendNum) {
		sendNum += ring_buffer_put(ring_buf, send_buf + sendNum, n - sendNum);
		if (ring_buffer_len(ring_buf) >= send_threshold) {
			sem_post(&send_sem);
		}
	}

}

void infoSender_flush()
{
	sem_post(&send_sem);
}

#define SOCKET_BUF_MAX (100*1024)
char socket_buf[SOCKET_BUF_MAX];
void * info_send_thread(void)
{
	int sfd;

	signal(SIGPIPE, socket_thread_sig_int);

start:
	log("socket connect to server...");
	sfd = socket_init(server_ip, server_port);
	if(sfd==-1){
		sleep(1);
		if(isSendThreadRun){
			goto start;
		}else{
			return NULL;
		}
	}

	log("socket thread start");

	while (isSendThreadRun) {
		sem_wait(&send_sem);
		while(sem_trywait(&send_sem)==0){
			;	//consume all sem has product
		}

		//send all data in ring buf
		log("%ld", (long)ring_buffer_len(ring_buf));
		while (ring_buffer_len(ring_buf) > 0) {
			long n = ring_buffer_get(ring_buf, socket_buf, SOCKET_BUF_MAX);
			long ret = socket_write(sfd, socket_buf, n);
			if(ret<0){
				log("ret=%ld", ret);
				goto start;
			}
		}
		log("send end");
	}
	log("send thread stop");
	close(sfd);

	return NULL;
}

//capture the sigpip erro when socket close and write to the socket
void socket_thread_sig_int(int signo)
{
	logE("socket thread sig: %d", signo);
}


long socket_write(int sfd, void * buf, long num)
{
	long send_num = 0;
	while (num > send_num) {
		long n = write(sfd, buf + send_num, num - send_num);
		if (n < 0)
			return n;
		send_num += n;
	}
	if(send_num != num){
		logE("socket_write error: num=%ld, send_num=%ld", num, send_num);
	}
	return send_num;
}

int socket_init(char * ip, unsigned short port)
{
	int sfd;
	struct sockaddr_in s_add;

	if (ip == NULL) {
		logE("socket_init error: ip is NULL");
	}

	sfd = socket(AF_INET, SOCK_STREAM, 0);
	if (-1 == sfd) {
		logE("socket_init error: socket fun is error");
		return -1;
	}

	bzero(&s_add, sizeof(struct sockaddr_in));
	s_add.sin_family = AF_INET;
	s_add.sin_addr.s_addr = inet_addr(ip);
	s_add.sin_port = htons(port);

	if (-1 == connect(sfd, (struct sockaddr *) (&s_add), sizeof(struct sockaddr))) {
		logE("socket_init error: connect is fail");
		return -1;
	}

	return sfd;
}

