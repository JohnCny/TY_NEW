/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author shaoming
 *
 * 2014年11月3日   上午9:24:06
 */
public class InformationOfficerFilter extends BaseQueryFilter{
	private String customerManagerId;
	private String name;
	private String telephone;
	private String idCardNumber;
	private String address;
	private String workUnit;
	private String jobDuty;
	private String infoMaintenanceArea;
	private String infoOfficerJob;
	private String researcherFeedbackWay;
	private String researcherPay;
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
	public String getIdCardNumber() {
		return idCardNumber;
	}
	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getJobDuty() {
		return jobDuty;
	}
	public void setJobDuty(String jobDuty) {
		this.jobDuty = jobDuty;
	}
	public String getInfoMaintenanceArea() {
		return infoMaintenanceArea;
	}
	public void setInfoMaintenanceArea(String infoMaintenanceArea) {
		this.infoMaintenanceArea = infoMaintenanceArea;
	}
	public String getInfoOfficerJob() {
		return infoOfficerJob;
	}
	public void setInfoOfficerJob(String infoOfficerJob) {
		this.infoOfficerJob = infoOfficerJob;
	}
	public String getResearcherFeedbackWay() {
		return researcherFeedbackWay;
	}
	public void setResearcherFeedbackWay(String researcherFeedbackWay) {
		this.researcherFeedbackWay = researcherFeedbackWay;
	}
	public String getResearcherPay() {
		return researcherPay;
	}
	public void setResearcherPay(String researcherPay) {
		this.researcherPay = researcherPay;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	
}
