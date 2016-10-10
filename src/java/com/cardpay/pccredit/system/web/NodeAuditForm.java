package com.cardpay.pccredit.system.web;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.ModelParam;
import com.wicresoft.jrad.base.web.form.BaseForm;

@ModelParam(table = "node_audit")
public class NodeAuditForm extends BaseForm{

	private String productId;
	
	private String nodeName;
	
	private String isstart;
	
	private String isend;
	
	private String nodeType;
	
	private String type;
	
	//用户显示 审批人员名称
	private String auditUserIds;
	private String auditUserNames;
	
	private Integer seqNo;

	private Date createdTime;

	private String createdBy;

	private Date modifiedTime;

	private String modifiedBy;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getIsstart() {
		return isstart;
	}

	public void setIsstart(String isstart) {
		this.isstart = isstart;
	}

	public String getIsend() {
		return isend;
	}

	public void setIsend(String isend) {
		this.isend = isend;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getAuditUserIds() {
		return auditUserIds;
	}

	public void setAuditUserIds(String auditUserIds) {
		this.auditUserIds = auditUserIds;
	}

	public String getAuditUserNames() {
		return auditUserNames;
	}

	public void setAuditUserNames(String auditUserNames) {
		this.auditUserNames = auditUserNames;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
