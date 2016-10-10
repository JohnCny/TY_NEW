package com.cardpay.pccredit.intopieces.filter;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class CustomerApplicationInfoFilter extends BusinessFilter {

	private String id;

	private String customerId;

	private String productId;

	private String applyQuota;

	private String finalApproval;

	private String actualQuote;

	private String temporaryQuota;

	private String cashAdvanceProportion;

	private String cardStatus;

	private String accountStatus;

	private String billingDate;

	private String repaymentAgreement;

	private String automaticRepaymentAgreement;

	private String customerRiskRating;

	private String aging;

	private String debitWay;

	private String repaymentCardNumber;

	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId == null ? null : customerId.trim();
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getApplyQuota() {
		return applyQuota;
	}

	public void setApplyQuota(String applyQuota) {
		this.applyQuota = applyQuota == null ? null : applyQuota.trim();
	}

	public String getFinalApproval() {
		return finalApproval;
	}

	public void setFinalApproval(String finalApproval) {
		this.finalApproval = finalApproval == null ? null : finalApproval
				.trim();
	}

	public String getActualQuote() {
		return actualQuote;
	}

	public void setActualQuote(String actualQuote) {
		this.actualQuote = actualQuote == null ? null : actualQuote.trim();
	}

	public String getTemporaryQuota() {
		return temporaryQuota;
	}

	public void setTemporaryQuota(String temporaryQuota) {
		this.temporaryQuota = temporaryQuota == null ? null : temporaryQuota
				.trim();
	}

	public String getCashAdvanceProportion() {
		return cashAdvanceProportion;
	}

	public void setCashAdvanceProportion(String cashAdvanceProportion) {
		this.cashAdvanceProportion = cashAdvanceProportion == null ? null
				: cashAdvanceProportion.trim();
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus == null ? null : cardStatus.trim();
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus == null ? null : accountStatus
				.trim();
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate == null ? null : billingDate.trim();
	}

	public String getRepaymentAgreement() {
		return repaymentAgreement;
	}

	public void setRepaymentAgreement(String repaymentAgreement) {
		this.repaymentAgreement = repaymentAgreement == null ? null
				: repaymentAgreement.trim();
	}

	public String getAutomaticRepaymentAgreement() {
		return automaticRepaymentAgreement;
	}

	public void setAutomaticRepaymentAgreement(
			String automaticRepaymentAgreement) {
		this.automaticRepaymentAgreement = automaticRepaymentAgreement == null ? null
				: automaticRepaymentAgreement.trim();
	}

	public String getCustomerRiskRating() {
		return customerRiskRating;
	}

	public void setCustomerRiskRating(String customerRiskRating) {
		this.customerRiskRating = customerRiskRating == null ? null
				: customerRiskRating.trim();
	}

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging == null ? null : aging.trim();
	}

	public String getDebitWay() {
		return debitWay;
	}

	public void setDebitWay(String debitWay) {
		this.debitWay = debitWay == null ? null : debitWay.trim();
	}

	public String getRepaymentCardNumber() {
		return repaymentCardNumber;
	}

	public void setRepaymentCardNumber(String repaymentCardNumber) {
		this.repaymentCardNumber = repaymentCardNumber == null ? null
				: repaymentCardNumber.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

}
