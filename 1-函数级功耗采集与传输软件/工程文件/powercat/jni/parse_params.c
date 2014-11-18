#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <stdbool.h>
#include "parse_params.h"
#include "log.h"

int param_pid = 1;
char param_ipAddr[24] = "192.168.2.67";
unsigned short param_port = 8010;
long buffer_size = 1024*1024;

/**
 * -p pid
 * -a ip address
 * -n port
 * -b buffer size
 */
void parse_params(int argc, char *argv[])
{
	int ch;
	while ((ch = getopt(argc, argv, "p::a::n::b::")) != -1)
	{
		switch (ch)
		{
		case 'p':
			param_pid = atoi(optarg);
			break;
		case 'a':
			strcpy(param_ipAddr, optarg);
			break;
		case 'n':
			param_port = atoi(optarg);
			break;
		case 'b':
			buffer_size = atol(optarg);
			break;
		}
	}

	log("======[ init params ]=========");
	log("pid=%d", param_pid);
	log("ipAddr=%s", param_ipAddr);
	log("port=%d", param_port);
	log("buffer_size=%ld", buffer_size);

}
