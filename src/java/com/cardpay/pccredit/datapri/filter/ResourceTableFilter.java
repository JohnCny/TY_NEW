package com.cardpay.pccredit.datapri.filter;

import com.wicresoft.jrad.base.database.dao.query.Operator;
import com.wicresoft.jrad.base.database.dao.query.OrderBy;
import com.wicresoft.jrad.base.database.dao.query.QueryParam;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

public class ResourceTableFilter extends BaseQueryFilter{

	@QueryParam(operator = Operator.contains, column = "res_tbl")
	private String resTbl;
	
	@QueryParam(operator = Operator.contains, column = "res_tbl_cn")
	private String resTblCn;
	
	@Override
	public OrderBy getDefaultOrderBy() {
		return OrderBy.desc("createdTime");
	}

	public String getResTbl() {
		return resTbl;
	}

	public void setResTbl(String resTbl) {
		this.resTbl = resTbl;
	}

	public String getResTblCn() {
		return resTblCn;
	}

	public void setResTblCn(String resTblCn) {
		this.resTblCn = resTblCn;
	}
}
