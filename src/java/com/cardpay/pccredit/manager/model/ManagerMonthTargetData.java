package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * 描述 ：客户经理每月指标数据
 * @author 张石树
 *
 * 2014-11-21 下午2:15:36
 */
@ModelParam(table="manager_month_target_data")
public class ManagerMonthTargetData extends BusinessModel{
	
	private String customerManagerId;
	
	private Integer dataYear;
	
	private Integer dataMonth;
	
	private String creditLineAccount;
	
	private Integer tubeNumber;
	
	private String activationRate;
	
	private String activeRate;
	
	private String monthPerdayCreditline;
	
	private String monthPerdayTeamCreditline;
	
	private String customerManagerLevel;
	
	private Integer subManangerCount;
	
	private String ifPassStandard;

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public Integer getDataYear() {
		return dataYear;
	}

	public void setDataYear(Integer dataYear) {
		this.dataYear = dataYear;
	}

	public Integer getDataMonth() {
		return dataMonth;
	}

	public void setDataMonth(Integer dataMonth) {
		this.dataMonth = dataMonth;
	}

	public String getCreditLineAccount() {
		return creditLineAccount;
	}

	public void setCreditLineAccount(String creditLineAccount) {
		this.creditLineAccount = creditLineAccount;
	}

	public Integer getTubeNumber() {
		return tubeNumber;
	}

	public void setTubeNumber(Integer tubeNumber) {
		this.tubeNumber = tubeNumber;
	}

	public String getActivationRate() {
		return activationRate;
	}

	public void setActivationRate(String activationRate) {
		this.activationRate = activationRate;
	}

	public String getActiveRate() {
		return activeRate;
	}

	public void setActiveRate(String activeRate) {
		this.activeRate = activeRate;
	}

	public String getMonthPerdayCreditline() {
		return monthPerdayCreditline;
	}

	public void setMonthPerdayCreditline(String monthPerdayCreditline) {
		this.monthPerdayCreditline = monthPerdayCreditline;
	}

	public String getMonthPerdayTeamCreditline() {
		return monthPerdayTeamCreditline;
	}

	public void setMonthPerdayTeamCreditline(String monthPerdayTeamCreditline) {
		this.monthPerdayTeamCreditline = monthPerdayTeamCreditline;
	}

	public String getCustomerManagerLevel() {
		return customerManagerLevel;
	}

	public void setCustomerManagerLevel(String customerManagerLevel) {
		this.customerManagerLevel = customerManagerLevel;
	}

	public Integer getSubManangerCount() {
		return subManangerCount;
	}

	public void setSubManangerCount(Integer subManangerCount) {
		this.subManangerCount = subManangerCount;
	}

	public String getIfPassStandard() {
		return ifPassStandard;
	}

	public void setIfPassStandard(String ifPassStandard) {
		this.ifPassStandard = ifPassStandard;
	}

}
