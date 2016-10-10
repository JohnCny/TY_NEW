/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.cardpay.pccredit.common;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Description of NumberFormatUtil
 * 
 * @author Vincent
 * @created on Jan 14, 2014
 * 
 * @version $Id: NumberFormatUtil.java 1932 2014-03-20 06:20:48Z vincent $
 */
public class NumberFormatUtil {

	static List<NumberFormat> nfs = new ArrayList<NumberFormat>();

	static {
		for (int i = 0; i <= 10; i++) {
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(i);
			nf.setMinimumFractionDigits(i);
			nf.setGroupingUsed(false);
			nfs.add(nf);
		}
	}

	/**
	 * 小数位数只能0-10
	 * 
	 * @param fractionDigits
	 * @param value
	 * @return
	 */
	public static String formatNumber(int fractionDigits, double value) {
		if (fractionDigits >= 0 && fractionDigits <= 10) {
			return nfs.get(fractionDigits).format(value);
		}

		throw new RuntimeException("Only supports fraction digits between 0 to 10");
	}

}
