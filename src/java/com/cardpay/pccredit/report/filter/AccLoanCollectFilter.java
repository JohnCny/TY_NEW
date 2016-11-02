package com.cardpay.pccredit.report.filter;

import java.util.Date;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chinh
 *
 * 2015-8-1下午4:29:53
 */
public class AccLoanCollectFilter extends BaseQueryFilter{
	// 起始日期
	private String startDate;
	// 截止日期
	private String endDate;
	//产品类型
	private String productId;
	
	private String userId;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
}
