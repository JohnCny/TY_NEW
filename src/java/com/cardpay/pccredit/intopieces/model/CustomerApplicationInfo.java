package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "customer_application_info", generator=IDType.assigned)
public class CustomerApplicationInfo  extends BusinessModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4785713660231246580L;

	private String code;

    private String customerId;

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
    
    private String uploadStatus;
    
    private String isAutoPay;
    
    private String serialNumber;

    private String decisionAmount;//审议结论-金额
    private String decisionRate;//审议结论-利率
    private String decisionTerm;//审议结论-期限
    private String decisionRefusereason;//审议结论-拒绝原因
    
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getApplyQuota() {
        return applyQuota;
    }

    public void setApplyQuota(String applyQuota) {
        this.applyQuota = applyQuota == null ? null : applyQuota.trim();
    }

    public String getFinalApproval() {
        return finalApproval;
    }

    public void setFinalApproval(String finalApproval) {
        this.finalApproval = finalApproval == null ? null : finalApproval.trim();
    }

    public String getActualQuote() {
        return actualQuote;
    }

    public void setActualQuote(String actualQuote) {
        this.actualQuote = actualQuote == null ? null : actualQuote.trim();
    }

    public String getTemporaryQuota() {
        return temporaryQuota;
    }

    public void setTemporaryQuota(String temporaryQuota) {
        this.temporaryQuota = temporaryQuota == null ? null : temporaryQuota.trim();
    }

    public String getCashAdvanceProportion() {
        return cashAdvanceProportion;
    }

    public void setCashAdvanceProportion(String cashAdvanceProportion) {
        this.cashAdvanceProportion = cashAdvanceProportion == null ? null : cashAdvanceProportion.trim();
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus == null ? null : cardStatus.trim();
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus == null ? null : accountStatus.trim();
    }

    public String getBillingDate() {
        return billingDate;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate == null ? null : billingDate.trim();
    }

    public String getRepaymentAgreement() {
        return repaymentAgreement;
    }

    public void setRepaymentAgreement(String repaymentAgreement) {
        this.repaymentAgreement = repaymentAgreement == null ? null : repaymentAgreement.trim();
    }

    public String getAutomaticRepaymentAgreement() {
        return automaticRepaymentAgreement;
    }

    public void setAutomaticRepaymentAgreement(String automaticRepaymentAgreement) {
        this.automaticRepaymentAgreement = automaticRepaymentAgreement == null ? null : automaticRepaymentAgreement.trim();
    }

    public String getCustomerRiskRating() {
        return customerRiskRating;
    }

    public void setCustomerRiskRating(String customerRiskRating) {
        this.customerRiskRating = customerRiskRating == null ? null : customerRiskRating.trim();
    }

    public String getAging() {
        return aging;
    }

    public void setAging(String aging) {
        this.aging = aging == null ? null : aging.trim();
    }

    public String getDebitWay() {
        return debitWay;
    }

    public void setDebitWay(String debitWay) {
        this.debitWay = debitWay == null ? null : debitWay.trim();
    }

    public String getRepaymentCardNumber() {
        return repaymentCardNumber;
    }

    public void setRepaymentCardNumber(String repaymentCardNumber) {
        this.repaymentCardNumber = repaymentCardNumber == null ? null : repaymentCardNumber.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getIsAutoPay() {
		return isAutoPay;
	}

	public void setIsAutoPay(String isAutoPay) {
		this.isAutoPay = isAutoPay;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDecisionAmount() {
		return decisionAmount;
	}

	public void setDecisionAmount(String decisionAmount) {
		this.decisionAmount = decisionAmount;
	}

	public String getDecisionRate() {
		return decisionRate;
	}

	public void setDecisionRate(String decisionRate) {
		this.decisionRate = decisionRate;
	}

	public String getDecisionTerm() {
		return decisionTerm;
	}

	public void setDecisionTerm(String decisionTerm) {
		this.decisionTerm = decisionTerm;
	}

	public String getDecisionRefusereason() {
		return decisionRefusereason;
	}

	public void setDecisionRefusereason(String decisionRefusereason) {
		this.decisionRefusereason = decisionRefusereason;
	}

}