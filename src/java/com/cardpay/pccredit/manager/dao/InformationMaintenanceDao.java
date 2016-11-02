/**
 * 
 */
package com.cardpay.pccredit.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.manager.filter.InformationMaintenanceFilter;
import com.cardpay.pccredit.manager.model.InformationPlansAction;
import com.cardpay.pccredit.manager.web.InformationMaintenanceForm;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author shaoming
 *
 * 2014年11月24日   下午3:19:03
 */
@Mapper
public interface InformationMaintenanceDao {
	List<InformationMaintenanceForm> findInformationMaintenanceFormByFilter(InformationMaintenanceFilter filter);
	
	int findInformationMaintenanceCountByFilter(InformationMaintenanceFilter filter);
	
	InformationMaintenanceForm findInformationMaintenanceFormById(@Param("id") String id);
	
	List<InformationPlansAction> findMaintenanceActionByMaintenanceId(@Param("id") String id);
	
	int findInformationPlanCountByDay(@Param("userId") String userId);
}
