package com.cardpay.pccredit.report.filter;

import java.util.Date;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-11-27下午4:29:53
 */
public class StatisticalFilter extends BaseQueryFilter {
	// 基准日期
	private Date basicDate;
	// 报表日期
	private Date reportDate;
	// 产品Id
	private String productId;
	
	public Date getBasicDate() {
		return basicDate;
	}
	
	public void setBasicDate(Date basicDate) {
		this.basicDate = basicDate;
	}
	
	public Date getReportDate() {
		return reportDate;
	}
	
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
