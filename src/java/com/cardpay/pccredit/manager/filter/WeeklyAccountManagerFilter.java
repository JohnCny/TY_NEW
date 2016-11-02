package com.cardpay.pccredit.manager.filter;

import java.util.List;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class WeeklyAccountManagerFilter extends BaseQueryFilter {

	private String id;
	private String coustomerManagerId;
	private String loginId;
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
	private String improvementSuggestionsMeasure;
	private String displayName;
	//add field
	private List<String> subManagerIds;
	
	
	public List<String> getSubManagerIds() {
		return subManagerIds;
	}

	public void setSubManagerIds(List<String> subManagerIds) {
		this.subManagerIds = subManagerIds;
	}

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

	public String getImprovementSuggestionsMeasure() {
		return improvementSuggestionsMeasure;
	}

	public void setImprovementSuggestionsMeasure(String improvementSuggestionsMeasure) {
		this.improvementSuggestionsMeasure = improvementSuggestionsMeasure;
	}

}
