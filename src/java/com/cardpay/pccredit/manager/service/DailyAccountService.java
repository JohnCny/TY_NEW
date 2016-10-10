package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.DailyAccountDao;
import com.cardpay.pccredit.manager.filter.DailyAccountManagerFilter;
import com.cardpay.pccredit.manager.filter.ManagerRiskWarningFilter;
import com.cardpay.pccredit.manager.model.DailyAccountManager;
import com.cardpay.pccredit.manager.web.DailyAccountManagerForm;
import com.cardpay.pccredit.manager.web.ManagerRiskWarningForm;
import com.cardpay.pccredit.sample2.filter.Sample2Filter;
import com.cardpay.pccredit.sample2.model.Sample2;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class DailyAccountService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private DailyAccountDao dailyAccountDao;

	/*
	 * 客户经理日报插入
	 */
	public int insertDailyAccount(DailyAccountManager dailyAccountManager) {
		return commonDao.insertObject(dailyAccountManager);
	}

	/*
	 * 根据filter查询客户经理日报
	 */
	public QueryResult<DailyAccountManagerForm> findDailyAccountManagersByFilter(DailyAccountManagerFilter filter) {
		List<DailyAccountManagerForm> dailyAccountManagerForm = dailyAccountDao.findDailyAccountManagersByFilter(filter);
		int size = dailyAccountDao.findDailyAccountManagersCountByFilter(filter);
		QueryResult<DailyAccountManagerForm> qs = new QueryResult<DailyAccountManagerForm>(size, dailyAccountManagerForm);
		return qs;
	}

	public DailyAccountManager findDailyAccountManagerById(String dailyId) {
		return commonDao.findObjectById(DailyAccountManager.class, dailyId);
	}

	public int updateAccountManager(DailyAccountManager dailyAccountManager) {
		dailyAccountManager.setModifiedTime(Calendar.getInstance().getTime());
		return commonDao.updateObject(dailyAccountManager);
	}
}
