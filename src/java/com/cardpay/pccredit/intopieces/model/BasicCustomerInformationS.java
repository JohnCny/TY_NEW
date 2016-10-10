package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * BasicCustomerInformationS类的描述
 * 
 * @author 王海东
 * @created on 2014-12-11
 * 
 * @version $Id:$
 */
@ModelParam(table = "basic_customer_information_s")
public class BasicCustomerInformationS extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String code;
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
	private String actualAmount;
	private String userId;
	private String divisionalStatus;
	private String customerBusinessType;
	private String customerBusinessAptitude;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDivisionalStatus() {
		return divisionalStatus;
	}

	public void setDivisionalStatus(String divisionalStatus) {
		this.divisionalStatus = divisionalStatus;
	}

	public String getCustomerBusinessType() {
		return customerBusinessType;
	}

	public void setCustomerBusinessType(String customerBusinessType) {
		this.customerBusinessType = customerBusinessType;
	}

	public String getCustomerBusinessAptitude() {
		return customerBusinessAptitude;
	}

	public void setCustomerBusinessAptitude(String customerBusinessAptitude) {
		this.customerBusinessAptitude = customerBusinessAptitude;
	}

}
