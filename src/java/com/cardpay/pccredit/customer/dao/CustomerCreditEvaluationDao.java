package com.cardpay.pccredit.customer.dao;
import com.cardpay.pccredit.customer.model.CustomerCreditEvaluation;
import com.wicresoft.util.annotation.Mapper;
/**
 * 
 * 该类的描述
 *
 * @author 王海东
 * @created on 2014-12-24
 * 
 * @version $Id:$
 */

@Mapper
public interface CustomerCreditEvaluationDao {

	//根据客户id查询授信额度模型
	CustomerCreditEvaluation findCustomerCreidtEvaluationByCustomerId(String customerId);
	//根据客户id更新授信额度模型
	void updateCustomerCreidtEvaluationByCustomerId(CustomerCreditEvaluation customerCreditEvaluation);
	//插入授信评估模型
	public int insertCustomerCreidtEvaluation(CustomerCreditEvaluation customerCreditEvaluation);

}