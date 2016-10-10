package com.cardpay.pccredit.dimensional.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * Description of DimensionalFilter
 * 
 * @author 王海东
 * @created on 2014-10-20
 * 
 * @version $Id:$
 */
public class DimensionalFilter extends BaseQueryFilter {

	private String customerName;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
