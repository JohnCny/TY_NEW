package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "t_risk_margin",generator=IDType.uuid32)
public class TRiskMargin extends BusinessModel{
	private static final long serialVersionUID = 1L;
	
	private String customerId;//客户经理id
	private String totalRiskMargin;	
	private Date updateTime;
	private String  accountOpenYear;
	private String accountOpenMonth;
	
	
	public String getAccountOpenYear() {
		return accountOpenYear;
	}
	public void setAccountOpenYear(String accountOpenYear) {
		this.accountOpenYear = accountOpenYear;
	}
	
	public String getAccountOpenMonth() {
		return accountOpenMonth;
	}
	public void setAccountOpenMonth(String accountOpenMonth) {
		this.accountOpenMonth = accountOpenMonth;
	}
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
