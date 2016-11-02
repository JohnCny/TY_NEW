/**
 * 
 */
package com.cardpay.pccredit.riskControl.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author shaoming
 *
 * 2014年11月10日   上午10:28:32
 */
public class RiskCustomerCollectionPlansActionForm extends BaseForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String collectionPlanId;
	private String collection;
	private String collectionResult;
	private String collectionStartTime;
	private String collectionEndTime;
	private String whetherImplement;
	private String endResult;
	private String collectionPromiseDate;
	private String collectionAmount;
	public String getCollectionPromiseDate() {
		return collectionPromiseDate;
	}
	public void setCollectionPromiseDate(String collectionPromiseDate) {
		this.collectionPromiseDate = collectionPromiseDate;
	}
	public String getCollectionPlanId() {
		return collectionPlanId;
	}
	public void setCollectionPlanId(String collectionPlanId) {
		this.collectionPlanId = collectionPlanId;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getCollectionResult() {
		return collectionResult;
	}
	public void setCollectionResult(String collectionResult) {
		this.collectionResult = collectionResult;
	}
	
	public String getCollectionStartTime() {
		return collectionStartTime;
	}
	public void setCollectionStartTime(String collectionStartTime) {
		this.collectionStartTime = collectionStartTime;
	}
	public String getCollectionEndTime() {
		return collectionEndTime;
	}
	public void setCollectionEndTime(String collectionEndTime) {
		this.collectionEndTime = collectionEndTime;
	}
	public String getWhetherImplement() {
		return whetherImplement;
	}
	public void setWhetherImplement(String whetherImplement) {
		this.whetherImplement = whetherImplement;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public String getCollectionAmount() {
		return collectionAmount;
	}
	public void setCollectionAmount(String collectionAmount) {
		this.collectionAmount = collectionAmount;
	}
	
}
