package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;

public class CustomerRecord  extends BusinessModel {
	
	private static final long serialVersionUID = 1L;
	private String recordId;//档案编号
	private String id;//进件编号
	private String customerId;//客户ID(外键)
	private String chineseName;//客户名称
	private String cardId;//证件号码
	
    private String productName; //产品名称
    private String actualQuote;//发放额度
    private String ids;//借阅主键
    private String status;//进件状态
    private  String recordStatus;//档案状态   
    private Date outTime;//借出时间
    private Date inTime;//归还时间
    private String displayName; //客户经理
    private String userId;//客户经理id
    private String groupName; //客户经理组长
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getActualQuote() {
		return actualQuote;
	}
	public void setActualQuote(String actualQuote) {
		this.actualQuote = actualQuote;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public Date getInTime() {
		return inTime;
	}
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
    
}
