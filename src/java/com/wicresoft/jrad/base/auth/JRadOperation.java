/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.wicresoft.jrad.base.auth;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description of Action
 * 
 * @author Vincent
 * @created on May 7, 2014
 * 
 * @version $Id: JRadOperation.java 814 2014-06-18 02:45:33Z vincent $
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JRadOperation {

	long value() default 0;

	public static final long CREATE = 1;

	public static final long CHANGE = 2;

	public static final long DISPLAY = 4;

	public static final long DELETE = 8;

	public static final long EXPORT = 16;

	public static final long BROWSE = 32;

	public static final long QUERY = 64;

	public static final long REPORT = 128;

	public static final long CONFIG = 256;

	public static final long APPROVE = 512;
	
	public static final long ADDBATCH = 1024;
	
	public static final long IMPORT = 2048;
	
	public static final long APPROVELOGUSER = 4096;
	
	public static final long APPROVELOG = 8192;
	
	public static final long APPLYAPPROVE = 16384;
	
	public static final long EXPORTUPLOAD = 32768;
	
	public static final long ISSUEDCUSTOMER = 65536;
	
	public static final long ISSUEDORG = 131072;
	
	public static final long DISPLAYACCOUNT = 262144;
	
	public static final long MAINTENANCE = 524288;
	
	public static final long TRANSFER = 1048576;
	
	public static final long ADDBLACKLIST = 2097152;
	
	public static final long AMOUNTADJUSTMENT = 4194304;
	
	public static final long DISTRIBUTION = 8388608;
	
	public static final long CHECKED = 16777216;
	
	public static final long REPORTED= 33554432;
	
	public static final long CONFIRM= 67108864;
	
	public static final long REFUSE= 134217728;
	
	public static final long CONFIGNODEINTOPIECES = 268435456;
	
	public static final long CONFIGNODEAMOUNTADJUSTMENT = 536870912;
	
	public static final long PUBLISHED = 1073741824;
	
	public static final long COLLECTIONTRANSFER = 2147483648L;
	
	public static final long APPROVEPASS = 4294967296L;
	
	public static final long RECONSIDER = 8589934592L;
	
	public static final long CONFIRMRECONSIDER = 17179869184L;
	
	public static final long CONFIRMACCOUNTABILITY = 34359738368L;
	
	public static final long RELEVANCECUSTOMER = 68719476736L;
	
	public static final long ASSESS = 137438953472L;
	
	public static final long SUBTURNOTHER = 274877906944L;
	
	public static final long FEEDBACK = 549755813888L;
	
	
}
