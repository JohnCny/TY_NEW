package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * @author chenzhifang
 *
 * 2014-11-18上午11:13:55
 */
@ModelParam(table = "manager_cash_configuration")
public class ManagerCashConfiguration extends BusinessModel {
	private static final long serialVersionUID = 1L;
	// 提取比率
	private String marginExtractInfo;
	
	private String startAmount;
	
	private String endAmount;

	public String getMarginExtractInfo() {
		return marginExtractInfo;
	}

	public void setMarginExtractInfo(String marginExtractInfo) {
		this.marginExtractInfo = marginExtractInfo;
	}

	public String getStartAmount() {
		return startAmount;
	}

	public void setStartAmount(String startAmount) {
		this.startAmount = startAmount;
	}

	public String getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(String endAmount) {
		this.endAmount = endAmount;
	}
}
