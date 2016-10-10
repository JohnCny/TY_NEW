package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author chenzhifang
 *
 * 2014-11-3下午3:46:41
 */
@ModelParam(table = "card_information")
public class CardInformation extends BusinessModel {
	private static final long serialVersionUID = 1L;

	private String customerId;
	
	private String productId;
	
	private String cardNumber;
	
	private String cardValidityPeriod;
	
	private String principal;
	
	private String numbererest;
	
	private String billDate;
	
	private String creditLine;
	
	private String availableCredit;
	
	private String usedLimit;
	
	private String cashAdvanceLimit;
	
	private String overdueNumber;
	
	private String cardStatus;

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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardValidityPeriod() {
		return cardValidityPeriod;
	}

	public void setCardValidityPeriod(String cardValidityPeriod) {
		this.cardValidityPeriod = cardValidityPeriod;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getNumbererest() {
		return numbererest;
	}

	public void setNumbererest(String numbererest) {
		this.numbererest = numbererest;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}

	public String getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(String availableCredit) {
		this.availableCredit = availableCredit;
	}

	public String getUsedLimit() {
		return usedLimit;
	}

	public void setUsedLimit(String usedLimit) {
		this.usedLimit = usedLimit;
	}

	public String getCashAdvanceLimit() {
		return cashAdvanceLimit;
	}

	public void setCashAdvanceLimit(String cashAdvanceLimit) {
		this.cashAdvanceLimit = cashAdvanceLimit;
	}

	public String getOverdueNumber() {
		return overdueNumber;
	}

	public void setOverdueNumber(String overdueNumber) {
		this.overdueNumber = overdueNumber;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
}
