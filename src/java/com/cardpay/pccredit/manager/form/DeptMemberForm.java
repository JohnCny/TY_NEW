package com.cardpay.pccredit.manager.form;

import com.wicresoft.jrad.base.web.form.BaseForm;

public class DeptMemberForm extends BaseForm {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String oname;
	private String name;
	private String display_name;
	private String external_id;
	
	
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getExternal_id() {
		return external_id;
	}
	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}
	
	
	

}
