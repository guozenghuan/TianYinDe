package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 服务
 * 
 * @author R & D
 * @email 
 * @date 2019-05-11 21:55:12
 */
public class TyUserServiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//父级id
	private Long parentId;
	//类型：liucheng=流程，fuwu=服务
	private String type;
	//标题
	private String name;
	//
	
	//内容
	private String text;
	//排序号就是id
	private Integer sort;

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
	 * 设置：父级id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父级id
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：类型：liucheng=流程，fuwu=服务
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型：liucheng=流程，fuwu=服务
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：标题
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：标题
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：内容
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 获取：内容
	 */
	public String getText() {
		return text;
	}
	/**
	 * 设置：排序号就是id
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序号就是id
	 */
	public Integer getSort() {
		return sort;
	}
}
