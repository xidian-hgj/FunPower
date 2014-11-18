package com.xd.powercatsence;


import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;


public class WebVideoSence extends SenceThread implements OnPreparedListener{
	private String senceName = "������Ƶ�ۿ�";
   //���϶�ý������ݰ�������:��Ƶ���š���Ƶ���ţ�����ķֱ������ģʽ������pa��ʾ������Ƶ��
    public static final String WPlayVideo = "pv";
    private String workId = WPlayVideo;
    public String site = "192.168.253.1";
    private String  videoUrl = "http://"+site+":8080/myapp/a.mp4";
   //��Ƶ���ź���
    public void  PlayVideo()  {  
  	MediaPlayer player = new MediaPlayer();
  	player.setOnPreparedListener(this);
  //	
  //�첽��������
  	try {
  		player.setDataSource(videoUrl);
		player.prepareAsync();
	} catch (IllegalStateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  	while(isRun){
  		
  	}
  	
  	player.stop();
  	
    } 
    
    

	@Override
	void worker() throws InterruptedException {
		while(isRun){
			if (workId.equals(WPlayVideo)) {
				 PlayVideo() ;
			}
	   }
			super.callOnChange();
		
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
	@Override
	//�첽prepared����
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		mp.start();
	}
}