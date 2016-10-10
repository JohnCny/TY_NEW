package com.cardpay.pccredit.riskControl.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author chenzhifang
 *
 * 2014-11-5下午4:40:44
 */
public class RiskReviewProcForm extends BaseForm {
	private static final long serialVersionUID = 5325766898406340374L;
	
	// 风险事项ID
	private String riskIssuesId;
	// 上报客户经理ID
	private String reportedManagerId;
	// 审核人
	private String auditPeople;
	// 审核时间
	private String auditTime;
	// 备注
	private String note;
	// 审核结果(通告/驳回)
	private String resultsOfAudit;
	
	public String getRiskIssuesId() {
		return riskIssuesId;
	}
	
	public void setRiskIssuesId(String riskIssuesId) {
		this.riskIssuesId = riskIssuesId;
	}
	
	public String getReportedManagerId() {
		return reportedManagerId;
	}
	
	public void setReportedManagerId(String reportedManagerId) {
		this.reportedManagerId = reportedManagerId;
	}
	
	public String getAuditPeople() {
		return auditPeople;
	}
	
	public void setAuditPeople(String auditPeople) {
		this.auditPeople = auditPeople;
	}
	
	public String getAuditTime() {
		return auditTime;
	}
	
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getResultsOfAudit() {
		return resultsOfAudit;
	}
	
	public void setResultsOfAudit(String resultsOfAudit) {
		this.resultsOfAudit = resultsOfAudit;
	}
}
