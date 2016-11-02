/**
 * 
 */
package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午2:37:24
 */
@ModelParam(table = "MAINTENANCE_PLAN",generator=IDType.uuid32)
public class Maintenance extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String customerManagerId;
	private String maintenanceGoal;
	private String maintenanceWay;
	private String maintenanceDay;
	private String createWay;
	private String endResult;
	private String remarksCreateReason;
	private Date maintenanceEndtime;
	private String appId;
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
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
	public String getMaintenanceWay() {
		return maintenanceWay;
	}
	public void setMaintenanceWay(String maintenanceWay) {
		this.maintenanceWay = maintenanceWay;
	}
	public String getMaintenanceDay() {
		return maintenanceDay;
	}
	public void setMaintenanceDay(String maintenanceDay) {
		this.maintenanceDay = maintenanceDay;
	}
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public String getRemarksCreateReason() {
		return remarksCreateReason;
	}
	public void setRemarksCreateReason(String remarksCreateReason) {
		this.remarksCreateReason = remarksCreateReason;
	}
	public Date getMaintenanceEndtime() {
		return maintenanceEndtime;
	}
	public void setMaintenanceEndtime(Date maintenanceEndtime) {
		this.maintenanceEndtime = maintenanceEndtime;
	}
	

}
