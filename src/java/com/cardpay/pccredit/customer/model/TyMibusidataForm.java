package com.cardpay.pccredit.customer.model;


import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 台帐临时表Form
 * @author zb
 */
@ModelParam(table = "Mibusidata",generator=IDType.uuid32)
public class TyMibusidataForm extends BusinessModel {
	private String YWBH;
	private String ID;
	private String KHH;
	private String KHMC;
	private String ZJHM;
	private String SSBM;
	private String SJ;
	private String SXED;
	private String GHJL;
	private String LOANDATE;
	private String DQRQ;
	private String DKQX;
	private String JZLL;
	private String REQLMT;
	private String DELAYINTERESTDAYS;
	private String DELAYAMTDAYS;
	private String DLAYDT;
	private String SHBJ;
	private String JKJE;
	private String KSQXRQ;
	private String PAYDEBT;
	private String DKYE;
	private String BNQX;
	private String BWQX;
	private String BALAMT;
	private String YXYE;
	private String WJFL3;
	private String WJFL4;
	private String WJFL5;
	private String OPERDATETIME;
	private String USERID;
	private String DISPLAY_NAME;
	private String YWJG;
	
	private String reqlmtsum;//贷款总额
	
	
	public String getReqlmtsum() {
		return reqlmtsum;
	}
	public void setReqlmtsum(String reqlmtsum) {
		this.reqlmtsum = reqlmtsum;
	}
	public String getYWBH() {
		return YWBH;
	}
	public void setYWBH(String yWBH) {
		YWBH = yWBH;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getKHH() {
		return KHH;
	}
	public void setKHH(String kHH) {
		KHH = kHH;
	}
	public String getKHMC() {
		return KHMC;
	}
	public void setKHMC(String kHMC) {
		KHMC = kHMC;
	}
	public String getZJHM() {
		return ZJHM;
	}
	public void setZJHM(String zJHM) {
		ZJHM = zJHM;
	}
	public String getSSBM() {
		return SSBM;
	}
	public void setSSBM(String sSBM) {
		SSBM = sSBM;
	}
	public String getSJ() {
		return SJ;
	}
	public void setSJ(String sJ) {
		SJ = sJ;
	}
	public String getSXED() {
		return SXED;
	}
	public void setSXED(String sXED) {
		SXED = sXED;
	}
	public String getGHJL() {
		return GHJL;
	}
	public void setGHJL(String gHJL) {
		GHJL = gHJL;
	}
	public String getLOANDATE() {
		return LOANDATE;
	}
	public void setLOANDATE(String lOANDATE) {
		LOANDATE = lOANDATE;
	}
	public String getDQRQ() {
		return DQRQ;
	}
	public void setDQRQ(String dQRQ) {
		DQRQ = dQRQ;
	}
	public String getDKQX() {
		return DKQX;
	}
	public void setDKQX(String dKQX) {
		DKQX = dKQX;
	}
	public String getJZLL() {
		return JZLL;
	}
	public void setJZLL(String jZLL) {
		JZLL = jZLL;
	}
	public String getREQLMT() {
		return REQLMT;
	}
	public void setREQLMT(String rEQLMT) {
		REQLMT = rEQLMT;
	}
	public String getDELAYINTERESTDAYS() {
		return DELAYINTERESTDAYS;
	}
	public void setDELAYINTERESTDAYS(String dELAYINTERESTDAYS) {
		DELAYINTERESTDAYS = dELAYINTERESTDAYS;
	}
	public String getDELAYAMTDAYS() {
		return DELAYAMTDAYS;
	}
	public void setDELAYAMTDAYS(String dELAYAMTDAYS) {
		DELAYAMTDAYS = dELAYAMTDAYS;
	}
	public String getDLAYDT() {
		return DLAYDT;
	}
	public void setDLAYDT(String dLAYDT) {
		DLAYDT = dLAYDT;
	}
	public String getSHBJ() {
		return SHBJ;
	}
	public void setSHBJ(String sHBJ) {
		SHBJ = sHBJ;
	}
	public String getJKJE() {
		return JKJE;
	}
	public void setJKJE(String jKJE) {
		JKJE = jKJE;
	}
	public String getKSQXRQ() {
		return KSQXRQ;
	}
	public void setKSQXRQ(String kSQXRQ) {
		KSQXRQ = kSQXRQ;
	}
	public String getPAYDEBT() {
		return PAYDEBT;
	}
	public void setPAYDEBT(String pAYDEBT) {
		PAYDEBT = pAYDEBT;
	}
	public String getDKYE() {
		return DKYE;
	}
	public void setDKYE(String dKYE) {
		DKYE = dKYE;
	}
	public String getBNQX() {
		return BNQX;
	}
	public void setBNQX(String bNQX) {
		BNQX = bNQX;
	}
	public String getBWQX() {
		return BWQX;
	}
	public void setBWQX(String bWQX) {
		BWQX = bWQX;
	}
	public String getBALAMT() {
		return BALAMT;
	}
	public void setBALAMT(String bALAMT) {
		BALAMT = bALAMT;
	}
	public String getYXYE() {
		return YXYE;
	}
	public void setYXYE(String yXYE) {
		YXYE = yXYE;
	}
	public String getWJFL3() {
		return WJFL3;
	}
	public void setWJFL3(String wJFL3) {
		WJFL3 = wJFL3;
	}
	public String getWJFL4() {
		return WJFL4;
	}
	public void setWJFL4(String wJFL4) {
		WJFL4 = wJFL4;
	}
	public String getWJFL5() {
		return WJFL5;
	}
	public void setWJFL5(String wJFL5) {
		WJFL5 = wJFL5;
	}
	public String getOPERDATETIME() {
		return OPERDATETIME;
	}
	public void setOPERDATETIME(String oPERDATETIME) {
		OPERDATETIME = oPERDATETIME;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getDISPLAY_NAME() {
		return DISPLAY_NAME;
	}
	public void setDISPLAY_NAME(String dISPLAY_NAME) {
		DISPLAY_NAME = dISPLAY_NAME;
	}
	public String getYWJG() {
		return YWJG;
	}
	public void setYWJG(String yWJG) {
		YWJG = yWJG;
	}
	
	 
	
}
