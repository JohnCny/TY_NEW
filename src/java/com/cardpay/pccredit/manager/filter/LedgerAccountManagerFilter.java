package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 4.2.40 客户经理台账
 * 
 * @author 王海东
 * @created on 2014-11-19
 * 
 * @version $Id:$
 */
public class LedgerAccountManagerFilter extends BaseQueryFilter{
	
	private String loginId; //登录Id
	private String displayName; //登录Id
	private String customerManagerd; // 客户经理ID
	private String affiliation; // 所属机构
	private String tubeNumber; // 管户数
	private String managementCycle; // 管理周期
	private String attendanceSituation; // 考勤情况
	private String customerVisitNumber; // 客户拜访数
	private String dailyQuality; // 日报质量
	private String weeklyQuality; // 周报质量
	private String customerMaintenanceNumber; // 客户维护数
	private String manumberenResult; // 维护结果
	private String softInformationCollection; // 软信息收集
	private String moneySituation; // 用款情况
	private String operatingCondition; // 经营情况
	private String paymentCondition; // 还款情况
	private String weekCompletion; // 周完成情况
	private String monthsComplete; // 月完成情况
	private String dailyLoanBalance; // 贷款日均余额
	private String leaderDescription; // 组长对微贷经理的主观描述

	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getCustomerManagerd() {
		return customerManagerd;
	}

	public void setCustomerManagerd(String customerManagerd) {
		this.customerManagerd = customerManagerd;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getTubeNumber() {
		return tubeNumber;
	}

	public void setTubeNumber(String tubeNumber) {
		this.tubeNumber = tubeNumber;
	}

	public String getManagementCycle() {
		return managementCycle;
	}

	public void setManagementCycle(String managementCycle) {
		this.managementCycle = managementCycle;
	}

	public String getAttendanceSituation() {
		return attendanceSituation;
	}

	public void setAttendanceSituation(String attendanceSituation) {
		this.attendanceSituation = attendanceSituation;
	}

	public String getCustomerVisitNumber() {
		return customerVisitNumber;
	}

	public void setCustomerVisitNumber(String customerVisitNumber) {
		this.customerVisitNumber = customerVisitNumber;
	}

	public String getDailyQuality() {
		return dailyQuality;
	}

	public void setDailyQuality(String dailyQuality) {
		this.dailyQuality = dailyQuality;
	}

	public String getWeeklyQuality() {
		return weeklyQuality;
	}

	public void setWeeklyQuality(String weeklyQuality) {
		this.weeklyQuality = weeklyQuality;
	}

	public String getCustomerMaintenanceNumber() {
		return customerMaintenanceNumber;
	}

	public void setCustomerMaintenanceNumber(String customerMaintenanceNumber) {
		this.customerMaintenanceNumber = customerMaintenanceNumber;
	}

	public String getManumberenResult() {
		return manumberenResult;
	}

	public void setManumberenResult(String manumberenResult) {
		this.manumberenResult = manumberenResult;
	}
	
	public String getSoftInformationCollection() {
		return softInformationCollection;
	}

	public void setSoftInformationCollection(String softInformationCollection) {
		this.softInformationCollection = softInformationCollection;
	}

	public String getMoneySituation() {
		return moneySituation;
	}

	public void setMoneySituation(String moneySituation) {
		this.moneySituation = moneySituation;
	}

	public String getOperatingCondition() {
		return operatingCondition;
	}

	public void setOperatingCondition(String operatingCondition) {
		this.operatingCondition = operatingCondition;
	}

	public String getPaymentCondition() {
		return paymentCondition;
	}

	public void setPaymentCondition(String paymentCondition) {
		this.paymentCondition = paymentCondition;
	}

	public String getWeekCompletion() {
		return weekCompletion;
	}

	public void setWeekCompletion(String weekCompletion) {
		this.weekCompletion = weekCompletion;
	}

	public String getMonthsComplete() {
		return monthsComplete;
	}

	public void setMonthsComplete(String monthsComplete) {
		this.monthsComplete = monthsComplete;
	}

	public String getDailyLoanBalance() {
		return dailyLoanBalance;
	}

	public void setDailyLoanBalance(String dailyLoanBalance) {
		this.dailyLoanBalance = dailyLoanBalance;
	}

	public String getLeaderDescription() {
		return leaderDescription;
	}

	public void setLeaderDescription(String leaderDescription) {
		this.leaderDescription = leaderDescription;
	}

}
