package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 
 * 描述 ：账户filter
 * @author 张石树
 *
 * 2014-11-5 上午11:49:26
 */
public class CustomerAccountInforFilter extends BaseQueryFilter{
	
	private String customerName;
	
	private String accountStatus;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
}
