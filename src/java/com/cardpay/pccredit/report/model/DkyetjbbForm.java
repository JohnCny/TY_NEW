package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 贷款余额统计
 * @author songchen
 */
public class DkyetjbbForm extends BaseForm {

	private static final long serialVersionUID = -8860955438817002631L;

	   private String  cname;        //客户名称
	   private String  cardnum;      //客户证件号码
	   private String  prodName;     //所属产品
	   private String  money;        //贷款金额
	   private String  interest;     //利率
	   private String  loandate;     //放款日期
	   private String  balamt;       //余额
	   private String  bj;  		 //已偿还本金
	   private String  lx;    		 //已偿还利息
	   private String  state;        //贷款状态
	   private String  busimanager;  //所属客户经理
	   private String  instcode;	 //所属机构
	   
	   
	   private String accountstate;
	   
	
	   public String getAccountstate() {
		return accountstate;
	}


	public void setAccountstate(String accountstate) {
		this.accountstate = accountstate;
	}


	private String rowIndex;//序号


	public String getCname() {
		return cname;
	}


	public void setCname(String cname) {
		this.cname = cname;
	}


	public String getCardnum() {
		return cardnum;
	}


	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}


	public String getProdName() {
		return prodName;
	}


	public void setProdName(String prodName) {
		this.prodName = prodName;
	}


	public String getMoney() {
		return money;
	}


	public void setMoney(String money) {
		this.money = money;
	}


	public String getInterest() {
		return interest;
	}


	public void setInterest(String interest) {
		this.interest = interest;
	}


	public String getLoandate() {
		return loandate;
	}


	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}


	public String getBalamt() {
		return balamt;
	}


	public void setBalamt(String balamt) {
		this.balamt = balamt;
	}


	public String getBj() {
		return bj;
	}


	public void setBj(String bj) {
		this.bj = bj;
	}


	public String getLx() {
		return lx;
	}


	public void setLx(String lx) {
		this.lx = lx;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getBusimanager() {
		return busimanager;
	}


	public void setBusimanager(String busimanager) {
		this.busimanager = busimanager;
	}


	public String getInstcode() {
		return instcode;
	}


	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}


	public String getRowIndex() {
		return rowIndex;
	}


	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
       

	
}