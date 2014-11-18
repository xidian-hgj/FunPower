package com.xd.powercatsence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import android.os.Environment;


public class FtpTransportSence extends SenceThread {

	private String senceName = "���紫��-FTP";
	
	public static final String downFile = "sg";
	
    
    private String workId =downFile;

    String site = "192.168.253.1";
    
	
    public static boolean downFile(
            String url,              //FTP������hostname  
            int port,                //FTP�������˿�  
            String username,         //FTP��¼�˺�  
            String password,          //FTP��¼����  
            String remotePath,       //FTP�������ϵ����·��   
            String fileName,         //Ҫ���ص��ļ���  
            String localPath        //���غ󱣴浽���ص�·�� 

            ) {    
        boolean success = false;    
        FTPClient ftp = new FTPClient();    
        try {    
            int reply;    
            ftp.connect(url, port);
            //�������Ĭ�϶˿ڣ�����ʹ��ftp.connect(url)�ķ�ʽֱ������FTP������     
            ftp.login(username, password);//��¼ 
            
            reply = ftp.getReplyCode();    
            if (!FTPReply.isPositiveCompletion(reply)) {    
                ftp.disconnect();    
                return success;    
            }
            
            ftp.changeWorkingDirectory(remotePath);//ת�Ƶ�FTP������Ŀ¼     
            FTPFile[] fs = ftp.listFiles();  
            
            for(FTPFile ff:fs){ 
             System.out.println("bb" + fs);
             
                if(ff.getName().equals(fileName)){  
                 System.out.println("dd");
                    File localFile = new File(localPath+"/"+ff.getName());    
                    OutputStream is = new FileOutputStream(localFile);     
                    ftp.retrieveFile(ff.getName(), is);  
                    System.out.println("ccc" +ff.getName()+fileName);
                    is.close();    
                }    
            }    
            ftp.logout();    
            success = true;    
           } catch (IOException e) {    
            e.printStackTrace();    
            } finally {    
               if (ftp.isConnected()) {  }    
              }    
        return success;    
     }
           

		void worker() throws InterruptedException {
				while(isRun){
					if (workId.equals(downFile)) {
						String filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
						downFile( site,21,"admin","admin","./","1.txt",filePath);
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