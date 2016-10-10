package com.cardpay.pccredit.product.model;

import java.util.Date;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * ProductFilterMap类的描述
 * 
 * @author 王海东
 * @created on 2014-11-26
 * 
 * @version $Id:$
 */
@ModelParam(table = "product_filter_map")
public class ProductFilterMap extends BusinessModel {

	private static final long serialVersionUID = 1L;
	private String productId;
	private String filterDictId;
	private Date createdTime;
	private String createdBy;
	private Date modifiedTime;
	private String modifiedBy;

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getFilterDictId() {
		return filterDictId;
	}

	public void setFilterDictId(String filterDictId) {
		this.filterDictId = filterDictId;
	}

}
