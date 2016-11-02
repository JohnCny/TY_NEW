package com.cardpay.pccredit.divisional.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "divisional_application")
public class Divisional extends BaseModel{
	private static final long serialVersionUID = 1L;
	private String id;
	private String customerId;
	private String customerManagerId;
	private String divisionalResult;
	private String divisionalTime;
	private String originalOrganizationOld;
	private String originalManagerOld;
	private String currentOrganizationId;
	private String divisionalProgress;
	private String divisionalType;
	private String createdBy;
	private Date createdTime;
	private String modifiedBy;
	private Date modifiedTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getDivisionalResult() {
		return divisionalResult;
	}
	public void setDivisionalResult(String divisionalResult) {
		this.divisionalResult = divisionalResult;
	}
	public String getDivisionalTime() {
		return divisionalTime;
	}
	public void setDivisionalTime(String divisionalTime) {
		this.divisionalTime = divisionalTime;
	}
	public String getOriginalOrganizationOld() {
		return originalOrganizationOld;
	}
	public void setOriginalOrganizationOld(String originalOrganizationOld) {
		this.originalOrganizationOld = originalOrganizationOld;
	}
	public String getOriginalManagerOld() {
		return originalManagerOld;
	}
	public void setOriginalManagerOld(String originalManagerOld) {
		this.originalManagerOld = originalManagerOld;
	}
	public String getCurrentOrganizationId() {
		return currentOrganizationId;
	}
	public void setCurrentOrganizationId(String currentOrganizationId) {
		this.currentOrganizationId = currentOrganizationId;
	}
	public String getDivisionalProgress() {
		return divisionalProgress;
	}
	public void setDivisionalProgress(String divisionalProgress) {
		this.divisionalProgress = divisionalProgress;
	}
	public String getDivisionalType() {
		return divisionalType;
	}
	public void setDivisionalType(String divisionalType) {
		this.divisionalType = divisionalType;
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
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	
}
