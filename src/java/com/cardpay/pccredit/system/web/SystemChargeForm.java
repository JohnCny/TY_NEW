package com.cardpay.pccredit.system.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

public class SystemChargeForm extends BaseForm{
	
	private static final long serialVersionUID = 1L;
	
	private String userIds;
	private String orgId;
	private String deptId;
	
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
}
