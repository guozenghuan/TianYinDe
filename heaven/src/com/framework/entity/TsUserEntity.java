package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 业务员账号
 * 
 * @author R & D
 * @email 
 * @date 2019-04-18 14:36:29
 */
public class TsUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//公司id
	private Long companyId;
	//头像地址
	private String portraitUrl;
	//姓名
	private String name;
	//手机号
	private String phone;
	//业务员账号
	private String account;
	//业务员密码
	private String password;
	//累计业绩
	private BigDecimal achievement;
	//综合评分
	private BigDecimal score;
	//创建时间
	private Date createtime;
	private String wechatName;
	private String wechatHead;
	private String wechatOpenid;
	
	//公司名称
	private String company;
	
	//累计单量
	private Integer orderCount;
	
	public String getWechatName() {
		return wechatName;
	}
	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}
	public String getWechatHead() {
		return wechatHead;
	}
	public void setWechatHead(String wechatHead) {
		this.wechatHead = wechatHead;
	}
	public String getWechatOpenid() {
		return wechatOpenid;
	}
	public void setWechatOpenid(String wechatOpenid) {
		this.wechatOpenid = wechatOpenid;
	}
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
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
	 * 设置：公司id
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	/**
	 * 获取：公司id
	 */
	public Long getCompanyId() {
		return companyId;
	}
	/**
	 * 设置：头像地址
	 */
	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}
	/**
	 * 获取：头像地址
	 */
	public String getPortraitUrl() {
		return portraitUrl;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：手机号
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：业务员账号
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取：业务员账号
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置：业务员密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：业务员密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：累计业绩
	 */
	public void setAchievement(BigDecimal achievement) {
		this.achievement = achievement;
	}
	/**
	 * 获取：累计业绩
	 */
	public BigDecimal getAchievement() {
		return achievement;
	}
	/**
	 * 设置：综合评分
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}
	/**
	 * 获取：综合评分
	 */
	public BigDecimal getScore() {
		return score;
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
