package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "wf_status_info")
public class WfStatusInfo extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -959284890543184723L;
	
	private String relationedProcess;
	private String isClosed;
	private String isStart;
	private String statusKeepTime; 
	private String statusName;
	private String statusCode;
	
	public String getRelationedProcess() {
		return relationedProcess;
	}
	public void setRelationedProcess(String relationedProcess) {
		this.relationedProcess = relationedProcess;
	}

	public String getIsClosed() {
		return isClosed;
	}
	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
	
	public String getIsStart() {
		return isStart;
	}
	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}
	public String getStatusKeepTime() {
		return statusKeepTime;
	}
	public void setStatusKeepTime(String statusKeepTime) {
		this.statusKeepTime = statusKeepTime;
	}
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
