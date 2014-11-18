package com.xd.powercatsence;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) public class MainActivity extends Activity {


	
    protected static final String TAG = "MainActivity";

    CpuFullSpeedSence cpuFullWork;
    MemFullSpeedSence memFullWrok;
    FileIOSence fileioWork;
    Button button;
    
    
    SenceView cpuFullSence;
    SenceView memFullSence;
    SenceView FileIOSence;
    //创建一个列表的类。用来存储json的数据
    List<JsonUtil> jsonData = new ArrayList<JsonUtil>();
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cpuFullSence = (SenceView) findViewById(R.id.cpufull_sence);
        cpuFullWork = new CpuFullSpeedSence(this);
        cpuFullSence.bindSenceThread(cpuFullWork);
        
        memFullSence = (SenceView) findViewById(R.id.memfull_sence);
        memFullWrok = new MemFullSpeedSence();
        memFullSence.bindSenceThread(memFullWrok);
        
        FileIOSence = (SenceView) findViewById(R.id.fileio_sence);
        fileioWork = new FileIOSence();
        FileIOSence.bindSenceThread(fileioWork);
        
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this,SecondActivity.class);
				startActivity(intent);
				MainActivity.this.finish();
				
			}
        	
        });
        
     /*   //加载Json数据，到其列表的内容
        LoadJsonData(jsonData);
//     判断是否为空，以此来判断是否进行相关操作 （注意写完了其他的将别的加载进来）
       
        	
      
        
//   通过进行名字的判断，选择进行那个来操作，一般有连个参数一个是时间，还有一个是ip地址
	if(jsonData.size() != 0){ 
	  for(JsonUtil js:jsonData){
        	if(js.getName().equals("msp")){
        		
        		CpuFullSpeed(js.getTime(),js.getIp());
        		
        		
        	} else if(js.getName().equals("msh") ){
        		
        		CpuFullSpeed1(js.getTime(),js.getIp());	
        		
        	} else if(js.getName().equals("fir")){
        		
        		 FileIO(js.getTime(),js.getIp());
        		 
        	} else if(js.getName().equals("fiw")){
        		
        		FileIO1(js.getTime(),js.getIp());
        	} else if (js.getName().equals("msr")){
        		
        		MemFullSpeed(js.getTime(),js.getIp());
        	} else if (js.getName().equals("msw") ){
        		
        		MemFullSpeed1(js.getTime(),js.getIp());
        	} else if (js.getName().equals("dia")){
        		
        		DialerMes(js.getTime(),js.getIp());
        	} else if (js.getName().equals("mes") ){
        		
        		DialerMes1(js.getTime(),js.getIp());
        	} else if (js.getName().equals("vcv")){
        		
        		Video(js.getTime(),js.getIp());
        	}  else if (js.getName().equals("vpa")){
        		
        		Video1(js.getTime(),js.getIp());
        	}  else if (js.getName().equals("vca") ){
        		
        		Video2(js.getTime(),js.getIp());
        	}  else if (js.getName().equals("vpv")){
        		
        		Video3(js.getTime(),js.getIp());
        	}  else if(js.getName().equals("itd")){
        		
        		TcpDown(js.getTime(),js.getIp());
        	}  else if(js.getName().equals("iht")){
        		
        		HttpTransport(js.getTime(),js.getIp());
        	}  else if(js.getName().equals("iud")){
        		
        		UdpDown(js.getTime(),js.getIp());
        	}  else if(js.getName().equals("web")){
        		
        		Web(js.getTime(),js.getIp());
        	}  else if (js.getName().equals("fup")){
        		
        		FileTrans(js.getTime(),js.getIp());
        	}  else if(js.getName().equals("ipv") ){
        		
        		WebVideo(js.getTime(),js.getIp());
        	}  else if(js.getName().equals("icm")){
        		
        		ComMes(js.getTime(),js.getIp());
        	}  else if(js.getName().equals("fdn") ){
        		Tcpsence(js.getTime(),js.getIp());
        	}
       
        }
	}  
   }
//	下面实现的是具体的操作
//  密集计算-pi值
	public void CpuFullSpeed(long d,String c){
		
		CpuFullSpeedSence cpu = new CpuFullSpeedSence(this);
		cpu.setWorkTime(d);
		cpu.workId = cpu.pi;
		try {
			cpu.start();
			cpu.thread.join();
			
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		Log.i("hbnbv","第一个可以执行了");
	}
	//密集计算-图像混合
	public void CpuFullSpeed1(long d,String c){
		
		CpuFullSpeedSence cpu = new CpuFullSpeedSence(this);
		cpu.setWorkTime(d);
		cpu.workId = cpu.image;
		try {
			cpu.start();
			cpu.thread.join();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		Log.i("hbnbv","第二个可以执行了");
	}
	// 文件读	
		public void FileIO(long d,String c){
			
			FileIOSence file = new FileIOSence();
		    file.setWorkTime(d);
		    file.workId = file.readWork;
		    try {
		    	file.start();
		    	file.thread.join();
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    Log.i("hbnbv","第4个可以执行了");
		}
	//文件写
		public void FileIO1(long d,String c){
			
			FileIOSence file = new FileIOSence();
		    file.setWorkTime(d);
		    file.workId = file.writeWork;
		    try {
		    	file.start();
		    	file.thread.join();
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
//内存读操作	
	public void MemFullSpeed(long d,String c){

		MemFullSpeedSence mem = new MemFullSpeedSence();
	    mem.setWorkTime(d);
	    try {
	    	mem.start();
	    	mem.thread.join();
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
//内存写操作
	public void MemFullSpeed1(long d,String c){

		MemFullSpeedSence mem = new MemFullSpeedSence();
	    mem.setWorkTime(d);
	    mem.workId = mem.writeWork;
	    try {
	    	mem.start();
	    	mem.thread.join();
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	//语音通话	
	public void DialerMes(long d,String c){
		
		DialerMesSence dm = new DialerMesSence(this);
		dm.setWorkTime(d);
		try {
			dm.start();
		    dm.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//短信收发
	public void DialerMes1(long d,String c){
	
		DialerMesSence dm = new DialerMesSence(this);
		dm.setWorkTime(d);
		dm.workId = dm.mes;
		try {
			dm.start();
		    dm.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//视频采集
	public void Video(long d,String c){
	
		VideoSence vi = new VideoSence();
		vi.setWorkTime(d);
		try {
			vi.start();
		    vi.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//音频播放
	public void Video1(long d,String c){
		
		VideoSence vi = new VideoSence();
		vi.setWorkTime(d);
		vi.workId = vi.PlayAudio;
		try {
			vi.start();
		
		    vi.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//音频采集
	public void Video2(long d,String c){
		
		VideoSence vi = new VideoSence();
		vi.setWorkTime(d);
		vi.workId = vi.CaptureAudio;
		try {
			vi.start();
		    vi.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("hbnbv","第三个可以执行了");
	}
	//视频播放
	public void Video3(long d,String c){

		VideoSence vi = new VideoSence();
		vi.setWorkTime(d);
		vi.workId = vi.PlayVideo;
		try {
			vi.start();
		    vi.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//  TCP下载	
	public void TcpDown(long d,String c){
	
		TcpDownSence tcp = new TcpDownSence();
		
		tcp.setWorkTime(d);
		tcp.site = c;
		
		try {
			tcp.start();
			tcp.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//ftp传输	
	public void FtpTransport(long d,String c){

		FtpTransportSence ftp = new FtpTransportSence();
		ftp.setWorkTime(d);
		ftp. site =c;
		try {
			ftp.start();
			ftp.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
// Http传输
	public void HttpTransport(long d,String c){

		HttpTransportSence http = new HttpTransportSence();
		http.setWorkTime(d);
		http.site = c;
		try {
			http.start();
			http.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//udp下载	
	public void UdpDown(long d,String c){
	
		UdpDownSence udp = new UdpDownSence();
		udp.setWorkTime(d);
		udp.site = c;
		try {
			udp.start();
			udp.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Web浏览
	public void Web(long d,String c){

		WebSence web = new WebSence();
		web.setWorkTime(d);
		web.site = c;
		try {
			web.start();
			web.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//文件上传
	public void FileTrans(long d,String c){
		
		FileTraSence filetrans = new FileTraSence(this);
		filetrans.setWorkTime(d);
		filetrans.site = c;
		try {
			filetrans.start();
			filetrans.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//在线观看视频
	public void WebVideo(long d,String c){

		WebVideoSence webv = new WebVideoSence();
		webv.setWorkTime(d);
		webv.site = c;
		try {
			webv.start();
			webv.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//文字聊天
	public void ComMes(long d, String c){

		ComMesSence commes = new ComMesSence();
		commes.setWorkTime(d);
		commes.site = c;
		try {
			commes.start();
			commes.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//文件下载
	public void Tcpsence(long d,String c){
	
		TcpSence tcp = new TcpSence();
		tcp.setWorkTime(d);
		tcp.site = c;
		try {
			tcp.start();
			tcp.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//导入json数据，包括服务端json数据的读取和json数据的解析
	private void LoadJsonData(final List<JsonUtil> arrayList) {
		// TODO Auto-generated method stub

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String urlPath = "http://219.245.72.18:8080/myapp/testjson.json";
				byte[] data = null;
				try {
					data = readJson(urlPath);//服务端数据的读取
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
				try {
					//建立jsonarray数组对象，用来存储字符流
					JSONArray array = new JSONArray(new String(data));
					//解析字符串流，提取相对应的内容（循环提取每一条的内容）
					for (int i = 0; i < array.length(); i++) {
						JSONObject item = array.getJSONObject(i);//设置一个对象
						JsonUtil js = new JsonUtil();//帮助类来帮助存储json 数据
						//分别从里面提取相应的参数
						js.setName(item.getString("name"));
						js.setTime(Long.parseLong(item.getString("time")));
						js.setIp(item.getString("ip"));
						//将数据添加到列表中（帮助类建立的列表）将数据依次循环放入帮助类列表
						arrayList.add(js);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	//服务端读取json数据的内容（生成字符串）
	private byte[] readJson(String urlPath) throws MalformedURLException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		URL url = new URL(urlPath);
		try {
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			InputStream is = conn.getInputStream();
			while((len = is.read(data)) != -1){
				baos.write(data, 0, len);
			}
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toByteArray();
	}*/
	}
}
