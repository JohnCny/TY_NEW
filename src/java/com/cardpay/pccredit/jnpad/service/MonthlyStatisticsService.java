package com.cardpay.pccredit.jnpad.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.jnpad.dao.MonthlyStatisticsDao;
import com.cardpay.pccredit.jnpad.model.MonthlyStatisticsModel;

@Service
public class MonthlyStatisticsService {
	@Autowired
	private MonthlyStatisticsDao commonDao;
	public List<MonthlyStatisticsModel>selectUser(@Param("userId") String userId,@Param("customeryeah") Integer customeryeah){
		return commonDao.selectUser(userId, customeryeah);
	}
	public List<MonthlyStatisticsModel>selectYear(@Param("userId") String userId){
		return commonDao.selectYear(userId);
	}
	public double selecTotalAmount(@Param("userId") String userId){
		return commonDao.selecTotalAmount(userId);
	}
	public List<MonthlyStatisticsModel>findxzz(@Param("userId") String userId){
		return commonDao.findxzz(userId);
	}
	public MonthlyStatisticsModel selectTeamYear(MonthlyStatisticsModel MonthlyStatisticsModel){
		return commonDao.selectTeamYear(MonthlyStatisticsModel);
	}
	public List<MonthlyStatisticsModel>selectAllteam(){
		return commonDao.selectAllteam();
	}
	public List<MonthlyStatisticsModel>selectAllYear(){
		return commonDao.selectAllYear();
	}
	public MonthlyStatisticsModel selectUserOnTeam(@Param("userId") String userId){
		return commonDao.selectUserOnTeam(userId);
	}
}
