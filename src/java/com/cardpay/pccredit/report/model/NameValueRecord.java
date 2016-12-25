package com.cardpay.pccredit.report.model;

/**
 * @author chenzhifang
 *
 * 2014-12-18下午3:39:28
 */
public class NameValueRecord {
	// 
	private String id;
	
	// 名称
	private String name;
	
	// 值
	private String value;
	private String value1;
	private String value2;

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
