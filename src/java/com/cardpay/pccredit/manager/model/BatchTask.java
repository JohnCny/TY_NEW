package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 记录批处理日志
 * @author sc
 */
@ModelParam(table = "T_BATCH_TASK")
public class BatchTask extends BusinessModel {
	
	  private String id;           
	  private String batchCode;  
	  private String batchName;   
	  private String status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}       
	
	  
}
