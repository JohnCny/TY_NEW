package com.cardpay.pccredit.zrrtz.model;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class ZrrtzFilter extends BusinessFilter{
	
	private String id;//进件编号
	private String chineseName;//客户名称
    private String productName; //产品名称
    private String cardId; //证件号码
    
    private String status;//状态
    
    private String userId;
    
    private String decision;
    
    private String custManagerIds;
    
    private String customerId;
    
    private String fdate;
	private String ldate;
	
	
    
    
	public String getFdate() {
		return fdate;
	}
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}
	public String getLdate() {
		return ldate;
	}
	public void setLdate(String ldate) {
		this.ldate = ldate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustManagerIds() {
		return custManagerIds;
	}
	public void setCustManagerIds(String custManagerIds) {
		this.custManagerIds = custManagerIds;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}
