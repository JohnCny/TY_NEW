package com.cardpay.pccredit.customer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.filter.CustomerMaintenanceLogFilter;
import com.cardpay.pccredit.customer.model.CustomerMaintenanceLog;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.dao.query.OrderBy;
import com.wicresoft.jrad.base.database.model.QueryResult;
/**
 * 
 * @author 季东晓
 *
 * 2014-10-28 上午10:06:32
 */

@Service
public class CustomerMaintenanceLogService {
	
	@Autowired
	private CommonDao commonDao;
	
	
	@Autowired
	private CustomerInforCommDao customerInforCommDao;
	
	

	
	/**
	 * 根据条件查询客户维护信息日志
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerMaintenanceLog> findCustomerInforLogByFilter(CustomerMaintenanceLogFilter filter){
		filter.setLimit(10000);
		filter.getOrderBys().add(new OrderBy("modifyFieldName", true)); 
		filter.getOrderBys().add(new OrderBy("createdTime", true));
		return commonDao.findObjectsByFilter(CustomerMaintenanceLog.class, filter);
	}
	

	/**
	 * 根据条件查询调查大纲维护日
	 * @param filter
	 * @return
	 */
	
	public List<CustomerMaintenanceLog> findCustomerInforLogMbxxByFilter(
			CustomerMaintenanceLogFilter filter) {
		return customerInforCommDao.findCustomerInforLogMbxxByFilter(filter);
	}
	
	/**
	 * 根据条件更新维护日志对应的表
	 * @param  modifyTableName,customerId, modifyTablevalue
	 * @return
	 */
	public void updateCustomerinforUpdateLog(String modifyTableName,String customerId,String modifyTablevalue) {
		 customerInforCommDao.updateCustomerinforUpdateLog(modifyTableName,customerId,modifyTablevalue);
	}
	
	/**
	 * 根据条件更新基本信息维护日志对应的表
	 * @param  modifyTableName,customerId, modifyTablevalue
	 * @return
	 */
	public void updateCustomerinforUpdatejbxxLog(String modifyTableName,String customerId,String modifyTablevalue) {
		 customerInforCommDao.updateCustomerinforUpdatejbxxLog(modifyTableName,customerId,modifyTablevalue);
	}
	
	/**
	 * 根据条件更新维护調查大綱对应的表
	 * @param  modifyTableName,customerId, modifyTablevalue,
	 * @return
	 */
	public void updateCustomerinforUpdatedcdgLog(String modifyTableName,String customerId,String modifyTablevalue,String questionCode) {
		 customerInforCommDao.updateCustomerinforUpdatedcdgLog(modifyTableName,customerId,modifyTablevalue,questionCode);
	}
	


}
