package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author chenzhifang
 *
 * 2014-12-24下午3:51:30
 */
@ModelParam(table = "RH_YQ_INFO")
public class YQ_Info extends AbstractCustomerInfo {
	private static final long serialVersionUID = 1L;
	private String loanOverdueNum;//贷款逾期笔数
	private String loanOverdueMonth;//贷款逾期月份数
	private String loanOverdueAmountTotal;//贷款逾期单月最高逾期总额
	private String loanOverdueLongestMonth;//贷款逾期最长逾期月数
	private String creditCardNum;//贷记卡逾期账户数
	private String creditCardMonth;//贷记卡逾期月份数
	private String creditCardAmountTotal;//贷记卡逾期单月最高逾期总额
	private String creditCardLongestMonth;//贷记卡逾期最长逾期月数
	private String quasiCreditCardNum;//准贷记卡60天以上透支账户数
	private String quasiCreditCardMonth;//准贷记卡60天以上透支月份数
	private String quasiCreditCardAmountTotal;//准贷记卡60天以上透支单月最高透支余额
	private String quasiCreditCardLongestMon;//准贷记卡60天以上透支最长透支月数
	
	public String getLoanOverdueNum() {
		return loanOverdueNum;
	}
	public void setLoanOverdueNum(String loanOverdueNum) {
		this.loanOverdueNum = loanOverdueNum;
	}
	public String getLoanOverdueMonth() {
		return loanOverdueMonth;
	}
	public void setLoanOverdueMonth(String loanOverdueMonth) {
		this.loanOverdueMonth = loanOverdueMonth;
	}
	public String getLoanOverdueAmountTotal() {
		return loanOverdueAmountTotal;
	}
	public void setLoanOverdueAmountTotal(String loanOverdueAmountTotal) {
		this.loanOverdueAmountTotal = loanOverdueAmountTotal;
	}
	public String getLoanOverdueLongestMonth() {
		return loanOverdueLongestMonth;
	}
	public void setLoanOverdueLongestMonth(String loanOverdueLongestMonth) {
		this.loanOverdueLongestMonth = loanOverdueLongestMonth;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getCreditCardMonth() {
		return creditCardMonth;
	}
	public void setCreditCardMonth(String creditCardMonth) {
		this.creditCardMonth = creditCardMonth;
	}
	public String getCreditCardAmountTotal() {
		return creditCardAmountTotal;
	}
	public void setCreditCardAmountTotal(String creditCardAmountTotal) {
		this.creditCardAmountTotal = creditCardAmountTotal;
	}
	public String getCreditCardLongestMonth() {
		return creditCardLongestMonth;
	}
	public void setCreditCardLongestMonth(String creditCardLongestMonth) {
		this.creditCardLongestMonth = creditCardLongestMonth;
	}
	public String getQuasiCreditCardNum() {
		return quasiCreditCardNum;
	}
	public void setQuasiCreditCardNum(String quasiCreditCardNum) {
		this.quasiCreditCardNum = quasiCreditCardNum;
	}
	public String getQuasiCreditCardMonth() {
		return quasiCreditCardMonth;
	}
	public void setQuasiCreditCardMonth(String quasiCreditCardMonth) {
		this.quasiCreditCardMonth = quasiCreditCardMonth;
	}
	public String getQuasiCreditCardAmountTotal() {
		return quasiCreditCardAmountTotal;
	}
	public void setQuasiCreditCardAmountTotal(String quasiCreditCardAmountTotal) {
		this.quasiCreditCardAmountTotal = quasiCreditCardAmountTotal;
	}
	public String getQuasiCreditCardLongestMon() {
		return quasiCreditCardLongestMon;
	}
	public void setQuasiCreditCardLongestMon(String quasiCreditCardLongestMon) {
		this.quasiCreditCardLongestMon = quasiCreditCardLongestMon;
	}
}
