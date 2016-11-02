package com.cardpay.pccredit.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.WeeklyAccountDao;
import com.cardpay.pccredit.manager.filter.WeeklyAccountManagerFilter;
import com.cardpay.pccredit.manager.model.WeeklyAccountManager;
import com.cardpay.pccredit.manager.web.WeeklyAccountManagerForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class WeeklyAccountService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private WeeklyAccountDao weeklyAccountDao;
	/*
	 * 客户经理周报插入
	 */
	public int insertWeeklyAccount(WeeklyAccountManager weeklyAccountManager){
		return commonDao.insertObject(weeklyAccountManager);
	}
	
	/**
	 * 根据客户经理查询周报信息
	 */
	public List<WeeklyAccountManagerForm> findWeeklyAccountByManagerId(String managerId,String startTime,String endTime){
		return weeklyAccountDao.findWeeklyAccountByManagerId(managerId,startTime,endTime);
	}
	
	/*
	 * 客户经理周报修改
	 */
	public int updateWeeklyAccount(WeeklyAccountManager weeklyAccountManager){
		weeklyAccountManager.setModifiedTime(new Date());
		return commonDao.updateObject(weeklyAccountManager);
	}
	
	/*
	 * 根据filter查询客户经理周报
	 */
	public QueryResult<WeeklyAccountManagerForm> findWeeklyAccountManagersByFilter(WeeklyAccountManagerFilter filter) {
		List<WeeklyAccountManagerForm> weeklyAccountManagerForm = weeklyAccountDao.findWeeklyAccountManagersByFilter(filter);
		int size = weeklyAccountDao.findWeeklyAccountManagersCountByFilter(filter);
		QueryResult<WeeklyAccountManagerForm> qs = new QueryResult<WeeklyAccountManagerForm>(size, weeklyAccountManagerForm);
		return qs;
	}
	
	/*
	 * 根据filter查询下属客户经理周报
	 */
	public QueryResult<WeeklyAccountManagerForm> findSubWeeklyAccountManagersByFilter(WeeklyAccountManagerFilter filter) {
		List<WeeklyAccountManagerForm> weeklyAccountManagerForm = weeklyAccountDao.findSubWeeklyAccountManagersByFilter(filter);
		int size = weeklyAccountDao.findSubWeeklyAccountManagersCountByFilter(filter);
		QueryResult<WeeklyAccountManagerForm> qs = new QueryResult<WeeklyAccountManagerForm>(size, weeklyAccountManagerForm);
		return qs;
	}
	/*
	 * 根据filter查询登录经理所属机构的所有客户经理周报
	 */
	public QueryResult<WeeklyAccountManagerForm> findOrgSubWeeklyAccountManagersByFilter(WeeklyAccountManagerFilter filter) {
		List<WeeklyAccountManagerForm> weeklyAccountManagerForm = weeklyAccountDao.findOrgSubWeeklyAccountManagersByFilter(filter);
		int size = weeklyAccountDao.findOrgSubWeeklyAccountManagersCountByFilter(filter);
		QueryResult<WeeklyAccountManagerForm> qs = new QueryResult<WeeklyAccountManagerForm>(size, weeklyAccountManagerForm);
		return qs;
	}
	
	public WeeklyAccountManager findWeeklyAccountManagerById(String weekId) {
		return commonDao.findObjectById(WeeklyAccountManager.class, weekId);
	}
}
