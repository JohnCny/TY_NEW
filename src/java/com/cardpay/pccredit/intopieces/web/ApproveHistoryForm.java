package com.cardpay.pccredit.intopieces.web;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;

public class ApproveHistoryForm extends BaseModel{
	
	private String statusName;
	
	private String examineResult;
	
	private String displayName;
	
	private String examineAmount;
	
	private Date startExamineTime;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getExamineResult() {
		return examineResult;
	}

	public void setExamineResult(String examineResult) {
		this.examineResult = examineResult;
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

	public Date getStartExamineTime() {
		return startExamineTime;
	}

	public void setStartExamineTime(Date startExamineTime) {
		this.startExamineTime = startExamineTime;
	}
	
}
