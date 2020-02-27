package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author R & D
 * @email 
 * @date 2019-03-23 14:20:58
 */
public class TyCommodityEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//分类
	private Long categotyId;
	//主图
	private String mainImage;
	//轮播图
	private String secondImages;
	//标题
	private String title;
	//价格
	private BigDecimal price;
	//内容
	private String text;
	//商品状态：0-正常，1-下架
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
	 * 设置：主图
	 */
	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}
	/**
	 * 获取：主图
	 */
	public String getMainImage() {
		return mainImage;
	}
	/**
	 * 设置：轮播图
	 */
	public void setSecondImages(String secondImages) {
		this.secondImages = secondImages;
	}
	/**
	 * 获取：轮播图
	 */
	public String getSecondImages() {
		return secondImages;
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
	 * 设置：价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	/**
	 * 获取：价格
	 */
	public BigDecimal getPrice() {
		return price;
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
	 * 设置：商品状态：0-正常，1-下架
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：商品状态：0-正常，1-下架
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
	public Long getCategotyId() {
		return categotyId;
	}
	public void setCategotyId(Long categotyId) {
		this.categotyId = categotyId;
	}
	
}
