package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.web.form.BaseForm;

/**
 * 信贷流程统计
 * @author zb
 */
public class XdlctjbbForms extends BaseForm {

	private static final long serialVersionUID = -8860955438817002631L;
	String chinese_name;
	String card_id;
	String PRODUCT_NAME;
	String APPLY_QUOTA;
	String CREATED_TIME;
	String start_examine_time;
	String jkrq;
	String xdsj;
	String display_name;
	String teamname;
	public XdlctjbbForms(){}
	public XdlctjbbForms(String chinese_name, String card_id,
			String pRODUCT_NAME, String aPPLY_QUOTA, String cREATED_TIME,
			String start_examine_time, String jkrq, String xdsj,
			String display_name, String teamname) {
		this.chinese_name = chinese_name;
		this.card_id = card_id;
		PRODUCT_NAME = pRODUCT_NAME;
		APPLY_QUOTA = aPPLY_QUOTA;
		CREATED_TIME = cREATED_TIME;
		this.start_examine_time = start_examine_time;
		this.jkrq = jkrq;
		this.xdsj = xdsj;
		this.display_name = display_name;
		this.teamname = teamname;
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
	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}
	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}
	public String getAPPLY_QUOTA() {
		return APPLY_QUOTA;
	}
	public void setAPPLY_QUOTA(String aPPLY_QUOTA) {
		APPLY_QUOTA = aPPLY_QUOTA;
	}
	public String getCREATED_TIME() {
		return CREATED_TIME;
	}
	public void setCREATED_TIME(String cREATED_TIME) {
		CREATED_TIME = cREATED_TIME;
	}
	public String getStart_examine_time() {
		return start_examine_time;
	}
	public void setStart_examine_time(String start_examine_time) {
		this.start_examine_time = start_examine_time;
	}
	public String getJkrq() {
		return jkrq;
	}
	public void setJkrq(String jkrq) {
		this.jkrq = jkrq;
	}
	public String getXdsj() {
		return xdsj;
	}
	public void setXdsj(String xdsj) {
		this.xdsj = xdsj;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getTeamname() {
		return teamname;
	}
	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}
	
	
	   
}