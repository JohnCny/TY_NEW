package com.cardpay.pccredit.riskControl.filter;

import java.util.Date;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-11-5下午4:33:53
 */
public class RiskReviewProcessFilter extends BaseQueryFilter {
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
	
	private String reportedManagerName;
	
	private String phase;
	
	private Date createdTime;
	
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

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getReportedManagerName() {
		return reportedManagerName;
	}

	public void setReportedManagerName(String reportedManagerName) {
		this.reportedManagerName = reportedManagerName;
	}
}
