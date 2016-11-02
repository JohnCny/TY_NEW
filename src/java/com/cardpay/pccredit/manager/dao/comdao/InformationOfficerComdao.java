/**
 * 
 */
package com.cardpay.pccredit.manager.dao.comdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.InformationOfficerDao;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;

/**
 * @author shaoming
 *
 * 2014年11月3日   下午1:31:49
 */
@Service
public class InformationOfficerComdao {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private InformationOfficerDao informationOfficerDao;
	
//	public QueryResult<CustomerInforWeb> findCustomerInforWebByFilter(ManagerInformationClientFilter filter){
//		List<CustomerInforWeb> customerInforWebs = informationOfficerDao.findCustomerInforWebsByFilter(filter);
//		int size = informationOfficerDao.findCustomerInforWebsCountByFilter(filter);
//		QueryResult<CustomerInforWeb> qr = new QueryResult<CustomerInforWeb>(size, customerInforWebs);
//		return qr;
//	}
}
