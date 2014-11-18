package com.PowerCatServer.Package;
//ͬ��������ͬ�İ����䶼��������е��࣬����Ϊpubic��ʱ��ᱻ�����ĳ������
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
��TCP�����ļ�����
���ļ�Ϊ�������ļ�
�����ܵ��ͻ��˵�����֮�������䴫���ļ���
���ͻ��˽������֮����ͻ��˴���
* */
public class TcpDownloadServer implements Runnable{
	//�����������˿�

	private static final int MONITORPORT = 12345; 

	private Socket socketServer;

	public TcpDownloadServer(Socket s) { 
		super();  
		this.socketServer = s; 

	}  
 
	public void run() {  
		// TODO Auto-generated method stub

		byte[] buf = new byte[100]; 
		OutputStream os = null;  
		FileInputStream fins = null; 
		try { 
			os = socketServer.getOutputStream();
			// �ļ�·��
			String filepath = "c:/test/1.txt";
			String filename = "1.txt";
			//���ļ������ݴ����ͻ���
			os.write(filename .getBytes()); 
			//���ļ������ȥ
			// ��ȡ�ļ�
			fins = new FileInputStream(filepath); 
			// System.out.println(fins);
			int data;  
			//ͨ��fins��ȡ�ļ�����ͨos���ļ�����
			while(-1!=(data = fins.read())) 
			{  
				os.write(data); 
			}
		} catch(IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{ 
				os.close(); 
				fins.close();  
				this.socketServer.close(); 
			}   catch(IOException e) { 
				e.printStackTrace();  
			} 

		} 
	}
	public static void server() {
		// TODO Auto-generated method stub
		try{  
			// ����������socket

			ServerSocket serverSocket = new ServerSocket(MONITORPORT);
		    
		     while(true) {  
		    	 // ���յ�һ���ͻ�������֮�󣬴���һ���µ��߳̽��з���
		    	 // ������ͨ��socket�������߳�
		    	 Socket s = serverSocket.accept();  
		    	 new Thread(new TcpDownloadServer(s)).start();
		     	}  
		     } catch(IOException e) {  

		    	 // TODO Auto-generated catch block
		    	 e.printStackTrace(); 

		     } 
		}  

	}  
