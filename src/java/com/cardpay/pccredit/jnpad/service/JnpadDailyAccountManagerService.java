package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.JnpadDailyAccountManagerDao;
import com.cardpay.pccredit.manager.filter.DailyAccountManagerFilter;
import com.cardpay.pccredit.manager.web.DailyAccountManagerForm;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class JnpadDailyAccountManagerService {

	@Autowired
	private JnpadDailyAccountManagerDao jnpadDailyAccountManagerDao;
	
	public QueryResult<DailyAccountManagerForm> findDailyAccountManagersByFilter(DailyAccountManagerFilter filter) {
		List<DailyAccountManagerForm> dailyAccountManagerForm = jnpadDailyAccountManagerDao.findDailyAccountManagersByFilter(filter);
		int size = jnpadDailyAccountManagerDao.findDailyAccountManagersCountByFilter(filter);
		QueryResult<DailyAccountManagerForm> qs = new QueryResult<DailyAccountManagerForm>(size, dailyAccountManagerForm);
		return qs;
	}
}
