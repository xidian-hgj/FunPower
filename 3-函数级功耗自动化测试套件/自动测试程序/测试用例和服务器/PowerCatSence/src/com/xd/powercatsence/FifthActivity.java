package com.xd.powercatsence;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FifthActivity extends Activity{
	protected static final String TAG = "FihthActivity";
	ComMesSence comMesWork;
	TcpSence tcpWork;
	Button button7;
	
	SenceView comMesSence;
    SenceView tcpSence;
	
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fifth);
  
        
        comMesSence = (SenceView) findViewById(R.id.comMesSence);
        comMesWork = new ComMesSence();
        comMesSence.bindSenceThread(comMesWork);
        
        tcpSence = (SenceView) findViewById(R.id.tcpSence1);
        tcpWork = new TcpSence();
        tcpSence.bindSenceThread(tcpWork);
        
        button7 = (Button) findViewById(R.id.button7);
        button7.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FifthActivity.this,FourthActivity.class);
				startActivity(intent);
				FifthActivity.this.finish();
				
			}
        	
        });
        
    }
}
