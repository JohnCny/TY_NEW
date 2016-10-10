package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "daily_account_manager",generator=IDType.uuid32)
public class DailyAccountManager extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String weeklyId;
	private Integer whatDay;
	private String morningPlan;
	private String morningActual;
	private String afternoonPlan;
	private String afternoonActual;
	private String daySummary;
	private String todayplan;
	private String tomorrowplan;
	
	public String getTodayplan() {
		return todayplan;
	}
	public void setTodayplan(String todayplan) {
		this.todayplan = todayplan;
	}
	public String getTomorrowplan() {
		return tomorrowplan;
	}
	public void setTomorrowplan(String tomorrowplan) {
		this.tomorrowplan = tomorrowplan;
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
	public Integer getWhatDay() {
		return whatDay;
	}
	public void setWhatDay(Integer whatDay) {
		this.whatDay = whatDay;
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
