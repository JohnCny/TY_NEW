package com.cardpay.pccredit.report.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

/**
 * @author chenzhifang
 *
 * 2014-12-1上午10:05:27
 */
public class AcctStatistical extends BusinessModel{
	private static final long serialVersionUID = -7513388150092036959L;

	private String rowIndex;
	
	private String name;
	
	private String id;
	
	// 授信总额度(基准日期)
	private String bCreditExtensionAmount;
	
	// 激活总额度(基准日期)
	private String bActivateExtensionAmount;
	
	// 透支本金(基准日期)
	private String bOverdraftPrincipal;
	
	// 透支总额度(基准日期)
	private String bOverdraftAmount;
	
	// 日均透支本金(基准日期)
	private String bOverdraftPrincipalAvg;
	
	// 日均透支总额度(基准日期)
	private String bOverdraftAmountAvg;
	
	// 不良透支本金(基准日期)
	private String bBadOverdraftPrincipal;
	
	// 不良率(基准日期)
	private String bBadRate;
	
	// 授信总额度(报表日期)
	private String rCreditExtensionAmount;
	
	// 激活总额度(报表日期)
	private String rActivateExtensionAmount;
	
	// 透支本金(报表日期)
	private String rOverdraftPrincipal;
	
	// 透支总额度(报表日期)
	private String rOverdraftAmount;
	
	// 日均透支本金(报表日期)
	private String rOverdraftPrincipalAvg;
	
	// 日均透支总额度(报表日期)
	private String rOverdraftAmountAvg;
	
	// 不良透支本金(报表日期)
	private String rBadOverdraftPrincipal;
	
	// 不良率(报表日期)
	private String rBadRate;
	
	// 授信总额度(净增数据)
	private String addCreditExtensionAmount;
	
	// 激活总额度(净增数据)
	private String addActivateExtensionAmount;
	
	// 透支本金(净增数据)
	private String addOverdraftPrincipal;
	
	// 透支总额度(净增数据)
	private String addOverdraftAmount;
	
	// 日均透支本金(净增数据)
	private String addOverdraftPrincipalAvg;
	
	// 日均透支总额度(净增数据)
	private String addOverdraftAmountAvg;
	
	// 不良透支本金(净增数据)
	private String addBadOverdraftPrincipal;
	
	// 不良率(净增数据)
	private String addBadRate;

	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

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

	public String getbCreditExtensionAmount() {
		return bCreditExtensionAmount;
	}

	public void setbCreditExtensionAmount(String bCreditExtensionAmount) {
		this.bCreditExtensionAmount = bCreditExtensionAmount;
	}

	public String getbActivateExtensionAmount() {
		return bActivateExtensionAmount;
	}

	public void setbActivateExtensionAmount(String bActivateExtensionAmount) {
		this.bActivateExtensionAmount = bActivateExtensionAmount;
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

	public String getbOverdraftPrincipalAvg() {
		return bOverdraftPrincipalAvg;
	}

	public void setbOverdraftPrincipalAvg(String bOverdraftPrincipalAvg) {
		this.bOverdraftPrincipalAvg = bOverdraftPrincipalAvg;
	}

	public String getbOverdraftAmountAvg() {
		return bOverdraftAmountAvg;
	}

	public void setbOverdraftAmountAvg(String bOverdraftAmountAvg) {
		this.bOverdraftAmountAvg = bOverdraftAmountAvg;
	}

	public String getbBadOverdraftPrincipal() {
		return bBadOverdraftPrincipal;
	}

	public void setbBadOverdraftPrincipal(String bBadOverdraftPrincipal) {
		this.bBadOverdraftPrincipal = bBadOverdraftPrincipal;
	}

	public String getbBadRate() {
		return bBadRate;
	}

	public void setbBadRate(String bBadRate) {
		this.bBadRate = bBadRate;
	}

	public String getrCreditExtensionAmount() {
		return rCreditExtensionAmount;
	}

	public void setrCreditExtensionAmount(String rCreditExtensionAmount) {
		this.rCreditExtensionAmount = rCreditExtensionAmount;
	}

	public String getrActivateExtensionAmount() {
		return rActivateExtensionAmount;
	}

	public void setrActivateExtensionAmount(String rActivateExtensionAmount) {
		this.rActivateExtensionAmount = rActivateExtensionAmount;
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

	public String getrOverdraftPrincipalAvg() {
		return rOverdraftPrincipalAvg;
	}

	public void setrOverdraftPrincipalAvg(String rOverdraftPrincipalAvg) {
		this.rOverdraftPrincipalAvg = rOverdraftPrincipalAvg;
	}

	public String getrOverdraftAmountAvg() {
		return rOverdraftAmountAvg;
	}

	public void setrOverdraftAmountAvg(String rOverdraftAmountAvg) {
		this.rOverdraftAmountAvg = rOverdraftAmountAvg;
	}

	public String getrBadOverdraftPrincipal() {
		return rBadOverdraftPrincipal;
	}

	public void setrBadOverdraftPrincipal(String rBadOverdraftPrincipal) {
		this.rBadOverdraftPrincipal = rBadOverdraftPrincipal;
	}

	public String getrBadRate() {
		return rBadRate;
	}

	public void setrBadRate(String rBadRate) {
		this.rBadRate = rBadRate;
	}

	public String getAddCreditExtensionAmount() {
		return addCreditExtensionAmount;
	}

	public void setAddCreditExtensionAmount(String addCreditExtensionAmount) {
		this.addCreditExtensionAmount = addCreditExtensionAmount;
	}

	public String getAddActivateExtensionAmount() {
		return addActivateExtensionAmount;
	}

	public void setAddActivateExtensionAmount(String addActivateExtensionAmount) {
		this.addActivateExtensionAmount = addActivateExtensionAmount;
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

	public String getAddOverdraftPrincipalAvg() {
		return addOverdraftPrincipalAvg;
	}

	public void setAddOverdraftPrincipalAvg(String addOverdraftPrincipalAvg) {
		this.addOverdraftPrincipalAvg = addOverdraftPrincipalAvg;
	}

	public String getAddOverdraftAmountAvg() {
		return addOverdraftAmountAvg;
	}

	public void setAddOverdraftAmountAvg(String addOverdraftAmountAvg) {
		this.addOverdraftAmountAvg = addOverdraftAmountAvg;
	}

	public String getAddBadOverdraftPrincipal() {
		return addBadOverdraftPrincipal;
	}

	public void setAddBadOverdraftPrincipal(String addBadOverdraftPrincipal) {
		this.addBadOverdraftPrincipal = addBadOverdraftPrincipal;
	}

	public String getAddBadRate() {
		return addBadRate;
	}

	public void setAddBadRate(String addBadRate) {
		this.addBadRate = addBadRate;
	}
}
