package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 提现申请
 * 
 * @author R & D
 * @email 
 * @date 2019-05-12 10:16:52
 */
public class TcUserWithdrawalEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户ID
	private Long userId;
	//单号
	private String orderNumber;
	//提现金额
	private BigDecimal money;
	//申请时间
	private Date createtime;
	//提现状态
	private String status;
	//备注
	private String note;

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
	 * 设置：单号
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 获取：单号
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * 设置：提现金额
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	/**
	 * 获取：提现金额
	 */
	public BigDecimal getMoney() {
		return money;
	}
	/**
	 * 设置：申请时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：申请时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * 设置：提现状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：提现状态
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：备注
	 */
	public String getNote() {
		return note;
	}
}
