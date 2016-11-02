package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 贺珈
 * 原始客户信息--学习（太原）
 *
 */
@ModelParam(table = "ty_customer_study",generator=IDType.assigned)
public class CustomerFirsthendStudy extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String khnm;//客户内码,个人第一位A,法人第一位为B
	private String ksrq;//开始日期
	private String jsrq;//结束日期
	private String szxx;//所在学校
	private String szyx;//所在院系
	private String zy;//专业
	private String xw;//学位
	private String xl;//学历
	private String xz;//学制
	private String xlzsh;//学历证书号
	private String xwzsh;//学位证书号
	private String bz;//备注


	public String getKhnm() {
		return khnm;
	}
	public void setKhnm(String khnm) {
		this.khnm = khnm;
	}
	public String getKsrq() {
		return ksrq;
	}
	public void setKsrq(String ksrq) {
		this.ksrq = ksrq;
	}
	public String getJsrq() {
		return jsrq;
	}
	public void setJsrq(String jsrq) {
		this.jsrq = jsrq;
	}
	public String getSzxx() {
		return szxx;
	}
	public void setSzxx(String szxx) {
		this.szxx = szxx;
	}
	public String getSzyx() {
		return szyx;
	}
	public void setSzyx(String szyx) {
		this.szyx = szyx;
	}
	public String getZy() {
		return zy;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	public String getXw() {
		return xw;
	}
	public void setXw(String xw) {
		this.xw = xw;
	}
	public String getXl() {
		return xl;
	}
	public void setXl(String xl) {
		this.xl = xl;
	}
	public String getXz() {
		return xz;
	}
	public void setXz(String xz) {
		this.xz = xz;
	}
	public String getXlzsh() {
		return xlzsh;
	}
	public void setXlzsh(String xlzsh) {
		this.xlzsh = xlzsh;
	}
	public String getXwzsh() {
		return xwzsh;
	}
	public void setXwzsh(String xwzsh) {
		this.xwzsh = xwzsh;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	
}
