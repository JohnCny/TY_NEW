/**
 * 
 */
package com.cardpay.pccredit.riskControl.constant;

/**
 * @author shaoming
 *
 * 2014年11月10日   上午9:15:58
 */
public enum RiskCustomerCollectionEndResultEnum {
	reject,// 拒绝还款 
	hang,//挂起
	losecontact,//失联
	collection,//催收中
	continuecollection,//继续催收
	repaymentcommitments,//承诺还款
	complete//催收完成
}
