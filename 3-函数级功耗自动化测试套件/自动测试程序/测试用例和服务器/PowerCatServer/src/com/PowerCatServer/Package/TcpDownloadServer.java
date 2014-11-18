package com.PowerCatServer.Package;
//同是来自相同的包，其都是这个包中的类，声明为pubic的时候会被其他的程序调用
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
用TCP进行文件传输
此文件为服务器文件
当接受到客户端的请求之后，先向其传输文件名
当客户端接受完毕之后，向客户端传输
* */
public class TcpDownloadServer implements Runnable{
	//服务器监听端口

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
			// 文件路径
			String filepath = "c:/test/1.txt";
			String filename = "1.txt";
			//将文件名数据传给客户端
			os.write(filename .getBytes()); 
			//将文件传输过去
			// 获取文件
			fins = new FileInputStream(filepath); 
			// System.out.println(fins);
			int data;  
			//通过fins读取文件，并通os将文件传输
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
			// 创建服务器socket

			ServerSocket serverSocket = new ServerSocket(MONITORPORT);
		    
		     while(true) {  
		    	 // 接收到一个客户端连接之后，创建一个新的线程进行服务
		    	 // 并将联通的socket传给该线程
		    	 Socket s = serverSocket.accept();  
		    	 new Thread(new TcpDownloadServer(s)).start();
		     	}  
		     } catch(IOException e) {  

		    	 // TODO Auto-generated catch block
		    	 e.printStackTrace(); 

		     } 
		}  

	}  
