package com.xd.powercatsence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.util.Map;
import java.util.TreeMap;

import android.os.Environment;
import android.util.Log;

public class FileIOSence extends SenceThread{
	private String senceName = "�ļ���д";
	
	
	final static String FOLDER = "/sample/";  
	String FILENAME = "test";
	final static String SUFFIX = ".txt";
	
	String fos = "д�ļ�";
	String fos1="��Ʋ���������Ŀ����ͨ����һЩ���ͳ����ķ��������Դ�뾲̬���������" +
			"��Android�ĸ��ֳ������й����������ĵĹ��ʽ��������������������й����У�" +
			"CPU��Wifi��3Gģ�鶼������������ģ����̹��Ŀ���ͨ���ɼ�ģ��Ĺ������ߣ���������Խ�����ÿ��ʱ����ڵ�Ȩ�صõ���" ;//д���ļ�������
	
    
	final static String TAG= "AAAA";
	public static final String readWork = "read";
	public static final String writeWork = "write";
	
	public String workId = readWork;
	
	

	public String workRead() {

		String result = null;
		String fos2;
		fos2=FILENAME;
		for(int i=0;i<10;i++){
			result = readFile1(fos2);//���ļ�
	    }
	    return result;
		
		
	}

	public boolean workWrite() {
		
	    
		for(int i=0;i<10;i++){
			
			writeFile(fos);//д�ļ�
		}
		return true;
	}
	
	private String readFile1(String sb) {   
		String foldername = Environment.getExternalStorageDirectory().getPath()   
                + FOLDER;//��ȡSD�����ļ�·��
		 
		String Path = foldername + sb + SUFFIX;//��ȡ�ļ���·��Ϊsdcard/sample/test.txt
	      
		if (null == Path) {   
	        Log.d(TAG, "Error: Invalid file name!");   
	        return null;   
	    }   
	 
	    String filecontent = null;   
	    File f = new File(Path);   
	    if (f != null && f.exists()) {
	        FileInputStream fis = null;   
	        try {   
	            fis = new FileInputStream(f); //���ļ��ŵ�  FileInputStream����
	        } catch (FileNotFoundException e1) {   
	            // TODO Auto-generated catch block   
	            e1.printStackTrace();   
	            Log.d(TAG, "Error: Input File not find!");   
	            return null;   
	        }   
	  
	        CharBuffer cb;   
	        try {   
	            cb = CharBuffer.allocate(fis.available()); //����һ��  CharBuffer
	        } catch (IOException e1) {   
	            // TODO Auto-generated catch block   
	            e1.printStackTrace();   
	            Log.d(TAG, "Error: CharBuffer initial failed!");   
	            return null;   
	        }   
	    
	        InputStreamReader isr;   
	        try {   
	            isr = new InputStreamReader(fis, "utf-8");  //��utf-8�ĸ�ʽ��ȡ�ļ�
	            try {   
	                if (cb != null) {   
	                   isr.read(cb);  //���ļ���Ϊ���Ƕ�ȡ�ļ���cb��
	                }   
	                filecontent = new String(cb.array());   
	                isr.close();   
	            } catch (IOException e) {   
	                e.printStackTrace();   
	            }   
	        } catch (UnsupportedEncodingException e) {   
	            // TODO Auto-generated catch block   
	            e.printStackTrace();           
	        }   
	    }   
	    Log.d(TAG, "readFile filecontent");   
	    return filecontent;   
	}
	
	private void writeFile(String sb) {
	    String foldername = Environment.getExternalStorageDirectory().getPath()   
	                             + FOLDER; //��ȡSD�����ļ�·��  
	    File folder = new File(foldername); //ͨ��������·�����ַ���ת��Ϊ����·����������һ���ļ���  
	    if (folder != null && !folder.exists()) {   
	        if (!folder.mkdir() && !folder.isDirectory())   
	        {   
	            Log.d(TAG, "Error: make dir failed!");   
	            return ;   
	        }   
	    }   
	   
	    //String stringToWrite = sb.toString();   
	    String targetPath = foldername + FILENAME+ SUFFIX;//д���ļ���·��Ϊsdcard/sample/test.txt
	    File targetFile = new File(targetPath); //ͨ��������·�����ַ���ת��Ϊ����·����������һ���ļ���   
	    if (targetFile != null) {   
	        if (targetFile.exists()) {   
	            targetFile.delete();//����ڸ���·���´�����д���ļ���ɾ��   
	        }   
	 
	        OutputStreamWriter osw;   
	        try{   
	            osw = new OutputStreamWriter(   
	                        new FileOutputStream(targetFile),"utf-8");   //��utf-8�ĸ�ʽд���ļ�
	            try {   
	                osw.write(sb);//д�ļ�   
	                osw.flush();   
	                osw.close();
	              
	            } catch (IOException e) {   
	                // TODO Auto-generated catch block   
	                e.printStackTrace();   
	            }   
	        } catch (UnsupportedEncodingException e1) {   
	            // TODO Auto-generated catch block   
	            e1.printStackTrace();   
	        } catch (FileNotFoundException e1) {   
	            // TODO Auto-generated catch block   
	            e1.printStackTrace();   
	        }   
	    }   
	}

	@Override
	void worker() throws InterruptedException {
		for(int i=0;i<200;i++){
			fos = fos+fos1;
		}
		
		while (isRun) {
			if (workId.equals(readWork)) {
				workRead();
			}
			if (workId.equals(writeWork)) {
				workWrite();
			}
			super.callOnChange();
		}
	}

	@Override
	void config(String str) {
		Map<String, String> map = MapUtil.stringToMap(str);
		if (map != null) {
			configMap(map);
			String val = map.get("workId");
			if (val != null) {
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
		map.put("workId", "" + workId);
		String str = MapUtil.mapToString(map);
		return str;
	}

	@Override
	String getInfo() {
		return getConfig();
	}

}
