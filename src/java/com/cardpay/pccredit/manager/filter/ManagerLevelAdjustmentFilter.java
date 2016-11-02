/**
 * 
 */
package com.cardpay.pccredit.manager.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 
 * 描述 ：客户经理评估Filter
 * @author 张石树
 *
 * 2014-11-13 下午2:34:51
 */
public class ManagerLevelAdjustmentFilter extends BaseQueryFilter{
	
	private String customerManagerId;
	
	private String customerManagerName;

	public String getCustomerManagerId() {
		return customerManagerId;
	}

	public void setCustomerManagerId(String customerManagerId) {
		this.customerManagerId = customerManagerId;
	}

	public String getCustomerManagerName() {
		return customerManagerName;
	}

	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}
	
}
