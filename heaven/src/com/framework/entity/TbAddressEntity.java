package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户收货地址
 * 
 * @author R & D
 * @email 
 * @date 2019-04-10 21:53:39
 */
public class TbAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户id
	private Long userId;
	//联系人
	private String name;
	//联系电话
	private String phone;
	//收获地址
	private String addres;
	//创建时间
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
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：联系人
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：联系人
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
	 * 设置：收获地址
	 */
	public void setAddres(String addres) {
		this.addres = addres;
	}
	/**
	 * 获取：收获地址
	 */
	public String getAddres() {
		return addres;
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
