package com.cardpay.pccredit.report.model;

/**
 * 用信还款情况统计
 * @author chenzhifang
 *
 * 2014-12-17下午3:39:11
 */
public class CreditPayment {
	private String rowIndex;
	
	private String name;
	
	private String id;
	
	// 基准日期透支本金额度(单位：分)
	private String bOverdraftPrincipal;
	
	// 基准日期透支总额度(单位：分)
	private String bOverdraftAmount;
	
	// 报表日期透支本金额度(单位：分)
	private String rOverdraftPrincipal;
	
	// 报表日期透支总额度(单位：分)
	private String rOverdraftAmount;
	
	// 净增透支本金额度(单位：分)
	private String addOverdraftPrincipal;
	
	// 净增透支总额度(单位：分)
	private String addOverdraftAmount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getbOverdraftPrincipal() {
		return bOverdraftPrincipal;
	}

	public void setbOverdraftPrincipal(String bOverdraftPrincipal) {
		this.bOverdraftPrincipal = bOverdraftPrincipal;
	}

	public String getbOverdraftAmount() {
		return bOverdraftAmount;
	}

	public void setbOverdraftAmount(String bOverdraftAmount) {
		this.bOverdraftAmount = bOverdraftAmount;
	}

	public String getrOverdraftPrincipal() {
		return rOverdraftPrincipal;
	}

	public void setrOverdraftPrincipal(String rOverdraftPrincipal) {
		this.rOverdraftPrincipal = rOverdraftPrincipal;
	}

	public String getrOverdraftAmount() {
		return rOverdraftAmount;
	}

	public void setrOverdraftAmount(String rOverdraftAmount) {
		this.rOverdraftAmount = rOverdraftAmount;
	}

	public String getAddOverdraftPrincipal() {
		return addOverdraftPrincipal;
	}

	public void setAddOverdraftPrincipal(String addOverdraftPrincipal) {
		this.addOverdraftPrincipal = addOverdraftPrincipal;
	}

	public String getAddOverdraftAmount() {
		return addOverdraftAmount;
	}

	public void setAddOverdraftAmount(String addOverdraftAmount) {
		this.addOverdraftAmount = addOverdraftAmount;
	}

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}
}
