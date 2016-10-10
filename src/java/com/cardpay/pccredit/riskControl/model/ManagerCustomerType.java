package com.cardpay.pccredit.riskControl.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
@ModelParam(table = "manager_customer_type",generator=IDType.uuid32)
public class ManagerCustomerType extends BusinessModel {
	
	private static final long serialVersionUID = 1L;
	
	private String levelId;//层级
	
	private String customerType;//客户类型

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	

}
