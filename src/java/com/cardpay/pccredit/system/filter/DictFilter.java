package com.cardpay.pccredit.system.filter;

import com.wicresoft.jrad.base.database.dao.query.Operator;
import com.wicresoft.jrad.base.database.dao.query.QueryParam;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * 
 * 描述 ：数据字典过滤类
 * @author 张石树
 *
 * 2014-11-5 下午3:56:29
 */
public class DictFilter extends BaseQueryFilter{
	
	@QueryParam(operator = Operator.contains, column = "dict_type")
	private String dictType;
	
	private String typeCode;
	
	@QueryParam(operator = Operator.contains, column = "type_name")
	private String typeName;

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
