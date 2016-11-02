package com.cardpay.pccredit.manager.dao;

import org.apache.ibatis.annotations.Param;

import com.wicresoft.util.annotation.Mapper;
/**
 * 
 * @author 季东晓
 *
 * 2014-12-11 下午2:26:40
 */
@Mapper
public interface ManagerCustomerTypeDao {
	
	public void deleteManagerCustomerType(@Param("levelId") String levelId);

}
