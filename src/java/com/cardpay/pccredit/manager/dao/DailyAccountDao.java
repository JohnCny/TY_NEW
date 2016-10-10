package com.cardpay.pccredit.manager.dao;

import java.util.List;

import com.cardpay.pccredit.manager.filter.DailyAccountManagerFilter;
import com.cardpay.pccredit.manager.web.DailyAccountManagerForm;
import com.wicresoft.util.annotation.Mapper;


/**
 * DailyAccountDao类的描述
 *
 * @author 王海东
 * @created on 2014-11-19
 * 
 * @version $Id:$
 */
@Mapper
public interface DailyAccountDao {
	
	/*
	 * 根据filter查询客户经理日报
	 */
	List<DailyAccountManagerForm> findDailyAccountManagersByFilter(DailyAccountManagerFilter filter);
	
	/*
	 * 根据filter查询客户经理日报条数
	 */
	public int findDailyAccountManagersCountByFilter(DailyAccountManagerFilter filter);

}
