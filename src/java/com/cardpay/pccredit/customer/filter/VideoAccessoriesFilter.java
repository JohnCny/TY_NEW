package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.database.dao.query.Operator;
import com.wicresoft.jrad.base.database.dao.query.QueryParam;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author tonyxc
 *
 * 2014-11-3下午3:51:30
 */
public class VideoAccessoriesFilter extends BaseQueryFilter {
	
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}
