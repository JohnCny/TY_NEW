package com.cardpay.pccredit.system.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class SystemConfigurationFilter extends BaseQueryFilter{
	
	
	private String id;
	private String sysCode;
	private String sysName;
	private String sysValues;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getSysValues() {
		return sysValues;
	}
	public void setSysValues(String sysValues) {
		this.sysValues = sysValues;
	}

}
