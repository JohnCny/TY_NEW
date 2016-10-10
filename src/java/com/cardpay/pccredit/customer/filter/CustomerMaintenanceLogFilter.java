package com.cardpay.pccredit.customer.filter;

import com.wicresoft.jrad.base.database.dao.query.Operator;
import com.wicresoft.jrad.base.database.dao.query.OrderBy;
import com.wicresoft.jrad.base.database.dao.query.QueryParam;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 
 * @author 季东晓
 *
 * 2014-10-28 上午10:11:20
 */

public class CustomerMaintenanceLogFilter extends BaseQueryFilter{
	
	private String customerId;

	@QueryParam(operator = Operator.equals, column = "modify_table_name")
	private String modifyTableName;
	
	@Override
	public OrderBy getDefaultOrderBy() {
		return OrderBy.asc("modifyFieldName");
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getModifyTableName() {
		return modifyTableName;
	}

	public void setModifyTableName(String modifyTableName) {
		this.modifyTableName = modifyTableName;
	}
	


}
