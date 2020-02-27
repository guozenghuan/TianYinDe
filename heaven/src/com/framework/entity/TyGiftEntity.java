package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;



/**
 * 礼物
 * 
 * @author R & D
 * @email 
 * @date 2019-05-07 00:31:33
 */
public class TyGiftEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//礼物码
	private String giftCode;
	//礼物名称
	private String giftName;
	//礼物价格
	private BigDecimal price;

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
	 * 设置：礼物码
	 */
	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}
	/**
	 * 获取：礼物码
	 */
	public String getGiftCode() {
		return giftCode;
	}
	/**
	 * 设置：礼物名称
	 */
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	/**
	 * 获取：礼物名称
	 */
	public String getGiftName() {
		return giftName;
	}
	/**
	 * 设置：礼物价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：礼物价格
	 */
	public BigDecimal getPrice() {
		return price;
	}
}
