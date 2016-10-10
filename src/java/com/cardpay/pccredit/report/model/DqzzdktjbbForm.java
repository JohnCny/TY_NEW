package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 到期终止贷款统计
 * @author songchen
 */
public class DqzzdktjbbForm extends BaseForm {

	private static final long serialVersionUID = -8860955438817002631L;

	 private String cname;           //客户名称
	 private String cardtype;        //客户证件号码
	 private String prodName;        //所属产品
	 private String loandate;        //放款日期
	 private String enddate;         //到期日期
	 private String money;           //贷款金额
	 private String baserate;        //利率
	 private String qs;           	 //期数
	 private String bjzh;            //本金总额
	 private String lxzh;            //利息总额
	 private String busimanager;     //所属客户经理
	 private String name;            //所属机构
	 
	
	 private String rowIndex;//序号


	public String getCname() {
		return cname;
	}


	public void setCname(String cname) {
		this.cname = cname;
	}


	public String getCardtype() {
		return cardtype;
	}


	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}


	public String getProdName() {
		return prodName;
	}


	public void setProdName(String prodName) {
		this.prodName = prodName;
	}


	public String getLoandate() {
		return loandate;
	}


	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}


	public String getEnddate() {
		return enddate;
	}


	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}


	public String getMoney() {
		return money;
	}


	public void setMoney(String money) {
		this.money = money;
	}


	public String getBaserate() {
		return baserate;
	}


	public void setBaserate(String baserate) {
		this.baserate = baserate;
	}


	public String getQs() {
		return qs;
	}


	public void setQs(String qs) {
		this.qs = qs;
	}


	public String getBjzh() {
		return bjzh;
	}


	public void setBjzh(String bjzh) {
		this.bjzh = bjzh;
	}


	public String getLxzh() {
		return lxzh;
	}


	public void setLxzh(String lxzh) {
		this.lxzh = lxzh;
	}


	public String getBusimanager() {
		return busimanager;
	}


	public void setBusimanager(String busimanager) {
		this.busimanager = busimanager;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRowIndex() {
		return rowIndex;
	}


	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}


	 
	
	
}