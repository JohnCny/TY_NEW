package com.cardpay.pccredit.report.filter;

import java.util.Date;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;
/**
 * 
 * @author 季东晓
 *
 * 2014-12-22 上午11:16:42
 */
public class WorkQualityFilter extends BaseQueryFilter {
	// 开始时间
	private Date startDate;
	// 结束时间
	private Date endDate;
	// 产品Id
	private String productId;
	
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
