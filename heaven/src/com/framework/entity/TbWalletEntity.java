package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 客户钱包
 * 
 * @author R & D
 * @email 
 * @date 2019-03-19 15:31:36
 */
public class TbWalletEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//关联用户id
	private Long userid;
	//钱包余额
	private BigDecimal wallet;

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
	 * 设置：关联用户id
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	/**
	 * 获取：关联用户id
	 */
	public Long getUserid() {
		return userid;
	}
	/**
	 * 设置：钱包余额
	 */
	public void setWallet(BigDecimal wallet) {
		this.wallet = wallet;
	}
	/**
	 * 获取：钱包余额
	 */
	public BigDecimal getWallet() {
		return wallet;
	}
}
