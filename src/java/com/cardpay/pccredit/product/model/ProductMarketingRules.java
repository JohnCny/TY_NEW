package com.cardpay.pccredit.product.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 产品营销规则
 * 
 * @author 王海东
 * @created on 2014-10-17
 * 
 * @version $Id:$
 */

@ModelParam(table = "product_marketing_rules")
public class ProductMarketingRules extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String marketingTime;
	private String marketingMethod;


	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMarketingTime() {
		return marketingTime;
	}

	public void setMarketingTime(String marketingTime) {
		this.marketingTime = marketingTime;
	}

	public String getMarketingMethod() {
		return marketingMethod;
	}

	public void setMarketingMethod(String marketingMethod) {
		this.marketingMethod = marketingMethod;
	}

}
