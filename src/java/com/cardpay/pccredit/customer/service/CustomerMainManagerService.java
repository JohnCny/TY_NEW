package com.cardpay.pccredit.customer.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CustomerMainManagerDao;
import com.cardpay.pccredit.customer.model.CustomerMainManager;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * CustomerMainManagerService类的描述
 * 
 * @author 王海东
 * @created on 2014-10-31
 * 
 * @version $Id:$
 */
@Service
public class CustomerMainManagerService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerMainManagerDao customerMainManagerDao;

	public String insertCustomerMainManager(CustomerMainManager customerMainManager) {
		customerMainManager.setCreatedTime(Calendar.getInstance().getTime());
		commonDao.insertObject(customerMainManager);
		return customerMainManager.getId();
	}

	public int updateCustomerMainManager(CustomerMainManager customerMainManager) {
		//customerMainManager.setModifiedTime(Calendar.getInstance().getTime());
		return customerMainManagerDao.updateCustomerManinManagerByCustomerId(customerMainManager);
	}
	
	public CustomerMainManager findCustomerMainManagerByCustomerId(String customerId) {
		return customerMainManagerDao.findCustomerMainManagerByCustomerId(customerId);
	}
	/**
	 * 根据进件ID获取台账信息
	 * @param applicationId
	 * @return
	 */
	public CustomerMainManager findCustomerMainManagerByApplicationId(String applicationId) {
		return customerMainManagerDao.findCustomerMainManagerByApplicationId(applicationId);
	}

}
