package com.cardpay.pccredit.customer.filter;

import java.util.List;

import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.database.dao.query.Operator;
import com.wicresoft.jrad.base.database.dao.query.QueryParam;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 
 * @author shaoming
 *
 */
public class CustomerRecordFilter extends BaseQueryFilter{
	private String recordId;//档案编号
	private String cardId;//证件号码
	private String customerName;//客户名称
	private String userId;//客户经理
	private String recordStatus;//档案状态
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

}
