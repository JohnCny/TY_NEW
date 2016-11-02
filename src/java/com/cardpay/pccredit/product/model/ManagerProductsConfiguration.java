package com.cardpay.pccredit.product.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 4.2.28 客户经理-产品-参数配置
 * 
 * @author 王海东
 * @created on 2014-11-10
 * 
 * @version $Id:$
 */

@ModelParam(table = "manager_products_configuration")
public class ManagerProductsConfiguration extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String customerManagerLevel;
	private String creditLine;
	private String marginExtractInfo;
	private String riskToleranceInformation;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCustomerManagerLevel() {
		return customerManagerLevel;
	}

	public void setCustomerManagerLevel(String customerManagerLevel) {
		this.customerManagerLevel = customerManagerLevel;
	}

	public String getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(String creditLine) {
		this.creditLine = creditLine;
	}

	public String getMarginExtractInfo() {
		return marginExtractInfo;
	}

	public void setMarginExtractInfo(String marginExtractInfo) {
		this.marginExtractInfo = marginExtractInfo;
	}

	public String getRiskToleranceInformation() {
		return riskToleranceInformation;
	}

	public void setRiskToleranceInformation(String riskToleranceInformation) {
		this.riskToleranceInformation = riskToleranceInformation;
	}

}
