package com.xd.powercatsence;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.Socket;
import java.util.Map;
import java.util.TreeMap;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class FileTraSence extends SenceThread{

	String site ="172.25.159.8";
	private String senceName = "文件上传";
	private Context context;
	
	private UploadLogService logService;
	String filename = "c.txt"; 
	
	public static final String ft = "ft";
    private String workId = ft;
    
    public FileTraSence(Context context)  
    {  
        this.logService = new UploadLogService(context);  
    } 
    
        public void workFt() {
        	Upload(filename);
        } 
        
        public void Upload(String name){
        	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))  
            {  
                //取得SDCard的目录 
                File upLoadFile = new File(Environment.getExternalStorageDirectory(), name); 
                if(upLoadFile.exists())  
                {  
                    uploadFile(upLoadFile);  
                }  
        	
        }
        }
        
        private Handler handler = new Handler()  
        {  
            @Override  
            public void handleMessage(Message msg)   
            {  
                //获得上传长度的进度   
                int length = msg.getData().getInt("size");  
                 }  
        };
       
        private void uploadFile(final File upLoadFile)   
        {
                   try   
                    { 
                  	     int fileLength = (int)upLoadFile.length();
                        //判断文件是否已有上传记录   
                        String souceid = logService.getBindId(upLoadFile); 
                       
                         //构造拼接协议   
                        String head = "TransferType=upload; "+"Content-Length="+ fileLength + "; filename="+ upLoadFile.getName() + "; sourceid="+  
                        (souceid==null? "" : souceid)+"\r\n";  
                        //通过Socket取得输出流           
                        Socket socket = new Socket(site, 7878); 
                        OutputStream outStream = socket.getOutputStream(); 
                        outStream.write(head.getBytes());                                    
                          
                        PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());      
                        //获取到字符流的id与位置   
                        String response = StreamTool.readLine(inStream);  
                        System.out.println(response);
                        String[] items = response.split(";");  
                        String responseid = items[0].substring(items[0].indexOf("=")+1);  
                        String position = items[1].substring(items[1].indexOf("=")+1);  
                        //代表原来没有上传过此文件，往数据库添加一条绑定记录   
                        if(souceid==null)  
                        {  
                            logService.save(responseid, upLoadFile);  
                        }  
                        InputStream inputStream = new FileInputStream(upLoadFile);
                        byte[] buffer = new byte[1024];  
                        int len = -1;  
                        //初始化长传的数据长度   
                        int length = Integer.valueOf(position);  
                        while( (len = inputStream.read(buffer)) != -1)  
                        {  
                      	     System.out.println(buffer);
                            outStream.write(buffer, 0, len);  
                            //设置长传数据长度   
                            length += len;  
                            Message msg = new Message();  
                            msg.getData().putInt("size", length);  
                            handler.sendMessage(msg);  
                        }   
                        inputStream.close();
                        outStream.close();  
                        inStream.close();  
                        socket.close();  
                        //判断上传完则删除数据   
                        if(length==upLoadFile.length())   
                            logService.delete(upLoadFile);  
                    }   
                    catch (Exception e)  
                    {  
                        e.printStackTrace();  
                    }   
        } 
        
        
   
  
   
	@Override
    void worker() throws InterruptedException {
		while(isRun){
			if (workId.equals(ft)) {
				workFt();
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

