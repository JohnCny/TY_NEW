/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：客户经理绩效每月绩效参数表 jn
 * @author songchen
 *
 * 2014-11-20 下午5:31:52
 */
@ModelParam(table="T_JX_PARAMETERS",generator=IDType.uuid32)
public class TJxParameters extends BusinessModel {

	private static final long serialVersionUID = 1L;
	/**
	 *当月发放贷款户数
	 */
	private String  monthLoanNum;
	/**
	 *当月有效管户数
	 */
	private String  monthEffectNum;
	
	/**
	 *当月逾期贷款笔数
	 */
	private String  monthOverdueLoannum; 
	/**
	 *当月逾期贷款天数
	 */
	private String  monthOverdueDays;  
	/**
	 *年份
	 */
	private String  year;  
	/**
	 *月份
	 */
	private String  month;     
	/**
	 *客户经理ID
	 */
	private String  customerManagerId;
	
	/**
	 * 当月协办次数
	 */
	private String  monthTimes;
	
	/**
	 * 机构
	 */
    private String instcode;
	
	
	
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}
	public String getMonthTimes() {
		return monthTimes;
	}
	public void setMonthTimes(String monthTimes) {
		this.monthTimes = monthTimes;
	}
	
	public String getMonthLoanNum() {
		return monthLoanNum;
	}
	public void setMonthLoanNum(String monthLoanNum) {
		this.monthLoanNum = monthLoanNum;
	}
	public String getMonthEffectNum() {
		return monthEffectNum;
	}
	public void setMonthEffectNum(String monthEffectNum) {
		this.monthEffectNum = monthEffectNum;
	}
	
	
	public String getMonthOverdueLoannum() {
		return monthOverdueLoannum;
	}
	public void setMonthOverdueLoannum(String monthOverdueLoannum) {
		this.monthOverdueLoannum = monthOverdueLoannum;
	}
	public String getMonthOverdueDays() {
		return monthOverdueDays;
	}
	public void setMonthOverdueDays(String monthOverdueDays) {
		this.monthOverdueDays = monthOverdueDays;
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
	public String getCustomerManagerId() {
		return customerManagerId;
	}
	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}
}
