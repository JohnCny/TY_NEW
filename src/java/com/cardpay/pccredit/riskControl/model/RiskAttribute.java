package com.cardpay.pccredit.riskControl.model;

import com.wicresoft.jrad.base.database.id.IDType;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

/**
 * 风险属性
 * @author chenzhifang
 *
 * 2014-10-29下午3:15:23
 */
@ModelParam(table = "risk_attribute",generator=IDType.uuid32)
public class RiskAttribute extends BusinessModel {
	private static final long serialVersionUID = -6310378837012935132L;

	//风险等级(1,2,3…)
	private String riskLevel;
	
	// 风险属性
	private String riskAttribute;
	
	// 描述
	private String riskDes;
	
	// 类型(ONLINE:线上、OFFLINE:线下)
	private String type;
	
	// 参数值
	private String value;

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getRiskAttribute() {
		return riskAttribute;
	}

	public void setRiskAttribute(String riskAttribute) {
		this.riskAttribute = riskAttribute;
	}

	public String getRiskDes() {
		return riskDes;
	}

	public void setRiskDes(String riskDes) {
		this.riskDes = riskDes;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
