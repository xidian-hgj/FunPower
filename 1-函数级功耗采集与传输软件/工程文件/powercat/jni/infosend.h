/*
 * infosend.h
 *
 *  Created on: Jul 1, 2014
 *      Author: hxs
 */

#ifndef INFOSEND_H_
#define INFOSEND_H_

int infoSender_init(char * ip, unsigned short port, long buffer_size);
void infoSender_close();
void infoSender_flush();

void infoSender_write(long long time, int cpu_num, double * cpu_power, double power_wifi, double power_3G, double pid_power);

#endif /* INFOSEND_H_ */
