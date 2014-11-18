/*
 * data_analyse.h
 *
 *  Created on: Jul 3, 2014
 *      Author: hxs
 */

#ifndef DATA_ANALYSE_H_
#define DATA_ANALYSE_H_

#define MAX_CORE_NUM 4

struct TAG_POWER_DATA {
	long long time;				//采样点的时间  单位ns
	int core_num;
	double core_power[MAX_CORE_NUM];
	double power_wifi;
	double power_3G;
	double power_pid;
};
typedef struct TAG_POWER_DATA PowerData;

#define POWER_DATA_GROUP_MAX (1024*10)
struct TAG_POWER_DATA_GROUP {
	long size;
	PowerData datas[POWER_DATA_GROUP_MAX];
};
typedef struct TAG_POWER_DATA_GROUP PowerDataGroup;

#define POWER_DATA_SET_MAX (1024*100)
struct TAG_POWER_DATA_SET {
	long size;
	PowerDataGroup * dataGroups[POWER_DATA_SET_MAX];
};
typedef struct TAG_POWER_DATA_SET PowerDataSet;

int loadPowerDataInFile(char * path, PowerDataSet ** set);
PowerData * getPowerData(PowerDataSet * set, long count);
long getPowerSetNum(PowerDataSet * set);
void printPowerData(PowerData * data);
long findPowerDataCountByTime(long long time, PowerDataSet *set);
double getPower(int cpu_index, PowerDataSet * set, long long timeStart, long long timeEnd);


#endif /* DATA_ANALYSE_H_ */
