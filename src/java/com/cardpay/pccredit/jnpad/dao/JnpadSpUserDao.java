package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.CustomerSpUser;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnpadSpUserDao {
	int addSpUser(CustomerSpUser CustomerSpUser);
	int addSpUser1(CustomerSpUser CustomerSpUser);
	List<CustomerSpUser>findspUser();
	List<CustomerSpUser>findspUser2(@Param("capid") String capid);
	List<CustomerSpUser>findspUser3(@Param("capid") String capid);
	CustomerSpUser selectUser(@Param("id") String id);
	List<CustomerSpUser>selectUser1(@Param("id") String id);
}
