package com.cardpay.pccredit.datapri.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BaseModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/**
 * 
 * 该类的描述
 * @author steven Zhang
 *
 * 2014-10-14 上午9:45:31
 */
@ModelParam(table = "sys_resource_table_data_rule")
public class ResourceTableDataRule extends BaseModel{

	private String resTblId;
	
	private String ruleSql;
	
	private byte[] divHtml;
	
	private Date createdTime = null;

	private String createdBy = null;

	private Date modifiedTime = null;

	private String modifiedBy = null;
	
	private String divHtmlStr;
	
	public String getDivHtmlStr() {
		return divHtmlStr;
	}

	public void setDivHtmlStr(String divHtmlStr) {
		this.divHtmlStr = divHtmlStr;
	}

	public String getResTblId() {
		return resTblId;
	}

	public void setResTblId(String resTblId) {
		this.resTblId = resTblId;
	}

	public String getRuleSql() {
		return ruleSql;
	}

	public void setRuleSql(String ruleSql) {
		this.ruleSql = ruleSql;
	}

	public byte[] getDivHtml() {
		return divHtml;
	}

	public void setDivHtml(byte[] divHtml) {
		this.divHtml = divHtml;
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
