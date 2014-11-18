/*
 * netinfo.c
 *
 *  Created on: Oct 6, 2014
 *      Author: hxs
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdbool.h>
#include <limits.h>
#include <stddef.h>
#include <unistd.h>
#include "log.h"

char * wifi_path_rx = "/sys/class/net/wlan0/statistics/rx_bytes";
char * wifi_path_tx = "/sys/class/net/wlan0/statistics/tx_bytes";

char * radio_path_rx = "/sys/class/net/rmnet0/statistics/rx_bytes";
char * radio_path_tx = "/sys/class/net/rmnet0/statistics/tx_bytes";

long long wifi_rx = 0;
long long wifi_tx = 0;
long long radio_rx = 0;
long long radio_tx = 0;

/**
 * 获取传输流量的和上一次调用的差值
 */
long long net_wifi_rx()
{
	FILE * fp;
	long long num, ret;
	if (access(wifi_path_rx, F_OK) != 0) {
		wifi_rx = 0;
		return 0;
	}

	fp = fopen(wifi_path_rx, "r");
	if (fp == NULL) {
		logE("wifi rx path open failed");
	}
	fscanf(fp, "%ld", &num);
	fclose(fp);

	if (wifi_rx == 0) {
		wifi_rx = num;
		return 0;
	}

	ret = num - wifi_rx;
	wifi_rx = num;
	return ret;
}

long long net_wifi_tx()
{
	FILE * fp;
	long long num, ret;
	if (access(wifi_path_tx, F_OK) != 0) {
		wifi_tx = 0;
		return 0;
	}

	fp = fopen(wifi_path_tx, "r");
	if (fp == NULL) {
		logE("wifi tx path open failed");
	}
	fscanf(fp, "%ld", &num);
	fclose(fp);

	if (wifi_tx == 0) {
		wifi_tx = num;
		return 0;
	}

	ret = num - wifi_tx;
	wifi_tx = num;
	return ret;
}

long long net_radio_rx()
{
	FILE * fp;
	long long num, ret;
	if (access(radio_path_rx, F_OK) != 0) {
		radio_rx = 0;
		return 0;
	}

	fp = fopen(radio_path_rx, "r");
	if (fp == NULL) {
		logE("radio rx path open failed");
	}
	fscanf(fp, "%ld", &num);
	fclose(fp);

	if (radio_rx == 0) {
		radio_rx = num;
		return 0;
	}

	ret = num - radio_rx;
	radio_rx = num;
	return ret;
}

long long net_radio_tx()
{
	FILE * fp;
	long long num, ret;
	if (access(radio_path_tx, F_OK) != 0) {
		radio_tx = 0;
		return 0;
	}

	fp = fopen(radio_path_tx, "r");
	if (fp == NULL) {
		logE("radio tx path open failed");
	}
	fscanf(fp, "%ld", &num);
	fclose(fp);

	if (radio_tx == 0) {
		radio_tx = num;
		return 0;
	}

	ret = num - radio_tx;
	radio_tx = num;
	return ret;
}

