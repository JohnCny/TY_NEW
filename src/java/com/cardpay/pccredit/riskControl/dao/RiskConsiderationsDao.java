package com.cardpay.pccredit.riskControl.dao;

import java.util.List;

import com.cardpay.pccredit.riskControl.filter.RiskConsiderationsFilter;
import com.cardpay.pccredit.riskControl.model.RiskConsiderations;
import com.wicresoft.util.annotation.Mapper;


/**
 * @author chenzhifang
 *
 * 2014-11-5上午10:52:12
 */
@Mapper
public interface RiskConsiderationsDao {
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<RiskConsiderations> findRiskConsiderationsByFilter(RiskConsiderationsFilter filter);
	
	/**
	 * 统计风险属性数
	 * @param filter
	 * @return
	 */
	public int findRiskConsiderationsCountByFilter(RiskConsiderationsFilter filter);

}
