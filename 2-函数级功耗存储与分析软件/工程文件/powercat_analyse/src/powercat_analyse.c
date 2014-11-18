/*
 ============================================================================
 Name        : powercat_analyse.c
 Author      : hexingshi
 Version     :
 Copyright   : hexingshi
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include "parse_params.h"
#include "data_analyse.h"
#include "log.h"


PowerDataSet * set;
int main(int argc, char *argv[])
{
	//parse_params(argc, argv);
	strcpy(filePath, "power.dat");
	loadPowerDataInFile(filePath, &set);
	long n = getPowerSetNum(set);
	double power;
	PowerData * data;

	log("count=%ld", n);

	data = getPowerData(set, 0);
	printPowerData(data);
	data = getPowerData(set, 1);
	printPowerData(data);
	data = getPowerData(set, 10);
	printPowerData(data);
	data = getPowerData(set, 100);
	printPowerData(data);
	data = getPowerData(set, n - 1);
	printPowerData(data);

	long long s, e;
	s = 1236195630235;
	e = 1236196216605;
	power = getPower(0, set, s, e);
	log("start=%lld end=%lld power=%lf", s, e, power);

	s = 1236195630235;
	e = 1236196645791;
	power = getPower(0, set, s, e);
	log("start=%lld end=%lld power=%lf", s, e, power);

	s = 1236195630235;
	e = 1236196216610;
	power = getPower(0, set, s, e);
	log("start=%lld end=%lld power=%lf", s, e, power);



	while (1) {
		;
	}

	return 0;
}
