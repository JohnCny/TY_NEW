/**
 * 
 */
package com.cardpay.pccredit.report.model;

/**
 * @author shaoming
 *
 * 2014年12月22日   下午4:55:37
 */
public class IntelligentAccountReport {
	private String cardNbr;
	private String activationStatus;
	private String principalOverdraft;
	private String interest;
	private String annualFee;
	private String totalAmountOverdraft;
	private String availableLimit;
	private String lastTotalAmountOverdraft;
	private String lastPrincipalOverdraft;
	private String lastInterest;
	private String paybackAccount;
	private String overdueAmount;
	private String overdueTime;
	public String getLastTotalAmountOverdraft() {
		return lastTotalAmountOverdraft;
	}
	public void setLastTotalAmountOverdraft(String lastTotalAmountOverdraft) {
		this.lastTotalAmountOverdraft = lastTotalAmountOverdraft;
	}
	public String getLastPrincipalOverdraft() {
		return lastPrincipalOverdraft;
	}
	public void setLastPrincipalOverdraft(String lastPrincipalOverdraft) {
		this.lastPrincipalOverdraft = lastPrincipalOverdraft;
	}
	public String getLastInterest() {
		return lastInterest;
	}
	public void setLastInterest(String lastInterest) {
		this.lastInterest = lastInterest;
	}
	public String getPaybackAccount() {
		return paybackAccount;
	}
	public void setPaybackAccount(String paybackAccount) {
		this.paybackAccount = paybackAccount;
	}
	public String getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	public String getOverdueTime() {
		return overdueTime;
	}
	public void setOverdueTime(String overdueTime) {
		this.overdueTime = overdueTime;
	}
	public String getCardNbr() {
		return cardNbr;
	}
	public void setCardNbr(String cardNbr) {
		this.cardNbr = cardNbr;
	}
	public String getActivationStatus() {
		return activationStatus;
	}
	public void setActivationStatus(String activationStatus) {
		this.activationStatus = activationStatus;
	}
	public String getPrincipalOverdraft() {
		return principalOverdraft;
	}
	public void setPrincipalOverdraft(String principalOverdraft) {
		this.principalOverdraft = principalOverdraft;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getAnnualFee() {
		return annualFee;
	}
	public void setAnnualFee(String annualFee) {
		this.annualFee = annualFee;
	}
	public String getTotalAmountOverdraft() {
		return totalAmountOverdraft;
	}
	public void setTotalAmountOverdraft(String totalAmountOverdraft) {
		this.totalAmountOverdraft = totalAmountOverdraft;
	}
	public String getAvailableLimit() {
		return availableLimit;
	}
	public void setAvailableLimit(String availableLimit) {
		this.availableLimit = availableLimit;
	}
	
}
