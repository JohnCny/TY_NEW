package com.cardpay.pccredit.riskControl.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * CustomerOverdue类的描述
 * 
 * @author 王海东
 * @created on 2014-11-4
 * 
 * @version $Id:$
 */
public class CustomerOverdueForm extends BaseForm {

	private static final long serialVersionUID = 1L;

	private String customerId;
	private String chineseName;
	private String productId;
	private String aging;
	private String userId;
	private String productName;
	private String numberDaysOverdue;
	private String currentBalance;
	private String minimumPayment;
	private String reminder;
	private String reminderWay;
	private Date reminderDate;
	private String overduePaybackAll;
	private Date lateDate;

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOverduePaybackAll() {
		return overduePaybackAll;
	}

	public void setOverduePaybackAll(String overduePaybackAll) {
		this.overduePaybackAll = overduePaybackAll;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
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

	public Date getLateDate() {
		return lateDate;
	}

	public void setLateDate(Date lateDate) {
		this.lateDate = lateDate;
	}

}
