package com.cardpay.pccredit.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.dao.CardStatisticalDao;
import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.CardStatistical;

/**
 * 发卡情况统计
 * @author chenzhifang
 *
 * 2014-11-27下午4:51:59
 */
@Service
public class CardStatisticalService {
	
	@Autowired
	private CardStatisticalDao cardStatisticalDao;
	
	/**
     * 统计机构的“灵活金”发卡情况
     * @param filter
     * @return
     */
	public List<CardStatistical> getOrgCardStatistical(StatisticalFilter filter){
		return cardStatisticalDao.getOrgCardStatistical(filter);
	}
	
	/**
     * 统计客户经理的“灵活金”发卡情况
     * @param filter
     * @return
     */
	public List<CardStatistical> getManagerCardStatistical(StatisticalFilter filter){
		return cardStatisticalDao.getManagerCardStatistical(filter);
	}
}
