package com.xd.powercatsence;

import java.util.Map;
import java.util.TreeMap;

public class MapUtil {
	public static Map<String, String> stringToMap(String str) {
		String [] pairs = str.split(",");
		if (pairs==null || pairs.length==0) {
			return null;
		}
		
		Map<String, String> map = new TreeMap<String, String>();
		for (String pairStr : pairs) {
			pairStr = pairStr.trim();
			String [] pair = pairStr.split("=");
			if (pair.length!=2) {
				return null;
			}
			map.put(pair[0], pair[1]);
		}
		return map;
	}
	
	public static String mapToString(Map<String, String> map) {
		if (map==null) {
			return "";
		}
		String str = map.toString();
		return str.substring(1, str.length()-1);
	}
}
