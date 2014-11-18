/*
 * parse_params.h
 *
 *  Created on: Jul 3, 2014
 *      Author: hxs
 */

#ifndef PARSE_PARAMS_H_
#define PARSE_PARAMS_H_

#include <limits.h>

extern int param_pid;
extern char param_ipAddr[24];
extern unsigned short param_port;
extern long buffer_size;

void parse_params(int argc, char *argv[]);

#endif /* PARSE_PARAMS_H_ */
