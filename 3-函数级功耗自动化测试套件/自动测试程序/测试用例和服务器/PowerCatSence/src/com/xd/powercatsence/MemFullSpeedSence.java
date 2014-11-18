package com.xd.powercatsence;

import java.util.Map;
import java.util.TreeMap;

public class MemFullSpeedSence extends SenceThread {

	private String senceName = "ÄÚ´æ²Ù×÷£¨¶ÁÐ´£©";

	public static final String readWork = "read";
	public static final String writeWork = "write";

	public String workId = readWork;

	private int[] mem = new int[1024 * 1024];

	public int workRead() {
		int m_i = 0;
		int num = 10;
		for (int n = 0; n < num; n++) {
			for (int i = 0; i < mem.length; i++) {
				m_i = mem[i];
			}
		}
		return m_i;
	}

	public double workWrite() {
		int m_i = 0;
		int num = 10;
		for (int n = 0; n < num; n++) {
			for (int i = 0; i < mem.length; i++) {
				mem[i] = m_i;
			}
		}
		return m_i;
	}

	@Override
	void worker() throws InterruptedException {
		while (isRun) {
			if (workId.equals(readWork)) {
				workRead();
			}
			if (workId.equals(writeWork)) {
				workWrite();
			}
			super.callOnChange();
		}
	}

	@Override
	void config(String str) {
		Map<String, String> map = MapUtil.stringToMap(str);
		if (map != null) {
			configMap(map);
			String val = map.get("workId");
			if (val != null) {
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
		map.put("workId", "" + workId);
		String str = MapUtil.mapToString(map);
		return str;
	}

	@Override
	String getInfo() {
		return getConfig();
	}

}
