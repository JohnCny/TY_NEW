package com.cardpay.pccredit.customer.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.CustomerCreditEvaluationDao;
import com.cardpay.pccredit.customer.model.CustomerCreditEvaluation;
import com.cardpay.pccredit.dimensional.model.Dimensional;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * CustomerCreditEvaluationService类的描述
 * 
 * @author 王海东
 * @created on 2014-12-23
 * 
 * @version $Id:$
 */
@Service
public class CustomerCreditEvaluationService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerCreditEvaluationDao customerCreditEvaluationDao;

	// 插入授信评估模型
	public String insertCustomerCreditEvaluation(CustomerCreditEvaluation customerCreditEvaluation) {
		customerCreditEvaluationDao.insertCustomerCreidtEvaluation(customerCreditEvaluation);
		return customerCreditEvaluation.getId();
	}

	// 根据客户Id查询授信评估模型
	public CustomerCreditEvaluation findCustomerCreidtEvaluationByCustomerId(String customerId) {
		return customerCreditEvaluationDao.findCustomerCreidtEvaluationByCustomerId(customerId);
	}
	
	//根据customerId修改四维评估表
		public void updateCustomerCreidtEvaluationByCustomerId(CustomerCreditEvaluation customerCreditEvaluation) {
			customerCreditEvaluation.setModifiedTime(Calendar.getInstance().getTime());
			customerCreditEvaluationDao.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
		}

}
