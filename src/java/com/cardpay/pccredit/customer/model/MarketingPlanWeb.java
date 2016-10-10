package com.cardpay.pccredit.customer.model;

import java.math.BigDecimal;
import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;

public class MarketingPlanWeb extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String id;
	private String chineseName;
	private String productName;
	private String marketingTime;
	private String marketingMethod;
	private Date marketingEndtime;
	private String endResult;
	private String createWay;
	private String userName;
	private BigDecimal countAction;
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
	public String getMarketingTime() {
		return marketingTime;
	}
	public void setMarketingTime(String marketingTime) {
		this.marketingTime = marketingTime;
	}
	public String getMarketingMethod() {
		return marketingMethod;
	}
	public void setMarketingMethod(String marketingMethod) {
		this.marketingMethod = marketingMethod;
	}
	public Date getMarketingEndtime() {
		return marketingEndtime;
	}
	public void setMarketingEndtime(Date marketingEndtime) {
		this.marketingEndtime = marketingEndtime;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigDecimal getCountAction() {
		return countAction;
	}
	public void setCountAction(BigDecimal countAction) {
		this.countAction = countAction;
	}
	
}
