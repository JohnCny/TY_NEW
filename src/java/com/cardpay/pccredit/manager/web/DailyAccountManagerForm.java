package com.cardpay.pccredit.manager.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

public class DailyAccountManagerForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String weeklyId;
	private String loginId;
	private String title;
	private String displayName;
	private Integer whatDay;
	private String morningPlan;
	private String morningActual;
	private String afternoonPlan;
	private String afternoonActual;
	private String daySummary;
	private Date createdTime;
	private Date modifiedTime;
	private String createdBy;
	private String modifiedBy;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getWhatDay() {
		return whatDay;
	}

	public void setWhatDay(Integer whatDay) {
		this.whatDay = whatDay;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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
