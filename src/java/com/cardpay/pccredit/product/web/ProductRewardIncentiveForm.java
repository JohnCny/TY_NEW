package com.cardpay.pccredit.product.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 4.2.11 产品奖励激励参数表
 * 
 * @author 王海东
 * @created on 2014-11-10
 * 
 * @version $Id:$
 */

public class ProductRewardIncentiveForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String rewardSheet;
	private String rewardIncentivesProportion;
	private String nterestIncomeParameter;
	private String feeSparameter;
	private String parametersFine;
	private String parametersAnnualFee;
	private String parametersCost;
	private String parametersRiskcost;
	private String deductionsParameters;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

}
