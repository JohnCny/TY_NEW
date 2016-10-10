package com.cardpay.pccredit.datapri.constant;

public class DataPriConstants {
	
	//表名
	public static final String JINJIAN_TABLE = "CUSTOMER_APPLICATION_INFO";
	
	public static final String KEHU_TABLE = "BASIC_CUSTOMER_INFORMATION";
	
	public static final String KEHUJINGLI_TABLE = "ACCOUNT_MANAGER_PARAMETER";
	
	public static final String CHANGPIN_TABLE = "PRODUCT_ATTRIBUTE";
	

	public static final String CURRENT_ROLE_ID = "${currentRoleId}";
	
	public static final String CURRENT_DEPT_ID = "${currentDeptId}";
	
	public static final String CURRENT_ORG_ID = "${currentOrgId}";
	
	public static final String CURRENT_USER_ID = "${currentUserId}";
	
	public static final String TEMP_ROLE_REPLACE_VALUE = "'1'";
	
	public static final String TEMP_DEPT_REPLACE_VALUE = "'1'";
	
	public static final String TEMP_ORG_REPLACE_VALUE = "'1'";
	
	public static final String TEMP_USER_REPLACE_VALUE = "'1'";
	
	public final static String ORG_TYPE = "org";
	
	public final static String DEPT_TYPE = "dept";
	
	public final static String SYS_EXCEPTION_MSG="系统异常";
	public final static String CHANGE_SUCCESS="系统异常";
	public final static String VALIDE_SQL_MSG = "请检查拼配置，配置生成sql如： {0}";
	public static final String CURRENT_ROLE_MSG = "角色";
	public static final String CURRENT_DEPT_MSG = "部门";
	public static final String CURRENT_ORG_MSG = "机构";
	public static final String CURRENT_USER_MSG = "用户";
	public static final String TEMP_VALIDATE_SQL = "select count(*) as ct from {0} where 1=2 {1}";
}
