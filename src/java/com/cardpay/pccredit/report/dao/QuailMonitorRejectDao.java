package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.QuailBankRejectMonitor;
import com.cardpay.pccredit.report.model.QuailBankReturnMonitor;
import com.cardpay.pccredit.report.model.QuailManaRejectMonitor;
import com.cardpay.pccredit.report.model.QuailManaReturnMonitor;
import com.cardpay.pccredit.report.model.bankProceMonitor;
import com.cardpay.pccredit.report.model.manaProceMonitor;
import com.wicresoft.util.annotation.Mapper;


/**
 * ProceMonitorDao类的描述
 *
 * @author 王海东
 * @created on 2014-12-19
 * 
 * @version $Id:$
 */
@Mapper
public interface QuailMonitorRejectDao {
	
	//“灵活金”业务/普通信用卡进件质量监测报表统计期间：（XXXX年XX月XX日-XXXX年XX月XX日） 客户经理/微贷经理维度, RejectApprove
	public List<QuailManaRejectMonitor> getQuailRejectMonitorStatistical(StatisticalFilter filter);
	//“灵活金”/普通信用卡业务进件质量监测报表统计期间：（XXXX年XX月XX日-XXXX年XX月XX日）一级支行/二级支行维度,RejectApprove
	public List<QuailBankRejectMonitor> getQuailBankRejectMonitorStatistical(StatisticalFilter filter);

}
