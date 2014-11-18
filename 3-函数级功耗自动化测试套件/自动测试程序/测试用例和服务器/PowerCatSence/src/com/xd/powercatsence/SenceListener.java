package com.xd.powercatsence;


public interface SenceListener {
	void onStart(long startTime, long workTime);
	void onStop(long runedTime, long workTime);
	void onChange(long num, long all);
	void onInfo(String info);
}
