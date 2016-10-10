package com.cardpay.pccredit.divisional.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

public class DivisionalTransfer extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String chineseName;
	private String cardType;
	private String cardId;
	private String residentialAddress;
	private String telephone;
	private String divisionalStatus;
	private String divisionalResult;
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getResidentialAddress() {
		return residentialAddress;
	}
	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDivisionalStatus() {
		return divisionalStatus;
	}
	public void setDivisionalStatus(String divisionalStatus) {
		this.divisionalStatus = divisionalStatus;
	}
	public String getDivisionalResult() {
		return divisionalResult;
	}
	public void setDivisionalResult(String divisionalResult) {
		this.divisionalResult = divisionalResult;
	}
	
}
