package com.xd.powercatsence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends Activity{
	protected static final String TAG = "MainActivity";
	DialerMesSence DiaMesWork;
	VideoSence videoWork;
    TcpDownSence tcpWork;
    Button button1;
    Button button2;
    
    SenceView dialermesSence;
    SenceView videoSence;
    SenceView tcpSence;
    SurfaceView sView;  //添加一个surfaceview
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        
        dialermesSence = (SenceView) findViewById(R.id.DialerMesSence);
        DiaMesWork = new DialerMesSence(this);
        dialermesSence.bindSenceThread(DiaMesWork);
        
        //设置surfaceView及其相关属性
        sView=(SurfaceView)findViewById(R.id.dview);
        sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        sView.getHolder().setFixedSize(320, 280);
        sView.getHolder().setKeepScreenOn(true);
        
        videoSence = (SenceView) findViewById(R.id.videoSence);
        videoWork = new VideoSence();
        videoWork.setSurfView(sView.getHolder().getSurface());
        videoSence.bindSenceThread(videoWork);
        
        tcpSence = (SenceView) findViewById(R.id.tcpSence);
        tcpWork = new TcpDownSence();
        tcpSence.bindSenceThread(tcpWork);
        
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SecondActivity.this,MainActivity.class);
				startActivity(intent);
				SecondActivity.this.finish();
				
			}
        	
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SecondActivity.this,ThirdActivity.class);
				startActivity(intent);
				SecondActivity.this.finish();
				
			}
        	
        });
    }

}

