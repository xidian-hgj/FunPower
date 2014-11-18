/*
 * cpuinfo.c
 *
 *  Created on: Jun 29, 2014
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
#include "cpuinfo.h"

double CPU_POWER_FULL = 1000.0;			//mW unit, in max freq and all core is on

char * path_cpu = "/sys/devices/system/cpu";
char * path_core_num = "/sys/devices/system/cpu/kernel_max";
char * path_time_freq = "cpufreq/stats/time_in_state";
char * path_cur_freq = "cpufreq/cpuinfo_cur_freq";
char * freqs_available = "cpufreq/scaling_available_frequencies";

void cpu_info_data_init(struct cpu_info * cpu, int core_index);
void cpu_info_no_init(struct cpu_info * cpu);

struct cpu_info * cpu_info_init(int * num)
{
	int i;
	FILE * fp;

	int core_num = 0;
	//get core num
	fp = fopen(path_core_num, "r");
	fscanf(fp, "%d", &core_num);
	core_num++;			//kernel_max is start with zero
	fclose(fp);
	struct cpu_info * cpus = (struct cpu_info *) malloc(sizeof(struct cpu_info) * core_num);
	for (i = 0; i < core_num; i++) {
		cpu_info_data_init(&cpus[i], i);
		cpu_info_cat(&cpus[i]);
	}

	log("cpuInfo inited");
	log("core_num=%d", core_num);
	for (i = 0; i < core_num; i++) {
		cpu_info_display(&cpus[i]);
	}

	*num = core_num;
	return cpus;
}

void cpu_info_relase(struct cpu_info * cpus)
{
	free(cpus);
}

void cpu_info_data_init(struct cpu_info * cpu, int core_index)
{
	FILE * fp;
	long freq;
	int freqs_num;
	int i;

	cpu->isInit = false;

	cpu->core_index = core_index;
	sprintf(cpu->cpu_path, "%s/cpu%d", path_cpu, cpu->core_index);
	sprintf(cpu->cur_freq_path, "%s/%s", cpu->cpu_path, path_cur_freq);
	sprintf(cpu->time_freq_path, "%s/%s", cpu->cpu_path, path_time_freq);
	sprintf(cpu->available_freqs_path, "%s/%s", cpu->cpu_path, freqs_available);

	fp = fopen(cpu->available_freqs_path, "r");
	if (fp == NULL) {		//the core is close
		cpu->isInit = false;
		return;
	}

	freqs_num = 0;
	while (!feof(fp)) {
		int n = fscanf(fp, "%ld", &freq);
		if (n == 1) {
			cpu->freqs[freqs_num] = freq;
			cpu->freqs_time[freqs_num] = 0;
			freqs_num++;
		}
	}
	fclose(fp);
	cpu->freqs_num = freqs_num;

	int max_freq = cpu->freqs[0];
	for (i = 1; i < freqs_num; i++) {
		if (cpu->freqs[i] > max_freq) {
			max_freq = cpu->freqs[i];
		}
	}
	cpu->max_freq = max_freq;

	if (cpu->max_freq != cpu->freqs[cpu->freqs_num - 1]) {
		logE("cpu info data init error: freqs is not in order");
	}
	cpu->isInit = true;
}

void cpu_info_display(struct cpu_info * cpu)
{
	int i;

	if (cpu == NULL) {
		logE("cpu_info is null");
		return;
	}

	log("===========[ cpu_info ]===========");
	log("cpu_path=%s", cpu->cpu_path);
	log("core_index=%d", cpu->core_index);
	log("freqs_num=%d", cpu->freqs_num);
	log("cur_freq=%ld", cpu->cur_freq);
	log("max_freq=%ld", cpu->max_freq);
	log("freqs times");
	for (i = 0; i < cpu->freqs_num; i++) {
		log("%ld %ld", cpu->freqs[i], cpu->freqs_time[i]);
	}
}

void cpu_info_no_init(struct cpu_info * cpu)
{
	cpu->isInit = false;
	cpu->cur_freq = 0;
	cpu->freqs_num = 0;
	cpu->freqs_avg = 0;
	cpu->max_freq = 10000000;
}

void cpu_info_cat(struct cpu_info * cpu)
{
	FILE * fp;
	long freq, times;
	int freq_index;

	//处理CPU关闭的情况
	if (cpu->isInit == false) {
		cpu_info_data_init(cpu, cpu->core_index);
	}
	if(access(cpu->cur_freq_path, F_OK)!=0){
		cpu_info_no_init(cpu);
		return;
	}


	fp = fopen(cpu->cur_freq_path, "r");
	if (fp == NULL) {
		logE("cpu freq path open failed");
	}
	fscanf(fp, "%ld", &cpu->cur_freq);
	fclose(fp);

	fp = fopen(cpu->time_freq_path, "r");
	if (fp == NULL) {
		logE("time freq path open failed");
	}
	freq_index = 0;
	while (!feof(fp)) {
		int n = fscanf(fp, "%ld %ld", &freq, &times);
		if (n == 2) {
			if (freq_index >= cpu->freqs_num) {
				logE("cpu info cat error: freq_index is too large");
				break;
			}
			//the order in time_instate and scaling_available_frequencies is equal in default
			if (cpu->freqs[freq_index] != freq) {
				logE("cpu info cat error: freq is not equal");
			}
			cpu->freqs_time[freq_index] = times;
			freq_index++;
		}
	}
	if (freq_index != cpu->freqs_num) {
		logE("cpu info cat error: num of time_in_state is equal freqs num");
	}
	fclose(fp);

	int i;
	double freqs_avg = 0.0;
	for (i = 0; i < cpu->freqs_num; i++) {
		freqs_avg += cpu->freqs_time[i] * (cpu->freqs[i] / (double) cpu->max_freq);
	}
	cpu->freqs_avg = freqs_avg;
}

void cpu_pid_info_cat(struct cpu_pid_info * cpu_pid, int pid, long long time, double cpu_power_rate, double clks)
{
	if (cpu_pid == NULL) {
		logE("cpu pid info cat error: cpu pid is null");
		return;
	}

	char path[PATH_MAX];
	FILE * fp = NULL;
	int i;

	cpu_pid->pid = pid;

	sprintf(path, "/proc/%d/stat", pid);
	fp = fopen(path, "r");
	if (fp == NULL) {
		cpu_pid->stime = 0;
		cpu_pid->utime = 0;
		cpu_pid->cstime = 0;
		cpu_pid->cutime = 0;
		cpu_pid->time_sum = 0;
		return;
	}

	int pid_t = 0;
	fscanf(fp, "%d", &pid_t);
	if (pid != pid_t) {
		logE("cpu pid info cat error: pid is error");
		return;
	}

	fscanf(fp, "%s", cpu_pid->filename);

	//skip other info before utime
	char skip_str[NAME_MAX];
	int skip_num = 11;
	for (i = 0; i < skip_num; i++) {
		fscanf(fp, "%s", skip_str);
	}

	fscanf(fp, "%ld %ld %ld %ld", &cpu_pid->utime, &cpu_pid->stime, &cpu_pid->cutime, &cpu_pid->cstime);
	long sum = cpu_pid->utime + cpu_pid->stime + cpu_pid->cutime + cpu_pid->cstime;
	if(sum != cpu_pid->time_sum){		//保存数据变化时间点
		cpu_pid->sum_change_used_time = time - cpu_pid->sum_change_last_time;
		cpu_pid->sum_change_last_time = time;
		cpu_pid->last_power_rate = cpu_power_rate * (sum-cpu_pid->time_sum)/((double)cpu_pid->sum_change_used_time/1000000000.0)/clks;
	}
	cpu_pid->time_sum = sum;

	skip_num = 4;
	for (i = 0; i < skip_num; i++) {
		fscanf(fp, "%s", skip_str);
	}

	fscanf(fp, "%ld", &cpu_pid->starttime);

	/*
	int lev_num = 0;
	while (!feof(fp)) {
		int n = fscanf(fp, "%s", skip_str);
		if (n == 1) {
			lev_num++;
		}
	}
	if (lev_num != 25) {
		logE("cpu pid info cat error: lev_num error");
	}*/

	fclose(fp);
}

