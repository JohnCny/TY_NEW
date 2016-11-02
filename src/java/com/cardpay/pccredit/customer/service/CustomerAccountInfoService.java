package com.cardpay.pccredit.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CustomerAccountInfoDao;
import com.cardpay.pccredit.customer.filter.CustomerAccountInforFilter;
import com.cardpay.pccredit.customer.web.CustomerAccountInfoForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 
 * 描述 ：客户账户信息service
 * @author 张石树
 *
 * 2014-11-3 上午9:58:31
 */
@Service
public class CustomerAccountInfoService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerAccountInfoDao customerAccountInforDao;

	/**
	 * 根据过滤条件分页
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerAccountInfoForm> findCustomerAccountsByFilter(CustomerAccountInforFilter filter) {
		List<CustomerAccountInfoForm> customerAccountForms = customerAccountInforDao.findCusomerAccountInfoByFilter(filter);
		int size = customerAccountInforDao.findCusomerAccountInfoCountByFilter(filter);
		QueryResult<CustomerAccountInfoForm> qs = new QueryResult<CustomerAccountInfoForm>(size, customerAccountForms);
		return qs;
	}

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public CustomerAccountInfoForm findCustomerAccountById(String id) {
		return customerAccountInforDao.findCustomerAccountById(id);
	}
	
	/**
	 * 根据cardNumber查询
	 * @param cardNumber
	 * @return
	 */
	public CustomerAccountInfoForm findCustomerAccountByCardNumber(String cardNumber) {
		return customerAccountInforDao.findCustomerAccountByCardNumber(cardNumber);
	}
	
	/**
	 * 根据accountNumber查询
	 * @param accountNumber
	 * @return
	 */
	public CustomerAccountInfoForm findCustomerAccountByaccountNumber(String accountNumber) {
		return customerAccountInforDao.findCustomerAccountByaccountNumber(accountNumber);
	}
	
	/**
	 * 根据客户id查询账户
	 * @param customerId
	 * @return
	 */
	public List<CustomerAccountInfoForm> findCustomerAccountByCustomerId(String customerId) {
		return customerAccountInforDao.findCustomerAccountByCustomerId(customerId);
	}
}
