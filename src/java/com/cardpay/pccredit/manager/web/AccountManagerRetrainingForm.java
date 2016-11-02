package com.cardpay.pccredit.manager.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author chenzhifang
 *
 * 2014-11-11下午3:20:52
 */
public class AccountManagerRetrainingForm extends BaseForm {
	private static final long serialVersionUID = -3462216023894767497L;
	
	private String customerManagerId;
	
	private String trainingObjective;
	
	private String trainingContent;
	
	private String trainingMethod;
	
	private String trainingLocation;
	
	private Date trainingTime;
	
	private String trainingPeople;
	
	private String whetherAbandoned;
	
	private String evaluationScore;
	
	private String testScore;
	
	private String implementationConclusion;

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public String getTrainingObjective() {
		return trainingObjective;
	}

	public void setTrainingObjective(String trainingObjective) {
		this.trainingObjective = trainingObjective;
	}

	public String getTrainingContent() {
		return trainingContent;
	}

	public void setTrainingContent(String trainingContent) {
		this.trainingContent = trainingContent;
	}

	public String getTrainingMethod() {
		return trainingMethod;
	}

	public void setTrainingMethod(String trainingMethod) {
		this.trainingMethod = trainingMethod;
	}

	public String getTrainingLocation() {
		return trainingLocation;
	}

	public void setTrainingLocation(String trainingLocation) {
		this.trainingLocation = trainingLocation;
	}

	public Date getTrainingTime() {
		return trainingTime;
	}

	public void setTrainingTime(Date trainingTime) {
		this.trainingTime = trainingTime;
	}

	public String getTrainingPeople() {
		return trainingPeople;
	}

	public void setTrainingPeople(String trainingPeople) {
		this.trainingPeople = trainingPeople;
	}

	public String getWhetherAbandoned() {
		return whetherAbandoned;
	}

	public void setWhetherAbandoned(String whetherAbandoned) {
		this.whetherAbandoned = whetherAbandoned;
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
}
