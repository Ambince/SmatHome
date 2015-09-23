package com.xiaye.smarthome.util;

import android.util.Log;

import com.xiaye.smarthome.bean.ControllerBean;
import com.xiaye.smarthome.bean.CookMenuBean;
import com.xiaye.smarthome.bean.CookUtensilBean;
import com.xiaye.smarthome.bean.CookingRcdStepBean;
import com.xiaye.smarthome.bean.CookingRecordBean;
import com.xiaye.smarthome.bean.DeviceInformationBean;
import com.xiaye.smarthome.bean.LightGroupBean;
import com.xiaye.smarthome.bean.LightGroupMemberBean;
import com.xiaye.smarthome.bean.MaterialBean;
import com.xiaye.smarthome.bean.UserBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析JSON
 */

public class ParseJson {

    public static List<CookingRecordBean> parseCookingRecordBean(String js)
            throws Exception {
        List<CookingRecordBean> cookbookDetailMorebeans = new ArrayList<CookingRecordBean>();

        JSONArray jsonArray = new JSONArray(js);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int foodProcessingId = jsonObject.getInt("foodProcessingId");
            String menuId = jsonObject.getString("menuId");
            double times = jsonObject.getDouble("times");
            int Nodes = jsonObject.getInt("Nodes");
            int usenumber = jsonObject.getInt("usenumber");
            String machineShapeCode = jsonObject.getString("machineShapeCode");
            int record = jsonObject.getInt("record");
            String datafilestoragepath = jsonObject
                    .getString("datafilestoragepath");

            CookingRecordBean bean = new CookingRecordBean(foodProcessingId,
                    menuId, times, Nodes, usenumber, machineShapeCode, record,
                    datafilestoragepath);
            cookbookDetailMorebeans.add(bean);

        }
        return cookbookDetailMorebeans;
    }

    public static List<CookingRecordBean> parseCookingRecordBean2(String js)
            throws Exception {
        List<CookingRecordBean> cookbookDetailMorebeans = new ArrayList<CookingRecordBean>();

        JSONArray jsonArray = new JSONArray(js);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int machineId = jsonObject.getInt("machineId");
            int foodProcessingId = jsonObject.getInt("foodProcessingId");
            String menuId = jsonObject.getString("menuId");
            double times = jsonObject.getDouble("times");
            int Nodes = jsonObject.getInt("Nodes");
            int usenumber = jsonObject.getInt("usenumber");
            String machineShapeCode = jsonObject.getString("machineShapeCode");
            int record = jsonObject.getInt("record");
            String datafilestoragepath = jsonObject
                    .getString("datafilestoragepath");

            CookingRecordBean bean = new CookingRecordBean(machineId,
                    foodProcessingId, menuId, times, Nodes, usenumber,
                    machineShapeCode, record, datafilestoragepath);
            cookbookDetailMorebeans.add(bean);

        }
        return cookbookDetailMorebeans;
    }

    public static CookingRecordBean parseCbDetailBean(String js)
            throws Exception {

        JSONArray jsonArray = new JSONArray(js);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            double times = jsonObject.getDouble("times");
            Log.e("parseCbDetailBean", times + "");
            int nodes = jsonObject.getInt("Nodes");
            int usenumber = jsonObject.getInt("usenumber");
            String machineShapeCode = jsonObject.getString("machineShapeCode");
            int record = Integer.parseInt(jsonObject.getString("record"));
            String datafilestoragepath = jsonObject
                    .getString("datafilestoragepath");
            String remark = jsonObject.getString("remark");

            CookingRecordBean bean = new CookingRecordBean(times, nodes,
                    usenumber, machineShapeCode, record, datafilestoragepath,
                    remark);

            return bean;
        }
        return null;
    }

    public static List<CookingRecordBean> parseCookingRecordbean(String js)
            throws Exception {
        List<CookingRecordBean> cookingRecordBeans = new ArrayList<CookingRecordBean>();

        JSONArray jsonArray = new JSONArray(js);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            int foodProcessingId = jsonObject.getInt("foodProcessingId");
            String menuName = jsonObject.getString("menuName");
            String menuId = jsonObject.getString("menuId");
            double times = jsonObject.getDouble("times");
            int Nodes = jsonObject.getInt("Nodes");
            int usenumber = jsonObject.getInt("usenumber");
            String machineShapeCode = jsonObject.getString("machineShapeCode");
            int record = jsonObject.getInt("record");
            String datafilestoragepath = jsonObject
                    .getString("datafilestoragepath");
            String remark = jsonObject.getString("remark");

            CookingRecordBean cookbookDetailMorebean = new CookingRecordBean(
                    foodProcessingId, menuId, times, Nodes, usenumber,
                    machineShapeCode, record, datafilestoragepath, remark,
                    menuName);

            cookingRecordBeans.add(cookbookDetailMorebean);

        }
        return cookingRecordBeans;
    }

    public static List<MaterialBean> parseMaterialBeanList(String js)
            throws Exception {

        List<MaterialBean> list = new ArrayList<MaterialBean>();

        JSONArray jsonArray = new JSONArray(js);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int foodProcessingId = jsonObject.getInt("foodProcessingId");
            String materialName = jsonObject.getString("materialName");
            int typeId = jsonObject.getInt("typeId");
            String materialNumber = jsonObject.getString("materialNumber");
            String materialProcessingMethod = jsonObject
                    .getString("materialProcessingMethod");
            int materialProcessingNumber = jsonObject
                    .getInt("materialProcessingNumber");

            MaterialBean bean = new MaterialBean(foodProcessingId,
                    materialName, typeId, materialNumber,
                    materialProcessingMethod, materialProcessingNumber);
            list.add(bean);
            Log.e("ParseJson", bean + "");
        }
        return list;
    }

    public static List<CookMenuBean> parseCookbookbean(String js)
            throws Exception {

        List<CookMenuBean> cookbookbeans = new ArrayList<CookMenuBean>();

        JSONArray jsonArray = new JSONArray(js);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String cookbook_listName = jsonObject.getString("menuName");
            String menuId = jsonObject.getString("menuId");
            String summarize = jsonObject.getString("summarize");
            String introduceMakeMethod = jsonObject
                    .getString("introduceMakeMethod");

            String meat = jsonObject.getString("meat");
            boolean isMeat = meat.equals("y");
            String dry = jsonObject.getString("dry");
            boolean isDry = dry.equals("y");

            String color = jsonObject.getString("color");
            String taste = jsonObject.getString("test");

            CookMenuBean bean = new CookMenuBean(cookbook_listName, menuId,
                    summarize, introduceMakeMethod, isMeat, isDry, color, taste);

            cookbookbeans.add(bean);

        }
        return cookbookbeans;

    }

    public static List<CookUtensilBean> parseDeviceInforbean(String js) {
        int machineID = 0;
        String machineName = null;
        int machineState = 0;

        List<CookUtensilBean> DeviceInfobeans = new ArrayList<CookUtensilBean>();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(js);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                machineID = jsonObject.getInt("machineId");
                machineName = jsonObject.getString("machineName");
                machineState = jsonObject.getInt("machineState");
                CookUtensilBean deInfo = new CookUtensilBean(machineID,
                        machineName, machineState);
                DeviceInfobeans.add(deInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return DeviceInfobeans;

    }

    // public static String parseRegisterbean(String js) throws Exception {
    //
    // JSONObject jsonObject = new JSONObject(js);
    // device_name = jsonObject.getString("device_name");
    // return device_name;
    // }
    //

    /**
     * @param js
     * @return
     * @throws Exception
     * @Description:解析“设备注册”时返回的JSON数据
     * @author ChenSir
     * @version 1.0
     * @date 2014-11-26 下午3:32:09
     */
    public static DeviceInformationBean parseDeviceInformation(String js)
            throws Exception {

        JSONObject jsonObject = new JSONObject(js);
        int device_Vaddrs = jsonObject.getInt("device_addrs");// 设备虚拟地址
        String device_id = jsonObject.getString("device_id");// 条形码
        String machineName = jsonObject.getString("device_name");
        String manufact = jsonObject.getString("manufact");
        String date = jsonObject.getString("date");
        String software_v = jsonObject.getString("software");
        int registerWay = jsonObject.getInt("registerWay");
        int dvType = jsonObject.getInt("machineType");
        int dvAddr = jsonObject.getInt("machineAddress");

        DeviceInformationBean deviceInformationbean = new DeviceInformationBean(
                device_Vaddrs, device_id, machineName, manufact, date,
                software_v, registerWay,dvType,dvAddr);

        return deviceInformationbean;
    }

    public static DeviceInformationBean parseDvInfoDetail(String js)
            throws Exception {

        JSONArray jsonArray = new JSONArray(js);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            int device_Vaddrs = jsonObject.getInt("machineId");
            String device_id = jsonObject.getString("machineShapeCode");
            String machineName = jsonObject.getString("machineName");
            int device_addrs = jsonObject.getInt("machineAddress");
            int mState = jsonObject.getInt("machineState");
            int machinePort = jsonObject.getInt("machinePort");
            String terminalPId = jsonObject.getString("terminalProgramId");
            String driverModuleId = jsonObject.getString("driverModuleId");
            String interfaceModuleId = jsonObject
                    .getString("interfaceModuleId");
            String mNote = jsonObject.getString("remark");
            int registerWay = jsonObject.getInt("registerWay");
            int typeId = jsonObject.getInt("typeId");
            DeviceInformationBean deviceInformationbean = new DeviceInformationBean(
                    device_Vaddrs, device_addrs, device_id, machineName,
                    mState, machinePort, terminalPId, driverModuleId,
                    interfaceModuleId, registerWay, mNote, typeId);

            return deviceInformationbean;
        }
        return null;
    }

    public static DeviceInformationBean parseRegisterInquirybean(String js)
            throws Exception {
        DeviceInformationBean registerInquirybean = null;

        JSONArray jsonArray = new JSONArray(js);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String machineShapeCode = jsonObject.getString("machineShapeCode");
            String machineName = jsonObject.getString("machineName");
            int typeId = jsonObject.getInt("typeId");
            String typeName = jsonObject.getString("typeName");
            String typeOfType = jsonObject.getString("typeOfType");
            String remark = jsonObject.getString("remark");
            Log.e("jsonObject", jsonObject.toString());

            registerInquirybean = new DeviceInformationBean(machineShapeCode,
                    machineName, typeId, remark, typeName, typeOfType);
        }

        return registerInquirybean;
    }

    public static List<String> parseMatchMenuName(String js) throws Exception {
        String menuName;
        List<String> list = new ArrayList<String>();
        JSONArray jsonArray = new JSONArray(js);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            menuName = jsonObject.getString("menuName");
            list.add(menuName);
        }
        return list;

    }

    public static String parseMatchMenuId(String js) throws Exception {
        String menuId = null;
        JSONArray jsonArray = new JSONArray(js);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            menuId = jsonObject.getString("menuName");
        }
        return menuId;

    }

    public static List<CookUtensilBean> parseCookUtensilList(String js)
            throws Exception {

        List<CookUtensilBean> machinebeans = new ArrayList<CookUtensilBean>();
        JSONArray jsonArray = new JSONArray(js);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String machineName = jsonObject.getString("machineName");
            int machineState = jsonObject.getInt("machineState");
            int machineId = jsonObject.getInt("machineId");
            String machineShapeCode = jsonObject.getString("machineShapeCode");
            CookUtensilBean machinebean = new CookUtensilBean(machineName,
                    machineState, machineId, machineShapeCode);

            machinebeans.add(machinebean);
        }
        return machinebeans;
    }

    public static List<CookingRcdStepBean> parseCookingRcdStepBean(String js) {
        List<CookingRcdStepBean> cRcdStepBeans = new ArrayList<CookingRcdStepBean>();
        int nodenumber;
        int timenode;
        String tips;
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(js);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nodenumber = jsonObject.getInt("nodenumber");
                timenode = jsonObject.getInt("timenode");
                tips = jsonObject.getString("tips");

                CookingRcdStepBean cRcdStepBean = new CookingRcdStepBean();
                cRcdStepBean.setNodeNumber(nodenumber);
                cRcdStepBean.setTimeOfNode(timenode);
                cRcdStepBean.setTips(tips);
                cRcdStepBeans.add(cRcdStepBean);
            }
            return cRcdStepBeans;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static List<UserBean> parseUserBeanList(String js)
            throws JSONException {

        List<UserBean> list = new ArrayList<UserBean>();
        JSONArray jArray = new JSONArray(js);

        for (int i = 0; i < jArray.length(); i++) {

            JSONObject jsonObject = jArray.getJSONObject(i);
            String memberName = jsonObject.getString("memberName");
            String callName = jsonObject.getString("callName");

            UserBean bean = new UserBean();
            bean.setMemberName(memberName);
            bean.setCallName(callName);

            list.add(bean);
        }

        return list;

    }

    public static List<ControllerBean> parseContrloControllerBeans(String js)
            throws JSONException {
        // TODO
        List<ControllerBean> list = new ArrayList<ControllerBean>();
        JSONArray jArray = new JSONArray(js);
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jsonObject = jArray.getJSONObject(i);

            int controllerID = jsonObject.getInt("ID");
            String controllerName = jsonObject.getString("USER");
            String controllerIP = jsonObject.getString("IP");
            int controllerPort = jsonObject.getInt("PORT");
            int controllerState = jsonObject.getInt("STATE");

            ControllerBean bean = new ControllerBean();
            bean.setControllerID(controllerID);
            bean.setControllerIP(controllerIP);
            bean.setControllerName(controllerName);
            bean.setControllerPort(controllerPort);
            bean.setControllerState(controllerState);

            list.add(bean);
        }
        return list;
    }

    public static byte[][] parseCookingRcdStepBeanByteArrary(String js) {
        byte[][] msg = null;
        try {
            JSONArray jsonArray = new JSONArray(js);
            int length = jsonArray.length();
            msg = new byte[length][];
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String tips = jsonObject.getString("tips");
                msg[i] = tips.getBytes();
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }
        return msg;
    }

    public static List<LightGroupBean> parseLightGroupBeans(String js)
            throws JSONException {
        List<LightGroupBean> list = new ArrayList<>();
        JSONArray jArray = new JSONArray(js);
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jsonObject = jArray.getJSONObject(i);

            int groupId = jsonObject.getInt("groupId");
            String groupName = jsonObject.getString("groupName");
            int groupAddress = jsonObject.getInt("groupAddress");
            int groupPort = jsonObject.getInt("machinePort");
            String remarks = jsonObject.getString("remarks");

            LightGroupBean bean = new LightGroupBean();
            bean.setGroupId(groupId);
            bean.setGroupName(groupName);
            bean.setGroupAddress(groupAddress);
            bean.setGroupPort(groupPort);
            bean.setRemarks(remarks);

            list.add(bean);
        }
        return list;
    }

    public static List<LightGroupMemberBean> parseLightGroupMemberBeans(String js)
            throws JSONException {
        List<LightGroupMemberBean> list = new ArrayList<>();
        JSONArray jArray = new JSONArray(js);
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jsonObject = jArray.getJSONObject(i);

            int groupAddress = jsonObject.getInt("groupAddress");
            int groupPort = jsonObject.getInt("groupPort");
            String remarks = jsonObject.getString("remarks");

            LightGroupMemberBean bean = new LightGroupMemberBean();
            bean.setGroupAddress(groupAddress);
            bean.setGroupPort(groupPort);
            bean.setRemarks(remarks);

            list.add(bean);
        }
        return list;
    }

    public static List<LightGroupBean> parseLightControlBeans(String js)
            throws JSONException {
        List<LightGroupBean> list = new ArrayList<>();
        JSONArray jArray = new JSONArray(js);
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jsonObject = jArray.getJSONObject(i);

            int id = jsonObject.getInt("groupId");
            int groupName = jsonObject.getInt("groupName");

            LightGroupBean bean = new LightGroupBean();
            bean.setGroupId(id);
            bean.setGroupAddress(groupName);

            list.add(bean);
        }
        return list;
    }


    public static List<LightGroupMemberBean> parseAllLightBeans(String js)
            throws JSONException {
        List<LightGroupMemberBean> list = new ArrayList<>();
        JSONArray jArray = new JSONArray(js);
        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jsonObject = jArray.getJSONObject(i);

            int device_Vaddrs = jsonObject.getInt("machineId");
            int addr = jsonObject.getInt("machineAddress");
            int groupPort = jsonObject.getInt("machinePort");
            String name = jsonObject.getString("machineName");

            LightGroupMemberBean bean = new LightGroupMemberBean();

            bean.setName(name);
            bean.setDv_addrs(addr);
            bean.setDevice_Vaddrs(device_Vaddrs);
            bean.setGroupPort(groupPort);

            list.add(bean);
        }
        return list;
    }


    /**
     * @param js
     * @return
     * @Description：将界面插件下载成功返回的字符串解析出APK路径
     * @author ChenSir
     * @version 1.0
     * @date 2014年10月26日 下午8:44:32
     */
    public static String getApkURL(String js) {
        String apkUrl = null;
        try {
            JSONObject json = new JSONObject(js);
            apkUrl = json.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return apkUrl;
    }
}