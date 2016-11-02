/**
 * 
 */
package com.cardpay.pccredit.manager.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;
import com.wicresoft.util.date.DateHelper;

/**
 * @author shaoming
 *
 * 2014年11月24日   下午3:17:26
 */
public class InformationMaintenanceForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String accessInfoId;
	private String name;
	private String telephone;
	private String maintenanceMatter;
	private Date maintenanceTime;
	private String customerManagerId;
	private String informationPlanId;
	private String informationWay;
	private String informationResult;
	private Date informationStartTime = DateHelper.getDateFormat("1970-01-01 12:00:00","yyyy-MM-dd HH:mm:ss");
	private Date informationEndTime;
	private int countAction;
	public String getInformationPlanId() {
		return informationPlanId;
	}
	public void setInformationPlanId(String informationPlanId) {
		this.informationPlanId = informationPlanId;
	}
	public String getInformationWay() {
		return informationWay;
	}
	public void setInformationWay(String informationWay) {
		this.informationWay = informationWay;
	}
	public String getInformationResult() {
		return informationResult;
	}
	public void setInformationResult(String informationResult) {
		this.informationResult = informationResult;
	}
	public Date getInformationStartTime() {
		return informationStartTime;
	}
	public void setInformationStartTime(Date informationStartTime) {
		this.informationStartTime = informationStartTime;
	}
	public Date getInformationEndTime() {
		return informationEndTime;
	}
	public void setInformationEndTime(Date informationEndTime) {
		this.informationEndTime = informationEndTime;
	}
	public String getAccessInfoId() {
		return accessInfoId;
	}
	public void setAccessInfoId(String accessInfoId) {
		this.accessInfoId = accessInfoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	public int getCountAction() {
		return countAction;
	}
	public void setCountAction(int countAction) {
		this.countAction = countAction;
	}
	
}
