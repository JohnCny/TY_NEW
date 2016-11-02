package com.cardpay.pccredit.intopieces.model;

public class CustomerApplicationContactVo {
	
	private String contactId;

    private String mainApplicationFormId;

    private String contactName;

    private String contactSex;

    private String contactRelationshipWithApplicant;

    private String contactUnitName;

    private String contactDepartment;

    private String contactPhone;

    private String cellPhone;

	public String getMainApplicationFormId() {
		return mainApplicationFormId;
	}

	public void setMainApplicationFormId(String mainApplicationFormId) {
		this.mainApplicationFormId = mainApplicationFormId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactSex() {
		return contactSex;
	}

	public void setContactSex(String contactSex) {
		this.contactSex = contactSex;
	}

	public String getContactRelationshipWithApplicant() {
		return contactRelationshipWithApplicant;
	}

	public void setContactRelationshipWithApplicant(
			String contactRelationshipWithApplicant) {
		this.contactRelationshipWithApplicant = contactRelationshipWithApplicant;
	}

	public String getContactUnitName() {
		return contactUnitName;
	}

	public void setContactUnitName(String contactUnitName) {
		this.contactUnitName = contactUnitName;
	}

	public String getContactDepartment() {
		return contactDepartment;
	}

	public void setContactDepartment(String contactDepartment) {
		this.contactDepartment = contactDepartment;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
    
}