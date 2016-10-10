package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author songchen
 *
 * 2016-09-14下午5:50:49
 */
@ModelParam(table = "manager_salary")
public class ManagerSalary extends BusinessModel {
	private static final long serialVersionUID = 1L;

	private String customerId;
	// 客户经理姓名
	private String managerName;
	// 绩效工资
	private String rewardIncentiveInformation;
	// 扣除金额
	private String deductAmount;
	// 底薪
	private String basePay;
	// 津贴
	private String allowance;
	// 责任工资
	private String dutySalary;
	// 返还风险保证金金额
	private String returnPrepareAmount;
	// 新增风险保证金
	private String insertPrepareAmount;
	// 风险准备金余额
	private String riskReserveBalances;
	//机构名称
	private String instCode;
	// 罚款
	private String fine;
	//年份
	private String year;
    //月份
	private String month;
	//备注
	private String describe;
	//基础任务量奖金
	private String  basicTaskBonus;
	//中间业务奖金
	private String middleAllowance;
	//缺勤扣
	private String attendDeduct;
	//业务量绩效
	private String volumePerformance;
	//利润提成
	private String profitDraw;
	//逾期扣款
	private String overdueDeduct;
	//岗位津贴
	private String subsidies;
	//审贷笔数
	private String auditNum;
	
	// 个人完成度
	private String competerPet;
	//主管绩效
	private String groupSalary;
	//主调绩效
	private String zdPerform;
	//辅调绩效
	private String fdPerform;
	//管户绩效
	private String ghPerform;
	//审批绩效
	private String spPerform;
	//岗位绩效
	private String gwPerform;
	//完成绩效
	private String competerPerform;
	//团队名
	private String shortName;
	
	
	public String getAuditNum() {
		return auditNum;
	}
	public void setAuditNum(String auditNum) {
		this.auditNum = auditNum;
	}
	public String getSubsidies() {
		return subsidies;
	}
	public void setSubsidies(String subsidies) {
		this.subsidies = subsidies;
	}
	public String getVolumePerformance() {
		return volumePerformance;
	}
	public void setVolumePerformance(String volumePerformance) {
		this.volumePerformance = volumePerformance;
	}
	public String getProfitDraw() {
		return profitDraw;
	}
	public void setProfitDraw(String profitDraw) {
		this.profitDraw = profitDraw;
	}
	public String getOverdueDeduct() {
		return overdueDeduct;
	}
	public void setOverdueDeduct(String overdueDeduct) {
		this.overdueDeduct = overdueDeduct;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getRewardIncentiveInformation() {
		return rewardIncentiveInformation;
	}
	public void setRewardIncentiveInformation(String rewardIncentiveInformation) {
		this.rewardIncentiveInformation = rewardIncentiveInformation;
	}
	public String getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}
	public String getBasePay() {
		return basePay;
	}
	public void setBasePay(String basePay) {
		this.basePay = basePay;
	}
	public String getAllowance() {
		return allowance;
	}
	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}
	public String getDutySalary() {
		return dutySalary;
	}
	public void setDutySalary(String dutySalary) {
		this.dutySalary = dutySalary;
	}
	public String getReturnPrepareAmount() {
		return returnPrepareAmount;
	}
	public void setReturnPrepareAmount(String returnPrepareAmount) {
		this.returnPrepareAmount = returnPrepareAmount;
	}
	public String getInsertPrepareAmount() {
		return insertPrepareAmount;
	}
	public void setInsertPrepareAmount(String insertPrepareAmount) {
		this.insertPrepareAmount = insertPrepareAmount;
	}
	public String getRiskReserveBalances() {
		return riskReserveBalances;
	}
	public void setRiskReserveBalances(String riskReserveBalances) {
		this.riskReserveBalances = riskReserveBalances;
	}
	public String getInstCode() {
		return instCode;
	}
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	public String getFine() {
		return fine;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getBasicTaskBonus() {
		return basicTaskBonus;
	}
	public void setBasicTaskBonus(String basicTaskBonus) {
		this.basicTaskBonus = basicTaskBonus;
	}
	public String getMiddleAllowance() {
		return middleAllowance;
	}
	public void setMiddleAllowance(String middleAllowance) {
		this.middleAllowance = middleAllowance;
	}
	public String getAttendDeduct() {
		return attendDeduct;
	}
	public void setAttendDeduct(String attendDeduct) {
		this.attendDeduct = attendDeduct;
	}
	public String getCompeterPet() {
		return competerPet;
	}
	public void setCompeterPet(String competerPet) {
		this.competerPet = competerPet;
	}
	public String getGroupSalary() {
		return groupSalary;
	}
	public void setGroupSalary(String groupSalary) {
		this.groupSalary = groupSalary;
	}
	public String getZdPerform() {
		return zdPerform;
	}
	public void setZdPerform(String zdPerform) {
		this.zdPerform = zdPerform;
	}
	public String getFdPerform() {
		return fdPerform;
	}
	public void setFdPerform(String fdPerform) {
		this.fdPerform = fdPerform;
	}
	public String getGhPerform() {
		return ghPerform;
	}
	public void setGhPerform(String ghPerform) {
		this.ghPerform = ghPerform;
	}
	public String getSpPerform() {
		return spPerform;
	}
	public void setSpPerform(String spPerform) {
		this.spPerform = spPerform;
	}
	public String getGwPerform() {
		return gwPerform;
	}
	public void setGwPerform(String gwPerform) {
		this.gwPerform = gwPerform;
	}
	public String getCompeterPerform() {
		return competerPerform;
	}
	public void setCompeterPerform(String competerPerform) {
		this.competerPerform = competerPerform;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	
	
	
}
