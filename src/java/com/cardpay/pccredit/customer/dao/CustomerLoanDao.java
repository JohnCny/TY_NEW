/**
 * 
 */
package com.cardpay.pccredit.customer.dao;

import java.util.List;

import com.cardpay.pccredit.customer.filter.CustomerLoanFilter;
import com.cardpay.pccredit.customer.web.CustomerLoanForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午3:07:50
 */
@Mapper
public interface CustomerLoanDao {
	
	/**
	 * 得到借款人(browse)
	 * @param filter
	 * @return
	 */
	List<CustomerLoanForm> queryCustomerLoanList(CustomerLoanFilter filter);
	
	/**
	 * 得到借款人数量
	 * @return
	 */
	int queryCustomerLoanCountList(CustomerLoanFilter filter);
	
	/**
	 * 得到非借款人(browse)
	 * @param filter
	 * @return
	 */
	List<CustomerLoanForm> queryNoCustomerLoanList(CustomerLoanFilter filter);
	
	/**
	 * 得到非借款人数量
	 * @return
	 */
	int queryNoCustomerLoanCountList(CustomerLoanFilter filter);
}
