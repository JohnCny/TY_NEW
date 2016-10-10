package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-11-18上午11:17:36
 */
public class ManagerCashConfigurationFilter extends BaseQueryFilter {
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
