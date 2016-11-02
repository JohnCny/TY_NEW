package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;
/*
 * 审批记录（审批客户经理、辅调客户经理记录）
 */
@ModelParam(table = "ty_application_log")
public class TyApplicationLog extends BusinessModel {
	
	private static final long serialVersionUID = -8470111754965975277L;
	
	private String userId;//客户经理id
	private String type;//1-审批客户经理，2-辅调客户经理
	private String applicationId;//进件id
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	
	
}