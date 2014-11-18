package com.xd.powercatsence;

import java.text.DecimalFormat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SenceView extends RelativeLayout implements SenceListener, OnCheckedChangeListener{
	
	protected static final int CMD_OnChange = 1;
	protected static final int CMD_OnStop = 2;
	private static final int CMD_OnStart = 3;
	private static final int CMD_OnInfo = 4;
	
	SenceThread sence;
	TextView senceName;
	TextView startTime;
	TextView workTime;
	TextView runTime;
	TextView infoStr;
	EditText configStr;
	ProgressBar progressBar;
	ToggleButton toggleBtn;
	String info = "";
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if (msg.what==CMD_OnChange) {
				updateSence();
			}
			if (msg.what==CMD_OnStop) {
				updateSence();
				toggleBtn.setChecked(false);
			}
			if (msg.what==CMD_OnStart) {
				updateSence();
				toggleBtn.setChecked(true);
			}
			if (msg.what==CMD_OnInfo) {
				infoStr.setText(info);
			}
			super.handleMessage(msg);
		}
		
	};
	
	public SenceView(Context context) {
		super(context);
		init(context);
	}
	
	public SenceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.sence_info, this);
		
		senceName = (TextView) findViewById(R.id.si_name);
		startTime = (TextView) findViewById(R.id.si_starttime);
		workTime = (TextView) findViewById(R.id.si_worktime);
		runTime = (TextView) findViewById(R.id.si_runtime);
		infoStr = (TextView) findViewById(R.id.si_info);
		configStr = (EditText) findViewById(R.id.si_config);
		progressBar = (ProgressBar) findViewById(R.id.si_progressbar);
		toggleBtn = (ToggleButton) findViewById(R.id.si_tooglebtn);
		toggleBtn.setOnCheckedChangeListener(this);
	}

	public void bindSenceThread(SenceThread sence) {
		if (sence==null) {
			return;
		}
		this.sence = sence;
		this.sence.setListener(this);
		updateSence();
	}
	
	private void updateSence(){
		DecimalFormat df = new DecimalFormat("0.00");
		senceName.setText(sence.getSenceName());
		startTime.setText("开始时间:"+sence.getStartTime());
		workTime.setText("计划时间:"+df.format(sence.getWorkTime()/1000.0)+"S");
		runTime.setText("运行时间:"+df.format(sence.getRunedTime()/1000.0)+"S");
		configStr.setText(sence.getConfig());
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView == toggleBtn) {
			if (isChecked==true) {
				String str = configStr.getText().toString();
				sence.config(str);
				sence.start();
				
			}else {
				sence.stop1();
			}
			return;
		}
	}

	@Override
	public void onStart(long startTime, long workTime) {
		handler.sendEmptyMessage(CMD_OnStart);
		if (workTime>0) {
			progressBar.setIndeterminate(false);
			progressBar.setMax((int) workTime);
		}else {
			progressBar.setIndeterminate(true);
			//progressBar.setMax(Integer.MAX_VALUE);
		}
	}

	@Override
	public void onStop(long runedTime, long workTime) {
		handler.sendEmptyMessage(CMD_OnStop);
		progressBar.setProgress((int) runedTime);
		progressBar.setIndeterminate(false);
	}

	@Override
	public void onChange(long num, long all) {
		progressBar.setProgress((int) num);
		handler.sendEmptyMessage(CMD_OnChange);
	}

	@Override
	public void onInfo(String info) {
		this.info = info;
		handler.sendEmptyMessage(CMD_OnInfo);
	}



}
