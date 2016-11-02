package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customer_application_other")
public class CustomerApplicationOther extends BusinessModel{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7521620058907746065L;

	private String mainApplicationFormId;
	
	private String mainApplicationFormCode;

    private String billingMethod;

    private String paperBillingShippingAddress;

    private String collarCardMode;

    private String consumptionUsePassword;

    private String smsOpeningTrading;
    
    private String useSecondCard;

    public String getMainApplicationFormId() {
		return mainApplicationFormId;
	}

	public void setMainApplicationFormId(String mainApplicationFormId) {
		this.mainApplicationFormId = mainApplicationFormId;
	}

	public String getBillingMethod() {
        return billingMethod;
    }

    public void setBillingMethod(String billingMethod) {
        this.billingMethod = billingMethod == null ? null : billingMethod.trim();
    }

    public String getPaperBillingShippingAddress() {
        return paperBillingShippingAddress;
    }

    public void setPaperBillingShippingAddress(String paperBillingShippingAddress) {
        this.paperBillingShippingAddress = paperBillingShippingAddress == null ? null : paperBillingShippingAddress.trim();
    }

    public String getCollarCardMode() {
        return collarCardMode;
    }

    public void setCollarCardMode(String collarCardMode) {
        this.collarCardMode = collarCardMode == null ? null : collarCardMode.trim();
    }

    public String getConsumptionUsePassword() {
        return consumptionUsePassword;
    }

    public void setConsumptionUsePassword(String consumptionUsePassword) {
        this.consumptionUsePassword = consumptionUsePassword == null ? null : consumptionUsePassword.trim();
    }

    public String getSmsOpeningTrading() {
        return smsOpeningTrading;
    }

    public void setSmsOpeningTrading(String smsOpeningTrading) {
        this.smsOpeningTrading = smsOpeningTrading == null ? null : smsOpeningTrading.trim();
    }

	public String getMainApplicationFormCode() {
		return mainApplicationFormCode;
	}

	public void setMainApplicationFormCode(String mainApplicationFormCode) {
		this.mainApplicationFormCode = mainApplicationFormCode;
	}

	public String getUseSecondCard() {
		return useSecondCard;
	}

	public void setUseSecondCard(String useSecondCard) {
		this.useSecondCard = useSecondCard;
	}
    
}