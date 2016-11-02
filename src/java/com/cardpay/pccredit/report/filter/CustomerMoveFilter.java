package com.cardpay.pccredit.report.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class CustomerMoveFilter extends BaseQueryFilter {
	private String customerName;
	private String certiCode;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCertiCode() {
		return certiCode;
	}
	public void setCertiCode(String certiCode) {
		this.certiCode = certiCode;
	}
	
}
