/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author shaoming
 *
 * 2014年11月24日   下午3:12:53
 */
@ModelParam(table = "information_maintenance_plans",generator=IDType.uuid32)
public class InformationMaintenance extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accessInfoId;
	private String maintenanceMatter;
	private Date maintenanceTime;
	private String customerManagerId;
	public String getAccessInfoId() {
		return accessInfoId;
	}
	public void setAccessInfoId(String accessInfoId) {
		this.accessInfoId = accessInfoId;
	}
	public String getMaintenanceMatter() {
		return maintenanceMatter;
	}
	public void setMaintenanceMatter(String maintenanceMatter) {
		this.maintenanceMatter = maintenanceMatter;
	}
	public Date getMaintenanceTime() {
		return maintenanceTime;
	}
	public void setMaintenanceTime(Date maintenanceTime) {
		this.maintenanceTime = maintenanceTime;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	
}
