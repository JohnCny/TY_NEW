package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 
 * @author 季东晓
 *
 * 2014-11-13 下午3:44:17
 */
public class CustomerOverdueHistoryFilter extends BaseQueryFilter {

	private String customerId;

	private String productId;

	

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
