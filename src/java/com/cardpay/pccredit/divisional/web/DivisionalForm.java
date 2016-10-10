package com.cardpay.pccredit.divisional.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

public class DivisionalForm extends BaseForm{
	private static final long serialVersionUID = 1L;
	private String id;
	private String customerId;
	private String customerManagerId;
	private String orgId;
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
}
