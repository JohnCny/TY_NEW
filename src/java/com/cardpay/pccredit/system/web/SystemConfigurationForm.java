package com.cardpay.pccredit.system.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

public class SystemConfigurationForm extends BaseForm{
	
	private static final long serialVersionUID = 1L;
	
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
