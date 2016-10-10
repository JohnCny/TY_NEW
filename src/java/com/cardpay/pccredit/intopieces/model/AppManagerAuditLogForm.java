package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;


public class AppManagerAuditLogForm extends BusinessModel{

	private static final long serialVersionUID = 1L;

	private String  applicationId;//进件id
	private String  auditType;     
	private String  userName1;    
	private String  userName2;      
	private String  userName3;
	private String  examineAmount;
	private String  examineLv;
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getUserName1() {
		return userName1;
	}
	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}
	public String getUserName2() {
		return userName2;
	}
	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}
	public String getUserName3() {
		return userName3;
	}
	public void setUserName3(String userName3) {
		this.userName3 = userName3;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getExamineAmount() {
		return examineAmount;
	}
	public void setExamineAmount(String examineAmount) {
		this.examineAmount = examineAmount;
	}
	public String getExamineLv() {
		return examineLv;
	}
	public void setExamineLv(String examineLv) {
		this.examineLv = examineLv;
	}
	
	
	
}
