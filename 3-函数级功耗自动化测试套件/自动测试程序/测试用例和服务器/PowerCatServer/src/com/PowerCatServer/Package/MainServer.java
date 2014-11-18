package com.PowerCatServer.Package;



import org.apache.ftpserver.FtpServer;

import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.ftpserver.usermanager.impl.BaseUser;


public class MainServer {
	
	public static void main(String[] args) throws IOException {
	    //���紫��-UDP������
		 Runnable UdpServer = new Runnable(){
			 public void run(){
				 try {
					UdpServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			 }
		 };
		 Thread thread1 = new Thread(UdpServer);	 
		 thread1.start();
		 
		//�ļ��ϴ�������
		 Runnable UpLoadFileServer = new Runnable(){
			 public void run(){
				 UpLoadFileServer();
			 }
		 };
		 Thread thread2 = new Thread(UpLoadFileServer);	 
		 thread2.start();
		 
		 //�ļ����ط����������紫��-TCP����ˣ�
		 Runnable DownLoadFileServer = new Runnable(){
			 public void run(){
				 DownLoadFileServer();
			 }
		 };
		 Thread thread3 = new Thread(DownLoadFileServer);	 
		 thread3.start();
		 //������������	
		 Runnable ComServer = new Runnable(){
			 public void run(){
				 try {
					 ComServer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 };
		 Thread thread4 = new Thread(ComServer);	 
		 thread4.start();
			
	
	//���紫��-FTP�����
		 Runnable FtpServer = new Runnable(){

			@Override
			public void run() {
				
					try {
						FtpServer();
					} catch (FtpException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		 };
	 	Thread thread5 = new Thread(FtpServer);	 
	 	thread5.start();
	}
	 //��������
	 private static void ComServer() throws IOException {
		// TODO Auto-generated method stub
		//�����������˵�DatagramSocket
	    	//ѭ������UDP���������ܿͻ��˵ķ��͵����ݣ���������

	        DatagramSocket  server = new DatagramSocket(4008);
	        String sendStr = "Hello ! I'm Server";
	        for(int i=0;i<4; i++){
	        	sendStr += sendStr;
	        }
	        //����˽����ֽ�
	        while(true){
	        	byte[] recvBuf = new byte[1000];
		        DatagramPacket recvPacket 
		            = new DatagramPacket(recvBuf , recvBuf.length);
		        
		  
		        server.receive(recvPacket);
		        //���ܿͻ��˷�����������
		        String recvStr = new String(recvPacket.getData() , 0 , recvPacket.getLength());
		        //System.out.println("Hello World!" + recvStr);
		        //��ȡ�ͻ�����صĶ˿ں͵�ַ
		        int port = recvPacket.getPort();
		        InetAddress addr = recvPacket.getAddress();
		        
		        //��sendStr���ݷ��͸��ͻ���
		        byte[] sendBuf;
		        sendBuf = sendStr.getBytes();
		        DatagramPacket sendPacket 
		            = new DatagramPacket(sendBuf , sendBuf.length , addr , port );
		        
		          server.send(sendPacket); 
		        
	        
		  }
	 }
	 
	//TCP���س���
	 private static void DownLoadFileServer() {
		// TODO Auto-generated method stub
		 TcpDownloadServer.server(); 
	}


	private static void UdpServer() throws IOException {
		// TODO Auto-generated method stub
				//�����������˵�DatagramSocket
			    	//ѭ������UDP���������ܿͻ��˵ķ��͵����ݣ���������
		
			        DatagramSocket  server = new DatagramSocket(4005);
			        String sendStr = "Hello ! I'm Server";
			        for(int i=0;i<4; i++){
			        	sendStr += sendStr;
			        }
			        //����˽����ֽ�
			        while(true){
			        	byte[] recvBuf = new byte[1000];
				        DatagramPacket recvPacket 
				            = new DatagramPacket(recvBuf , recvBuf.length);
				        
				  
				        server.receive(recvPacket);
				        //���ܿͻ��˷�����������
				        String recvStr = new String(recvPacket.getData() , 0 , recvPacket.getLength());
				        //System.out.println("Hello World!" + recvStr);
				        //��ȡ�ͻ�����صĶ˿ں͵�ַ
				        int port = recvPacket.getPort();
				        InetAddress addr = recvPacket.getAddress();
				        
				        //��sendStr���ݷ��͸��ͻ���
				        byte[] sendBuf;
				        sendBuf = sendStr.getBytes();
				        DatagramPacket sendPacket 
				            = new DatagramPacket(sendBuf , sendBuf.length , addr , port );
				        //while(true){
				          server.send(sendPacket); 
				        
			        //}
				  }
	}


	public static void UpLoadFileServer() {
		// TODO Auto-generated method stub
		 ServerWindow window = new ServerWindow("�ļ��ϴ������");   
	        window.setSize(300, 300);   
	        window.setVisible(true); 
	        System.out.println("����������");
	}
	
	
	public static void FtpServer() throws FtpException{
		   
		FtpServerFactory serverFactory = new FtpServerFactory();
		
		BaseUser user = new BaseUser();
	
		user.setName("admin");
		
		user.setPassword("admin");
		
		user.setHomeDirectory("F:/test");
		
		serverFactory.getUserManager().save(user);
		
		FtpServer server = serverFactory.createServer();
		
		server.start();
		 
	}
		

}



	

