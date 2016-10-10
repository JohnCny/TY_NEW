/**
 * 
 */
package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BaseModel;

/**
 * 描述 ：客户账户信息
 * @author 张石树
 *
 * 2014-11-4 下午5:52:48
 */
public class CustomerAccountInfo extends BaseModel{

	private String customerId;
	
	private String accountStatus;
	
	private String overdueAmount;
	
	private String tableTurnPay;
	
	private String compoundAmount;
	
	private String currentPeriodPay;
	
	private String verificationCode;
	
	private String currentPeriodAnnuity;
	
	private String cardNumber;
	
	private String currentPeriodOverduePay;
	
	private String currentPeriodCash;
	
	private String currentPeriodScale;
	
	private String accountStatusDate;
	
	private String accountChargeAmount;
	
	private String creditAmount;
	
	private String amountProfitsInreturn;
	
	private String reimbursementDeadline;
	
	private String statementDate;
	
	private String debitAmountAdjustment;
	
	private String consumptionLogo;
	
	private String compoundInterestReceivable;
	
	private String interestReceivable;
	
	private String accruedInterestCompound;
	
	private String currentPeriodAccrued;
	
	private String currentShouldCompounded;
	
	private String nextInterestReceivable;
	
	private String recentInterestDate;
	
	private String lastPaymentDate;
	
	private String amountInstallmentPayment;
	
	private String instalmentTotalAmount;
	
	private String paymentInstallmentCredit;
	
	private String stagingRemainingPrincipal;
	
	private String overdueTotalNumber;
	
	private String repaymentAccountPeriod;
	
	private String consumptionAccountPeriod;
	
	private String transactionsAccount;
	
	private String accountQueueDate;
	
	private String amountOtherCosts;
	
	private String amountControversy;
	
	private String prePeriodAccount;
	
	private String recentLowestBill;
	
	private String overdueTime;
	
	private String tempAmount;
	
	private String tempStartDate;
	
	private String tempLossDate;
	
	private String chargeLogo;
	
	private String accountNumber;
	
	private String aging;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getTableTurnPay() {
		return tableTurnPay;
	}

	public void setTableTurnPay(String tableTurnPay) {
		this.tableTurnPay = tableTurnPay;
	}

	public String getCompoundAmount() {
		return compoundAmount;
	}

	public void setCompoundAmount(String compoundAmount) {
		this.compoundAmount = compoundAmount;
	}

	public String getCurrentPeriodPay() {
		return currentPeriodPay;
	}

