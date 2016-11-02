/**
 * 
 */
package com.cardpay.pccredit.customeradd.model;

import java.util.Date;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午2:58:41
 */
public class MaintenanceForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String customerId;
	private String chineseName;
	private String customerManagerId;
	private String userName;
	private String maintenanceGoal;
	private String maintenanceWay;
	private String maintenanceDay;
	private String createWay;
	private String endResult;
	private String remarksCreateReason;
	private Date maintenanceEndtime;
	private String maintenancePlanId;
	private String maintenanceResult;
	private String maintenanceStartTime;
	private String maintenanceEndTime;
	
	
	private String productName;
	private String cardId;
	private String actualQuote;
	private String debitWay;
	
	private String appId;
	
	private String displayName;
	private String userId;
	
	private String repayWay;
	private String repayStatus;
	private String productId;
	private String dkye;
	
	public String getDkye() {
		return dkye;
	}
	public void setDkye(String dkye) {
		this.dkye = dkye;
	}
	public String getRepayWay() {
		return repayWay;
	}
	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}
	public String getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getActualQuote() {
		return actualQuote;
	}
	public void setActualQuote(String actualQuote) {
		this.actualQuote = actualQuote;
	}
	public String getDebitWay() {
		return debitWay;
	}
	public void setDebitWay(String debitWay) {
		this.debitWay = debitWay;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMaintenancePlanId() {
		return maintenancePlanId;
	}
	public void setMaintenancePlanId(String maintenancePlanId) {
		this.maintenancePlanId = maintenancePlanId;
	}
	public String getMaintenanceResult() {
		return maintenanceResult;
	}
	public void setMaintenanceResult(String maintenanceResult) {
		this.maintenanceResult = maintenanceResult;
	}
	
	public String getMaintenanceStartTime() {
		return maintenanceStartTime;
	}
	public void setMaintenanceStartTime(String maintenanceStartTime) {
		this.maintenanceStartTime = maintenanceStartTime;
	}
	public String getMaintenanceEndTime() {
		return maintenanceEndTime;
	}
	public void setMaintenanceEndTime(String maintenanceEndTime) {
		this.maintenanceEndTime = maintenanceEndTime;
	}
	public Date getMaintenanceEndtime() {
		return maintenanceEndtime;
	}
	public void setMaintenanceEndtime(Date maintenanceEndtime) {
		this.maintenanceEndtime = maintenanceEndtime;
	}
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
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getMaintenanceGoal() {
		return maintenanceGoal;
	}
	public void setMaintenanceGoal(String maintenanceGoal) {
		this.maintenanceGoal = maintenanceGoal;
	}
	public String getMaintenanceWay() {
		return maintenanceWay;
	}
	public void setMaintenanceWay(String maintenanceWay) {
		this.maintenanceWay = maintenanceWay;
	}
	public String getMaintenanceDay() {
		return maintenanceDay;
	}
	public void setMaintenanceDay(String maintenanceDay) {
		this.maintenanceDay = maintenanceDay;
	}
	public String getCreateWay() {
		return createWay;
	}
	public void setCreateWay(String createWay) {
		this.createWay = createWay;
	}
	public String getEndResult() {
		return endResult;
	}
	public void setEndResult(String endResult) {
		this.endResult = endResult;
	}
	public String getRemarksCreateReason() {
		return remarksCreateReason;
	}
	public void setRemarksCreateReason(String remarksCreateReason) {
		this.remarksCreateReason = remarksCreateReason;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
