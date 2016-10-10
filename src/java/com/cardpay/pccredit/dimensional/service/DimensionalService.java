package com.cardpay.pccredit.dimensional.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.dimensional.dao.DimensionalDao;
import com.cardpay.pccredit.dimensional.filter.DimensionalFilter;
import com.cardpay.pccredit.dimensional.model.Dimensional;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * Description of DimensionalService
 * 
 * @author 王海东
 * @created on 2014-10-20
 * 
 * @version $Id:$
 */

@Service
public class DimensionalService {

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private DimensionalDao dimensionalDao;
	//根据Filter查询四维评估模型
	public QueryResult<Dimensional> findDimensionalByFilter(DimensionalFilter filter) {
		return commonDao.findObjectsByFilter(Dimensional.class, filter);
	}
	//根据客户Id查询四维评估模型
	public Dimensional findDimensionalByCustomerId(String customerId) {
		return dimensionalDao.findDimensionalByCustomerId(customerId);
	}
	//插入四维评估模型
	public String insertDimensional(Dimensional dimensional) {
		dimensional.setCreatedTime(Calendar.getInstance().getTime());
		commonDao.insertObject(dimensional);
		return dimensional.getId();
	}
	//根据customerId修改四维评估表
	public void updateDimensionalByCustomerId(Dimensional dimensional) {
		dimensional.setModifiedTime(Calendar.getInstance().getTime());
		dimensionalDao.updateDimensionalByCustomerId(dimensional);
	}
	
	/**
	 * 根据进件申请Id查询思维评估信息
	 * @param applicationId
	 * @return
	 */
	public Dimensional findDimensionalByAppId(String applicationId) {
		return dimensionalDao.findDimensionalByAppId(applicationId);
	}
}
