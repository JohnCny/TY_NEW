package com.cardpay.pccredit.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.filter.WeeklyAccountManagerFilter;
import com.cardpay.pccredit.manager.web.WeeklyAccountManagerForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface WeeklyAccountDao {
	
	public List<WeeklyAccountManagerForm> findWeeklyAccountByManagerId(@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("customerManagerId")String customerManagerId);

	/*
	 * 根据filter查询客户经理周报
	 */
	List<WeeklyAccountManagerForm> findWeeklyAccountManagersByFilter(WeeklyAccountManagerFilter filter);
	
	/*
	 * 根据filter查询客户经理周报数量
	 */
	public int findWeeklyAccountManagersCountByFilter(WeeklyAccountManagerFilter filter);
	
	/*
	 * 根据filter查询登录经理下属的客户经理周报
	 */
	List<WeeklyAccountManagerForm> 	findSubWeeklyAccountManagersByFilter(WeeklyAccountManagerFilter filter);
	
	/*
	 * 根据filter查询登录经理下属的客户经理周报数量
	 */
	public int findSubWeeklyAccountManagersCountByFilter(WeeklyAccountManagerFilter filter);
	
	/*
	 * 根据filter查询登录经理所属机构的所有客户经理周报
	 */
	List<WeeklyAccountManagerForm> 	findOrgSubWeeklyAccountManagersByFilter(WeeklyAccountManagerFilter filter);
	
	/*
	 *  根据filter查询登录经理所属机构的所有客户经理周报数量
	 */
	public int findOrgSubWeeklyAccountManagersCountByFilter(WeeklyAccountManagerFilter filter);
	
	

}
