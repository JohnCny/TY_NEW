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
 * 2014年11月11日   下午2:41:24
 */
@ModelParam(table = "MAINTENANCE_PLANS_ACTION",generator=IDType.uuid32)
public class MaintenanceAction extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String maintenancePlanId;
	private String maintenanceWay;
	private String maintenanceResult;
	private Date maintenanceStartTime;
	private Date maintenanceEndTime;
	public String getMaintenancePlanId() {
		return maintenancePlanId;
	}
	public void setMaintenancePlanId(String maintenancePlanId) {
		this.maintenancePlanId = maintenancePlanId;
	}
	public String getMaintenanceWay() {
		return maintenanceWay;
	}
	public void setMaintenanceWay(String maintenanceWay) {
		this.maintenanceWay = maintenanceWay;
	}
	public String getMaintenanceResult() {
		return maintenanceResult;
	}
	public void setMaintenanceResult(String maintenanceResult) {
		this.maintenanceResult = maintenanceResult;
	}
	public Date getMaintenanceStartTime() {
		return maintenanceStartTime;
	}
	public void setMaintenanceStartTime(Date maintenanceStartTime) {
		this.maintenanceStartTime = maintenanceStartTime;
	}
	public Date getMaintenanceEndTime() {
		return maintenanceEndTime;
	}
	public void setMaintenanceEndTime(Date maintenanceEndTime) {
		this.maintenanceEndTime = maintenanceEndTime;
	}
	
}
