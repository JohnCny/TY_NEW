package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 姚绍明
 *
 */
@ModelParam(table = "marketing_plans_action",generator=IDType.uuid32)
public class CustomerMarketingPlan extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String marketingPlanId;
	private String marketing;
	private String marketingResult;
	private Date marketingStartTime;
	private Date marketingEndTime;
	private String whetherImplement;
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
	public Date getMarketingStartTime() {
		return marketingStartTime;
	}
	public void setMarketingStartTime(Date marketingStartTime) {
		this.marketingStartTime = marketingStartTime;
	}
	public Date getMarketingEndTime() {
		return marketingEndTime;
	}
	public void setMarketingEndTime(Date marketingEndTime) {
		this.marketingEndTime = marketingEndTime;
	}
	public String getWhetherImplement() {
		return whetherImplement;
	}
	public void setWhetherImplement(String whetherImplement) {
		this.whetherImplement = whetherImplement;
	}
}
