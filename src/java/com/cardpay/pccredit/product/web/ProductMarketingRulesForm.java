package com.cardpay.pccredit.product.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 产品营销规则 & 催收规则  &经理层级参数配置 &产品奖励激励参数 合集
 * 
 * @author 王海东
 * @created on 2014-10-17
 * 
 * @version $Id:$
 */

public class ProductMarketingRulesForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	//product_marketing_rules表
	private String productId;
	private String MarketingTime;
	private String MarketingMethod;
	//product_reward_incentive表
	private String customerManagerLevel;
	private String creditLine;
	private String marginExtractInfo;
	private String riskToleranceInformation;
	private String rewardSheet;
	private String rewardIncentivesProportion;
	private String nterestIncomeParameter;
	private String feeSparameter;
	private String parametersFine;
	private String parametersAnnualFee;
	private String parametersCost;
	private String parametersRiskcost;
	private String deductionsParameters;
	//ProductAccountability表
	private String aging;
	private String overdueDay;
	private String overdueAmount;
	private String overdueTotalTime;
	private String overdueTotalAmount;
	//ProductMaintain表
	private String day;
	private String maintenanceWay;

	
	
	public String getOverdueDay() {
		return overdueDay;
	}

	public void setOverdueDay(String overdueDay) {
		this.overdueDay = overdueDay;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getOverdueTotalTime() {
		return overdueTotalTime;
	}

	public void setOverdueTotalTime(String overdueTotalTime) {
		this.overdueTotalTime = overdueTotalTime;
	}

	public String getOverdueTotalAmount() {
		return overdueTotalAmount;
	}

	public void setOverdueTotalAmount(String overdueTotalAmount) {
		this.overdueTotalAmount = overdueTotalAmount;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMaintenanceWay() {
		return maintenanceWay;
	}

	public void setMaintenanceWay(String maintenanceWay) {
		this.maintenanceWay = maintenanceWay;
	}

	public String getCustomerManagerLevel() {
		return customerManagerLevel;
	}

	public void setCustomerManagerLevel(String customerManagerLevel) {
		this.customerManagerLevel = customerManagerLevel;
	}

	public String getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}

	public String getMarginExtractInfo() {
		return marginExtractInfo;
	}

	public void setMarginExtractInfo(String marginExtractInfo) {
		this.marginExtractInfo = marginExtractInfo;
	}

	public String getRiskToleranceInformation() {
		return riskToleranceInformation;
	}

	public void setRiskToleranceInformation(String riskToleranceInformation) {
		this.riskToleranceInformation = riskToleranceInformation;
	}

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}

	
	public String getRewardSheet() {
		return rewardSheet;
	}

	public void setRewardSheet(String rewardSheet) {
		this.rewardSheet = rewardSheet;
	}

	public String getRewardIncentivesProportion() {
		return rewardIncentivesProportion;
	}

	public void setRewardIncentivesProportion(String rewardIncentivesProportion) {
		this.rewardIncentivesProportion = rewardIncentivesProportion;
	}

	public String getNterestIncomeParameter() {
		return nterestIncomeParameter;
	}

	public void setNterestIncomeParameter(String nterestIncomeParameter) {
		this.nterestIncomeParameter = nterestIncomeParameter;
	}

	public String getFeeSparameter() {
		return feeSparameter;
	}

	public void setFeeSparameter(String feeSparameter) {
		this.feeSparameter = feeSparameter;
	}

	public String getParametersFine() {
		return parametersFine;
	}

	public void setParametersFine(String parametersFine) {
		this.parametersFine = parametersFine;
	}

	public String getParametersAnnualFee() {
		return parametersAnnualFee;
	}

	public void setParametersAnnualFee(String parametersAnnualFee) {
		this.parametersAnnualFee = parametersAnnualFee;
	}

	public String getParametersCost() {
		return parametersCost;
	}

	public void setParametersCost(String parametersCost) {
		this.parametersCost = parametersCost;
	}

	public String getParametersRiskcost() {
		return parametersRiskcost;
	}

	public void setParametersRiskcost(String parametersRiskcost) {
		this.parametersRiskcost = parametersRiskcost;
	}

	public String getDeductionsParameters() {
		return deductionsParameters;
	}

	public void setDeductionsParameters(String deductionsParameters) {
		this.deductionsParameters = deductionsParameters;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMarketingTime() {
		return MarketingTime;
	}

	public void setMarketingTime(String marketingTime) {
		MarketingTime = marketingTime;
	}

	public String getMarketingMethod() {
		return MarketingMethod;
	}

	public void setMarketingMethod(String marketingMethod) {
		MarketingMethod = marketingMethod;
	}

}
