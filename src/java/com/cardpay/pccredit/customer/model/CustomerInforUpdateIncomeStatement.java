package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 客户维护信息
 *
 * @author Evans zhang
 *
 * 2014-10-13 下午2:37:07
 */
@ModelParam(table = "income_statement",generator=IDType.uuid32)
public class CustomerInforUpdateIncomeStatement extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;
	private int loanType;
	private String names;
	private int no;
	private String january;
	private String february;
	private String march;
	private String april;
	private String may;
	private String june;
	private String july;
	private String august;
	private String september;
	private String october;
	private String november;
	private String december;
	private String totalAll;
	private String monthlyAverage;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public int getLoanType() {
		return loanType;
	}
	public void setLoanType(int loanType) {
		this.loanType = loanType;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getJanuary() {
		return january;
	}
	public void setJanuary(String january) {
		this.january = january;
	}
	public String getFebruary() {
		return february;
	}
	public void setFebruary(String february) {
		this.february = february;
	}
	public String getMarch() {
		return march;
	}
	public void setMarch(String march) {
		this.march = march;
	}
	public String getApril() {
		return april;
	}
	public void setApril(String april) {
		this.april = april;
	}
	public String getMay() {
		return may;
	}
	public void setMay(String may) {
		this.may = may;
	}
	public String getJune() {
		return june;
	}
	public void setJune(String june) {
		this.june = june;
	}
	public String getJuly() {
		return july;
	}
	public void setJuly(String july) {
		this.july = july;
	}
	public String getAugust() {
		return august;
	}
	public void setAugust(String august) {
		this.august = august;
	}
	public String getSeptember() {
		return september;
	}
	public void setSeptember(String september) {
		this.september = september;
	}
	public String getOctober() {
		return october;
	}
	public void setOctober(String october) {
		this.october = october;
	}
	public String getNovember() {
		return november;
	}
	public void setNovember(String november) {
		this.november = november;
	}
	public String getDecember() {
		return december;
	}
	public void setDecember(String december) {
		this.december = december;
	}
	
	public String getMonthlyAverage() {
		return monthlyAverage;
	}
	public void setMonthlyAverage(String monthlyAverage) {
		this.monthlyAverage = monthlyAverage;
	}
	public String getTotalAll() {
		return totalAll;
	}
	public void setTotalAll(String totalAll) {
		this.totalAll = totalAll;
	}
	
	
}
