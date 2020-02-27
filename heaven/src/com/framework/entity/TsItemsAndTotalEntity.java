package com.framework.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TsItemsAndTotalEntity implements Serializable{
	private static final long serialVersionUID = 902747705686578244L;
	
	private Long id;
	//业务员Id
	private Long tsUserId;
	//单号
	private String number;
	//包单总金
	private BigDecimal totalPrice;
	//小计: 
	//公司成本
	private BigDecimal costTotal;
	//现结
	private BigDecimal cashTotal;
	//佣金
	private BigDecimal comm;
	//佣金率
	private BigDecimal comml;
	//信息源:
	//提成 率
	private BigDecimal tcx;
	//公司成本
	private BigDecimal costX;
	//经办人：
	//提成  
	private BigDecimal tcj;
	//公司成本
	private BigDecimal costJ;
	//现结 合计
	private BigDecimal cashXJ;
	
	//毛利润
	private BigDecimal mly;
	//毛利润率
	private BigDecimal mlly;
	//净利润
	private BigDecimal jly;
	//净利润率
	private BigDecimal jll;
	//后勤人员提成  
	private BigDecimal hjrytc;
	//对账实收 
	private BigDecimal check;
	//成本 
	private BigDecimal costAll;
	//公司收入 
	private BigDecimal companyIncome;
	//(行政审核)审核人员
	private String audit;
	//（财务）审核状态
	private String finance;
	//制单日期
	private String createtime;
	//提成基率
	private BigDecimal b;
	//提成利率
	private BigDecimal commission;
	//行政利润
	private BigDecimal adProfit;
	//业务员提成
	private BigDecimal salCommission;
	
	
	
	
	
//	//司仪提成：
//	//单价
//	private BigDecimal price;
//	//公司成本
//	private BigDecimal costT; 
//	//现结
//	private BigDecimal cashT;
//	//操作员
//	private String czy;
//	//规费：
//	//包/不包 
//	//守灵/安/中/大/永
//	//公司成本
//	private BigDecimal costW;
//	//现结
//	private BigDecimal cashW;
//	//挽
//	private BigDecimal wl;
//	//棺
//	private BigDecimal coffin;
//	//罐
//	private BigDecimal pot;
//	//花
//	private BigDecimal bloom;
//	//其他
//	private BigDecimal other;
	

	
	
	public BigDecimal getB() {
		return b;
	}
	public void setB(BigDecimal b) {
		this.b = b;
	}
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public BigDecimal getAdProfit() {
		return adProfit;
	}
	public void setAdProfit(BigDecimal adProfit) {
		this.adProfit = adProfit;
	}
	public BigDecimal getSalCommission() {
		return salCommission;
	}
	public void setSalCommission(BigDecimal salCommission) {
		this.salCommission = salCommission;
	}
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public BigDecimal getCostTotal() {
		return costTotal;
	}
	public void setCostTotal(BigDecimal costTotal) {
		this.costTotal = costTotal;
	}
	public BigDecimal getCashTotal() {
		return cashTotal;
	}
	public void setCashTotal(BigDecimal cashTotal) {
		this.cashTotal = cashTotal;
	}
	public BigDecimal getComm() {
		return comm;
	}
	public void setComm(BigDecimal comm) {
		this.comm = comm;
	}
	public BigDecimal getComml() {
		return comml;
	}
	public void setComml(BigDecimal comml) {
		this.comml = comml;
	}
	public BigDecimal getTcx() {
		return tcx;
	}
	public void setTcx(BigDecimal tcx) {
		this.tcx = tcx;
	}
	public BigDecimal getCostX() {
		return costX;
	}
	public void setCostX(BigDecimal costX) {
		this.costX = costX;
	}
	public BigDecimal getTcj() {
		return tcj;
	}
	public void setTcj(BigDecimal tcj) {
		this.tcj = tcj;
	}
	public BigDecimal getCostJ() {
		return costJ;
	}
	public void setCostJ(BigDecimal costJ) {
		this.costJ = costJ;
	}
	public BigDecimal getCashXJ() {
		return cashXJ;
	}
	public void setCashXJ(BigDecimal cashXJ) {
		this.cashXJ = cashXJ;
	}
	public BigDecimal getMly() {
		return mly;
	}
	public void setMly(BigDecimal mly) {
		this.mly = mly;
	}
	public BigDecimal getMlly() {
		return mlly;
	}
	public void setMlly(BigDecimal mlly) {
		this.mlly = mlly;
	}
	public BigDecimal getJly() {
		return jly;
	}
	public void setJly(BigDecimal jly) {
		this.jly = jly;
	}
	public BigDecimal getJll() {
		return jll;
	}
	public void setJll(BigDecimal jll) {
		this.jll = jll;
	}
	public BigDecimal getHjrytc() {
		return hjrytc;
	}
	public void setHjrytc(BigDecimal hjrytc) {
		this.hjrytc = hjrytc;
	}
	public BigDecimal getCheck() {
		return check;
	}
	public void setCheck(BigDecimal check) {
		this.check = check;
	}
	public BigDecimal getCostAll() {
		return costAll;
	}
	public void setCostAll(BigDecimal costAll) {
		this.costAll = costAll;
	}
	public BigDecimal getCompanyIncome() {
		return companyIncome;
	}
	public void setCompanyIncome(BigDecimal companyIncome) {
		this.companyIncome = companyIncome;
	}
	
	public String getAudit() {
		return audit;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	public String getFinance() {
		return finance;
	}
	public void setFinance(String finance) {
		this.finance = finance;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

}














