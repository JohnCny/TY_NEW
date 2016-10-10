package com.cardpay.pccredit.manager.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * SysOrganizationForm类的描述
 * 
 * @author sc
 * @created on 2015-8-20
 * 
 * @version $Id:$
 */
public class SysOrganizationForm extends BaseForm {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String displayName;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	
	
}
