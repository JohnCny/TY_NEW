package com.cardpay.pccredit.customer.dao;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.CustomerMainManager;
import com.wicresoft.util.annotation.Mapper;

/**
 * CustomerMainManagerDao类的描述
 * 
 * @author 王海东
 * @created on 2014-11-3
 * 
 * @version $Id:$
 */
@Mapper
public interface CustomerMainManagerDao {

	public CustomerMainManager findCustomerMainManagerByCustomerId(@Param("customerId") String customerId);
	
	/**
	 * 根据进件ID获取台账信息
	 * @param applicationId
	 * @return
	 */
	
	public CustomerMainManager findCustomerMainManagerByApplicationId(@Param("applicationId") String applicationId);
	
	public int updateCustomerManinManagerByCustomerId(CustomerMainManager customerMainManager);

}
