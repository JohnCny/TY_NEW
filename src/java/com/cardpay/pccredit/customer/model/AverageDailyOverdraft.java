package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * AverageDailyOverdraft类的描述
 * 
 * @author 王海东
 * @created on 2014-11-26
 * 
 * @version $Id:$
 */
@ModelParam(table = "average_daily_overdraft")
public class AverageDailyOverdraft extends BusinessModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accountNumber;
	private String averageDailyOverdraft;
	private String principalOverdraft;
	private String totalAmountOverdraft;
	private Integer allDueStatus;
	private Integer lowDueStatus;
	private String periods;
	private Integer month;
	private Integer year;
	private Date createdTime;
	private String customerId;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAverageDailyOverdraft() {
		return averageDailyOverdraft;
	}

	public void setAverageDailyOverdraft(String averageDailyOverdraft) {
		this.averageDailyOverdraft = averageDailyOverdraft;
	}

	public String getPrincipalOverdraft() {
		return principalOverdraft;
	}

	public void setPrincipalOverdraft(String principalOverdraft) {
		this.principalOverdraft = principalOverdraft;
	}

	public String getTotalAmountOverdraft() {
		return totalAmountOverdraft;
	}

	public void setTotalAmountOverdraft(String totalAmountOverdraft) {
		this.totalAmountOverdraft = totalAmountOverdraft;
	}

	public Integer getAllDueStatus() {
		return allDueStatus;
	}

	public void setAllDueStatus(Integer allDueStatus) {
		this.allDueStatus = allDueStatus;
	}

	public Integer getLowDueStatus() {
		return lowDueStatus;
	}

	public void setLowDueStatus(Integer lowDueStatus) {
		this.lowDueStatus = lowDueStatus;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
