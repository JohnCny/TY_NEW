package com.cardpay.pccredit.customer.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

public class AmountAdjustmentForm extends BaseForm{

	private String customerId;
	
	private String customerName;
	
	private String productId;
	
	private String productName;
	
	private String actualAmount;
	
	private String assessAmount;
	
	private String approvalLimit;
	
	private String originalAmount;
	
	private Date adjustAmountTime;
	
	private String approval;
	
	private String cardType;
	
	private String cardId;
	
	private String adjustmentType;
	
	//显示审核 传值
	private String approveStatus;
	
	private String reason;
	
	private String serialNumber;

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

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getAssessAmount() {
		return assessAmount;
	}

	public void setAssessAmount(String assessAmount) {
		this.assessAmount = assessAmount;
	}

	public String getApprovalLimit() {
		return approvalLimit;
	}

	public void setApprovalLimit(String approvalLimit) {
		this.approvalLimit = approvalLimit;
	}

	public String getOriginalAmount() {
		return originalAmount;
	}

	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}

	public Date getAdjustAmountTime() {
		return adjustAmountTime;
	}

	public void setAdjustAmountTime(Date adjustAmountTime) {
		this.adjustAmountTime = adjustAmountTime;
	}

	public String getApproval() {
		return approval;
	}

	public void setApproval(String approval) {
		this.approval = approval;
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

	public String getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
