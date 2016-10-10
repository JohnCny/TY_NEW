/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：客户经理绩效每月绩效参数表详细 jn
 * 主要包含客户日均贷款余额和产品利率等
 * @author songchen
 *
 * 2014-11-20 下午5:31:52
 */
@ModelParam(table="T_JX_SPECIFIC_PARAMETERS",generator=IDType.uuid32)
public class TJxSpecificParameters extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String  year;                          
	private String  month;                         
	private String  monthDayAverageCustLoanamt;
	private String  prodLimit;                     
	private String  prodType;                    
	private String  customerId;                   
	private String  customerManagerId;
	
	private String instcode;
	
	
	
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonthDayAverageCustLoanamt() {
		return monthDayAverageCustLoanamt;
	}
	public void setMonthDayAverageCustLoanamt(String monthDayAverageCustLoanamt) {
		this.monthDayAverageCustLoanamt = monthDayAverageCustLoanamt;
	}
	public String getProdLimit() {
		return prodLimit;
	}
	public void setProdLimit(String prodLimit) {
		this.prodLimit = prodLimit;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
	
	
	
}
