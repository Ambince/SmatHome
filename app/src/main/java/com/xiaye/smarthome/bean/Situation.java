package com.xiaye.smarthome.bean;

import java.io.Serializable;

/**
 * 
 * @ClassName: Situation
 * @Description: 健康状况表
 * @author Android组-ChengBin
 * @version 1.0
 * @date 2014-11-25 下午2:27:02
 * 
 */
public class Situation implements Serializable {
	
	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */
	
	
	private static final long serialVersionUID = 1133386377607486474L;
	public String name;
	public String result;
	public String unit;
	public String value;

	public Situation() {
		
	}
	
	public Situation(String name, String result, String unit, String value) {
		super();
		this.name = name;
		this.result = result;
		this.unit = unit;
		this.value = value;
	}
}
