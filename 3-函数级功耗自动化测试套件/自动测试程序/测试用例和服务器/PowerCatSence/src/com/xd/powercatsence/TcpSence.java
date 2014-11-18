package com.xd.powercatsence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import android.os.Environment;
//TCP方式实现文件下载
public class TcpSence extends SenceThread {

	private String senceName = "文件下载";
	//设置服务器的ip和端口
	 private static final int SERVERPORT  = 12345; 
	 private static final int CLIENTPORT  = 54321; 
	 public static final String tcpdown = "td";
	 String site= "172.25.159.8";//服务器ip  
	 private String workId =tcpdown;
	 // 用来接受传输过来的字符
	 void tcpworker(){
	   byte[] buf= new byte[100]; 
	   Socket socket = new Socket(); 
	   try {  
	   // 建立连接
	     socket.connect(new InetSocketAddress(site,SERVERPORT), CLIENTPORT); 
	     InputStream is = socket.getInputStream(); 
	     int len = is.read(buf);
	     String filename = new String(buf,0,len);
	   // 接收传输来的文件名
	   System.out.println(filename);
	   
	   String filePath1=Environment.getExternalStorageDirectory().getAbsolutePath()+"/1.txt";
   	
	   File saveFile = new File(filePath1);
	   FileOutputStream fos = new FileOutputStream(saveFile); 
	   int data; 
	    while(-1!=(data = is.read())) {  
	    	fos.write(data); 
	    } 
	    is.close();
	    fos.close(); 
	    socket.close(); 
	   } catch(IOException e) {  
	   // TODO Auto-generated catch block
		   e.printStackTrace(); 
	   } 
      
	}
	void worker() throws InterruptedException {
		while(isRun){
			if (workId.equals(tcpdown)) {
				tcpworker();
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