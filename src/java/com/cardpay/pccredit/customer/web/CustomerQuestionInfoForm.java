package com.cardpay.pccredit.customer.web;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * CustomerQuestionInfo类的描述
 * 
 * @author 王海东
 * @created on 2014-10-30
 * 
 * @version $Id:$
 */
public class CustomerQuestionInfoForm extends BaseForm {

	private static final long serialVersionUID = 1L;
	private String customerId;
	private String questionCode;
	private String questionName;
	private String questionAnswer;
	private String createdBy;
	private Date createdTime;
	private String modifiedBy;
	private Date modifiedTime;

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

}
