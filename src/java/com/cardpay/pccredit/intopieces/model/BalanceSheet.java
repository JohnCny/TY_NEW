package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "balance_sheet")
public class BalanceSheet extends BusinessModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5841337386594827879L;

	private Long loanApplyId;

	private Long loanType;

	private String name;

	private Long no;

	private String contentsTextNumber;

	private Long createdPeople;

	private Date createdTime;

	private Long modifyPeople;

	private Date modified;

	public Long getLoanApplyId() {
		return loanApplyId;
	}

	public void setLoanApplyId(Long loanApplyId) {
		this.loanApplyId = loanApplyId;
	}

	public Long getLoanType() {
		return loanType;
	}

	public void setLoanType(Long loanType) {
		this.loanType = loanType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getContentsTextNumber() {
		return contentsTextNumber;
	}

	public void setContentsTextNumber(String contentsTextNumber) {
		this.contentsTextNumber = contentsTextNumber == null ? null
				: contentsTextNumber.trim();
	}

	public Long getCreatedPeople() {
		return createdPeople;
	}

	public void setCreatedPeople(Long createdPeople) {
		this.createdPeople = createdPeople;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getModifyPeople() {
		return modifyPeople;
	}

	public void setModifyPeople(Long modifyPeople) {
		this.modifyPeople = modifyPeople;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}
}