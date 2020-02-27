package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



/**
 * 服务
 * 
 * @author R & D
 * @email 
 * @date 2019-03-24 23:01:32
 */
public class TyServiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//父级id
	private Long parentId;
	//类型：liucheng=流程，fuwu=服务
	private String type;
	//标题
	private String name;
	//业务员价格
	private BigDecimal unitPrice;
	//客户价格
	private BigDecimal costPrice;
	//内容
	private String text;
	
	private Integer sort; 
	
	private TyServiceEntity children; //存放子节点
	
	private List<TyServiceEntity> childrenFw; //存放子节点
	
	public List<TyServiceEntity> getChildrenFw() {
		return childrenFw;
	}
	public void setChildrenFw(List<TyServiceEntity> childrenFw) {
		this.childrenFw = childrenFw;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public TyServiceEntity getChildren() {
		return children;
	}
	public void setChildren(TyServiceEntity children) {
		this.children = children;
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
	 * 设置：父级id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父级id
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * 设置：类型：liucheng=流程，fuwu=服务
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：类型：liucheng=流程，fuwu=服务
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置：标题
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：标题
	 */
	public String getName() {
		return name;
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
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
}
