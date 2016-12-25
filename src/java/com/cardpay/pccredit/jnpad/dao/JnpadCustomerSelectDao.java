package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.jnpad.model.CIPERSONBASINFO;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.wicresoft.util.annotation.Mapper;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
@Mapper
public interface JnpadCustomerSelectDao {

	
	//根据证件号码查客户信息
	public CustomerInfo selectCustomerInforByCardId(@Param(value = "cardId") String cardId);
	//根据证件和经理id
	public CustomerInfo selectCustomerInforByCardIdAndUserId(@Param(value = "cardId") String cardId,@Param(value = "userId") String userId);
	
	//根据id查客户信息
	public CIPERSONBASINFO selectCustomerInfoById(@Param(value = "id")String id);

	public CIPERSONBASINFO selectCustomerByNm(@Param(value = "custid")String custid);
	//根据姓名和经理Id查询
	public List<CustomerInfo> selectCustomerInfo(@Param(value = "cardId")String cardId,@Param(value = "chineseName")String chineseName, @Param(value = "userId")String userId);
	//当前客户没有申请荣耀卡的客户
	public List<CustomerInfo> selectByserIdOnRy( @Param(value = "userId")String userId,@Param(value = "ProductName")String ProductName);
	
	//当前客户没有申请荣耀卡的客户
	int selectByserIdOnRyCount( @Param(value = "userId")String userId,@Param(value = "ProductName")String ProductName);
	
	public List<CustomerInfo> selectCusByUser(@Param(value = "userId")String userId);
	
	public List<CustomerInfo> selectAllcustormerId(@Param(value = "userId")String userId);
	
	public int findCount(@Param(value = "ID")String ID,@Param(value = "status")String status);

	public int findNoSQCount(@Param(value = "userId")String userId);

}