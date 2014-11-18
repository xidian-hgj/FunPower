package com.xd.powercatsence;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class LaunchActivity extends Activity{
	Button button9;
	Button button10;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch);
 
        button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LaunchActivity.this,AutomaticActivity.class);
				startActivity(intent);
				LaunchActivity.this.finish();
				
			}
        	
        });
        
        button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LaunchActivity.this,MainActivity.class);
				startActivity(intent);
				LaunchActivity.this.finish();
				
			}
        	
        });
    }
}
