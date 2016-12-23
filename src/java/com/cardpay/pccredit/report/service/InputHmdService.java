package com.cardpay.pccredit.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.dao.InputHmdDao;
import com.cardpay.pccredit.report.model.CustomerHmd;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class InputHmdService {

	@Autowired
	private InputHmdDao hmdDao;

	public void insetHmd(CustomerHmd hmd) {
		// TODO Auto-generated method stub
		hmdDao.insetHmd(hmd);
	}

	public QueryResult<CustomerHmd> queryAll(CustomerHmd filter) {
		// TODO Auto-generated method stub
		List<CustomerHmd> cplist = hmdDao.queryAll(filter);
		int size = hmdDao.querySize(filter);
		QueryResult<CustomerHmd> queryResult = new QueryResult<CustomerHmd>(size,cplist);
		return queryResult;
	}

	public CustomerHmd queryByCardId(String cardId) {
		// TODO Auto-generated method stub
		return hmdDao.queryByCardId(cardId);
	}
}
