package com.cardpay.pccredit.customer.web;

import com.wicresoft.jrad.base.database.model.BaseModel;


public class CustomerAdjustmentQuotaForm extends BaseModel{

	private String cardType;
	
	private String cardId;
	
	private String actualAmount;
	
	private String appQuotaAmount;
	
	private String customerId;
	
	private String customerName;
	
	private String adjustmentType;

	private String productId;
	
	private String productName;
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getAppQuotaAmount() {
		return appQuotaAmount;
	}

	public void setAppQuotaAmount(String appQuotaAmount) {
		this.appQuotaAmount = appQuotaAmount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