	public void setCurrentPeriodPay(String currentPeriodPay) {
		this.currentPeriodPay = currentPeriodPay;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getCurrentPeriodAnnuity() {
		return currentPeriodAnnuity;
	}

	public void setCurrentPeriodAnnuity(String currentPeriodAnnuity) {
		this.currentPeriodAnnuity = currentPeriodAnnuity;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCurrentPeriodOverduePay() {
		return currentPeriodOverduePay;
	}

	public void setCurrentPeriodOverduePay(String currentPeriodOverduePay) {
		this.currentPeriodOverduePay = currentPeriodOverduePay;
	}

	public String getCurrentPeriodCash() {
		return currentPeriodCash;
	}

	public void setCurrentPeriodCash(String currentPeriodCash) {
		this.currentPeriodCash = currentPeriodCash;
	}

	public String getCurrentPeriodScale() {
		return currentPeriodScale;
	}

	public void setCurrentPeriodScale(String currentPeriodScale) {
		this.currentPeriodScale = currentPeriodScale;
	}

	public String getAccountStatusDate() {
		return accountStatusDate;
	}

	public void setAccountStatusDate(String accountStatusDate) {
		this.accountStatusDate = accountStatusDate;
	}

	public String getAccountChargeAmount() {
		return accountChargeAmount;
	}

	public void setAccountChargeAmount(String accountChargeAmount) {
		this.accountChargeAmount = accountChargeAmount;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getAmountProfitsInreturn() {
		return amountProfitsInreturn;
	}

	public void setAmountProfitsInreturn(String amountProfitsInreturn) {
		this.amountProfitsInreturn = amountProfitsInreturn;
	}

	public String getReimbursementDeadline() {
		return reimbursementDeadline;
	}

	public void setReimbursementDeadline(String reimbursementDeadline) {
		this.reimbursementDeadline = reimbursementDeadline;
	}

	public String getStatementDate() {
		return statementDate;
	}

	public void setStatementDate(String statementDate) {
		this.statementDate = statementDate;
	}

	public String getDebitAmountAdjustment() {
		return debitAmountAdjustment;
	}

	public void setDebitAmountAdjustment(String debitAmountAdjustment) {
		this.debitAmountAdjustment = debitAmountAdjustment;
	}

	public String getConsumptionLogo() {
		return consumptionLogo;
	}

	public void setConsumptionLogo(String consumptionLogo) {
		this.consumptionLogo = consumptionLogo;
	}

	public String getCompoundInterestReceivable() {
		return compoundInterestReceivable;
	}

	public void setCompoundInterestReceivable(String compoundInterestReceivable) {
		this.compoundInterestReceivable = compoundInterestReceivable;
	}

	public String getInterestReceivable() {
		return interestReceivable;
	}

	public void setInterestReceivable(String interestReceivable) {
		this.interestReceivable = interestReceivable;
	}

	public String getAccruedInterestCompound() {
		return accruedInterestCompound;
	}

	public void setAccruedInterestCompound(String accruedInterestCompound) {
		this.accruedInterestCompound = accruedInterestCompound;
	}

	public String getCurrentPeriodAccrued() {
		return currentPeriodAccrued;
	}

	public void setCurrentPeriodAccrued(String currentPeriodAccrued) {
		this.currentPeriodAccrued = currentPeriodAccrued;
	}

	public String getCurrentShouldCompounded() {
		return currentShouldCompounded;
	}

	public void setCurrentShouldCompounded(String currentShouldCompounded) {
		this.currentShouldCompounded = currentShouldCompounded;
	}

	public String getNextInterestReceivable() {
		return nextInterestReceivable;
	}

	public void setNextInterestReceivable(String nextInterestReceivable) {
		this.nextInterestReceivable = nextInterestReceivable;
	}

	public String getRecentInterestDate() {
		return recentInterestDate;
	}

	public void setRecentInterestDate(String recentInterestDate) {
		this.recentInterestDate = recentInterestDate;
	}

	public String getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(String lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public String getAmountInstallmentPayment() {
		return amountInstallmentPayment;
	}

	public void setAmountInstallmentPayment(String amountInstallmentPayment) {
		this.amountInstallmentPayment = amountInstallmentPayment;
	}

	public String getInstalmentTotalAmount() {
		return instalmentTotalAmount;
	}

	public void setInstalmentTotalAmount(String instalmentTotalAmount) {
		this.instalmentTotalAmount = instalmentTotalAmount;
	}

	public String getPaymentInstallmentCredit() {
		return paymentInstallmentCredit;
	}

	public void setPaymentInstallmentCredit(String paymentInstallmentCredit) {
		this.paymentInstallmentCredit = paymentInstallmentCredit;
	}

	public String getStagingRemainingPrincipal() {
		return stagingRemainingPrincipal;
	}

	public void setStagingRemainingPrincipal(String stagingRemainingPrincipal) {
		this.stagingRemainingPrincipal = stagingRemainingPrincipal;
	}

	public String getOverdueTotalNumber() {
		return overdueTotalNumber;
	}

	public void setOverdueTotalNumber(String overdueTotalNumber) {
		this.overdueTotalNumber = overdueTotalNumber;
	}

	public String getRepaymentAccountPeriod() {
		return repaymentAccountPeriod;
	}

	public void setRepaymentAccountPeriod(String repaymentAccountPeriod) {
		this.repaymentAccountPeriod = repaymentAccountPeriod;
	}

	public String getConsumptionAccountPeriod() {
		return consumptionAccountPeriod;
	}

	public void setConsumptionAccountPeriod(String consumptionAccountPeriod) {
		this.consumptionAccountPeriod = consumptionAccountPeriod;
	}

	public String getTransactionsAccount() {
		return transactionsAccount;
	}

	public void setTransactionsAccount(String transactionsAccount) {
		this.transactionsAccount = transactionsAccount;
	}

	public String getAccountQueueDate() {
		return accountQueueDate;
	}

	public void setAccountQueueDate(String accountQueueDate) {
		this.accountQueueDate = accountQueueDate;
	}

	public String getAmountOtherCosts() {
		return amountOtherCosts;
	}

	public void setAmountOtherCosts(String amountOtherCosts) {
		this.amountOtherCosts = amountOtherCosts;
	}

	public String getAmountControversy() {
		return amountControversy;
	}

	public void setAmountControversy(String amountControversy) {
		this.amountControversy = amountControversy;
	}

	public String getPrePeriodAccount() {
		return prePeriodAccount;
	}

	public void setPrePeriodAccount(String prePeriodAccount) {
		this.prePeriodAccount = prePeriodAccount;
	}

	public String getRecentLowestBill() {
		return recentLowestBill;
	}

	public void setRecentLowestBill(String recentLowestBill) {
		this.recentLowestBill = recentLowestBill;
	}

	public String getOverdueTime() {
		return overdueTime;
	}

	public void setOverdueTime(String overdueTime) {
		this.overdueTime = overdueTime;
	}

	public String getTempAmount() {
		return tempAmount;
	}

	public void setTempAmount(String tempAmount) {
		this.tempAmount = tempAmount;
	}

	public String getTempStartDate() {
		return tempStartDate;
	}

	public void setTempStartDate(String tempStartDate) {
		this.tempStartDate = tempStartDate;
	}

	public String getTempLossDate() {
		return tempLossDate;
	}

	public void setTempLossDate(String tempLossDate) {
		this.tempLossDate = tempLossDate;
	}

	public String getChargeLogo() {
		return chargeLogo;
	}

	public void setChargeLogo(String chargeLogo) {
		this.chargeLogo = chargeLogo;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}
	
}
