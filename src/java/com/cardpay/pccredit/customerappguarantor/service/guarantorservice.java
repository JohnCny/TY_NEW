package com.cardpay.pccredit.customerappguarantor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.Sx.dao.SxDao;
import com.cardpay.pccredit.Sx.model.SxInputData;
import com.cardpay.pccredit.Sx.model.SxOutputData;
import com.cardpay.pccredit.customer.model.TyRepaySxForm;
import com.cardpay.pccredit.customerappguarantor.dao.guarantordao;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.postLoan.filter.SxFilter;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class guarantorservice {
@Autowired
private guarantordao dao;
	

	//-------------------------
	public List<CustomerApplicationGuarantor> findguarantor() {
		// TODO Auto-generated method stub
		return dao.findguarantor();
	}


	public void insertguarantor(CustomerApplicationGuarantor filter) {
		// TODO Auto-generated method stub
		
	}

}
