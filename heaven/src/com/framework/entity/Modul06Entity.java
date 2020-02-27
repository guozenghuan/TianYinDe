package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 02:12:11
 */
public class Modul06Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//模板路径
	private String modulPath;
	//模板类型:system=系统模板,user=用户模板
	private String modulType;
	//
	private String image01;
	//
	private String text01;
	//
	private String text02;
	//
	private String text03;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：模板路径
	 */
	public void setModulPath(String modulPath) {
		this.modulPath = modulPath;
	}
	/**
	 * 获取：模板路径
	 */
	public String getModulPath() {
		return modulPath;
	}
	/**
	 * 设置：模板类型:system=系统模板,user=用户模板
	 */
	public void setModulType(String modulType) {
		this.modulType = modulType;
	}
	/**
	 * 获取：模板类型:system=系统模板,user=用户模板
	 */
	public String getModulType() {
		return modulType;
	}
	/**
	 * 设置：
	 */
	public void setImage01(String image01) {
		this.image01 = image01;
	}
	/**
	 * 获取：
	 */
	public String getImage01() {
		return image01;
	}
	/**
	 * 设置：
	 */
	public void setText01(String text01) {
		this.text01 = text01;
	}
	/**
	 * 获取：
	 */
	public String getText01() {
		return text01;
	}
	/**
	 * 设置：
	 */
	public void setText02(String text02) {
		this.text02 = text02;
	}
	/**
	 * 获取：
	 */
	public String getText02() {
		return text02;
	}
	/**
	 * 设置：
	 */
	public void setText03(String text03) {
		this.text03 = text03;
	}
	/**
	 * 获取：
	 */
	public String getText03() {
		return text03;
	}
}