package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TsOrderEntity implements Serializable{
	private static final long serialVersionUID = -8922842776208229343L;
	
	private Long id;
	//业务员Id
	private Long tsUserId;
	//用户ID
	private Long userId;
	//单号
	private String number;  
	//姓名
	private String name;
	//性别
	private String sex;
	//年龄
	private Long age;
	//电话
	private String phone;
	//厅
	private String ting;
	//办场
	private String yuan;
	//结款时间
	private String loanTime;
	//类别
	private String sort;
	//接体地址
	private String jtdc;
	//包单
	private String chit;
	//业务编号
	private String orderNumb;
	//服务总价格
	private BigDecimal total;
	//时间
	private Date createtime;
	
	//佣金
	private BigDecimal rebate;
	//业务员人数
	private Integer tsNumber;
//	//定金
//	private BigDecimal deposit;
//	//定金支付状态
//	private Integer depositSatatus;
	
	
	
	
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTing() {
		return ting;
	}
	public void setTing(String ting) {
		this.ting = ting;
	}
	public String getYuan() {
		return yuan;
	}
	public void setYuan(String yuan) {
		this.yuan = yuan;
	}
	public String getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getJtdc() {
		return jtdc;
	}
	public void setJtdc(String jtdc) {
		this.jtdc = jtdc;
	}
	public String getChit() {
		return chit;
	}
	public void setChit(String chit) {
		this.chit = chit;
	}
	public String getOrderNumb() {
		return orderNumb;
	}
	public void setOrderNumb(String orderNumb) {
		this.orderNumb = orderNumb;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public BigDecimal getRebate() {
		return rebate;
	}
	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}
//	public BigDecimal getDeposit() {
//		return deposit;
//	}
//	public void setDeposit(BigDecimal deposit) {
//		this.deposit = deposit;
//	}
//	public Integer getDepositSatatus() {
//		return depositSatatus;
//	}
//	public void setDepositSatatus(Integer depositSatatus) {
//		this.depositSatatus = depositSatatus;
//	}
	public Integer getTsNumber() {
		return tsNumber;
	}
	public void setTsNumber(Integer tsNumber) {
		this.tsNumber = tsNumber;
	}
	
	
}











