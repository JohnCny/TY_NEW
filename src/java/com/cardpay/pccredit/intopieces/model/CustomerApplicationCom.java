package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customer_application_com")
public class CustomerApplicationCom extends BusinessModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2536624686699302430L;

    private String mainApplicationFormId;

    private String promoteWay;

    private String numberPiecesSource;

    private String relationshipWithApplicant;

    private String otherNote;

    public String getMainApplicationFormId() {
		return mainApplicationFormId;
	}

	public void setMainApplicationFormId(String mainApplicationFormId) {
		this.mainApplicationFormId = mainApplicationFormId;
	}

	public String getPromoteWay() {
        return promoteWay;
    }

    public void setPromoteWay(String promoteWay) {
        this.promoteWay = promoteWay == null ? null : promoteWay.trim();
    }

    public String getNumberPiecesSource() {
        return numberPiecesSource;
    }

    public void setNumberPiecesSource(String numberPiecesSource) {
        this.numberPiecesSource = numberPiecesSource == null ? null : numberPiecesSource.trim();
    }

    public String getRelationshipWithApplicant() {
        return relationshipWithApplicant;
    }

    public void setRelationshipWithApplicant(String relationshipWithApplicant) {
        this.relationshipWithApplicant = relationshipWithApplicant == null ? null : relationshipWithApplicant.trim();
    }

    public String getOtherNote() {
        return otherNote;
    }

    public void setOtherNote(String otherNote) {
        this.otherNote = otherNote == null ? null : otherNote.trim();
    }
}