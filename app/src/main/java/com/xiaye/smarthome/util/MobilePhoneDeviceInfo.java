package com.xiaye.smarthome.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class MobilePhoneDeviceInfo {
	public static boolean isInit = false;
	public static float density = 0;
	public static int densityDpi = 0;
	protected static String TAG = "MobilePhoneDeviceInfo";
	public static float width = 0;
	public static float height = 0;
	
	
	public static void initDeviceInfo(Context context, Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		density = dm.density;
		densityDpi = dm.densityDpi;
		isInit = true;
		width = dm.widthPixels;
		height = dm.heightPixels;
	}
}
