/**
 * 
 */
package com.cardpay.pccredit.manager.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 
 * 描述 ：客户经理评估信息From
 * 
 * @author 张石树
 * 
 * 2014-11-13 下午2:16:06
 */
public class ManagerAssessmentScoreForm extends BaseForm {

	private Double creditUtilizationRate;
	
	private Double numbererestRecoveryRate;
	
	private Double overdueRate;
	
	private Double lateRecoveryRate;
	
	private Double overdueBalanceRate;
	
	private Double defectiveLoansRate;
	
	private Double creditUtilizationScore;
	
	private Double numbererestRecoveryScore;
	
	private Double overdueScore;
	
	private Double lateRecoveryScore;
	
	private Double overdueBalanceScore;
	
	private Double defectiveLoansScore;
	
	private Double customerPotentialValue;
	
	private Double selfTrainingAbility;
	
	private Double teamCooperationAbility;
	
	private Double customerManagementAbility;
	
	private Double orgaCoordinationAbility;
	
	private Double deposit;
	
	private Double lastDeposit;
	
	private Double bankingProduct;
	
	private Double lastBankingProduct;
	
	private Double totalScore;
	
	private String totalEvaluation;
	
	private String otherEvaluation;
	
	private String assessed;
	
	private Date assessedConfirmDate;
	
	private String assessor;
	
	private Date assessmentDate;
	
	private String assessedName;
	
	private String assessorName;
    
	//用于显示评估标准和打分范围
	private String creditUtilizationRatePgbz;
	private String creditUtilizationRateBetScore;
	private String numbererestRecoveryRatePgbz;
	private String numbererestRecoveryRateBetScore;
	private String overdueRatePgbz;
	private String overdueRateBetScore;
	private String lateRecoveryRatePgbz;
	private String lateRecoveryRateBetScore;
	private String overdueBalanceRatePgbz;
	private String overdueBalanceRateBetScore;
	private String defectiveLoansRatePgbz;
	private String defectiveLoansRateBetScore;
	
	private Integer dataYear;
	
	private Integer dataMonth;
	
	private String customerManagerLevel;
	
	public Double getCreditUtilizationRate() {
		return creditUtilizationRate;
	}

	public void setCreditUtilizationRate(Double creditUtilizationRate) {
		this.creditUtilizationRate = creditUtilizationRate;
	}

	public Double getNumbererestRecoveryRate() {
		return numbererestRecoveryRate;
	}

	public void setNumbererestRecoveryRate(Double numbererestRecoveryRate) {
		this.numbererestRecoveryRate = numbererestRecoveryRate;
	}

	public Double getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(Double overdueRate) {
		this.overdueRate = overdueRate;
	}

	public Double getLateRecoveryRate() {
		return lateRecoveryRate;
	}

	public void setLateRecoveryRate(Double lateRecoveryRate) {
		this.lateRecoveryRate = lateRecoveryRate;
	}

	public Double getOverdueBalanceRate() {
		return overdueBalanceRate;
	}

	public void setOverdueBalanceRate(Double overdueBalanceRate) {
		this.overdueBalanceRate = overdueBalanceRate;
	}

	public Double getCreditUtilizationScore() {
		return creditUtilizationScore;
	}

	public void setCreditUtilizationScore(Double creditUtilizationScore) {
		this.creditUtilizationScore = creditUtilizationScore;
	}

	public Double getNumbererestRecoveryScore() {
		return numbererestRecoveryScore;
	}

	public void setNumbererestRecoveryScore(Double numbererestRecoveryScore) {
		this.numbererestRecoveryScore = numbererestRecoveryScore;
	}

	public Double getOverdueScore() {
		return overdueScore;
	}

	public void setOverdueScore(Double overdueScore) {
		this.overdueScore = overdueScore;
	}

	public Double getLateRecoveryScore() {
		return lateRecoveryScore;
	}

	public void setLateRecoveryScore(Double lateRecoveryScore) {
		this.lateRecoveryScore = lateRecoveryScore;
	}

	public Double getOverdueBalanceScore() {
		return overdueBalanceScore;
	}

	public void setOverdueBalanceScore(Double overdueBalanceScore) {
		this.overdueBalanceScore = overdueBalanceScore;
	}

	public Double getDefectiveLoansScore() {
		return defectiveLoansScore;
	}

	public void setDefectiveLoansScore(Double defectiveLoansScore) {
		this.defectiveLoansScore = defectiveLoansScore;
	}

	public Double getDefectiveLoansRate() {
		return defectiveLoansRate;
	}

	public void setDefectiveLoansRate(Double defectiveLoansRate) {
		this.defectiveLoansRate = defectiveLoansRate;
	}

	public Double getCustomerPotentialValue() {
		return customerPotentialValue;
	}

	public void setCustomerPotentialValue(Double customerPotentialValue) {
		this.customerPotentialValue = customerPotentialValue;
	}

	public Double getSelfTrainingAbility() {
		return selfTrainingAbility;
	}

	public void setSelfTrainingAbility(Double selfTrainingAbility) {
		this.selfTrainingAbility = selfTrainingAbility;
	}

	public Double getTeamCooperationAbility() {
		return teamCooperationAbility;
	}

	public void setTeamCooperationAbility(Double teamCooperationAbility) {
		this.teamCooperationAbility = teamCooperationAbility;
	}

	public Double getCustomerManagementAbility() {
		return customerManagementAbility;
	}

	public void setCustomerManagementAbility(Double customerManagementAbility) {
		this.customerManagementAbility = customerManagementAbility;
	}

	public Double getOrgaCoordinationAbility() {
		return orgaCoordinationAbility;
	}

