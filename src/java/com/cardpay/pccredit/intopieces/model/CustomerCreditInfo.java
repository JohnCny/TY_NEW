package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

/**
 * 客户用信信息
 * @author 宋辰
 *
 */
public class CustomerCreditInfo  extends BusinessModel {
	
	
    
	private String jzll;//基准利率
	private String htll;//合同利率
	private String ffje;//本次发放金额
	private String jkrq;//借款日期
	private String dqrq;//到期日期
	private String dkqx;//贷款期限
	private String jkje;//借款金额
	private String zhhkr;//最后还款日
	private String shbj;//收回本金
	private String shlx;//收回利息
	
	private String yqbj;//逾期本金
	
	
	public String getYqbj() {
		return yqbj;
	}
	public void setYqbj(String yqbj) {
		this.yqbj = yqbj;
	}
	public String getJzll() {
		return jzll;
	}
	public void setJzll(String jzll) {
		this.jzll = jzll;
	}
	public String getHtll() {
		return htll;
	}
	public void setHtll(String htll) {
		this.htll = htll;
	}
	public String getFfje() {
		return ffje;
	}
	public void setFfje(String ffje) {
		this.ffje = ffje;
	}
	public String getJkrq() {
		return jkrq;
	}
	public void setJkrq(String jkrq) {
		this.jkrq = jkrq;
	}
	public String getDqrq() {
		return dqrq;
	}
	public void setDqrq(String dqrq) {
		this.dqrq = dqrq;
	}
	public String getDkqx() {
		return dkqx;
	}
	public void setDkqx(String dkqx) {
		this.dkqx = dkqx;
	}
	public String getJkje() {
		return jkje;
	}
	public void setJkje(String jkje) {
		this.jkje = jkje;
	}
	public String getZhhkr() {
		return zhhkr;
	}
	public void setZhhkr(String zhhkr) {
		this.zhhkr = zhhkr;
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
	
	
}
