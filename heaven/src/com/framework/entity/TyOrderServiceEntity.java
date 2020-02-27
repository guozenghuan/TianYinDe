package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 服务订单
 * 
 * @author R & D
 * @email 
 * @date 2019-04-16 11:46:29
 */
public class TyOrderServiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//
	private Long id;
	//用户ID
	private Long userid;
	//单号
	private String number;
	//服务费
	private BigDecimal servicePrice;
	//支付状态：0=待支付，1=已支付
	private Integer payStatus;
	//联系人
	private String name;
	//电话
	private String phone;
	//下单时间
	private Date createtime;
	//是否派单业务员：0-未派单，1-已派单
	private Integer tsUserStatus;
	//业务员ID
	private Long tsUserid;
	//服务列表：0-未确认，1-已确认
	private Integer serviceStatus;
	//订单服务总价
	private BigDecimal price;
	//价格备注
	private String note;
	//是否付款：0-待付款，1-已付款，2-线下打款
	private Integer priceStatus;
	//订单状态：0-服务中，1-业务员已确认
	private Integer status;
	//客户确定订单服务完成：0-未确认，1-已确认
	private Integer userStatus;
	//业务员评分：1-5星
	private Integer score;
	//原订单id
	private Long pid;
	//报价单 审核状态  0 待审核  1 通过 2不通过  3其他
	private Integer auditState;
	//业务员评价
	private String evaluate;
	//业务员提成
	private BigDecimal commission;
	//fwz
	private Integer fwz;
	//订金
	private String deposit;
	
	//是否收取定金 0不收取 1收取
	private int isCollect;
	//定金支付状态 0未支付 1已支付
	private int depStu;
	
	

	public int getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}
	public int getDepStu() {
		return depStu;
	}
	public void setDepStu(int depStu) {
		this.depStu = depStu;
	}



	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public Integer getFwz() {
		return fwz;
	}
	public void setFwz(Integer fwz) {
		this.fwz = fwz;
	}
	public BigDecimal getCommission() {	
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
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
	 * 设置：用户ID
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserid() {
		return userid;
	}
	/**
	 * 设置：单号
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 获取：单号
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 设置：服务费
	 */
	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}
	/**
	 * 获取：服务费
	 */
	public BigDecimal getServicePrice() {
		return servicePrice;
	}
	/**
	 * 设置：支付状态：0=待支付，1=已支付
	 */
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	/**
	 * 获取：支付状态：0=待支付，1=已支付
	 */
	public Integer getPayStatus() {
		return payStatus;
	}
	/**
	 * 设置：联系人
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：联系人
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：下单时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：下单时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * 设置：是否派单业务员：0-未派单，1-已派单
	 */
	public void setTsUserStatus(Integer tsUserStatus) {
		this.tsUserStatus = tsUserStatus;
	}
	/**
	 * 获取：是否派单业务员：0-未派单，1-已派单
	 */
	public Integer getTsUserStatus() {
		return tsUserStatus;
	}
	/**
	 * 设置：业务员ID
	 */
	public void setTsUserid(Long tsUserid) {
		this.tsUserid = tsUserid;
	}
	/**
	 * 获取：业务员ID
	 */
	public Long getTsUserid() {
		return tsUserid;
	}
	/**
	 * 设置：服务列表：0-未确认，1-已确认
	 */
	public void setServiceStatus(Integer serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	/**
	 * 获取：服务列表：0-未确认，1-已确认
	 */
	public Integer getServiceStatus() {
		return serviceStatus;
	}
	/**
	 * 设置：订单服务总价
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：订单服务总价
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * 设置：价格备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：价格备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：是否付款：0-待付款，1-已付款
	 */
	public void setPriceStatus(Integer priceStatus) {
		this.priceStatus = priceStatus;
	}
	/**
	 * 获取：是否付款：0-待付款，1-已付款
	 */
	public Integer getPriceStatus() {
		return priceStatus;
	}
	/**
	 * 设置：订单状态：0-服务中，1-服务结束
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：订单状态：0-服务中，1-服务结束
	 */
	public Integer getStatus() {
		return status;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 设置：业务员评分：1-5星
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	/**
	 * 获取：业务员评分：1-5星
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * 设置：业务员评价
	 */
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	/**
	 * 获取：业务员评价
	 */
	public String getEvaluate() {
		return evaluate;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Integer getAuditState() {
		return auditState;
	}
	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}
}
