package com.cardpay.pccredit.product.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * Description of AddressAccessories
 * 
 * @author 王海东
 * @created on 2014-10-11
 * 
 * @version $Id:$
 */
@ModelParam(table = "address_accessories")
public class AddressAccessories extends BusinessModel {

	private static final long serialVersionUID = 1L;

	private String applicationId;
	private String productId;
	private String productAccessoryName;
	private String productAccessoriesUrl;
	private String appendixTypeCode;
	private String addressAccessoriesId;

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

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getProductAccessoryName() {
		return productAccessoryName;
	}

	public void setProductAccessoryName(String productAccessoryName) {
		this.productAccessoryName = productAccessoryName;
	}

	public String getProductAccessoriesUrl() {
		return productAccessoriesUrl;
	}

	public void setProductAccessoriesUrl(String productAccessoriesUrl) {
		this.productAccessoriesUrl = productAccessoriesUrl;
	}

	public String getAddressAccessoriesId() {
		return addressAccessoriesId;
	}

	public void setAddressAccessoriesId(String addressAccessoriesId) {
		this.addressAccessoriesId = addressAccessoriesId;
	}

}
