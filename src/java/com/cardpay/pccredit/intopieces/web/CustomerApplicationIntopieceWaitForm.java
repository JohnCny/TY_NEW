package com.cardpay.pccredit.intopieces.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * CustomerApplicationIntopieceWaitForm类的描述
 * 
 * @author 王海东
 * @created on 2014-11-28
 * 
 * @version $Id:$
 */
public class CustomerApplicationIntopieceWaitForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	// CustomerApplicationInfo
	private String id;
	private String code;
	private String customerId;
	private String chineseName;
	private String residentialAddress;
	private String examineAmount;
	private String cardType;
	private String cardId;
	private String applyCredit;
	private String productId;
	private String applyQuota;
	private String finalApproval;
	private String actualQuote;
	private String temporaryQuota;
	private String cashAdvanceProportion;
	private String cardStatus;
	private String accountStatus;
	private String billingDate;
	private String repaymentAgreement;
	private String automaticRepaymentAgreement;
	private String customerRiskRating;
	private String aging;
	private String debitWay;
	private String repaymentCardNumber;
	private String status;
	// WfStatusQueueRecord
	private String beforeStatus;
	private String currentProcess;
	private String currentStatus;
	private String examineResult;
	private String examineUser;
	private Date startExamineTime;
	private String subprocessIsClosed;
	// customer_application_process
	private String applicationId;
	private String auditUser;
	private String serialNumber;
	private String auditOpinion;
	private String refusalReason;
	private String fallbackReason;
	private String nodeId;
	private String nextNodeId;
	private String delayAuditUser;
	private Date auditTime;
	private Date createdTime;
	
	private String nodeName; //下一个节点的名称

	private String userId;
	private String productName;
	
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getExamineAmount() {
		return examineAmount;
	}

	public void setExamineAmount(String examineAmount) {
		this.examineAmount = examineAmount;
	}

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
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

	public String getApplyCredit() {
		return applyCredit;
	}

	public void setApplyCredit(String applyCredit) {
		this.applyCredit = applyCredit;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getApplyQuota() {
		return applyQuota;
	}

	public void setApplyQuota(String applyQuota) {
		this.applyQuota = applyQuota;
	}

	public String getFinalApproval() {
		return finalApproval;
	}

	public void setFinalApproval(String finalApproval) {
		this.finalApproval = finalApproval;
	}

	public String getActualQuote() {
		return actualQuote;
	}

	public void setActualQuote(String actualQuote) {
		this.actualQuote = actualQuote;
	}

	public String getTemporaryQuota() {
		return temporaryQuota;
	}

	public void setTemporaryQuota(String temporaryQuota) {
		this.temporaryQuota = temporaryQuota;
	}

	public String getCashAdvanceProportion() {
		return cashAdvanceProportion;
	}

	public void setCashAdvanceProportion(String cashAdvanceProportion) {
		this.cashAdvanceProportion = cashAdvanceProportion;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public String getBillingDate() {
		return billingDate;
	}

	public void setBillingDate(String billingDate) {
		this.billingDate = billingDate;
	}

	public String getRepaymentAgreement() {
		return repaymentAgreement;
	}

	public void setRepaymentAgreement(String repaymentAgreement) {
		this.repaymentAgreement = repaymentAgreement;
	}

	public String getAutomaticRepaymentAgreement() {
		return automaticRepaymentAgreement;
	}

	public void setAutomaticRepaymentAgreement(String automaticRepaymentAgreement) {
		this.automaticRepaymentAgreement = automaticRepaymentAgreement;
	}

	public String getCustomerRiskRating() {
		return customerRiskRating;
	}

	public void setCustomerRiskRating(String customerRiskRating) {
		this.customerRiskRating = customerRiskRating;
	}

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}

	public String getDebitWay() {
		return debitWay;
	}

	public void setDebitWay(String debitWay) {
		this.debitWay = debitWay;
	}

	public String getRepaymentCardNumber() {
		return repaymentCardNumber;
	}

	public void setRepaymentCardNumber(String repaymentCardNumber) {
		this.repaymentCardNumber = repaymentCardNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBeforeStatus() {
		return beforeStatus;
	}

	public void setBeforeStatus(String beforeStatus) {
		this.beforeStatus = beforeStatus;
	}

	public String getCurrentProcess() {
		return currentProcess;
	}

	public void setCurrentProcess(String currentProcess) {
		this.currentProcess = currentProcess;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getExamineResult() {
		return examineResult;
	}

	public void setExamineResult(String examineResult) {
		this.examineResult = examineResult;
	}

	public String getExamineUser() {
		return examineUser;
	}

	public void setExamineUser(String examineUser) {
		this.examineUser = examineUser;
	}

	public Date getStartExamineTime() {
		return startExamineTime;
	}

	public void setStartExamineTime(Date startExamineTime) {
		this.startExamineTime = startExamineTime;
	}

	public String getSubprocessIsClosed() {
		return subprocessIsClosed;
	}

	public void setSubprocessIsClosed(String subprocessIsClosed) {
		this.subprocessIsClosed = subprocessIsClosed;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getRefusalReason() {
		return refusalReason;
	}

	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}

	public String getFallbackReason() {
		return fallbackReason;
	}

	public void setFallbackReason(String fallbackReason) {
		this.fallbackReason = fallbackReason;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(String nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	public String getDelayAuditUser() {
		return delayAuditUser;
	}

	public void setDelayAuditUser(String delayAuditUser) {
		this.delayAuditUser = delayAuditUser;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
