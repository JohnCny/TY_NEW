package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * CustomerCreidtEvaluation类的描述 授信评估
 * 
 * @author 王海东
 * @created on 2014-12-23
 * 
 * @version $Id:$
 */
@ModelParam(table = "customer_credit_evaluation")
public class CustomerCreditEvaluation extends BusinessModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String customerId;
	private String customerName;
	private String gender;
	private String idCard;

	private String modelType; //模型类型

	private String houseValue; // 房产价值

	private String carValue; // 车产价值

	private String otherValue; // 其它资产
	
	private String wetValue; // 净资产

	private String debitRemainingValue; // 征信贷款剩余金额

	private String totalUsedAmountMax; // 贷记卡授信总额/已用额度/最近6月平均使用额度(三者取最大):

	private String totalCreditAmount; // 贷记卡总授信额度

	private String otherDebitValue; // 或有负债

	private String dailyFlowOver10000; // 日均流水大于1万

	private String age3550;// 年龄35-50岁

	private String inland;// 岛内（思明区/湖里区）

	private String married; // 已婚

	private String haveRecord; // 有征信记录

	private String creidtBelow70per; // 征信贷记卡使用情况（低于70%）

	private String sexFemale; // 性别（女）

	private String livelihood; // 民生行业

	private String ourBusiness; // 我行业务品种

	private String haveLoans; // 在我行有贷款余额的

	private String carValueOver5; // 车产价值大于5万

	private String selfBusinessLifeOver1; // 个体户经营年限大于1年

	private String creditLimit; // 额度上限

	private String averageDailyFlow;// 我行日均流水

	private String capitalValue; // 股金价值

	private String familyLineCredit; // 家庭在我行授信额度

	private String customerType; // 客户类型

	private String guaranteeType; // 担保人类型

	private String applicantAssets; // 申请人资产

	private String guarantorLiability; // 担保人负债

	private String applicantLiability; // 申请人负债

	private String guarantorPropertyValue; // 担保人房产价值

	private String guarantorCarValue; // 担保人车产价值

	private String guarantorOtherAssets; // 担保人其他资产

	private String guarantorCreditLoan; // 担保人征信贷款剩余金额

	private String guarantorCreditUsedMax; // 担保人征信贷款剩余金额+担保人贷记卡授信总额/贷记卡已用额度/最近六个月平均使用额度（此三项取最大项）

	private String guarantorCreditTotalLimit; // 担保人准贷记卡总授信额度

	private String guarantorExternalAmount; // 担保人对外担保金额

	private String applicantCreditLoanSum; // 申请人征信贷款剩余金额

	private String applicantCreditUsedMax; // 申请人贷记卡授信总额/贷记卡已用额度/最近六个月平均使用额度（此三项取最大项）

	private String applicantCreditTotalQuota; // 申请人准贷记卡总授信额度

	private String applicantExternalAmount; // 申请人对外担保金额

	private String guarantorAmount; // 担保人数量

	private String highQuailtyIndustry; // 优质行业

	private Date createdTime;

	private String createdBy;

	private Date modifiedTime;

	private String modifiedBy;

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getWetValue() {
		return wetValue;
	}

	public void setWetValue(String wetValue) {
		this.wetValue = wetValue;
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

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getHouseValue() {
		return houseValue;
	}

	public void setHouseValue(String houseValue) {
		this.houseValue = houseValue;
	}

	public String getCarValue() {
		return carValue;
	}

	public void setCarValue(String carValue) {
		this.carValue = carValue;
	}

	public String getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}

	public String getDebitRemainingValue() {
		return debitRemainingValue;
	}

	public void setDebitRemainingValue(String debitRemainingValue) {
		this.debitRemainingValue = debitRemainingValue;
	}

	public String getTotalUsedAmountMax() {
		return totalUsedAmountMax;
	}

	public void setTotalUsedAmountMax(String totalUsedAmountMax) {
		this.totalUsedAmountMax = totalUsedAmountMax;
	}

	public String getTotalCreditAmount() {
		return totalCreditAmount;
	}

	public void setTotalCreditAmount(String totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}

	public String getOtherDebitValue() {
		return otherDebitValue;
	}

	public void setOtherDebitValue(String otherDebitValue) {
		this.otherDebitValue = otherDebitValue;
	}

	public String getDailyFlowOver10000() {
		return dailyFlowOver10000;
	}

	public void setDailyFlowOver10000(String dailyFlowOver10000) {
		this.dailyFlowOver10000 = dailyFlowOver10000;
	}

	public String getAge3550() {
		return age3550;
	}

	public void setAge3550(String age3550) {
		this.age3550 = age3550;
	}

	public String getInland() {
		return inland;
	}

	public void setInland(String inland) {
		this.inland = inland;
	}

	public String getMarried() {
		return married;
	}

	public void setMarried(String married) {
		this.married = married;
	}

	public String getHaveRecord() {
		return haveRecord;
	}

	public void setHaveRecord(String haveRecord) {
		this.haveRecord = haveRecord;
	}

	public String getCreidtBelow70per() {
		return creidtBelow70per;
	}

	public void setCreidtBelow70per(String creidtBelow70per) {
		this.creidtBelow70per = creidtBelow70per;
	}

	public String getSexFemale() {
		return sexFemale;
	}

	public void setSexFemale(String sexFemale) {
		this.sexFemale = sexFemale;
	}

	public String getLivelihood() {
		return livelihood;
	}

	public void setLivelihood(String livelihood) {
		this.livelihood = livelihood;
	}

	public String getOurBusiness() {
		return ourBusiness;
	}

	public void setOurBusiness(String ourBusiness) {
		this.ourBusiness = ourBusiness;
	}

	public String getHaveLoans() {
		return haveLoans;
	}

	public void setHaveLoans(String haveLoans) {
		this.haveLoans = haveLoans;
	}

	public String getCarValueOver5() {
		return carValueOver5;
	}

	public void setCarValueOver5(String carValueOver5) {
		this.carValueOver5 = carValueOver5;
	}

	public String getSelfBusinessLifeOver1() {
		return selfBusinessLifeOver1;
	}

	public void setSelfBusinessLifeOver1(String selfBusinessLifeOver1) {
		this.selfBusinessLifeOver1 = selfBusinessLifeOver1;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getAverageDailyFlow() {
		return averageDailyFlow;
	}

	public void setAverageDailyFlow(String averageDailyFlow) {
		this.averageDailyFlow = averageDailyFlow;
	}

	public String getCapitalValue() {
		return capitalValue;
	}

	public void setCapitalValue(String capitalValue) {
		this.capitalValue = capitalValue;
	}

	public String getFamilyLineCredit() {
		return familyLineCredit;
	}

	public void setFamilyLineCredit(String familyLineCredit) {
		this.familyLineCredit = familyLineCredit;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public String getApplicantAssets() {
		return applicantAssets;
	}

	public void setApplicantAssets(String applicantAssets) {
		this.applicantAssets = applicantAssets;
	}

	public String getGuarantorLiability() {
		return guarantorLiability;
	}

	public void setGuarantorLiability(String guarantorLiability) {
		this.guarantorLiability = guarantorLiability;
	}

	public String getApplicantLiability() {
		return applicantLiability;
	}

	public void setApplicantLiability(String applicantLiability) {
		this.applicantLiability = applicantLiability;
	}

	public String getGuarantorPropertyValue() {
		return guarantorPropertyValue;
	}

	public void setGuarantorPropertyValue(String guarantorPropertyValue) {
		this.guarantorPropertyValue = guarantorPropertyValue;
	}

	public String getGuarantorCarValue() {
		return guarantorCarValue;
	}

	public void setGuarantorCarValue(String guarantorCarValue) {
		this.guarantorCarValue = guarantorCarValue;
	}

	public String getGuarantorOtherAssets() {
		return guarantorOtherAssets;
	}

	public void setGuarantorOtherAssets(String guarantorOtherAssets) {
		this.guarantorOtherAssets = guarantorOtherAssets;
	}

	public String getGuarantorCreditLoan() {
		return guarantorCreditLoan;
	}

	public void setGuarantorCreditLoan(String guarantorCreditLoan) {
		this.guarantorCreditLoan = guarantorCreditLoan;
	}

	public String getGuarantorCreditUsedMax() {
		return guarantorCreditUsedMax;
	}

	public void setGuarantorCreditUsedMax(String guarantorCreditUsedMax) {
		this.guarantorCreditUsedMax = guarantorCreditUsedMax;
	}

	public String getGuarantorCreditTotalLimit() {
		return guarantorCreditTotalLimit;
	}

	public void setGuarantorCreditTotalLimit(String guarantorCreditTotalLimit) {
		this.guarantorCreditTotalLimit = guarantorCreditTotalLimit;
	}

	public String getGuarantorExternalAmount() {
		return guarantorExternalAmount;
	}

	public void setGuarantorExternalAmount(String guarantorExternalAmount) {
		this.guarantorExternalAmount = guarantorExternalAmount;
	}

	public String getApplicantCreditLoanSum() {
		return applicantCreditLoanSum;
	}

	public void setApplicantCreditLoanSum(String applicantCreditLoanSum) {
		this.applicantCreditLoanSum = applicantCreditLoanSum;
	}

	public String getApplicantCreditUsedMax() {
		return applicantCreditUsedMax;
	}

	public void setApplicantCreditUsedMax(String applicantCreditUsedMax) {
		this.applicantCreditUsedMax = applicantCreditUsedMax;
	}

	public String getApplicantCreditTotalQuota() {
		return applicantCreditTotalQuota;
	}

	public void setApplicantCreditTotalQuota(String applicantCreditTotalQuota) {
		this.applicantCreditTotalQuota = applicantCreditTotalQuota;
	}

	public String getApplicantExternalAmount() {
		return applicantExternalAmount;
	}

	public void setApplicantExternalAmount(String applicantExternalAmount) {
		this.applicantExternalAmount = applicantExternalAmount;
	}

	public String getGuarantorAmount() {
		return guarantorAmount;
	}

	public void setGuarantorAmount(String guarantorAmount) {
		this.guarantorAmount = guarantorAmount;
	}

	public String getHighQuailtyIndustry() {
		return highQuailtyIndustry;
	}

	public void setHighQuailtyIndustry(String highQuailtyIndustry) {
		this.highQuailtyIndustry = highQuailtyIndustry;
	}

}
