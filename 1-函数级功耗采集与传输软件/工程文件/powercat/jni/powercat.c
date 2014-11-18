#include <stdio.h>
#include <unistd.h>
#include <stdbool.h>
#include <time.h>
#include <string.h>
#include <signal.h>
#include "log.h"
#include "cpuinfo.h"
#include "infosend.h"
#include "parse_params.h"
#include "netinfo.h"

void cpu_worker(int pid);
long long get_time();
void sig_int(int signo);

int main(int argc, char * argv[])
{
	parse_params(argc, argv);

	log("powercat start");
	signal(SIGINT, sig_int);

	infoSender_init(param_ipAddr, param_port, buffer_size);
	cpu_worker(param_pid);

	return 0;
}

void sig_int(int signo)
{
	infoSender_close();
}

void cpu_worker(int pid)
{

	int i;
	struct cpu_info * cpus;
	struct cpu_pid_info cpu_pid;

	int core_num;
	int clk_tck;
	long long time, last_time;
	double core_power[32];			//核心功耗
	double cpu_power;				//cpu总功耗
	double cpu_power_rate;

	double wifi, radio;

	memset(&cpu_pid, 0, sizeof(struct cpu_pid_info));

	clk_tck = get_cpu_clk_tck();
	log("clk_tck=%d", clk_tck);
	log("开始采集数据");

	//初始化cpu功耗采集
	cpus = cpu_info_init(&core_num);
	for (i = 0; i < core_num; i++) {
		cpus[i].power = 0.0;
	}

	//初始化进程功耗采集
	cpu_pid.sum_change_used_time = 0;
	cpu_pid.sum_change_last_time = get_time();
	cpu_pid.power = 0.0;

	wifi = 0.0;
	radio = 0.0;
	time = get_time();

	while (1) {
		last_time = time;
		time = get_time();

		//cpu power cat
		cpu_power_rate = 0;
		for (i = 0; i < core_num; i++) {
			cpu_info_cat(&cpus[i]);
			double power_rate = (cpus[i].cur_freq / (double) cpus[i].max_freq) * CPU_POWER_FULL / core_num;
			cpu_power_rate += power_rate;
			//printf("%ld, %lf\n", cpus[i].cur_freq, cpu_power_rate);
			cpus[i].power += power_rate * (time - last_time) / 1000000000.0;
			core_power[i] = cpus[i].power;
		}

		//cpu总功耗
		cpu_power=0;
		for(i=0; i<core_num; i++){
			cpu_power += core_power[i];
		}

		//printf("%lf %lf %lld\n", core_power[0], core_power[1], time - last_time);

		//pid power cat
		cpu_pid_info_cat(&cpu_pid, pid, time, cpu_power_rate, clk_tck*core_num);
		cpu_pid.power += cpu_pid.last_power_rate * (time - last_time) / 1000000000.0;

		wifi += net_wifi_rx() + net_wifi_tx();
		radio += net_radio_rx() + net_radio_tx();

		//log("%lf %lf", wifi, radio);
		infoSender_write(time, core_num, core_power, wifi, radio, cpu_pid.power);
	}
}




long long get_time()
{
	long long tm;
	struct timespec tp;

	clock_gettime(CLOCK_MONOTONIC, &tp);

	tm = tp.tv_sec * 1000000000ll + tp.tv_nsec;
	return tm;
}

