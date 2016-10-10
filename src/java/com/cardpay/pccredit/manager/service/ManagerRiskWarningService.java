package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.constant.ReturnReceiptConstant;
import com.cardpay.pccredit.manager.dao.ManagerRiskWarningDao;
import com.cardpay.pccredit.manager.filter.ManagerRiskWarningFilter;
import com.cardpay.pccredit.manager.model.ManagerRiskWarning;
import com.cardpay.pccredit.manager.web.ManagerRiskWarningForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * ManagerRiskWarningService类的描述
 * 
 * @author 王海东
 * @created on 2014-11-12
 * 
 * @version $Id:$
 */
@Service
public class ManagerRiskWarningService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private ManagerRiskWarningDao managerRiskWarningDao;

	public String insertManagerRiskWarning(ManagerRiskWarning managerRiskWarning) {
		managerRiskWarning.setReturnReceipt(ReturnReceiptConstant.UNCHECKED);
		managerRiskWarning.setInitiateTime(Calendar.getInstance().getTime());
		managerRiskWarning.setCreatedTime(Calendar.getInstance().getTime());
		commonDao.insertObject(managerRiskWarning);
		return managerRiskWarning.getId();
	}

	public int updateManagerRiskWarning(ManagerRiskWarning managerRiskWarning) {
		managerRiskWarning.setModifiedTime(Calendar.getInstance().getTime());
		return commonDao.updateObject(managerRiskWarning);
	}

	public int updateFeedBackById(String riskId, String feedback) {
		return managerRiskWarningDao.updateFeedBackById(riskId, feedback);
	}

	public int deleteManagerRiskWarning(String riskId) {
		return commonDao.deleteObject(ManagerRiskWarning.class, riskId);
	}

	public ManagerRiskWarning findManagerRiskWarningById(String riskId) {
		return commonDao.findObjectById(ManagerRiskWarning.class, riskId);
	}

	/*
	 * 根据filter查询创建的警示书
	 */
	public QueryResult<ManagerRiskWarningForm> findCreateManagerRiskWarningByFilter(ManagerRiskWarningFilter filter) {
		List<ManagerRiskWarningForm> managerRiskWarningForm = managerRiskWarningDao.findCreateManagerRiskWarningByFilter(filter);
		int size = managerRiskWarningDao.findCreateManagerRiskWarningCountByFilter(filter);
		QueryResult<ManagerRiskWarningForm> qs = new QueryResult<ManagerRiskWarningForm>(size, managerRiskWarningForm);
		return qs;
	}

	/*
	 * 根据filter查询接受的警示书
	 */
	public QueryResult<ManagerRiskWarningForm> findReciveManagerRiskWarningByFilter(ManagerRiskWarningFilter filter) {
		List<ManagerRiskWarningForm> managerRiskWarningForm = managerRiskWarningDao.findReciveManagerRiskWarningByFilter(filter);
		int size = managerRiskWarningDao.findReciveManagerRiskWarningCountByFilter(filter);
		QueryResult<ManagerRiskWarningForm> qs = new QueryResult<ManagerRiskWarningForm>(size, managerRiskWarningForm);
		return qs;
	}

	/*
	 * 查询创建的警示书
	 */
	public List<ManagerRiskWarningForm> findCreateManagerRiskWarningListByFilter(ManagerRiskWarningFilter filter) {
	  return managerRiskWarningDao.findCreateManagerRiskWarningByFilter(filter);
	}
}
