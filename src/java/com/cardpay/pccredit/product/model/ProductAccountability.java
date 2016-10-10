package com.cardpay.pccredit.product.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * ProductAccountability类的描述
 * 
 * @author 王海东
 * @created on 2014-11-18
 * 
 * @version $Id:$
 */
@ModelParam(table = "product_accountability")
public class ProductAccountability extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String aging;
	private String overdueDay;
	private String overdueAmount;
	private String overdueTotalTime;
	private String overdueTotalAmount;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAging() {
		return aging;
	}

	public void setAging(String aging) {
		this.aging = aging;
	}

	public String getOverdueDay() {
		return overdueDay;
	}

	public void setOverdueDay(String overdueDay) {
		this.overdueDay = overdueDay;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

	public String getOverdueTotalTime() {
		return overdueTotalTime;
	}

	public void setOverdueTotalTime(String overdueTotalTime) {
		this.overdueTotalTime = overdueTotalTime;
	}

	public String getOverdueTotalAmount() {
		return overdueTotalAmount;
	}

	public void setOverdueTotalAmount(String overdueTotalAmount) {
		this.overdueTotalAmount = overdueTotalAmount;
	}

}
