/*
 * data_analyse.c
 *
 *  Created on: Jul 3, 2014
 *      Author: hxs
 */

#include <stdio.h>
#include <stdlib.h>
#include <memory.h>
#include "log.h"
#include "data_analyse.h"

#define isPowerGroupFull(g) ((g->size<POWER_DATA_GROUP_MAX)?0:1)
#define isPowerSetFull(s)  	((s->size<POWER_DATA_SET_MAX)?0:1)

void addPowerDataGroup(PowerDataGroup * group, PowerData * data);
void addPowerDataSet(PowerDataSet * set, PowerData * data);
PowerDataGroup* newGroupInSet(PowerDataSet* set);

int loadPowerDataInFile(char * path, PowerDataSet ** set)
{
	FILE * fp = NULL;
	int line_max = 1024;
	char * line, *line_start;
	int i;
	PowerData data;

	if (path == NULL) {
		logE("loadPowerDataInFile : path is null");
		return -1;
	}

	fp = fopen(path, "r");
	if (fp == NULL) {
		logE("loadPowerDataInFile : open file=%s fail", path);
		return -2;
	}

	PowerDataSet *powerSet = (PowerDataSet *) malloc(sizeof(PowerDataSet));
	if (powerSet == NULL) {
		logE("loadPowerDataInFile : PowerDataSet malloc memory fail");
		return -3;
	} else {
		memset(powerSet, 0, sizeof(PowerDataSet));
	}

	line_start = (char *) malloc(line_max * sizeof(char));
	int scan_num;
	char str[128];
	while (!feof(fp)) {
		int num = 0;
		line = line_start;
		fgets(line, line_max, fp);

		scan_num = sscanf(line, "%s", str);
		if (scan_num == 1) {
			line += strlen(str);
			line += 1;
			data.time = atoll(str);
			num++;
		}

		scan_num = sscanf(line, "%s", str);
		if (scan_num == 1) {
			line += strlen(str);
			line += 1;
			data.core_num = atoi(str);
			num++;
		}

		for (i = 0; i < data.core_num; i++) {
			scan_num = sscanf(line, "%s", str);
			if (scan_num == 1) {
				line += strlen(str);
				line += 1;
				data.core_power[i] = atof(str);
				num++;
			}
		}

		scan_num = sscanf(line, "%s", str);
		if (scan_num == 1) {
			line += strlen(str);
			line += 1;
			data.power_pid = atof(str);
			num++;
		}

		scan_num = sscanf(line, "%s", str);
		if (scan_num == 1) {
			line += strlen(str);
			line += 1;
			data.power_wifi = atof(str);
			num++;
		}

		scan_num = sscanf(line, "%s", str);
		if (scan_num == 1) {
			line += strlen(str);
			line += 1;
			data.power_3G = atof(str);
			num++;
		}

		if (num == 5 + data.core_num) {
			addPowerDataSet(powerSet, &data);
		} else {
			logE("sscanf fail num=%d", 5 + data.core_num);
		}
	}

	*set = powerSet;
	return 1;
}

long getPowerSetNum(PowerDataSet * set)
{
	return (set->size - 1) * POWER_DATA_GROUP_MAX + set->dataGroups[set->size - 1]->size;
}

/**
 * count : start at 0
 */
PowerData * getPowerData(PowerDataSet * set, long count)
{
	long groupCount = count / POWER_DATA_GROUP_MAX;
	long inCount = count % POWER_DATA_GROUP_MAX;
	PowerDataGroup * group = set->dataGroups[groupCount];
	if (group != NULL && inCount < group->size) {
		return &(group->datas[inCount]);
	} else {
		return NULL;
	}
}

void printPowerData(PowerData * data)
{
	log("%lld %d",data->time, data->core_num);
}

PowerDataGroup* newGroupInSet(PowerDataSet* set)
{
	PowerDataGroup* group = (PowerDataGroup*) malloc(sizeof(PowerDataGroup));
	memset(group, 0, sizeof(PowerDataGroup));
	group->size = 0;
	set->dataGroups[set->size] = group;
	set->size++;
	return group;
}

void addPowerDataSet(PowerDataSet * set, PowerData * data)
{
	if (set == NULL || data == NULL) {
		logE("addPowerDataSet : set or data is null");
		return;
	}

	if (set->size == 0) {
		newGroupInSet(set);
	}

	PowerDataGroup * group = set->dataGroups[set->size - 1];

	if (isPowerGroupFull(group)) {
		group = newGroupInSet(set);
	}

	addPowerDataGroup(group, data);
}

void addPowerDataGroup(PowerDataGroup * group, PowerData * data)
{
	if (group == NULL || data == NULL) {
		logE("addPowerDataGroup : group or data is null");
		return;
	}

	group->datas[group->size] = *data;
	group->size++;
}

/**
 * find the count most near the time, and before it
 * count is start in zero
 */
long findPowerDataCountByTime(long long time, PowerDataSet *set)
{
	long begin, end, mid, ret;
	PowerData * data_begin, *data_end, *data_mid;

	begin = 0;
	end = getPowerSetNum(set) - 1;

	data_begin = getPowerData(set, begin);
	data_end = getPowerData(set, end);

	if (time < data_begin->time || time > data_end->time) {
		logE("time is out range");
		return -1;
	}

	while (begin < end) {
		mid = (begin + end) / 2;
		data_mid = getPowerData(set, mid);
		if (data_mid->time < time) {
			begin = mid + 1;
		} else if (data_mid->time > time) {
			end = mid - 1;
		} else {
			ret = mid;
			break;
		}
	}

	if (begin == end) {
		ret = begin < mid ? begin : mid;
	}
	return ret;
}

double getPower(int cpu_index, PowerDataSet * set, long long timeStart, long long timeEnd)
{
	long count_start, count_end;
	double power_sum, power_over_s, power_over_e;
	PowerData * data_start_u, *data_start_d, *data_end_u, *data_end_d;

	long begin, end;
	PowerData * data_begin, *data_end;

	begin = 0;
	end = getPowerSetNum(set) - 1;

	data_begin = getPowerData(set, begin);
	data_end = getPowerData(set, end);

	if (timeStart < data_begin->time) {
		timeStart = data_begin->time;
	}
	if (timeEnd > data_end->time) {
		timeEnd = data_end->time;
	}

	count_start = findPowerDataCountByTime(timeStart, set);
	count_end = findPowerDataCountByTime(timeEnd, set);

	if (count_start == -1 || count_end == -1) {
		logE("timeStart or timeEnd is out range");
		return 0.0;
	}

	data_start_u = getPowerData(set, count_start);
	data_start_d = getPowerData(set, count_start + 1);
	data_end_u = getPowerData(set, count_end);
	data_end_d = getPowerData(set, count_end + 1);

	power_sum = data_end_d->core_power[cpu_index] - data_start_u->core_power[cpu_index];
	power_over_s = ((timeStart - data_start_u->time) / ((double) (data_start_d->time - data_start_u->time))) * (data_start_d->core_power[cpu_index]-data_start_u->core_power[cpu_index]);
	power_over_e = ((data_end_d->time - timeEnd) / ((double) (data_end_d->time - data_end_u->time))) * (data_end_d->core_power[cpu_index]-data_end_u->core_power[cpu_index]);

	return power_sum - power_over_s - power_over_e;
}

