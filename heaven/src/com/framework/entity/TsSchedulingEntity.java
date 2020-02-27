package com.framework.entity;

import java.io.Serializable;
import java.util.Date;

public class TsSchedulingEntity implements Serializable{
	private static final long serialVersionUID = 9128091159399965933L;
	
	//
	private Long id;
	//业务员ID
	private Long tsUserId;
	//公司id
	//private Long companyId;
	//公司名称
	private String company;
	//头像地址
	private String portraitUrl;
	//姓名
	private String name;
	//手机号
	private String phone;
	//业务员账号
	private String account;
	//值班日期
	private String scheduling;
	//创建日期
	private String createtime;
	//比较日期
	private Date tsscheduling;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
//	public Long getCompanyId() {
//		return companyId;
//	}
//	public void setCompanyId(Long companyId) {
//		this.companyId = companyId;
//	}
	public String getPortraitUrl() {
		return portraitUrl;
	}
	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
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
	public String getScheduling() {
		return scheduling;
	}
	public void setScheduling(String scheduling) {
		this.scheduling = scheduling;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public Date getTsscheduling() {
		return tsscheduling;
	}
	public void setTsscheduling(Date tsscheduling) {
		this.tsscheduling = tsscheduling;
	}
	 
	
	

}
