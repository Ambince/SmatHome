package com.xiaye.smarthome.bean;

import java.io.Serializable;

public class CookUtensilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String machineName;
	private int machineState;
	private int machineId;
	private String machineShapeCode;

	public CookUtensilBean() {

	}

	public CookUtensilBean(String machineName, int machineState, int machineId,
			String machineShapeCode) {
		this.machineName = machineName;
		this.machineState = machineState;
		this.machineId = machineId;
		this.machineShapeCode = machineShapeCode;
	}

	public CookUtensilBean(int machineID, String machineName, int machineState) {
		this.machineId = machineID;
		this.machineName = machineName;
		this.machineState = machineState;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public int getMachineState() {
		return machineState;
	}

	public void setMachineState(int machineState) {
		this.machineState = machineState;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	public String getMachineShapeCode() {
		return machineShapeCode;
	}

	public void setMachineShapeCode(String machineShapeCode) {
		this.machineShapeCode = machineShapeCode;
	}

}
