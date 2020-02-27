package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 赴邀
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public class TyInvitationToinviteEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//模板
	private Long modulId;
	private Long modulUserId;
	//
	private Long userId;
	//客户姓名
	private String name;
	//联系电话
	private String phone;
	//赴邀人数
	private Integer number;
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
	 * 设置：客户姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：客户姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：赴邀人数
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}
	/**
	 * 获取：赴邀人数
	 */
	public Integer getNumber() {
		return number;
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
