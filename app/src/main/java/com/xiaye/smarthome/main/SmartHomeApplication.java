package com.xiaye.smarthome.main;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.xiaye.smarthome.bean.CookMenuBean;

import java.util.HashMap;

/**
 * 
 * @ClassName: SmartHomeApplication
 * @Description: 全局数据共享区
 * @author ChenSir
 * @version 1.0
 * @date 2014-12-12 上午11:36:39
 * 
 */
public class SmartHomeApplication extends Application {

	public final static String TAG = "SmartHomeApplication";

	public static String exctCookingFlag = null;// 执行烹调流程标志

	public static HashMap<String, Object> appMap = null;

	public static int machineId = 0;

	public static int mUpdateUIFlag = 0;
	public  static CookMenuBean  menuBean;

	public static String mHTMLPath = Environment.getExternalStorageDirectory()
			+ "/smart/apk/html/";

	// 智能烹调提示信息数组
	public static byte[][] tips = null;

	public static Context mAppContext;

	@Override
	public void onCreate() {
		super.onCreate();
		exctCookingFlag = "";
		appMap = new HashMap<String, Object>();
		machineId = 0;
		menuBean = new CookMenuBean();
		mAppContext = getApplicationContext();
	}
}
