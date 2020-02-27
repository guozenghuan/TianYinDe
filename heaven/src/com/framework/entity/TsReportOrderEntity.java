package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TsReportOrderEntity implements Serializable{
	private static final long serialVersionUID = -7660376506564766206L;

	private Long id;
	//业务员Id      
	private Long tsUserId;
	//用户ID
	private Long userId;
	//单号
	private String number;
	//下单时间
	private String createtime;
	//订单提交时间
	private String ordertime;
	//服务总价格
	private BigDecimal total;
	
	//公司成本
	private BigDecimal auditor;
	//公司利润       
	private BigDecimal companyProfit;
	//业务员提成    
	private  BigDecimal  profit;
	//审核状态     
	private Long status;//(2:审核中 /0:审核通过 /1:审核未通过)
	//审核时间
	private String checktime;


	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	 
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public BigDecimal getCompanyProfit() {
		return companyProfit;
	}
	public void setCompanyProfit(BigDecimal companyProfit) {
		this.companyProfit = companyProfit;
	}
	public BigDecimal getProfit() {
		return profit;
	}
	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}
	public BigDecimal getAuditor() {
		return auditor;
	}
	public void setAuditor(BigDecimal auditor) {
		this.auditor = auditor;
	}
	public String getChecktime() {
		return checktime;
	}
	public void setChecktime(String checktime) {
		this.checktime = checktime;
	}

}
