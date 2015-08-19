package com.xiaye.smarthome.bean;


public class RecordOnDvBean {
	
	private int recordNo;//记录号
	
	private boolean isUploaded;//是否已上传

	
	public RecordOnDvBean(){
		
	}
	
	public RecordOnDvBean(int recordNo, boolean isUploaded){
		this.recordNo = recordNo;
		this.setUploaded(isUploaded);
	}
	
	public int getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

	public boolean isUploaded() {
		return isUploaded;
	}

	public void setUploaded(boolean isUploaded) {
		this.isUploaded = isUploaded;
	}

	
}
