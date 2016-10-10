package com.cardpay.pccredit.intopieces.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardpay.pccredit.intopieces.dao.CustomerApplicationComDao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

public class CustomerApplicationComService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerApplicationComDao customerApplicationComDao;

	public void save(CustomerApplicationCom customerApplicationCom) {
		commonDao.insertObject(customerApplicationCom);
	}
}
