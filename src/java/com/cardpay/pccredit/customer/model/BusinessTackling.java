package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

public class BusinessTackling extends BusinessModel {
	private static final long serialVersionUID = 1L;
	private String cardId;//身份证号
	private String customerName;//客户姓名
	private String safeType;//保险类型
	private String safeName;//保险名称
	private String safeCompany;//入保公司
	private String productName;//产品名称
	private String interest;//利率
	private String money;//金额
	private String settle;//是否结清
	private String manager;//客户经理
	
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getSafeType() {
		return safeType;
	}
	public void setSafeType(String safeType) {
		this.safeType = safeType;
	}
	public String getSafeName() {
		return safeName;
	}
	public void setSafeName(String safeName) {
		this.safeName = safeName;
	}
	public String getSafeCompany() {
		return safeCompany;
	}
	public void setSafeCompany(String safeCompany) {
		this.safeCompany = safeCompany;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getSettle() {
		return settle;
	}
	public void setSettle(String settle) {
		this.settle = settle;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	
}
