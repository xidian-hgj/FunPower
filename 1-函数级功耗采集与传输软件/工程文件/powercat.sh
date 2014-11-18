#! /system/bin/sh

echo "=======[ PowerCat ]======"

name=$1
activity=$2
ipAddr="192.168.2.67"
port=8010
pid=
buffer_size=1048576

echo "Start the ${name} Benchmark"
if [ -n "$activity" ]; then
	echo "Android program is starting"	
	am start -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -n ${name}/.${activity} >> /dev/null
else
	echo "C program is starting"
	./${name} &
fi
echo 


echo "Get PID of ${name}"
pid=`ps | busybox grep ${name} | busybox awk '{print $2}'`
echo "the PID of ${name} is ${pid}"
echo

echo "Start the PowerCat collect program"
param_powercat="-p${pid} -a${ipAddr} -n${port} -b${buffer_size}"
echo "powercat param is ${param_powercat}"
./powercat ${param_powercat} &
echo

read key

busybox killall powercat
busybox killall ${name}
echo "PowerCat collect program be closed"
