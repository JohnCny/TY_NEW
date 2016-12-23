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
