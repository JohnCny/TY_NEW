package com.cardpay.pccredit.zrrtz.model;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class IncomingData extends BusinessFilter{
	private String chinese_name;
	private String card_id;
	private String product_name;
	private String apply_quota;
	private String actual_quote;
	private String display_name;
	private String fdate;
	private String ldate;
	public String getActual_quote() {
		return actual_quote;
	}

	public void setActual_quote(String actual_quote) {
		this.actual_quote = actual_quote;
	}
	public String getFdate() {
		return fdate;
	}

	public void setFdate(String fdate) {
		this.fdate = fdate;
	}

	public String getLdate() {
		return ldate;
	}

	public void setLdate(String ldate) {
		this.ldate = ldate;
	}

	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
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
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getApply_quota() {
		return apply_quota;
	}
	public void setApply_quota(String apply_quota) {
		this.apply_quota = apply_quota;
	}
	
}
