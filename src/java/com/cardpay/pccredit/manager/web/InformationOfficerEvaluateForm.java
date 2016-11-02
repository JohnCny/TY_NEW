/**
 * 
 */
package com.cardpay.pccredit.manager.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author shaoming
 *
 * 2014年11月19日   下午1:33:59
 */
public class InformationOfficerEvaluateForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messengerId;
	private String messengerName;
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
	public String getMessengerName() {
		return messengerName;
	}
	public void setMessengerName(String messengerName) {
		this.messengerName = messengerName;
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
