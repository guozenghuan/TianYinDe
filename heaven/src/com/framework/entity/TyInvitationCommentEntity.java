package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 模板评论
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public class TyInvitationCommentEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//模板
	private Long modulId;
	
	private Long modulUserId;
	//
	private Long userId;
	//评论姓名
	private String name;
	//信息
	private String note;
	//时间
	private Date createtime;
	
	public Long getModulUserId() {
		return modulUserId;
	}
	public void setModulUserId(Long modulUserId) {
		this.modulUserId = modulUserId;
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
	 * 设置：模板
	 */
	public void setModulId(Long modulId) {
		this.modulId = modulId;
	}
	/**
	 * 获取：模板
	 */
	public Long getModulId() {
		return modulId;
	}
	/**
	 * 设置：
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：评论姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：评论姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：信息
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：信息
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
