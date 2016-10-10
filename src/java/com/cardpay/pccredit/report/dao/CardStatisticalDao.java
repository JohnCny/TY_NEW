package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.CardStatistical;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-11-27下午4:52:44
 */
@Mapper
public interface CardStatisticalDao {
	
	/**
     * 统计机构的“灵活金”发卡情况
     * @param filter
     * @return
     */
	public List<CardStatistical> getOrgCardStatistical(StatisticalFilter filter);
	
	/**
     * 统计客户经理的“灵活金”发卡情况
     * @param filter
     * @return
     */
	public List<CardStatistical> getManagerCardStatistical(StatisticalFilter filter);
}
