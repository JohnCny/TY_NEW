package com.cardpay.pccredit.system.model;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "node_control")
public class NodeControl extends BaseModel{

	private String  currentNode;
	
	private String currentStatus;
	
	private String nextNode;

	public String getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getNextNode() {
		return nextNode;
	}

	public void setNextNode(String nextNode) {
		this.nextNode = nextNode;
	}
}
