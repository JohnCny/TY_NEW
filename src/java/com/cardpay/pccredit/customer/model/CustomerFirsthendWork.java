package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 贺珈
 * 原始客户信息--工作情况（太原）
 *
 */
@ModelParam(table = "ty_customer_work",generator=IDType.assigned)
public class CustomerFirsthendWork extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String khnm;//客户内码,个人第一位A,法人第一位为B
	private String ksrq;//开始日期
	private String jsrq;//结束日期
	private String dwmc;//单位名称
	private String dwxz;//单位性质
	private String dwdh;//单位电话
	private String dwyzbm;//单位邮政编码
	private String szbm;//所在部门
	private String zc;//职称
	private String hyxz;//行业性质
	private String nsr;//年收入
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

	public String getDwxz() {
		return dwxz;
	}
	public void setDwxz(String dwxz) {
		this.dwxz = dwxz;
	}
	public String getDwdh() {
		return dwdh;
	}
	public void setDwdh(String dwdh) {
		this.dwdh = dwdh;
	}
	public String getDwyzbm() {
		return dwyzbm;
	}
	public void setDwyzbm(String dwyzbm) {
		this.dwyzbm = dwyzbm;
	}
	public String getSzbm() {
		return szbm;
	}
	public void setSzbm(String szbm) {
		this.szbm = szbm;
	}
	public String getZc() {
		return zc;
	}
	public void setZc(String zc) {
		this.zc = zc;
	}
	public String getHyxz() {
		return hyxz;
	}
	public void setHyxz(String hyxz) {
		this.hyxz = hyxz;
	}
	public String getNsr() {
		return nsr;
	}
	public void setNsr(String nsr) {
		this.nsr = nsr;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	
}
