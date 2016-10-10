package com.cardpay.pccredit.system.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "node_audit")
public class NodeAudit extends BusinessModel{

	private String productId;
	
	private String nodeName;
	
	private String isstart;
	
	private String isend;
	
	private String nodeType;
	
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
