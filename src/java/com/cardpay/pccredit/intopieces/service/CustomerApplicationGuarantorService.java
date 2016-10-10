package com.cardpay.pccredit.intopieces.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cardpay.pccredit.intopieces.dao.CustomerApplicationGuarantorDao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

public class CustomerApplicationGuarantorService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerApplicationGuarantorDao customerApplicationGuarantorDao;

	public void save(CustomerApplicationGuarantor customerApplicationGuarantor) {
		commonDao.insertObject(customerApplicationGuarantor);
	}
}
