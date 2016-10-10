/**
 * 
 */
package com.cardpay.pccredit.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.manager.dao.ManagerLevelAdjustmentDao;
import com.cardpay.pccredit.manager.filter.BatchRunFilter;
import com.cardpay.pccredit.manager.filter.ManagerLevelAdjustmentFilter;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.model.BatchTask;
import com.cardpay.pccredit.manager.model.ManagerLevelAdjustment;
import com.cardpay.pccredit.manager.model.ManagerMonthTargetData;
import com.cardpay.pccredit.manager.web.ManagerLevelAdjustmentForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.model.User;

/**
 * 
 * 描述 ：客户经理级别调整service
 * @author 张石树
 *
 * 2014-11-20 上午10:15:40
 */
@Service
public class ManagerLevelAdjustmentService {

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private ManagerLevelAdjustmentDao managerLevelAdjustmentDao;
	
	/**
	 * 分页查询
	 * @param filter
	 * @return
	 */
	public QueryResult<ManagerLevelAdjustmentForm> findManagerLevelAdjustmentByFilter(
			ManagerLevelAdjustmentFilter filter) {
		List<ManagerLevelAdjustmentForm> levelAdjustmentForms = managerLevelAdjustmentDao.findManagerLevelAdjustmentByFilter(filter);
		int size = managerLevelAdjustmentDao.findManagerLevelAdjustmentCountByFilter(filter);
		QueryResult<ManagerLevelAdjustmentForm> qs = new QueryResult<ManagerLevelAdjustmentForm>(size, levelAdjustmentForms);
		return qs;
	}
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ManagerLevelAdjustmentForm findManagerLevelAdjustmentById(String id){
		return managerLevelAdjustmentDao.findManagerLevelAdjustmentById(id);
	}

	/**
	 * 更新级别调整信息
	 * @param managerLevelAdjustment
	 */
	public void updateManagerLevelAdjustment(
			ManagerLevelAdjustment managerLevelAdjustment) {
		commonDao.updateObject(managerLevelAdjustment);
	}

	/**
	 * 客户经理
	 * @param filter
	 * @return
	 */
	public QueryResult<ManagerMonthTargetData> findManagerMonthTargetDataByFilter(ManagerLevelAdjustmentFilter filter) {
		return commonDao.findObjectsByFilter(ManagerMonthTargetData.class, filter);
	}

	/**
	 * 修改是否处理
	 * @param id
	 * @param user
	 */
	public void handleAdjustmentLevel(String id, User user) {
		ManagerLevelAdjustment managerLevelAdjustment = commonDao.findObjectById(ManagerLevelAdjustment.class, id);
		managerLevelAdjustment.setModifiedBy(user.getId());
		managerLevelAdjustment.setModifiedTime(new Date());
		managerLevelAdjustment.setIfHandled(ManagerLevelAdjustmentConstant.IFHANDLE_1);
		commonDao.updateObject(managerLevelAdjustment);
		
		//更新客户经理参数信息
		AccountManagerParameter  accountManagerParameter = managerLevelAdjustmentDao.findAccountManagerByUserId(managerLevelAdjustment.getCustomerManagerId());
		accountManagerParameter.setModifiedBy(user.getId());
		accountManagerParameter.setModifiedTime(new Date());
		accountManagerParameter.setLevelInformation(managerLevelAdjustment.getAdjustAfterLevel());
		commonDao.updateObject(accountManagerParameter);
	}
	
	
	/**
	 * 批处理重跑分页查询
	 * @param filter
	 * @return
	 */
	public QueryResult<BatchTask> findBatchFilter(BatchRunFilter filter) {
		List<BatchTask> list = managerLevelAdjustmentDao.findBatchListFilter(filter);
		int size = managerLevelAdjustmentDao.findBatchCountFilter(filter);
		QueryResult<BatchTask> qs = new QueryResult<BatchTask>(size, list);
		return qs;
	}
}
