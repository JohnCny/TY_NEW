package com.cardpay.pccredit.intopieces.constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	/*还款结束*/
	public static String REPAYEND = "repayEnd";//太原复用

	/* 操作失败 */
	public static String FAIL_MESSAGE = "操作失败";
	
	/* 操作成功 */
	public static String SUCCESS_MESSAGE = "操作成功";
	
	/* 操作成功 */
	public static String UPLOAD_SUCCESS_MESSAGE = "导出并上传成功";
	
	/* 文件为空 */
	public static String FILE_EMPTY = "文件不可为空";
	
	/* 结果集 */
	public static String RESULT_LIST1 = "resultList1";
	
	/* 结果集 */
	public static String RESULT_LIST2 = "resultList2";
	
	/* 影像资料上传路径 */
	public static String FILE_PATH = "/usr/pccreditFile/";
	//public static String FILE_PATH = "/home/sealy/TFile/";
	
	/* 影像资料补扫上传路径 */
	public static String FILE_PATH_BS = "/usr/pccreditFilebs/";
	//public static String FILE_PATH_BS = "D://tp//";
	
	/* 保存进件*/
	public static String SAVE_INTOPICES = "save";
	
	/* 申请进件*/
	public static String APPROVE_INTOPICES = "audit";  //太原复用
	
	/* 拒绝进件*/
	public static String REFUSE_INTOPICES = "refuse";//太原复用
	
	/* 申请已通过*/
	public static String APPROVED_INTOPICES = "approved";//太原复用
	
	/* 成功进件*/
	public static String SUCCESS_INTOPICES = "success";//太原复用
	
	/* 申请未通过-补充调查*/
	public static String NOPASS_REPLENISH_INTOPICES = "nopass_replenish";//太原复用
	
	/* 申请未通过-重新调查*/
	public static String NOPASS_RE_INTOPICES = "nopass_re";//太原复用
	
	/*放款成功*/
	public static String END = "end";//太原复用
	
	/*以下是上传状态*/
	public static String INITIAL_INTOPICES="initial";
	
	public static String  EXPORT_INTOPICES="export";
	
	public static String  UPLOAD_INTOPICES="upload";
	
	
	
	
	
	/*联系人*/
	public static String CONTACTID = "contactId";
	
	/*担保人*/
	public static String GUARANTORID = "guarantorId";
	
    /*推荐人*/
	public static String RECOMMENDID = "recommendId";
	
	/**
	 * 定时生成 默认用户
	 */
	public static String SCHEDULES_SYSTEM_USER = "system";
	
	
	/*FTP链接配置*/
	public static String FTPIP = "11.23.11.43";
	
	public static String FTPPORT = "21";
	
	public static String FTPUSERNAME = "root";
	
	public static String FTPPASSWORD = "abc,123";
	
	public static String FTPPATH = "qiankang";
	
	
	/*进件审批记录*/
	public static String APPSP= "1";//审批客户经理
	public static String APPFD= "2";//辅调客户经理
	/*档案流程属性*/
	public static String RECORD_APPLY = "1";//已申请
	public static String RECORD_OUT = "2";//未归还
	public static String RECORD_IN = "3";//已归还
	public static String RECORD_UN = "4";//尚未归类
	
	/*拒绝阶段*/
	public static String REFUSE_1 = "上会阶段";//上会阶段
	public static String REFUSE_2 = "申请表阶段";//申请表阶段
	public static String REFUSE_3 = "征信阶段";//征信阶段
	public static String REFUSE_4 = "调查阶段";//调查阶段
	public static String REFUSE_5 = "做表阶段";//做表阶段
	
	public static Map<Integer,String> ATT_BATCH_1 = new HashMap<Integer,String>(){{
		/*put(1,"合同扫描件");
		put(2,"贷款申请表");
		put(4,"调查报告");
		put(8,"征信查询授权书");
		put(16,"工作底稿");
		put(32,"信用报告及联网核查");
		put(64,"贷审小组决议表");
		put(128,"规范操作承诺书");
		put(256,"收入证明文件");
		put(512,"借款人资产文件、住址证明");
		put(1024,"借款人及共同借款人身份证复印件");
		put(2048,"借款人及共同借款人婚姻状况证明");
		put(4096,"担保人及配偶身份证明复印件");
		put(8192,"担保人及配偶婚姻状况说明");
		put(16384,"担保人收入证明");
		put(1073741824,"其他");*/
		put(1,"照片1");
		put(2,"照片2");
		put(4,"照片");
		put(8,"照片3");
		put(16,"照片4");
		put(32,"照片5");
		put(64,"照片");
		put(128,"照片");
		put(256,"照片");
		put(512,"照片");
		put(1024,"照片");
		put(2048,"照片");
		put(4096,"照片");
		put(8192,"照片");
		put(16384,"照片");
		put(1073741824,"照片");
	}};
	
}
