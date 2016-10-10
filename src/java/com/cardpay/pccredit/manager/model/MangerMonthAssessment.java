package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * 描述 ：月度考核指标 
 * @author 张石树
 *
 * 2014-11-25 上午10:19:51
 */
@ModelParam(table="manger_month_assessment")
public class MangerMonthAssessment extends BusinessModel{
	
	private String customerManagerLevel;
	
	private String creditlineAccount = "0";
	
	private String monthdayAvgCreditline  = "0";
	
	private String monthdayTeamAvgCreditline  = "0";
	
	private Integer tubeNumber = 0;
	
	private String activeRate = "0";
	
	private String activationRate = "0";

	public String getCustomerManagerLevel() {
		return customerManagerLevel;
	}

	public void setCustomerManagerLevel(String customerManagerLevel) {
		this.customerManagerLevel = customerManagerLevel;
	}

	public String getCreditlineAccount() {
		return creditlineAccount;
	}

	public void setCreditlineAccount(String creditlineAccount) {
		this.creditlineAccount = creditlineAccount;
	}

	public String getMonthdayAvgCreditline() {
		return monthdayAvgCreditline;
	}

	public void setMonthdayAvgCreditline(String monthdayAvgCreditline) {
		this.monthdayAvgCreditline = monthdayAvgCreditline;
	}

	public String getMonthdayTeamAvgCreditline() {
		return monthdayTeamAvgCreditline;
	}

	public void setMonthdayTeamAvgCreditline(String monthdayTeamAvgCreditline) {
		this.monthdayTeamAvgCreditline = monthdayTeamAvgCreditline;
	}

	public Integer getTubeNumber() {
		return tubeNumber;
	}

	public void setTubeNumber(Integer tubeNumber) {
		this.tubeNumber = tubeNumber;
	}

	public String getActiveRate() {
		return activeRate;
	}

	public void setActiveRate(String activeRate) {
		this.activeRate = activeRate;
	}

	public String getActivationRate() {
		return activationRate;
	}

	public void setActivationRate(String activationRate) {
		this.activationRate = activationRate;
	}
}
