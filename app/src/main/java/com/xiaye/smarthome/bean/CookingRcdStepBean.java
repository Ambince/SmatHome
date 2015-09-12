package com.xiaye.smarthome.bean;

import java.io.Serializable;

/**
 * 
 * @description 记录步骤表
 * 
 */
public class CookingRcdStepBean implements Serializable {

	private static final long serialVersionUID = -4099002684097867556L;
	
	private int foodProcessingId;
	private int nodeNumber;
	private int timeOfNode;
	private String tips;

	public CookingRcdStepBean() {

	}

	public int getFoodProcessingId() {
		return foodProcessingId;
	}

	public void setFoodProcessingId(int foodProcessingId) {
		this.foodProcessingId = foodProcessingId;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}

	public void setNodeNumber(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public int getTimeOfNode() {
		return timeOfNode;
	}

	public void setTimeOfNode(int timeOfNode) {
		this.timeOfNode = timeOfNode;
	}

}
