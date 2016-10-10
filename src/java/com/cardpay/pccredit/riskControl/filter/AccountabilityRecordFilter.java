package com.cardpay.pccredit.riskControl.filter;

import com.wicresoft.jrad.base.database.dao.query.Operator;
import com.wicresoft.jrad.base.database.dao.query.QueryParam;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class AccountabilityRecordFilter extends BaseQueryFilter {
	
	@QueryParam(operator = Operator.contains, column = "chinese_name")
	private String chineseName;
	
	private String cardType;//证件类型
	
	private String cardId; //证件号码
	
	private String customerManagerId;//经理Id
	
    private String auditStatus;//审核状态 未审核 卡中心审核 审核结束
	
	private String auditStage;//审核阶段 卡中心 cfcc 客户经理 manager

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

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	
	

}
