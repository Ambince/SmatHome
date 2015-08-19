package com.xiaye.smarthome.bean;

import java.io.Serializable;


public class DvinfoBean implements Serializable {
	private int typeId;
	private int typeOfType;
	private String typeName;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTypeOfType() {
		return typeOfType;
	}

	public void setTypeOfType(int typeOfType) {
		this.typeOfType = typeOfType;
	}
}
