package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author chenzhifang
 *
 * 2014-12-24下午3:51:30
 */
@ModelParam(table = "RH_XYTS_INFO")
public class XYTS_Info extends AbstractCustomerInfo {
	private static final long serialVersionUID = 1L;
	private String housingLoanNum;//住房贷款笔数
	private String otherLoanNum;//其他贷款笔数
	private String firstLoanDate;//首笔贷款发放月份
	private String creditCardNum;//贷记卡账户数
	private String firstCreditCardDate;//首张贷记卡发卡月份
	private String quasiCreditCardNum;//准贷记卡账户数
	private String firstQuasiCreditCardDate;//首张准贷记卡发卡月份
	private String declareNum;//本人声明数目
	private String markNum;//异议标注数目
	
	public String getHousingLoanNum() {
		return housingLoanNum;
	}
	public void setHousingLoanNum(String housingLoanNum) {
		this.housingLoanNum = housingLoanNum;
	}
	public String getOtherLoanNum() {
		return otherLoanNum;
	}
	public void setOtherLoanNum(String otherLoanNum) {
		this.otherLoanNum = otherLoanNum;
	}
	public String getFirstLoanDate() {
		return firstLoanDate;
	}
	public void setFirstLoanDate(String firstLoanDate) {
		this.firstLoanDate = firstLoanDate;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getFirstCreditCardDate() {
		return firstCreditCardDate;
	}
	public void setFirstCreditCardDate(String firstCreditCardDate) {
		this.firstCreditCardDate = firstCreditCardDate;
	}
	public String getQuasiCreditCardNum() {
		return quasiCreditCardNum;
	}
	public void setQuasiCreditCardNum(String quasiCreditCardNum) {
		this.quasiCreditCardNum = quasiCreditCardNum;
	}
	public String getFirstQuasiCreditCardDate() {
		return firstQuasiCreditCardDate;
	}
	public void setFirstQuasiCreditCardDate(String firstQuasiCreditCardDate) {
		this.firstQuasiCreditCardDate = firstQuasiCreditCardDate;
	}
	public String getDeclareNum() {
		return declareNum;
	}
	public void setDeclareNum(String declareNum) {
		this.declareNum = declareNum;
	}
	public String getMarkNum() {
		return markNum;
	}
	public void setMarkNum(String markNum) {
		this.markNum = markNum;
	}
}
