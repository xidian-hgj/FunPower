package com.xd.powercatsence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends Activity{
	protected static final String TAG = "ThirdActivity";
	FtpTransportSence FtpWork;
	HttpTransportSence HttpWork;
	UdpDownSence UdpWork;
    Button button3;
    Button button4;
    
    SenceView ftpSence;
    SenceView httpSence;
    SenceView udpSence;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
       
        
        ftpSence = (SenceView) findViewById(R.id.ftpSence);
        FtpWork = new FtpTransportSence();
        ftpSence.bindSenceThread(FtpWork);
        
        httpSence = (SenceView) findViewById(R.id.httpSence);
        HttpWork = new HttpTransportSence();
        httpSence.bindSenceThread(HttpWork);
        
        udpSence = (SenceView) findViewById(R.id.udpSence);
        UdpWork = new UdpDownSence();
        udpSence.bindSenceThread(UdpWork);
        
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ThirdActivity.this,SecondActivity.class);
				startActivity(intent);
				ThirdActivity.this.finish();
				
			}
        	
        });
        
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ThirdActivity.this,FourthActivity.class);
				startActivity(intent);
				ThirdActivity.this.finish();
				
			}
        	
        });
    }

}

