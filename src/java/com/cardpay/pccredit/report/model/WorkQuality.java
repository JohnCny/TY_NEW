package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
public class WorkQuality extends BusinessModel{
	
	private static final long serialVersionUID = 1L;

	private String chineseName; //客户名称
	
	private String cardId; //证件号码 
	
	private String applicationId;//进件Id
	
	private String writeName; //录入人员
	
	private String approvedName; //复核人员
	
	private String cardIdNum; //证件号码修改次数
	
	private String chineseNameNum; //姓名修改次数
	
	private String telephoneNum; //手机号码修改次数
	
	

	public String getWriteName() {
		return writeName;
	}

	public void setWriteName(String writeName) {
		this.writeName = writeName;
	}

	public String getApprovedName() {
		return approvedName;
	}

	public void setApprovedName(String approvedName) {
		this.approvedName = approvedName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}


	public String getCardIdNum() {
		return cardIdNum;
	}

	public void setCardIdNum(String cardIdNum) {
		this.cardIdNum = cardIdNum;
	}

	public String getChineseNameNum() {
		return chineseNameNum;
	}

	public void setChineseNameNum(String chineseNameNum) {
		this.chineseNameNum = chineseNameNum;
	}

	public String getTelephoneNum() {
		return telephoneNum;
	}

	public void setTelephoneNum(String telephoneNum) {
		this.telephoneNum = telephoneNum;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	
}

