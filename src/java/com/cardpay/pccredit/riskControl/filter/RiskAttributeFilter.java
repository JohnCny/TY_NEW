package com.cardpay.pccredit.riskControl.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-10-29下午3:20:20
 */
public class RiskAttributeFilter extends BaseQueryFilter {
	
	// 类型(ONLINE:线上、OFFLINE:线下)
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
