package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 济南
 * 对私客户家庭成员信息
 */
@ModelParam(table = "T_CIPERSONFAMILY",generator=IDType.assigned)
public class CIPERSONFAMILY extends BusinessModel{
	private static final long serialVersionUID = 1L;
	
	private String   id;               
	private String   partyid;         
	private String   partyinitid;      
	private String   custid;           
	private String   typeid;           
	private String   name;            
	private String   sex;              
	private String   familymbercction; 
	private String   papertype;        
	private String   papercode;        
	private String   movephone;        
	private String   fixationphone;    
	private String   jobunit;          
	private String   createduser;      
	private String   createdtime;      
	private String   groupid;          
	private String   instcitycode;     
	private String   deletereason;     
	private String   status;           
	private String   operator;        
	private String   operdatetime;     
	private String   istrans;          
	private String   create_time;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPartyid() {
		return partyid;
	}
	public void setPartyid(String partyid) {
		this.partyid = partyid;
	}
	public String getPartyinitid() {
		return partyinitid;
	}
	public void setPartyinitid(String partyinitid) {
		this.partyinitid = partyinitid;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getFamilymbercction() {
		return familymbercction;
	}
	public void setFamilymbercction(String familymbercction) {
		this.familymbercction = familymbercction;
	}
	public String getPapertype() {
		return papertype;
	}
	public void setPapertype(String papertype) {
		this.papertype = papertype;
	}
	public String getPapercode() {
		return papercode;
	}
	public void setPapercode(String papercode) {
		this.papercode = papercode;
	}
	public String getMovephone() {
		return movephone;
	}
	public void setMovephone(String movephone) {
		this.movephone = movephone;
	}
	public String getFixationphone() {
		return fixationphone;
	}
	public void setFixationphone(String fixationphone) {
		this.fixationphone = fixationphone;
	}
	public String getJobunit() {
		return jobunit;
	}
	public void setJobunit(String jobunit) {
		this.jobunit = jobunit;
	}
	public String getCreateduser() {
		return createduser;
	}
	public void setCreateduser(String createduser) {
		this.createduser = createduser;
	}
	public String getCreatedtime() {
		return createdtime;
	}
	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getInstcitycode() {
		return instcitycode;
	}
	public void setInstcitycode(String instcitycode) {
		this.instcitycode = instcitycode;
	}
	public String getDeletereason() {
		return deletereason;
	}
	public void setDeletereason(String deletereason) {
		this.deletereason = deletereason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperdatetime() {
		return operdatetime;
	}
	public void setOperdatetime(String operdatetime) {
		this.operdatetime = operdatetime;
	}
	public String getIstrans() {
		return istrans;
	}
	public void setIstrans(String istrans) {
		this.istrans = istrans;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}      

}
