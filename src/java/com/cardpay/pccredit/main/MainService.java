package com.cardpay.pccredit.main;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.constant.MaintenanceEndResultEnum;
import com.cardpay.pccredit.customer.service.CustomerMarketingService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.divisional.constant.DivisionalConstant;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;
import com.cardpay.pccredit.manager.dao.StatisticsManagerDao;
import com.cardpay.pccredit.manager.service.InformationMaintenanceService;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEndResultEnum;
import com.cardpay.pccredit.riskControl.dao.NplsInfomationDao;
import com.cardpay.pccredit.riskControl.service.RiskCustomerCollectionService;

@Service
public class MainService {

	@Autowired
	private CustomerMarketingService customerMarketingService;
	
	@Autowired
	private DivisionalService divisionalService;
	
	@Autowired
	private CustomerApplicationInfoService customerApplicationInfoService;
	
	@Autowired
	private MaintenanceService maintenanceService;
	
	@Autowired
	private RiskCustomerCollectionService riskCustomerCollectionService;
	
	@Autowired
	private StatisticsManagerDao statisticsManagerDao;
	
	@Autowired
	private NplsInfomationDao nplsInfomationDao;
	
	@Autowired
	private InformationMaintenanceService informationMaintenanceService;
	
	public HashMap<String,Integer> getHomeData(String userId,int day){
		HashMap<String,Integer> homeData = new HashMap<String,Integer>();
		int marketing_size = 99;
		int maintenance_size = 99;
		int collection_size = 99;
		if(day!=0){
			//营销计划条数
			marketing_size = customerMarketingService.findMarketingCountByDay(userId,day);
			//客户维护计划条数
			maintenance_size = maintenanceService.findMaintenanceCountByDay(userId, MaintenanceEndResultEnum.maintaining.toString(), day);
			//催收
			collection_size = riskCustomerCollectionService.findCollectionCountByDay(userId, RiskCustomerCollectionEndResultEnum.collection.toString(), day);;
			//7日内预期还款
			int promise_size = riskCustomerCollectionService.findCollectionPromiseCountByDay(userId, RiskCustomerCollectionEndResultEnum.repaymentcommitments.toString(), day);
			homeData.put("promise", promise_size);
		}else{
			//营销计划条数
			marketing_size = customerMarketingService.findMarketingCountToday(userId);
			//进件分配条数
			int divisional_size = divisionalService.findDivisionalCounsToday(userId, DivisionalConstant.DISTRIBUTION, DivisionalProgressEnum.charge.toString());
			//拒绝进件条数
			int application_reject_size = customerApplicationInfoService.findCustomerApplicationInfoCount(userId,Constant.REFUSE_INTOPICES,null);
			//补充调查进件条数
			int application_nopass_size = customerApplicationInfoService.findCustomerApplicationInfoCount(userId,Constant.NOPASS_REPLENISH_INTOPICES,null);
			//客户维护计划条数
			maintenance_size = maintenanceService.findMaintenanceCountToday(userId, MaintenanceEndResultEnum.maintaining.toString());
			//客户信息渠道维护计划条数
			int officer_channels_size = informationMaintenanceService.findInformationPlanCountByDay(userId);
			//客户催收计划条数
			collection_size = riskCustomerCollectionService.findCollectionCountToday(userId, RiskCustomerCollectionEndResultEnum.collection.toString());
			// 风险事项警示
			homeData.put("riskNumber",statisticsManagerDao.findRiskWarningByManagerId(userId));
			// 问责
			homeData.put("abilityNumber",statisticsManagerDao.findAccountabilityRecordByManagerId(userId));
			//产品发布
			homeData.put("productNumber",statisticsManagerDao.findProductByManagerId(userId));
			
			homeData.put("divisional", divisional_size);
			homeData.put("applicationReject", application_reject_size);
			homeData.put("applicationInfo", application_nopass_size);
			homeData.put("officerChannels", officer_channels_size);
		}
		homeData.put("collection", collection_size);
		homeData.put("marketing", marketing_size);
		homeData.put("maintenance", maintenance_size);
		return homeData;
	}
}
