package com.cardpay.pccredit.riskControl.constant;

/**
 * 
 * 描述 ：常量类
 * @author 张石树
 *
 * 2014-11-7 下午2:42:24
 */
public class RiskConstants {

	public final static String NPLS_INFO_NEW = "new";
	
	public final static String NPLS_INFO_CONFIRM = "confirm";
	
	public final static String NPLS_INFO_REFUSE = "refuse";
	
	//已核销
	public final static String NPLS_INFO_VERIFICATION = "verification"; 
	
	public final static String NPLS_CREATE_METHOD_AUTO = "auto";
	
	public final static String NPLS_CREATE_METHOD_MANUAL = "manual";

	public static final String NPLS_CONFIRM_MESSAGE = "操作成功";

	public static final String NPLS_CONFIRM_REFUSE = "操作成功";
	
	public static final String NPLS_CONFIRM_TO_RISKCUSTOMER = "不良资产确认生成风险名单";
	
	public static final String NPLS_CONFIRM_TO_ABILITY = "不良资产核销产生"; 
	
	//CMMJXFS-核销条件 	01 	账龄
	public static final String NPLS_INFO_CONDITION_TYPE_1 = "01"; 	
//	CMMJXFS-核销条件 	02 	持卡人死亡
	public static final String NPLS_INFO_CONDITION_TYPE_2 = "02"; 	
//	CMMJXFS-核销条件 	03 	经营不善、破产
	public static final String NPLS_INFO_CONDITION_TYPE_3 = "03"; 	
//	CMMJXFS-核销条件 	04 	违法
	public static final String NPLS_INFO_CONDITION_TYPE_4 = "04"; 	
//	CMMJXFS-核销条件 	05 	诈骗
	public static final String NPLS_INFO_CONDITION_TYPE_5 = "05"; 	
//	CMMJXFS-核销条件 	06 	自然灾害
	public static final String NPLS_INFO_CONDITION_TYPE_6 = "06"; 	
//	CMMJXFS-核销条件 	07 	其他
	public static final String NPLS_INFO_CONDITION_TYPE_7 = "07"; 	
//	CMMJXFS-核销条件 	08 	逾期金额
	public static final String NPLS_INFO_CONDITION_TYPE_8 = "08"; 	
	
	public static final String RISK_LEVEL_ONE = "1";
	
	public static final String RISK_LEVEL_TWO = "2";
	
	public static final String RISK_LEVEL_THREE = "3";
	
	public static final String RISK_LEVEL_FOUR = "4";
	
}
