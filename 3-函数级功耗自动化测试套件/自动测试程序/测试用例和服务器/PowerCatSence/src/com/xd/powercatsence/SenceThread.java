package com.xd.powercatsence;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

public abstract class SenceThread  extends Thread{
	protected static final String TAG = "SenceThread";
	public Thread thread;
//	protected boolean isRun = true;
	protected boolean isRun = false;
	private long startTime;
	private long workTime;
	protected SenceListener sl;
	
	public SenceThread(){
	}
	
	private Runnable runnable = new Runnable() {
	@Override
	public void run() {
		try {
			worker();
			Log.d(TAG, "thread end by worker exit");
		} catch (InterruptedException e) {
			Log.d(TAG, "thread end by stop");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (sl!=null) {
			sl.onStop(getRunedTime(), workTime);
		}
	}
	};

		
	public void start() {
		if (isRun == false) {
			isRun = true;
			thread = new Thread(runnable);
			thread.start();
			startTime = System.currentTimeMillis();
			
			if (workTime>0) {
				Timer timer = new Timer();
				timer.schedule(new StopTask(), workTime);
			}
			
			if (sl!=null) {
				sl.onStart(startTime, workTime);
			}
			}
	}

	public void stop1() {
		if (isRun==true) {
			isRun = false;
			thread.interrupt();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	class StopTask extends TimerTask{
		@Override
		public void run() {
			stop1();
		}
	}
	
	public void setListener(SenceListener sl) {
		this.sl = sl;
	}
	
	public long getRunedTime() {
		if (startTime==0) {
			return 0;
		}else {
			return System.currentTimeMillis() - startTime;
		}
	}
	
	public long getWorkTime() {
		return workTime;
	}

	public void setWorkTime(long workTime) {
		this.workTime = workTime;
	}
	
	public void configMap(Map<String, String> map) {
		if (map!=null) {
			String val = map.get("workTime");
			if (val!=null) {
				workTime = Long.parseLong(val);
			}
		}
	}
	public void getConfigMap(Map<String, String> map) {
		if (map!=null) {
			map.put("workTime", ""+workTime);
		}
	}
	
	public  String getStartTime() {
		DateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.CHINESE);
		Date date =  new Date(startTime);
		return format.format(date);
	}

	abstract void worker() throws InterruptedException, IOException;
	abstract void config(String str);
	abstract String getConfig();
	abstract String getInfo();
	abstract String getSenceName();

	public void callOnChange() {
		if(sl!=null){
			sl.onChange(getRunedTime(), workTime);
		}
	}
	public void callOnInfo() {
		if(sl!=null){
			sl.onInfo(getInfo());
		}
	}
}
