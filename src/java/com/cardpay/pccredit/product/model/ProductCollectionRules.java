package com.cardpay.pccredit.product.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 产品催收规则
 * 
 * @author 王海东
 * @created on 2014-11-10
 * 
 * @version $Id:$
 */

@ModelParam(table = "product_collection_rules")
public class ProductCollectionRules extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String aging;
	private String collectionType; // 催收类型 date or age

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	private String money;
	private String collectionTime;
	private String collectionWay;
	private String operation;
	private String overdueDayEnd;
	private String overdueDayStart;
	private Date createdTime;
	private String createdBy;
	private Date modifiedTime;
	private String modifiedBy;

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOverdueDayEnd() {
		return overdueDayEnd;
	}

	public void setOverdueDayEnd(String overdueDayEnd) {
		this.overdueDayEnd = overdueDayEnd;
	}

	public String getOverdueDayStart() {
		return overdueDayStart;
	}

	public void setOverdueDayStart(String overdueDayStart) {
		this.overdueDayStart = overdueDayStart;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}

	public String getCollectionWay() {
		return collectionWay;
	}

	public void setCollectionWay(String collectionWay) {
		this.collectionWay = collectionWay;
	}

}
