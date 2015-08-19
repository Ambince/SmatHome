package com.xiaye.smarthome.bean;

import java.io.Serializable;

import android.R.integer;

/*
 * 
 * @ClassName: DeviceInformationBean
 * @Description: 设备信息表
 * @author  Android组-ChenSir
 * @version 1.0
 * @date 2014-11-25 下午10:27:05
 *
 */

public class DeviceInformationBean implements Serializable {

	private static final long serialVersionUID = 2291619795146806174L;

	private int device_Vaddrs;// 设备虚拟地址
	private int device_addrs;// 设备地址
	private String device_id;// 设备条形码
	private String machineName;// 设备别名
	private int registerWay;// 注册方式
	private int mState;// 当前状态
	private int machinePort;// 设备端口
	private String terminalPId;// 终端程序ID
	private String driverModuleId;// 驱动模块ID
	private String interfaceModuleId;// 界面模块ID
	private String mNote;// 备注
	private int typeId;
	private String typeName;
	private String typeOfType;
	
	
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeOfType() {
		return typeOfType;
	}

	public void setTypeOfType(String typeOfType) {
		this.typeOfType = typeOfType;
	}

	//
	private String software;// 软件版本
	private String manufact;// 生产厂家
	private String date;// 生产日期

	public DeviceInformationBean() {

	}

	public DeviceInformationBean(int device_Vaddrs, String device_id,
			String machineName, String manufact, String date, String software,
			int registerWay) {

		this.setDevice_Vaddrs(device_Vaddrs);
		this.setDevice_id(device_id);
		this.machineName = machineName;
		this.manufact = manufact;
		this.date = date;
		this.software = software;
		this.registerWay = registerWay;
	}

	public DeviceInformationBean(int device_Vaddrs, String machineName,
			int machineState) {
		this.device_Vaddrs = device_Vaddrs;
		this.machineName = machineName;
		this.mState = machineState;

	}

	public DeviceInformationBean(String machineName, int mState) {

		this.machineName = machineName;
		this.mState = mState;
	}

	public DeviceInformationBean(int device_Vaddrs, int device_addrs,
			String device_id, String machineName, int mState, int machinePort,
			String terminalPId, String driverModuleId,
			String interfaceModuleId, int registerWay, String mNote, int typeId) {

		this.device_Vaddrs = device_Vaddrs;
		this.device_addrs = device_addrs;
		this.device_id = device_id;
		this.machineName = machineName;
		this.mState = mState;
		this.machinePort = machinePort;
		this.terminalPId = terminalPId;
		this.driverModuleId = driverModuleId;
		this.interfaceModuleId = interfaceModuleId;
		this.registerWay = registerWay;
		this.mNote = mNote;
		this.typeId = typeId;
	}

	public DeviceInformationBean(int registerWay, String machineName,
			int typeId, String mNote) {
		this.registerWay = registerWay;
		this.machineName = machineName;
		this.typeId = typeId;
		this.mNote = mNote;

	}

	public DeviceInformationBean(String machineShapeCode, String machineName, int typeId,
			String remark, String typeName, String typeOfType) {

		this.device_id = machineShapeCode;
		this.machineName = machineName;
		this.typeId = typeId;
		this.mNote = remark;
		this.typeName = typeName;
		this.typeOfType = typeOfType;

	}
	
	

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getManufact() {
		return manufact;
	}

	public void setManufact(String manufact) {
		this.manufact = manufact;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public int getRegisterWay() {
		return registerWay;
	}

	public void setRegisterWay(int registerWay) {
		this.registerWay = registerWay;
	}

	public int getmState() {
		return mState;
	}

	public void setmState(int mState) {
		this.mState = mState;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public int getDevice_Vaddrs() {
		return device_Vaddrs;
	}

	public void setDevice_Vaddrs(int device_Vaddrs) {
		this.device_Vaddrs = device_Vaddrs;
	}

	public int getDevice_addrs() {
		return device_addrs;
	}

	public void setDevice_addrs(int device_addrs) {
		this.device_addrs = device_addrs;
	}

	public String getmNote() {
		return mNote;
	}

	public void setmNote(String mNote) {
		this.mNote = mNote;
	}

	public int getMachinePort() {
		return machinePort;
	}

	public void setMachinePort(int machinePort) {
		this.machinePort = machinePort;
	}

	public String getTerminalPId() {
		return terminalPId;
	}

	public void setTerminalPId(String terminalPId) {
		this.terminalPId = terminalPId;
	}

	public String getDriverModuleId() {
		return driverModuleId;
	}

	public void setDriverModuleId(String driverModuleId) {
		this.driverModuleId = driverModuleId;
	}

	public String getInterfaceModuleId() {
		return interfaceModuleId;
	}

	public void setInterfaceModuleId(String interfaceModuleId) {
		this.interfaceModuleId = interfaceModuleId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
}