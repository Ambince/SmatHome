package com.xiaye.smarthome.util;

import java.io.Serializable;

/**
 * 
 * @author ChenSir
 * @description 共享数据单位
 *
 */

public class SingleData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public long time = 0;//时间戳
	public int Type = 0;// 消息类型
	public int Len = 0;// 数据长度
	public byte[] data = {};// 数据
	public SingleData(int type, int len, byte[] data,long time) {
		this.Type = type;
		this.Len = len;
		this.data = data;
		this.time = time;
	}
}