package com.xiaye.smarthome.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.xiaye.smarthome.main.SmartHomeApplication;

import android.util.Log;

/**
 * @description 主程序和插件共享数据结构，包含两个链表和若干访问接口函数
 * @author ChenSir
 * @date 2014.9.21
 * 
 */
public class ShareData {

	public static HashMap<Integer, SingleData> pluginMap = new HashMap<Integer, SingleData>();
	public static List<SingleData> toMainList = new ArrayList<SingleData>();

	public static int clearFlag = 1;

	/**
	 * @return 两个链表是否为空
	 */
	public static boolean isToPlugin() {
		return pluginMap.isEmpty();
	}

	// 设置发送给插件的信息
	public static void SetdataToPlugin(int type, int len, byte[] data, long time) {
		SingleData toPlugdata = new SingleData(type, len, data, time);
		pluginMap.put(type, toPlugdata);
	}

	public static void ClearDataFromPlugin(int type) {
		pluginMap.remove(type);
	}

	// 发送给插件的消息（智能烹调界面提示信息）（二维数组）
	public static void SetTipsToPlugin(int type, int len, byte[][] data,
			long time) {
		Log.i("SetTipsToPlugin", "type=" + type);
		SmartHomeApplication.tips = data;
	}

	/**
	 * 
	 * @Description: 获取插件
	 * @author ChengBin
	 * @version 1.0
	 * @date 2014-10-20 下午11:03:31
	 * @return
	 */
	public static HashMap<Integer, SingleData> getPluginData() {
		return pluginMap;
	}

	public static void clearPluginData() {
		pluginMap.clear();
	}

	// 设置发送给主界面的信息
	public static void SetdataFromPlugin(SingleData data) {
		Log.e("Share", "Type========================="+data.Type+"");
		toMainList.add(data);
	}

	public static boolean isFromPlugin() {
		return toMainList.isEmpty();
	}

	/**
	 * @author ChenSir
	 * @return 主界面从“fromPlugList”获取数据
	 */
	public static int getFromPluginType() {
		if (toMainList != null && toMainList.size() != 0) {
			Log.e("ShareData", toMainList.size()+"");
			return toMainList.get(0).Type;
		}
		return -1;
	}

	public static int getFromPluginLen() {
		if (toMainList != null && toMainList.size() != 0) {
			return toMainList.get(0).Len;
		}
		return -1;
	}

	public static byte[] getFromPluginData() {
		if (toMainList != null && toMainList.size() != 0) {
			return toMainList.get(0).data;
		}
		return null;
	}

	// 用于主程序处理完数据之后删除链表头
	public static void SetClearFlag(int flag) {

		if (flag == 0) {
			if (ShareData.toMainList.size() != 0) {
				ShareData.toMainList.remove(0);
			}
		}
	}

	// 获取设备条形码
	public String GetmachineShapeCode() {
		return null;
//		return ViewData.machineShapeCode;
	}

//	public static void setAppPackageNameToMain(Context context) {
//		LoadClassUtil.loadclass(context);
//	}
}
