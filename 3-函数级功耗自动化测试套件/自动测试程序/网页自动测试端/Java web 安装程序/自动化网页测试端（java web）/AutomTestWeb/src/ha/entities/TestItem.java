package ha.entities;
/**
 * 前台一条数据对应的类
 * @author dzp
 *
 */
public class TestItem {
	private String name;
	private String time;
	private String ip;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Override
	public String toString(){
		return "{\"name\":\""+name+"\",\"time\":\""+time+"\",\"ip\":\""+ip+"\"}";
	}
	
}
