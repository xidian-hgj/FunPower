package com.xd.powercatsence;


import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.Surface;
//CpuFullSpeedSence集成自Sence Thread这个类
public class VideoSence extends SenceThread {

	private String senceName = "多媒体视频音频";
   //多媒体的内容包括四个:视频采集、视频播放、音频采集、音频播放，下面的分别代表其模式（例：pa表示播放音频）
    public static final String PlayAudio = "pa";
    public static final String CaptureAudio = "ca";
    public static final String PlayVideo = "pv";
    public static final String CaptureVideo = "cv";
    public String workId = CaptureVideo;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mPlayer;

//设置Surface，为了进行视频采集
	private Surface sf;


//    关于多媒体的四个操作函数（音视频采集与播放）
//   播放音频的函数
    public void PlayAudio()  {  
    	mPlayer = new MediaPlayer();  
    	String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/love.mp3";
        try {
			mPlayer.setDataSource(path);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	    try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
     	mPlayer.start();
     	//加上一个循环播放的功能
     	mPlayer.setLooping(true);
     	//加上一个死循环，当其是1时，则一直进行播放。
    	while(isRun){
    		
    	}
    	mPlayer.stop();
     	
    }  
    
//  音频采集函数
    public void CaptureAudio() { 
    	
   
       mediaRecorder = new MediaRecorder();  
       
    	mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //采集音频 

    	mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //输出格式3GP  
    	
    	mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音频编码方式  
    	
    	String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/love.mp3";
    	
    	mediaRecorder.setOutputFile(path);  //存储路径
    	 try { 

             // 准备录音 

             mediaRecorder.prepare();

             // 开始录制音

             mediaRecorder.start(); //音频采集开始
            
            	while(isRun){
            		
            	}
            	mediaRecorder.stop();//音频采集结束
       
         } catch (IllegalStateException e) { 

             // TODO Auto-generated catch block 

             e.printStackTrace(); 

         } catch (IOException e) { 

             // TODO Auto-generated catch block 

             e.printStackTrace(); 

         } 

    } 
   //视频播放函数
    public void  PlayVideo()  {  
    	mPlayer = new MediaPlayer();  
    	mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/love12.mp4";
        try {
			mPlayer.setDataSource(path);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      //设置播放的声音文件  
	    try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
     	mPlayer.start(); 
     	mPlayer.setLooping(true);//视频的循环播放
        while(isRun){
    		
    	}
    	mPlayer.stop();
     	
    } 
   //视频采集函数
    public void CaptureVideo()  {  
  
    	
       String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/love12.mp4";
    	
       mediaRecorder = new MediaRecorder(); 
      
       //视频采集格式的设定一定要配置好，注意顺序
       mediaRecorder.setAudioSource(MediaRecorder.AudioSource .MIC);//设置音乐输出格式

    	mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); //从照相机中采集视频  
    
    	mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); //输出格式3GP  
    	
    	mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音乐编码方式	
    	
    	mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);//设置视频编码方式 
    	
    	mediaRecorder.setOutputFile(path);  

    	mediaRecorder.setVideoFrameRate(10);  //录制帧数，每秒20帧  
    	
    	mediaRecorder.setVideoSize(320, 240);
    	
    	mediaRecorder.setPreviewDisplay(sf);

    	
  
       
     	 try { 

             // 准备录制 
     		
             mediaRecorder.prepare(); 
            
           
             mediaRecorder.start(); 
//            加一个延时保证其进行可以顺利的进行录像
             while(isRun==true){
            	 
             }
             //采集完要进行结束和释放
             mediaRecorder.stop();
             mediaRecorder.release();
             mediaRecorder = null;
           
         } catch (IllegalStateException e) { 

             // TODO Auto-generated catch block 

             e.printStackTrace(); 

         } catch (IOException e) { 

             // TODO Auto-generated catch block 

             e.printStackTrace(); 

         } 
     	 
  } 
   //suface 的调用接口函数
    public void setSurfView(Surface sf){
    	this.sf = sf;
    }
   //选择不同的函数操作（对四个函数进行操作）
	@Override
	void worker() throws InterruptedException {
		while(isRun){
			if (workId.equals(PlayAudio)) {
				 PlayAudio() ;
			}
			if (workId.equals(CaptureAudio)) {
				 CaptureAudio();
			}
			if (workId.equals(PlayVideo)) {
				 PlayVideo() ;
			}
			if (workId.equals(CaptureVideo)) {
				 CaptureVideo();
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
//
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
