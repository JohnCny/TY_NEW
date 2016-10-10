/**
 * 
 */
package com.cardpay.pccredit.datapri.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 该类的描述
 * @author steven Zhang
 *
 * 2014-10-13 下午3:31:22
 */
@ModelParam(table = "sys_resource_table_field")
public class ResourceTableField extends BaseModel{

	private String resTblId;
	
	private String resTblField;
	
	private String resTblFieldCn;
	
	private String resTblFieldType;
	
	private Date createdTime = null;

	private String createdBy = null;

	private Date modifiedTime = null;

	private String modifiedBy = null;

	public String getResTblId() {
		return resTblId;
	}

	public void setResTblId(String resTblId) {
		this.resTblId = resTblId;
	}

	public String getResTblField() {
		return resTblField;
	}

	public void setResTblField(String resTblField) {
		this.resTblField = resTblField;
	}

	public String getResTblFieldCn() {
		return resTblFieldCn;
	}

	public void setResTblFieldCn(String resTblFieldCn) {
		this.resTblFieldCn = resTblFieldCn;
	}

	public String getResTblFieldType() {
		return resTblFieldType;
	}

	public void setResTblFieldType(String resTblFieldType) {
		this.resTblFieldType = resTblFieldType;
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
