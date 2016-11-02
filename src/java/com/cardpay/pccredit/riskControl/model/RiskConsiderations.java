package com.cardpay.pccredit.riskControl.model;

import org.apache.commons.lang.StringUtils;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 风险事项表
 * @author chenzhifang
 *
 * 2014-11-5上午10:35:22
 */
@ModelParam(table = "risk_considerations",generator=IDType.uuid32)
public class RiskConsiderations extends BusinessModel {
	private static final long serialVersionUID = -613831065437558292L;
	// 机构
	private String institutions;
	// 区域
	private String area;
	// 行业
	private String industry;
	// 风险事项描述
	private String riskIssuesDescribed;
	// 风险等级
	private String riskLevel;
	// 信息渠道
	private String informationChannel;
	// 是否通过
	private String whetherThrough;
	// 发布机构
	private String releaseInstitution;
	
	private Integer maintenanceDay;
	
	private String maintenanceTarget;
	
	private String maintenanceWay;

	public String getProvince(){
		return !StringUtils.isEmpty(area) ? area.split(",")[0] : "";
	}
	
	public String getCity(){
		return !StringUtils.isEmpty(area) ? area.split(",")[1] : "";
	}
	
	public String getCountry(){
		return !StringUtils.isEmpty(area) ? area.split(",")[2] : "";
	}
	
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
