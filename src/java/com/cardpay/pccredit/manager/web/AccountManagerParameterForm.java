package com.cardpay.pccredit.manager.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * AccountManagerParameter类的描述
 * 
 * @author 王海东
 * @created on 2014-11-7
 * 
 * @version $Id:$
 */
public class AccountManagerParameterForm extends BaseForm {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String displayName;
	private String levelInformation;
	private String creditLine;
	private Date entryTime;
	private String riskTolerance;
	private String basePay;
	private String createdBy;
	private Date createdTime;
	private String modifiedBy;
	private Date modifiedTime;
	
	private String instcode;
	
	private String managerType;

	
	
	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}

	public String getInstcode() {
		return instcode;
	}

	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLevelInformation() {
		return levelInformation;
	}

	public void setLevelInformation(String levelInformation) {
		this.levelInformation = levelInformation;
	}

	public String getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getRiskTolerance() {
		return riskTolerance;
	}

	public void setRiskTolerance(String riskTolerance) {
		this.riskTolerance = riskTolerance;
	}

	public String getBasePay() {
		return basePay;
	}

	public void setBasePay(String basePay) {
		this.basePay = basePay;
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
