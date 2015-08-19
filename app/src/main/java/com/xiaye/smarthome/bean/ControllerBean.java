package com.xiaye.smarthome.bean;

/**
 * 
 * @ClassName: ControllerBean
 * @Description: 中控对象
 * @author ChenSir
 * @version 1.0
 * @date 2015-5-29 下午1:56:45
 * 
 */
public class ControllerBean {

	// ID：中控ID
	// USER：中控别名
	// IP：中控ip地址
	// PORT：中控端口
	// STATE：中控在线状态
	private int controllerID;
	private String controllerName;
	private String controllerIP;
	private int controllerPort;
	private int controllerState;// 1代表在线 0代表不在线

	public int getControllerState() {
		return controllerState;
	}

	public void setControllerState(int controllerState) {
		this.controllerState = controllerState;
	}

	public int getControllerPort() {
		return controllerPort;
	}

	public void setControllerPort(int controllerPort) {
		this.controllerPort = controllerPort;
	}

	public String getControllerIP() {
		return controllerIP;
	}

	public void setControllerIP(String controllerIP) {
		this.controllerIP = controllerIP;
	}

	public String getControllerName() {
		return controllerName;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public int getControllerID() {
		return controllerID;
	}

	public void setControllerID(int controllerID) {
		this.controllerID = controllerID;
	}

}
