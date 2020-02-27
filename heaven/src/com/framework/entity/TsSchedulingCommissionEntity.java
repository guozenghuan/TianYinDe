package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class TsSchedulingCommissionEntity implements Serializable{
	private static final long serialVersionUID = -6119283600745161713L;

	private Long id;
	//业务员ID
	private Long tsUserId;
	//公司名称
	private String company;
	//姓名
	private String name;
	//手机号
	private String phone;
	//业务员账号
	private String account;
    //单号
	private String number;
	//提成
	private BigDecimal commission;
	//业务员本月累计提成
	private BigDecimal commissionTotal;
	//创建日期
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public BigDecimal getCommissionTotal() {
		return commissionTotal;
	}
	public void setCommissionTotal(BigDecimal commissionTotal) {
		this.commissionTotal = commissionTotal;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
}
