package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 贺珈
 * 原始客户信息--家庭财产（太原）
 *
 */
@ModelParam(table = "ty_customer_family_cc",generator=IDType.assigned)
public class CustomerFirsthendFamilyCc extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String khnm;//客户内码,个人第一位A,法人第一位为B
	private String tzh;//权证/特征号
	private String cczl;//财产种类
	private String ccmc;//财产名称
	private String sl;//数量
	private String dw;//单位
	private String gzrq;//购置日期
	private String zcyz;//资产原值
	private String pgjz;//评估价值
	private String pgrq;//评估日期
	private String zl;//质量
	public String getKhnm() {
		return khnm;
	}
	public void setKhnm(String khnm) {
		this.khnm = khnm;
	}
	public String getTzh() {
		return tzh;
	}
	public void setTzh(String tzh) {
		this.tzh = tzh;
	}
	public String getCczl() {
		return cczl;
	}
	public void setCczl(String cczl) {
		this.cczl = cczl;
	}
	public String getCcmc() {
		return ccmc;
	}
	public void setCcmc(String ccmc) {
		this.ccmc = ccmc;
	}
	public String getSl() {
		return sl;
	}
	public void setSl(String sl) {
		this.sl = sl;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getGzrq() {
		return gzrq;
	}
	public void setGzrq(String gzrq) {
		this.gzrq = gzrq;
	}
	public String getZcyz() {
		return zcyz;
	}
	public void setZcyz(String zcyz) {
		this.zcyz = zcyz;
	}
	public String getPgjz() {
		return pgjz;
	}
	public void setPgjz(String pgjz) {
		this.pgjz = pgjz;
	}
	public String getPgrq() {
		return pgrq;
	}
	public void setPgrq(String pgrq) {
		this.pgrq = pgrq;
	}
	public String getZl() {
		return zl;
	}
	public void setZl(String zl) {
		this.zl = zl;
	}
	
}
