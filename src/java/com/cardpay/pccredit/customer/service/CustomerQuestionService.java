package com.cardpay.pccredit.customer.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CustomerQuestionDao;
import com.cardpay.pccredit.customer.model.CustomerQuestionInfo;
import com.cardpay.pccredit.customer.web.CustomerQuestionInfoForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * CustomerQuestionService类的描述
 * 
 * @author 王海东
 * @created on 2014-10-31
 * 
 * @version $Id:$
 */
@Service
public class CustomerQuestionService {

	@Autowired
	private CustomerQuestionDao customerQuestionDao;

	@Autowired
	private CommonDao commonDao;

	/**
	 * 根据customerId去查询该用户的问卷调查
	 */
	public List<CustomerQuestionInfoForm> findCustomerQuestionByCustomerId(String customerId) {
		return customerQuestionDao.findCustomerQuestionByCustomerId(customerId);

	}
	
	/**
	 * 根据进件Id去查询该用户的问卷调查
	 */
	public List<CustomerQuestionInfoForm> findCustomerQuestionByapplicationId(String applicationId) {
		return customerQuestionDao.findCustomerQuestionByapplicationId(applicationId);

	}
	
	/**
	 *  通过customerId & questionCode来查询CUSTOMER_QUESTION_INFO表里是否有数据
	 */
	public CustomerQuestionInfo findCustomerQuestionByCustomerQu(String customerId,String questionCode) {
		return customerQuestionDao.findCustomerQuestionByCustomerQu(customerId, questionCode);

	}

	/**
	 * 插入或更新用户问卷调查表
	 * 
	 * @param loginId
	 * @param customerQuestionInfo
	 */
	public void insertOrUpdateCustomerQusetion(List<CustomerQuestionInfo> customerQuestionInfos, String loginId) {

		for (CustomerQuestionInfo customerQuestionInfo : customerQuestionInfos) {
			CustomerQuestionInfo cqi = customerQuestionDao.findCustomerQuestionByCustomerQu(customerQuestionInfo.getCustomerId(), customerQuestionInfo.getQuestionCode());
			if (cqi == null) {
				customerQuestionInfo.setCreatedTime(new Date());
				customerQuestionInfo.setCreatedBy(loginId);
				commonDao.insertObject(customerQuestionInfo);
			} else {
				cqi.setModifiedBy(loginId);
				cqi.setModifiedTime(new Date());
				cqi.setQuestionAnswer(customerQuestionInfo.getQuestionAnswer());
				commonDao.updateObject(cqi);
			}
		}

	}

}
