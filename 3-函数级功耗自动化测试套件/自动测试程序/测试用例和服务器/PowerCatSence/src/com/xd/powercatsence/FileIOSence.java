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
	private String senceName = "文件读写";
	
	
	final static String FOLDER = "/sample/";  
	String FILENAME = "test";
	final static String SUFFIX = ".txt";
	
	String fos = "写文件";
	String fos1="设计测试用例的目的是通过对一些典型场景的分析，结合源码静态分析结果，" +
			"对Android的各种场景运行过程中所消耗的功率进行评估。测试用例运行过程中，" +
			"CPU、Wifi和3G模块都会产生功率消耗，进程功耗可以通过采集模块的功率曲线，并计算测试进程在每个时间段内的权重得到。" ;//写入文件的内容
	
    
	final static String TAG= "AAAA";
	public static final String readWork = "read";
	public static final String writeWork = "write";
	
	public String workId = readWork;
	
	

	public String workRead() {

		String result = null;
		String fos2;
		fos2=FILENAME;
		for(int i=0;i<10;i++){
			result = readFile1(fos2);//读文件
	    }
	    return result;
		
		
	}

	public boolean workWrite() {
		
	    
		for(int i=0;i<10;i++){
			
			writeFile(fos);//写文件
		}
		return true;
	}
	
	private String readFile1(String sb) {   
		String foldername = Environment.getExternalStorageDirectory().getPath()   
                + FOLDER;//获取SD卡的文件路径
		 
		String Path = foldername + sb + SUFFIX;//读取文件的路径为sdcard/sample/test.txt
	      
		if (null == Path) {   
	        Log.d(TAG, "Error: Invalid file name!");   
	        return null;   
	    }   
	 
	    String filecontent = null;   
	    File f = new File(Path);   
	    if (f != null && f.exists()) {
	        FileInputStream fis = null;   
	        try {   
	            fis = new FileInputStream(f); //将文件放到  FileInputStream流中
	        } catch (FileNotFoundException e1) {   
	            // TODO Auto-generated catch block   
	            e1.printStackTrace();   
	            Log.d(TAG, "Error: Input File not find!");   
	            return null;   
	        }   
	  
	        CharBuffer cb;   
	        try {   
	            cb = CharBuffer.allocate(fis.available()); //创建一个  CharBuffer
	        } catch (IOException e1) {   
	            // TODO Auto-generated catch block   
	            e1.printStackTrace();   
	            Log.d(TAG, "Error: CharBuffer initial failed!");   
	            return null;   
	        }   
	    
	        InputStreamReader isr;   
	        try {   
	            isr = new InputStreamReader(fis, "utf-8");  //用utf-8的格式读取文件
	            try {   
	                if (cb != null) {   
	                   isr.read(cb);  //当文件不为空是读取文件到cb中
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
	                             + FOLDER; //获取SD卡的文件路径  
	    File folder = new File(foldername); //通过将给定路径名字符串转换为抽象路径名来创建一个文件。  
	    if (folder != null && !folder.exists()) {   
	        if (!folder.mkdir() && !folder.isDirectory())   
	        {   
	            Log.d(TAG, "Error: make dir failed!");   
	            return ;   
	        }   
	    }   
	   
	    //String stringToWrite = sb.toString();   
	    String targetPath = foldername + FILENAME+ SUFFIX;//写入文件的路径为sdcard/sample/test.txt
	    File targetFile = new File(targetPath); //通过将给定路径名字符串转换为抽象路径名来创建一个文件。   
	    if (targetFile != null) {   
	        if (targetFile.exists()) {   
	            targetFile.delete();//如果在给定路径下存在所写的文件就删掉   
	        }   
	 
	        OutputStreamWriter osw;   
	        try{   
	            osw = new OutputStreamWriter(   
	                        new FileOutputStream(targetFile),"utf-8");   //用utf-8的格式写入文件
	            try {   
	                osw.write(sb);//写文件   
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
