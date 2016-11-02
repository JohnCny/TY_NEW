package com.cardpay.pccredit.riskControl.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author chenzhifang
 *
 * 2014-11-5上午11:22:15
 */
public class RiskConsiderationsForm extends BaseForm {
	private static final long serialVersionUID = 1163336590697532939L;

	private String institutions;
	
	private String area;
	
	private String industry;
	
	private String riskIssuesDescribed;
	
	private String riskLevel;
	
	private String informationChannel;
	
	private String whetherThrough;
	
	private String releaseInstitution;
	
	private Integer maintenanceDay;
	
	private String maintenanceTarget;
	
	private String maintenanceWay;

	public String getInstitutions() {
		return institutions;
	}

	public void setInstitutions(String institutions) {
		this.institutions = institutions;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getRiskIssuesDescribed() {
		return riskIssuesDescribed;
	}

	public void setRiskIssuesDescribed(String riskIssuesDescribed) {
		this.riskIssuesDescribed = riskIssuesDescribed;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getInformationChannel() {
		return informationChannel;
	}

	public void setInformationChannel(String informationChannel) {
		this.informationChannel = informationChannel;
	}

	public String getWhetherThrough() {
		return whetherThrough;
	}

	public void setWhetherThrough(String whetherThrough) {
		this.whetherThrough = whetherThrough;
	}

	public String getReleaseInstitution() {
		return releaseInstitution;
	}

	public void setReleaseInstitution(String releaseInstitution) {
		this.releaseInstitution = releaseInstitution;
	}

	public Integer getMaintenanceDay() {
		return maintenanceDay;
	}

	public void setMaintenanceDay(Integer maintenanceDay) {
		this.maintenanceDay = maintenanceDay;
	}

	public String getMaintenanceTarget() {
		return maintenanceTarget;
	}

	public void setMaintenanceTarget(String maintenanceTarget) {
		this.maintenanceTarget = maintenanceTarget;
	}

	public String getMaintenanceWay() {
		return maintenanceWay;
	}

	public void setMaintenanceWay(String maintenanceWay) {
		this.maintenanceWay = maintenanceWay;
	}
}
