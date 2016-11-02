package com.cardpay.pccredit.riskControl.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author chenzhifang
 *
 * 2014-11-5下午4:25:30
 */
@ModelParam(table = "risk_review_process",generator=IDType.uuid32)
public class RiskReviewProcess extends BusinessModel {
	private static final long serialVersionUID = -6563474887173061937L;
	// 风险事项ID
	private String riskIssuesId;
	// 上报客户经理ID
	private String reportedManagerId;
	// 审核人
	private String auditPeople;
	// 审核时间
	private Date auditTime;
	// 备注
	private String note;
	// 审核结果(通告/驳回)
	private String resultsOfAudit;
	
	// 上报客户经理
	private String reportedManagerName;
	// 审核人名
	private String auditPeopleCnName;
	// 风险事项描述
	private String riskIssuesDescribed;
	// 阶段
	private String phase;
	// 当前状态
	private String status;
	
	public String getRiskIssuesDescribed() {
		return riskIssuesDescribed;
	}

	public void setRiskIssuesDescribed(String riskIssuesDescribed) {
		this.riskIssuesDescribed = riskIssuesDescribed;
	}

	public String getReportedManagerName() {
		return reportedManagerName;
	}

	public void setReportedManagerName(String reportedManagerName) {
		this.reportedManagerName = reportedManagerName;
	}

	public String getAuditPeopleCnName() {
		return auditPeopleCnName;
	}

	public void setAuditPeopleCnName(String auditPeopleCnName) {
		this.auditPeopleCnName = auditPeopleCnName;
	}

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

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
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

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
