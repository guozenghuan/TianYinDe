package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 平台帮助
 * 
 * @author R & D
 * @email 
 * @date 2019-04-10 14:46:55
 */
public class TyHelpEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//标题
	private String title;
	//内容
	private String text;
	//排序-默认顺序和id一样
	private Long sort;
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
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：内容
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * 获取：内容
	 */
	public String getText() {
		return text;
	}
	/**
	 * 设置：排序-默认顺序和id一样
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序-默认顺序和id一样
	 */
	public Long getSort() {
		return sort;
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
