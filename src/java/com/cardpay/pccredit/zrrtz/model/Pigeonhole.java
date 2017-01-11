package com.cardpay.pccredit.zrrtz.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

public class Pigeonhole extends BusinessModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String ywbh;//业务编号
    private String pigeonhole;//是否归档
    private String userId;//客户经理ID
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getYwbh() {
		return ywbh;
	}
	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}
	public String getPigeonhole() {
		return pigeonhole;
	}
	public void setPigeonhole(String pigeonhole) {
		this.pigeonhole = pigeonhole;
	}
    
	}
