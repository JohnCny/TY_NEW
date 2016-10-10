/*
 * @(#)NumberFormatHelper.java  Jul 26, 2013
 *
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.common;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Comments of NumberFormatHelper
 * 
 * @author Vincent Yang
 */
public class FormatTool {

	// ￥116,125.50 ==> 116,125.50
	static NumberFormat cashFormat = null;
	// 116,126
	static NumberFormat cashFormat2 = null;
	// ￥116,125
	static NumberFormat cashFormat3 = null;

	static NumberFormat cashFormat4 = null;
	// 获取两位小数
	static NumberFormat cashFormat5 = null;
	// 01,02
	static NumberFormat ballFormat = null;

	static {
		// cashFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
		cashFormat = NumberFormat.getNumberInstance(Locale.CHINA);
		cashFormat.setMaximumFractionDigits(2);
		cashFormat.setMinimumFractionDigits(2);

		cashFormat2 = NumberFormat.getCurrencyInstance(Locale.CHINA);
		cashFormat2.setMaximumFractionDigits(0);

		cashFormat3 = NumberFormat.getNumberInstance(Locale.CHINA);
		cashFormat3.setMaximumFractionDigits(0);

		ballFormat = NumberFormat.getNumberInstance();
		ballFormat.setMaximumIntegerDigits(2);
		ballFormat.setMinimumIntegerDigits(2);

		cashFormat4 = NumberFormat.getNumberInstance();
		cashFormat4.setMaximumFractionDigits(2);
		cashFormat4.setMinimumFractionDigits(2);

		cashFormat5 = NumberFormat.getNumberInstance();
		cashFormat5.setMinimumFractionDigits(2);
	}

	public String fengToYuan(long feng) {
		return cashFormat.format((double) feng / 100);
	}

	public String fengToYuan4(long feng) {
		return cashFormat4.format((double) feng / 100);
	}

	public String fengToYuan4(String feng) {
		long lfeng = NumberUtils.toLong(feng, 0);
		return cashFormat4.format((double) lfeng / 100);
	}

	public String fengToYuan(String feng) {
		long lfeng = NumberUtils.toLong(feng, 0);
		return fengToYuan(lfeng);
	}

	public String fengToYuan2(long feng) {
		return cashFormat3.format((double) feng / 100);
	}

	public String fengToYuan2(String feng) {
		long lfeng = NumberUtils.toLong(feng, 0);
		return fengToYuan2(lfeng);
	}

	public String formatBallNumber(int number) {
		return ballFormat.format(number);
	}

	public String formatCash(long yuan) {
		return cashFormat2.format(yuan);
	}

	public String formatCash(String yuan) {
		long lyuan = NumberUtils.toLong(yuan, 0);
		return cashFormat2.format(lyuan);
	}

	public String formatNumber(int fractionDigits, double value) {
		return NumberFormatUtil.formatNumber(fractionDigits, value); 
	}

	public String formatNumber(int fractionDigits, String value) {
		double doubleValue = NumberUtils.toDouble(value, 0);
		return formatNumber(fractionDigits, doubleValue);
	}

	public String formatNumber(double value) {
		return cashFormat5.format(value);
	}

	// 超过长度截取为*
	public String formatString(String str, int length) {
		if (StringUtils.isNotEmpty(str)) {
			int len = 0;
			for (int i = 0; i < str.length(); i++) {
				char ch = str.charAt(i);
				if (ch > 255) {
					len += 2;
				} else {
					len++;
				}

				if (len > length) {
					return str.substring(0, i) + "*";
				}
			}

		}

		return str;
	}

	// 截取末尾字符
	public String subString(String str, int length) {
		if (StringUtils.isNotEmpty(str) && str.length() > length) {
			return str.substring(0, str.length() - length);
		}
		return str;
	}

	// 截取末尾字符
	public String subString(String str, String... sux) {
		for (String s : sux) {
			if (str.indexOf(s) > 0) {
				return str.replace(s, "");
			}
		}
		return str;
	}

	// 除首尾替换为*
	public String formatString(int size, String str) {
		if (StringUtils.isNotBlank(str)) {
			char[] ch = str.toCharArray();
			// 邮箱
			int index2 = str.indexOf("@");
			index2 = index2 > 0 ? index2 : str.length();
			for (int i = size; i < index2 - size; i++) {
				ch[i] = '*';
			}
			return new String(ch);
		} else
			return str;
	}

	public static String formatMobile(String mobile) {
		if (StringUtils.isNotBlank(mobile) && mobile.length() == 11) {
			String one = mobile.substring(0, 3);
			String two = mobile.substring(3, 7);
			String three = mobile.substring(7, 11);
			return one + "-" + two + "-" + three;
		} else {
			return mobile;
		}
	}

	public static Object trimEmptyTo(Object object, String value) {
		if (object == null) {
			return value;
		}

		return object;
	}
	
	public static String formatNumber(Object object, Integer length,Integer multiple) {
		if (object != null && NumberUtils.isNumber(object.toString())) {
			return NumberFormatUtil.formatNumber(length, Double.parseDouble(object.toString())/multiple); 
		} else {
			if(object==null){
				return "";
			} else {
				return object.toString();
			}
		}
	}

	public static void main(String[] args) {
		FormatTool tool = new FormatTool();

		System.out.println(tool.formatNumber(2, 123456750));

		System.out.println(tool.fengToYuan(123456750));
		System.out.println(tool.fengToYuan2(123456789));

		System.out.println(tool.formatCash(123456789));
		System.out.println(tool.fengToYuan4(123456789));

		System.out.println(tool.formatString(1, "vinyy@wicresoft.com"));
	}

}
