/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author shaoming
 *
 * 2014年11月19日   上午11:35:32
 */
public class InformationOfficerEvaluateFilter extends BaseQueryFilter{
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
