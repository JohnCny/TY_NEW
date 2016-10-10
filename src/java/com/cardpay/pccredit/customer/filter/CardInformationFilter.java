package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-11-3下午3:51:30
 */
public class CardInformationFilter extends BaseQueryFilter {
	
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
