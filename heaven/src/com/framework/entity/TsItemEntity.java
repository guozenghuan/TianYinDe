package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TsItemEntity implements Serializable{
	private static final long serialVersionUID = 3069914840991282644L;

	private Long id;
	//业务员Id
	private Long tsUserId;
	//单号
	private String number; 
	//商品/服务Id
	private Long itemId;
	//商品/服务
	private String item;
	//数量
	private Long count;
	//单件成本    实际成本 (name)
	private BigDecimal unit;
	//客户价格(售价)
	private BigDecimal price;
	//给业务员的价格   unitPrice
	private BigDecimal cost;
	//现结
	private BigDecimal cash;
	//备注
	private String remark;
	//时间
	private String createtime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTsUserId() {
		return tsUserId;
	}
	public void setTsUserId(Long tsUserId) {
		this.tsUserId = tsUserId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public BigDecimal getUnit() {
		return unit;
	}
	public void setUnit(BigDecimal unit) {
		this.unit = unit;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public BigDecimal getCash() {
		return cash;
	}
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

}




