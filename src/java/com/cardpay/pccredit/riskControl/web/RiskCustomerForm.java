package com.cardpay.pccredit.riskControl.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author chenzhifang
 *
 * 2014-10-27下午1:39:11
 */
public class RiskCustomerForm extends BaseForm {
	private static final long serialVersionUID = 1407519332773757395L;

	private String customerId;
	
	private String riskLevel;
	
	private String riskCategories;
	
	private String status;
	
	private String refuseReason;
	
	private String reportedIdManager;
	
	private String riskDes;
	
	private String riskAttribute;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getRiskCategories() {
		return riskCategories;
	}

	public void setRiskCategories(String riskCategories) {
		this.riskCategories = riskCategories;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getReportedIdManager() {
		return reportedIdManager;
	}

	public void setReportedIdManager(String reportedIdManager) {
		this.reportedIdManager = reportedIdManager;
	}

	public String getRiskDes() {
		return riskDes;
	}

	public void setRiskDes(String riskDes) {
		this.riskDes = riskDes;
	}

	public String getRiskAttribute() {
		return riskAttribute;
	}

	public void setRiskAttribute(String riskAttribute) {
		this.riskAttribute = riskAttribute;
	}
}
