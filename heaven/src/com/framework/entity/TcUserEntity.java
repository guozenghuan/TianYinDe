package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 客户信息
 * 
 * @author R & D
 * @email 
 * @date 2019-04-07 14:18:10
 */
public class TcUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户openid
	private String openid;
	//用户昵称
	private String nickname;
	//用户头像
	private String headimgurl;
	//钱包
	private BigDecimal wallet;
	//创建时间
	private Date createtime;
	//状态：0-正常，1-冻结
	private Integer status;

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
	 * 设置：用户openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：用户openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：用户昵称
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * 获取：用户昵称
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * 设置：用户头像
	 */
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	/**
	 * 获取：用户头像
	 */
	public String getHeadimgurl() {
		return headimgurl;
	}
	/**
	 * 设置：钱包
	 */
	public void setWallet(BigDecimal wallet) {
		this.wallet = wallet;
	}
	/**
	 * 获取：钱包
	 */
	public BigDecimal getWallet() {
		return wallet;
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
	/**
	 * 设置：状态：0-正常，1-冻结
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0-正常，1-冻结
	 */
	public Integer getStatus() {
		return status;
	}
}
