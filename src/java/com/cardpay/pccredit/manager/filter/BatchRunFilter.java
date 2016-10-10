/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import java.util.Date;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 */
public class BatchRunFilter extends BaseQueryFilter{
	
	private String status;
	private Date startDate;
	private Date endDate;
	
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
