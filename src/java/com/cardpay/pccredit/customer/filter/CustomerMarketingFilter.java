package com.cardpay.pccredit.customer.filter;

import java.util.List;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 
 * @author 姚绍明
 *
 */
public class CustomerMarketingFilter extends BaseQueryFilter{
	private String id;
	private String createWay; //创建方式
	private String customerId;
	private String customerManagerId;
	private String productId;
	private String implementationObjectives;//实施目标
	private String endResult;
	private String marketingTime;
	private String marketingMethod;
	private List<String> ids;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getImplementationObjectives() {
		return implementationObjectives;
	}
	public void setImplementationObjectives(String implementationObjectives) {
		this.implementationObjectives = implementationObjectives;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
}
