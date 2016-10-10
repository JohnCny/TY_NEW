package com.cardpay.pccredit.dimensional.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 *  四维评估模型实体
 * 
 * @author 王海东
 * @created on 2014-10-20
 * 
 * @version $Id:$
 */
@ModelParam(table = "dimensional_model_credit")
public class Dimensional extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String customerId;
	private String customerName;
	private String gender;
	private String idCard;
	private String annualNetIncome;
	private String accountManagerSeries;
	private String capacityT;
	private String capacityFinal;
	private String registerValue;
	private String registerWeight;
	private String houseValue;
	private String houseWeight;
	private String marriageValue;
	private String marriageWeight;
	private String titleValue;
	private String titleWeight;
	private String livingCombinedValue;
	private String livingT;
	private String livingFinal;
	private String industryRiskFactor;
	private String industryT;
	private String industryFinalValue;
	private String sixMonthsOverdueValue;
	private String sixMonthsOverdueWeight;
	private String successiveLateValue;
	private String successiveLateWeight;
	private String singleLateValue;
	private String singleLateWeight;
	private String allLateValue;
	private String allLateWeight;
	private String responsibleValue;
	private String responsibleWeight;
	private String homeJobChangeValue;
	private String homeJobChangeWeight;
	private String sixMonthsSucceValue;
	private String sixMonthsSucceWeight;
	private String charaterT;
	private String charaterAllValue;
	private String charaterFinalValue;
	private String denialValue;
	private String finalValue;
	

	public String getLivingCombinedValue() {
		return livingCombinedValue;
	}

	public void setLivingCombinedValue(String livingCombinedValue) {
		this.livingCombinedValue = livingCombinedValue;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAnnualNetIncome() {
		return annualNetIncome;
	}

	public void setAnnualNetIncome(String annualNetIncome) {
		this.annualNetIncome = annualNetIncome;
	}

	public String getAccountManagerSeries() {
		return accountManagerSeries;
	}

	public void setAccountManagerSeries(String accountManagerSeries) {
		this.accountManagerSeries = accountManagerSeries;
	}

	public String getCapacityT() {
		return capacityT;
	}

	public void setCapacityT(String capacityT) {
		this.capacityT = capacityT;
	}

	public String getCapacityFinal() {
		return capacityFinal;
	}

	public void setCapacityFinal(String capacityFinal) {
		this.capacityFinal = capacityFinal;
	}

	public String getRegisterValue() {
		return registerValue;
	}

	public void setRegisterValue(String registerValue) {
		this.registerValue = registerValue;
	}

	public String getRegisterWeight() {
		return registerWeight;
	}

	public void setRegisterWeight(String registerWeight) {
		this.registerWeight = registerWeight;
	}

	public String getHouseValue() {
		return houseValue;
	}

	public void setHouseValue(String houseValue) {
		this.houseValue = houseValue;
	}

	public String getHouseWeight() {
		return houseWeight;
	}

	public void setHouseWeight(String houseWeight) {
		this.houseWeight = houseWeight;
	}

	public String getMarriageValue() {
		return marriageValue;
	}

	public void setMarriageValue(String marriageValue) {
		this.marriageValue = marriageValue;
	}

	public String getMarriageWeight() {
		return marriageWeight;
	}

	public void setMarriageWeight(String marriageWeight) {
		this.marriageWeight = marriageWeight;
	}

	public String getTitleValue() {
		return titleValue;
	}

	public void setTitleValue(String titleValue) {
		this.titleValue = titleValue;
	}

	public String getTitleWeight() {
		return titleWeight;
	}

	public void setTitleWeight(String titleWeight) {
		this.titleWeight = titleWeight;
	}

	public String getLivingT() {
		return livingT;
	}

	public void setLivingT(String livingT) {
		this.livingT = livingT;
	}

	public String getLivingFinal() {
		return livingFinal;
	}

	public void setLivingFinal(String livingFinal) {
		this.livingFinal = livingFinal;
	}

	public String getIndustryRiskFactor() {
		return industryRiskFactor;
	}

	public void setIndustryRiskFactor(String industryRiskFactor) {
		this.industryRiskFactor = industryRiskFactor;
	}

	public String getIndustryT() {
		return industryT;
	}

	public void setIndustryT(String industryT) {
		this.industryT = industryT;
	}

	public String getIndustryFinalValue() {
		return industryFinalValue;
	}

	public void setIndustryFinalValue(String industryFinalValue) {
		this.industryFinalValue = industryFinalValue;
	}

	public String getSixMonthsOverdueValue() {
		return sixMonthsOverdueValue;
	}

	public void setSixMonthsOverdueValue(String sixMonthsOverdueValue) {
		this.sixMonthsOverdueValue = sixMonthsOverdueValue;
	}

	public String getSixMonthsOverdueWeight() {
		return sixMonthsOverdueWeight;
	}

	public void setSixMonthsOverdueWeight(String sixMonthsOverdueWeight) {
		this.sixMonthsOverdueWeight = sixMonthsOverdueWeight;
	}

	public String getSuccessiveLateValue() {
		return successiveLateValue;
	}

	public void setSuccessiveLateValue(String successiveLateValue) {
		this.successiveLateValue = successiveLateValue;
	}

	public String getSuccessiveLateWeight() {
		return successiveLateWeight;
	}

	public void setSuccessiveLateWeight(String successiveLateWeight) {
		this.successiveLateWeight = successiveLateWeight;
	}

	public String getSingleLateValue() {
		return singleLateValue;
	}

	public void setSingleLateValue(String singleLateValue) {
		this.singleLateValue = singleLateValue;
	}

	public String getSingleLateWeight() {
		return singleLateWeight;
	}

	public void setSingleLateWeight(String singleLateWeight) {
		this.singleLateWeight = singleLateWeight;
	}

	public String getAllLateValue() {
		return allLateValue;
	}

	public void setAllLateValue(String allLateValue) {
		this.allLateValue = allLateValue;
	}

	public String getAllLateWeight() {
		return allLateWeight;
	}

	public void setAllLateWeight(String allLateWeight) {
		this.allLateWeight = allLateWeight;
	}

	public String getResponsibleValue() {
		return responsibleValue;
	}

	public void setResponsibleValue(String responsibleValue) {
		this.responsibleValue = responsibleValue;
	}

	public String getResponsibleWeight() {
		return responsibleWeight;
	}

	public void setResponsibleWeight(String responsibleWeight) {
		this.responsibleWeight = responsibleWeight;
	}

	public String getHomeJobChangeValue() {
		return homeJobChangeValue;
	}

	public void setHomeJobChangeValue(String homeJobChangeValue) {
		this.homeJobChangeValue = homeJobChangeValue;
	}

	public String getHomeJobChangeWeight() {
		return homeJobChangeWeight;
	}

	public void setHomeJobChangeWeight(String homeJobChangeWeight) {
		this.homeJobChangeWeight = homeJobChangeWeight;
	}

	public String getSixMonthsSucceValue() {
		return sixMonthsSucceValue;
	}

	public void setSixMonthsSucceValue(String sixMonthsSucceValue) {
		this.sixMonthsSucceValue = sixMonthsSucceValue;
	}

	public String getSixMonthsSucceWeight() {
		return sixMonthsSucceWeight;
	}

	public void setSixMonthsSucceWeight(String sixMonthsSucceWeight) {
		this.sixMonthsSucceWeight = sixMonthsSucceWeight;
	}

	public String getCharaterT() {
		return charaterT;
	}

	public void setCharaterT(String charaterT) {
		this.charaterT = charaterT;
	}

	public String getCharaterAllValue() {
		return charaterAllValue;
	}

	public void setCharaterAllValue(String charaterAllValue) {
		this.charaterAllValue = charaterAllValue;
	}

	public String getCharaterFinalValue() {
		return charaterFinalValue;
	}

	public void setCharaterFinalValue(String charaterFinalValue) {
		this.charaterFinalValue = charaterFinalValue;
	}

	public String getDenialValue() {
		return denialValue;
	}

	public void setDenialValue(String denialValue) {
		this.denialValue = denialValue;
	}

	public String getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(String finalValue) {
		this.finalValue = finalValue;
	}

}
