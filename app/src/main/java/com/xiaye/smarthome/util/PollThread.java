package com.xiaye.smarthome.util;

import java.util.HashMap;
import java.util.Map.Entry;

import android.os.Handler;
import android.os.Message;

public class PollThread implements Runnable {
	protected static final String TAG = "PollThread";
	private Handler handler;
	private HashMap<Integer, Long> times = new HashMap<Integer, Long>();

	 
	public PollThread(Handler handler) {
		this.handler = handler;
	}
	
	/**
	 * 如果有送给插件的数据存在，通知handler处理,仅仅发一个信息，信息0本身没有任何意义。
	 */
	@Override
	public void run() {
		
		if (!ShareData.isToPlugin() ) {
			Message msg = null;
			
			for (Entry<Integer, SingleData> data : ShareData.getPluginData().entrySet()) {
				int type = data.getKey();
				SingleData sid = data.getValue();
				
				if (!times.containsKey(type)) {  //第一次运行
					times.put(type, sid.time);
					msg = new Message();
					msg.what = data.getKey(); 			// 获取键，即类型
					msg.obj = data.getValue();			// 获取值，即byte数组
					handler.sendMessage(msg);		 // 发送数据到handler处理
				} else {
					Long time = times.get(type);
					if (sid.time > time) {
						times.put(type, sid.time);
						msg = new Message();
						msg.what = data.getKey(); 			// 获取键，即类型
						msg.obj = data.getValue();			// 获取值，即byte数组
						handler.sendMessage(msg);			// 发送数据到handler处理
					}
				}
			}
			
		} 
		
		handler.postDelayed(this, 1000);
	}
}
