package com.cardpay.pccredit.jnpad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.customer.web.MaintenanceWeb;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.wicresoft.util.annotation.Mapper;
@Mapper
public interface JnpadMaintenanceDao {
	/**
	 * 找 字节点
	 */
	public List<ManagerBelongMapForm> findChildId(@Param("id") String userId) ;

	/**
	 * 根据客户经理从属的客户经理参数id查询客户经理信息
	 * @param childIdInStr
	 * @return
	 */
	public List<AccountManagerParameterForm> findAccountManagerParameterByChildIds(@Param("childIdInStr") String childIdInStr);

	
	/**
	 * 获取指定客户经理的客户列表
	 */
	
	public int findCustomerCountByFilter(CustomerInforFilter filter);

	public int findMaintenancePlansCountList(MaintenanceFilter filter);

	public List<MaintenanceForm> findMaintenancePlansList(MaintenanceFilter filter);

}
