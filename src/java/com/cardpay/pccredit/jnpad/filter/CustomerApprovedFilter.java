package com.cardpay.pccredit.jnpad.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class CustomerApprovedFilter  extends BaseQueryFilter{
	private String status;
	private String userId;
	private String productName;
	private String id ;
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	} 
}
