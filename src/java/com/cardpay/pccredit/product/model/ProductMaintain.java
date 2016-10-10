package com.cardpay.pccredit.product.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * ProductAccountability类的描述
 * 
 * @author 王海东
 * @created on 2014-11-18
 * 
 * @version $Id:$
 */
@ModelParam(table = "product_maintain")
public class ProductMaintain extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String day;
	private String maintenanceWay;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getMaintenanceWay() {
		return maintenanceWay;
	}

	public void setMaintenanceWay(String maintenanceWay) {
		this.maintenanceWay = maintenanceWay;
	}

}
