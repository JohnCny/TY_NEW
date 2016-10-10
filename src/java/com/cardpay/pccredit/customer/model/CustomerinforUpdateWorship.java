package com.cardpay.pccredit.customer.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 季东晓
 *
 * 2014-10-29 下午1:58:29
 */
@ModelParam(table = "customer_worship_information",generator=IDType.uuid32)
public class CustomerinforUpdateWorship extends BusinessModel{
	
	private static final long serialVersionUID = 1L;
	
	private String customerId;
	
	private Date marketingTime; // 营销时间
	
	private String engagedIndustry; //从事行业
	
	private String contactPhoneNumber;//联系电话
	
	private Integer demandCredit;//是否有信贷需求
	
	private String amountNumberent;//意向金额
	
	private String numberentionDeadline;//意向期限
	
	private String numbererestRepaymentWay;//意向还款方式
	
	private String area;//区域
	
	private String address;//地址
	
	private Integer suggestedMarketingAgain;//是否建议再营销
	
	private Date suggestMarketingTimeAgain;//建议再营销时间
	
	private Integer potentialCustomer;//是否潜在客户

	
	public Date getMarketingTime() {
		return marketingTime;
	}

	public void setMarketingTime(Date marketingTime) {
		this.marketingTime = marketingTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public Integer getDemandCredit() {
		return demandCredit;
	}

	public void setDemandCredit(Integer demandCredit) {
		this.demandCredit = demandCredit;
	}

	public Integer getSuggestedMarketingAgain() {
		return suggestedMarketingAgain;
	}

	public void setSuggestedMarketingAgain(Integer suggestedMarketingAgain) {
		this.suggestedMarketingAgain = suggestedMarketingAgain;
	}

	public Date getSuggestMarketingTimeAgain() {
		return suggestMarketingTimeAgain;
	}

	public void setSuggestMarketingTimeAgain(Date suggestMarketingTimeAgain) {
		this.suggestMarketingTimeAgain = suggestMarketingTimeAgain;
	}

	public Integer getPotentialCustomer() {
		return potentialCustomer;
	}

	public void setPotentialCustomer(Integer potentialCustomer) {
		this.potentialCustomer = potentialCustomer;
	}

	public String getEngagedIndustry() {
		return engagedIndustry;
	}

	public void setEngagedIndustry(String engagedIndustry) {
		this.engagedIndustry = engagedIndustry;
	}



	public String getAmountNumberent() {
		return amountNumberent;
	}

	public void setAmountNumberent(String amountNumberent) {
		this.amountNumberent = amountNumberent;
	}

	public String getNumberentionDeadline() {
		return numberentionDeadline;
	}

	public void setNumberentionDeadline(String numberentionDeadline) {
		this.numberentionDeadline = numberentionDeadline;
	}

	public String getNumbererestRepaymentWay() {
		return numbererestRepaymentWay;
	}

	public void setNumbererestRepaymentWay(String numbererestRepaymentWay) {
		this.numbererestRepaymentWay = numbererestRepaymentWay;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
