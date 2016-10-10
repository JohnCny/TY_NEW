/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.database.model.BusinessModel;

@ModelParam(table = "qz_appln_attachment_list")
public class QzApplnAttachmentList extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String customerId;
	private String applicationId;
	private String bussType;
	private String chkValue;
	private String clientName;
	private String globalId;
	private String shopName;
	private String shopId;
	private String user_1;
	private String user_2;
	private String user_3;
	private String user_4;
	
	private String docid;
	private String uploadValue;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getGlobalId() {
		return globalId;
	}
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getChkValue() {
		return chkValue;
	}
	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getBussType() {
		return bussType;
	}
	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
	public String getUser_1() {
		return user_1;
	}
	public void setUser_1(String user_1) {
		this.user_1 = user_1;
	}
	public String getUser_2() {
		return user_2;
	}
	public void setUser_2(String user_2) {
		this.user_2 = user_2;
	}
	public String getUser_3() {
		return user_3;
	}
	public void setUser_3(String user_3) {
		this.user_3 = user_3;
	}
	public String getUser_4() {
		return user_4;
	}
	public void setUser_4(String user_4) {
		this.user_4 = user_4;
	}
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}
	public String getUploadValue() {
		return uploadValue;
	}
	public void setUploadValue(String uploadValue) {
		this.uploadValue = uploadValue;
	}
}
