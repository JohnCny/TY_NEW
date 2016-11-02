package com.cardpay.pccredit.tools;

/**
 * @author chenzhifang
 *
 * 2014-12-3下午4:56:35
 */
public class DataFileConf {
	
	// 对应的序号
	private int index;
	
	// 数据库类型
	private String jdbcType;
	
	// 列
	private String column;
	
	private String style;

	public DataFileConf(){}
	
	public DataFileConf(String column, String jdbcType, int index, String style){
		this.column = column;
		this.jdbcType = jdbcType;
		this.index = index;
		this.style = style;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
}
