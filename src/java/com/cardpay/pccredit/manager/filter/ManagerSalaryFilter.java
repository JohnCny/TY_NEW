package com.cardpay.pccredit.manager.filter;

import java.util.List;

import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-11-14下午5:54:55
 */
public class ManagerSalaryFilter extends BaseQueryFilter {

	private String customerId;
	// 客户经理姓名
	private String managerName;
	
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
	
	private String customerManagerId;
	private List<AccountManagerParameterForm> customerManagerIds;
	
	private String organName;
	
	private String date;
	
	private String managerType;
	

	public String getManagerType() {
		return managerType;
	}

	public void setManagerType(String managerType) {
		this.managerType = managerType;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public List<AccountManagerParameterForm> getCustomerManagerIds() {
		return customerManagerIds;
	}

	public void setCustomerManagerIds(
			List<AccountManagerParameterForm> customerManagerIds) {
		this.customerManagerIds = customerManagerIds;
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

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
}
