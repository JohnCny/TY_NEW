package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.riskControl.filter.RiskCustomerCollectionPlanFilter;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.web.RiskCustomerCollectionPlanForm;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnpadRiskCustomerCollectionDao {
	//通过id得到逾期客户催收计划
	public RiskCustomerCollectionPlanForm findRiskCustomerCollectionPlanById(@Param(value = "id")String id);
	public int findCountByFilter(RiskCustomerCollectionPlanFilter filter);
	//过滤查询逾期催收客户
	public List<RiskCustomerCollectionPlanForm> findRiskCustomerCollectionPlans(
			RiskCustomerCollectionPlanFilter filter);
	
	//得到当前客户经理下属经理名下的逾期客户催收信息数量
	public int findRiskViewSubCollectionPlansCountByFilter(RiskCustomerCollectionPlanFilter filter);
	//风险客户名单
	public List<RiskCustomer> findRiskCustomersByFilter(RiskCustomerFilter filter);
	
	/**
	 * 通过客户经理id得到名下的逾期客户id和name
	 * @param userId
	 * @return
	 */
	List<Dict> getCustomerIdAndName(@Param("userId") String userId);
	/**
	 * 通过客户经理id得到名下的逾期客户id和name数量
	 * @param userId
	 * @return
	 */
	int getCustomerIdAndNameCount(@Param("userId") String userId);
	/**
	 * 添加催收信息
	 * @param filter
	 * @return
	 */
	int InsertCs(RiskCustomerCollectionPlanForm filter);
	
	int updateCs(RiskCustomerCollectionPlanForm filter);
	
	int selectOrCs(RiskCustomerCollectionPlanForm filter);

}
