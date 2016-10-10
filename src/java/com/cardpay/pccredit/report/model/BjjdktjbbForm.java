package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 被拒绝贷款统计
 * @author songchen
 */
public class BjjdktjbbForm extends BaseForm {

	private static final long serialVersionUID = -8860955438817002631L;


	
	private String cname;                //客户名称
	private String cardtype;     		 //客户证件号码
	private String prodName;     		 //所属产品
	private String appdate;       		 //申请日期
	private String reqlmt;        		 //申请金额
	private String refuseDate;  		 //拒绝日期
	private String refuseReason;		 //拒绝原因
	private String busimanager;  		 //所属客户经理
	private String name;            	 //所属机构
	 
	
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


	public String getAppdate() {
		return appdate;
	}


	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}


	public String getReqlmt() {
		return reqlmt;
	}


	public void setReqlmt(String reqlmt) {
		this.reqlmt = reqlmt;
	}


	public String getRefuseDate() {
		return refuseDate;
	}


	public void setRefuseDate(String refuseDate) {
		this.refuseDate = refuseDate;
	}


	public String getRefuseReason() {
		return refuseReason;
	}


	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
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