package com.cardpay.pccredit.riskControl.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-10-27下午3:44:53
 */
@Mapper
public interface RiskCustomerDao {
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<RiskCustomer> findRiskCustomersByFilter(RiskCustomerFilter filter);
	
	/**
	 * 统计风险客户数
	 * @param filter
	 * @return
	 */
	public int findRiskCustomersCountByFilter(RiskCustomerFilter filter);
	
	/**
	 * 过滤查询客户的风险等级
	 * @param filter
	 * @return
	 */
	public List<RiskCustomer> findRiskListByFilter(RiskCustomerFilter filter);
	
	/**
	 * 统计客户的风险等级
	 * @param filter
	 * @return
	 */
	public int findRiskListCountByFilter(RiskCustomerFilter filter);
	
	
	/**
	 * 过滤查询客户的风险等级根据customerId
	 * @param filter
	 * @return
	 */
	public List<RiskCustomer> findRiskCustomerBycustomerId(@Param("customerId")String customerId);
	

}
