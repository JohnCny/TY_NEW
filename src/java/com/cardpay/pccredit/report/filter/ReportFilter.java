package com.cardpay.pccredit.report.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class ReportFilter extends BaseQueryFilter {
	private String certiCode;//客户证件号码
	private String customerName;//客户 暂不用
	private String organName;//所属机构
	private String custManagerName;
	private String status;
	public String getCertiCode() {
		return certiCode;
	}
	public void setCertiCode(String certiCode) {
		this.certiCode = certiCode;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getCustManagerName() {
		return custManagerName;
	}
	public void setCustManagerName(String custManagerName) {
		this.custManagerName = custManagerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	
}
