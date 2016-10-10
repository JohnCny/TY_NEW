package com.cardpay.pccredit.riskControl.constant;

/**
 * @author chenzhifang
 *
 * 2014-10-31下午2:02:26
 */
public enum RiskAttributeEnum {
	// 多次申请
	REPEATEDLY,
	// 多张卡
	MANYCARD,
	// 多次逾期
	MANYTIMESEXPIRY,
	//欺诈
	QZ,
	//法院强制执行
	FYQZZX,
	//不良资产核销
	BLZCKX;
}
