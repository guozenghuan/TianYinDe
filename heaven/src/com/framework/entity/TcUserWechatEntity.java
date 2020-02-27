package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 用户微信收款码
 * 
 * @author R & D
 * @email 
 * @date 2019-05-12 10:16:52
 */
public class TcUserWechatEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//用户ID
	private Long userId;
	//是否上传
	private String status;
	//微信收款码
	private String wechat;
	//更新时间
	private Date createtime;
	
	//用户昵称
	private String nickname;
	//用户头像
	private String headimgurl;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
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
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * 设置：是否上传
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：是否上传
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：微信收款码
	 */
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	/**
	 * 获取：微信收款码
	 */
	public String getWechat() {
		return wechat;
	}
	/**
	 * 设置：更新时间
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getCreatetime() {
		return createtime;
	}
}
