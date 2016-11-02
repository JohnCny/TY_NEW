/**
 * 
 */
package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.BusinessModel;

/**
 * 描述 ：卡片信息描述
 * 
 * @author 张石树
 * 
 *         2014-11-3 上午9:39:27
 */
public class CardInfomation extends BaseModel {
	
	private String customerId;
	
	private String productId;
	
	private String cardNumber;
	
	private String cardActivateDate;
	
	private String cardStatusCode;
	
	private String cardStatusDate;
	
	private String cardLogoutCode;
	
	private String cashSuspendDate;
	
	private String cardAffiliated;
	
	private String idNumber;
	
	private String expireDate;
	
	private String againExpireDate;
	
	private String cardCost;
	
	private String nextCostMonth;
	
	private String cardMakeDate;
	
	private String cardMakeCode;
	
	private String cardAffiliatedScale;
	
	private String cardLossDate;
	
	private String cashSuspendMode;

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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardActivateDate() {
		return cardActivateDate;
	}

	public void setCardActivateDate(String cardActivateDate) {
		this.cardActivateDate = cardActivateDate;
	}

	public String getCardStatusCode() {
		return cardStatusCode;
	}

	public void setCardStatusCode(String cardStatusCode) {
		this.cardStatusCode = cardStatusCode;
	}

	public String getCardStatusDate() {
		return cardStatusDate;
	}

	public void setCardStatusDate(String cardStatusDate) {
		this.cardStatusDate = cardStatusDate;
	}

	public String getCardLogoutCode() {
		return cardLogoutCode;
	}

	public void setCardLogoutCode(String cardLogoutCode) {
		this.cardLogoutCode = cardLogoutCode;
	}

	public String getCashSuspendDate() {
		return cashSuspendDate;
	}

	public void setCashSuspendDate(String cashSuspendDate) {
		this.cashSuspendDate = cashSuspendDate;
	}

	public String getCardAffiliated() {
		return cardAffiliated;
	}

	public void setCardAffiliated(String cardAffiliated) {
		this.cardAffiliated = cardAffiliated;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getAgainExpireDate() {
		return againExpireDate;
	}

	public void setAgainExpireDate(String againExpireDate) {
		this.againExpireDate = againExpireDate;
	}

	public String getCardCost() {
		return cardCost;
	}

	public void setCardCost(String cardCost) {
		this.cardCost = cardCost;
	}

	public String getNextCostMonth() {
		return nextCostMonth;
	}

	public void setNextCostMonth(String nextCostMonth) {
		this.nextCostMonth = nextCostMonth;
	}

	public String getCardMakeDate() {
		return cardMakeDate;
	}

	public void setCardMakeDate(String cardMakeDate) {
		this.cardMakeDate = cardMakeDate;
	}

	public String getCardMakeCode() {
		return cardMakeCode;
	}

	public void setCardMakeCode(String cardMakeCode) {
		this.cardMakeCode = cardMakeCode;
	}

	public String getCardAffiliatedScale() {
		return cardAffiliatedScale;
	}

	public void setCardAffiliatedScale(String cardAffiliatedScale) {
		this.cardAffiliatedScale = cardAffiliatedScale;
	}

	public String getCardLossDate() {
		return cardLossDate;
	}

	public void setCardLossDate(String cardLossDate) {
		this.cardLossDate = cardLossDate;
	}

	public String getCashSuspendMode() {
		return cashSuspendMode;
	}

	public void setCashSuspendMode(String cashSuspendMode) {
		this.cashSuspendMode = cashSuspendMode;
	}

}
