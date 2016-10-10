package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;

public class CustomerMarketingWeb extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String id;
	private String customerName;
	private String productId;
	private String productName;
	private String createWay;
	private String implementationObjective;
	private String endResult;
	private String marketingTime;
	private String marketingMethod;
	private Date marketingEndtime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	public String getImplementationObjective() {
		return implementationObjective;
	}
	public void setImplementationObjective(String implementationObjective) {
		this.implementationObjective = implementationObjective;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public String getMarketingTime() {
		return marketingTime;
	}
	public void setMarketingTime(String marketingTime) {
		this.marketingTime = marketingTime;
	}
	public String getMarketingMethod() {
		return marketingMethod;
	}
	public void setMarketingMethod(String marketingMethod) {
		this.marketingMethod = marketingMethod;
	}
	public Date getMarketingEndtime() {
		return marketingEndtime;
	}
	public void setMarketingEndtime(Date marketingEndtime) {
		this.marketingEndtime = marketingEndtime;
	}
	
}
