package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.RetrainingDao;
import com.cardpay.pccredit.manager.filter.RetrainingFilter;
import com.cardpay.pccredit.manager.model.Retraining;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * 再培训计划service
 * @author chenzhifang
 *
 * 2014-11-12上午10:43:08
 */
@Service
public class RetrainingService {
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private RetrainingDao retrainingDao;
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<Retraining> findRetrainingByFilter(RetrainingFilter filter) {
		List<Retraining> list = retrainingDao.findRetrainingsByFilter(filter);
		int size = retrainingDao.findRetrainingsCountByFilter(filter);
		QueryResult<Retraining> qs = new QueryResult<Retraining>(size, list);
		return qs;
	}
	
	/**
	 * 更新再培训计划
	 * @param riskCustomer
	 * @return
	 */
	public int updateRetraining(Retraining Retraining) {
		Retraining.setModifiedTime(Calendar.getInstance().getTime());
		
		return commonDao.updateObject(Retraining);
	}
	
	/**
	 * 插入再培训计划
	 * @param riskCustomer
	 * @return
	 */
	public String insertRetraining(Retraining retraining) {
		if(retraining.getCreatedTime() == null){
			retraining.setCreatedTime(Calendar.getInstance().getTime());
		}
		if(retraining.getModifiedTime() == null){
			retraining.setModifiedTime(Calendar.getInstance().getTime());
		}
		commonDao.insertObject(retraining);
		return retraining.getId();
	}

	/**
	 * 删除再培训计划
	 * @param riskCustomerId
	 * @return
	 */
	public int deleteRetraining(String retrainingId) {
		return commonDao.deleteObject(Retraining.class, retrainingId);
	}

	/**
	 * 通过ID查找再培训计划
	 * @param filter
	 * @return
	 */
	public Retraining findRetrainingById(String retrainingId) {
		return commonDao.findObjectById(Retraining.class, retrainingId);
	}
	
}
