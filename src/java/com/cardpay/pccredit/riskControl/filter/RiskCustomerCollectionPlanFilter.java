/**
 * 
 */
package com.cardpay.pccredit.riskControl.filter;

import java.util.Date;
import java.util.List;

import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author shaoming
 *
 * 2014年11月6日   下午3:38:44
 */
public class RiskCustomerCollectionPlanFilter extends BaseQueryFilter{
	private String id;
	private String createWay;
	private String customerId;
	private String customerManagerId;
	private List<AccountManagerParameterForm> customerManagerIds;
	private String productId;
	private String implementationObjective;
	private String endResult;
	private String collectionTime;
	private String collectionMethod;
	private Date collectionPromiseDate;
	private Date collectionEndtime;
	
	
	public List<AccountManagerParameterForm> getCustomerManagerIds() {
		return customerManagerIds;
	}
	public void setCustomerManagerIds(
			List<AccountManagerParameterForm> customerManagerIds) {
		this.customerManagerIds = customerManagerIds;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getImplementationObjective() {
		return implementationObjective;
	}
	public void setImplementationObjective(String implementationObjective) {
		this.implementationObjective = implementationObjective;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public String getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}
	public String getCollectionMethod() {
		return collectionMethod;
	}
	public void setCollectionMethod(String collectionMethod) {
		this.collectionMethod = collectionMethod;
	}
	public Date getCollectionPromiseDate() {
		return collectionPromiseDate;
	}
	public void setCollectionPromiseDate(Date collectionPromiseDate) {
		this.collectionPromiseDate = collectionPromiseDate;
	}
	public Date getCollectionEndtime() {
		return collectionEndtime;
	}
	public void setCollectionEndtime(Date collectionEndtime) {
		this.collectionEndtime = collectionEndtime;
	}
	
}
