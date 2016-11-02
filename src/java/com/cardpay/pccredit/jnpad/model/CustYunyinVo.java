package com.cardpay.pccredit.jnpad.model;

/**
 * 客户运营情况
 * @author songchen
 *
 */
public class CustYunyinVo {
   private String  ksze;//客户授信总额;
   private String  kyze;//客户用信总额;
   private String  kyyeze;//客户逾期余额总额;
   private String  yqkhs;//逾期客户数;
   private String  hxkhs;//核销客户数;
public String getKsze() {
	return ksze;
}
public void setKsze(String ksze) {
	this.ksze = ksze;
}
public String getKyze() {
	return kyze;
}
public void setKyze(String kyze) {
	this.kyze = kyze;
}
public String getKyyeze() {
	return kyyeze;
}
public void setKyyeze(String kyyeze) {
	this.kyyeze = kyyeze;
}
public String getYqkhs() {
	return yqkhs;
}
public void setYqkhs(String yqkhs) {
	this.yqkhs = yqkhs;
}
public String getHxkhs() {
	return hxkhs;
}
public void setHxkhs(String hxkhs) {
	this.hxkhs = hxkhs;
}
   
}	
