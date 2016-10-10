/**
 * 
 */
package com.cardpay.pccredit.product.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 筛选结果
 *
 * @author Evans zhang
 *
 * 2014-10-23 下午2:13:46
 */
@ModelParam(table = "screening_results")
public class ScreeningResults extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String productId;
	private String customerId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}
