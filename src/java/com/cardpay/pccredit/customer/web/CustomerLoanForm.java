/**
 * 
 */
package com.cardpay.pccredit.customer.web;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午2:58:41
 */
public class CustomerLoanForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;//客户id
	private String chineseName;//客户名称
	private String cardId;//证件号码
	private String userId;//客户经理id
	private String userName;//客户经理名称
	private String groupName;//主管名称
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
