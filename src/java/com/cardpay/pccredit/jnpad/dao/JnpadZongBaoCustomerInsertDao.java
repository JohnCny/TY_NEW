package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnpadZongBaoCustomerInsertDao {

	//查看抢单列表
	List<CustomerInfo> selectCustomerInfo(@Param (value ="userId") String userId);
	//抢单
	Object updateCustomerInfo(@Param (value ="customerId")String customerId,@Param (value ="userId")String userId);
	
}
