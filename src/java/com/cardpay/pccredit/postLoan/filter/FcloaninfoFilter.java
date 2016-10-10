package com.cardpay.pccredit.postLoan.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class FcloaninfoFilter extends BaseQueryFilter {

	 private String busiCode;
	 private String repayamt;
	 private String rapayinterest;
	 private String repaydate;
	public String getBusiCode() {
		return busiCode;
	}
	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
	public String getRepayamt() {
		return repayamt;
	}
	public void setRepayamt(String repayamt) {
		this.repayamt = repayamt;
	}
	public String getRapayinterest() {
		return rapayinterest;
	}
	public void setRapayinterest(String rapayinterest) {
		this.rapayinterest = rapayinterest;
	}
	public String getRepaydate() {
		return repaydate;
	}
	public void setRepaydate(String repaydate) {
		this.repaydate = repaydate;
	}



	 
	 
}
