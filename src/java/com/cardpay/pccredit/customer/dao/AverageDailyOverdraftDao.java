package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.AverageDailyOverdraft;
import com.wicresoft.util.annotation.Mapper;


/**
 * AverageDailyOverdraft类的描述
 *
 * @author 王海东
 * @created on 2014-11-26
 * 
 * @version $Id:$
 */
@Mapper
public interface AverageDailyOverdraftDao {
	
	public List<AverageDailyOverdraft> findHalfYearAverageDailyOverdraft(@Param("productId")String productId); //获取六个月内全额还款的用户

	
	public List<AverageDailyOverdraft> findHalfYearAverageDailyOverdraftZDHK(@Param("productId")String productId); //连续半年内每月只还最低还款额 （灵活金）用户

	//查询所有全额还款的用户
	public List<AverageDailyOverdraft> findAllDueStatusTrue();
}


