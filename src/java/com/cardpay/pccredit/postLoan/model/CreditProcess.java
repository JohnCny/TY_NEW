package com.cardpay.pccredit.postLoan.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;
import com.wicresoft.jrad.base.database.model.BusinessModel;

/*
 *信贷流程跟踪表 
 * */
public class CreditProcess extends BusinessFilter {
private String spmc;//企业名称
private String customername;//客户姓名
private String teamname;//所属团队
private String bljg;//所属区域
private String dklx;//贷款种类
private String mainassure;//担保类型
private String industry;//行业类型
private String mananger;//客户经理
private String ghmananger;//管户客户经理
private Date applytime;//申请时间
private String applymoney;//申请金额
private Date willtime;//首次上会时间 决议时间
private String auditopinion;//决议结果
private String examineamount;//批准金额
private String audittime;//放款时间
private String zmts;//周末天数
private String zjdays;//办理总计天数
private String examine;//合同年利率
private String status;//当前状态
private String id;//客户ID
private String cardId;//身份证号

private String userid4;
private String startexaminetime;
private String cssptime;
private String dbfs;
private String examineresult;
private String userid1;
private String userid2;
private String userid3;
private String qx;
private String examinelv;
private String sptime;
private String sdjytime;
private String spje;
private String splv;
private String spqx;
private String beizhu;
private String jlyy;
private String zsw;
private String sdwuser1;
private String sdtime;
private String sdqx;
private String sdje;
private String lv;
private String jjyy;
private String sdwuser1yj;
private String productname;


public String getUserid4() {
	return userid4;
}
public void setUserid4(String userid4) {
	this.userid4 = userid4;
}
public String getStartexaminetime() {
	return startexaminetime;
}
public void setStartexaminetime(String startexaminetime) {
	this.startexaminetime = startexaminetime;
}
public String getCssptime() {
	return cssptime;
}
public void setCssptime(String cssptime) {
	this.cssptime = cssptime;
}
public String getDbfs() {
	return dbfs;
}
public void setDbfs(String dbfs) {
	this.dbfs = dbfs;
}
public String getExamineresult() {
	return examineresult;
}
public void setExamineresult(String examineresult) {
	this.examineresult = examineresult;
}
public String getUserid1() {
	return userid1;
}
public void setUserid1(String userid1) {
	this.userid1 = userid1;
}
public String getUserid2() {
	return userid2;
}
public void setUserid2(String userid2) {
	this.userid2 = userid2;
}
public String getUserid3() {
	return userid3;
}
public void setUserid3(String userid3) {
	this.userid3 = userid3;
}
public String getQx() {
	return qx;
}
public void setQx(String qx) {
	this.qx = qx;
}
public String getExaminelv() {
	return examinelv;
}
public void setExaminelv(String examinelv) {
	this.examinelv = examinelv;
}
public String getSptime() {
	return sptime;
}
public void setSptime(String sptime) {
	this.sptime = sptime;
}
public String getSdjytime() {
	return sdjytime;
}
public void setSdjytime(String sdjytime) {
	this.sdjytime = sdjytime;
}
public String getSpje() {
	return spje;
}
public void setSpje(String spje) {
	this.spje = spje;
}
public String getSplv() {
	return splv;
}
public void setSplv(String splv) {
	this.splv = splv;
}
public String getSpqx() {
	return spqx;
}
public void setSpqx(String spqx) {
	this.spqx = spqx;
}
public String getBeizhu() {
	return beizhu;
}
public void setBeizhu(String beizhu) {
	this.beizhu = beizhu;
}
public String getJlyy() {
	return jlyy;
}
public void setJlyy(String jlyy) {
	this.jlyy = jlyy;
}
public String getZsw() {
	return zsw;
}
public void setZsw(String zsw) {
	this.zsw = zsw;
}
public String getSdwuser1() {
	return sdwuser1;
}
public void setSdwuser1(String sdwuser1) {
	this.sdwuser1 = sdwuser1;
}
public String getSdtime() {
	return sdtime;
}
public void setSdtime(String sdtime) {
	this.sdtime = sdtime;
}
public String getSdqx() {
	return sdqx;
}
public void setSdqx(String sdqx) {
	this.sdqx = sdqx;
}
public String getSdje() {
	return sdje;
}
public void setSdje(String sdje) {
	this.sdje = sdje;
}
public String getLv() {
	return lv;
}
public void setLv(String lv) {
	this.lv = lv;
}
public String getJjyy() {
	return jjyy;
}
public void setJjyy(String jjyy) {
	this.jjyy = jjyy;
}
public String getSdwuser1yj() {
	return sdwuser1yj;
}
public void setSdwuser1yj(String sdwuser1yj) {
	this.sdwuser1yj = sdwuser1yj;
}
public String getProductname() {
	return productname;
}
public void setProductname(String productname) {
	this.productname = productname;
}
public String getSpmc() {
	return spmc;
}
public void setSpmc(String spmc) {
	this.spmc = spmc;
}
public String getCustomername() {
	return customername;
}
public void setCustomername(String customername) {
	this.customername = customername;
}
public String getTeamname() {
	return teamname;
}
public void setTeamname(String teamname) {
	this.teamname = teamname;
}
public String getBljg() {
	return bljg;
}
public void setBljg(String bljg) {
	this.bljg = bljg;
}
public String getDklx() {
	return dklx;
}
public void setDklx(String dklx) {
	this.dklx = dklx;
}
public String getMainassure() {
	return mainassure;
}
public void setMainassure(String mainassure) {
	this.mainassure = mainassure;
}
public String getIndustry() {
	return industry;
}
public void setIndustry(String industry) {
	this.industry = industry;
}
public String getMananger() {
	return mananger;
}
public void setMananger(String mananger) {
	this.mananger = mananger;
}
public String getGhmananger() {
	return ghmananger;
}
public void setGhmananger(String ghmananger) {
	this.ghmananger = ghmananger;
}
public Date getApplytime() {
	return applytime;
}
public void setApplytime(Date applytime) {
	this.applytime = applytime;
}
public String getApplymoney() {
	return applymoney;
}
public void setApplymoney(String applymoney) {
	this.applymoney = applymoney;
}
public Date getWilltime() {
	return willtime;
}
public void setWilltime(Date willtime) {
	this.willtime = willtime;
}
public String getAuditopinion() {
	return auditopinion;
}
public void setAuditopinion(String auditopinion) {
	this.auditopinion = auditopinion;
}
public String getExamineamount() {
	return examineamount;
}
public void setExamineamount(String examineamount) {
	this.examineamount = examineamount;
}

public String getAudittime() {
	return audittime;
}
public void setAudittime(String audittime) {
	this.audittime = audittime;
}
public String getZmts() {
	return zmts;
}
public void setZmts(String zmts) {
	this.zmts = zmts;
}
public String getZjdays() {
	return zjdays;
}
public void setZjdays(String zjdays) {
	this.zjdays = zjdays;
}
public String getExamine() {
	return examine;
}
public void setExamine(String examine) {
	this.examine = examine;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getCardId() {
	return cardId;
}
public void setCardId(String cardId) {
	this.cardId = cardId;
}

}
