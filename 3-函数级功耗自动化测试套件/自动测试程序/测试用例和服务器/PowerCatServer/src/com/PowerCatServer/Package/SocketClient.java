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
            //������׽��ָ���ʵ�ʷ���������   
            Socket socket = new Socket(site, 7878);  
            OutputStream outStream = socket.getOutputStream();              
            String filename = "2.txt";  
            File file = new File(filename);  
            //�����ϴ��ļ�ͷ���ϴ���ʱ����ж��ϴ����ļ��Ƿ���ڣ��Ƿ�����ϴ���¼   
            //���ǲ���������������Զ�����һ��id,���ͻ��˷���   
            String head = "Content-Length="+ file.length() + "; filename="+ filename + "; sourceid=1278916111468/r/n";  
            outStream.write(head.getBytes());  
              
            PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());      
            String response = StreamTool.readLine(inStream);  
            //System.out.println(response);  
            String[] items = response.split(";");  
            //���쿪ʼ�ϴ��ļ���λ��   
            String position = items[1].substring(items[1].indexOf("=")+1);  
            //�Զ��ķ�ʽ��ʼ����   
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
    * ��ȡ�� 
    * @param inStream 
    * @return �ֽ����� 
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

