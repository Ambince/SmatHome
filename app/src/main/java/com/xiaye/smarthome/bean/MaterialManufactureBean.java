package com.xiaye.smarthome.bean;

import java.io.Serializable;

/*
 * 
 * @ClassName: MaterialManufactureBean
 * @Description: 原料制作表对象
 * @author  Android组-ChenSir
 * @version 1.0
 * @date 2014-11-25 下午10:24:07
 *
 */
public class MaterialManufactureBean implements Serializable {

	
	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */
	
	
	private static final long serialVersionUID = 1L;
	private String materialId;
	private int foodProcessingId;
	private String materialName;
	private String typeId;
	private String materialNumber;
	private String materialProcessingMethod;
	private int materialProcessingNumber;

	public MaterialManufactureBean(String materialId, int foodProcessingId,
			String materialName, String typeId, String materialNumber,
			String materialProcessingMethod, int materialProcessingNumber) {

		this.materialId = materialId;
		this.foodProcessingId = foodProcessingId;
		this.materialName = materialName;
		this.typeId = typeId;
		this.materialNumber = materialNumber;
		this.materialProcessingMethod = materialProcessingMethod;
		this.materialProcessingNumber = materialProcessingNumber;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
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