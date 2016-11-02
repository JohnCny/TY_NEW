/**
 * 
 */
package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author songchen
 */

public class CustomerInfoLszFilter extends BaseQueryFilter{
	private String jjh;//借据号

	public String getJjh() {
		return jjh;
	}

	public void setJjh(String jjh) {
		this.jjh = jjh;
	}
	
}
