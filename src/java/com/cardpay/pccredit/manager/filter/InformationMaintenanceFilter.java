/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import java.util.Date;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author shaoming
 *
 * 2014年11月24日   下午3:20:18
 */
public class InformationMaintenanceFilter extends BaseQueryFilter{
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
