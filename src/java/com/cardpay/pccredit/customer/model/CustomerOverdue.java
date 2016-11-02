package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author chenzhifang
 *
 * 2014-11-3下午3:55:08
 */
@ModelParam(table = "customer_overdue")
public class CustomerOverdue extends BusinessModel {
	private static final long serialVersionUID = 1L;

	private String customerId;
	
	private String productId;

	private String numberDaysOverdue;
	
	private String currentBalance;
	
	private String minimumPayment;
	
	private String reminder;
	
	private String reminderWay;
	
	private Date reminderDate;
	
	private String overduePaybackAll;
	private Date lateDate;
	public String getOverduePaybackAll() {
		return overduePaybackAll;
	}

	public void setOverduePaybackAll(String overduePaybackAll) {
		this.overduePaybackAll = overduePaybackAll;
	}

	public Date getLateDate() {
		return lateDate;
	}

	public void setLateDate(Date lateDate) {
		this.lateDate = lateDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getNumberDaysOverdue() {
		return numberDaysOverdue;
	}

	public void setNumberDaysOverdue(String numberDaysOverdue) {
		this.numberDaysOverdue = numberDaysOverdue;
	}

	public String getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getMinimumPayment() {
		return minimumPayment;
	}

	public void setMinimumPayment(String minimumPayment) {
		this.minimumPayment = minimumPayment;
	}

	public String getReminder() {
		return reminder;
	}

	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	public String getReminderWay() {
		return reminderWay;
	}

	public void setReminderWay(String reminderWay) {
		this.reminderWay = reminderWay;
	}

	public Date getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	
}
