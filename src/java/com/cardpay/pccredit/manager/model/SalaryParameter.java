package com.cardpay.pccredit.manager.model;

import org.apache.commons.lang.StringUtils;

import com.cardpay.pccredit.common.Arith;

/**
 * @author chenzhifang
 *
 * 2014-11-17下午3:42:30
 */
public class SalaryParameter {
	// Y：是叶子节点， N: 不是叶子节点
	private String isLeaf;
	// 客户经理Id
	private String managerId;
	// 奖励激励加权数
	private String weightedNumber;
	// 底薪
	private String basePay="0";
	// 责任工资
	private String dutySalary="0";
	// 津贴
	private String allowance="0";
	// 目标活跃数量
	private String targetActiveNumber="0";
	// 目标激活数量
	private String targetActivationNumber="0";
	// 新增客户奖金比例
	private String insertCustomerAwardRate="0";
	// 管户奖金比例
	private String customerNumberAwardRate="0";
	// 管理绩效比例
	private String teamAwardRate="0";
	// 风险准备金的权数
	//private String prepareRate="0";
	// 当月实际活跃数 
	private String actualActiveCardNumber="0";
	// 当月新增活跃数
	private String addActivationCardNumber="0";
	// 返还金额(风险保证金的返还方式：风险保证金从缴纳第三年的第一个月开始，逐月返还每期计提（第三年第一个月返还第一个月计提）)
	private String insertPrepareAmount="0";
	// 收息分润
	private String sxfr="0";
	// 上月的风险准备金余额
	private String riskReserveBalances="0";
	private String marginExtractInfo="0";
	
	/*
	 * 责任工资发放标准=责任工资×工资系数
	 * 工资系数=激活率完成比×40%+活跃率完成比×60%* (激活率完成比 = 激活数/目标数量)
	 * 注：工资系数低于60%当月责任工资发放为0。
	 * 计算责任工资
	 */
	public String calculateDutySalary(){
		String dutySalary = "";
		// 工资系数
		double coefficient = Arith.mul(Arith.divReturnStr(addActivationCardNumber, targetActivationNumber), "0.6")
				+ Arith.mul(Arith.divReturnStr(actualActiveCardNumber, targetActiveNumber),"0.4");
		if (coefficient < 0.6) {
			dutySalary = "0";
		} else {
			dutySalary = Arith.mulReturnStr(this.getDutySalary(), coefficient+"");
		}
		return dutySalary;
	}
	
	/*
	 * 绩效工资=(新增客户奖金+管户奖金+收息分润+管理绩效+其他)
	 * 1.	新增客户奖金=当月新增活跃客户数×A
	 * 2.	管户奖金=当月实际活跃客户数×B
	 * 3.	收息分润=当月客户“灵活金”业务实收利息收入×C
	 * 4.	管理绩效=管理层级员工所负责团队规模当月客户“灵活金”业务实收利息收入×D
	 * 
	 * 计算绩效工资
	 */
	public String calculatePerformanceSalary(String managePerformance){
		// 新增客户奖金
		String addCustomerAward = Arith.mulReturnStr(this.getInsertCustomerAwardRate(), this.getAddActivationCardNumber());
		// 管户奖金
		String manageAward = Arith.mulReturnStr(this.getCustomerNumberAwardRate(), this.getActualActiveCardNumber());
		
		String total = Arith.addReturnStr(addCustomerAward, manageAward);
		
		total = Arith.addReturnStr(total, sxfr);
		
		if(StringUtils.isNotEmpty(managePerformance)){
			// 如果管理绩效不为空
			total = Arith.addReturnStr(total, Arith.mulReturnStr(managePerformance, teamAwardRate));
		}
		return total;
	}
	
	public String getManagerId() {
		return managerId;
	}
	
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
	public String getBasePay() {
		return basePay;
	}
	
	public void setBasePay(String basePay) {
		this.basePay = basePay;
	}
	
	public String getDutySalary() {
		return dutySalary;
	}
	
	public void setDutySalary(String dutySalary) {
		this.dutySalary = dutySalary;
	}
	
	public String getAllowance() {
		return allowance;
	}
	
	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

	public String getInsertCustomerAwardRate() {
		return insertCustomerAwardRate;
	}

	public void setInsertCustomerAwardRate(String insertCustomerAwardRate) {
		this.insertCustomerAwardRate = insertCustomerAwardRate;
	}

	public String getCustomerNumberAwardRate() {
		return customerNumberAwardRate;
	}

	public void setCustomerNumberAwardRate(String customerNumberAwardRate) {
		this.customerNumberAwardRate = customerNumberAwardRate;
	}

	public String getTeamAwardRate() {
		return teamAwardRate;
	}

	public void setTeamAwardRates(String teamAwardRate) {
		this.teamAwardRate = teamAwardRate;
	}

	public String getActualActiveCardNumber() {
		return actualActiveCardNumber;
	}
	
	public void setActualActiveCardNumber(String actualActiveCardNumber) {
		this.actualActiveCardNumber = actualActiveCardNumber;
	}
	
	public String getAddActivationCardNumber() {
		return addActivationCardNumber;
	}
	
	public void setAddActivationCardNumber(String addActivationCardNumber) {
		this.addActivationCardNumber = addActivationCardNumber;
	}
	
	public String getIsLeaf() {
		return isLeaf;
	}
	
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	public String getInsertPrepareAmount() {
		return insertPrepareAmount;
	}
	
	public void setInsertPrepareAmount(String insertPrepareAmount) {
		this.insertPrepareAmount = insertPrepareAmount;
	}
	
	/*
	 * 是否为叶子节点
	 */
	public boolean isLeaf(){
		return "Y".equals(this.isLeaf);
	}


	public String getTargetActiveNumber() {
		return targetActiveNumber;
	}

	public void setTargetActiveNumber(String targetActiveNumber) {
		this.targetActiveNumber = targetActiveNumber;
	}

	public String getTargetActivationNumber() {
		return targetActivationNumber;
	}

	public void setTargetActivationNumber(String targetActivationNumber) {
		this.targetActivationNumber = targetActivationNumber;
	}

	public String getSxfr() {
		return sxfr;
	}

	public void setSxfr(String sxfr) {
		this.sxfr = sxfr;
	}

	public void setTeamAwardRate(String teamAwardRate) {
		this.teamAwardRate = teamAwardRate;
	}

	public String getRiskReserveBalances() {
		return riskReserveBalances;
	}

	public void setRiskReserveBalances(String riskReserveBalances) {
		this.riskReserveBalances = riskReserveBalances;
	}

	public String getWeightedNumber() {
		return weightedNumber;
	}

	public void setWeightedNumber(String weightedNumber) {
		this.weightedNumber = weightedNumber;
	}

	public String getMarginExtractInfo() {
		return marginExtractInfo;
	}

	public void setMarginExtractInfo(String marginExtractInfo) {
		this.marginExtractInfo = marginExtractInfo;
	}
}
