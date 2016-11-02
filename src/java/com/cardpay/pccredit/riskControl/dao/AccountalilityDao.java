package com.cardpay.pccredit.riskControl.dao;

import org.apache.ibatis.annotations.Param;

import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-10-27下午3:44:53
 */
@Mapper
public interface AccountalilityDao {
	
	public void autoAccountalilityEnd(@Param("maxDay")String maxDay);
}
