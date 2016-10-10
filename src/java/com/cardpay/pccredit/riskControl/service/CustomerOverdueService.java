package com.cardpay.pccredit.riskControl.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CustomerOverdueDao;
import com.cardpay.pccredit.customer.filter.CustomerOverdueFilter;
import com.cardpay.pccredit.customer.model.CustomerOverdue;
import com.cardpay.pccredit.riskControl.web.CustomerOverdueForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.date.DateHelper;


/**
 * CustomerOverdueService类的描述
 *
 * @author 王海东
 * @created on 2014-11-4
 * 
 * @version $Id:$
 */
@Service
public class CustomerOverdueService {
	
	@Autowired
	private CustomerOverdueDao customerOverdueDao;
	
	@Autowired
	private CommonDao commonDao;
	
	public QueryResult<CustomerOverdueForm> findCustomerOverdue(CustomerOverdueFilter filter) {
		List<CustomerOverdueForm> customerOverdueForm = customerOverdueDao.findCustomerOverdue(filter);
		int size = customerOverdueDao.findCustomerOverdueCount(filter);
		QueryResult<CustomerOverdueForm> qs = new QueryResult<CustomerOverdueForm>(size, customerOverdueForm);
		return qs;
	}
	/**
	 * 查询当前客户经理下的逾期客户数量
	 * @param id
	 * @return
	 */
	public int findCustomerOverdueCountById(String id){
		return customerOverdueDao.findCustomerOverdueCountById(id);
	}
	/**
	 * 查询逾期客户
	 * @param customerId 客户id
	 * @param productId 产品id
	 * @return
	 */
	public CustomerOverdue findCustomerOverdueCountByCustomerIdAndProductId(String customerId,String productId){
		return customerOverdueDao.findCustomerOverdueCountByCustomerIdAndProductId(customerId,productId);
	}
	/**
	 * 更新逾期客户
	 * @param customerOverdue
	 * @return
	 */
	public boolean updateCustomerOverdue(CustomerOverdue customerOverdue){
		customerOverdue.setModifiedTime(DateHelper.normalizeDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		int i = commonDao.updateObject(customerOverdue);
		return i>0?true:false;
	}
}
