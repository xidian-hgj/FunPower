package com.xd.powercatsence;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.TreeMap;
public class UdpDownSence extends SenceThread{

	private String senceName = "���紫��-UDP";
	//���÷�������ip�Ͷ˿�
	String site = "172.25.159.8";//������ip
	int sendBf_Max = 1024 * 128;
	int port = 4005;
    public static final String udpdown = "ud";
    
    private String workId =udpdown; 
   
     public void workPi() throws IOException {  
    	 for(int i=0;i<10;i++){
    	//�½�DatagramSocket���󣬿ͻ���
 		DatagramSocket client = new DatagramSocket();
         //��Ҫ���͵����� sendStr
         String sendStr = "Hello! I'm Client";
         //��Ҫ��������ݷֽ�Ϊ�ֽ�
         byte[] sendBuf;
        
         sendBuf = sendStr.getBytes();
         //����һ�� InetAddress��ַ
         InetAddress addr = InetAddress.getByName(site);//����ip
        
         
         //����һ��DatagramPacket����ע�����͵����ݣ���С�͵�ַ�Լ��˿ںţ������з��� client.send()
         DatagramPacket sendPacket 
             = new DatagramPacket(sendBuf ,sendBuf.length , addr , port);
         client.send(sendPacket);
         
         //�����ֽ�����
         byte[] recvBuf = new byte[10000];
         DatagramPacket recvPacket
             = new DatagramPacket(recvBuf , recvBuf.length);
         //ѭ������UDP��������������Ϣ
         //while(isRun)
        // {
 	        client.receive(recvPacket);
         //}
          //�����̽���ʱ���� 
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

