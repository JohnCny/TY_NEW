package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.MonthlyStatisticsModel;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface MonthlyStatisticsDao {
	//添加新客户经理
	void insertMonthlyStatistics(MonthlyStatisticsModel MonthlyStatisticsModel);
	//更新客户经理的贷款信息
	void updateMonthlyStatistics(MonthlyStatisticsModel MonthlyStatisticsModel);
	//查询新客户经理
	List<MonthlyStatisticsModel>selectAllUserId(@Param("customeryeah") Integer customeryeah);
	//查询客户经理月季贷款总额
	List<MonthlyStatisticsModel>selectotalAmountByUserId(@Param("userId") String userId);
	//查询年份以及团队
	List<MonthlyStatisticsModel>selectYear(@Param("userId") String userId);
	//查询当前客户经理的月季贷款信息
	List<MonthlyStatisticsModel>selectUser(@Param("userId") String userId,@Param("customeryeah") Integer customeryeah);
	//查询当前客户经理的总用信额度
	double selecTotalAmount(@Param("userId") String userId);
	//查询当前客户经理是否为小组长
	List<MonthlyStatisticsModel>findxzz(@Param("userId") String userId);
	
	//统计团队月季贷款信息
	MonthlyStatisticsModel selectTeamYear(MonthlyStatisticsModel MonthlyStatisticsModel);
	
	//查询所有的区域以及团队
	List<MonthlyStatisticsModel>selectAllteam();
	//查询所有有贷款的年份
	List<MonthlyStatisticsModel>selectAllYear();
	
	//删除月季表中所有信息
	void deleteAll();
	
	//查询当前客户经理所在团队
	MonthlyStatisticsModel selectUserOnTeam(@Param("userId") String userId);
}
