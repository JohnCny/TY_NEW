/**   
* 
* 
*/
package com.cardpay.pbccrcReport;

/**   
 * @Title: Constants.java 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 谭文华   
 * @date 2014-12-2 上午11:52:33
 */
public class Constants {
	
	public final static String PBOC_REQUEST_RESULT_0 = "0";
	public final static String PBOC_REQUEST_RESULT_1 = "1";
	public final static String PBOC_REQUEST_RESULT_2 = "2";
	public final static String PBOC_REQUEST_RESULT_3 = "3";
	public final static String PBOC_REQUEST_RESULT_4 = "4";
	public final static String PBOC_REQUEST_RESULT_5 = "5";
	public final static String PBOC_REQUEST_RESULT_6 = "6";
	public final static String PBOC_REQUEST_RESULT_7 = "7";
	
	//征信报告信息标记
	public final static int PBOC_CONTENT_TABLEINDEX = 2;//正文在第二个table
	public final static int PBOC_YH_INFO_TABLEINDEX = 1;//用户信息在正文的第一个子table
	public final static int PBOC_YH_OTHER_INFO_TABLEINDEX = 2;//用户(其他)信息在正文的第二个子table
	public final static int PBOC_PO_INFO_TABLEINDEX = 3;//配偶信息在正文的第三个子table
	public final static int PBOC_JZ_INFO_TABLEINDEX = 4;//居住信息在正文的第四个子table
	public final static int PBOC_ZY_INFO_TABLEINDEX = 5;//职业信息在正文的第五个子table
	public final static int PBOC_XYTS_INFO_TABLEINDEX = 6;//信用提示信息在正文的第六个子table
	public final static int PBOC_YQ_INFO_TABLEINDEX = 7;//逾期信息在正文的第七个子table
	public final static int PBOC_WJQDK_INFO_TABLEINDEX = 8;//未结清贷款信息在正文的第八个子table
	public final static int PBOC_WXHDJK_INFO_TABLEINDEX = 9;//未销户贷记卡信息在正文的第九个子table
	public final static int PBOC_DWDB_INFO_TABLEINDEX = 10;//未销户贷记卡信息在正文的第十个子table
	public final static int PBOC_Query_INFO_TABLEINDEX = 22;//查询记录信息在正文的第20个子table
}
