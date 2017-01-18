package com.cardpay.pccredit.riskControl.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 风险客户
 * @author chenzhifang
 *
 * 2014-10-24下午5:44:41
 */
@ModelParam(table = "risk_list",generator=IDType.uuid32)
public class RiskCustomer extends BusinessModel{
	
	private static final long serialVersionUID = 1L;

	private String customerId;
	
	private String riskLevel;
	
	private String riskCreateType;
	
	private String riskDes;
	
	// 风险属性
	private String riskAttribute;
	
	private String status;
	
	private String refuseReason;
	
	private String reportedIdManager;
	
	// 客户名称
	private String customerName;
	
	// 客户经理名称
	private String reportedManager;
	
	// 证件类型
	private String cardType;
	
	// 证件号码
	private String cardId;
	
	private String maxRiskLevel;//最大风险等级
	
	private String numRisk;//风险个数
	
	private String riskDescription;//风险属性
	
	
	private String productId;
	
	private String productName;
	
	
	private String  custManagerId;
	
	private Date  CREATED_TIME;
	private String userId;
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCREATED_TIME() {
		return CREATED_TIME;
	}

	public void setCREATED_TIME(Date cREATED_TIME) {
		CREATED_TIME = cREATED_TIME;
	}

	public String getCustManagerId() {
		return custManagerId;
	}

	public void setCustManagerId(String custManagerId) {
		this.custManagerId = custManagerId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMaxRiskLevel() {
		return maxRiskLevel;
	}

	public void setMaxRiskLevel(String maxRiskLevel) {
		this.maxRiskLevel = maxRiskLevel;
	}

	public String getNumRisk() {
		return numRisk;
	}

	public void setNumRisk(String numRisk) {
		this.numRisk = numRisk;
	}

	public String getReportedManager() {
		return reportedManager;
	}

	public void setReportedManager(String reportedManager) {
		this.reportedManager = reportedManager;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getReportedIdManager() {
		return reportedIdManager;
	}

	public void setReportedIdManager(String reportedIdManager) {
		this.reportedIdManager = reportedIdManager;
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

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getRiskAttribute() {
		return riskAttribute;
	}

	public void setRiskAttribute(String riskAttribute) {
		this.riskAttribute = riskAttribute;
	}

	public String getRiskDes() {
		return riskDes;
	}

	public void setRiskDes(String riskDes) {
		this.riskDes = riskDes;
	}

	public String getRiskCreateType() {
		return riskCreateType;
	}

	public void setRiskCreateType(String riskCreateType) {
		this.riskCreateType = riskCreateType;
	}

	public String getRiskDescription() {
		return riskDescription;
	}

	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
	}
}
