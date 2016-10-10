/**
 * 
 */
package com.cardpay.pccredit.manager.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.InformationMaintenanceDao;
import com.cardpay.pccredit.manager.filter.InformationMaintenanceFilter;
import com.cardpay.pccredit.manager.model.InformationMaintenance;
import com.cardpay.pccredit.manager.model.InformationPlansAction;
import com.cardpay.pccredit.manager.web.InformationMaintenanceForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author shaoming
 *
 * 2014年11月24日   下午3:06:43
 */
@Service
public class InformationMaintenanceService {
	@Autowired
	private InformationMaintenanceDao informationMaintenanceDao;
	
	@Autowired
	private CommonDao commonDao;
	/**
	 * 维护计划页面
	 * @param filter
	 * @return
	 */
	public QueryResult<InformationMaintenanceForm> findInformationMaintenanceByFilter(InformationMaintenanceFilter filter){
		List<InformationMaintenanceForm> forms = informationMaintenanceDao.findInformationMaintenanceFormByFilter(filter);
		int size = informationMaintenanceDao.findInformationMaintenanceCountByFilter(filter);
		QueryResult<InformationMaintenanceForm> qr = new QueryResult<InformationMaintenanceForm>(size,forms);
		return qr;
	}
	/**
	 * 添加信息员维护计划
	 * @param informationMaintenance
	 * @return
	 */
	public String insertInformationMaintenance(InformationMaintenance informationMaintenance){
		informationMaintenance.setCreatedTime(new Date());
		commonDao.insertObject(informationMaintenance);
		return informationMaintenance.getId();
	}
	/**
	 * 更新信息员维护计划
	 * @param informationMaintenance
	 * @return
	 */
	public boolean updateInformationMaintenance(InformationMaintenance informationMaintenance){
		informationMaintenance.setModifiedTime(new Date());
		int i = commonDao.updateObject(informationMaintenance);
		return i>0?true:false;
	}
	/**
	 * 得到InformationMaintenanceForm
	 * @param id
	 * @return
	 */
	public InformationMaintenanceForm findInformationMaintenanceFormById(String id){
		return informationMaintenanceDao.findInformationMaintenanceFormById(id);
	}
	/**
	 * 得到该维护计划下所有的实施记录
	 * @param id
	 * @return
	 */
	public List<InformationPlansAction> findMaintenanceActionByMaintenanceId(String id){
		return informationMaintenanceDao.findMaintenanceActionByMaintenanceId(id);
	}
	/**
	 * 在当前计划下插入一条新实施记录
	 * @param action
	 * @return
	 */
	public String insertInformationPlansAction(InformationPlansAction action){
		action.setCreatedTime(new Date());
		commonDao.insertObject(action);
		return action.getId();
	}
	/**
	 * 通过id得到实施记录
	 * @param id
	 * @return
	 */
	public InformationPlansAction findInformationPlansActionById(String id){
		return commonDao.findObjectById(InformationPlansAction.class, id);
	}
	/**
	 * 通过id得到实施记录
	 * @param action
	 * @return
	 */
	public boolean updateInformationPlansAction(InformationPlansAction action){
		action.setModifiedTime(new Date());
		int i = commonDao.updateObject(action);
		return i>0?true:false;
	}
	
	public int findInformationPlanCountByDay(String userId){
		int result = 99;
		if(StringUtils.isNotEmpty(userId)){
			result = informationMaintenanceDao.findInformationPlanCountByDay(userId);
		}
		return result;
	}
}
