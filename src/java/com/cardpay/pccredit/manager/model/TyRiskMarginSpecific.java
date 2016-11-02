package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "ty_risk_margin_specific",generator=IDType.uuid32)
public class TyRiskMarginSpecific extends BusinessModel{
	/*
	 * 风险保证金log表
	 */
	private static final long serialVersionUID = 1L;
	private String riskId	;
	private String year;	
	private String month;	
	private String inRiskMargin;	
	private String outRiskMargin;	
	private String deductRiskMargin;
	public String getRiskId() {
		return riskId;
	}
	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getInRiskMargin() {
		return inRiskMargin;
	}
	public void setInRiskMargin(String inRiskMargin) {
		this.inRiskMargin = inRiskMargin;
	}
	public String getOutRiskMargin() {
		return outRiskMargin;
	}
	public void setOutRiskMargin(String outRiskMargin) {
		this.outRiskMargin = outRiskMargin;
	}
	public String getDeductRiskMargin() {
		return deductRiskMargin;
	}
	public void setDeductRiskMargin(String deductRiskMargin) {
		this.deductRiskMargin = deductRiskMargin;
	}	
	
}
