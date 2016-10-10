package com.cardpay.pccredit.intopieces.filter;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class MakeCardFilter extends BusinessFilter{
	private String cardNumber;
	
	private String cardOrganization;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardOrganization() {
		return cardOrganization;
	}

	public void setCardOrganization(String cardOrganization) {
		this.cardOrganization = cardOrganization;
	}

}
