package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customer_account_data")
public class CustomerAccountData extends BusinessModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2536624686699302430L;

	private String mainApplicationFormId;
	private String oaccountMybank;
	private String creditCard;
	private String howManyCard;
	private String maximumAmount;
	private String payMybank;

	public String getMainApplicationFormId() {
		return mainApplicationFormId;
	}

	public void setMainApplicationFormId(String mainApplicationFormId) {
		this.mainApplicationFormId = mainApplicationFormId;
	}

	public String getOaccountMybank() {
		return oaccountMybank;
	}

	public void setOaccountMybank(String oaccountMybank) {
		this.oaccountMybank = oaccountMybank;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getHowManyCard() {
		return howManyCard;
	}

	public void setHowManyCard(String howManyCard) {
		this.howManyCard = howManyCard;
	}

	public String getMaximumAmount() {
		return maximumAmount;
	}

	public void setMaximumAmount(String maximumAmount) {
		this.maximumAmount = maximumAmount;
	}

	public String getPayMybank() {
		return payMybank;
	}

	public void setPayMybank(String payMybank) {
		this.payMybank = payMybank;
	}

}