/**
 * 
 */
package com.cardpay.pccredit.riskControl.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：不良资产model
 * @author 张石树
 *
 * 2014-11-4 上午9:33:30
 */
@ModelParam(table="npls_information_configuration")
public class NplsInfomationConfiguration extends BusinessModel{

	private Integer aging;
	
	private String overdueMoney;
	
	private String numberDaysOverdue;
	

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

	public String getNumberDaysOverdue() {
		return numberDaysOverdue;
	}

	public void setNumberDaysOverdue(String numberDaysOverdue) {
		this.numberDaysOverdue = numberDaysOverdue;
	}
}
