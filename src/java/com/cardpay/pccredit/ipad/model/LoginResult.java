/**
 * 
 */
package com.cardpay.pccredit.ipad.model;

/**
 * @author shaoming
 *
 * 2014年11月28日   下午2:21:02
 */
public class LoginResult {
	private UserIpad user;
	private String status;
	private String reason;
	
	public UserIpad getUser() {
		return user;
	}
	public void setUser(UserIpad user) {
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
