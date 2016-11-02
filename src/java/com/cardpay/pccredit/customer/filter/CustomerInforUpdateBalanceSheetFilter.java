package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 * 
 * 资产负债表查询过滤
 * 
 * 2014-10-22下午2:15:44
 */
public class CustomerInforUpdateBalanceSheetFilter extends BaseQueryFilter {
	
	private String customerId;
	private int loanType;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public int getLoanType() {
		return loanType;
	}
	public void setLoanType(int loanType) {
		this.loanType = loanType;
	}
}
