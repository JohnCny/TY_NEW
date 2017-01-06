package com.cardpay.pccredit.postLoan.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
/**
 * 台帐表Form
 * @author sc
 */
public class RefuseMibusidata extends BusinessModel {
	 private String id;
	 private String ywbh;
	 private String CHINESE_NAME;
	 private String CARD_ID;
	 private String TELEPHONE;
	 private String industry;
	 private String spmc;
	 private String RESIDENTIAL_ADDRESS;
	 private String JKJE;
	 private String JKRQ;
	 private String AUDIT_TIME;
	 private String REFUSAL_REASON;
	 private String DISPLAY_NAME;
	 private String PROCESS_OP_STATUS;
	 
	
	 public RefuseMibusidata(){}
	
	public RefuseMibusidata(String id, String ywbh, String cHINESE_NAME,
			String cARD_ID, String tELEPHONE, String industry, String spmc,
			String rESIDENTIAL_ADDRESS, String jKJE, String jKRQ,
			String aUDIT_TIME, String rEFUSAL_REASON, String dISPLAY_NAME,
			String pROCESS_OP_STATUS) {
		this.id = id;
		this.ywbh = ywbh;
		CHINESE_NAME = cHINESE_NAME;
		CARD_ID = cARD_ID;
		TELEPHONE = tELEPHONE;
		this.industry = industry;
		this.spmc = spmc;
		RESIDENTIAL_ADDRESS = rESIDENTIAL_ADDRESS;
		JKJE = jKJE;
		JKRQ = jKRQ;
		AUDIT_TIME = aUDIT_TIME;
		REFUSAL_REASON = rEFUSAL_REASON;
		DISPLAY_NAME = dISPLAY_NAME;
		PROCESS_OP_STATUS = pROCESS_OP_STATUS;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getCHINESE_NAME() {
		return CHINESE_NAME;
	}
	public void setCHINESE_NAME(String cHINESE_NAME) {
		CHINESE_NAME = cHINESE_NAME;
	}
	public String getCARD_ID() {
		return CARD_ID;
	}
	public void setCARD_ID(String cARD_ID) {
		CARD_ID = cARD_ID;
	}
	public String getTELEPHONE() {
		return TELEPHONE;
	}
	public void setTELEPHONE(String tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getSpmc() {
		return spmc;
	}
	public void setSpmc(String spmc) {
		this.spmc = spmc;
	}
	public String getRESIDENTIAL_ADDRESS() {
		return RESIDENTIAL_ADDRESS;
	}
	public void setRESIDENTIAL_ADDRESS(String rESIDENTIAL_ADDRESS) {
		RESIDENTIAL_ADDRESS = rESIDENTIAL_ADDRESS;
	}
	public String getJKJE() {
		return JKJE;
	}
	public void setJKJE(String jKJE) {
		JKJE = jKJE;
	}
	public String getJKRQ() {
		return JKRQ;
	}
	public void setJKRQ(String jKRQ) {
		JKRQ = jKRQ;
	}
	public String getAUDIT_TIME() {
		return AUDIT_TIME;
	}
	public void setAUDIT_TIME(String aUDIT_TIME) {
		AUDIT_TIME = aUDIT_TIME;
	}
	public String getREFUSAL_REASON() {
		return REFUSAL_REASON;
	}
	public void setREFUSAL_REASON(String rEFUSAL_REASON) {
		REFUSAL_REASON = rEFUSAL_REASON;
	}
	public String getDISPLAY_NAME() {
		return DISPLAY_NAME;
	}
	public void setDISPLAY_NAME(String dISPLAY_NAME) {
		DISPLAY_NAME = dISPLAY_NAME;
	}
	public String getPROCESS_OP_STATUS() {
		return PROCESS_OP_STATUS;
	}
	public void setPROCESS_OP_STATUS(String pROCESS_OP_STATUS) {
		PROCESS_OP_STATUS = pROCESS_OP_STATUS;
	}
	 
	 
	 
	  
}
