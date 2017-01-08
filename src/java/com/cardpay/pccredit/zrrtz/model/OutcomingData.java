package com.cardpay.pccredit.zrrtz.model;


import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

public class OutcomingData extends BusinessModel {
	
	  private static final long serialVersionUID = 1L;
	  private String ywbh;
	  private String Customername;
	  private String Idcard            ;
	  private String Productname       ;
	  private String actual_quote      ;
	  private String Managername       ;
	  private String Money             ;
	  private String Deadline          ;
	  private String Providedate       ;
	  private String Expiredate        ;
	  private String Classification    ;
	  private String Scopeoperation    ;
	  private String Operationaddress  ;
	  private String Principal         ;
	  private String Assist            ;
	  private String Groupes           ;
	  private String Members           ;
	  private String Patternslend      ;
	  private String Ratepaying        ;
	  private String Giveback          ;
	  private String SJ                ;
	  private String Remark            ;
	  private String rowIndex;//序号
	public String getCustomername() {
		return Customername;
	}
	public void setCustomername(String customername) {
		Customername = customername;
	}
	public String getIdcard() {
		return Idcard;
	}
	public void setIdcard(String idcard) {
		Idcard = idcard;
	}
	public String getProductname() {
		return Productname;
	}
	public void setProductname(String productname) {
		Productname = productname;
	}
	public String getActual_quote() {
		return actual_quote;
	}
	public void setActual_quote(String actual_quote) {
		this.actual_quote = actual_quote;
	}
	public String getManagername() {
		return Managername;
	}
	public void setManagername(String managername) {
		Managername = managername;
	}
	public String getMoney() {
		return Money;
	}
	public void setMoney(String money) {
		Money = money;
	}
	public String getDeadline() {
		return Deadline;
	}
	public void setDeadline(String deadline) {
		Deadline = deadline;
	}
	public String getProvidedate() {
		return Providedate;
	}
	public void setProvidedate(String providedate) {
		Providedate = providedate;
	}
	public String getExpiredate() {
		return Expiredate;
	}
	public void setExpiredate(String expiredate) {
		Expiredate = expiredate;
	}
	public String getClassification() {
		return Classification;
	}
	public void setClassification(String classification) {
		Classification = classification;
	}
	public String getScopeoperation() {
		return Scopeoperation;
	}
	public void setScopeoperation(String scopeoperation) {
		Scopeoperation = scopeoperation;
	}
	public String getOperationaddress() {
		return Operationaddress;
	}
	public void setOperationaddress(String operationaddress) {
		Operationaddress = operationaddress;
	}
	public String getPrincipal() {
		return Principal;
	}
	public void setPrincipal(String principal) {
		Principal = principal;
	}
	public String getAssist() {
		return Assist;
	}
	public void setAssist(String assist) {
		Assist = assist;
	}
	public String getGroupes() {
		return Groupes;
	}
	public void setGroupes(String groupes) {
		Groupes = groupes;
	}
	public String getMembers() {
		return Members;
	}
	public void setMembers(String members) {
		Members = members;
	}
	public String getPatternslend() {
		return Patternslend;
	}
	public void setPatternslend(String patternslend) {
		Patternslend = patternslend;
	}
	public String getRatepaying() {
		return Ratepaying;
	}
	public void setRatepaying(String ratepaying) {
		Ratepaying = ratepaying;
	}
	public String getGiveback() {
		return Giveback;
	}
	public void setGiveback(String giveback) {
		Giveback = giveback;
	}
	public String getSJ() {
		return SJ;
	}
	public void setSJ(String sJ) {
		SJ = sJ;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	  
}
