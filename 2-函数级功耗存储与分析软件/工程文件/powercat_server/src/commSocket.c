#include <sys/socket.h>
#include <netinet/in.h>
#include "log.h"

int socket_init(unsigned short portnum) {
	int sfp, nfp, ret;
	struct sockaddr_in s_add;
	int sin_size;
	sfp = socket(AF_INET, SOCK_STREAM, 0);
	if (-1 == sfp) {
		logE("socket fail!");
		return -1;
	}

	bzero(&s_add, sizeof(struct sockaddr_in));
	s_add.sin_family = AF_INET;
	s_add.sin_addr.s_addr = htonl(INADDR_ANY);
	s_add.sin_port = htons(portnum);
	ret = bind(sfp, (struct sockaddr *) (&s_add), sizeof(struct sockaddr));
	if (-1 == ret) {
		logE("bind fail!");
		return -1;
	}

	ret = listen(sfp, 10);
	if (-1 == ret) {
		log("listen fail!");
		return -1;
	}

	return sfp;
}


int socket_accept(int sfp, struct sockaddr_in * client_addr) {
	int sin_size = sizeof(struct sockaddr_in);
	int nfp = accept(sfp, (struct sockaddr *) (client_addr), &sin_size);
	if (-1 == nfp) {
		logE("socket accept fail!");
		return -1;
	}

	char * addr = inet_ntoa(client_addr->sin_addr);
	unsigned int port = ntohs(client_addr->sin_port);
	log("accepted address=%s:%u", addr, port);

	return nfp;
}

int socket_exit(int sfp) {
	return close(sfp);
}
