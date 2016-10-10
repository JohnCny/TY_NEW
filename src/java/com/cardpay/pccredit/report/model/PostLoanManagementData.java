/**
 * 
 */
package com.cardpay.pccredit.report.model;

/**
 * @author shaoming
 *
 * 2014年12月22日   下午4:56:10
 */
public class PostLoanManagementData {
	private String allDueStatusCount;
	private String lowDueStatusCount;
	public String getAllDueStatusCount() {
		return allDueStatusCount;
	}
	public void setAllDueStatusCount(String allDueStatusCount) {
		this.allDueStatusCount = allDueStatusCount;
	}
	public String getLowDueStatusCount() {
		return lowDueStatusCount;
	}
	public void setLowDueStatusCount(String lowDueStatusCount) {
		this.lowDueStatusCount = lowDueStatusCount;
	}
	
}
