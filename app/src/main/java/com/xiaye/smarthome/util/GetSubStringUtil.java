package com.xiaye.smarthome.util;

public class GetSubStringUtil {

	public static String getNeededString(String s, int start, int end) {
		String realstring = null;
		if (s != null) {
			realstring = s.substring(start, end);
		}
		return realstring;
	}

	public static byte[] getUpfId(byte[] s) {
		byte[] realMsg = new byte[4];
		for (int i = 5, j = 0; i < s.length; i++, j++) {
			realMsg[j] = s[i];
		}
		return realMsg;

	}

}
