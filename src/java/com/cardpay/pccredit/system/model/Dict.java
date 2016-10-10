package com.cardpay.pccredit.system.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * 描述 ：数据字典model
 * @author 张石树
 *
 * 2014-11-5 下午3:56:43
 */
@ModelParam(table="dict")
public class Dict extends BusinessModel{
	private String dictType;
	private String TypeCode;
	private String TypeName;
	private String bankCode;
	
	public String getTypeCode() {
		return TypeCode;
	}
	public void setTypeCode(String typeCode) {
		TypeCode = typeCode;
	}
	public String getTypeName() {
		return TypeName;
	}
	public void setTypeName(String typeName) {
		TypeName = typeName;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
}
