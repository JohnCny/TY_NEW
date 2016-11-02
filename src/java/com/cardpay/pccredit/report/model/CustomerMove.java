package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 客户移交历史表
 * @author songchen
 */
@ModelParam(table = "ty_customer_move")
public class CustomerMove extends BusinessModel {

	private static final long serialVersionUID = -8860955438817002631L;

	 private String customerId;  
	 private String approveId;   
	 private String moveId;      
	 private String status;       
	 private String approveGh;   
	 private String approveName; 
	 private String moveGh;      
	 private String moveName;
	 
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getApproveId() {
		return approveId;
	}
	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	public String getMoveId() {
		return moveId;
	}
	public void setMoveId(String moveId) {
		this.moveId = moveId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApproveGh() {
		return approveGh;
	}
	public void setApproveGh(String approveGh) {
		this.approveGh = approveGh;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public String getMoveGh() {
		return moveGh;
	}
	public void setMoveGh(String moveGh) {
		this.moveGh = moveGh;
	}
	public String getMoveName() {
		return moveName;
	}
	public void setMoveName(String moveName) {
		this.moveName = moveName;
	}
	
	
}