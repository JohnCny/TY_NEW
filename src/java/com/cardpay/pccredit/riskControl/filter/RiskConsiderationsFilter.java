package com.cardpay.pccredit.riskControl.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-11-5上午10:41:00
 */
public class RiskConsiderationsFilter extends BaseQueryFilter {
	// 角色
	private String role;
	
	private String institutions;
	
	private String area;
	
	private String industry;
	
	private String riskIssuesDescribed;
	
	private String riskLevel;
	
	private String informationChannel;
	
	private String whetherThrough;
	
	private String releaseInstitution;

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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
