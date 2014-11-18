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
//TCP��ʽʵ���ļ�����
public class TcpSence extends SenceThread {

	private String senceName = "�ļ�����";
	//���÷�������ip�Ͷ˿�
	 private static final int SERVERPORT  = 12345; 
	 private static final int CLIENTPORT  = 54321; 
	 public static final String tcpdown = "td";
	 String site= "172.25.159.8";//������ip  
	 private String workId =tcpdown;
	 // �������ܴ���������ַ�
	 void tcpworker(){
	   byte[] buf= new byte[100]; 
	   Socket socket = new Socket(); 
	   try {  
	   // ��������
	     socket.connect(new InetSocketAddress(site,SERVERPORT), CLIENTPORT); 
	     InputStream is = socket.getInputStream(); 
	     int len = is.read(buf);
	     String filename = new String(buf,0,len);
	   // ���մ��������ļ���
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