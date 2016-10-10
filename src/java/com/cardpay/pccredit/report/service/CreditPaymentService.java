package com.cardpay.pccredit.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.dao.CreditPaymentDao;
import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.CreditPayment;

/**
 * 用信还款情况统计
 * @author chenzhifang
 *
 * 2014-12-17下午3:44:01
 */
@Service
public class CreditPaymentService {
	@Autowired
	private CreditPaymentDao creditPaymentDao;
	
	/**
     * 统计机构的“灵活金”用信还款情况
     * @param filter
     * @return
     */
	public List<CreditPayment> getOrgCreditPayment(StatisticalFilter filter){
		return creditPaymentDao.getOrgCreditPayment(filter);
	}
	
	/**
     * 统计客户经理的“灵活金”用信还款情况
     * @param filter
     * @return
     */
	public List<CreditPayment> getManagerCreditPayment(StatisticalFilter filter){
		return creditPaymentDao.getManagerCreditPayment(filter);
	}
	
}
