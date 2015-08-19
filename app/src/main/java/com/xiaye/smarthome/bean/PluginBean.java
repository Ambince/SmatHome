package com.xiaye.smarthome.bean;

import java.io.Serializable;

public class PluginBean implements Serializable{	
		
	private static final long serialVersionUID = 8938274970665283684L;
	
	
	private String fileName;//  界面插件的文件名
	private String matchMcSCode;// 界面插件的序列号（同 设备序列号）
	private static int matchMcState = 1;// 界面插件当前状态（打开0/关闭1）
	private int machineId;// 设备虚拟地址
	

	public int getMatchMcState() {
		return matchMcState;
	}

	public void setMatchMcState(int matchMcState) {
		PluginBean.matchMcState = matchMcState;
	}


	public String getMatchMcSCode() {
		return matchMcSCode;
	}

	public void setMatchMcSCode(String matchMcSCode) {
		this.matchMcSCode = matchMcSCode;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
