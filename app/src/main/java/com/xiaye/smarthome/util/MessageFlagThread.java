package com.xiaye.smarthome.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MessageFlagThread implements Runnable {
	@SuppressWarnings("unused")
	private final String TAG = "MessageFlagThread";
	Handler handler;

	public MessageFlagThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		Message msg = null;
		if (!ShareData.isFromPlugin()) {
			msg = handler.obtainMessage();
			int pluginFlag = ShareData.getFromPluginType();
			Log.e(TAG, "type==="+pluginFlag);
			switch (pluginFlag) {
			case 1:
				// 查询和控制信息
				msg.what = 1;
				msg.arg1 = ShareData.getFromPluginLen();// len
				msg.arg2 = ShareData.getFromPluginType();// type
				msg.obj = ShareData.getFromPluginData();// value
				break;

			case 2:
				// 插件输出设备条形码
				msg.what = 2;
				break;

			case 5:
				// 请求状态信息界面部分数据
				msg.what = 5;
				msg.obj = ShareData.getFromPluginData();
				break;

			default:
				break;
			}
			handler.sendMessage(msg);
		} else {
			// do nothing.
		}
		handler.postDelayed(MessageFlagThread.this, 300);
	}
}
