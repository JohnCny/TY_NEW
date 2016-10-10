package com.cardpay.pccredit.intopieces.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardpay.pccredit.intopieces.dao.CustomerApplicationContactDao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

public class CustomerApplicationContactService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerApplicationContactDao customerApplicationContactDao;

	public void save(CustomerApplicationContact customerApplicationContact) {
		commonDao.insertObject(customerApplicationContact);
	}
}
