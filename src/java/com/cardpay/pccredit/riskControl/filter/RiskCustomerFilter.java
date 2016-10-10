package com.cardpay.pccredit.riskControl.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-10-24下午5:54:43
 */
public class RiskCustomerFilter extends BaseQueryFilter{
	
	private String customerId;
	
	private String reportedIdManager;
	
	private String role;
	
	private String status;
	
	// 客户名称
	private String customerName;
	
	// 证件类型
	private String cardType;
	
	// 证件号码
	private String cardId;
	
	// 风险类型
	private String riskCreateType;
	
	// 风险属性
	private String riskAttribute;
	
	private String custManagerId;
	
	public String getCustManagerId() {
		return custManagerId;
	}

	public void setCustManagerId(String custManagerId) {
		this.custManagerId = custManagerId;
	}

	public String getStatus() {
		return status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRiskCreateType() {
		return riskCreateType;
	}

	public void setRiskCreateType(String riskCreateType) {
		this.riskCreateType = riskCreateType;
	}

	public String getReportedIdManager() {
		return reportedIdManager;
	}

	public void setReportedIdManager(String reportedIdManager) {
		this.reportedIdManager = reportedIdManager;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRiskAttribute() {
		return riskAttribute;
	}

	public void setRiskAttribute(String riskAttribute) {
		this.riskAttribute = riskAttribute;
	}
}
