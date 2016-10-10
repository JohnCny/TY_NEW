package com.cardpay.pccredit.report.dao;

import java.util.List;

import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.QuailBankReturnMonitor;
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
public interface ProceMonitorDao {
	//“灵活金”/普通信用卡业务流程监测报表（统计期间：XXXX年XX月XX日-XXXX年XX月XX日，客户经理/微贷经理维度）
	public List<manaProceMonitor> getProceMonitorStatistical(StatisticalFilter filter);
	//“灵活金”/信用卡业务流程监测报表（统计期间：XXXX年XX月XX日-XXXX年XX月XX日，一级支行/二级支行维度）
	public List<bankProceMonitor> getBankProceMonitorStatistical(StatisticalFilter filter);


}
