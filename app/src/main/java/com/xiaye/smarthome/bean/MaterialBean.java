package com.xiaye.smarthome.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: Material
 * @Description: 材料Bean
 * @author Android组-ChengBin
 * @version 1.0
 * @date 2014-11-26 下午4:52:49
 * 
 */

public class MaterialBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int foodProcessingId;
	private String materialName;
	private int typeId;
	private String materialNumber;
	private String materialProcessingMethod;
	private int materialProcessingNumber;
	
	
	public MaterialBean() {

	}

	public MaterialBean(int foodProcessingId, String materialName, int typeId,
			String materialNumber, String materialProcessingMethod,
			int materialProcessingNumber) {
		this.foodProcessingId = foodProcessingId;
		this.materialName = materialName;
		this.typeId = typeId;
		this.materialNumber = materialNumber;
		this.materialProcessingMethod = materialProcessingMethod;
		this.materialProcessingNumber = materialProcessingNumber;
	}

	

	public int getFoodProcessingId() {
		return foodProcessingId;
	}

	public void setFoodProcessingId(int foodProcessingId) {
		this.foodProcessingId = foodProcessingId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getMaterialProcessingMethod() {
		return materialProcessingMethod;
	}

	public void setMaterialProcessingMethod(String materialProcessingMethod) {
		this.materialProcessingMethod = materialProcessingMethod;
	}

	public int getMaterialProcessingNumber() {
		return materialProcessingNumber;
	}

	public void setMaterialProcessingNumber(int materialProcessingNumber) {
		this.materialProcessingNumber = materialProcessingNumber;
	}

}
