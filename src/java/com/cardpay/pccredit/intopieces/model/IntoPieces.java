package com.cardpay.pccredit.intopieces.model;

import java.math.BigDecimal;

import com.wicresoft.jrad.base.database.model.BusinessModel;

public class IntoPieces  extends BusinessModel {
	
	
	private String id;//进件编号
	private String customerId;//客户ID(外键)
	private String chineseName;//客户名称
	
    private String productId;//产品Id
    private String productName; //产品名称
    private String cardId;//证件号码
    private String applyQuota;//申请额度
    private String actualQuote;//发放额度
    private String status;//申请额度
    private String statusName;
    
    private String nodeName;
    
    private String tyCustomerId;
    
    private String decisionRefuseReason; //备注
    
    private String displayName; //客户经理
    private String userId;//客户经理id
    private String groupName; //客户经理组长
    
    private String jjh; //对应银行借据号
    private String jkrq; //对应银行借款日期
    
    
    private String reqlmt;
    
    private BigDecimal actualAmt;
    
    private BigDecimal amt;
    
    private String refusqlReason;
    private String fallBackReason;
    
    private String repayStatus;
    
    private String finalApproval;
    
    private String managerName;
    
    private String organName;
    
    
    
    public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	public String getRefusqlReason() {
		return refusqlReason;
	}
	public void setRefusqlReason(String refusqlReason) {
		this.refusqlReason = refusqlReason;
	}
	public String getFallBackReason() {
		return fallBackReason;
	}
	public void setFallBackReason(String fallBackReason) {
		this.fallBackReason = fallBackReason;
	}

	public String getReqlmt() {
		return reqlmt;
	}
	public void setReqlmt(String reqlmt) {
		this.reqlmt = reqlmt;
	}

	
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public BigDecimal getActualAmt() {
		return actualAmt;
	}
	public void setActualAmt(BigDecimal actualAmt) {
		this.actualAmt = actualAmt;
	}


    
    
	public String getFinalApproval() {
		return finalApproval;
	}
	public void setFinalApproval(String finalApproval) {
		this.finalApproval = finalApproval;
	}
	public String getDecisionRefuseReason() {
		return decisionRefuseReason;
	}
	public void setDecisionRefuseReason(String decisionRefuseReason) {
		this.decisionRefuseReason = decisionRefuseReason;
	}
	//进度
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
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getApplyQuota() {
		return applyQuota;
	}
	public void setApplyQuota(String applyQuota) {
		this.applyQuota = applyQuota;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getTyCustomerId() {
		return tyCustomerId;
	}
	public void setTyCustomerId(String tyCustomerId) {
		this.tyCustomerId = tyCustomerId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getActualQuote() {
		return actualQuote;
	}
	public void setActualQuote(String actualQuote) {
		this.actualQuote = actualQuote;
	}
	public String getJjh() {
		return jjh;
	}
	public void setJjh(String jjh) {
		this.jjh = jjh;
	}
	public String getJkrq() {
		return jkrq;
	}
	public void setJkrq(String jkrq) {
		this.jkrq = jkrq;
	}
	
}
