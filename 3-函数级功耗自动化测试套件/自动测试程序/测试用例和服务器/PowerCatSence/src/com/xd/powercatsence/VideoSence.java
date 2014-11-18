package com.xd.powercatsence;


import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.Surface;
//CpuFullSpeedSence������Sence Thread�����
public class VideoSence extends SenceThread {

	private String senceName = "��ý����Ƶ��Ƶ";
   //��ý������ݰ����ĸ�:��Ƶ�ɼ�����Ƶ���š���Ƶ�ɼ�����Ƶ���ţ�����ķֱ������ģʽ������pa��ʾ������Ƶ��
    public static final String PlayAudio = "pa";
    public static final String CaptureAudio = "ca";
    public static final String PlayVideo = "pv";
    public static final String CaptureVideo = "cv";
    public String workId = CaptureVideo;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mPlayer;

//����Surface��Ϊ�˽�����Ƶ�ɼ�
	private Surface sf;


//    ���ڶ�ý����ĸ���������������Ƶ�ɼ��벥�ţ�
//   ������Ƶ�ĺ���
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
     	//����һ��ѭ�����ŵĹ���
     	mPlayer.setLooping(true);
     	//����һ����ѭ����������1ʱ����һֱ���в��š�
    	while(isRun){
    		
    	}
    	mPlayer.stop();
     	
    }  
    
//  ��Ƶ�ɼ�����
    public void CaptureAudio() { 
    	
   
       mediaRecorder = new MediaRecorder();  
       
    	mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //�ɼ���Ƶ 

    	mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //�����ʽ3GP  
    	
    	mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//������Ƶ���뷽ʽ  
    	
    	String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/love.mp3";
    	
    	mediaRecorder.setOutputFile(path);  //�洢·��
    	 try { 

             // ׼��¼�� 

             mediaRecorder.prepare();

             // ��ʼ¼����

             mediaRecorder.start(); //��Ƶ�ɼ���ʼ
            
            	while(isRun){
            		
            	}
            	mediaRecorder.stop();//��Ƶ�ɼ�����
       
         } catch (IllegalStateException e) { 

             // TODO Auto-generated catch block 

             e.printStackTrace(); 

         } catch (IOException e) { 

             // TODO Auto-generated catch block 

             e.printStackTrace(); 

         } 

    } 
   //��Ƶ���ź���
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
		}      //���ò��ŵ������ļ�  
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
     	mPlayer.setLooping(true);//��Ƶ��ѭ������
        while(isRun){
    		
    	}
    	mPlayer.stop();
     	
    } 
   //��Ƶ�ɼ�����
    public void CaptureVideo()  {  
  
    	
       String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/love12.mp4";
    	
       mediaRecorder = new MediaRecorder(); 
      
       //��Ƶ�ɼ���ʽ���趨һ��Ҫ���úã�ע��˳��
       mediaRecorder.setAudioSource(MediaRecorder.AudioSource .MIC);//�������������ʽ

    	mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA); //��������вɼ���Ƶ  
    
    	mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); //�����ʽ3GP  
    	
    	mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//�������ֱ��뷽ʽ	
    	
    	mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);//������Ƶ���뷽ʽ 
    	
    	mediaRecorder.setOutputFile(path);  

    	mediaRecorder.setVideoFrameRate(10);  //¼��֡����ÿ��20֡  
    	
    	mediaRecorder.setVideoSize(320, 240);
    	
    	mediaRecorder.setPreviewDisplay(sf);

    	
  
       
     	 try { 

             // ׼��¼�� 
     		
             mediaRecorder.prepare(); 
            
           
             mediaRecorder.start(); 
//            ��һ����ʱ��֤����п���˳���Ľ���¼��
             while(isRun==true){
            	 
             }
             //�ɼ���Ҫ���н������ͷ�
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
   //suface �ĵ��ýӿں���
    public void setSurfView(Surface sf){
    	this.sf = sf;
    }
   //ѡ��ͬ�ĺ������������ĸ��������в�����
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
