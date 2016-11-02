package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;


public class CustomerApplicationProcessForm extends BusinessModel{

	private static final long serialVersionUID = 1L;

	private String displayName;
	private String examineAmount;
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
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getExamineAmount() {
		return examineAmount;
	}
	public void setExamineAmount(String examineAmount) {
		this.examineAmount = examineAmount;
	}
	
}
