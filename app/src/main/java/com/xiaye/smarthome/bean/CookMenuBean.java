package com.xiaye.smarthome.bean;

import java.io.Serializable;

public class CookMenuBean implements Serializable {

	private static final long serialVersionUID = -2316055231329685671L;

	/**
	 * 菜谱id(主键)
	 */
	private String menuId;
	/**
	 * 菜系名称
	 */
	private String menuName;
	/**
	 * 所属菜系
	 */
	private String theCuisine;
	/**
	 * 干湿
	 */
	private boolean dry;
	/**
	 * 荤素
	 */
	private boolean meat;
	/**
	 * 鱼类
	 */
	private boolean fish;
	/**
	 * 肉类
	 */
	private boolean meatType;
	/**
	 * 禽类
	 */
	private boolean birds;
	/**
	 * 菜类
	 */
	private boolean food;
	/**
	 * 菌类
	 */
	private boolean mushroom;
	/**
	 * 制品类
	 */
	private boolean productsClass;
	/**
	 * 颜色
	 */
	private String color;
	
	/**
	 * 味道
	 */
	private String taste;
	
	
	/**
	 * 菜谱概述
	 */
	private String summarize;
	/**
	 * 制作介绍
	 */
	private String introduceMakeMethod;

	
	public CookMenuBean() {

	}
	
	public CookMenuBean(String menuId, String menuName) {
		this.menuId = menuId;
		this.menuName = menuName;
	}

	public CookMenuBean(String menuName, String theCuisine, boolean dry,
			boolean meat, boolean fish, boolean meatType, boolean birds,
			boolean food, boolean mushroom, boolean productsClass,
			String color, String summarize, String introduceMakeMethod) {
		this.menuName = menuName;
		this.theCuisine = theCuisine;
		this.dry = dry;
		this.meat = meat;
		this.fish = fish;
		this.meatType = meatType;
		this.birds = birds;
		this.food = food;
		this.mushroom = mushroom;
		this.productsClass = productsClass;
		this.color = color;
		this.summarize = summarize;
		this.introduceMakeMethod = introduceMakeMethod;

	}

	

	public CookMenuBean(String menuName, String menuId,
			String summarize, String introduceMakeMethod, boolean isMeat,
			boolean isDry, String color, String taste) {
		this.menuName = menuName;
		this.menuId = menuId;
		this.summarize = summarize;
		this.introduceMakeMethod = introduceMakeMethod;
		this.meat = isMeat;
		this.dry = isDry;
		this.color = color;
		this.setTaste(taste);
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getTheCuisine() {
		return theCuisine;
	}

	public void setTheCuisine(String theCuisine) {
		this.theCuisine = theCuisine;
	}

	public boolean isDry() {
		return dry;
	}

	public void setDry(boolean dry) {
		this.dry = dry;
	}

	public boolean isMeat() {
		return meat;
	}

	public void setMeat(boolean meat) {
		this.meat = meat;
	}

	public boolean isFish() {
		return fish;
	}

	public void setFish(boolean fish) {
		this.fish = fish;
	}

	public boolean isMeatType() {
		return meatType;
	}

	public void setMeatType(boolean meatType) {
		this.meatType = meatType;
	}

	public boolean isBirds() {
		return birds;
	}

	public void setBirds(boolean birds) {
		this.birds = birds;
	}

	public boolean isFood() {
		return food;
	}

	public void setFood(boolean food) {
		this.food = food;
	}

	public boolean isMushroom() {
		return mushroom;
	}

	public void setMushroom(boolean mushroom) {
		this.mushroom = mushroom;
	}

	public boolean isProductsClass() {
		return productsClass;
	}

	public void setProductsClass(boolean productsClass) {
		this.productsClass = productsClass;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSummarize() {
		return summarize;
	}

	public void setSummarize(String summarize) {
		this.summarize = summarize;
	}

	public String getIntroduceMakeMethod() {
		return introduceMakeMethod;
	}

	public void setIntroduceMakeMethod(String introduceMakeMethod) {
		this.introduceMakeMethod = introduceMakeMethod;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

}
