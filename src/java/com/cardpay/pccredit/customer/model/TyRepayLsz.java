package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author sonchen
 * 流水表（太原）
 *
 */
@ModelParam(table = "TY_REPAY_LSZ",generator=IDType.assigned)
public class TyRepayLsz extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String  ywbh;       //流水账ID  
	private String  kmh;        //科目号    
	private String  khjl;       //客户经理  
	private String  jjh;        //借据号    
	private String  ywjg;       //业务机构  
	private String  ywjgh;      //业务机构号
	private String  dkzh;       //贷款账号  
	private String  fzh;       //分账号    
	private String  lsh;        //流水号     
	private String  ywdm;       //业务代码  
	private String  jzrq;       //记账日期  
	private String  ywrq;       //业务日期  
	private String  zy;         //摘要          
	private String  pzh;        //凭证号        
	private String  jf;         //借方金额  
	private String  df;         //贷方金额   
	private String  ye;         //结欠余额   
	private String  qxrx;       //起息日期  
	private String  zxrq;       //止息日期  
	private String  sfmz;       //是否抹账  
	private String  mzyh;       //抹账员号  
	private String  mzsj;       //抹账时间  
	private String  sfcl;       //是否处理  
	private String  createTime;//更新时间  
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getKmh() {
		return kmh;
	}
	public void setKmh(String kmh) {
		this.kmh = kmh;
	}
	public String getKhjl() {
		return khjl;
	}
	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}
	public String getJjh() {
		return jjh;
	}
	public void setJjh(String jjh) {
		this.jjh = jjh;
	}
	public String getYwjg() {
		return ywjg;
	}
	public void setYwjg(String ywjg) {
		this.ywjg = ywjg;
	}
	public String getYwjgh() {
		return ywjgh;
	}
	public void setYwjgh(String ywjgh) {
		this.ywjgh = ywjgh;
	}
	public String getDkzh() {
		return dkzh;
	}
	public void setDkzh(String dkzh) {
		this.dkzh = dkzh;
	}
	public String getFzh() {
		return fzh;
	}
	public void setFzh(String fzh) {
		this.fzh = fzh;
	}
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	public String getYwdm() {
		return ywdm;
	}
	public void setYwdm(String ywdm) {
		this.ywdm = ywdm;
	}
	public String getJzrq() {
		return jzrq;
	}
	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}
	public String getYwrq() {
		return ywrq;
	}
	public void setYwrq(String ywrq) {
		this.ywrq = ywrq;
	}
	public String getZy() {
		return zy;
	}
	public void setZy(String zy) {
		this.zy = zy;
	}
	public String getPzh() {
		return pzh;
	}
	public void setPzh(String pzh) {
		this.pzh = pzh;
	}
	public String getJf() {
		return jf;
	}
	public void setJf(String jf) {
		this.jf = jf;
	}
	public String getDf() {
		return df;
	}
	public void setDf(String df) {
		this.df = df;
	}
	public String getYe() {
		return ye;
	}
	public void setYe(String ye) {
		this.ye = ye;
	}
	public String getQxrx() {
		return qxrx;
	}
	public void setQxrx(String qxrx) {
		this.qxrx = qxrx;
	}
	public String getZxrq() {
		return zxrq;
	}
	public void setZxrq(String zxrq) {
		this.zxrq = zxrq;
	}
	public String getSfmz() {
		return sfmz;
	}
	public void setSfmz(String sfmz) {
		this.sfmz = sfmz;
	}
	public String getMzyh() {
		return mzyh;
	}
	public void setMzyh(String mzyh) {
		this.mzyh = mzyh;
	}
	public String getMzsj() {
		return mzsj;
	}
	public void setMzsj(String mzsj) {
		this.mzsj = mzsj;
	}
	public String getSfcl() {
		return sfcl;
	}
	public void setSfcl(String sfcl) {
		this.sfcl = sfcl;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
