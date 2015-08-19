package com.xiaye.smarthome.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import com.xiaye.smarthome.bean.PluginBean;
import com.xiaye.smarthome.constant.Type;

public class GetDataFromSharedPreference {

	public static String getUserName(String key, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE); // 私有数据
		String id = sharedPreferences.getString(key, "");
		return id;
	}

	public static void setUserName(String value, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE); // 私有数据
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString(Type.USER_NAME, value);
		editor.commit();// 提交修改
	}

	public static String getPassword(String key, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE); // 私有数据
		String ps = sharedPreferences.getString(key, null);
		return ps;

	}

	public static void setPassword(String value, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE); // 私有数据
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString(Type.PASSWORD_KEY, value);
		editor.commit();// 提交修改
	}

	public static String getServerIP(String key, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE); // 私有数据
		String ip = sharedPreferences.getString(key, null);
		return ip;
	}

	public static void setServerIP(String value, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE); // 私有数据
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString(Type.SERVERIP_KEY, value);
		editor.commit();// 提交修改
	}

	public static void setPluginBean(String key, Object value, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE); // 私有数据
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString(key, decodeObject(value));
		editor.commit();// 提交修改
	}

	public static PluginBean getPluginBean(String key, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE);
		String serializable = sharedPreferences.getString(key, "");
		PluginBean bean = encodeObjet(serializable);
		return bean;
	}
	
	public static String getPluginPkgName(String key, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE);
		String PkgName = sharedPreferences.getString(key, null);
		return PkgName;
	}
	
	public static void setPluginPkgName(String value, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString("PluginPkgName", value);
		editor.commit();// 提交修改
	}
	
	public static String getPluginCode(String key, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE);
		String mCode = sharedPreferences.getString(key, null);
		return mCode;
	}
	
	public static void setPluginCode(String value, Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString("mCode", value);
		editor.commit();// 提交修改
	}
	
	public static boolean removePreference(String key,Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"MyData", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.remove(key);
		return editor.commit();// 提交修改
	}
	
	
	
	
	

	/************************ 对象编码解码 ********************************/
	/**
	 * 
	 * @Description: 将传入的对象编码
	 * 
	 * @author ChenSir
	 * @version 1.0
	 * @date 2014年11月2日 下午5:26:13
	 * @param obj
	 * @return
	 */
	public static String decodeObject(Object obj) {
		String objectVal = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(obj);
			objectVal = new String(Base64.encode(baos.toByteArray(),
					Base64.DEFAULT));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectVal;
	}

	public static PluginBean encodeObjet(String value) {

		try {
			byte[] buffer = Base64.decode(value, Base64.DEFAULT);
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(bais);
				PluginBean bean = (PluginBean) ois.readObject();
				return bean;
			} finally {
				if (bais != null) {
					bais.close();
				}
				if (ois != null) {
					ois.close();
				}
				bais = null;
				ois = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
