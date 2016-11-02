package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "weekly_account_manager", generator = IDType.uuid32)
public class WeeklyAccountManager extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String id;
	private String title;
	private String customerManagerId;
	private String managerWeeklySummary; // 微贷经理周总结
	private String leaderReview;// 组长点评
	private String teamLeaderReview;// 团队负责人点评
	private String actualCredit;// 本周实际授信额度
	private Integer actualNumber;// 本周实际进件数
	private Integer actualNumberVisit;// 实际拜访次数
	private Integer actualNumberCustomers;// 实际拜访客户数
	private String monthlyNumber;// 月度进件指标完成比例
	private String monthlyCredit;// 月度实际授信额度完成比例
	private Integer groupNumber;// 组进件数
	private String groupCredit;// 组授信额度
	private String resistanceDifficultCollect;// 阻力及困难汇总
	private String improvementSuggestions;// 改进建议及措施
	
	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getImprovementSuggestions() {
		return improvementSuggestions;
	}

	public void setImprovementSuggestions(String improvementSuggestions) {
		this.improvementSuggestions = improvementSuggestions;
	}

}
