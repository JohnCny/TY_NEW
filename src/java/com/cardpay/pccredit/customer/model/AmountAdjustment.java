package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table="amount_adjustment")
public class AmountAdjustment extends BusinessModel{

	private String customerId;
	
	private String productId;
	
	private String actualAmount;
	
	private String assessAmount;
	
	private String approvalLimit;
	
	private String originalAmount;
	
	private Date adjustAmountTime;
	
	private String approval;
	
	private String adjustmentType;

	private String serialNumber;
	
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

	public String getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
}
