package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import com.cardpay.pccredit.manager.filter.DailyAccountManagerFilter;
import com.cardpay.pccredit.manager.web.DailyAccountManagerForm;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnpadDailyAccountManagerDao {

	//查询当前客户经理日报数量
	public int findDailyAccountManagersCountByFilter(DailyAccountManagerFilter filter) ;

	
	//查询客户经理日报列表
	public List<DailyAccountManagerForm> findDailyAccountManagersByFilter(DailyAccountManagerFilter filter);

	
	
	
}
