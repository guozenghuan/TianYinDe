package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 商品订单
 * 
 * @author R & D
 * @email 
 * @date 2019-03-30 22:55:09
 */
public class TyOrderCommodityEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户id
	private Long userId;
	//用户地址id
	private Long addressId;
	//商品id
	private Long commodityId;
	//商品标图
	private String commodityImage;
	//商品名称
	private String commodityTitle;
	//商品价格
	private BigDecimal commodityPrice;
	//单号
	private String number;
	//支付状态：0=待支付，1=已支付
	private Integer payStatus;
	//发货时间
	private Date expressTime;
	//发货状态：0=代发货，1=已发货
	private Integer expressStatus;
	//快递名称
	private String expressName;
	//快递单号
	private String expressNumber;
	//联系人
	private String name;
	//联系电话
	private String phone;
	//收获地址
	private String addres;
	//订单状态：0，未完成，1=已完成，2=已退款
	private Integer status;
	//下单时间
	private Date createtime;

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
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：用户地址id
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	/**
	 * 获取：用户地址id
	 */
	public Long getAddressId() {
		return addressId;
	}
	/**
	 * 设置：商品id
	 */
	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}
	/**
	 * 获取：商品id
	 */
	public Long getCommodityId() {
		return commodityId;
	}
	/**
	 * 设置：商品标图
	 */
	public void setCommodityImage(String commodityImage) {
		this.commodityImage = commodityImage;
	}
	/**
	 * 获取：商品标图
	 */
	public String getCommodityImage() {
		return commodityImage;
	}
	/**
	 * 设置：商品名称
	 */
	public void setCommodityTitle(String commodityTitle) {
		this.commodityTitle = commodityTitle;
	}
	/**
	 * 获取：商品名称
	 */
	public String getCommodityTitle() {
		return commodityTitle;
	}
	/**
	 * 设置：商品价格
	 */
	public void setCommodityPrice(BigDecimal commodityPrice) {
		this.commodityPrice = commodityPrice;
	}
	/**
	 * 获取：商品价格
	 */
	public BigDecimal getCommodityPrice() {
		return commodityPrice;
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
	 * 设置：发货时间
	 */
	public void setExpressTime(Date expressTime) {
		this.expressTime = expressTime;
	}
	/**
	 * 获取：发货时间
	 */
	public Date getExpressTime() {
		return expressTime;
	}
	/**
	 * 设置：发货状态：0=代发货，1=已发货
	 */
	public void setExpressStatus(Integer expressStatus) {
		this.expressStatus = expressStatus;
	}
	/**
	 * 获取：发货状态：0=代发货，1=已发货
	 */
	public Integer getExpressStatus() {
		return expressStatus;
	}
	/**
	 * 设置：快递名称
	 */
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	/**
	 * 获取：快递名称
	 */
	public String getExpressName() {
		return expressName;
	}
	/**
	 * 设置：快递单号
	 */
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	/**
	 * 获取：快递单号
	 */
	public String getExpressNumber() {
		return expressNumber;
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
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：收获地址
	 */
	public void setAddres(String addres) {
		this.addres = addres;
	}
	/**
	 * 获取：收获地址
	 */
	public String getAddres() {
		return addres;
	}
	/**
	 * 设置：订单状态：0，未完成，1=已完成，2=已退款
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：订单状态：0，未完成，1=已完成，2=已退款
	 */
	public Integer getStatus() {
		return status;
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
}
