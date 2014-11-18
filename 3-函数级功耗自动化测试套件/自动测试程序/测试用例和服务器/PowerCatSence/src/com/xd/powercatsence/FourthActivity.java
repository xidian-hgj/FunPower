package com.xd.powercatsence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class FourthActivity extends Activity {

    protected static final String TAG = "FourthActivity";

    
    
    WebSence webWork;
    FileTraSence filetraWrok;
    WebVideoSence webVideoWork;
    Button button5;
    Button button6;
    
    SenceView webSence;
    SenceView fileTraSence;
    SenceView webVideoSence;
    WebView wv;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth);
        
        wv =  (WebView) findViewById(R.id.wv);
        webSence = (SenceView) findViewById(R.id.web_sence);
        webWork = new WebSence();
        webWork.setWebView(wv);
        webSence.bindSenceThread(webWork);
        
        fileTraSence = (SenceView) findViewById(R.id.filetra_sence);
        filetraWrok = new FileTraSence(this);
        fileTraSence.bindSenceThread(filetraWrok);
        
        webVideoSence = (SenceView) findViewById(R.id.webvideo_sence);
        webVideoWork = new WebVideoSence();
        webVideoSence.bindSenceThread(webVideoWork);
        
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FourthActivity.this,ThirdActivity.class);
				startActivity(intent);
				FourthActivity.this.finish();
				
			}
        	
        });
        
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FourthActivity.this,FifthActivity.class);
				startActivity(intent);
				FourthActivity.this.finish();
				
			}
        	
        });
        
    }

}