	public void setOrgaCoordinationAbility(Double orgaCoordinationAbility) {
		this.orgaCoordinationAbility = orgaCoordinationAbility;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getLastDeposit() {
		return lastDeposit;
	}

	public void setLastDeposit(Double lastDeposit) {
		this.lastDeposit = lastDeposit;
	}

	public Double getBankingProduct() {
		return bankingProduct;
	}

	public void setBankingProduct(Double bankingProduct) {
		this.bankingProduct = bankingProduct;
	}

	public Double getLastBankingProduct() {
		return lastBankingProduct;
	}

	public void setLastBankingProduct(Double lastBankingProduct) {
		this.lastBankingProduct = lastBankingProduct;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public String getTotalEvaluation() {
		return totalEvaluation;
	}

	public void setTotalEvaluation(String totalEvaluation) {
		this.totalEvaluation = totalEvaluation;
	}

	public String getOtherEvaluation() {
		return otherEvaluation;
	}

	public void setOtherEvaluation(String otherEvaluation) {
		this.otherEvaluation = otherEvaluation;
	}

	public String getAssessed() {
		return assessed;
	}

	public void setAssessed(String assessed) {
		this.assessed = assessed;
	}

	public Date getAssessedConfirmDate() {
		return assessedConfirmDate;
	}

	public void setAssessedConfirmDate(Date assessedConfirmDate) {
		this.assessedConfirmDate = assessedConfirmDate;
	}

	public String getAssessor() {
		return assessor;
	}

	public void setAssessor(String assessor) {
		this.assessor = assessor;
	}

	public Date getAssessmentDate() {
		return assessmentDate;
	}

	public void setAssessmentDate(Date assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	public String getAssessedName() {
		return assessedName;
	}

	public void setAssessedName(String assessedName) {
		this.assessedName = assessedName;
	}

	public String getCreditUtilizationRatePgbz() {
		return creditUtilizationRatePgbz;
	}

	public void setCreditUtilizationRatePgbz(String creditUtilizationRatePgbz) {
		this.creditUtilizationRatePgbz = creditUtilizationRatePgbz;
	}

	public String getCreditUtilizationRateBetScore() {
		return creditUtilizationRateBetScore;
	}

	public void setCreditUtilizationRateBetScore(
			String creditUtilizationRateBetScore) {
		this.creditUtilizationRateBetScore = creditUtilizationRateBetScore;
	}

	public String getNumbererestRecoveryRatePgbz() {
		return numbererestRecoveryRatePgbz;
	}

	public void setNumbererestRecoveryRatePgbz(String numbererestRecoveryRatePgbz) {
		this.numbererestRecoveryRatePgbz = numbererestRecoveryRatePgbz;
	}

	public String getNumbererestRecoveryRateBetScore() {
		return numbererestRecoveryRateBetScore;
	}

	public void setNumbererestRecoveryRateBetScore(
			String numbererestRecoveryRateBetScore) {
		this.numbererestRecoveryRateBetScore = numbererestRecoveryRateBetScore;
	}

	public String getOverdueRatePgbz() {
		return overdueRatePgbz;
	}

	public void setOverdueRatePgbz(String overdueRatePgbz) {
		this.overdueRatePgbz = overdueRatePgbz;
	}

	public String getOverdueRateBetScore() {
		return overdueRateBetScore;
	}

	public void setOverdueRateBetScore(String overdueRateBetScore) {
		this.overdueRateBetScore = overdueRateBetScore;
	}

	public String getLateRecoveryRatePgbz() {
		return lateRecoveryRatePgbz;
	}

	public void setLateRecoveryRatePgbz(String lateRecoveryRatePgbz) {
		this.lateRecoveryRatePgbz = lateRecoveryRatePgbz;
	}

	public String getLateRecoveryRateBetScore() {
		return lateRecoveryRateBetScore;
	}

	public void setLateRecoveryRateBetScore(String lateRecoveryRateBetScore) {
		this.lateRecoveryRateBetScore = lateRecoveryRateBetScore;
	}

	public String getOverdueBalanceRatePgbz() {
		return overdueBalanceRatePgbz;
	}

	public void setOverdueBalanceRatePgbz(String overdueBalanceRatePgbz) {
		this.overdueBalanceRatePgbz = overdueBalanceRatePgbz;
	}

	public String getOverdueBalanceRateBetScore() {
		return overdueBalanceRateBetScore;
	}

	public void setOverdueBalanceRateBetScore(String overdueBalanceRateBetScore) {
		this.overdueBalanceRateBetScore = overdueBalanceRateBetScore;
	}

	public String getDefectiveLoansRatePgbz() {
		return defectiveLoansRatePgbz;
	}

	public void setDefectiveLoansRatePgbz(String defectiveLoansRatePgbz) {
		this.defectiveLoansRatePgbz = defectiveLoansRatePgbz;
	}

	public String getDefectiveLoansRateBetScore() {
		return defectiveLoansRateBetScore;
	}

	public void setDefectiveLoansRateBetScore(String defectiveLoansRateBetScore) {
		this.defectiveLoansRateBetScore = defectiveLoansRateBetScore;
	}

	public String getAssessorName() {
		return assessorName;
	}

	public void setAssessorName(String assessorName) {
		this.assessorName = assessorName;
	}

	public Integer getDataYear() {
		return dataYear;
	}

	public void setDataYear(Integer dataYear) {
		this.dataYear = dataYear;
	}

	public Integer getDataMonth() {
		return dataMonth;
	}

	public void setDataMonth(Integer dataMonth) {
		this.dataMonth = dataMonth;
	}

	public String getCustomerManagerLevel() {
		return customerManagerLevel;
	}

	public void setCustomerManagerLevel(String customerManagerLevel) {
		this.customerManagerLevel = customerManagerLevel;
	}

}
