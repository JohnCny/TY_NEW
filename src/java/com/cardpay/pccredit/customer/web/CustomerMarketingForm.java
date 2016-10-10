package com.cardpay.pccredit.customer.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;


/**
 * CustomerMarketingForm类的描述
 * 
 * @author 王海东
 * @created on 2014-10-24
 * 
 * @version $Id:$
 */
public class CustomerMarketingForm extends BaseForm{
	private static final long serialVersionUID = 1L;
	private String id;
	private String createWay;
	private String customerId;
	private String productId;
	private String implementationObjective;
	private String endResult;
	private String marketingTime;
	private String marketingMethod;
	private String createUser;
	private Date createDate;
	private String modifyUser;
	private Date modifyDate;
	private String customerManagerId;
	private String marketingStartTime;
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getMarketingStartTime() {
		return marketingStartTime;
	}
	public void setMarketingStartTime(String marketingStartTime) {
		this.marketingStartTime = marketingStartTime;
	}
	
	
}