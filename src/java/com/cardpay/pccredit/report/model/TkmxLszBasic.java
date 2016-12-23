package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

public class TkmxLszBasic extends BusinessModel{
	private String CHINESE_NAME;
	private String CARD_ID;
	private String CPMC; //产品名称
	private String FFJE; //本次发放金额
	private String HTLL; //合同利率
	private String QXRQ; //起息日起  贷款日起
	private String KHJL; //客户经理
	private String BLJG;//所属机构
	
	private String JF;//借方金额
	private String JZLL; //基准利率
	private String JZRQ;//记帐日期
	private String DKYE; //贷款余额
	private String YWJG;
	String ACCOUNT_STATUS;//贷款状态
	
	private String TELEPHONE;
	private String DF;
	private String BXHJ;
	
	String Shbj; //收回本金
	String Shlx;//收回利息
	
	String DQRQ; //到期日起
	String DKQX; //贷款期限
	String QS;//期数
	String JE;//应换本金
	String YHLX;//应还利息
	
	String hkr; //距离还款日期
	
	String JKRQ; //借款日期(申请日期)
	String APPLY_QUOTA;//申请额度
	String AUDIT_TIME;//拒绝日期
	String REFUSAL_REASON;//拒绝原因
	String DISPLAY_NAME;
	
	
	
	public TkmxLszBasic(){}


	public TkmxLszBasic(String cHINESE_NAME, String cARD_ID, String cPMC,
			String fFJE, String hTLL, String qXRQ, String kHJL, String bLJG,
			String jF, String jZLL, String jZRQ, String dKYE, String yWJG,
			String aCCOUNT_STATUS, String tELEPHONE, String dF, String bXHJ,
			String shbj, String shlx, String dQRQ, String dKQX, String qS,
			String jE, String yHLX, String hkr, String jKRQ,
			String aPPLY_QUOTA, String aUDIT_TIME, String rEFUSAL_REASON,
			String dISPLAY_NAME) {
		super();
		CHINESE_NAME = cHINESE_NAME;
		CARD_ID = cARD_ID;
		CPMC = cPMC;
		FFJE = fFJE;
		HTLL = hTLL;
		QXRQ = qXRQ;
		KHJL = kHJL;
		BLJG = bLJG;
		JF = jF;
		JZLL = jZLL;
		JZRQ = jZRQ;
		DKYE = dKYE;
		YWJG = yWJG;
		ACCOUNT_STATUS = aCCOUNT_STATUS;
		TELEPHONE = tELEPHONE;
		DF = dF;
		BXHJ = bXHJ;
		Shbj = shbj;
		Shlx = shlx;
		DQRQ = dQRQ;
		DKQX = dKQX;
		QS = qS;
		JE = jE;
		YHLX = yHLX;
		this.hkr = hkr;
		JKRQ = jKRQ;
		APPLY_QUOTA = aPPLY_QUOTA;
		AUDIT_TIME = aUDIT_TIME;
		REFUSAL_REASON = rEFUSAL_REASON;
		DISPLAY_NAME = dISPLAY_NAME;
	}








	public String getShbj() {
		return Shbj;
	}

	public void setShbj(String shbj) {
		Shbj = shbj;
	}

	public String getShlx() {
		return Shlx;
	}

	public void setShlx(String shlx) {
		Shlx = shlx;
	}

	public String getACCOUNT_STATUS() {
		return ACCOUNT_STATUS;
	}



	public void setACCOUNT_STATUS(String aCCOUNT_STATUS) {
		ACCOUNT_STATUS = aCCOUNT_STATUS;
	}



	public String getHkr() {
		return hkr;
	}

	public void setHkr(String hkr) {
		this.hkr = hkr;
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

	public String getQS() {
		return QS;
	}

	public void setQS(String qS) {
		QS = qS;
	}

	public String getTELEPHONE() {
		return TELEPHONE;
	}

	public void setTELEPHONE(String tELEPHONE) {
		TELEPHONE = tELEPHONE;
	}

	public String getDF() {
		return DF;
	}

	public void setDF(String dF) {
		DF = dF;
	}

	public String getBXHJ() {
		return BXHJ;
	}

	public void setBXHJ(String bXHJ) {
		BXHJ = bXHJ;
	}

	public String getJE() {
		return JE;
	}

	public void setJE(String jE) {
		JE = jE;
	}

	public String getYHLX() {
		return YHLX;
	}

	public void setYHLX(String yHLX) {
		YHLX = yHLX;
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
	public String getCPMC() {
		return CPMC;
	}
	public void setCPMC(String cPMC) {
		CPMC = cPMC;
	}
	public String getFFJE() {
		return FFJE;
	}
	public void setFFJE(String fFJE) {
		FFJE = fFJE;
	}
	public String getHTLL() {
		return HTLL;
	}
	public void setHTLL(String hTLL) {
		HTLL = hTLL;
	}
	
	public String getQXRQ() {
		return QXRQ;
	}


	public void setQXRQ(String qXRQ) {
		QXRQ = qXRQ;
	}


	public String getKHJL() {
		return KHJL;
	}
	public void setKHJL(String kHJL) {
		KHJL = kHJL;
	}
	public String getBLJG() {
		return BLJG;
	}
	public void setBLJG(String bLJG) {
		BLJG = bLJG;
	}

	public String getJF() {
		return JF;
	}

	public void setJF(String jF) {
		JF = jF;
	}

	public String getJZLL() {
		return JZLL;
	}

	public void setJZLL(String jZLL) {
		JZLL = jZLL;
	}

	public String getJZRQ() {
		return JZRQ;
	}

	public void setJZRQ(String jZRQ) {
		JZRQ = jZRQ;
	}

	public String getDKYE() {
		return DKYE;
	}

	public void setDKYE(String dKYE) {
		DKYE = dKYE;
	}

	public String getYWJG() {
		return YWJG;
	}

	public void setYWJG(String yWJG) {
		YWJG = yWJG;
	}

	public String getJKRQ() {
		return JKRQ;
	}

	public void setJKRQ(String jKRQ) {
		JKRQ = jKRQ;
	}

	public String getAPPLY_QUOTA() {
		return APPLY_QUOTA;
	}

	public void setAPPLY_QUOTA(String aPPLY_QUOTA) {
		APPLY_QUOTA = aPPLY_QUOTA;
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
	
}
