package com.jni.info;

import com.xiaye.smarthome.main.MainActivity.JNIHandler;

import android.os.Message;


public class FlagThread implements Runnable {

	public static final String TAG = "FlagThread";
	JNIHandler handler;
	Flag fl;

	public FlagThread(Flag fl, JNIHandler handler) {
		this.fl = fl;
		this.handler = handler;
	}

	@Override
	public void run() {
		Message msg = new Message();
		if (Flag.flag == 1) {// 如果收到消息。
			msg.what = 1;
			msg.obj = fl.type;
			fl.setFlag(0);

		} else if (Flag.flag == 0) {
			msg.what = 0;
		}
		handler.sendMessage(msg);
		handler.postDelayed(FlagThread.this, 100);
	}
}
