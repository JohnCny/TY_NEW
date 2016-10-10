package com.cardpay.pccredit.intopieces.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardpay.pccredit.intopieces.dao.CustomerApplicationRecomDao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

public class CustomerApplicationRecomService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerApplicationRecomDao customerApplicationRecomDao;

	public void save(CustomerApplicationRecom customerApplicationRecom) {
		commonDao.insertObject(customerApplicationRecom);
	}
}
