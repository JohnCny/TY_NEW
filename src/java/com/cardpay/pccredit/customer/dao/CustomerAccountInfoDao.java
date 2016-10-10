package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.CustomerAccountInforFilter;
import com.cardpay.pccredit.customer.web.CustomerAccountInfoForm;
import com.wicresoft.util.annotation.Mapper;
/**
 * 
 * 描述 ：客户账户信息
 * @author 张石树
 *
 * 2014-11-5 下午1:50:50
 */
@Mapper
public interface CustomerAccountInfoDao {
	
	/**
	 * 根据过滤条件分页
	 * @param filter
	 * @return
	 */
	public List<CustomerAccountInfoForm> findCusomerAccountInfoByFilter(CustomerAccountInforFilter filter);

	/**
	 * 根据过滤条件统计
	 * @param filter
	 * @return
	 */
	public int findCusomerAccountInfoCountByFilter(CustomerAccountInforFilter filter);

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public CustomerAccountInfoForm findCustomerAccountById(@Param("id") String id);
	
	/**
	 * 根据accountNumber查询
	 * @param accountNumber
	 * @return
	 */
	public CustomerAccountInfoForm findCustomerAccountByaccountNumber(@Param("accountNumber") String accountNumber);
	
	
	/**
	 * 根据cardNumber查询
	 * @param cardNumber
	 * @return
	 */
	public CustomerAccountInfoForm findCustomerAccountByCardNumber(@Param("cardNumber") String cardNumber);

	/**
	 * 根据客户id查询账户
	 * @param customerId
	 * @return
	 */
	public List<CustomerAccountInfoForm> findCustomerAccountByCustomerId(@Param("customerId") String customerId);
	
}
