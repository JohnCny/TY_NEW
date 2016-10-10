package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * CustomerMainManager类的描述
 * 
 * @author 王海东
 * @created on 2014-10-30
 * 
 * @version $Id:$
 */
@ModelParam(table = "customer_main_manager")
public class CustomerMainManager extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String customerId;
	private String manageException;
	private String unnormalBigaccountOut;
	private String managerImprove;
	private String marriageCircs;
	private String assetsAndLiabilities;
	private String personImprove;
	private String monthBackCircs;
	private String exceptionDisplay;
	private String industryRisk;
	private String familyCircs;
	private String childrenCircs;
	private String personBehaviour;
	private String loanAfterCircs;
	private String createdBy;
	private Date createdTime;
	private String modifiedBy;
	private Date modifiedTime;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getManageException() {
		return manageException;
	}

	public void setManageException(String manageException) {
		this.manageException = manageException;
	}



	public String getUnnormalBigaccountOut() {
		return unnormalBigaccountOut;
	}

	public void setUnnormalBigaccountOut(String unnormalBigaccountOut) {
		this.unnormalBigaccountOut = unnormalBigaccountOut;
	}

	public String getManagerImprove() {
		return managerImprove;
	}

	public void setManagerImprove(String managerImprove) {
		this.managerImprove = managerImprove;
	}

	public String getMarriageCircs() {
		return marriageCircs;
	}

	public void setMarriageCircs(String marriageCircs) {
		this.marriageCircs = marriageCircs;
	}

	public String getAssetsAndLiabilities() {
		return assetsAndLiabilities;
	}

	public void setAssetsAndLiabilities(String assetsAndLiabilities) {
		this.assetsAndLiabilities = assetsAndLiabilities;
	}

	public String getPersonImprove() {
		return personImprove;
	}

	public void setPersonImprove(String personImprove) {
		this.personImprove = personImprove;
	}

	public String getMonthBackCircs() {
		return monthBackCircs;
	}

	public void setMonthBackCircs(String monthBackCircs) {
		this.monthBackCircs = monthBackCircs;
	}

	public String getExceptionDisplay() {
		return exceptionDisplay;
	}

	public void setExceptionDisplay(String exceptionDisplay) {
		this.exceptionDisplay = exceptionDisplay;
	}

	public String getIndustryRisk() {
		return industryRisk;
	}

	public void setIndustryRisk(String industryRisk) {
		this.industryRisk = industryRisk;
	}

	public String getFamilyCircs() {
		return familyCircs;
	}

	public void setFamilyCircs(String familyCircs) {
		this.familyCircs = familyCircs;
	}

	public String getChildrenCircs() {
		return childrenCircs;
	}

	public void setChildrenCircs(String childrenCircs) {
		this.childrenCircs = childrenCircs;
	}

	public String getPersonBehaviour() {
		return personBehaviour;
	}

	public void setPersonBehaviour(String personBehaviour) {
		this.personBehaviour = personBehaviour;
	}

	public String getLoanAfterCircs() {
		return loanAfterCircs;
	}

	public void setLoanAfterCircs(String loanAfterCircs) {
		this.loanAfterCircs = loanAfterCircs;
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
