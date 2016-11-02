package com.cardpay.pccredit.report.model;

/**
 * 评审岗位人员风控状况统计表
 * @author chenzhifang
 *
 * 2014-12-22下午5:40:11
 */
public class ReviewRiskControl {
	// 序号
	private String rowIndex;
	
	// 姓名
	private String name;
	
	private String id;
	
	// 上岗时间
	private String mountGuardTiem;
	
	// 通过数量
	private String passNumber;
	
	// 授信额度
	private String creditAmount;
	
	// 透支本金
	private String overdraftPrincipal;
	
	// 不良户数
	private String badnessNumber;
	
	// 不良透支本金
	private String badnessOverdraftPrincipal;
	
	// 不良率
	private String badnessRate;

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMountGuardTiem() {
		return mountGuardTiem;
	}

	public void setMountGuardTiem(String mountGuardTiem) {
		this.mountGuardTiem = mountGuardTiem;
	}

	public String getPassNumber() {
		return passNumber;
	}

	public void setPassNumber(String passNumber) {
		this.passNumber = passNumber;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public String getOverdraftPrincipal() {
		return overdraftPrincipal;
	}

	public void setOverdraftPrincipal(String overdraftPrincipal) {
		this.overdraftPrincipal = overdraftPrincipal;
	}

	public String getBadnessNumber() {
		return badnessNumber;
	}

	public void setBadnessNumber(String badnessNumber) {
		this.badnessNumber = badnessNumber;
	}

	public String getBadnessOverdraftPrincipal() {
		return badnessOverdraftPrincipal;
	}

	public void setBadnessOverdraftPrincipal(String badnessOverdraftPrincipal) {
		this.badnessOverdraftPrincipal = badnessOverdraftPrincipal;
	}

	public String getBadnessRate() {
		return badnessRate;
	}

	public void setBadnessRate(String badnessRate) {
		this.badnessRate = badnessRate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
