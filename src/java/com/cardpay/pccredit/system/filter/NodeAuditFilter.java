package com.cardpay.pccredit.system.filter;

import com.wicresoft.jrad.base.database.dao.query.OrderBy;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 产品过滤条件
 * 
 * @author 王海东
 * @created on 2014-10-11
 * 
 * @version $Id:$
 */
public class NodeAuditFilter extends BaseQueryFilter {

	private String productId;
	
	private String nodeType;
	
	private Boolean isDeleted = false;
	
	@Override
	public OrderBy getDefaultOrderBy() {
		return new OrderBy("seqNo", true);
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
