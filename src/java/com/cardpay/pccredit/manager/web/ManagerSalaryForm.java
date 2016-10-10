package com.cardpay.pccredit.manager.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:56:47
 */
public class ManagerSalaryForm extends BaseForm  {
	private static final long serialVersionUID = 8755396462007710503L;
	
	private String customerId;
	
	private String rewardIncentiveInformation;
	
	private String deductAmount;
	
	private String basePay;
	
	private String allowance;
	
	private String dutySalary;
	
	private String returnPrepareAmount;
	
	private String insertPrepareAmount;
	
	private String fine;
	
	private String year;
	
	private String month;
	
	private String describe;
	
	private String  basicTaskBonus;
	
	private String middleAllowance;
	
	private String attendDeduct;

	
	
	
	public String getBasicTaskBonus() {
		return basicTaskBonus;
	}

	public void setBasicTaskBonus(String basicTaskBonus) {
		this.basicTaskBonus = basicTaskBonus;
	}

	public String getMiddleAllowance() {
		return middleAllowance;
	}

	public void setMiddleAllowance(String middleAllowance) {
		this.middleAllowance = middleAllowance;
	}

	public String getAttendDeduct() {
		return attendDeduct;
	}

	public void setAttendDeduct(String attendDeduct) {
		this.attendDeduct = attendDeduct;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRewardIncentiveInformation() {
		return rewardIncentiveInformation;
	}

	public void setRewardIncentiveInformation(String rewardIncentiveInformation) {
		this.rewardIncentiveInformation = rewardIncentiveInformation;
	}

	public String getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}

	public String getBasePay() {
		return basePay;
	}

	public void setBasePay(String basePay) {
		this.basePay = basePay;
	}

	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

	public String getDutySalary() {
		return dutySalary;
	}

	public void setDutySalary(String dutySalary) {
		this.dutySalary = dutySalary;
	}

	public String getReturnPrepareAmount() {
		return returnPrepareAmount;
	}

	public void setReturnPrepareAmount(String returnPrepareAmount) {
		this.returnPrepareAmount = returnPrepareAmount;
	}

	public String getInsertPrepareAmount() {
		return insertPrepareAmount;
	}

	public void setInsertPrepareAmount(String insertPrepareAmount) {
		this.insertPrepareAmount = insertPrepareAmount;
	}

	public String getFine() {
		return fine;
	}

	public void setFine(String fine) {
		this.fine = fine;
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
