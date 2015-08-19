package com.xiaye.smarthome.bean;

import java.io.Serializable;


/*
 * 
 * @ClassName: CookingRecordBean
 * @Description: 烹调记录表对象
 * @author  Android组-ChenSir
 * @version 1.0
 * @date 2014-11-25 下午10:26:18
 *
 */

public class CookingRecordBean implements Serializable {	
	
	private static final long serialVersionUID = 8831321448336205456L;
	
	
	private int machineId;
	private int foodProcessingId;
	private String menuId;
	private double times;
	private int Nodes;
	private int usenumber;
	private String machineShapeCode;
	private int record;
	private String datafilestoragepath;
	private String remark;

	// 烹调记录表增加属性
	private String menuName;


	public CookingRecordBean(int foodProcessingId, String menuId, double times,
			int Nodes, int usenumber, String machineShapeCode, int record,
			String datafilestoragepath) {
		this.foodProcessingId = foodProcessingId;
		this.menuId = menuId;
		this.times = times;
		this.Nodes = Nodes;
		this.usenumber = usenumber;
		this.machineShapeCode = machineShapeCode;
		this.record = record;
		this.datafilestoragepath = datafilestoragepath;

	}

	public CookingRecordBean(int foodProcessingId, String menuId, double times,
			int Nodes, int usenumber, String machineShapeCode, int record,
			String datafilestoragepath, String remark,String menuName) {
		this.foodProcessingId = foodProcessingId;
		this.menuId = menuId;
		this.times = times;
		this.Nodes = Nodes;
		this.usenumber = usenumber;
		this.machineShapeCode = machineShapeCode;
		this.record = record;
		this.datafilestoragepath = datafilestoragepath;
		this.remark = remark;
		this.menuName = menuName;

	}

	public CookingRecordBean(int foodProcessingId, String menuId,
			String remark, int usenumber) {
		this.foodProcessingId = foodProcessingId;
		this.menuId = menuId;
		this.remark = remark;
		this.usenumber = usenumber;
	}

	public CookingRecordBean(int foodProcessingId, String remark, int usenumber) {
		this.foodProcessingId = foodProcessingId;
		this.remark = remark;
		this.usenumber = usenumber;
	}

	public CookingRecordBean(String menuName, int usenumber) {
		this.menuName = menuName;
		this.usenumber = usenumber;
	}

	public CookingRecordBean(double times, int nodes, int usenumber,
			String machineShapeCode, int record, String datafilestoragepath,
			String remark) {
		this.times = times;
		this.Nodes = nodes;
		this.usenumber = usenumber;
		this.machineShapeCode = machineShapeCode;
		this.record = record;
		this.datafilestoragepath = datafilestoragepath;
		this.remark = remark;
	}

	public CookingRecordBean(int machineId, int foodProcessingId,
			String menuId, double times, int nodes, int usenumber,
			String machineShapeCode, int record, String datafilestoragepath) {
		this.machineId = machineId;
		this.foodProcessingId = foodProcessingId;
		this.menuId = menuId;
		this.times = times;
		this.Nodes = nodes;
		this.usenumber = usenumber;
		this.machineShapeCode = machineShapeCode;
		this.record = record;
		this.datafilestoragepath = datafilestoragepath;
	}

	public int getFoodProcessingId() {
		return foodProcessingId;
	}

	public void setFoodProcessingId(int foodProcessingId) {
		this.foodProcessingId = foodProcessingId;
	}

	public double getTimes() {
		return times;
	}

	public void setTimes(double times) {
		this.times = times;
	}

	public int getNodes() {
		return Nodes;
	}

	public void setNodes(int nodes) {
		Nodes = nodes;
	}

	public int getUsenumber() {
		return usenumber;
	}

	public void setUsenumber(int usenumber) {
		this.usenumber = usenumber;
	}

	public String getMachineShapeCode() {
		return machineShapeCode;
	}

	public void setMachineShapeCode(String machineShapeCode) {
		this.machineShapeCode = machineShapeCode;
	}

	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}

	public String getDatafilestoragepath() {
		return datafilestoragepath;
	}

	public void setDatafilestoragepath(String datafilestoragepath) {
		this.datafilestoragepath = datafilestoragepath;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

}
