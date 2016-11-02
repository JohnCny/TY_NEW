package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;


/**
 * @author chenzhifang
 *
 * 2014-11-27下午5:02:29
 */
public class CardStatistical extends BusinessModel{
	private static final long serialVersionUID = -2298923110877624650L;
	
	private String rowIndex;
	
	private String name;
	
	private String id;
	
	private String bSendCardNumber;
	
	private String bAcceptCardNumber;
	
	private String bActivateCardNumber;
	
	private String bNoActivateCardNumber;
	
	private String bActiveRate;
	
	private String rSendCardNumber;
	
	private String rAcceptCardNumber;
	
	private String rActivateCardNumber;
	
	private String rNoActivateCardNumber;
	
	private String rActiveRate;
	
	private String addSendCardNumber;
	
	private String addAcceptCardNumber;
	
	private String addActivateCardNumber;
	
	private String addNoActivateCardNumber;
	
	private String addActiveRate;

	public String getbSendCardNumber() {
		return bSendCardNumber;
	}

	public void setbSendCardNumber(String bSendCardNumber) {
		this.bSendCardNumber = bSendCardNumber;
	}

	public String getbAcceptCardNumber() {
		return bAcceptCardNumber;
	}

	public void setbAcceptCardNumber(String bAcceptCardNumber) {
		this.bAcceptCardNumber = bAcceptCardNumber;
	}

	public String getbActivateCardNumber() {
		return bActivateCardNumber;
	}

	public void setbActivateCardNumber(String bActivateCardNumber) {
		this.bActivateCardNumber = bActivateCardNumber;
	}

	public String getbNoActivateCardNumber() {
		return bNoActivateCardNumber;
	}

	public void setbNoActivateCardNumber(String bNoActivateCardNumber) {
		this.bNoActivateCardNumber = bNoActivateCardNumber;
	}

	public String getrSendCardNumber() {
		return rSendCardNumber;
	}

	public void setrSendCardNumber(String rSendCardNumber) {
		this.rSendCardNumber = rSendCardNumber;
	}

	public String getrAcceptCardNumber() {
		return rAcceptCardNumber;
	}

	public void setrAcceptCardNumber(String rAcceptCardNumber) {
		this.rAcceptCardNumber = rAcceptCardNumber;
	}

	public String getrActivateCardNumber() {
		return rActivateCardNumber;
	}

	public void setrActivateCardNumber(String rActivateCardNumber) {
		this.rActivateCardNumber = rActivateCardNumber;
	}

	public String getrNoActivateCardNumber() {
		return rNoActivateCardNumber;
	}

	public void setrNoActivateCardNumber(String rNoActivateCardNumber) {
		this.rNoActivateCardNumber = rNoActivateCardNumber;
	}

	public String getAddSendCardNumber() {
		return addSendCardNumber;
	}

	public void setAddSendCardNumber(String addSendCardNumber) {
		this.addSendCardNumber = addSendCardNumber;
	}

	public String getAddAcceptCardNumber() {
		return addAcceptCardNumber;
	}

	public void setAddAcceptCardNumber(String addAcceptCardNumber) {
		this.addAcceptCardNumber = addAcceptCardNumber;
	}

	public String getAddActivateCardNumber() {
		return addActivateCardNumber;
	}

	public void setAddActivateCardNumber(String addActivateCardNumber) {
		this.addActivateCardNumber = addActivateCardNumber;
	}

	public String getAddNoActivateCardNumber() {
		return addNoActivateCardNumber;
	}

	public void setAddNoActivateCardNumber(String addNoActivateCardNumber) {
		this.addNoActivateCardNumber = addNoActivateCardNumber;
	}

	public String getbActiveRate() {
		return bActiveRate;
	}

	public void setbActiveRate(String bActiveRate) {
		this.bActiveRate = bActiveRate;
	}

	public String getrActiveRate() {
		return rActiveRate;
	}

	public void setrActiveRate(String rActiveRate) {
		this.rActiveRate = rActiveRate;
	}

	public String getAddActiveRate() {
		return addActiveRate;
	}

	public void setAddActiveRate(String addActiveRate) {
		this.addActiveRate = addActiveRate;
	}

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
