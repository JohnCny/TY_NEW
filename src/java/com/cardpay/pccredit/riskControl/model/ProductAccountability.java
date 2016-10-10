package com.cardpay.pccredit.riskControl.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-5 下午2:15:25
 */
@ModelParam(table = "product_accountability",generator=IDType.uuid32)
public class ProductAccountability extends BusinessModel {
	
	private static final long serialVersionUID = 1L;
	
	private String productId;//产品ID
	
	private String aging;//账龄
	
	private String overdueAmount;//逾期金额
	
	private String overdueTotalTime;//逾期总次数
	
	private String overdueTotalAmount;//逾期总金额
	
	private String overdueDay;//逾期天数
	
	

	public String getOverdueDay() {
		return overdueDay;
	}

	public void setOverdueDay(String overdueDay) {
		this.overdueDay = overdueDay;
	}

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
