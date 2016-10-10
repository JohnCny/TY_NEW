/**
 * 
 */
package com.cardpay.pccredit.manager.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 描述 ：中心人员绩效参数
 * @author 贺珈
 *
 * 2014-11-20 下午5:31:52
 */
@ModelParam(table="ty_performance_center",generator=IDType.uuid32)
public class TyPerformanceCenter extends BusinessModel {

	private static final long serialVersionUID = 1L;
	
	private String centerType;
	
	private String centerLevel;
	
	private String regularPay;

	public String getCenterType() {
		return centerType;
	}

	public void setCenterType(String centerType) {
		this.centerType = centerType;
	}

	public String getCenterLevel() {
		return centerLevel;
	}

	public void setCenterLevel(String centerLevel) {
		this.centerLevel = centerLevel;
	}

	public String getRegularPay() {
		return regularPay;
	}

	public void setRegularPay(String regularPay) {
		this.regularPay = regularPay;
	}

}
