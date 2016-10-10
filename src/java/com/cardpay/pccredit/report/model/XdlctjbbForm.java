package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 信贷流程统计
 * @author songchen
 */
public class XdlctjbbForm extends BaseForm {

	private static final long serialVersionUID = -8860955438817002631L;

	private String   chineseName;                 
	private String   cardId;                       
	private String   prodName;      
	private String   applyQuota ;                   
	private String   createdTime;                   
	private String   clts;                         
	private String   dcr;                          
	private String   dcts;                         
	private String   sdhrq;                        
	private String   sdhts;                        
	private String   dkffjjr;                      
	private String   totalNum;                     
	private String   custManager;
	private String   orgCode;                     
	private String 	 name;
	private String   rowIndex;//序号
	
	public String getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
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
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getApplyQuota() {
		return applyQuota;
	}
	public void setApplyQuota(String applyQuota) {
		this.applyQuota = applyQuota;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getClts() {
		return clts;
	}
	public void setClts(String clts) {
		this.clts = clts;
	}
	public String getDcr() {
		return dcr;
	}
	public void setDcr(String dcr) {
		this.dcr = dcr;
	}
	public String getDcts() {
		return dcts;
	}
	public void setDcts(String dcts) {
		this.dcts = dcts;
	}
	public String getSdhrq() {
		return sdhrq;
	}
	public void setSdhrq(String sdhrq) {
		this.sdhrq = sdhrq;
	}
	public String getSdhts() {
		return sdhts;
	}
	public void setSdhts(String sdhts) {
		this.sdhts = sdhts;
	}
	public String getDkffjjr() {
		return dkffjjr;
	}
	public void setDkffjjr(String dkffjjr) {
		this.dkffjjr = dkffjjr;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getCustManager() {
		return custManager;
	}
	public void setCustManager(String custManager) {
		this.custManager = custManager;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	   
	   
}