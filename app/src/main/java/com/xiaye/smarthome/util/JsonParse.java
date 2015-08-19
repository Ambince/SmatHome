package com.xiaye.smarthome.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 
 * 查询数据库时转化格式
 * 
 */
public class JsonParse {

	public String pagingJsonParse(Object start, Object all, Object condition) {
		String result;
		JSONObject object = new JSONObject();
		try {
			object.put("start", start);
			object.put("all", all);
			object.put("condition", String.valueOf(condition));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		result = object.toString();
		return result;
	}

	public static String initInfoJsonParse(String userName, String password, String sIP) {
		JSONObject json = new JSONObject();
		try {
			json.put("SM_USER", userName);
			json.put("SM_USERPWD", password);
			json.put("NET_WEBSERVERIP", sIP);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	// 更新设备信息表
	public String updateDeviceInfo(int machineId, int registerWay,
			String machineName, int typeId, String remark) {

		JSONObject jsonToDB = new JSONObject();
		try {
			jsonToDB.put("machineId", machineId);
			jsonToDB.put("registerWay", registerWay);
			jsonToDB.put("machineName", machineName);
			jsonToDB.put("typeId", typeId);
			jsonToDB.put("remark", remark);
			// jsonToDB.put("machineId", 1);
			// jsonToDB.put("registerWay", 1);
			// jsonToDB.put("machineName", "12333");
			// jsonToDB.put("typeId", 1);
			// jsonToDB.put("remark", "321111");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonToDB.toString();
	}

	public String threeConditionsQuery(Object start, Object all,
			Object condition, Object condition2, Object condition3) {

		String result;
		JSONObject object = new JSONObject();
		try {
			object.put("start", start);
			object.put("all", all);
			object.put("condition", String.valueOf(condition));
			object.put("conditionTwo", String.valueOf(condition2));
			object.put("conditionThree", String.valueOf(condition3));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		result = object.toString();
		return result;
	}

	public String twoConditionsQuery(Object start, Object all,
			Object condition, Object condition2) {

		String result;
		JSONObject object = new JSONObject();
		try {
			object.put("start", start);
			object.put("all", all);
			object.put("condition", String.valueOf(condition));
			object.put("conditionTwo", String.valueOf(condition2));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		result = object.toString();
		return result;
	}

}
