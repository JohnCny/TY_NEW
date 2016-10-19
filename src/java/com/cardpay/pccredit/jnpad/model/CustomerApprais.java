package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

import oracle.sql.DATE;

@ModelParam(table = "CUSTOMER_APPRAIS",generator=IDType.assigned)
public class CustomerApprais extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String chinesename;
	private String cardid;
	private String zfqk;
	private String zcqk;
	private String yyqk;
	private String dwxz;
	private String dwgl;
	private String jzsj;
	private String hyzk;
	private String hjzk;
	private String jycd;
	private String zgzs;
	private String zc;
	private String age;
	private String jkqk;
	private String ggjl;
	private String zw;
	private String grsr;
	private String zwsrb;
	private String syrk;
	private String tjr;
	private String khjlzgyx;
	private String khdysr;
	private String cykh;
	private String zf;
	private String jyed;
	private Date time;
	private String pfdj;
	public String getPfdj() {
		return pfdj;
	}
	public void setPfdj(String pfdj) {
		this.pfdj = pfdj;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChinesename() {
		return chinesename;
	}
	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getZfqk() {
		return zfqk;
	}
	public void setZfqk(String zfqk) {
		this.zfqk = zfqk;
	}
	public String getZcqk() {
		return zcqk;
	}
	public void setZcqk(String zcqk) {
		this.zcqk = zcqk;
	}
	public String getYyqk() {
		return yyqk;
	}
	public void setYyqk(String yyqk) {
		this.yyqk = yyqk;
	}
	public String getDwxz() {
		return dwxz;
	}
	public void setDwxz(String dwxz) {
		this.dwxz = dwxz;
	}
	public String getDwgl() {
		return dwgl;
	}
	public void setDwgl(String dwgl) {
		this.dwgl = dwgl;
	}
	public String getJzsj() {
		return jzsj;
	}
	public void setJzsj(String jzsj) {
		this.jzsj = jzsj;
	}
	public String getHyzk() {
		return hyzk;
	}
	public void setHyzk(String hyzk) {
		this.hyzk = hyzk;
	}
	public String getHjzk() {
		return hjzk;
	}
	public void setHjzk(String hjzk) {
		this.hjzk = hjzk;
	}
	public String getJycd() {
		return jycd;
	}
	public void setJycd(String jycd) {
		this.jycd = jycd;
	}
	public String getZgzs() {
		return zgzs;
	}
	public void setZgzs(String zgzs) {
		this.zgzs = zgzs;
	}
	public String getZc() {
		return zc;
	}
	public void setZc(String zc) {
		this.zc = zc;
	}

	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getJkqk() {
		return jkqk;
	}
	public void setJkqk(String jkqk) {
		this.jkqk = jkqk;
	}
	public String getGgjl() {
		return ggjl;
	}
	public void setGgjl(String ggjl) {
		this.ggjl = ggjl;
	}
	public String getZw() {
		return zw;
	}
	public void setZw(String zw) {
		this.zw = zw;
	}
	public String getGrsr() {
		return grsr;
	}
	public void setGrsr(String grsr) {
		this.grsr = grsr;
	}
	public String getZwsrb() {
		return zwsrb;
	}
	public void setZwsrb(String zwsrb) {
		this.zwsrb = zwsrb;
	}
	public String getSyrk() {
		return syrk;
	}
	public void setSyrk(String syrk) {
		this.syrk = syrk;
	}
	public String getTjr() {
		return tjr;
	}
	public void setTjr(String tjr) {
		this.tjr = tjr;
	}
	public String getKhjlzgyx() {
		return khjlzgyx;
	}
	public void setKhjlzgyx(String khjlzgyx) {
		this.khjlzgyx = khjlzgyx;
	}
	public String getKhdysr() {
		return khdysr;
	}
	public void setKhdysr(String khdysr) {
		this.khdysr = khdysr;
	}
	
	public String getCykh() {
		return cykh;
	}
	public void setCykh(String cykh) {
		this.cykh = cykh;
	}
	public String getZf() {
		return zf;
	}
	public void setZf(String zf) {
		this.zf = zf;
	}
	public String getJyed() {
		return jyed;
	}
	public void setJyed(String jyed) {
		this.jyed = jyed;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}


}
