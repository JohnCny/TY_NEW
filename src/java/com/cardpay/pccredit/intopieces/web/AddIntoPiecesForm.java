package com.cardpay.pccredit.intopieces.web;

import com.wicresoft.jrad.base.database.model.BaseModel;

public class AddIntoPiecesForm extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	private String customerId;
    private String productId;
    private String excelId;
    private String imageId;
    
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getExcelId() {
		return excelId;
	}
	public void setExcelId(String excelId) {
		this.excelId = excelId;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	} 
}
