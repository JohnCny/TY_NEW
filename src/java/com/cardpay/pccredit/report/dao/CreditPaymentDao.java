package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.CreditPayment;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author chenzhifang
 *
 * 2014-12-17下午3:46:09
 */
@Mapper
public interface CreditPaymentDao {
	/**
     * 统计机构的“灵活金”用信还款情况
     * @param filter
     * @return
     */
	public List<CreditPayment> getOrgCreditPayment(StatisticalFilter filter);
	
	/**
     * 统计客户经理的“灵活金”用信还款情况
     * @param filter
     * @return
     */
	public List<CreditPayment> getManagerCreditPayment(StatisticalFilter filter);
}
