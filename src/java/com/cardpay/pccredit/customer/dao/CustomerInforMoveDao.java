package com.cardpay.pccredit.customer.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.web.CustomerInfoMoveForm;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.util.annotation.Mapper;
/**
 * 
 * @author shaoming
 *  
 */
@Mapper
public interface CustomerInforMoveDao {
	
	public List<CustomerInfoMoveForm> findCustomerMoveList(CustomerInforFilter filter);
	public int findCustomerMoveListCount(CustomerInforFilter filter);

}
