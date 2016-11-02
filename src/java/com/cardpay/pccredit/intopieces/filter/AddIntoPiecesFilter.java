package com.cardpay.pccredit.intopieces.filter;

import com.wicresoft.jrad.base.database.dao.business.BusinessFilter;

public class AddIntoPiecesFilter extends BusinessFilter{
	
	private String customerId;
    private String productId;
    private String excelId;
    private String imageId;
    private String applicationId;
    
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
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	} 
}
