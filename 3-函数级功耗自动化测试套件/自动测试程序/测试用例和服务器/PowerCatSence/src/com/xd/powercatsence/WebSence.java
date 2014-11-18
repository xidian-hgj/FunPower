package com.xd.powercatsence;

import java.util.Map;
import java.util.TreeMap;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebSence extends SenceThread{

	private String senceName = "网页浏览";
	private WebView wv;
	boolean flag=true; 
    public static final String w = "w";
    private String workId = w;
    String site = "192.168.253.1";
    int  count=0;
    public void workw() {  
    	
    	
    	final String url2 = "http://"+site+":8080/myapp/hao123.htm";
    	
    	wv.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				wv.loadUrl(url2);
				wv.setWebViewClient(new WebViewClient(){
					
		    		public void onPageFinished(WebView wv,String url2){
		    					    		
		    		}
		    	});
				
			}
		}); 
    	
    	
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    
    	count++;
    	System.out.println("网页执行是的次数："+count);
		
    }			
  
    //suface 的调用接口函数
    public void setWebView(WebView wv){
    	this.wv = wv;
    }
    
	@Override
	void worker() throws InterruptedException {
		while(isRun){
			if (workId.equals(w)) {
				workw();
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
