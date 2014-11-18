package com.PowerCatServer.Package;

import java.io.*;
import java.net.Socket;


 
public class SocketClient   
{  
	public static String site="172.25.159.8";
    public static void main(String[] args)   
    {  
        try   
        {     
            //这里的套接字根据实际服务器更改   
            Socket socket = new Socket(site, 7878);  
            OutputStream outStream = socket.getOutputStream();              
            String filename = "2.txt";  
            File file = new File(filename);  
            //构造上传文件头，上传的时候会判断上传的文件是否存在，是否存在上传记录   
            //若是不存在则服务器会自动生成一个id,给客户端返回   
            String head = "Content-Length="+ file.length() + "; filename="+ filename + "; sourceid=1278916111468/r/n";  
            outStream.write(head.getBytes());  
              
            PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());      
            String response = StreamTool.readLine(inStream);  
            //System.out.println(response);  
            String[] items = response.split(";");  
            //构造开始上传文件的位置   
            String position = items[1].substring(items[1].indexOf("=")+1);  
            //以读的方式开始访问   
            RandomAccessFile fileOutStream = new RandomAccessFile(file, "r");  
            fileOutStream.seek(Integer.valueOf(position));  
            byte[] buffer = new byte[1024]; 
            int len = -1; 
            int i = 0; 
            while( (len = fileOutStream.read(buffer)) != -1) 
            {  
                outStream.write(buffer, 0, len); 
                i++;  
                //if(i==10) break;   
            }  
            fileOutStream.close();  
            outStream.close();  
            inStream.close();  
            socket.close();  
        }   
        catch (Exception e)   
        {                      
            e.printStackTrace();  
        }  
    }  
    /** 
    * 读取流 
    * @param inStream 
    * @return 字节数组 
    * @throws Exception 
    */  
    public static byte[] readStream(InputStream inStream) throws Exception  
    {  
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
            byte[] buffer = new byte[1024];  
            int len = -1;  
            while( (len=inStream.read(buffer)) != -1)  
            {  
                outSteam.write(buffer, 0, len);  
            }  
            outSteam.close();  
            inStream.close();  
            return outSteam.toByteArray();  
    }  
}   

