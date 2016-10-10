/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author shaoming
 *
 * 2014年11月19日   上午11:30:32
 */
@ModelParam(table = "information_officer_evaluation",generator=IDType.uuid32)
public class InformationOfficerEvaluate extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messengerId;
	private String researcherAttitude;
	private String researcherTimely;
	private String researcherAccuracy;
	private String evaluationPeople;
	public String getMessengerId() {
		return messengerId;
	}
	public void setMessengerId(String messengerId) {
		this.messengerId = messengerId;
	}
	public String getResearcherAttitude() {
		return researcherAttitude;
	}
	public void setResearcherAttitude(String researcherAttitude) {
		this.researcherAttitude = researcherAttitude;
	}
	public String getResearcherTimely() {
		return researcherTimely;
	}
	public void setResearcherTimely(String researcherTimely) {
		this.researcherTimely = researcherTimely;
	}
	public String getResearcherAccuracy() {
		return researcherAccuracy;
	}
	public void setResearcherAccuracy(String researcherAccuracy) {
		this.researcherAccuracy = researcherAccuracy;
	}
	public String getEvaluationPeople() {
		return evaluationPeople;
	}
	public void setEvaluationPeople(String evaluationPeople) {
		this.evaluationPeople = evaluationPeople;
	}
	
}
