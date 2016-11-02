package com.cardpay.pccredit.riskControl.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-10-24下午5:54:43
 */
public class NplsInfomationFilter extends BaseQueryFilter{
	// 客户名称
	private String customerName;
	
	//产品
	private String accountId;
	
	//创建方式
	private String createMethod;
	
	//核销类型
	private String verificationType;
	
	//审核状态
	private String reviewResults;
	
	//核心核销状态
	private String verificationStatus;

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

	public String getCreateMethod() {
		return createMethod;
	}

	public void setCreateMethod(String createMethod) {
		this.createMethod = createMethod;
	}

	public String getVerificationType() {
		return verificationType;
	}

	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}

	public String getReviewResults() {
		return reviewResults;
	}

	public void setReviewResults(String reviewResults) {
		this.reviewResults = reviewResults;
	}

	public String getVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(String verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	
	
}
