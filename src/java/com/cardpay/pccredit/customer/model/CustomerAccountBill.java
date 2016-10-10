/**
 * 
 */
package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ： 账单model
 * @author 张石树
 *
 * 2014-11-18 下午3:00:25
 */
@ModelParam(table="customer_account_bill")
public class CustomerAccountBill extends BaseModel{
	private String accountNumber;
	
	private String overdueAmount;
	
	private String currentMonthInterestAccount;
	
	private String paybackAccount;
	
	private Date createdDate;

	private Integer billDataYear;
	
	private Integer billDataMonth;
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getCurrentMonthInterestAccount() {
		return currentMonthInterestAccount;
	}

	public void setCurrentMonthInterestAccount(String currentMonthInterestAccount) {
		this.currentMonthInterestAccount = currentMonthInterestAccount;
	}

	public String getPaybackAccount() {
		return paybackAccount;
	}

	public void setPaybackAccount(String paybackAccount) {
		this.paybackAccount = paybackAccount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getBillDataYear() {
		return billDataYear;
	}

	public void setBillDataYear(Integer billDataYear) {
		this.billDataYear = billDataYear;
	}

	public Integer getBillDataMonth() {
		return billDataMonth;
	}

	public void setBillDataMonth(Integer billDataMonth) {
		this.billDataMonth = billDataMonth;
	}

}
