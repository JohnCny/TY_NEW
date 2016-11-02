package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "maintenance_account_manager",generator=IDType.uuid32)
public class MaintenanceAccountManager extends BusinessModel{
	
	private static final long serialVersionUID = 1L;
	private String 	hierarchy	;//	层级
	
	private String 	weightedNumber	;//	奖励激励加权数
	
	private String 	customerNumberAward	;//	管户奖金比例
	
	private String 	teamAward	;//	管理绩效比例
	
	private String 	allowance	;//	津贴
	
	private String 	insertCustomerAward	;//	新增客户奖金比例
	
	
	
	private String 	sleepRate	;//	睡眠卡奖金比例
	
	private String 	validRate	;//	有效卡奖金比例
	
	private String 	activeRate	;//	活跃卡奖金比例
	
	private String 	activateRate	;//	激活卡奖金比例
	
	private String 	dutySalary	;//	责任工资
	
	private String 	customerTypeid	;//	客户类型id
	
	private String 	customerType	;//	客户类型
	
	private String 	customerTypeCode	;//	客户类型code

	public String getCustomerTypeid() {
		return customerTypeid;
	}

	public void setCustomerTypeid(String customerTypeid) {
		this.customerTypeid = customerTypeid;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerTypeCode() {
		return customerTypeCode;
	}

	public void setCustomerTypeCode(String customerTypeCode) {
		this.customerTypeCode = customerTypeCode;
	}

	public String getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getWeightedNumber() {
		return weightedNumber;
	}

	public void setWeightedNumber(String weightedNumber) {
		this.weightedNumber = weightedNumber;
	}

	public String getCustomerNumberAward() {
		return customerNumberAward;
	}

	public void setCustomerNumberAward(String customerNumberAward) {
		this.customerNumberAward = customerNumberAward;
	}

	public String getTeamAward() {
		return teamAward;
	}

	public void setTeamAward(String teamAward) {
		this.teamAward = teamAward;
	}

	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

	public String getInsertCustomerAward() {
		return insertCustomerAward;
	}

	public void setInsertCustomerAward(String insertCustomerAward) {
		this.insertCustomerAward = insertCustomerAward;
	}

	public String getSleepRate() {
		return sleepRate;
	}

	public void setSleepRate(String sleepRate) {
		this.sleepRate = sleepRate;
	}

	public String getValidRate() {
		return validRate;
	}

	public void setValidRate(String validRate) {
		this.validRate = validRate;
	}

	public String getActiveRate() {
		return activeRate;
	}

	public void setActiveRate(String activeRate) {
		this.activeRate = activeRate;
	}

	public String getActivateRate() {
		return activateRate;
	}

	public void setActivateRate(String activateRate) {
		this.activateRate = activateRate;
	}

	public String getDutySalary() {
		return dutySalary;
	}

	public void setDutySalary(String dutySalary) {
		this.dutySalary = dutySalary;
	}


}
