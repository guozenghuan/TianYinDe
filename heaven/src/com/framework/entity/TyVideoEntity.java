package com.framework.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 视频展示
 * 
 * @author R & D
 * @email 
 * @date 2019-04-09 18:41:09
 */
public class TyVideoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//视频标题
	private String title;
	//视频详情
	private String note;
	//封面图路径
	private String image;
	//视频路径
	private String path;
	//排序-默认顺序和id一样
	private Long sort;
	//状态：0-显示，1-隐藏
	private Integer status;
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
	 * 设置：视频标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：视频标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：视频详情
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：视频详情
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：封面图路径
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：封面图路径
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置：视频路径
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：视频路径
	 */
	public String getPath() {
		return path;
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
	 * 设置：状态：0-显示，1-隐藏
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态：0-显示，1-隐藏
	 */
	public Integer getStatus() {
		return status;
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
