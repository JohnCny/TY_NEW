package com.cardpay.pccredit.riskControl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.riskControl.model.CUSTOMERBLACKLIST;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface CustormerBlackListDao {
	/**\
	 *筛选黑名单客户
	 * @param cl
	 * @return
	 */
	List <CUSTOMERBLACKLIST> findCustormerBlackList(CUSTOMERBLACKLIST cl);
	
	/**
	 * 添加黑名单客户
	 * @param cl
	 * @return
	 */
	int addCustomerBlackList(@Param("id")String id,@Param("userid")String userid,@Param("customerid")String customerid,@Param("reason")String reason  );
	
	int findCustormerBlackListCount(CUSTOMERBLACKLIST cl);
	int findAllCustormerBlackListCount(CUSTOMERBLACKLIST cl);
	/**
	 * 当前客户经理的黑名单客户
	 * @param cl
	 * @return
	 */
	List <CUSTOMERBLACKLIST> findAllCustormerBlackList(CUSTOMERBLACKLIST cl);
	/**
	 * 移除黑名单
	 * @param cl
	 * @return
	 */
	int deleteByCoustorId(@Param("customerid")String customerid,@Param("userid")String userid);
	/**
	 * 当前客户经理非黑名单客户
	 * @param cl
	 * @return
	 */
	List <CUSTOMERBLACKLIST> findAllNoCustormerBlackList(CUSTOMERBLACKLIST cl);
	int findAllNoCustormerBlackListCount(CUSTOMERBLACKLIST cl);
	
	
	int selectCount(@Param("customerid")String customerid);
}
