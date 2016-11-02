/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 
 * 描述 ：客户经理级别调整信息
 * @author 张石树
 *
 * 2014-11-20 上午10:01:19
 */
@ModelParam(table = "manager_level_adjustment")
public class ManagerLevelAdjustment extends BusinessModel {

	private String customerManagerId;
	
	private String systemAdvice;
	
	private String ifHandled;
	
	private String originalLevel;
	
	private String adjustAfterLevel;

	private Integer dataYear;
	
	private Integer dataMonth;

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public String getSystemAdvice() {
		return systemAdvice;
	}

	public void setSystemAdvice(String systemAdvice) {
		this.systemAdvice = systemAdvice;
	}

	public String getIfHandled() {
		return ifHandled;
	}

	public void setIfHandled(String ifHandled) {
		this.ifHandled = ifHandled;
	}

	public String getOriginalLevel() {
		return originalLevel;
	}

	public void setOriginalLevel(String originalLevel) {
		this.originalLevel = originalLevel;
	}

	public String getAdjustAfterLevel() {
		return adjustAfterLevel;
	}

	public void setAdjustAfterLevel(String adjustAfterLevel) {
		this.adjustAfterLevel = adjustAfterLevel;
	}

	public Integer getDataYear() {
		return dataYear;
	}

	public void setDataYear(Integer dataYear) {
		this.dataYear = dataYear;
	}

	public Integer getDataMonth() {
		return dataMonth;
	}

	public void setDataMonth(Integer dataMonth) {
		this.dataMonth = dataMonth;
	}
	
}
