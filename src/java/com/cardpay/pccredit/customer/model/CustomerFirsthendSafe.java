package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 贺珈
 * 原始客户信息--入保信息（太原）
 *
 */
@ModelParam(table = "ty_customer_work",generator=IDType.assigned)
public class CustomerFirsthendSafe extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String khnm;//客户内码,个人第一位A,法人第一位为B
	private String bxlx;//保险类型
	private String bxmc;//保险名称
	private String cbgs;//承保公司
	private String bxnr;//保险内容
	private String bz;//备注


	public String getKhnm() {
		return khnm;
	}
	public void setKhnm(String khnm) {
		this.khnm = khnm;
	}

	public String getBxlx() {
		return bxlx;
	}
	public void setBxlx(String bxlx) {
		this.bxlx = bxlx;
	}
	public String getBxmc() {
		return bxmc;
	}
	public void setBxmc(String bxmc) {
		this.bxmc = bxmc;
	}
	public String getCbgs() {
		return cbgs;
	}
	public void setCbgs(String cbgs) {
		this.cbgs = cbgs;
	}
	public String getBxnr() {
		return bxnr;
	}
	public void setBxnr(String bxnr) {
		this.bxnr = bxnr;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
}
