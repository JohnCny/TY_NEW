package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.database.dao.query.Operator;
import com.wicresoft.jrad.base.database.dao.query.QueryParam;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;
/**
 * 
 * @author 季东晓
 *
 * 2014-10-30 下午1:30:52
 */

public class CustomerInforUpdateWorshipFilter extends BaseQueryFilter {
	
	@QueryParam(operator = Operator.equals, column = "customer_id")
	private String customerId;
	
	@QueryParam(operator = Operator.equals, column = "customer_id")
	private String applicationId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

}
