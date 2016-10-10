package com.cardpay.pccredit.riskControl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.riskControl.constant.RiskAttributeTypeEnum;
import com.cardpay.pccredit.riskControl.filter.RiskAttributeFilter;
import com.cardpay.pccredit.riskControl.model.RiskAttribute;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author chenzhifang
 * 
 *  风险属性
 *  
 * 2014-10-29下午3:19:14
 */
@Service
public class RiskAttributeService {
	
	@Autowired
	private CommonDao commonDao;
	
	/**
	 * 分页过滤查询风险属性列表
	 * @param filter
	 * @return
	 */
	public QueryResult<RiskAttribute> findRiskAttributesByFilter(RiskAttributeFilter filter) {
		
		return commonDao.findObjectsByFilter(RiskAttribute.class, filter);
	}
	
	/**
	 * 过滤查询风险属性列表
	 * @param filter
	 * @return
	 */
	public List<RiskAttribute> findRiskAttributeListByFilter(RiskAttributeFilter filter) {
		QueryResult<RiskAttribute> qr = findRiskAttributesByFilter(filter);
		return qr.getItems();
	}
	
	/**
	 * 过滤查询线上风险属性列表
	 * @param filter
	 * @return
	 */
	public List<RiskAttribute> findRiskOnlineAttributeList() {
		RiskAttributeFilter filter = new RiskAttributeFilter();
		// 线上风险属性
		filter.setType(RiskAttributeTypeEnum.ONLINE.toString());
		QueryResult<RiskAttribute> qr = findRiskAttributesByFilter(filter);
		return qr.getItems();
	}
}
