package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.CustomerSpUser;
import com.cardpay.workflow.models.WfStatusQueueRecord;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface JnpadSpUserDao {
	int addSpUser(CustomerSpUser CustomerSpUser);
	int addSpUser1(CustomerSpUser CustomerSpUser);
	List<CustomerSpUser>findspUser();
	List<CustomerSpUser>findspUser2(@Param("capid") String capid);
	List<CustomerSpUser>findspUser3(@Param("capid") String capid);
	void  deleteSpUser(@Param("capid") String capid);
	CustomerSpUser selectUser(@Param("id") String id);
	List<CustomerSpUser>selectUser1(@Param("id") String id);
	void updateObject(WfStatusQueueRecord WfStatusQueueRecord);
	List<CustomerSpUser>findSpHjy(@Param("capid") String capid,@Param("userid") String userid);
	List<CustomerSpUser>findUserResult(@Param("spuserid") String spuserid);
	List<CustomerSpUser>findUserResult1(@Param("capid") String capid);
}
