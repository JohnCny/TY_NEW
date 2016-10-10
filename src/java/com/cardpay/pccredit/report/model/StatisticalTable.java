package com.cardpay.pccredit.report.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table="statistical_table")
public class StatisticalTable extends BaseModel{
	
	private String customerManagerId;
	
	private Integer sendCardNumber;
	
	private Integer acceptCardNumber;
	
	private Integer activateCardNumber;
	
	private String creditExtensionAmount;
	
	private String activateExtensionAmount;
	
	private String overdraftPrincipal;
	
	private String overdraftAmount;
	
	private String overdraftPrincipalAverage;
	
	private String overdraftAmountAverage;
	
	private String badOverdraftPrincipal;
	
	private Integer repayPrincipalNumber;
	
	private String repaymentPrincipal;
	
	private Integer increasedOverdraftNumber;
	
	private String lineExtraction;
	
	private Integer overdraftPrincipalNumber;
	
	private Date createdDate;
	
	private String productId;

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public Integer getSendCardNumber() {
		return sendCardNumber;
	}

	public void setSendCardNumber(Integer sendCardNumber) {
		this.sendCardNumber = sendCardNumber;
	}

	public Integer getAcceptCardNumber() {
		return acceptCardNumber;
	}

	public void setAcceptCardNumber(Integer acceptCardNumber) {
		this.acceptCardNumber = acceptCardNumber;
	}

	public Integer getActivateCardNumber() {
		return activateCardNumber;
	}

	public void setActivateCardNumber(Integer activateCardNumber) {
		this.activateCardNumber = activateCardNumber;
	}

	public String getCreditExtensionAmount() {
		return creditExtensionAmount;
	}

	public void setCreditExtensionAmount(String creditExtensionAmount) {
		this.creditExtensionAmount = creditExtensionAmount;
	}

	public String getActivateExtensionAmount() {
		return activateExtensionAmount;
	}

	public void setActivateExtensionAmount(String activateExtensionAmount) {
		this.activateExtensionAmount = activateExtensionAmount;
	}

	public String getOverdraftPrincipal() {
		return overdraftPrincipal;
	}

	public void setOverdraftPrincipal(String overdraftPrincipal) {
		this.overdraftPrincipal = overdraftPrincipal;
	}

	public String getOverdraftAmount() {
		return overdraftAmount;
	}

	public void setOverdraftAmount(String overdraftAmount) {
		this.overdraftAmount = overdraftAmount;
	}

	public String getOverdraftPrincipalAverage() {
		return overdraftPrincipalAverage;
	}

	public void setOverdraftPrincipalAverage(String overdraftPrincipalAverage) {
		this.overdraftPrincipalAverage = overdraftPrincipalAverage;
	}

	public String getOverdraftAmountAverage() {
		return overdraftAmountAverage;
	}

	public void setOverdraftAmountAverage(String overdraftAmountAverage) {
		this.overdraftAmountAverage = overdraftAmountAverage;
	}

	public String getBadOverdraftPrincipal() {
		return badOverdraftPrincipal;
	}

	public void setBadOverdraftPrincipal(String badOverdraftPrincipal) {
		this.badOverdraftPrincipal = badOverdraftPrincipal;
	}

	public Integer getRepayPrincipalNumber() {
		return repayPrincipalNumber;
	}

	public void setRepayPrincipalNumber(Integer repayPrincipalNumber) {
		this.repayPrincipalNumber = repayPrincipalNumber;
	}

	public String getRepaymentPrincipal() {
		return repaymentPrincipal;
	}

	public void setRepaymentPrincipal(String repaymentPrincipal) {
		this.repaymentPrincipal = repaymentPrincipal;
	}

	public Integer getIncreasedOverdraftNumber() {
		return increasedOverdraftNumber;
	}

	public void setIncreasedOverdraftNumber(Integer increasedOverdraftNumber) {
		this.increasedOverdraftNumber = increasedOverdraftNumber;
	}

	public String getLineExtraction() {
		return lineExtraction;
	}

	public void setLineExtraction(String lineExtraction) {
		this.lineExtraction = lineExtraction;
	}

	public Integer getOverdraftPrincipalNumber() {
		return overdraftPrincipalNumber;
	}

	public void setOverdraftPrincipalNumber(Integer overdraftPrincipalNumber) {
		this.overdraftPrincipalNumber = overdraftPrincipalNumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
