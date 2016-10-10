package com.cardpay.pccredit.customer.dao.comdao;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerOverdue;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

@Service
public class CustomerOverdueComDao {
	
	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 根据客户Id和产品Id 查询当期逾期记录
	 * @return List
	 */
	public  CustomerOverdue findCustomerOverdueBycustomerIdAndproductId(String customerId,String productId){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("productId", productId);
		String sql = "select * from customer_overdue a where a.customer_id=#{customerId} and a.product_id =#{productId}";
		List<CustomerOverdue> list= commonDao.queryBySql(CustomerOverdue.class, sql, params);
		if(list.size() > 0){
			CustomerOverdue customerOverdue = list.get(0);
		return customerOverdue;
		}else{
			
			return null;
			
		}
	}

}
