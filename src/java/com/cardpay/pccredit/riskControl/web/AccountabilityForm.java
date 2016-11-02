package com.cardpay.pccredit.riskControl.web;

import com.wicresoft.jrad.base.web.form.BaseForm;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-7 下午3:52:15
 */
public class AccountabilityForm extends BaseForm{
	
	private static final long serialVersionUID = 1L;
	
    private String customerManagerId;
	
	private String customerId;
	
	private String productId;
	
	private String createWay;//移交客户、催收案件主动移交、强制移交、核销、人为触发（创建方式）
	
	private String createReason;//问责原因
	
	private String money;//金额
	
	private String forfeit;//罚金
	
	private String guaranty;//保证金
	
	private String accountabilityConclusion;//问责结论
	
	private String accountManagerComment;//客户经理意见
	
	private String reconsideration;//是否复议0否1是
	
	private String reconsiderationConclusion;//复议结论
	
	private String chineseName;//客户名称
	
	private String cardType;//证件类型
	
	private String cardId; //证件号码
	
	private String displayName;//客户经理的名称
	
	private String auditStatus;//审核状态 未审核 卡中心审核 审核结束
	
	private String auditStage;//审核阶段 卡中心 cfcc 客户经理 manager

	public String getAccountabilityConclusion() {
		return accountabilityConclusion;
	}

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
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

	public String getCreateWay() {
		return createWay;
	}

	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}

	public String getCreateReason() {
		return createReason;
	}

	public void setCreateReason(String createReason) {
		this.createReason = createReason;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getForfeit() {
		return forfeit;
	}

	public void setForfeit(String forfeit) {
		this.forfeit = forfeit;
	}

	public String getGuaranty() {
		return guaranty;
	}

	public void setGuaranty(String guaranty) {
		this.guaranty = guaranty;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}

	public void setAccountabilityConclusion(String accountabilityConclusion) {
		this.accountabilityConclusion = accountabilityConclusion;
	}

	public String getAccountManagerComment() {
		return accountManagerComment;
	}

	public void setAccountManagerComment(String accountManagerComment) {
		this.accountManagerComment = accountManagerComment;
	}

	public String getReconsideration() {
		return reconsideration;
	}

	public void setReconsideration(String reconsideration) {
		this.reconsideration = reconsideration;
	}

	public String getReconsiderationConclusion() {
		return reconsiderationConclusion;
	}

	public void setReconsiderationConclusion(String reconsiderationConclusion) {
		this.reconsiderationConclusion = reconsiderationConclusion;
	}
	

}
