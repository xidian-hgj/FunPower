/*
 * log.h
 *
 *  Created on: Apr 15, 2014
 *      Author: hxs
 */

#ifndef LOG_H_
#define LOG_H_

#include <stdio.h>

#define LOG_SHOW		//log print switch


#ifdef LOG_SHOW

#define log(...)	{printf(__VA_ARGS__); printf("\n");}
#define logE(...)	{printf("==>Error "); printf(__VA_ARGS__); printf("\n");}

#else

#define log( ...)
#define logE( ...)
#endif


#endif /* LOG_H_ */
