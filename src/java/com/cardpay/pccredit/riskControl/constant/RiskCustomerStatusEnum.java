package com.cardpay.pccredit.riskControl.constant;

/**
 * @author chenzhifang
 * 
 * 风险客户状态
 *  
 * 2014-10-27下午1:31:41
 */
public enum RiskCustomerStatusEnum {
	UNREPORTED,// 未上报
	REPORTED_CARDCENTER,   // 已上报(上报卡中心)
	CONFIRMED_CARDCENTER,  // 已确认(卡中心确认)
	REJECT_CARDCENTER;     // 拒绝(卡中心拒绝)
	
	/**
	 * 通过当前状态获取上报的下一个状态
	 * @param currentStatus
	 * @return string
	 */
	public static String getReportedNextStatusByCurrent(String currentStatus){
		return REPORTED_CARDCENTER.toString();
	}
	
	/**
	 * 通过当前状态获取确认的下一个状态
	 * @param currentStatus
	 * @return string
	 */
	public static String getConfirmedNextStatusByCurrent(String currentStatus){
		return CONFIRMED_CARDCENTER.toString();
	}
	
	/**
	 * 通过当前状态获取拒绝的下一个状态
	 * @param currentStatus
	 * @return string
	 */
	public static String getRejectNextStatusByCurrent(String currentStatus){
		return REJECT_CARDCENTER.toString();
	}
}
