package com.cardpay.pccredit.riskControl.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "collection_plans_action",generator=IDType.uuid32)
public class RiskCustomerCollectionPlansAction extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String collectionPlanId;
	private String collection;
	private String collectionResult;
	private Date collectionStartTime;
	private Date collectionEndTime;
	private String whetherImplement;
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
	
	public Date getCollectionStartTime() {
		return collectionStartTime;
	}
	public void setCollectionStartTime(Date collectionStartTime) {
		this.collectionStartTime = collectionStartTime;
	}
	public Date getCollectionEndTime() {
		return collectionEndTime;
	}
	public void setCollectionEndTime(Date collectionEndTime) {
		this.collectionEndTime = collectionEndTime;
	}
	public String getWhetherImplement() {
		return whetherImplement;
	}
	public void setWhetherImplement(String whetherImplement) {
		this.whetherImplement = whetherImplement;
	}
	
}
