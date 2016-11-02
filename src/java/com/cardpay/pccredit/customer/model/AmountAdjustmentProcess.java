package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * 描述 ：
 * @author 张石树
 *
 * 2014-12-5 上午11:08:11
 */
@ModelParam(table = "amount_adjustment_process")
public class AmountAdjustmentProcess extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String amountAdjustmentId;
	private String auditUser;
	private String serialNumber;
	private String auditOpinion;
	private String refusalReason;
	private String fallbackReason;
	private String examineAmount;
	private String nextNodeId;
	private String delayAuditUser;
	private Date auditTime;
	private String processOpStatus;

	public String getExamineAmount() {
		return examineAmount;
	}

	public void setExamineAmount(String examineAmount) {
		this.examineAmount = examineAmount;
	}

	public String getAmountAdjustmentId() {
		return amountAdjustmentId;
	}

	public void setAmountAdjustmentId(String amountAdjustmentId) {
		this.amountAdjustmentId = amountAdjustmentId;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getRefusalReason() {
		return refusalReason;
	}

	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}

	public String getFallbackReason() {
		return fallbackReason;
	}

	public void setFallbackReason(String fallbackReason) {
		this.fallbackReason = fallbackReason;
	}

	public String getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	public String getDelayAuditUser() {
		return delayAuditUser;
	}

	public void setDelayAuditUser(String delayAuditUser) {
		this.delayAuditUser = delayAuditUser;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getProcessOpStatus() {
		return processOpStatus;
	}

	public void setProcessOpStatus(String processOpStatus) {
		this.processOpStatus = processOpStatus;
	}

}
