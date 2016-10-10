package com.cardpay.pccredit.product.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * Description of AccessoriesList
 * 
 * @author 王海东
 * @created on 2014-10-11
 * 
 * @version $Id:$
 */
@ModelParam(table = "accessories_list")
public class AccessoriesList extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String productId;
	private String appendixTypeCode;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAppendixTypeCode() {
		return appendixTypeCode;
	}

	public void setAppendixTypeCode(String appendixTypeCode) {
		this.appendixTypeCode = appendixTypeCode;
	}

}
