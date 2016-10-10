/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.wicresoft.jrad.base.auth;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Description of JRadOperationTool
 * 
 * @author Vincent
 * @created on Jun 11, 2014
 * 
 * @version $Id: JRadOperationHelper.java 964 2014-07-03 03:12:27Z Evans $
 */
public class JRadOperationHelper {

	private static Map<Long, String> opsStr2Long = new LinkedHashMap<Long, String>();
	private static Map<String, Long> opsLong2Str = new LinkedHashMap<String, Long>();
	static {
		opsStr2Long.put(JRadOperation.CREATE, "create");
		opsStr2Long.put(JRadOperation.CHANGE, "change");
		opsStr2Long.put(JRadOperation.DISPLAY, "display");
		opsStr2Long.put(JRadOperation.DELETE, "delete");
		opsStr2Long.put(JRadOperation.EXPORT, "export");
		opsStr2Long.put(JRadOperation.BROWSE, "browse");
		opsStr2Long.put(JRadOperation.QUERY, "query");
		opsStr2Long.put(JRadOperation.EXPORT, "export");
		opsStr2Long.put(JRadOperation.REPORT, "report");
		opsStr2Long.put(JRadOperation.CONFIG, "config");
		opsStr2Long.put(JRadOperation.APPROVE, "approve");
		opsStr2Long.put(JRadOperation.ADDBATCH, "addbatch");
		opsStr2Long.put(JRadOperation.IMPORT, "import");
		opsStr2Long.put(JRadOperation.APPROVELOG, "approvelog");
		opsStr2Long.put(JRadOperation.APPROVELOGUSER, "approveloguser");
		opsStr2Long.put(JRadOperation.APPLYAPPROVE, "applyapprove");
		opsStr2Long.put(JRadOperation.EXPORTUPLOAD, "exportupload");
		opsStr2Long.put(JRadOperation.ISSUEDCUSTOMER, "issuedcustomer");
		opsStr2Long.put(JRadOperation.ISSUEDORG, "issuedorg");
		opsStr2Long.put(JRadOperation.DISPLAYACCOUNT, "displayaccount");
		opsStr2Long.put(JRadOperation.MAINTENANCE, "maintenance");
		opsStr2Long.put(JRadOperation.TRANSFER, "transfer");
		opsStr2Long.put(JRadOperation.ADDBLACKLIST, "addblacklist");
		opsStr2Long.put(JRadOperation.AMOUNTADJUSTMENT, "amountadjustment");
		opsStr2Long.put(JRadOperation.DISTRIBUTION, "distribution");
		opsStr2Long.put(JRadOperation.CHECKED, "checked");
		opsStr2Long.put(JRadOperation.REPORTED, "reported");
		opsStr2Long.put(JRadOperation.CONFIRM, "confirm");
		opsStr2Long.put(JRadOperation.REFUSE, "refuse");
		opsStr2Long.put(JRadOperation.CONFIGNODEINTOPIECES, "confignodeintopieces");
		opsStr2Long.put(JRadOperation.CONFIGNODEAMOUNTADJUSTMENT, "confignodeamountadjustment");
		opsStr2Long.put(JRadOperation.PUBLISHED, "published");
		opsStr2Long.put(JRadOperation.COLLECTIONTRANSFER, "collectiontransfer");
		opsStr2Long.put(JRadOperation.APPROVEPASS, "approvepass");
		opsStr2Long.put(JRadOperation.RECONSIDER, "reconsider");
		opsStr2Long.put(JRadOperation.CONFIRMRECONSIDER, "confirmreconsider");
		opsStr2Long.put(JRadOperation.CONFIRMACCOUNTABILITY, "confirmaccountability");
		opsStr2Long.put(JRadOperation.RELEVANCECUSTOMER, "relevancecustomer");
		opsStr2Long.put(JRadOperation.ASSESS, "assess");
		opsStr2Long.put(JRadOperation.SUBTURNOTHER, "subturnother");
		opsStr2Long.put(JRadOperation.FEEDBACK, "feedback");
		
		
		for (Entry<Long, String> entry : opsStr2Long.entrySet()) {
			opsLong2Str.put(entry.getValue(), entry.getKey());
		}
	}
	
	public static List<Long> getOperationNameAll() {
		return  new ArrayList<Long>(opsStr2Long.keySet()); 
	}

	public static String getOperationName(Long operation) {
		return opsStr2Long.get(operation);
	}

	public static List<Long> getOperationNameByoper(Long operation) {
		List<Long> operList = new ArrayList<Long>();
		for (long oper : opsStr2Long.keySet()) {
			if ((oper & operation) == oper) {
				operList.add(oper);
			}
		}
		return operList;
	}

	public static Long getOperationByName(String opName) {
		return opsLong2Str.get(opName);
	}
	
	
	public static void main(String[] args) {
		//System.out.println(JRadOperationHelper.getOperationName(JRadOperation.CREATE));
		getOperationNameAll();
		// for (String str : getOperationNameByoper(32L)) {
		// System.out.println(str);
		// }
		// System.out.println(2%7);
	}
}
