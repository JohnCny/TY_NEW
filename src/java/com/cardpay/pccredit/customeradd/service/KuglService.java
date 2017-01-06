package com.cardpay.pccredit.customeradd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.MaintenanceDao;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customeradd.dao.KuglDao;
import com.cardpay.pccredit.customeradd.model.CIPERSONBASINFO;
import com.cardpay.pccredit.customeradd.model.MaintenanceForm;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.database.model.QueryResult;
@Service
public class KuglService {
	@Autowired
	private KuglDao dao;

	
	public List<AccountManagerParameterForm> findcustomermanager() {
		// TODO Auto-generated method stub
		return dao.findcustomermanager();
	}
	public List<CustomerFirsthendBase> findcustomerinfo(String cardid) {
		// TODO Auto-generated method stub
		return dao.fincustomerinfo(cardid);
	}
	public QueryResult<MaintenanceForm> findMaintenancePlansList(MaintenanceFilter filter){
		List<MaintenanceForm> plans = dao.findMaintenancePlansList(filter);
		int size = dao.findMaintenancePlansCountList(filter);
		QueryResult<MaintenanceForm> qr = new QueryResult<MaintenanceForm>(size,plans);
		return qr;
	}
	

}
