package com.cardpay.pccredit.customeradd.dao;

import java.util.List;

import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customeradd.model.CIPERSONBASINFO;
import com.cardpay.pccredit.customeradd.model.MaintenanceForm;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface KuglDao {
	/**
	 * 查询登陆人角色 
	 * @param filter
	 * @return
	 */
	/*List<Role> findRole();
	*//**
	 * 通过传入的id查询用户的职级
	 * @param filter
	 * @return
	 *//*
	String finduserrole(String userId);
	List<CustomerInfoForm> findallcustomer();
	List<CustomerInfoForm> findmanagercustomer(String userroleid);
	List<String> findalluser();*/
	

	List<CustomerFirsthendBase> fincustomerinfo(String cardid);

	List<AccountManagerParameterForm> findcustomermanager();

	List<MaintenanceForm> findMaintenancePlansList(MaintenanceFilter filter);
	
	int findMaintenancePlansCountList(MaintenanceFilter filter);
	
}
