package com.cardpay.pccredit.riskControl.dao;

import java.util.List;

import com.cardpay.pccredit.riskControl.filter.AgrCrdXykCunegFilter;
import com.cardpay.pccredit.riskControl.model.AgrCrdXykCuneg;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-12-22下午4:24:00
 */
@Mapper
public interface AgrCrdXykCunegDao {
	
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public List<AgrCrdXykCuneg> findAgrCrdXykCunegsByFilter(AgrCrdXykCunegFilter filter);
	
	/**
	 * 统计风险事项审核流程表记录数
	 * @param filter
	 * @return
	 */
	public int findAgrCrdXykCunegsCountByFilter(AgrCrdXykCunegFilter filter);
}
