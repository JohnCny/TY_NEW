package com.cardpay.pccredit.customeradd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customeradd.dao.JxglDao;
import com.cardpay.pccredit.customeradd.dao.KuglDao;
import com.cardpay.pccredit.customeradd.model.JxglForm;
import com.cardpay.pccredit.customeradd.model.MaintenanceForm;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class JxglService {
	@Autowired
	private JxglDao dao;
	
	public List<AccountManagerParameterForm> findcustomermanager() {
		// TODO Auto-generated method stub
		return dao.findcustomermanager();
	}

	public QueryResult<JxglForm> findAlljxglList(MaintenanceFilter filter) {
		List<JxglForm> plans = dao.findAlljxglList(filter);
		int size = dao.findAlljxglCountList(filter);
		QueryResult<JxglForm> qr = new QueryResult<JxglForm>(size,plans);
		return qr;
	}

	public List<JxglForm> findalljxgllists(MaintenanceFilter filter) {
		// TODO Auto-generated method stub
		return dao.findAlljxglLists(filter);
	}

}
