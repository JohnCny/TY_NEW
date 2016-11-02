/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author shaoming
 *
 * 2014年11月25日   上午9:18:41
 */
@ModelParam(table = "information_plans_action" , generator=IDType.uuid32)
public class InformationPlansAction extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String informationPlanId;
	private String informationWay;
	private String informationResult;
	private Date informationStartTime;
	private Date informationEndTime;
	public String getInformationPlanId() {
		return informationPlanId;
	}
	public void setInformationPlanId(String informationPlanId) {
		this.informationPlanId = informationPlanId;
	}
	public String getInformationWay() {
		return informationWay;
	}
	public void setInformationWay(String informationWay) {
		this.informationWay = informationWay;
	}
	public String getInformationResult() {
		return informationResult;
	}
	public void setInformationResult(String informationResult) {
		this.informationResult = informationResult;
	}
	public Date getInformationStartTime() {
		return informationStartTime;
	}
	public void setInformationStartTime(Date informationStartTime) {
		this.informationStartTime = informationStartTime;
	}
	public Date getInformationEndTime() {
		return informationEndTime;
	}
	public void setInformationEndTime(Date informationEndTime) {
		this.informationEndTime = informationEndTime;
	}
	
}
