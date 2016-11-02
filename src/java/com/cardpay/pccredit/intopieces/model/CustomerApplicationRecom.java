package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customer_application_recom")
public class CustomerApplicationRecom extends BusinessModel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6474208315016628063L;

	private String mainApplicationFormId;
	
	private String mainApplicationFormCode;

    private String name;

    private String outlet;

    private String contactPhone;

    private String recommendedIdentityCardNumb;

    public String getMainApplicationFormId() {
		return mainApplicationFormId;
	}

	public void setMainApplicationFormId(String mainApplicationFormId) {
		this.mainApplicationFormId = mainApplicationFormId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet == null ? null : outlet.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getRecommendedIdentityCardNumb() {
        return recommendedIdentityCardNumb;
    }

    public void setRecommendedIdentityCardNumb(String recommendedIdentityCardNumb) {
        this.recommendedIdentityCardNumb = recommendedIdentityCardNumb == null ? null : recommendedIdentityCardNumb.trim();
    }

	public String getMainApplicationFormCode() {
		return mainApplicationFormCode;
	}

	public void setMainApplicationFormCode(String mainApplicationFormCode) {
		this.mainApplicationFormCode = mainApplicationFormCode;
	}
    
}