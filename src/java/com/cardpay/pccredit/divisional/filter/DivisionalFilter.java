package com.cardpay.pccredit.divisional.filter;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;
/**
 * 
 * @author shaoming
 *
 */
public class DivisionalFilter extends BaseQueryFilter{
	private String id;
	private CustomerInfor customerinfor;
	private String customerManagerId;
	private String divisionalResult;
	private String divisionalTime;
	private String originalOrganizationOld;
	private String originalManagerOld;
	private String currentOrganizationId;
	private String divisionalProgress;
	private Integer divisionalType;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public CustomerInfor getCustomerinfor() {
		return customerinfor;
	}
	public void setCustomerinfor(CustomerInfor customerinfor) {
		this.customerinfor = customerinfor;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	public String getDivisionalResult() {
		return divisionalResult;
	}
	public void setDivisionalResult(String divisionalResult) {
		this.divisionalResult = divisionalResult;
	}
	public String getDivisionalTime() {
		return divisionalTime;
	}
	public void setDivisionalTime(String divisionalTime) {
		this.divisionalTime = divisionalTime;
	}
	public String getOriginalOrganizationOld() {
		return originalOrganizationOld;
	}
	public void setOriginalOrganizationOld(String originalOrganizationOld) {
		this.originalOrganizationOld = originalOrganizationOld;
	}
	public String getOriginalManagerOld() {
		return originalManagerOld;
	}
	public void setOriginalManagerOld(String originalManagerOld) {
		this.originalManagerOld = originalManagerOld;
	}
	public String getCurrentOrganizationId() {
		return currentOrganizationId;
	}
	public void setCurrentOrganizationId(String currentOrganizationId) {
		this.currentOrganizationId = currentOrganizationId;
	}
	
	public String getDivisionalProgress() {
		return divisionalProgress;
	}
	public void setDivisionalProgress(String divisionalProgress) {
		this.divisionalProgress = divisionalProgress;
	}
	public Integer getDivisionalType() {
		return divisionalType;
	}
	public void setDivisionalType(Integer divisionalType) {
		this.divisionalType = divisionalType;
	}
	
	
}
