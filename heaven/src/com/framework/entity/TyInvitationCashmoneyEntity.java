package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 奠金
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:46:50
 */
public class TyInvitationCashmoneyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//模板用户ID
	private Long modulUserId;
	//模板
	private Long modulId;
	//用户ID
	private Long userId;
	//客户姓名
	private String name;
	//奠金金额
	private BigDecimal money;
	//订单号
	private String orderNumber;
	//支付状态:0-支付成功,1-支付失败
	private Integer status;
	//时间
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
	 * 设置：模板用户ID
	 */
	public void setModulUserId(Long modulUserId) {
		this.modulUserId = modulUserId;
	}
	/**
	 * 获取：模板用户ID
	 */
	public Long getModulUserId() {
		return modulUserId;
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
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
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
	 * 设置：奠金金额
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	/**
	 * 获取：奠金金额
	 */
	public BigDecimal getMoney() {
		return money;
	}
	/**
	 * 设置：订单号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 获取：订单号
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 设置：支付状态:0-支付成功,1-支付失败
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：支付状态:0-支付成功,1-支付失败
	 */
	public Integer getStatus() {
		return status;
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
