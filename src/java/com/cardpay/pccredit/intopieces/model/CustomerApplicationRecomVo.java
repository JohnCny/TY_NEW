package com.cardpay.pccredit.intopieces.model;

public class CustomerApplicationRecomVo{
	
	private String recommendId;
	
	private String mainApplicationFormId;

    private String name;

    private String outlet;

    private String recommendedContactPhone;

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
		this.name = name;
	}

	public String getOutlet() {
		return outlet;
	}

	public void setOutlet(String outlet) {
		this.outlet = outlet;
	}

	public String getRecommendedContactPhone() {
		return recommendedContactPhone;
	}

	public void setRecommendedContactPhone(String recommendedContactPhone) {
		this.recommendedContactPhone = recommendedContactPhone;
	}

	public String getRecommendedIdentityCardNumb() {
		return recommendedIdentityCardNumb;
	}

	public void setRecommendedIdentityCardNumb(String recommendedIdentityCardNumb) {
		this.recommendedIdentityCardNumb = recommendedIdentityCardNumb;
	}

	public String getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	
}