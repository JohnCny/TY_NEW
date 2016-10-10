package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "make_card")
public class MakeCard extends BusinessModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8470111754965975277L;
	private String customerName;
	private String identityType;
	private String customerIdentity;
	private String cardNumber;
	private String cardOrganization;
	private String cardOrganizationStatus;
	private String cardCustomerStatus;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardOrganizationStatus() {
		return cardOrganizationStatus;
	}
	public void setCardOrganizationStatus(String cardOrganizationStatus) {
		this.cardOrganizationStatus = cardOrganizationStatus;
	}
	public String getCardCustomerStatus() {
		return cardCustomerStatus;
	}
	public void setCardCustomerStatus(String cardCustomerStatus) {
		this.cardCustomerStatus = cardCustomerStatus;
	}
	public String getCustomerIdentity() {
		return customerIdentity;
	}
	public void setCustomerIdentity(String customerIdentity) {
		this.customerIdentity = customerIdentity;
	}
	public String getCardOrganization() {
		return cardOrganization;
	}
	public void setCardOrganization(String cardOrganization) {
		this.cardOrganization = cardOrganization;
	}
	public String getIdentityType() {
		return identityType;
	}
	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}
	
}