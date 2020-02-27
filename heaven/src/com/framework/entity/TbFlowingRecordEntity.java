package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 用户流水
 * 
 * @author R & D
 * @email 
 * @date 2019-03-30 14:52:39
 */
public class TbFlowingRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户id
	private Long userId;
	//类型标识符，礼金=cash_gift，赠送礼物=give_gift，提现=cash_withdrawal，购物=shopping，服务费用=service，退款=refund
	private String type;
	//类型标识符，礼金=cash_gift，赠送礼物=give_gift，提现=cash_withdrawal，购物=shopping，服务费用=service，退款=refund
	private String typeName;
	//(符号)收入：+，支出：-
	private String symbol;
	//金额
	private BigDecimal money;
	//流水详情
	private String note;
	//时间
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
	 * 设置：类型标识符，礼金=cash_gift，提现=cash_withdrawal，购物=shopping，服务费用=service，退款=refund
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型标识符，礼金=cash_gift，提现=cash_withdrawal，购物=shopping，服务费用=service，退款=refund
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：类型名称  ，礼金=cash_gift，提现=cash_withdrawal，购物=shopping，退款=refund
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * 获取：类型名称  ，礼金=cash_gift，提现=cash_withdrawal，购物=shopping，退款=refund
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置：(符号)收入：+，支出：-
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	/**
	 * 获取：(符号)收入：+，支出：-
	 */
	public String getSymbol() {
		return symbol;
	}
	/**
	 * 设置：金额
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	/**
	 * 获取：金额
	 */
	public BigDecimal getMoney() {
		return money;
	}
	/**
	 * 设置：流水详情
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：流水详情
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
