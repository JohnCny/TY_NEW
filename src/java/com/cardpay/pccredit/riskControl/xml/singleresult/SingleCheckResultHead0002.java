package com.cardpay.pccredit.riskControl.xml.singleresult;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author chenzhifang
 *
 * 2014-10-30下午6:09:11
 */
public class SingleCheckResultHead0002 {
	
	@XStreamAlias("BankCode")
	private String bankCode;
	
	@XStreamAlias("EntrustDate")
	private String entrustDate;
	
	@XStreamAlias("BusinessCode")
	private String businessCode;
	
	@XStreamAlias("UserCode")
	private String userCode;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getEntrustDate() {
		return entrustDate;
	}

	public void setEntrustDate(String entrustDate) {
		this.entrustDate = entrustDate;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
}
