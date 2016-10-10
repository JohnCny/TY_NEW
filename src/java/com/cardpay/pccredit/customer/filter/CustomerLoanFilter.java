package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class CustomerLoanFilter extends BusinessFilter{
	
    private String customerName;//客户名称
    
    private String cardId;//证件号码
    
    private String userId;
    
    private String custManagerIds;
    

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustManagerIds() {
		return custManagerIds;
	}

	public void setCustManagerIds(String custManagerIds) {
		this.custManagerIds = custManagerIds;
	}
}
