package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 客户经理再培训计划
 * @author chenzhifang
 *
 * 2014-11-11下午3:11:58
 */
@ModelParam(table = "account_manager_retraining")
public class AccountManagerRetraining  extends BusinessModel {
	private static final long serialVersionUID = -3362643509962111286L;
	
	private String retrainId;
	
	private String customerManagerId;
	
	private String customerManagerNmae;
	
	private String evaluationScore;
	
	private String testScore;
	
	private String implementationConclusion;

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

	public String getRetrainId() {
		return retrainId;
	}

	public void setRetrainId(String retrainId) {
		this.retrainId = retrainId;
	}

	public String getCustomerManagerNmae() {
		return customerManagerNmae;
	}

	public void setCustomerManagerNmae(String customerManagerNmae) {
		this.customerManagerNmae = customerManagerNmae;
	}
}
