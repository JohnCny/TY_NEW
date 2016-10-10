package com.cardpay.pccredit.product.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 产品机构中间表
 * 
 * @author 王海东
 * @created on 2014-11-4
 * 
 * @version $Id:$
 */
@ModelParam(table = "products_agency_association")
public class ProductsAgencyAssociation extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String productId;
	private String institution;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

}
