package com.cardpay.pccredit.riskControl.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.riskControl.dao.RiskConsiderationsDao;
import com.cardpay.pccredit.riskControl.filter.RiskConsiderationsFilter;
import com.cardpay.pccredit.riskControl.model.RiskConsiderations;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author chenzhifang
 *
 * 2014-11-5上午11:17:47
 */
@Service
public class RiskConsiderationsService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private RiskConsiderationsDao riskConsiderationsDao;
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<RiskConsiderations> findRiskConsiderationsByFilter(RiskConsiderationsFilter filter) {
		//return commonDao.findObjectsByFilter(RiskCustomer.class, filter);
		List<RiskConsiderations> riskConsiderations = riskConsiderationsDao.findRiskConsiderationsByFilter(filter);
		int size = riskConsiderationsDao.findRiskConsiderationsCountByFilter(filter);
		QueryResult<RiskConsiderations> qs = new QueryResult<RiskConsiderations>(size, riskConsiderations);
		return qs;
	}

	/**
	 * 插入风险属性
	 * @param riskConsiderations
	 * @return id
	 */
	public String insertRiskConsiderations(RiskConsiderations riskConsiderations) {
		riskConsiderations.setCreatedTime(Calendar.getInstance().getTime());
		commonDao.insertObject(riskConsiderations);

		return riskConsiderations.getId();
	}

	/**
	 * 更新风险属性
	 * @param riskConsiderations
	 * @return 
	 */
	public int updateRiskConsiderations(RiskConsiderations riskConsiderations) {
		riskConsiderations.setModifiedTime(Calendar.getInstance().getTime());

		return commonDao.updateObject(riskConsiderations);
	}

	/**
	 * 删除风险属性
	 * @param id
	 * @return 
	 */
	public int deleteRiskConsiderations(String id) {
		return commonDao.deleteObject(RiskConsiderations.class, id);
	}

	/**
	 * 通过Id查找风险属性
	 * @param id
	 * @return 
	 */
	public RiskConsiderations findRiskConsiderationsById(String id) {
		return commonDao.findObjectById(RiskConsiderations.class, id);
	}

	/**
	 * 批量删除风险属性
	 * @param id
	 * @return 
	 */
	public int batchDeleteRiskConsiderations(List<String> ids) {
		return commonDao.batchDeleteObjects(RiskConsiderations.class, ids);
	}

}
