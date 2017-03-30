package com.cardpay.pccredit.toolsjn;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestClearLog {
	public static void dailyTestClearLog() throws IOException {
		// 读取文件,插入数据到表里
		// String log_path = "/home/log/";
		String log_path = "F:/home/log/";
		File file = new File(log_path);
		File[] tempList = file.listFiles();
		// System.out.println("该目录下对象个数"+tempList.length);
		for (int i = 0; i < tempList.length; i++) {
			String[] str = tempList[i].getName().split("\\.");
			
			if (str.length != 2) {// jradbaseweb.log
				String dateString = str[2];
				//System.out.println(tempList[i].getName() + "--" + dateString);
				if (CheckIsTrue(dateString)) {
					//删除该文件
					String path = log_path + "jradbaseweb.log." + dateString;
					//System.out.println("要删除的文件是："+path);
					File f = new File(path);
					//System.out.println(f.exists());
					f.delete();
				}
			}
		}
	}

	private static Boolean CheckIsTrue(String dateString) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateNow = format.format(new Date());
		Map<Integer, Integer> map = getMonthAndDaysBetweenDate(dateString,
				dateNow);
		int months = map.get(1).intValue();//月数
		//System.out.println("相差的月份数：" + months);
		if (months >= 1) {//  相差3个月
			return true;
		}
		return false;
	}

	public static Map<Integer, Integer> getMonthAndDaysBetweenDate(
			String date1, String date2) {
		Map<Integer, Integer> map = new HashMap();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = null;
		try {
			d1 = sd.parse(date1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date d2 = null;
		try {
			d2 = sd.parse(date2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int months = 0;// 相差月份
		int days = 0;
		int y1 = d1.getYear();
		int y2 = d2.getYear();
		int dm1 = d2.getMonth();// 起始日期月份
		int dm2 = d2.getMonth();// 结束日期月份
		int dd1 = d1.getDate(); //起始日期天
		int dd2 = d2.getDate(); // 结束日期天
		if (d1.getTime() < d2.getTime()) {
			months = d2.getMonth() - d1.getMonth() + (y2 - y1) * 12;
			if (dd2 < dd1) {
				months = months - 1;
			}
			/*
			 * System.out.println(getFormatDateTime(addMonthsToDate(DateUtil.
			 * parseDate(date1, "yyyy-MM-dd"),months),"yyyy-MM-dd"));
			 * days=getDaysBetweenDate
			 * (getFormatDateTime(addMonthsToDate(DateUtil.parseDate(date1,
			 * "yyyy-MM-dd"),months),"yyyy-MM-dd"),date2);
			 */
			map.put(1, months);
			map.put(2, days);
		}
		return map;
	}
}
