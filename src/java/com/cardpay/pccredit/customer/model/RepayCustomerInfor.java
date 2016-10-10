package com.cardpay.pccredit.customer.model;

import com.wicresoft.jrad.base.database.model.BaseModel;


/**
 * 
 * @author 贺珈
 * 还款客户列表
 *
 */
public class RepayCustomerInfor extends BaseModel {
	private static final long serialVersionUID = 1L;
	private String jjh;//借据号
	private String rq;//日期
	private String customer;//客户名称
	private String name;//客户经理名称
	public String getJjh() {
		return jjh;
	}
	public void setJjh(String jjh) {
		this.jjh = jjh;
	}
	public String getRq() {
		return rq;
	}
	public void setRq(String rq) {
		this.rq = rq;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	
}
