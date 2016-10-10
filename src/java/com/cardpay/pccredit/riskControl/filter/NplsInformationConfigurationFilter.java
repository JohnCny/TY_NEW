package com.cardpay.pccredit.riskControl.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-17 下午1:56:09
 */
public class NplsInformationConfigurationFilter extends BaseQueryFilter{
	
    private Integer aging;
	
	private String overdueMoney;

	public Integer getAging() {
		return aging;
	}

	public void setAging(Integer aging) {
		this.aging = aging;
	}

	public String getOverdueMoney() {
		return overdueMoney;
	}

	public void setOverdueMoney(String overdueMoney) {
		this.overdueMoney = overdueMoney;
	}
	

}
