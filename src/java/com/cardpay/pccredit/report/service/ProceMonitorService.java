package com.cardpay.pccredit.report.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.report.dao.ProceMonitorDao;
import com.cardpay.pccredit.report.dao.QuailMonitorRejectDao;
import com.cardpay.pccredit.report.dao.QuailMonitorReturnDao;
import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.QuailBankRejectMonitor;
import com.cardpay.pccredit.report.model.QuailBankReturnMonitor;
import com.cardpay.pccredit.report.model.QuailManaRejectMonitor;
import com.cardpay.pccredit.report.model.QuailManaReturnMonitor;
import com.cardpay.pccredit.report.model.bankProceMonitor;
import com.cardpay.pccredit.report.model.manaProceMonitor;


/**
 * ProceMonitorService类的描述
 *
 * @author 王海东
 * @created on 2014-12-19
 * 
 * @version $Id:$
 */
@Service
public class ProceMonitorService {
	
	@Autowired
	private ProceMonitorDao proceMonitorDao;
	
	@Autowired
	private QuailMonitorRejectDao quailMonitorRejectDao;
	
	@Autowired
	private QuailMonitorReturnDao quailMonitorReturnDao;
	/**
	 * “灵活金”/普通信用卡业务流程监测报表（统计期间：XXXX年XX月XX日-XXXX年XX月XX日，客户经理/微贷经理维度）
	 * @param filter
	 * @return
	 */
	public List<manaProceMonitor> getProceMonitorStatistical(StatisticalFilter filter) {
		return proceMonitorDao.getProceMonitorStatistical(filter);
	}
	

	/**
	 * “灵活金”/信用卡业务流程监测报表（统计期间：XXXX年XX月XX日-XXXX年XX月XX日，一级支行/二级支行维度）
	 * @param filter
	 * @return
	 */
	public List<bankProceMonitor> getBankProceMonitorStatistical(StatisticalFilter filter) {
		return proceMonitorDao.getBankProceMonitorStatistical(filter);
	}
	
	/**
	 * ReturnApprove
	 * “灵活金”业务/普通信用卡进件质量监测报表统计期间：（XXXX年XX月XX日-XXXX年XX月XX日） 客户经理/微贷经理维度
	 * @param filter
	 * @return
	 */
	public List<QuailManaReturnMonitor> getQuailReturnMonitorStatistical(StatisticalFilter filter) {
		return quailMonitorReturnDao.getQuailReturnMonitorStatistical(filter);
	}
	/**
	 * ReturnApprove
	 * “灵活金”业务/普通信用卡进件质量监测报表统计期间：（XXXX年XX月XX日-XXXX年XX月XX日）一级支行/二级支行维度
	 * @param filter
	 * @return
	 */
	public List<QuailBankReturnMonitor> getQuailBankReturnMonitorStatistical(StatisticalFilter filter) {
		return quailMonitorReturnDao.getQuailBankReturnMonitorStatistical(filter);
	}
	
	/**
	 * RejectApprove
	 * “灵活金”业务/普通信用卡进件质量监测报表统计期间：（XXXX年XX月XX日-XXXX年XX月XX日） 客户经理/微贷经理维度
	 * @param filter
	 * @return
	 */
	public List<QuailManaRejectMonitor> getQuailRejectMonitorStatistical(StatisticalFilter filter) {
		return quailMonitorRejectDao.getQuailRejectMonitorStatistical(filter);
	}
	/**
	 * RejectApprove
	 * “灵活金”业务/普通信用卡进件质量监测报表统计期间：（XXXX年XX月XX日-XXXX年XX月XX日）一级支行/二级支行维度
	 * @param filter
	 * @return
	 */
	public List<QuailBankRejectMonitor> getQuailBankRejectMonitorStatistical(StatisticalFilter filter) {
		return quailMonitorRejectDao.getQuailBankRejectMonitorStatistical(filter);
	}

}
