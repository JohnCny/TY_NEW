package com.cardpay.pbccrcReport.pojo;

import com.wicresoft.jrad.base.database.model.BusinessModel;

/**
 * 客户信息抽象类
 * @author chenzhifang
 *
 * 2014-12-24上午9:19:58
 */
public abstract class AbstractCustomerInfo extends BusinessModel{
	private static final long serialVersionUID = 1L;
	// 客户Id
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
