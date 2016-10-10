package com.cardpay.pccredit.system.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "system_configuration")
public class SystemConfiguration extends BusinessModel{
	
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
