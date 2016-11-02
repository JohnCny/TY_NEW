package com.cardpay.pccredit.manager.dao;

import java.util.List;

import com.cardpay.pccredit.jnpad.model.RetrainingVo;
import com.cardpay.pccredit.manager.filter.RetrainingFilter;
import com.cardpay.pccredit.manager.model.Retraining;
import com.wicresoft.util.annotation.Mapper;

/**
 * 再培训计划表dao
 * @author chenzhifang
 *
 * 2014-11-12上午10:42:26
 */
@Mapper
public interface RetrainingDao {
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<Retraining> findRetrainingsByFilter(RetrainingFilter filter);
	
	/**
	 * 统计客户经理再培训计划记录数
	 * @param filter
	 * @return
	 */
	public int findRetrainingsCountByFilter(RetrainingFilter filter);
	
	public List<RetrainingVo> findRetrainingsVoByFilter(RetrainingFilter filter);
}
