package com.xiaye.smarthome.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: User
 * @Description: 用餐成员Bean
 * @author  Android组-ChengBin
 * @version 1.0
 * @date 2014-11-24 下午7:29:10
 *
 */
public class UserBean implements Serializable {
	
	
	private static final long serialVersionUID = -6626625370756541693L;
	
	private String id;
	
	private String memberName;
	private String callName;
	
	private String nativePlace;
	private String taboos;
	private String hoby;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCallName() {
		return callName;
	}

	public void setCallName(String callName) {
		this.callName = callName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
