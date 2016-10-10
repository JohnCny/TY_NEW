package com.cardpay.pccredit.system.model;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "node_audit_user")
public class NodeAuditUser extends BaseModel{

	private String nodeId;
	
	private String userId;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
