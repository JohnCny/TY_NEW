package com.cardpay.pccredit.riskControl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.riskControl.filter.RiskCustomerCollectionPlanFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlansAction;
import com.cardpay.pccredit.riskControl.web.RiskCustomerCollectionPlanForm;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author shaoming
 *
 * 2014年11月6日   下午3:33:58
 */
@Mapper
public interface RiskCustomerCollectionDao {
	 /**
	  * 过滤查询逾期催收客户
	  * @param filter
	  * @return
	  */
	List<RiskCustomerCollectionPlanForm> findRiskCustomerCollectionPlans(RiskCustomerCollectionPlanFilter filter);
	/**
	 * 得到当前客户经理下属经理名下的逾期客户催收信息数量
	 * @param filter
	 * @return
	 */
	int findRiskViewSubCollectionPlansCountByFilter(RiskCustomerCollectionPlanFilter filter);
	/**
	 * 过滤查询逾期催收客户(下属)
	 * @param filter
	 * @return
	 */
	List<RiskCustomerCollectionPlanForm> findRiskViewSubCollectionPlansByFilter(RiskCustomerCollectionPlanFilter filter);
	/**
	 * 查询符合条件的记录数
	 * @param filter
	 * @return
	 */
	int findCountByFilter(RiskCustomerCollectionPlanFilter filter);
	/**
	 * 通过id得到催收计划
	 * @param id
	 * @return
	 */
	RiskCustomerCollectionPlanForm findRiskCustomerCollectionPlanById(@Param("id") String id);
	/**
	 * 通过催收计划id得到该计划下的所有实施信息
	 * @param collectionPlanId
	 * @return
	 */
	List<RiskCustomerCollectionPlansAction> findRiskCustomerCollectionPlansActionByCollectionPlanId(@Param("collectionPlanId") String collectionPlanId);
	/**
	 * 通过客户经理id得到名下的逾期客户id和name
	 * @param userId
	 * @return
	 */
	List<Dict> getCustomerIdAndName(@Param("userId") String userId);
	/**
	 * 通过客户id得到名下的产品id和name
	 * @param customerId
	 * @return
	 */
	List<Dict> getProductIdAndName(@Param("customerId") String customerId);
	
	
	
	/**
	 * 检查催收计划是否有重复
	 * @param customerId
	 * @param productId
	 * @param endResult
	 * @return
	 */
	public int checkCollectionPlan(@Param("customerId") String customerId,@Param("productId") String productId,@Param("endResult1") String endResult1,@Param("endResult2") String endResult2);
	/**
	 * 统计客户催收计划条数
	 * @param customerManagerId
	 * @param result
	 * @param start
	 * @param end
	 * @return
	 */
	public int findCollectionCountToday(@Param("customerManagerId") String customerManagerId,@Param("result") String result,@Param("startTime") String start,@Param("endTime") String end);
	/**
	 * 统计七日预期还款条数
	 * @param customerManagerId
	 * @param result
	 * @param start
	 * @param end
	 * @return
	 */
	public int findCollectionPromiseCountByDay(@Param("customerManagerId") String customerManagerId,@Param("result") String result,@Param("startTime") String start,@Param("endTime") String end);
}
