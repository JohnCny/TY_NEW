package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 季东晓
 *
 * 2014-10-28 上午10:21:23
 */

@ModelParam(table = "customer_maintenancee_log",generator=IDType.uuid32)

public class CustomerMaintenanceLog extends BaseModel {
	
	
	private static final long serialVersionUID = 1L;
	
	private String customerId;

	private String maintenancePersonalId;

	private String maintenanceScheduleId;
	
	private String marketingPlanId;
	
	private String modifyTableName;
	
	private String modifyFieldName;
	
	private String modifyReason;
	
	private String originalCost;
	
	private String presentValue;
	
	private String maintenanceType;
	
	private String createdBy;
	
	private Date createdTime;
	
	private String questionName;
	

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMaintenancePersonalId() {
		return maintenancePersonalId;
	}

	public void setMaintenancePersonalId(String maintenancePersonalId) {
		this.maintenancePersonalId = maintenancePersonalId;
	}

	public String getMaintenanceScheduleId() {
		return maintenanceScheduleId;
	}

	public void setMaintenanceScheduleId(String maintenanceScheduleId) {
		this.maintenanceScheduleId = maintenanceScheduleId;
	}

	public String getMarketingPlanId() {
		return marketingPlanId;
	}

	public void setMarketingPlanId(String marketingPlanId) {
		this.marketingPlanId = marketingPlanId;
	}

	public String getModifyTableName() {
		return modifyTableName;
	}

	public void setModifyTableName(String modifyTableName) {
		this.modifyTableName = modifyTableName;
	}

	public String getModifyFieldName() {
		return modifyFieldName;
	}

	public void setModifyFieldName(String modifyFieldName) {
		this.modifyFieldName = modifyFieldName;
	}

	public String getModifyReason() {
		return modifyReason;
	}

	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}

	public String getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(String originalCost) {
		this.originalCost = originalCost;
	}

	public String getPresentValue() {
		return presentValue;
	}

	public void setPresentValue(String presentValue) {
		this.presentValue = presentValue;
	}

	public String getMaintenanceType() {
		return maintenanceType;
	}

	public void setMaintenanceType(String maintenanceType) {
		this.maintenanceType = maintenanceType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	
	


}
