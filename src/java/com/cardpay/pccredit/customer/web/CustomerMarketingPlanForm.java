package com.cardpay.pccredit.customer.web;

import com.wicresoft.jrad.base.web.form.BaseForm;
/**
 * 
 * @author 姚绍明
 *
 */
public class CustomerMarketingPlanForm extends BaseForm{
	private static final long serialVersionUID = 1L;
	private String id;
	private String marketingPlanId;
	private String marketing;
	private String marketingResult;
	private String marketingStartTime;
	private String marketingEndTime;
	private String whetherImplement;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMarketingPlanId() {
		return marketingPlanId;
	}
	public void setMarketingPlanId(String marketingPlanId) {
		this.marketingPlanId = marketingPlanId;
	}
	public String getMarketing() {
		return marketing;
	}
	public void setMarketing(String marketing) {
		this.marketing = marketing;
	}
	public String getMarketingResult() {
		return marketingResult;
	}
	public void setMarketingResult(String marketingResult) {
		this.marketingResult = marketingResult;
	}
	public String getMarketingStartTime() {
		return marketingStartTime;
	}
	public void setMarketingStartTime(String marketingStartTime) {
		this.marketingStartTime = marketingStartTime;
	}
	public String getMarketingEndTime() {
		return marketingEndTime;
	}
	public void setMarketingEndTime(String marketingEndTime) {
		this.marketingEndTime = marketingEndTime;
	}
	public String getWhetherImplement() {
		return whetherImplement;
	}
	public void setWhetherImplement(String whetherImplement) {
		this.whetherImplement = whetherImplement;
	}
	
}
