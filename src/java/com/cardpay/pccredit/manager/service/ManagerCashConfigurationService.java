package com.cardpay.pccredit.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.filter.ManagerCashConfigurationFilter;
import com.cardpay.pccredit.manager.model.ManagerCashConfiguration;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author chenzhifang
 *
 * 2014-11-18上午11:18:42
 */
@Service
public class ManagerCashConfigurationService {
	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<ManagerCashConfiguration> findManagerCashConfigurationByFilter(ManagerCashConfigurationFilter filter) {
		return commonDao.findObjectsByFilter(ManagerCashConfiguration.class, filter);
	}
}
