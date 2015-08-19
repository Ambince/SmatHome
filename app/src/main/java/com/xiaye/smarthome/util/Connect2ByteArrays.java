package com.xiaye.smarthome.util;

public class Connect2ByteArrays {
	public static byte[] conn2ByteArrays(byte[] data1,byte[] data2){
		byte[] data3 = new byte[data1.length+data2.length];
		System.arraycopy(data1,0,data3,0,data1.length);
		System.arraycopy(data2,0,data3,data1.length,data2.length);
		return data3;	
	}
}
