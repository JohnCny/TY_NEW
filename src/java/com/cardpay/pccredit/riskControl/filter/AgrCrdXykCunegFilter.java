package com.cardpay.pccredit.riskControl.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author chenzhifang
 *
 * 2014-12-22下午4:27:11
 */
public class AgrCrdXykCunegFilter extends BaseQueryFilter {

	private String custrNbr;

	public String getCustrNbr() {
		return custrNbr;
	}

	public void setCustrNbr(String custrNbr) {
		this.custrNbr = custrNbr;
	}
	
}
