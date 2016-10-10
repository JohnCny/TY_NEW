package com.cardpay.pccredit.riskControl.constant;

public enum RiskType {
	turn,//移交 
	collection,//催收移交 
	system,//强制问责
	cancel,//核销 
	Manual,//人为触发（创建方式）
}
