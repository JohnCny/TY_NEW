package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 预期还款贷款统计
 * @author songchen
 */
public class YqhkdktjbbForm extends BaseForm {

	private static final long serialVersionUID = -8860955438817002631L;

	   private String  cname;           //客户名称
	   private String  cardnum;         //客户证件号码
	   private String  prodName;     	//所属产品
	   private String  money;           //贷款金额
	   private String  hkr;  			//距离还款日(日)
	   private String  hkbj;			//应还本金
	   private String  yhlx;			//应还利息
	   private String  busimanager;     //所属客户经理
	   private String  instcode;        //所属机构
	   
	   private String rowIndex;
	   
	public String getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
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
	public String getHkr() {
		return hkr;
	}
	public void setHkr(String hkr) {
		this.hkr = hkr;
	}
	public String getHkbj() {
		return hkbj;
	}
	public void setHkbj(String hkbj) {
		this.hkbj = hkbj;
	}
	public String getYhlx() {
		return yhlx;
	}
	public void setYhlx(String yhlx) {
		this.yhlx = yhlx;
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
	   
	   
	   
}