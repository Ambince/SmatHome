package com.jni.info;

/**
 * 
 * @ClassName: Flag
 * @Description: 封装判断是否收到JNI消息的标志
 * @author Android组-ChenSir
 * @version 1.0
 * @date 2014-11-25 下午9:23:37
 * 
 */

public class Flag {

	static int flag = 0; // 0代表未收到，1代表收到

	int type = 0; // 总控传来的消息类型

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		Flag.flag = flag;
	}

	public void setType(int type) {
		this.type = type;

	}
}
