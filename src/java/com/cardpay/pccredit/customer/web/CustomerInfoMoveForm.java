package com.cardpay.pccredit.customer.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 
 * @author shaoming
 *
 * 2014年10月31日   上午10:15:02
 */
public class CustomerInfoMoveForm extends BaseForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;//客户id
    private String customerName;//客户名称
    private String cardType;//证件类型
    private String cardId;//证件号码
    private String receiveId;//申请表id
    private String approveId;//申请人id
    private String approveGh;//申请人工号
    private String approveName;//申请人名称
    private String moveId;//转交人id
    private String moveGh;//转交人工号
    private String moveName;//转交人名称
    private String status;//状态
   
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

}
