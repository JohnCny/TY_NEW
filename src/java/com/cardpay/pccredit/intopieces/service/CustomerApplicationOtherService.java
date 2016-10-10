package com.cardpay.pccredit.intopieces.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardpay.pccredit.intopieces.dao.CustomerApplicationOtherDao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

public class CustomerApplicationOtherService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerApplicationOtherDao customerApplicationOtherDao;

	public void save(CustomerApplicationOther customerApplicationOther) {
		commonDao.insertObject(customerApplicationOther);
	}
}
