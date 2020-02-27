package com.framework.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;



/**
 * 模板信息表
 * 
 * @author R & D
 * @email 
 * @date 2019-04-28 12:58:09
 */
public class ModulEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//模板类型:system=系统模板,user=用户模板
	private String modulType;
	//系统则：0，用户则：userid
	private Long userId;
	//音乐id
	private Long musicId;
	private String musicPath;
	
	//关联的模板id，modul_01=1,模板表名=模板id;号隔开
	private String modulNameAndId;
	//模板状态：show=显示，hide=隐藏
	private String status;
	//创建时间
	private Date createtime;
	
	private List<String> moduleList;
	
	private Long oneModulId;
	private String oneModulPath;
	
	public String getMusicPath() {
		return musicPath;
	}
	public void setMusicPath(String musicPath) {
		this.musicPath = musicPath;
	}
	public Long getOneModulId() {
		return oneModulId;
	}
	public void setOneModulId(Long oneModulId) {
		this.oneModulId = oneModulId;
	}
	public String getOneModulPath() {
		return oneModulPath;
	}
	public void setOneModulPath(String oneModulPath) {
		this.oneModulPath = oneModulPath;
	}
	public List<String> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<String> moduleList) {
		this.moduleList = moduleList;
	}
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
	 * 设置：音乐id
	 */
	public void setMusicId(Long musicId) {
		this.musicId = musicId;
	}
	/**
	 * 获取：音乐id
	 */
	public Long getMusicId() {
		return musicId;
	}
	/**
	 * 设置：关联的模板id，modul_01=1,模板表名=模板id;号隔开
	 */
	public void setModulNameAndId(String modulNameAndId) {
		this.modulNameAndId = modulNameAndId;
	}
	/**
	 * 获取：关联的模板id，modul_01=1,模板表名=模板id;号隔开
	 */
	public String getModulNameAndId() {
		return modulNameAndId;
	}
	/**
	 * 设置：模板状态：show=显示，hide=隐藏
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：模板状态：show=显示，hide=隐藏
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
