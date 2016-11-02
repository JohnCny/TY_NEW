/**
 * 
 */
package com.cardpay.pccredit.ipad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.util.date.DateHelper;

/**
 * @author shaoming
 *
 * 2014年11月25日   下午3:58:06
 */
public class ProductAttribute extends BusinessModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8453126276207509013L;
	private String id;
	private String productName;
	private String purposeLoan;
	private String creditLine;
	private String rateRange;
	private String productTypeCode;
	private String loanTimeLimit;
	private String loanPrincipal;
	private String letterPaymentWay;
	private String numbererestSettlementWay;
	private String assureMeans;
	private String penaltyNumbererest;
	private String productRiskTolerance;
	private String status;
	private String seqno;
	private Date createdTime;
	private String createdBy;
	private Date modifiedTime;
	private String modifiedBy;
	private String pictureUrl;
	private String pictureName;
	
	private String prodLiTime;
	
	
	
	public String getProdLiTime() {
		return prodLiTime;
	}
	public void setProdLiTime(String prodLiTime) {
		this.prodLiTime = prodLiTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPurposeLoan() {
		return purposeLoan;
	}
	public void setPurposeLoan(String purposeLoan) {
		this.purposeLoan = purposeLoan;
	}
	public String getCreditLine() {
		return creditLine;
	}
	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}
	public String getRateRange() {
		return rateRange;
	}
	public void setRateRange(String rateRange) {
		this.rateRange = rateRange;
	}
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	public String getLoanTimeLimit() {
		return loanTimeLimit;
	}
	public void setLoanTimeLimit(String loanTimeLimit) {
		this.loanTimeLimit = loanTimeLimit;
	}
	public String getLoanPrincipal() {
		return loanPrincipal;
	}
	public void setLoanPrincipal(String loanPrincipal) {
		this.loanPrincipal = loanPrincipal;
	}
	public String getLetterPaymentWay() {
		return letterPaymentWay;
	}
	public void setLetterPaymentWay(String letterPaymentWay) {
		this.letterPaymentWay = letterPaymentWay;
	}
	public String getNumbererestSettlementWay() {
		return numbererestSettlementWay;
	}
	public void setNumbererestSettlementWay(String numbererestSettlementWay) {
		this.numbererestSettlementWay = numbererestSettlementWay;
	}
	public String getAssureMeans() {
		return assureMeans;
	}
	public void setAssureMeans(String assureMeans) {
		this.assureMeans = assureMeans;
	}
	public String getPenaltyNumbererest() {
		return penaltyNumbererest;
	}
	public void setPenaltyNumbererest(String penaltyNumbererest) {
		this.penaltyNumbererest = penaltyNumbererest;
	}
	public String getProductRiskTolerance() {
		return productRiskTolerance;
	}
	public void setProductRiskTolerance(String productRiskTolerance) {
		this.productRiskTolerance = productRiskTolerance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
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
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
}
