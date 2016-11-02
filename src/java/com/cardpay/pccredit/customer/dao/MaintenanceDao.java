/**
 * 
 */
package com.cardpay.pccredit.customer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.customer.web.MaintenanceWeb;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午3:07:50
 */
@Mapper
public interface MaintenanceDao {
	/**
	 * 得到维护计划(browse)
	 * @param filter
	 * @return
	 */
	List<MaintenanceForm> findMaintenancePlansByFilter(MaintenanceFilter filter);
	
	/**
	 * 得到维护计划(browse)
	 * @param filter
	 * @return
	 */
	List<MaintenanceWeb> findMaintenanceWebPlansByFilter(MaintenanceFilter filter);
	
	/**
	 * 得到维护计划(browse)
	 * @param filter
	 * @return
	 */
	List<MaintenanceForm> findMaintenancePlansList(MaintenanceFilter filter);
	
	/**
	 * 得到维护计划数量
	 * @return
	 */
	int findMaintenancePlansCountList(MaintenanceFilter filter);
	
	/**
	 * 得到维护计划(browse)(下属)
	 * @param filter
	 * @return
	 */
	List<MaintenanceForm> findSubMaintenancePlansByFilter(MaintenanceFilter filter);
	/**
	 * 得到维护计划数量
	 * @return
	 */
	int findMaintenancePlansCountByFilter(MaintenanceFilter filter);
	/**
	 * 得到维护计划数量(下属)
	 * @param filter
	 * @return
	 */
	int findSubMaintenancePlansCountByFilter(MaintenanceFilter filter);
	/**
	 * 得到维护计划(change)
	 * @param maintenanceId
	 * @return
	 */
	MaintenanceForm findMaintenanceById(@Param("maintenanceId") String maintenanceId);
	
	/**
	 * 得到维护计划(change)
	 * @param maintenanceId
	 * @param appId
	 * @return
	 */
	MaintenanceForm findMaintenance(MaintenanceWeb m);
	
	/**
	 * 得到维护计划(change)
	 * @param maintenanceId
	 * @param appId
	 * @return
	 */
	MaintenanceForm findMaintenAndAppInfo(MaintenanceWeb m);
	/**
	 * 得到维护计划
	 * @param maintenanceId
	 * @param appId
	 * @return
	 */
	MaintenanceForm findMaintenanceByCustomerId(MaintenanceFilter filter);
	
	/**
	 * 通过维护计划id得到实施记录
	 * @param id
	 * @return
	 */
	List<MaintenanceWeb> findMaintenanceActionByMaintenanceId(@Param("id") String id);
	
	int checkRepeat(@Param("id") String id,@Param("endResult") String endResult);
	
	int findMaintenanceCountToday(@Param("customerManagerId") String customerManagerId,@Param("result") String result,@Param("startTime") String start,@Param("endTime") String end);
	
	/**
	 * 找 字节点
	 */
	public List<ManagerBelongMapForm> findChildId(@Param("id") String userId);
}
