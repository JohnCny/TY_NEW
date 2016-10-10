package com.cardpay.pccredit.report.model;

import java.math.BigDecimal;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 已发放贷款统计
 * @author songchen
 */
public class YffdktjbbForm extends BaseForm {

	private static final long serialVersionUID = -8860955438817002631L;

	private String cname;         //客户名称
	private String cardtype;      //客户证件号码
	private String prodName;      //所属产品
	private String money;         //贷款金额
	private String baserate;      //利率
	private String interest;      //放款日期
	private String state;         //贷款状态
	private String busimanager;   //所属客户经理
	private String name;          //所属机构
	 
	private String accountstate;
	public String getAccountstate() {
		return accountstate;
	}


	public void setAccountstate(String accountstate) {
		this.accountstate = accountstate;
	}


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


	public String getInterest() {
		return interest;
	}


	public void setInterest(String interest) {
		this.interest = interest;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
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