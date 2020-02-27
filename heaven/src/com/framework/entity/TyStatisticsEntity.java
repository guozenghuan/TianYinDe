package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 平台信息统计
 * 
 * @author R & D
 * @email 
 * @date 2019-05-14 18:02:32
 */
public class TyStatisticsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//今日新增用户
	private Integer todayUser;
	//平台总用户
	private Integer allUser;
	//今日商品下单
	private Integer todayCommodity;
	//累计商品单量
	private Integer allCommodity;
	//今日服务下单
	private Integer todayService;
	//累计服务下单
	private Integer allService;
	//今日预约服务费
	private BigDecimal todayServiceMoney;
	//累计预约服务费
	private BigDecimal allServiceMoney;
	//今日服务套餐费
	private BigDecimal todayServicePrice;
	//累计服务套餐费
	private BigDecimal allServicePrice;
	//今日赠送平台礼物
	private BigDecimal todayGift;
	//累计赠送平台礼物
	private BigDecimal allGift;
	//今日收到奠金
	private BigDecimal todayCash;
	//累计收到奠金
	private BigDecimal allCash;
	//累计行政利润
	private BigDecimal totalAProfit;
	
	public BigDecimal getTotalAProfit() {
		return totalAProfit;
	}
	public void setTotalAProfit(BigDecimal totalAProfit) {
		this.totalAProfit = totalAProfit;
	}
	private Date createtime;
	
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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
	 * 设置：今日新增用户
	 */
	public void setTodayUser(Integer todayUser) {
		this.todayUser = todayUser;
	}
	/**
	 * 获取：今日新增用户
	 */
	public Integer getTodayUser() {
		return todayUser;
	}
	/**
	 * 设置：平台总用户
	 */
	public void setAllUser(Integer allUser) {
		this.allUser = allUser;
	}
	/**
	 * 获取：平台总用户
	 */
	public Integer getAllUser() {
		return allUser;
	}
	/**
	 * 设置：今日商品下单
	 */
	public void setTodayCommodity(Integer todayCommodity) {
		this.todayCommodity = todayCommodity;
	}
	/**
	 * 获取：今日商品下单
	 */
	public Integer getTodayCommodity() {
		return todayCommodity;
	}
	/**
	 * 设置：累计商品单量
	 */
	public void setAllCommodity(Integer allCommodity) {
		this.allCommodity = allCommodity;
	}
	/**
	 * 获取：累计商品单量
	 */
	public Integer getAllCommodity() {
		return allCommodity;
	}
	/**
	 * 设置：今日服务下单
	 */
	public void setTodayService(Integer todayService) {
		this.todayService = todayService;
	}
	/**
	 * 获取：今日服务下单
	 */
	public Integer getTodayService() {
		return todayService;
	}
	/**
	 * 设置：累计服务下单
	 */
	public void setAllService(Integer allService) {
		this.allService = allService;
	}
	/**
	 * 获取：累计服务下单
	 */
	public Integer getAllService() {
		return allService;
	}
	/**
	 * 设置：今日预约服务费
	 */
	public void setTodayServiceMoney(BigDecimal todayServiceMoney) {
		this.todayServiceMoney = todayServiceMoney;
	}
	/**
	 * 获取：今日预约服务费
	 */
	public BigDecimal getTodayServiceMoney() {
		return todayServiceMoney;
	}
	/**
	 * 设置：累计预约服务费
	 */
	public void setAllServiceMoney(BigDecimal allServiceMoney) {
		this.allServiceMoney = allServiceMoney;
	}
	/**
	 * 获取：累计预约服务费
	 */
	public BigDecimal getAllServiceMoney() {
		return allServiceMoney;
	}
	/**
	 * 设置：今日服务套餐费
	 */
	public void setTodayServicePrice(BigDecimal todayServicePrice) {
		this.todayServicePrice = todayServicePrice;
	}
	/**
	 * 获取：今日服务套餐费
	 */
	public BigDecimal getTodayServicePrice() {
		return todayServicePrice;
	}
	/**
	 * 设置：累计服务套餐费
	 */
	public void setAllServicePrice(BigDecimal allServicePrice) {
		this.allServicePrice = allServicePrice;
	}
	/**
	 * 获取：累计服务套餐费
	 */
	public BigDecimal getAllServicePrice() {
		return allServicePrice;
	}
	/**
	 * 设置：今日赠送平台礼物
	 */
	public void setTodayGift(BigDecimal todayGift) {
		this.todayGift = todayGift;
	}
	/**
	 * 获取：今日赠送平台礼物
	 */
	public BigDecimal getTodayGift() {
		return todayGift;
	}
	/**
	 * 设置：累计赠送平台礼物
	 */
	public void setAllGift(BigDecimal allGift) {
		this.allGift = allGift;
	}
	/**
	 * 获取：累计赠送平台礼物
	 */
	public BigDecimal getAllGift() {
		return allGift;
	}
	/**
	 * 设置：今日收到奠金
	 */
	public void setTodayCash(BigDecimal todayCash) {
		this.todayCash = todayCash;
	}
	/**
	 * 获取：今日收到奠金
	 */
	public BigDecimal getTodayCash() {
		return todayCash;
	}
	/**
	 * 设置：累计收到奠金
	 */
	public void setAllCash(BigDecimal allCash) {
		this.allCash = allCash;
	}
	/**
	 * 获取：累计收到奠金
	 */
	public BigDecimal getAllCash() {
		return allCash;
	}
}
