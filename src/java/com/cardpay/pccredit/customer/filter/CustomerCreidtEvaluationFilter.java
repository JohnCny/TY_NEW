package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;


/**
 * CustomerCreidtEvaluationFilter类的描述
 *
 * @author 王海东
 * @created on 2014-12-24
 * 
 * @version $Id:$
 */
public class CustomerCreidtEvaluationFilter extends BaseQueryFilter{
	
	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
