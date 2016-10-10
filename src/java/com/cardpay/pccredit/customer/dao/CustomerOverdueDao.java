package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.CustomerOverdueFilter;
import com.cardpay.pccredit.customer.model.CustomerOverdue;
import com.cardpay.pccredit.riskControl.web.CustomerOverdueForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-11-3下午4:05:59
 */
@Mapper
public interface CustomerOverdueDao {

	/*
     * 查询符合条件的记录数
     */
    public int findCountByFilter(CustomerOverdueFilter filter);
    
    /**
	 * 过滤查询逾期客户数量
	 * @param filter
	 * @return
	 */
    public int findCustomerOverdueCount(CustomerOverdueFilter filter);
    
    /**
	 * 过滤查询逾期客户
	 * @param filter
	 * @return
	 */

	public List<CustomerOverdueForm> findCustomerOverdue(CustomerOverdueFilter filter);
	/**
	 * 查询当前客户经理下的逾期客户数量
	 * @param id
	 * @return
	 */
	public int findCustomerOverdueCountById(@Param("id") String id);
	/**
	 * 查询逾期客户
	 * @param customerId
	 * @param productId
	 */
	public CustomerOverdue findCustomerOverdueCountByCustomerIdAndProductId(@Param("cid") String customerId,@Param("pid") String productId);
 
}
