/**
 * 
 */
package com.cardpay.pccredit.riskControl.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author shaoming
 *
 * 2014年11月6日   下午3:18:08
 */
@ModelParam(table = "collection_plan",generator=IDType.uuid32)
public class RiskCustomerCollectionPlan extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String createWay;
	private String customerId;
	private String productId;
	private String implementationObjective;
	private String endResult;
	private String collectionTime;
	private String collectionMethod;
	private Date collectionPromiseDate;
	private Date collectionEndtime;
	private String collectionAmount;
	private String customerManagerId;
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
	public String getCollectionAmount() {
		return collectionAmount;
	}
	public void setCollectionAmount(String collectionAmount) {
		this.collectionAmount = collectionAmount;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}	
}
