package com.cardpay.pccredit.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.dao.AcctStatisticalDao;
import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.AcctStatistical;

/**
 * 账户统计分析
 * @author chenzhifang
 *
 * 2014-12-1上午10:03:57
 */
@Service
public class AcctStatisticalService {
	
	@Autowired
	private AcctStatisticalDao acctStatisticalDao;
	
	/**
     * 统计机构的“灵活金”发卡情况
     * @param filter
     * @return
     */
	public List<AcctStatistical> getOrgAcctStatistical(StatisticalFilter filter){
		return acctStatisticalDao.getOrgAcctStatistical(filter);
	}
	
	/**
     * 统计客户经理的“灵活金”发卡情况
     * @param filter
     * @return
     */
	public List<AcctStatistical> getManagerAcctStatistical(StatisticalFilter filter){
		return acctStatisticalDao.getManagerAcctStatistical(filter);
	}
}
