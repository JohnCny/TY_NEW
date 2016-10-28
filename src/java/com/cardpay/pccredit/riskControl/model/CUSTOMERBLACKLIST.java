package com.cardpay.pccredit.riskControl.model;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class CUSTOMERBLACKLIST extends BaseQueryFilter{
	private String id;
	private String custormerid;
	private String reason;
	private String userid;
	private String chinese_name;
	private String card_id;
	private String display_name;
	private String customer_id;
	private String card_type;
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getChinese_name() {
		return chinese_name;
	}
	public void setChinese_name(String chinese_name) {
		this.chinese_name = chinese_name;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustormerid() {
		return custormerid;
	}
	public void setCustormerid(String custormerid) {
		this.custormerid = custormerid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

}
