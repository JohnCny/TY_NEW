package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.model.CustomerQuestionInfo;
import com.cardpay.pccredit.customer.web.CustomerQuestionInfoForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * CustomerQuestionDao类的描述
 * 
 * @author 王海东
 * @created on 2014-10-31
 * 
 * @version $Id:$
 */
@Mapper
public interface CustomerQuestionDao {

	/**
	 * 通过customerId 来查询问卷调查
	 */
	public List<CustomerQuestionInfoForm> findCustomerQuestionByCustomerId(String customerId);
	
	/**
	 * 通过applicationId 来查询问卷调查
	 */
	public List<CustomerQuestionInfoForm> findCustomerQuestionByapplicationId(String applicationId);
	
	
	/**
	 * 通过customerId & questionCode来查询CUSTOMER_QUESTION_INFO表里是否有数据
	 */
	public CustomerQuestionInfo findCustomerQuestionByCustomerQu(@Param("customerId") String customerId,@Param("questionCode")String questionCode);

}
