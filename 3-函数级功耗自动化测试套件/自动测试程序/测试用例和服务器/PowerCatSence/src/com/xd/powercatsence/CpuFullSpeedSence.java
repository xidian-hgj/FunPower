package com.xd.powercatsence;


import java.util.Map;
import java.util.TreeMap;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.content.Context;





public class CpuFullSpeedSence extends SenceThread{

	private String senceName = "密集计算";
	private Context context;
	
	
	public static final int INT_C = 2000000;  
    public static final String image = "image";
    public static final String pi = "pi";
    
    public String workId = pi;
    
    public CpuFullSpeedSence(Context context){
    	this.context = context;
    }
     
    public double workPi() {  
    	long n;   
		double pi;   
		double sum=0;   
		double t;    
		for(n=1;n<INT_C;n++){    
		    t=1.0/(n*n);   //使用级数计算pi值    pi^2=1/6(1+1/2^2+1/3^2...)
			sum+=t;    
		}    
		pi=Math.sqrt(6.0*sum); //使用级数计算pi值    pi^2=1/6(1+1/2^2+1/3^2...)
		return pi;
    }  
      
    public void workImage() {  
    	setupViews();

    	} 

    private void setupViews() {
    	
    	Drawable drawable = context.getResources().getDrawable(R.drawable.image);//得到手机的桌面图片
    	Bitmap bitmap = ImageUtil.drawableToBitmap(drawable);//将Drawable格式转化为bmp
    	Bitmap zoomBitmap = ImageUtil.zoomBitmap(bitmap, 300, 300);// 缩放图片
    	Bitmap roundBitmap = ImageUtil
    	.getRoundedCornerBitmap(zoomBitmap, 10.0f);// 获取圆角图片
    	Bitmap reflectBitmap = ImageUtil.createReflectionImageWithOrigin(roundBitmap);// 获取倒影图片
    	
    	}
    
    
    
	@Override
	void worker() throws InterruptedException {
		while(isRun){
			if (workId.equals(pi)) {
				workPi();
			}
			if (workId.equals(image)) {
				workImage();
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
