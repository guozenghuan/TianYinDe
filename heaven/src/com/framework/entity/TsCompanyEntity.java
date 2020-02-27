package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 公司信息
 * 
 * @author R & D
 * @email 
 * @date 2019-03-20 13:52:39
 */
public class TsCompanyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//公司名称
	private String name;
	//备注信息
	private String note;
	//创建时间
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
	 * 设置：公司名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：公司名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：备注信息
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：备注信息
	 */
	public String getNote() {
		return note;
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
