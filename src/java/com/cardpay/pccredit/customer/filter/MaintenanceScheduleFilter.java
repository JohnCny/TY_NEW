package com.cardpay.pccredit.customer.filter;

import java.util.Date;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-11-11上午9:42:14
 */
public class MaintenanceScheduleFilter extends BaseQueryFilter {
	// 客户ID
	private String customerId;
	// 客户经理ID
	private String customerManagerId;
	// 维护目标
	private String maintenanceGoal;
	// 计划开始时间
	private Date plannedStartTime;
	// 计划结束时间
	private Date plannedEndTime;
	//系统、主管、客户经理（创建方式）
	private String createWay;
	// 备注创建原因(存量风险维护。。。)
	private String remarksCreateReason;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getMaintenanceGoal() {
		return maintenanceGoal;
	}
	public void setMaintenanceGoal(String maintenanceGoal) {
		this.maintenanceGoal = maintenanceGoal;
	}
	public Date getPlannedStartTime() {
		return plannedStartTime;
	}
	public void setPlannedStartTime(Date plannedStartTime) {
		this.plannedStartTime = plannedStartTime;
	}
	public Date getPlannedEndTime() {
		return plannedEndTime;
	}
	public void setPlannedEndTime(Date plannedEndTime) {
		this.plannedEndTime = plannedEndTime;
	}
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	public String getRemarksCreateReason() {
		return remarksCreateReason;
	}
	public void setRemarksCreateReason(String remarksCreateReason) {
		this.remarksCreateReason = remarksCreateReason;
	}
}
