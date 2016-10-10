package com.cardpay.pccredit.manager.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

public class WeeklyAccountManagerForm  extends BaseForm{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String loginId;
	private String displayName;
	private String title;
	private String coustomerManagerId;
	private String managerWeeklySummary;
	private String leaderReview;
	private String teamLeaderReview;
	private String actualCredit;
	private Integer actualNumber;
	private Integer actualNumberVisit;
	private Integer actualNumberCustomers;
	private String monthlyNumber;
	private String monthlyCredit;
	private Integer groupNumber;
	private String groupCredit;
	private String resistanceDifficultCollect;
	private String improvementSuggestions;
	private Date createdTime;
	private Date modifiedTime;
	private String createdBy;
	private String modifiedBy;
	
	
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
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
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
	public String getCoustomerManagerId() {
		return coustomerManagerId;
	}
	public void setCoustomerManagerId(String coustomerManagerId) {
		this.coustomerManagerId = coustomerManagerId;
	}
	public String getManagerWeeklySummary() {
		return managerWeeklySummary;
	}
	public void setManagerWeeklySummary(String managerWeeklySummary) {
		this.managerWeeklySummary = managerWeeklySummary;
	}
	public String getLeaderReview() {
		return leaderReview;
	}
	public void setLeaderReview(String leaderReview) {
		this.leaderReview = leaderReview;
	}
	public String getTeamLeaderReview() {
		return teamLeaderReview;
	}
	public void setTeamLeaderReview(String teamLeaderReview) {
		this.teamLeaderReview = teamLeaderReview;
	}
	public String getActualCredit() {
		return actualCredit;
	}
	public void setActualCredit(String actualCredit) {
		this.actualCredit = actualCredit;
	}
	public Integer getActualNumber() {
		return actualNumber;
	}
	public void setActualNumber(Integer actualNumber) {
		this.actualNumber = actualNumber;
	}
	public Integer getActualNumberVisit() {
		return actualNumberVisit;
	}
	public void setActualNumberVisit(Integer actualNumberVisit) {
		this.actualNumberVisit = actualNumberVisit;
	}
	public Integer getActualNumberCustomers() {
		return actualNumberCustomers;
	}
	public void setActualNumberCustomers(Integer actualNumberCustomers) {
		this.actualNumberCustomers = actualNumberCustomers;
	}
	public String getMonthlyNumber() {
		return monthlyNumber;
	}
	public void setMonthlyNumber(String monthlyNumber) {
		this.monthlyNumber = monthlyNumber;
	}
	public String getMonthlyCredit() {
		return monthlyCredit;
	}
	public void setMonthlyCredit(String monthlyCredit) {
		this.monthlyCredit = monthlyCredit;
	}
	public Integer getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(Integer groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getGroupCredit() {
		return groupCredit;
	}
	public void setGroupCredit(String groupCredit) {
		this.groupCredit = groupCredit;
	}
	public String getResistanceDifficultCollect() {
		return resistanceDifficultCollect;
	}
	public void setResistanceDifficultCollect(String resistanceDifficultCollect) {
		this.resistanceDifficultCollect = resistanceDifficultCollect;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImprovementSuggestions() {
		return improvementSuggestions;
	}
	public void setImprovementSuggestions(String improvementSuggestions) {
		this.improvementSuggestions = improvementSuggestions;
	}
	

}
