package com.xd.powercatsence;

import java.util.Map;
import java.util.TreeMap;

import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;

public class PhoneSence extends SenceThread{

	private String senceName = "电话短信";
	
    public static final String TelephoneWork = "tel";
    public static final String MessageWork = "mes";
    
    private String workId = TelephoneWork;

    public void workTel()  {  
    	
    	String callee = "15191430319";
		Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + callee));
		startActivity(i);//触发这个拨号
 }  
       


	private void startActivity(Intent i) {
		// TODO Auto-generated method stub
		
	}



	// double类型的计算函数     
    public void workMes() {  
    	
    	 String phoneNo ="15191430319";
         String message = "hello word";  
         sendSMS(phoneNo, message);

    } 
    private void sendSMS(String phoneNumber, String message)
    {        

        SmsManager sms = SmsManager.getDefault();
        
       // Log.v("ROGER", "will send SMS");
        sms.sendTextMessage(phoneNumber, null, message, null, null);        
    }  


   //计算选择方案
	@Override
	void worker() throws InterruptedException {
		while(isRun){
			if (workId.equals(TelephoneWork )) {
				workTel();
			}
			if (workId.equals(MessageWork )) {
				workMes();
			}
			super.callOnChange();
		}
	}
//MAP是一个存储类
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
//
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
