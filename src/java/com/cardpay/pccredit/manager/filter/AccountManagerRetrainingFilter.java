package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-11-11下午3:19:36
 */
public class AccountManagerRetrainingFilter extends BaseQueryFilter {
	private String retrainId;
	
	private String customerManagerId;
	// 客户经理名
	private String customerManagerNmae;
	
	private String evaluationScore;
	
	private String testScore;
	
	private String implementationConclusion;
	
	private String orgId;

	public String getRetrainId() {
		return retrainId;
	}

	public void setRetrainId(String retrainId) {
		this.retrainId = retrainId;
	}

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public String getEvaluationScore() {
		return evaluationScore;
	}

	public void setEvaluationScore(String evaluationScore) {
		this.evaluationScore = evaluationScore;
	}

	public String getTestScore() {
		return testScore;
	}

	public void setTestScore(String testScore) {
		this.testScore = testScore;
	}

	public String getImplementationConclusion() {
		return implementationConclusion;
	}

	public void setImplementationConclusion(String implementationConclusion) {
		this.implementationConclusion = implementationConclusion;
	}

	public String getCustomerManagerNmae() {
		return customerManagerNmae;
	}

	public void setCustomerManagerNmae(String customerManagerNmae) {
		this.customerManagerNmae = customerManagerNmae;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
