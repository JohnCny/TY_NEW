package com.cardpay.pccredit.intopieces.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

import oracle.sql.DATE;
/*
 * 审批记录（审批客户经理、辅调客户经理记录）
 */
@ModelParam(table = "T_APP_MANAGER_AUDIT_LOG")
public class AppManagerAuditLog extends BusinessModel {
	
	private static final long serialVersionUID = -8470111754965975277L;
	
	private String  applicationId;//进件id
	private String  auditType;     
	private String  userId_1;    
	private String  userId_2;      
	private String  userId_3;
	private String  examineAmount;
	private String  examineLv;
	private String  qx;
	private String  userId_4;
	private String  hkfs;
	private String  beiZhu;
	private String  name1;
	private String  name2;
	private String  name3;
	private String  displayName;
	private String  pname;
	private String  sqje;
	private String cardId;
	private String pid;
	private String cid;
	private String applyQuota;
	private String PRODCREDITRANGE;
	private String FINAL_APPROVAL;
	private Date time;
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getFINAL_APPROVAL() {
		return FINAL_APPROVAL;
	}
	public void setFINAL_APPROVAL(String fINAL_APPROVAL) {
		FINAL_APPROVAL = fINAL_APPROVAL;
	}
	public String getApplyQuota() {
		return applyQuota;
	}
	public void setApplyQuota(String applyQuota) {
		this.applyQuota = applyQuota;
	}
	public String getPRODCREDITRANGE() {
		return PRODCREDITRANGE;
	}
	public void setPRODCREDITRANGE(String pRODCREDITRANGE) {
		PRODCREDITRANGE = pRODCREDITRANGE;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getSqje() {
		return sqje;
	}
	public void setSqje(String sqje) {
		this.sqje = sqje;
	}
	private String did;
	
	public String getDid() {
		return did;
	}
	public void setDid(String did) {
		this.did = did;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getName3() {
		return name3;
	}
	public void setName3(String name3) {
		this.name3 = name3;
	}
	public String getBeiZhu() {
		return beiZhu;
	}
	public void setBeiZhu(String beiZhu) {
		this.beiZhu = beiZhu;
	}
	public String getHkfs() {
		return hkfs;
	}
	public void setHkfs(String hkfs) {
		this.hkfs = hkfs;
	}
	public String getExamineAmount() {
		return examineAmount;
	}
	public void setExamineAmount(String examineAmount) {
		this.examineAmount = examineAmount;
	}
	public String getExamineLv() {
		return examineLv;
	}
	public void setExamineLv(String examineLv) {
		this.examineLv = examineLv;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getAuditType() {
		return auditType;
	}
	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}
	public String getUserId_1() {
		return userId_1;
	}
	public void setUserId_1(String userId_1) {
		this.userId_1 = userId_1;
	}
	public String getUserId_2() {
		return userId_2;
	}
	public void setUserId_2(String userId_2) {
		this.userId_2 = userId_2;
	}
	public String getUserId_3() {
		return userId_3;
	}
	public void setUserId_3(String userId_3) {
		this.userId_3 = userId_3;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getQx() {
		return qx;
	}
	public void setQx(String qx) {
		this.qx = qx;
	}
	public String getUserId_4() {
		return userId_4;
	}
	public void setUserId_4(String userId_4) {
		this.userId_4 = userId_4;
	}
	
	
}