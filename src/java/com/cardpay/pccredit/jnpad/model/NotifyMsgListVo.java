package com.cardpay.pccredit.jnpad.model;

import java.util.List;

import com.cardpay.pccredit.customer.model.CIPERSONBASINFOCOPY;

/**
 * 通知
 * @author songchen
 *
 */
public class NotifyMsgListVo {
	private int shendaihui;/*审贷会通知*/
	private int yuanshiziliao;/*客户原始资料变更通知*/
	private int peixun;///*培训通知*/ 
	private int kaocha;/*考察成绩通知*/
	private int qita;/*其他通知*/
	
	private int refuseCount;/*拒绝进件数量*/
	private int returnCount;/*补充调查*/
	private int risk;/*风险客户数量*/
	private int sum;/*总和*/
	private int Pcount;
	private int blackcount;/*黑名单数量*/
	private int passCount;
	
	private int ziliaobiangeng;
	private List<JnpadCustomerBianGeng> bianggeng;
	
	
	
	public int getPassCount() {
		return passCount;
	}
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	public int getBlackcount() {
		return blackcount;
	}
	public void setBlackcount(int blackcount) {
		this.blackcount = blackcount;
	}
	public int getPcount() {
		return Pcount;
	}
	public void setPcount(int pcount) {
		Pcount = pcount;
	}
	public int getZiliaobiangeng() {
		return ziliaobiangeng;
	}
	public void setZiliaobiangeng(int ziliaobiangeng) {
		this.ziliaobiangeng = ziliaobiangeng;
	}

	public List<JnpadCustomerBianGeng> getBianggeng() {
		return bianggeng;
	}
	public void setBianggeng(List<JnpadCustomerBianGeng> bianggeng) {
		this.bianggeng = bianggeng;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getRisk() {
		return risk;
	}
	public void setRisk(int risk) {
		this.risk = risk;
	}
	public int getRefuseCount() {
		return refuseCount;
	}
	public void setRefuseCount(int refuseCount) {
		this.refuseCount = refuseCount;
	}
	public int getReturnCount() {
		return returnCount;
	}
	public void setReturnCount(int returnCount) {
		this.returnCount = returnCount;
	}
	public int getShendaihui() {
		return shendaihui;
	}
	public void setShendaihui(int shendaihui) {
		this.shendaihui = shendaihui;
	}
	public int getYuanshiziliao() {
		return yuanshiziliao;
	}
	public void setYuanshiziliao(int yuanshiziliao) {
		this.yuanshiziliao = yuanshiziliao;
	}
	public int getPeixun() {
		return peixun;
	}
	public void setPeixun(int peixun) {
		this.peixun = peixun;
	}
	public int getKaocha() {
		return kaocha;
	}
	public void setKaocha(int kaocha) {
		this.kaocha = kaocha;
	}
	public int getQita() {
		return qita;
	}
	public void setQita(int qita) {
		this.qita = qita;
	}




}
