package com.cardpay.pccredit.product.filter;

import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 产品过滤条件
 * 
 * @author 王海东
 * @created on 2014-10-11
 * 
 * @version $Id:$
 */
public class AppendixDictFilter extends BaseQueryFilter {

	private String typeName;
	private String typeCode;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

}
