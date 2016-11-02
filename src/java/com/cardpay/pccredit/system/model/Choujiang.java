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
@ModelParam(table="ty_tmp")
public class Choujiang extends BusinessModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String exentId;
	private String result;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExentId() {
		return exentId;
	}
	public void setExentId(String exentId) {
		this.exentId = exentId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	

}
