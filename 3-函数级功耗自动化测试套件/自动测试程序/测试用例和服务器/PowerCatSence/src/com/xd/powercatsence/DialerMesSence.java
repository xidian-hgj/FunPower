package com.xd.powercatsence;

import java.util.Map;
import java.util.TreeMap;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.util.Log;

public class DialerMesSence extends SenceThread{

	private String senceName = "����ͨ�������շ�";
	private Context context;

	
	
    public static final String dia = "dia";
    public static final String mes = "mes";
    
    public String workId = dia;
    
    public DialerMesSence(Context context){
    	this.context = context;
    }
    
    public void workDia() {  
    	String callee = "13152169050";//Ĭ�ϵĵ绰����
    	if (PhoneNumberUtils.isGlobalPhoneNumber(callee)){
			Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + callee));//����
			context.startActivity(i);
			
		} 
		
    }  
      
    public void workMes() {  
    	String phoneNo = "13152169050";//Ĭ�ϵĵ绰����
        String message = "��ã�";
        if (phoneNo.length()>0 && message.length()>0){                
        	Log.v("ROGER", "will begin sendSMS");
           	sendSMS(phoneNo, message);//���Ͷ���
           	
        }
        
    

}
//ʵ�ֶ��ŵķ���
private void sendSMS(String phoneNumber, String message)
{        
	
	PendingIntent pi = PendingIntent.getActivity(context, 0,new Intent(), 0);  
	Log.v("ROGER", "will init SMS Manager");
	SmsManager sms =SmsManager.getDefault();

	Log.v("ROGER", "will send SMS");
	sms.sendTextMessage(phoneNumber, null, message, pi, null);        
}  
    	
 
    
    
	@Override
	void worker() throws InterruptedException {
		while(isRun){
			if (workId.equals(dia)) {
				workDia();
			}
			if (workId.equals(mes)) {
				workMes();
			}
			super.callOnChange();
		}
	}

	@Override
	void config(String str) {
		Map<String, String> map = MapUtil.stringToMap(str);
		if (map!=null) {
			configMap(map);
			String val = map.get("workId");
			if (val!=null) {
				workId = val;
			}
		}
	}

	@Override
	String getSenceName() {
		return senceName;
	}

	@Override
	String getConfig() {
		Map<String, String> map = new TreeMap<String, String>();
		getConfigMap(map);
		map.put("workId", ""+workId);
		String str = MapUtil.mapToString(map);
		return str;
	}

	@Override
	String getInfo() {
		return getConfig();
	}
}

