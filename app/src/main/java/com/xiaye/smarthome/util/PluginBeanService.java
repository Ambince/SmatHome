package com.xiaye.smarthome.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.xiaye.smarthome.bean.PluginBean;
import com.xiaye.smarthome.constant.Type;

/*
 * 
 * @ClassName: PluginBeanService
 * @Description: 封装操作pluginManagerList的函数
 * @author  ChenSir
 * @date 2014年10月19日 下午8:58:15
 *
 */
public class PluginBeanService {

	public static final String TAG = "PluginBeanService";

	/**
	 * 
	 * @Description:根据设备序列号遍历链表获取对应文件名
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014年10月25日 下午2:13:24
	 * @param device_ID
	 * @return
	 */
	public String getPackageName(String device_ID, Context context) {
		String pckgName = null;
		List<PluginBean> list = getAllPluginBean(context);
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				// 遍历每一个序列号
				PluginBean bean = list.get(i);
				String temp = bean.getMatchMcSCode();
				if (device_ID.equals(temp)) {
					pckgName = bean.getFileName();
					break;
				}
			}
		}
		return pckgName;
	}

	/**
	 * 
	 * @Description:根据文件名查询设备序列号
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-12-11 下午4:48:08
	 * @param pkgName
	 * @param context
	 * @return
	 */

	public String getMatchMcSCode(String pkgName, Context context) {
		String mcSCode = null;
		List<PluginBean> list = getAllPluginBean(context);
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				// 遍历每一个序列号
				PluginBean bean = list.get(i);
				String temp = bean.getFileName();
				if (pkgName.equals(temp)) {
					mcSCode = bean.getMatchMcSCode();
					break;
				}
			}
		}
		return mcSCode;
	}

	/**
	 * 
	 * @Description:根据设备序列号设置设备状态
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014年10月25日 下午2:26:18
	 * @param device_ID
	 * @param state
	 */
	public void setCertainState(String device_ID, int state, Context context) {
		List<PluginBean> list = getAllPluginBean(context);
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				// 遍历每一个序列号
				PluginBean bean = list.get(i);
				String temp = bean.getMatchMcSCode();
				if (device_ID.equals(temp)) {
					bean.setMatchMcState(state);
					GetDataFromSharedPreference.setPluginBean(device_ID, bean,
							context);
					break;
				}
			}
		}
	}

	public void setCertainMachineID(String device_ID, int machineID,
			Context context) {
		List<PluginBean> list = getAllPluginBean(context);
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				// 遍历每一个序列号
				PluginBean bean = list.get(i);
				String temp = bean.getMatchMcSCode();
				if (device_ID.equals(temp)) {
					// 修改状态
					bean.setMachineId(machineID);
					// 保存在SharePreference
					GetDataFromSharedPreference.setPluginBean(device_ID, bean,
							context);
					break;
				}
			}
		}
	}

	/**
	 * 
	 * @Description:根据文件名获取对应设备虚拟地址
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014年10月25日 下午4:15:00
	 * @param pckgName
	 * @return
	 */
	public int getMachineID(String pckgName, Context context) {
		List<PluginBean> list = getAllPluginBean(context);
		int machineID = 0;
		if (list != null && list.size() != 0) {

			for (int i = 0; i < list.size(); i++) {
				// 遍历每一个序列号
				PluginBean bean = list.get(i);
				String temp = bean.getFileName();
				if (pckgName.equals(temp)) {
					machineID = bean.getMachineId();
					break;
				}
			}
		}
		if (machineID > 0) {

			return machineID;
		}
		return 0;
	}

	/**
	 * 
	 * @Description:根据key删除SharedPreference中的数据
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-13 下午7:04:43
	 * @param key
	 */
	public boolean removeCertatinPlugBean(String key, Context context) {
		if (key != null) {

			SharedPreferences sharedPreferences = context.getSharedPreferences(
					"MyData", Context.MODE_PRIVATE);
			boolean isRemoved = sharedPreferences.edit().remove(key).commit();
			return isRemoved;
		}
		return false;

	}

	/**
	 * 
	 * @Description:获得PluginBean列表
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014-11-26 下午1:53:19
	 * @param context
	 * @return
	 */
	public static List<PluginBean> getAllPluginBean(Context context) {
		List<PluginBean> list = new ArrayList<PluginBean>();
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE);
		Map<String, ?> allContent = sharedPreferences.getAll();
		// 遍历map
		for (Map.Entry<String, ?> entry : allContent.entrySet()) {
			// 筛选Plugin
			String key = entry.getKey().toString();
			if (key != null) {
				if (!(key.equals(Type.USER_NAME))) {
					if (!(key.equals(Type.PASSWORD_KEY))) {
						if (!(key.equals(Type.SERVERIP_KEY))) {
							if (!(key.equals("PluginPkgName"))) {
								if (!(key.equals("mCode"))) {
									Log.i(TAG, key);
									PluginBean bean = GetDataFromSharedPreference
											.encodeObjet(entry.getValue()
													.toString());
									list.add(bean);
									return list;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
}
