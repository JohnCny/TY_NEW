package com.cardpay.pccredit.jnpad.model;

import java.util.Date;

public class LoginInfo {
	private String ID;
	private String LOGIN;
	private Date ACTION_TIME;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getLOGIN() {
		return LOGIN;
	}
	public void setLOGIN(String lOGIN) {
		LOGIN = lOGIN;
	}
	public Date getACTION_TIME() {
		return ACTION_TIME;
	}
	public void setACTION_TIME(Date aCTION_TIME) {
		ACTION_TIME = aCTION_TIME;
	}
	

}
