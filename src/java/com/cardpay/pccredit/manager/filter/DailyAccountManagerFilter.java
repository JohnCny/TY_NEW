package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * DailyAccountManagerFilter类的描述
 * 
 * @author 王海东
 * @created on 2014-11-19
 * 
 * @version $Id:$
 */
public class DailyAccountManagerFilter extends BaseQueryFilter {

	private String id;
	private String weeklyId;
	private String loginId;
	private String morningPlan;
	private String morningActual;
	private String afternoonPlan;
	private String afternoonActual;
	private String daySummary;

    private String displayName;
    

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWeeklyId() {
		return weeklyId;
	}

	public void setWeeklyId(String weeklyId) {
		this.weeklyId = weeklyId;
	}



	public String getMorningPlan() {
		return morningPlan;
	}

	public void setMorningPlan(String morningPlan) {
		this.morningPlan = morningPlan;
	}

	public String getMorningActual() {
		return morningActual;
	}

	public void setMorningActual(String morningActual) {
		this.morningActual = morningActual;
	}

	public String getAfternoonPlan() {
		return afternoonPlan;
	}

	public void setAfternoonPlan(String afternoonPlan) {
		this.afternoonPlan = afternoonPlan;
	}

	public String getAfternoonActual() {
		return afternoonActual;
	}

	public void setAfternoonActual(String afternoonActual) {
		this.afternoonActual = afternoonActual;
	}

	public String getDaySummary() {
		return daySummary;
	}

	public void setDaySummary(String daySummary) {
		this.daySummary = daySummary;
	}

}