void cpu_pid_info_display(struct cpu_pid_info * cpu_pid)
{
	if (cpu_pid == NULL) {
		logE("cpu pid info display error: cpu_pid is null");
		return;
	}

	log("===========[ cpu_pid_info ]===========");
	log("pid=%d", cpu_pid->pid);
	log("filename=%s", cpu_pid->filename);
	log("%ld %ld %ld %ld", cpu_pid->utime, cpu_pid->stime, cpu_pid->cutime, cpu_pid->cstime);
}

int get_cpu_clk_tck()
{
	int n = sysconf(_SC_CLK_TCK);
	log("clk tck=%d", n);
	return n;
}

void cpu_info_test()
{
	struct cpu_info* cpus;
	int core_num;
	int i;
	get_cpu_clk_tck();
	cpus = cpu_info_init(&core_num);
	for (i = 0; i < core_num; i++) {
		cpu_info_cat(&cpus[i]);
		cpu_info_display(&cpus[i]);
	}
}

void cpu_pid_info_test()
{
	log("==================================================");
	struct cpu_pid_info cpu_pid;
	//cpu_pid_info_cat(&cpu_pid, 20901);
	cpu_pid_info_display(&cpu_pid);
	sleep(10);
	log("==================================================");
	//cpu_pid_info_cat(&cpu_pid, 20901);
	cpu_pid_info_display(&cpu_pid);
}
