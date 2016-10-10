package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.CustomerMarketingFilter;
import com.cardpay.pccredit.customer.model.CustomerMarketingPlan;
import com.cardpay.pccredit.customer.model.CustomerMarketingWeb;
import com.cardpay.pccredit.customer.model.MarketingPlanWeb;
import com.cardpay.pccredit.product.model.ProductMarketingRules;
import com.wicresoft.util.annotation.Mapper;

/**
 * 
 * @author 姚绍明
 *
 */
@Mapper
public interface CustomerMarketingDao {
	/**
	 * By营销计划id,得到相应的营销实施信息
	 * @param marketingPlanId
	 * @return
	 */
	public List<CustomerMarketingPlan> findCustomerMarketingPlansByMarketingPlanId(@Param("id") String marketingPlanId);
	/**
	 * 得到营销计划信息
	 * @param id
	 * @return
	 */
	public CustomerMarketingWeb findCustomerMarketingById(@Param("id")String id);
	/**
	 * 通过营销计划id得到客户经理id
	 * @param id
	 * @return
	 */
	public String findCustomerManagerIdByMarketingId(@Param("id") String id);
	/**
	 * 得到下属客户经理名下客户的营销计划信息
	 * @param ids
	 * @return
	 */
	public List<MarketingPlanWeb> findSubMarketingPlan(CustomerMarketingFilter filter);
	/**
	 * 得到下属客户经理名下客户的营销计划信息数量
	 * @param ids
	 * @return
	 */
	public int findSubMarketingPlanCountsByIds(List<String> ids);
	/**
	 * 通过产品Id获取营销规则
	 */
	public ProductMarketingRules findProductMarketingRulesByProductId(@Param("productId")String productId);
	/**
	 * 统计今日营销计划数量
	 * @return
	 */
	public int findMarketingCountToday(@Param("userId") String userId);
	/**
	 * 统计营销计划数量
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public int findMarketingCountByDay(@Param("userId") String userId,@Param("start") String start,@Param("end") String end);
}
