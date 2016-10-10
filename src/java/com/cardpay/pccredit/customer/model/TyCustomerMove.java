package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author chenzhifang
 *
 * 2014-11-3下午3:46:41
 */
@ModelParam(table = "ty_customer_move")
public class TyCustomerMove extends BusinessModel {
	private static final long serialVersionUID = 1L;

	private String customerId;
	
	private String approveId;
	
	private String approveGh;
	
	private String approveName;
	
	private String moveId;
	
	private String moveGh;
	
	private String moveName;
	
	private String status;
	

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

	public String getMoveId() {
		return moveId;
	}

	public void setMoveId(String moveId) {
		this.moveId = moveId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
