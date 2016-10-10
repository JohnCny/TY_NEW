package com.cardpay.pccredit.intopieces.model;


public class CustomerApplicationGuarantorVo{
	
	private String guarantorId;

    private String mainApplicationFormId;

    private String guarantorMortgagorPledge;

    private String guarantorSex;

    private String guarantorRelationshipWithApplicant;

    private String unitName;

    private String department;

    private String guarantorContactPhone;

    private String guarantorCellPhone;

    private String documentNumber;

	public String getMainApplicationFormId() {
		return mainApplicationFormId;
	}

	public void setMainApplicationFormId(String mainApplicationFormId) {
		this.mainApplicationFormId = mainApplicationFormId;
	}

	public String getGuarantorMortgagorPledge() {
		return guarantorMortgagorPledge;
	}

	public void setGuarantorMortgagorPledge(String guarantorMortgagorPledge) {
		this.guarantorMortgagorPledge = guarantorMortgagorPledge;
	}

	public String getGuarantorSex() {
		return guarantorSex;
	}

	public void setGuarantorSex(String guarantorSex) {
		this.guarantorSex = guarantorSex;
	}

	public String getGuarantorRelationshipWithApplicant() {
		return guarantorRelationshipWithApplicant;
	}

	public void setGuarantorRelationshipWithApplicant(
			String guarantorRelationshipWithApplicant) {
		this.guarantorRelationshipWithApplicant = guarantorRelationshipWithApplicant;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGuarantorContactPhone() {
		return guarantorContactPhone;
	}

	public void setGuarantorContactPhone(String guarantorContactPhone) {
		this.guarantorContactPhone = guarantorContactPhone;
	}

	public String getGuarantorCellPhone() {
		return guarantorCellPhone;
	}

	public void setGuarantorCellPhone(String guarantorCellPhone) {
		this.guarantorCellPhone = guarantorCellPhone;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getGuarantorId() {
		return guarantorId;
	}

	public void setGuarantorId(String guarantorId) {
		this.guarantorId = guarantorId;
	}
    
}