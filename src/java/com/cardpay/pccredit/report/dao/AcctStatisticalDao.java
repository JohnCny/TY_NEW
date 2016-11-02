package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.AcctStatistical;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-12-1上午10:04:54
 */
@Mapper
public interface AcctStatisticalDao {
	
	/**
     * 统计机构的“灵活金”透支情况
     * @param filter
     * @return
     */
	public List<AcctStatistical> getOrgAcctStatistical(StatisticalFilter filter);
	
	/**
     * 统计客户经理的“灵活金”透支情况
     * @param filter
     * @return
     */
	public List<AcctStatistical> getManagerAcctStatistical(StatisticalFilter filter);
}
