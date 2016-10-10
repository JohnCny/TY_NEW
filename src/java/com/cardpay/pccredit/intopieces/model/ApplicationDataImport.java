package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "application_data_import")
public class ApplicationDataImport extends BusinessModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String chineseName;
	private String englishName;
	private String fieldType;
	private String fieldLength;
	private String fieldStart;
	private String fieldEnd;
	private String must;
	private String note;
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldLength() {
		return fieldLength;
	}
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}
	public String getFieldStart() {
		return fieldStart;
	}
	public void setFieldStart(String fieldStart) {
		this.fieldStart = fieldStart;
	}
	public String getFieldEnd() {
		return fieldEnd;
	}
	public void setFieldEnd(String fieldEnd) {
		this.fieldEnd = fieldEnd;
	}
	public String getMust() {
		return must;
	}
	public void setMust(String must) {
		this.must = must;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}