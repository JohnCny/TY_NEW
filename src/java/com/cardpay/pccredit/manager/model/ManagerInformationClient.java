package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author shaoming
 *
 * 2014年11月3日   上午9:58:47
 */
@ModelParam(table = "MANAGER_INFORMATION_CLIENT",generator=IDType.assigned)
public class ManagerInformationClient extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messengerId;
	private String customerManagerId;
	private String customerId;
	public String getMessengerId() {
		return messengerId;
	}
	public void setMessengerId(String messengerId) {
		this.messengerId = messengerId;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}	
	
}
