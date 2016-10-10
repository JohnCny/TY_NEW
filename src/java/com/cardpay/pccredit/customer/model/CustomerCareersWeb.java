package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

/**
 * 
 * @author shaoming
 *
 * 2014年10月30日   下午4:56:02
 */
public class CustomerCareersWeb extends BusinessModel{
	
	private static final long serialVersionUID = 1L;
	private String customerName;
    private String nameUnit;
    private String departmentName;
    private String unitAddress;
    private String zipCode;
    private String unitPhone;
    private String unitNature;
    private String industryType;
    private String positio;
    private String title;
    private String annualIncome;
    private String yearWorkUnit;
    private String beforeNameUnit;
    private String beforeYearWorkUnit;
    private String workTime;
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getNameUnit() {
		return nameUnit;
	}

	public void setNameUnit(String nameUnit) {
		this.nameUnit = nameUnit;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getUnitPhone() {
		return unitPhone;
	}

	public void setUnitPhone(String unitPhone) {
		this.unitPhone = unitPhone;
	}

	public String getUnitNature() {
		return unitNature;
	}

	public void setUnitNature(String unitNature) {
		this.unitNature = unitNature;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getPositio() {
		return positio;
	}

	public void setPositio(String positio) {
		this.positio = positio;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getYearWorkUnit() {
		return yearWorkUnit;
	}

	public void setYearWorkUnit(String yearWorkUnit) {
		this.yearWorkUnit = yearWorkUnit;
	}

	public String getBeforeNameUnit() {
		return beforeNameUnit;
	}

	public void setBeforeNameUnit(String beforeNameUnit) {
		this.beforeNameUnit = beforeNameUnit;
	}

	public String getBeforeYearWorkUnit() {
		return beforeYearWorkUnit;
	}

	public void setBeforeYearWorkUnit(String beforeYearWorkUnit) {
		this.beforeYearWorkUnit = beforeYearWorkUnit;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

}
