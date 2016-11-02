package com.cardpay.pccredit.jnpad.model;

public class JnUserLoginResult {
	private JnUserLoginIpad user;
	private String status;
	private String reason;
	
	public JnUserLoginIpad getUser() {
		return user;
	}
	public void setUser(JnUserLoginIpad user) {
		this.user = user;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
