/**
 * 
 */
package com.cardpay.pccredit.ipad.model;

/**
 * ipad 接口 数据字典
 * @author shaoming
 *
 * 2014年11月25日   下午5:16:52
 */
public class DataDictionary {
	private String code;
	private String name;
	
	public DataDictionary() {
	}
	
	public DataDictionary(String code, String name) {
		this.code = code;
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
