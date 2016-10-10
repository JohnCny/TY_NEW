/**
 * 
 */
package com.cardpay.pccredit.riskControl.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：不良资产model
 * @author 张石树
 *
 * 2014-11-4 上午9:33:30
 */
@ModelParam(table="npls_information")
public class NplsInfomation extends BusinessModel{

	private String customerId;
	
	private String accountId;
	
	private String reviewResults;
	//系统自动匹配、卡中心手动添加（创建方式）
	private String createMethod;
	
	private Date verificationTime;
	
	private String verificationStatus;
	
	private String verificationType;
	
	private String verificationAccount;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	public String getVerificationAccount() {
		return verificationAccount;
	}

	public void setVerificationAccount(String verificationAccount) {
		this.verificationAccount = verificationAccount;
	}

}
