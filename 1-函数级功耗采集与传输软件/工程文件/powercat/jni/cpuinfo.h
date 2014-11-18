/*
 * cpuinfo.h
 *
 *  Created on: Jun 29, 2014
 *      Author: hxs
 */

#ifndef CPUINFO_H_
#define CPUINFO_H_

#include <limits.h>

#define FREQS_NUM_MAX 64

// info of the core in cpu
struct cpu_info {
	char isInit;
	char cpu_path[PATH_MAX];
	char cur_freq_path[PATH_MAX];
	char time_freq_path[PATH_MAX];
	char available_freqs_path[PATH_MAX];

	int core_index;			//CPU core index
	int freqs_num;
	long max_freq;						//max freq in the all freqs
	long cur_freq;						//current freq of the core
	double freqs_avg;
	long freqs[FREQS_NUM_MAX];			//available  frequency
	long freqs_time[FREQS_NUM_MAX];		//time in all frequency
	double power;						//mJ
};


// info of the process about cpu time
struct cpu_pid_info {
	int pid;
	char filename[NAME_MAX];
	long utime;
	long stime;
	long cutime;
	long cstime;
	long time_sum;
	long starttime;			//the time in jiffies when the process started

	long sum_change_used_time;		//上一次sum值改变所用的时间
	long long sum_change_last_time;		//上一次sum值改变的时间点
	double last_power_rate;			//上次改变时候的功率
	double power;					//功耗 mJ
};

extern double CPU_POWER_FULL;

struct cpu_info *  cpu_info_init(int * num);
void cpu_info_relase(struct cpu_info *cpus);
void cpu_info_cat(struct cpu_info * cpu);
void cpu_info_display(struct cpu_info * cpu);
void cpu_pid_info_cat(struct cpu_pid_info * cpu_pid, int pid, long long time, double cpu_power_rate, double clks);
void cpu_pid_info_display(struct cpu_pid_info * cpu_pid);
int get_cpu_clk_tck();



void cpu_pid_info_test();
void cpu_info_test();

#endif /* CPUINFO_H_ */
