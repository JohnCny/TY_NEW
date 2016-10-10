/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author shaoming
 *
 * 2014年11月3日   下午1:26:34
 */
public class ManagerInformationClientFilter extends BaseQueryFilter{
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
