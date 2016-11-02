package com.cardpay.pccredit.intopieces.filter;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class IntoPiecesFilter extends BusinessFilter{
	
	private String id;//进件编号
	private String chineseName;//客户名称
    private String productName; //产品名称
    private String cardId; //证件号码
    
    private String status;
    
    private String userId;
    
    private String decision;
    
    private String custManagerIds;
    
    /*added by sc 节点中文名 */
	private String nextNodeName;
	
	
	 private String isUpload;
	 private String first_flag;
	 private String batchId;
	 private String viewType;//单张查看 翻页查看
	 private String type;//操作类型
	 private String originalName;
	 
	 private String startAmt;
	 private String endAmt;
	 
	 private String custManagerId;
	 private String organName;
	 
	 
    
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getCustManagerId() {
		return custManagerId;
	}
	public void setCustManagerId(String custManagerId) {
		this.custManagerId = custManagerId;
	}
	public String getStartAmt() {
		return startAmt;
	}
	public void setStartAmt(String startAmt) {
		this.startAmt = startAmt;
	}
	public String getEndAmt() {
		return endAmt;
	}
	public void setEndAmt(String endAmt) {
		this.endAmt = endAmt;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(String isUpload) {
		this.isUpload = isUpload;
	}
	public String getFirst_flag() {
		return first_flag;
	}
	public void setFirst_flag(String first_flag) {
		this.first_flag = first_flag;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
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
	public String getNextNodeName() {
		return nextNodeName;
	}
	public void setNextNodeName(String nextNodeName) {
		this.nextNodeName = nextNodeName;
	}
	
	
	
}
