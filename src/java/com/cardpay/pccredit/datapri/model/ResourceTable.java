package com.cardpay.pccredit.datapri.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * 资源主体
 * @author steven Zhang
 *
 * 2014-10-13 上午11:33:56
 */
@ModelParam(table = "sys_resource_table")
public class ResourceTable extends BaseModel{
	
	private String resTbl;
	
	private String resTblCn;
	
	private String resTblDesc;
	
	private Date createdTime = null;

	private String createdBy = null;

	private Date modifiedTime = null;

	private String modifiedBy = null;

	public String getResTbl() {
		return resTbl;
	}

	public void setResTbl(String resTbl) {
		this.resTbl = resTbl;
	}

	public String getResTblCn() {
		return resTblCn;
	}

	public void setResTblCn(String resTblCn) {
		this.resTblCn = resTblCn;
	}

	public String getResTblDesc() {
		return resTblDesc;
	}

	public void setResTblDesc(String resTblDesc) {
		this.resTblDesc = resTblDesc;
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
	
}
