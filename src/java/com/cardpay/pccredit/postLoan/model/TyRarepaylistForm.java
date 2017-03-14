package com.cardpay.pccredit.postLoan.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 还款情况表Form
 * @author zb
 */
public class TyRarepaylistForm extends BusinessModel {
	String ywbh;
	String jkrq;
	String bj;
	String lx;
	String jxfs;
	String jzrq;
	String create_time;
	String khmc;
	String display_name;
	
	public TyRarepaylistForm(){}
	public TyRarepaylistForm(String ywbh, String jkrq, String bj, String lx,
			String jxfs, String jzrq, String create_time, String khmc,
			String display_name) {
		this.ywbh = ywbh;
		this.jkrq = jkrq;
		this.bj = bj;
		this.lx = lx;
		this.jxfs = jxfs;
		this.jzrq = jzrq;
		this.create_time = create_time;
		this.khmc = khmc;
		this.display_name = display_name;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getJkrq() {
		return jkrq;
	}
	public void setJkrq(String jkrq) {
		this.jkrq = jkrq;
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
	public String getJxfs() {
		return jxfs;
	}
	public void setJxfs(String jxfs) {
		this.jxfs = jxfs;
	}
	public String getJzrq() {
		return jzrq;
	}
	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	
	
	
	  
}
