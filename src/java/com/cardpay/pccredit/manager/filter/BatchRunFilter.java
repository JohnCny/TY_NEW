/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import java.util.Date;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 */
public class BatchRunFilter extends BaseQueryFilter{
	
	private String status;
	private Date startDate;
	private Date endDate;
	private Date InputTime0;
	private Date InputTime01;
	
	
	public Date getInputTime0() {
		return InputTime0;
	}

	public void setInputTime0(Date inputTime0) {
		InputTime0 = inputTime0;
	}

	public Date getInputTime01() {
		return InputTime01;
	}

	public void setInputTime01(Date inputTime01) {
		InputTime01 = inputTime01;
	}

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
