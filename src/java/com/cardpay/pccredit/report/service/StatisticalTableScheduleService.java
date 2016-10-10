package com.cardpay.pccredit.report.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.cardpay.pccredit.report.dao.StatisticalTableDao;
import com.cardpay.pccredit.report.dao.comdao.StatisticalTableCommDao;
import com.cardpay.pccredit.report.model.StatisticalTable;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.util.date.DateHelper;

/**
 * 
 * 描述 ：客户经理每日统计信息
 * @author 张石树
 *
 * 2014-12-8 下午5:55:24
 */
@Service
public class StatisticalTableScheduleService {
	
	private Logger logger = Logger.getLogger(StatisticalTableScheduleService.class);
	
	@Autowired
	private StatisticalTableDao statisticalTableDao;
	
	@Autowired
	private StatisticalTableCommDao statisticalTableCommDao;
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	
	@Autowired
	private CommonDao commonDao;

	/**
     * 统计客户经理每天的信息
     * @param filter
     * @return
     */
	public void addStatisticalTable(){
		Calendar calendar = Calendar.getInstance();
		String createDateStr = DateHelper.getDateFormat(calendar.getTime(), "yyyy-MM-dd");
		int year = calendar.get(Calendar.YEAR);
		int yearDay = calendar.get(Calendar.DAY_OF_YEAR);
		try {
			//统计客户经理 发卡数、激活卡数、激活总额度、到卡数、授信总额度
			statisticalTableDao.statisticalTableInfo();
			//透支本金额度(单位：分) 透支总额度(单位：分) 日均透支本金(单位：分) 日均透支总额度(单位：分)
			List<HashMap<String, Object>> list = statisticalTableCommDao.getManagerStatisticalData();
			for (Iterator<HashMap<String, Object>> iterator = list.iterator(); iterator.hasNext();) {
				HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
				String managerId = (String) map.get("MANAGERID");
				String productId = (String) map.get("PRODUCTID");
				BigDecimal principalOverdraft = (BigDecimal) map.get("PRINCIPALOVERDRAFT");
				BigDecimal totalAmountOverdraft = (BigDecimal) map.get("TOTALAMOUNTOVERDRAFT");
				if(StringUtils.isNotEmpty(managerId)){
					StatisticalTable statisticalTable = statisticalTableCommDao.getStatisticalTable(createDateStr, managerId, productId);
					if(statisticalTable != null){
						statisticalTable.setOverdraftPrincipal(principalOverdraft.longValue() + "");
						statisticalTable.setOverdraftAmount(totalAmountOverdraft.longValue() + "");
						
						List<HashMap<String, Object>> tempList = statisticalTableCommDao.getManagerAverageDailyOverdraft(year, managerId);
						if(tempList != null && tempList.size() > 0){
							HashMap<String, Object> tempMap = tempList.get(0);
							BigDecimal tempPrincipalOverdraft = (BigDecimal) tempMap.get("PRINCIPALOVERDRAFT");
							BigDecimal tempTotalAmountOverdraft = (BigDecimal) tempMap.get("TOTALAMOUNTOVERDRAFT");
							BigDecimal averagePrincipalOverdraft = tempPrincipalOverdraft.divide(new BigDecimal(yearDay));
							BigDecimal averageTotalAmountOverdraft = tempTotalAmountOverdraft.divide(new BigDecimal(yearDay));
							statisticalTable.setOverdraftPrincipalAverage(averagePrincipalOverdraft.longValue() + "");
							statisticalTable.setOverdraftAmountAverage(averageTotalAmountOverdraft.longValue() + "");
							commonDao.updateObject(statisticalTable);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addStatisticalTable schedule error.", e);
		}
	}
}
