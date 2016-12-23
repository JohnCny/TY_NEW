package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class CustomerHmd extends BusinessFilter{

	/**
	 * 
	 */
	private String name;
	private String cardId;
	private String comefrom;

	public CustomerHmd(String name, String cardId, String comefrom) {
		super();
		this.name = name;
		this.cardId = cardId;
		this.comefrom = comefrom;
	}

	public CustomerHmd() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getComefrom() {
		return comefrom;
	}

	public void setComefrom(String comefrom) {
		this.comefrom = comefrom;
	}

	
}
