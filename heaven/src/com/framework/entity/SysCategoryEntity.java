package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 类目表
 * 
 * @author R & D
 * @email 
 * @date 2019-11-18 09:52:48
 */
public class SysCategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//类目名称
	private String name;
	//类目关键字，以JSON数组格式
	private String keywords;
	//类目广告语介绍
	private String desc;
	//父类目ID
	private Long pid;
	//类目图标
	private String iconUrl;
	//类目图片
	private String picUrl;
	//
	private String level;
	//排序
	private Integer sortOrder;
	//创建时间
	private Date addTime;
	//更新时间
	private Date updateTime;
	//逻辑删除
	private Integer deleted;

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
	 * 设置：类目名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：类目名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：类目关键字，以JSON数组格式
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	/**
	 * 获取：类目关键字，以JSON数组格式
	 */
	public String getKeywords() {
		return keywords;
	}
	/**
	 * 设置：类目广告语介绍
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * 获取：类目广告语介绍
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * 设置：父类目ID
	 */
	public void setPid(Long pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父类目ID
	 */
	public Long getPid() {
		return pid;
	}
	/**
	 * 设置：类目图标
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	/**
	 * 获取：类目图标
	 */
	public String getIconUrl() {
		return iconUrl;
	}
	/**
	 * 设置：类目图片
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 获取：类目图片
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * 设置：
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * 获取：
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * 设置：排序
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * 获取：排序
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}
	/**
	 * 设置：创建时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：逻辑删除
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	/**
	 * 获取：逻辑删除
	 */
	public Integer getDeleted() {
		return deleted;
	}
}
