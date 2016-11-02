package com.cardpay.workflow.models;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "wf_process_info")
public class WfProcessInfo extends BusinessModel{
	
	private static final long serialVersionUID = -7014003970141656050L;

	private String processType;
	
	private String version;
	
	private String id;
	
	public String getProcessType() {
		return processType;
	}
	public void setProcessType(String processType) {
		this.processType = processType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	} 
	
}
