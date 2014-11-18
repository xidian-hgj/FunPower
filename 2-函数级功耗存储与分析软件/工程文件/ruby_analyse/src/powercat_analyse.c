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
#include <ruby.h>
#include "parse_params.h"
#include "data_analyse.h"
#include "log.h"

PowerDataSet * set;

VALUE method_load_PowerData(VALUE self, VALUE v_path);
VALUE method_get_Power(VALUE self, 
	VALUE v_cpu, VALUE v_name, VALUE v_cTime, VALUE v_rTime) ;
 
VALUE analyse = Qnil; 
void Init_power(){
	analyse = rb_define_module("Power");
	rb_define_method(analyse, "load_PowerData", RUBY_METHOD_FUNC(method_load_PowerData), 1);
	rb_define_method(analyse, "get_Power", RUBY_METHOD_FUNC(method_get_Power), 4);
	log("ruby lib require success");
}

VALUE method_load_PowerData(VALUE self, VALUE v_path) {  
	long len = 0;
	int ret = 0;
	char * path = rb_str2cstr(v_path, &len);
	ret = loadPowerDataInFile(path, &set);
	return INT2NUM(ret);
}  

VALUE method_get_Power(VALUE self, 
	VALUE v_cpu, VALUE v_name, VALUE v_cTime, VALUE v_rTime) {  
	char *name;
	int cpu;
	char * s_str;
	char * e_str;
	long long s, e;
	double power;
	long len = 0;

	name = rb_str2cstr(v_name, &len);
	cpu = NUM2INT(v_cpu);
	s_str = rb_str2cstr(v_cTime, &len);
	e_str = rb_str2cstr(v_rTime, &len);
	s = atoll(s_str);
	e = atoll(e_str);

	power = getPower(cpu, set, s, e);

	return rb_float_new(power);
} 



