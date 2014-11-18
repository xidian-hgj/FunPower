package com.xd.powercatsence;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.TreeMap;
public class UdpDownSence extends SenceThread{

	private String senceName = "网络传输-UDP";
	//设置服务器的ip和端口
	String site = "172.25.159.8";//服务器ip
	int sendBf_Max = 1024 * 128;
	int port = 4005;
    public static final String udpdown = "ud";
    
    private String workId =udpdown; 
   
     public void workPi() throws IOException {  
    	 for(int i=0;i<10;i++){
    	//新建DatagramSocket对象，客户端
 		DatagramSocket client = new DatagramSocket();
         //需要发送的数据 sendStr
         String sendStr = "Hello! I'm Client";
         //将要传输的内容分解为字节
         byte[] sendBuf;
        
         sendBuf = sendStr.getBytes();
         //创建一个 InetAddress网址
         InetAddress addr = InetAddress.getByName(site);//主机ip
        
         
         //创建一个DatagramPacket对象，注明发送的内容，大小和地址以及端口号，并进行发送 client.send()
         DatagramPacket sendPacket 
             = new DatagramPacket(sendBuf ,sendBuf.length , addr , port);
         client.send(sendPacket);
         
         //接受字节数据
         byte[] recvBuf = new byte[10000];
         DatagramPacket recvPacket
             = new DatagramPacket(recvBuf , recvBuf.length);
         //循环接受UDP服务器发出的信息
         //while(isRun)
        // {
 	        client.receive(recvPacket);
         //}
          //当进程结束时结束 
         client.close();}
    }  
      


   
    
    
    
	@Override
	void worker() throws InterruptedException, IOException {
		while(isRun){
			if (workId.equals(udpdown)) {
				workPi();
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

