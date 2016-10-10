/**
 * 
 */
package com.cardpay.pccredit.riskControl.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author shaoming
 *
 * 2014年11月6日   下午4:05:25
 */
public class RiskCustomerCollectionPlanForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String createWay;
	private String customerId;
	private String chineseName;
	private String productId;
	private String productName;
	private String userName;
	private String implementationObjective;
	private String endResult;
	private String collectionTime;
	private String collectionMethod;
	private Date collectionPromiseDate;
	private Date collectionEndtime;
	private String collectionAmount;
	private String customerManagerId;
	private String collectionStartTime;
	private int size;
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
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getCollectionStartTime() {
		return collectionStartTime;
	}
	public void setCollectionStartTime(String collectionStartTime) {
		this.collectionStartTime = collectionStartTime;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}
