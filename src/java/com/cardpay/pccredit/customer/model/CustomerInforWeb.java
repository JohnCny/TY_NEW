package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BaseModel;

/**
 * 
 * @author 姚绍明
 *
 */
public class CustomerInforWeb extends BaseModel{
	private static final long serialVersionUID = 1L;
	private String id;
	private String chineseName;
	private String nationality;
	private String sex;
	private String pinyinenglishName;
	private String birthday;
	private String cardType;
	private String cardId;  
	private String residentialAddress;
	private String zipCode;
	private String homePhone;
	private String telephone;
	private String mail;
	private String residentialPropertie;
	private String maritalStatus;
	private String degreeEducation;
	private String householdAddress;
	private String zipCodeAddress;
	private String applyCredit;
	private String finalApproval; 
	private String actualAmount; 
	private String temporaryQuota; 
	private String cashAdvanceRatio;
	private String cardStatus;
	private String userName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPinyinenglishName() {
		return pinyinenglishName;
	}
	public void setPinyinenglishName(String pinyinenglishName) {
		this.pinyinenglishName = pinyinenglishName;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getResidentialAddress() {
		return residentialAddress;
	}
	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getResidentialPropertie() {
		return residentialPropertie;
	}
	public void setResidentialPropertie(String residentialPropertie) {
		this.residentialPropertie = residentialPropertie;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getDegreeEducation() {
		return degreeEducation;
	}
	public void setDegreeEducation(String degreeEducation) {
		this.degreeEducation = degreeEducation;
	}
	public String getHouseholdAddress() {
		return householdAddress;
	}
	public void setHouseholdAddress(String householdAddress) {
		this.householdAddress = householdAddress;
	}
	public String getZipCodeAddress() {
		return zipCodeAddress;
	}
	public void setZipCodeAddress(String zipCodeAddress) {
		this.zipCodeAddress = zipCodeAddress;
	}
	public String getApplyCredit() {
		return applyCredit;
	}
	public void setApplyCredit(String applyCredit) {
		this.applyCredit = applyCredit;
	}
	public String getFinalApproval() {
		return finalApproval;
	}
	public void setFinalApproval(String finalApproval) {
		this.finalApproval = finalApproval;
	}
	public String getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getTemporaryQuota() {
		return temporaryQuota;
	}
	public void setTemporaryQuota(String temporaryQuota) {
		this.temporaryQuota = temporaryQuota;
	}
	public String getCashAdvanceRatio() {
		return cashAdvanceRatio;
	}
	public void setCashAdvanceRatio(String cashAdvanceRatio) {
		this.cashAdvanceRatio = cashAdvanceRatio;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
