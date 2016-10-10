package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customer_application_guarantor")
public class CustomerApplicationGuarantor extends BusinessModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8820423258178329720L;

    private String mainApplicationFormId;
    
    private String mainApplicationFormCode;

    private String guarantorMortgagorPledge;

    private String sex;

    private String relationshipWithApplicant;

    private String unitName;

    private String department;

    private String contactPhone;

    private String cellPhone;

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
        this.guarantorMortgagorPledge = guarantorMortgagorPledge == null ? null : guarantorMortgagorPledge.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getRelationshipWithApplicant() {
        return relationshipWithApplicant;
    }

    public void setRelationshipWithApplicant(String relationshipWithApplicant) {
        this.relationshipWithApplicant = relationshipWithApplicant == null ? null : relationshipWithApplicant.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone == null ? null : cellPhone.trim();
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber == null ? null : documentNumber.trim();
    }

	public String getMainApplicationFormCode() {
		return mainApplicationFormCode;
	}

	public void setMainApplicationFormCode(String mainApplicationFormCode) {
		this.mainApplicationFormCode = mainApplicationFormCode;
	}
    
}