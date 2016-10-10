package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 贺珈
 * 原始客户信息--生产经营（太原）
 *
 */
@ModelParam(table = "ty_customer_manage",generator=IDType.assigned)
public class CustomerFirsthendManage extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String khnm;//客户内码,个人第一位A,法人第一位为B
	private String zyywmc;//主营业务名称
	private String jygm;//经营规模
	private String jyzk;//经营状况
	private String yjzsr;//预计总收入
	private String yjcsr;//预计纯收入

	public String getKhnm() {
		return khnm;
	}
	public void setKhnm(String khnm) {
		this.khnm = khnm;
	}
	public String getZyywmc() {
		return zyywmc;
	}
	public void setZyywmc(String zyywmc) {
		this.zyywmc = zyywmc;
	}
	public String getJygm() {
		return jygm;
	}
	public void setJygm(String jygm) {
		this.jygm = jygm;
	}
	public String getJyzk() {
		return jyzk;
	}
	public void setJyzk(String jyzk) {
		this.jyzk = jyzk;
	}
	public String getYjzsr() {
		return yjzsr;
	}
	public void setYjzsr(String yjzsr) {
		this.yjzsr = yjzsr;
	}
	public String getYjcsr() {
		return yjcsr;
	}
	public void setYjcsr(String yjcsr) {
		this.yjcsr = yjcsr;
	}
	
}
