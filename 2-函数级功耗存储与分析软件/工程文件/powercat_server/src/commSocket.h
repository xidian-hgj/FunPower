/*
 * commSocket.h
 *
 *  Created on: Apr 15, 2014
 *      Author: hxs
 */

#ifndef COMMSOCKET_H_
#define COMMSOCKET_H_

#include <sys/socket.h>
#include <netinet/in.h>

int socket_init(unsigned short portnum);
int socket_accept(int sfp, struct sockaddr_in * client_addr);
int socket_exit(int sfp);

#endif /* COMMSOCKET_H_ */
