/*
 ============================================================================
 Name        : powercat_analyze.c
 Author      : hexingshi
 Version     :
 Copyright   : hexingshi
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <signal.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include "commSocket.h"
#include "log.h"

void sig_int(int signo);

char * buf = NULL;

int main(int argc, char * argv[])
{

	char * power_path = "./power.dat";
	FILE * power_fp;
	unsigned int buf_num = 10 * 1024;
	unsigned short port = 8010;
	struct sockaddr_in client_socket;
	int sfp, nfp;

	if(argc==2){
		power_path = argv[1];
	}
	log("power_path=%s", power_path);
	log("powercat analyze start");
	signal(SIGINT, sig_int);
	power_fp = fopen(power_path, "wa");
	if (power_fp == NULL) {
		logE("power.dat file open fail");
	}
	buf = (char *) malloc(buf_num);
	sfp = socket_init(port);

	log("wait for accpt");
	nfp = socket_accept(sfp, &client_socket);

	while (true) {
		int rec_num = read(nfp, buf, buf_num);
		if (rec_num <= 0) {
			log("sockt close");
			close(nfp);
			break;
		}
		fwrite(buf, rec_num, 1, power_fp);
	}

	log("program exit");
	return 0;
}

void sig_int(int signo)
{
	log("signal interrupt");
	if (buf != NULL) {
		free(buf);
		buf = NULL;
	}
	exit(0);
}
