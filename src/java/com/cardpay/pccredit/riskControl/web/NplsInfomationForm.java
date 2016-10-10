/**
 * 
 */
package com.cardpay.pccredit.riskControl.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 描述 ：
 * @author 张石树
 *
 * 2014-11-4 上午11:05:34
 */
public class NplsInfomationForm extends BaseForm{
	
	private String customerId;
	
	private String accountId;
	
	private String reviewResults;
	//系统自动匹配、卡中心手动添加（创建方式）
	private String createMethod;
	
	private Date verificationTime;
	
	private String verificationStatus;
	
	private String verificationType;
	
	private Date createdTime;

	private String createdBy;

	private Date modifiedTime;

	private String modifiedBy;
	
	private String customerName;
	
	private String cardId;
	
	private String accountStatus;
	
	private String accountNumber;
	
	private String overdueAmount;
	
	private String overdueTime;
	
	private String verificationAccount;
	
	private String cardNumber;
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getReviewResults() {
		return reviewResults;
	}

	public void setReviewResults(String reviewResults) {
		this.reviewResults = reviewResults;
	}

	public String getCreateMethod() {
		return createMethod;
	}

	public void setCreateMethod(String createMethod) {
		this.createMethod = createMethod;
	}

	public Date getVerificationTime() {
		return verificationTime;
	}

	public void setVerificationTime(Date verificationTime) {
		this.verificationTime = verificationTime;
	}

	public String getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getOverdueTime() {
		return overdueTime;
	}

	public void setOverdueTime(String overdueTime) {
		this.overdueTime = overdueTime;
	}

	public String getVerificationAccount() {
		return verificationAccount;
	}

	public void setVerificationAccount(String verificationAccount) {
		this.verificationAccount = verificationAccount;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}
