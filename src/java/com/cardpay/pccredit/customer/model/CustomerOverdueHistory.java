package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author chenzhifang
 *
 * 2014-11-3下午3:55:08
 */
@ModelParam(table = "customer_overdue_history")
public class CustomerOverdueHistory extends BusinessModel {
	private static final long serialVersionUID = 1L;

	private String customerId;
	
	private String productId;

	private String numberDaysOverdue;
	
	private String overdueAmount;
	
	private String lateDate;
	
	

	public String getNumberDaysOverdue() {
		return numberDaysOverdue;
	}

	public void setNumberDaysOverdue(String numberDaysOverdue) {
		this.numberDaysOverdue = numberDaysOverdue;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getLateDate() {
		return lateDate;
	}

	public void setLateDate(String lateDate) {
		this.lateDate = lateDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	
	
}
