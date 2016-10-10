package com.cardpay.pccredit.customer.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.AverageDailyOverdraftDao;
import com.cardpay.pccredit.customer.dao.CustomerOverdueHistoryDao;
import com.cardpay.pccredit.customer.model.AverageDailyOverdraft;
import com.cardpay.pccredit.customer.model.CustomerOverdueHistory;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.util.date.DateHelper;

@Service
public class CustomerQuotaService {

	@Autowired
	private CommonDao commonDao;
	@Autowired
	private AverageDailyOverdraftDao averageDailyOverdraftDao;

	@Autowired
	private CustomerOverdueHistoryDao customerOverdueHistoryDao;

	/**
	 * 找出需要降额度的客户
	 */
	public Set<String> downQuotaCustomer(String productId) {

		Set<String> customerIdSet = new HashSet<String>();

		// 连续半年内每月只还最低还款额 （灵活金）用户
		List<AverageDailyOverdraft> downQuotaCustomerList = averageDailyOverdraftDao.findHalfYearAverageDailyOverdraftZDHK(productId);
		// 半年内逾期三次的客户
		List<CustomerOverdueHistory> customerOverdueHistorylist = customerOverdueHistoryDao.findHalfYearOverdueByProductId(productId);
		// 半年内连续两次逾期的客户
		List<CustomerOverdueHistory> customerOverdueHistoryTwolist = customerOverdueHistoryDao.findHalfYearOverdueTwoByProductId(productId);
		// 半年内逾期天数大于60天的客户
		List<CustomerOverdueHistory> customerOverdueHistoryDaylist = customerOverdueHistoryDao.findHalfYearOverdueDayByProductId(productId);
		for (AverageDailyOverdraft averageDailyOverdraft : downQuotaCustomerList) {

			String customerId1 = averageDailyOverdraft.getCustomerId();
			// 半年内逾期三次的客户
			// List<CustomerOverdueHistory> customerOverdueHistorylist =
			// customerOverdueHistoryDao.findHalfYearOverdueByProductId(productId);
			for (CustomerOverdueHistory customerOverdueHistory : customerOverdueHistorylist) {

				String customerId2 = customerOverdueHistory.getCustomerId();
				if (customerId1.equals(customerId2)) {
					customerIdSet.add(customerId1);
				}

			}

			// 半年内连续两次逾期的客户
			// List<CustomerOverdueHistory> customerOverdueHistoryTwolist =
			// customerOverdueHistoryDao.findHalfYearOverdueTwoByProductId(productId);
			String lateDateo = "";
			String customerIdo = "";
			for (CustomerOverdueHistory customerOverdueHistory : customerOverdueHistoryTwolist) {

				String LateDate = customerOverdueHistory.getLateDate();
				String customerId3 = customerOverdueHistory.getCustomerId();
				if (customerId3.equalsIgnoreCase(customerIdo)) {
					Boolean flag = compareDate(lateDateo, LateDate);
					if (flag) {
						if (customerId1.equals(customerId3)) {
							customerIdSet.add(customerId1);
						}
						// customerIdSet.add(customerOverdueHistory.getCustomerId());
					}
				}
				lateDateo = LateDate;
				customerIdo = customerId3;
			}

			// 半年内逾期天数大于60天的客户
			// List<CustomerOverdueHistory> customerOverdueHistoryDaylist =
			// customerOverdueHistoryDao.findHalfYearOverdueDayByProductId(productId);
			for (CustomerOverdueHistory customerOverdueHistory : customerOverdueHistoryDaylist) {

				String customerId4 = customerOverdueHistory.getCustomerId();
				if (customerId1.equals(customerId4)) {
					customerIdSet.add(customerId1);
				}
			}

		}

		return customerIdSet;
	}

	/**
	 * 找出需要升额度的客户
	 */
	public List<AverageDailyOverdraft> upQuotaCustomer(String productId) {
		return averageDailyOverdraftDao.findHalfYearAverageDailyOverdraft(productId);
	}

	/**
	 * 找出最近连续半年内全额还款的用户
	 */
	public List<AverageDailyOverdraft> findHalfYearAllAllDueStatusTrue() {
		List<AverageDailyOverdraft> ado = averageDailyOverdraftDao.findAllDueStatusTrue();
		
		for (int i = 0; i < ado.size(); i++) {
			ado.get(i);
		}
		for (AverageDailyOverdraft averageDailyOverdraft : ado) {
			averageDailyOverdraft.getAccountNumber();
			Integer year = averageDailyOverdraft.getYear();
			Integer month = averageDailyOverdraft.getMonth();
			Date d = StringToDate(year.toString() + month.toString());
		}
		return null;

	}
	
	public boolean checkHalfYearLink(List<String> d) {
		boolean flag = true;
		Calendar rightNow = Calendar.getInstance();
		
		
		return flag;
		
	}
	
	/**
	 * String 转换成Date
	 * @param s
	 * @return
	 */
	public static Date StringToDate(String s) {
		if ("".equals(s)) {
			return null;
		} else {
			Date date = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
				date = sdf.parse(s);
				return date;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 比较两个两个日期的月份是否相邻
	 */
	public Boolean compareDate(String dateString1, String dateString2) {

		if (dateString1 != null && dateString2 != null) {
			Date date1 = DateHelper.getDateFormat(dateString1, "yyyy-MM-dd");
			Date date2 = DateHelper.getDateFormat(dateString2, "yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			int val1 = cal.get(Calendar.MONTH) + 1;
			cal.setTime(date2);
			int val2 = cal.get(Calendar.MONTH) + 1;
			if (Math.abs(val1 - val2) == 1) {
				return true;
			} else {

				return false;
			}
		} else {

			return false;
		}
	}
}
