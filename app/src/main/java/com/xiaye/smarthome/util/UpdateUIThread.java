package com.xiaye.smarthome.util;

import com.xiaye.smarthome.constant.UI_Constant;
import com.xiaye.smarthome.main.SmartHomeApplication;

import android.os.Handler;
import android.os.Message;

public class UpdateUIThread extends Thread {

	private int flag;
	private Handler handler;

	public UpdateUIThread (Handler handler){
		this.handler = handler;
	}
	
	
	@Override
	public void run() {

		Message msg = new Message();
		flag = SmartHomeApplication.mUpdateUIFlag;

		if (flag == 1) {
			// 如果更新插件快捷键入口
			msg.what = 1;
			msg.obj = SmartHomeApplication.appMap
					.get(UI_Constant.PLUGIN_SHORTCUT_KEY);
			
		} else {
			//......
		}
		
		handler.sendMessage(msg);
		handler.postDelayed(UpdateUIThread.this, 3000);
	}

}
