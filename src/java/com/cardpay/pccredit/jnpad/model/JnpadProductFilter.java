package com.cardpay.pccredit.jnpad.model;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;
/**
 * 产品过滤条件filter
 * @author sealy
 *
 */
public class JnpadProductFilter extends BaseQueryFilter{

	
	private String productName;
	
	private String type;
	
	private String status;
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}
