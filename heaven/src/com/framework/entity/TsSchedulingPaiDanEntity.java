package com.framework.entity;

import java.io.Serializable;
import java.util.Date;

public class TsSchedulingPaiDanEntity implements Serializable{
	private static final long serialVersionUID = -6273284419928272367L;
	
	private Long id;
	//业务员Id
	private Long tsUserId;
	//业务员当天派单量
	private Integer number;
	//值班日期
	private String date;
	//创建时间
	private String createtime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTsUserId() {
		return tsUserId;
	}
	public void setTsUserId(Long tsUserId) {
		this.tsUserId = tsUserId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	
	
    
}
