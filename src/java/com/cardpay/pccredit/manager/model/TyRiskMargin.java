package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "ty_risk_margin",generator=IDType.uuid32)
public class TyRiskMargin extends BusinessModel{
	/*
	 * 风险保证金总表
	 */
	private static final long serialVersionUID = 1L;
	private String customerId	;
	private String totalRiskMargin;	
	private Date updateTime;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getTotalRiskMargin() {
		return totalRiskMargin;
	}
	public void setTotalRiskMargin(String totalRiskMargin) {
		this.totalRiskMargin = totalRiskMargin;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	

}
