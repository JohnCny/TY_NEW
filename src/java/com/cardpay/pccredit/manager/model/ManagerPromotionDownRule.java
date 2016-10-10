package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 
 * @author 宋辰
 * 2015-11-21 下午4:01:17
 */
@ModelParam(table = "TY_PROMOTION_DOWN_RULES",generator=IDType.uuid32)
public class ManagerPromotionDownRule extends BusinessModel{
	
	private static final long serialVersionUID = 1L;
	
	private String	initialLevel	;//	初始层级
	private String	quarterAverageCreditBalance	;//	季度日均用信余额
	private Integer	tubeNumber	;//	管户数（户）
	private String	overLoanRatio	;//	逾期贷款率
	private String	kpiScore	;//	月均KPI得分
	public String getInitialLevel() {
		return initialLevel;
	}
	public void setInitialLevel(String initialLevel) {
		this.initialLevel = initialLevel;
	}
	public String getQuarterAverageCreditBalance() {
		return quarterAverageCreditBalance;
	}
	public void setQuarterAverageCreditBalance(String quarterAverageCreditBalance) {
		this.quarterAverageCreditBalance = quarterAverageCreditBalance;
	}
	public Integer getTubeNumber() {
		return tubeNumber;
	}
	public void setTubeNumber(Integer tubeNumber) {
		this.tubeNumber = tubeNumber;
	}
	public String getOverLoanRatio() {
		return overLoanRatio;
	}
	public void setOverLoanRatio(String overLoanRatio) {
		this.overLoanRatio = overLoanRatio;
	}
	public String getKpiScore() {
		return kpiScore;
	}
	public void setKpiScore(String kpiScore) {
		this.kpiScore = kpiScore;
	}
	
	
	
}
