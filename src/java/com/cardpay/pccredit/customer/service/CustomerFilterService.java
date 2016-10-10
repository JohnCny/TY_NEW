package com.cardpay.pccredit.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.web.CustomerInforForm;
import com.cardpay.pccredit.product.dao.ProductDao;


/**
 * CustomerFilterService类的描述
 *
 * @author 王海东
 * @created on 2014-10-24
 * 
 * @version $Id:$
 */
@Service
public class CustomerFilterService {
	
	@Autowired
	private ProductDao productDao;
	
	public List<CustomerInforForm> findScreeningResultsCustomerByProductId(String productId){
		return productDao.findScreeningResultsCustomerByProductId(productId);
		
	}

}
