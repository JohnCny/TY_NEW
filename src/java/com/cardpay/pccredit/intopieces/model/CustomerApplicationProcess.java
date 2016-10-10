package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * CustomerApplicationProcess类的描述
 * 
 * @author 王海东
 * @created on 2014-11-28
 * 
 * @version $Id:$
 */
@ModelParam(table = "customer_application_process")
public class CustomerApplicationProcess extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String applicationId;
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
	
	private String examineLv;
	
	

	public String getExamineLv() {
		return examineLv;
	}

	public void setExamineLv(String examineLv) {
		this.examineLv = examineLv;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getExamineAmount() {
		return examineAmount;
	}

	public void setExamineAmount(String examineAmount) {
		this.examineAmount = examineAmount;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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
