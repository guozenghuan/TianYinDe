package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 12:58:09
 */
public class ModulMusicEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//类型:system=系统,user=用户
	private String type;
	//系统则：0，用户则：userid
	private Long userId;
	//
	private String name;
	//
	private String path;
	//
	private Date createtime;

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
	 * 设置：类型:system=系统,user=用户
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型:system=系统,user=用户
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：系统则：0，用户则：userid
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：系统则：0，用户则：userid
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置：
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
