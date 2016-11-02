package com.cardpay.pccredit.product.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * Description of AppendixDict
 * 
 * @author 王海东
 * @created on 2014-10-14
 * 
 * @version $Id:$
 */
@ModelParam(table = "appendix_dict")
public class AppendixDict extends BusinessModel {

	
	private static final long serialVersionUID = 1L;
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
