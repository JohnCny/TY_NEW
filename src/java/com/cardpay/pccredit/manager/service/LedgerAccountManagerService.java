package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.LedgerAccountManagerDao;
import com.cardpay.pccredit.manager.filter.DailyAccountManagerFilter;
import com.cardpay.pccredit.manager.filter.LedgerAccountManagerFilter;
import com.cardpay.pccredit.manager.model.LedgerAccountManager;
import com.cardpay.pccredit.manager.web.DailyAccountManagerForm;
import com.cardpay.pccredit.manager.web.LedgerAccountManagerForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * LedgerAccountManagerService类的描述
 * 
 * @author 王海东
 * @created on 2014-11-19
 * 
 * @version $Id:$
 */
@Service
public class LedgerAccountManagerService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private  LedgerAccountManagerDao ledgerAccountManagerDao;

	public String insertLedgerAccountManager(LedgerAccountManager ledgerAccountManager) {
		ledgerAccountManager.setCreatedTime(Calendar.getInstance().getTime());
		commonDao.insertObject(ledgerAccountManager);
		return ledgerAccountManager.getId();
	}

	public int updateLedgerAccountManager(LedgerAccountManager ledgerAccountManager) {
		ledgerAccountManager.setModifiedTime(Calendar.getInstance().getTime());
		return commonDao.updateObject(ledgerAccountManager);
	}

	public int deleteLedgerAccountManager(String ledgerId) {
		return commonDao.deleteObject(LedgerAccountManager.class, ledgerId);
	}

	public LedgerAccountManager findLedgerAccountManagerById(String ledgerId) {
		return commonDao.findObjectById(LedgerAccountManager.class, ledgerId);
	}

	/*
	 * 根据filter查询客户经理日报
	 */
	public QueryResult<LedgerAccountManagerForm> findLedgerAccountManagersByFilter(LedgerAccountManagerFilter filter) {
		List<LedgerAccountManagerForm> ledgerAccountManagerForm = ledgerAccountManagerDao.findLedgerAccountManagersByFilter(filter);
		int size = ledgerAccountManagerDao.findLedgerAccountManagersCountByFilter(filter);
		QueryResult<LedgerAccountManagerForm> qs = new QueryResult<LedgerAccountManagerForm>(size, ledgerAccountManagerForm);
		return qs;
	}

}
