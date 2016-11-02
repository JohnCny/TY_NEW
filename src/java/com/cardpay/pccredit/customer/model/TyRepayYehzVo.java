package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * @author 贺珈
 * 余额汇总表（太原）
 *
 */
@ModelParam(table = "ty_repay_yehz",generator=IDType.assigned)
public class TyRepayYehzVo extends BusinessModel{
	private static final long serialVersionUID = 1L;
	private String jjh;//借据号
	private String ywbh;//业务编号
	private String khh;//客户号
	private String zhtbh;//主合同编号
	private String jgdm;//机构代码
	private String kmh;//科目号
	private String hbzl;//币种
	private String jkje;//借款金额
	private String qxrq;//起息日期
	private String dkye;//贷款余额
	private String bnqx;//表内欠息
	private String bwqx;//表外欠息
	private String wjfl1;//五级正常
	private String wjfl2;//五级关注
	private String wjfl3;//五级次级
	private String wjfl4;//五级可疑
	private String wjfl5;//五级损失
	private String shbj;//收回本金
	private String shlx;//收回利息
	private String lxjs;//利息积数
	private String ksqxrq;//开始欠息日期
	private String yhxbj;//已核销本金
	private String yhxlx;//已核销利息
	private String zhhkr;//最后还款日
	private String bqll;//本期利率(月利率)
	
	private String createTime;
	private String khmc;
	private String zjlx;
	private String zjhm;
	
	
	
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getJjh() {
		return jjh;
	}
	public void setJjh(String jjh) {
		this.jjh = jjh;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getKhh() {
		return khh;
	}
	public void setKhh(String khh) {
		this.khh = khh;
	}
	public String getZhtbh() {
		return zhtbh;
	}
	public void setZhtbh(String zhtbh) {
		this.zhtbh = zhtbh;
	}
	public String getJgdm() {
		return jgdm;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public String getKmh() {
		return kmh;
	}
	public void setKmh(String kmh) {
		this.kmh = kmh;
	}
	public String getHbzl() {
		return hbzl;
	}
	public void setHbzl(String hbzl) {
		this.hbzl = hbzl;
	}
	public String getJkje() {
		return jkje;
	}
	public void setJkje(String jkje) {
		this.jkje = jkje;
	}
	public String getQxrq() {
		return qxrq;
	}
	public void setQxrq(String qxrq) {
		this.qxrq = qxrq;
	}
	public String getDkye() {
		return dkye;
	}
	public void setDkye(String dkye) {
		this.dkye = dkye;
	}
	public String getBnqx() {
		return bnqx;
	}
	public void setBnqx(String bnqx) {
		this.bnqx = bnqx;
	}
	public String getBwqx() {
		return bwqx;
	}
	public void setBwqx(String bwqx) {
		this.bwqx = bwqx;
	}
	public String getWjfl1() {
		return wjfl1;
	}
	public void setWjfl1(String wjfl1) {
		this.wjfl1 = wjfl1;
	}
	public String getWjfl2() {
		return wjfl2;
	}
	public void setWjfl2(String wjfl2) {
		this.wjfl2 = wjfl2;
	}
	public String getWjfl3() {
		return wjfl3;
	}
	public void setWjfl3(String wjfl3) {
		this.wjfl3 = wjfl3;
	}
	public String getWjfl4() {
		return wjfl4;
	}
	public void setWjfl4(String wjfl4) {
		this.wjfl4 = wjfl4;
	}
	public String getWjfl5() {
		return wjfl5;
	}
	public void setWjfl5(String wjfl5) {
		this.wjfl5 = wjfl5;
	}
	public String getShbj() {
		return shbj;
	}
	public void setShbj(String shbj) {
		this.shbj = shbj;
	}
	public String getShlx() {
		return shlx;
	}
	public void setShlx(String shlx) {
		this.shlx = shlx;
	}
	public String getLxjs() {
		return lxjs;
	}
	public void setLxjs(String lxjs) {
		this.lxjs = lxjs;
	}
	public String getKsqxrq() {
		return ksqxrq;
	}
	public void setKsqxrq(String ksqxrq) {
		this.ksqxrq = ksqxrq;
	}
	public String getYhxbj() {
		return yhxbj;
	}
	public void setYhxbj(String yhxbj) {
		this.yhxbj = yhxbj;
	}
	public String getYhxlx() {
		return yhxlx;
	}
	public void setYhxlx(String yhxlx) {
		this.yhxlx = yhxlx;
	}
	public String getZhhkr() {
		return zhhkr;
	}
	public void setZhhkr(String zhhkr) {
		this.zhhkr = zhhkr;
	}
	public String getBqll() {
		return bqll;
	}
	public void setBqll(String bqll) {
		this.bqll = bqll;
	}
	
}
