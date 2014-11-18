package com.xd.powercatsence;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AutomaticActivity extends Activity{
	Button button8;
	Button button9;
	EditText edit;
	EditText edit1;
	EditText edit2;
	String str;
	String str1;
	String str2;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.automatic);
	       
	        edit = (EditText) findViewById(R.id.edit);
	        edit1 = (EditText) findViewById(R.id.edittext);
	        edit2 = (EditText) findViewById(R.id.edittext1);
	        button8 = (Button) findViewById(R.id.button8);
	        button9 = (Button) findViewById(R.id.button9);

	        button8.setOnClickListener(new Button.OnClickListener(){
             
				@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    //新建一个泛型数据结构对象jsonData
				List<JsonUtil> jsonData = new ArrayList<JsonUtil>();
				str = edit.getText().toString();
				str1=edit1.getText().toString();
				boolean flag=false; 
				
				Pattern pattern=Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
				Matcher m=pattern.matcher(str);
				flag=m.matches();
				if(flag){
					LoadJsonData(jsonData);		 
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
				        		
				        	}  else if(js.getName().equals("ift")){
				        		
				        		FtpTransport(js.getTime(),js.getIp());
				        		
				        		
				        	}  else if(js.getName().equals("web")){
				        		
				        		Web(js.getTime(),js.getIp());
				        		
				        	
				        	}  else if (js.getName().equals("fup")){
				        		
				        		FileTrans(js.getTime(),js.getIp());
				        		
				        		
				        	}  else if(js.getName().equals("ipv") ){
				        		
				        		WebVideo(js.getTime(),js.getIp());
				        		
				        	}  else if(js.getName().equals("tcm")){
				        		
				        		ComMes(js.getTime(),js.getIp());
				        	
				        	}  else if(js.getName().equals("fdn") ){
				        		
				        		Tcpsence(js.getTime(),js.getIp());
				        		
				        		
				        	}
				       
				        }
					}  
						
					
					
				}else{
					Toast.makeText(AutomaticActivity.this, "请输入有效地IP地址", Toast.LENGTH_SHORT).show();
				}
				
			}
	        	
	        });
	        button9.setOnClickListener(new Button.OnClickListener(){
	        	public void onClick(View v) {
	        		List<JsonUtil> jsonData = new ArrayList<JsonUtil>();
					str2 = edit2.getText().toString();
					if(str2.equals("")){
						Toast.makeText(AutomaticActivity.this, "请输入测试文件名", Toast.LENGTH_SHORT).show();
					}else{
					//String path = Environment.getExternalStorageDirectory().getAbsolutePath() +".mp3";
						LoadJsonData1(jsonData);		 
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
					        		
					        	}  else if(js.getName().equals("ift")){
					        		
					        		FtpTransport(js.getTime(),js.getIp());
					        		
					        		Toast.makeText(AutomaticActivity.this, "aaa", Toast.LENGTH_SHORT).show(); 
					        	}  else if(js.getName().equals("web")){
					        		
					        		Web(js.getTime(),js.getIp());
					        		
					        	Toast.makeText(AutomaticActivity.this, "aaa", Toast.LENGTH_SHORT).show(); 
					        	}  else if (js.getName().equals("fup")){
					        		
					        		FileTrans(js.getTime(),js.getIp());
					        		
					        		
					        	}  else if(js.getName().equals("ipv") ){
					        		
					        		WebVideo(js.getTime(),js.getIp());
					        		
					        	}  else if(js.getName().equals("tcm")){
					        		
					        		ComMes(js.getTime(),js.getIp());
					        	
					        	}  else if(js.getName().equals("fdn") ){
					        		
					        		Tcpsence(js.getTime(),js.getIp());
					        		
					        		
					        	}
					       
					        }
						}  
							
						
						
					
		        	
	        	}	
	        	}
	        });
	        
	        
	 }



//下面实现的是具体的操作
//密集计算-pi值
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
	  
	SurfaceView sView;  //添加一个surfaceview
	sView=(SurfaceView)findViewById(R.id.dview);
    sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    sView.getHolder().setFixedSize(320, 280);
    sView.getHolder().setKeepScreenOn(true);
    
	VideoSence vi = new VideoSence();
	vi.setWorkTime(d);
	vi.setSurfView(sView.getHolder().getSurface());
	
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
//TCP下载	
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
//Http传输
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
			
			String urlPath = null;
			// TODO Auto-generated method stub		
			try {
				urlPath = "http://"+str+":8080/myapp/"+URLEncoder.encode(str1+".json", "UTF-8");
				
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			byte[] data = null;
			try {
				data = readJson(urlPath);

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
  private void LoadJsonData1(final List<JsonUtil> arrayList) {
	// TODO Auto-generated method stub	
	Thread t = new Thread(new Runnable() {
		@Override
		public void run()  {
			// TODO Auto-generated method stub
			//读取本地的.json 文件
			  
	       byte[] data = null;
	       String dirname=Environment.getExternalStorageDirectory().getAbsolutePath();
	       String myfile="/"+str2+".json";
	       File afile= new File(dirname,myfile);
	       try {
			InputStream r= new FileInputStream(afile);
			  ByteArrayOutputStream byteout=new ByteArrayOutputStream();
		       byte temp[]=new byte[1024];
		       int i=0;
		      
			while((i=r.read(temp))!=-1){
				
				byteout.write(temp);
		    	   
		       }
			data=byteout.toByteArray();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
  }
}

