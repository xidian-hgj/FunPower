package ha.action;

import ha.entities.TestItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import net.sf.json.JSONArray;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 后台逻辑处理类
 * @author dzp
 *
 */
public class DataProcessAction extends ActionSupport{
	private ArrayList<TestItem> test;
	private String fileName;
	/**
	 * 存储数据8
	 * @return
	 */
	public String storeData(){
		String nowpath;
		String tempdir;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			for(TestItem ti:test){
				if(ti != null){
					sb.append(ti.toString()+",");
				}
			}
			String dataJson = sb.substring(0, sb.length() - 1)+"]";
			nowpath = System.getProperty("user.dir");
			tempdir = nowpath.replace("bin", "webapps");
			tempdir +="\\myapp\\"; 
			File savefile=new File(tempdir+fileName+".json");
			FileWriter outstream=new FileWriter(savefile);
			outstream.write(dataJson);
			outstream.flush();
			outstream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	
	public ArrayList<TestItem> getTest() {
		return test;
	}

	public void setTest(ArrayList<TestItem> test) {
		this.test = test;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
