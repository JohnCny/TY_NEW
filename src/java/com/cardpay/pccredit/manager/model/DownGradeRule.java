package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "downgrade_rule",generator=IDType.uuid32)
public class DownGradeRule extends BusinessModel{
	
	private static final long serialVersionUID = 1L;
	private String currentLevel;//			当前级别
	private String downgradeLevel;//		降低到级别
	private String endingPerformanceIndicator	;//		考核期末业绩指标
	private String evaluationScore;//		综合评估得分连续两期低于多少分
	private String indicatorCompletionRate;//		考核期末团队业绩指标完成率
	private String teamSize;//		考核期末团队规模
	private String quarterPerformanceIndicator	;//自然年内季度业绩指标
	private String yearEvaluationScore;//		自然年内综合评估得分
	
	public String getCurrentLevel() {
		return currentLevel;
	}
	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}
	public String getDowngradeLevel() {
		return downgradeLevel;
	}
	public void setDowngradeLevel(String downgradeLevel) {
		this.downgradeLevel = downgradeLevel;
	}
	public String getEndingPerformanceIndicator() {
		return endingPerformanceIndicator;
	}
	public void setEndingPerformanceIndicator(String endingPerformanceIndicator) {
		this.endingPerformanceIndicator = endingPerformanceIndicator;
	}
	public String getEvaluationScore() {
		return evaluationScore;
	}
	public void setEvaluationScore(String evaluationScore) {
		this.evaluationScore = evaluationScore;
	}
	public String getIndicatorCompletionRate() {
		return indicatorCompletionRate;
	}
	public void setIndicatorCompletionRate(String indicatorCompletionRate) {
		this.indicatorCompletionRate = indicatorCompletionRate;
	}
	public String getTeamSize() {
		return teamSize;
	}
	public void setTeamSize(String teamSize) {
		this.teamSize = teamSize;
	}
	public String getQuarterPerformanceIndicator() {
		return quarterPerformanceIndicator;
	}
	public void setQuarterPerformanceIndicator(String quarterPerformanceIndicator) {
		this.quarterPerformanceIndicator = quarterPerformanceIndicator;
	}
	public String getYearEvaluationScore() {
		return yearEvaluationScore;
	}
	public void setYearEvaluationScore(String yearEvaluationScore) {
		this.yearEvaluationScore = yearEvaluationScore;
	}


}
