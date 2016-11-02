package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerManagerTarget;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.manager.constant.ManageLederPeriod;
import com.cardpay.pccredit.manager.constant.ManagerTargetType;
import com.cardpay.pccredit.manager.dao.StatisticsManagerDao;
import com.cardpay.pccredit.manager.model.DailyAccountManager;
import com.cardpay.pccredit.manager.model.LedgerAccountManager;
import com.cardpay.pccredit.manager.model.WeeklyAccountManager;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.WeeklyAccountManagerForm;
import com.cardpay.pccredit.system.model.SystemConfiguration;
import com.cardpay.pccredit.system.service.SystemConfigurationService;
import com.wicresoft.util.date.DateHelper;

/**
 * 客户经理统计 周/月/年
 *
 * @author Evans zhang
 *
 * 2014-11-18 下午2:09:26
 */
@Service
public class StatisticsScheduleService {
	
	@Autowired
	private StatisticsManagerDao statisticsManagerDao;
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;

	@Autowired
	private WeeklyAccountService weeklyAccountService;
	
	@Autowired
	private DailyAccountService dailyAccountService;
	
	@Autowired
	private ManagerBelongMapService  managerBelongMapService;
	
	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private LedgerAccountManagerService ledgerAccountManagerService;
	
	@Autowired
	private SystemConfigurationService systemConfigurationService;


	/**
	 * 判断系统是否按那个周期统计
	 */
	public boolean isLedger(ManagerTargetType managerTargetType){
		List<SystemConfiguration> systemConfigurationList=systemConfigurationService.findSystemConfigurationByCode(ManageLederPeriod.period);
		if(systemConfigurationList!=null&&systemConfigurationList.size()>0){
		if(managerTargetType.name().equals(systemConfigurationList.get(0).getSysValues())){
			return true;
		}
		}
		return false;
	}
	
	/**
	 * 统计客户经理生成上星期周报信息统计客户经理 授信额度 、进件数 、拜访次数、拜访客户数 、组进件数、组授信额度 。下个星期日报信息（星期天晚上生成）
	 */
	public void insertWeekSchedule(){
		try{
		Map<String, Integer> mapCountApply=new HashMap<String, Integer>();
		Map<String, Integer> mapCountApplication=new HashMap<String, Integer>();
		Map<String, Integer> mapCountNumber=new HashMap<String, Integer>();
		Map<String, Integer> mapCountCustomer=new HashMap<String, Integer>();
		
		  Date startTime=null;
		  Calendar date=Calendar.getInstance(Locale.CHINA);
		  date.setTime(new Date());
		  date.add(Calendar.DAY_OF_MONTH, -7);
		  startTime=date.getTime();
		  
		  Calendar nextdate=Calendar.getInstance(Locale.CHINA);
		  nextdate.setTime(new Date());
		  nextdate.add(Calendar.WEEK_OF_MONTH, 1);
		  nextdate.set(Calendar.DAY_OF_WEEK, nextdate.getActualMinimum(Calendar.DAY_OF_WEEK));
		  nextdate.add(Calendar.DATE, 1);
		  String startDate=DateHelper.getDateFormat(nextdate.getTime(), "yyyy-MM-dd");
		  nextdate.add(Calendar.DATE, 4);
		  String endDate=DateHelper.getDateFormat(nextdate.getTime(), "yyyy-MM-dd");
		  
		  String startMonthTime=null;
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
			String startTimeStr=DateHelper.getDateFormat(cal.getTime(), "yyyy-MM-dd");
			startMonthTime=startTimeStr+" 00:00:00";
			
		
		//查找所有客户经理
		List<AccountManagerParameterForm> managerList=accountManagerParameterService.findAccountManagerParameterAll();
		for(AccountManagerParameterForm accountManagerParameterForm:managerList){
			//插入下星期周报和日报
			try{
			String title=accountManagerParameterForm.getDisplayName()+"("+startDate+"到"+endDate+")周报";
			WeeklyAccountManager weeklyAccountManager=new WeeklyAccountManager();
			weeklyAccountManager.setCustomerManagerId(accountManagerParameterForm.getUserId());
			weeklyAccountManager.setTitle(title);
			weeklyAccountManager.setCreatedTime(new Date());
			weeklyAccountManager.setModifiedTime(new Date());
			weeklyAccountService.insertWeeklyAccount(weeklyAccountManager);
			for(int i=1;i<=5;i++){
				DailyAccountManager dailyAccountManager=new DailyAccountManager();
				dailyAccountManager.setWeeklyId(weeklyAccountManager.getId());
				dailyAccountManager.setWhatDay(i);
				dailyAccountManager.setCreatedTime(new Date());
				dailyAccountManager.setModifiedTime(new Date());
				dailyAccountService.insertDailyAccount(dailyAccountManager);
			}
			
			//统计上周的客户信息 授信额度
			Integer totalApplyQuota=statisticsManagerDao.findCustomerApplyQuota(DateHelper.getDateFormat(startTime,"yyyy-MM-dd HH:mm:ss"),DateHelper.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),accountManagerParameterForm.getUserId());
			mapCountApply.put(accountManagerParameterForm.getUserId(),totalApplyQuota);
			//统计上周的客户信息 进件数
			Integer totalApplicationNumber=statisticsManagerDao.findCountCustomer(DateHelper.getDateFormat(startTime,"yyyy-MM-dd HH:mm:ss"),DateHelper.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),accountManagerParameterForm.getUserId());
			mapCountApplication.put(accountManagerParameterForm.getUserId(), totalApplicationNumber);
			//统计拜访次数
			List<Map<String, Object>> mapList=statisticsManagerDao.findVisitCustomerActionCount(DateHelper.getDateFormat(startTime,"yyyy-MM-dd HH:mm:ss"),DateHelper.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),accountManagerParameterForm.getUserId());
			
			Integer totalVisitCount=0;
 			for(Map<String, Object> map:mapList){
	 				if(map.get("visitCount")!=null){
	 					totalVisitCount=totalVisitCount+Integer.parseInt(map.get("visitCount").toString());
	 				}
 			}
 			
 			mapCountNumber.put(accountManagerParameterForm.getUserId(), totalVisitCount);
 			
 			//统计拜访客户数
 			Integer countCustomer=statisticsManagerDao.findCountVisitCustomer(DateHelper.getDateFormat(startTime,"yyyy-MM-dd HH:mm:ss"),DateHelper.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),accountManagerParameterForm.getUserId());
 			mapCountCustomer.put(accountManagerParameterForm.getUserId(), countCustomer); 		
			}catch(Exception e){
				System.out.println("======================1"+accountManagerParameterForm.getUserId());
			}
		}
		
		for(AccountManagerParameterForm accountManagerParameterForm:managerList){
			try{
			//查询客户经理目标数
			CustomerManagerTarget customerManagerTarget=accountManagerParameterService.getcustomerManagerTargetBymanagerIdDate(accountManagerParameterForm.getUserId(),ManagerTargetType.year.name());
			if(customerManagerTarget!=null){
			//统计本月的客户信息 授信额度
			Integer totalMonthApplyQuota=statisticsManagerDao.findCustomerApplyQuota(startMonthTime,DateHelper.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),accountManagerParameterForm.getUserId());
			//统计本月的客户信息 进件数
			Integer totalMonthApplicationNumber=statisticsManagerDao.findCountCustomer(startMonthTime,DateHelper.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),accountManagerParameterForm.getUserId());
			
			//统计组的授信额度
			Integer groupApply=0;
			Integer groupApplication=0;
			List<AccountManagerParameterForm> list=managerBelongMapService.findSubListManagerByManagerId(accountManagerParameterForm.getUserId());
			for(AccountManagerParameterForm chirdManager:list){
				groupApply=groupApply+mapCountApply.get(chirdManager.getUserId());
				groupApplication=groupApplication+mapCountApplication.get(chirdManager.getUserId());
			}
			
			List<WeeklyAccountManagerForm> listWeekly=weeklyAccountService.findWeeklyAccountByManagerId(DateHelper.getDateFormat(startTime,"yyyy-MM-dd HH:mm:ss"),DateHelper.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),accountManagerParameterForm.getUserId());
			
			WeeklyAccountManagerForm weeklyAccountManagerForm=null;
			if(listWeekly!=null){
				weeklyAccountManagerForm=listWeekly.get(0);
				WeeklyAccountManager weeklyAccountManager=new WeeklyAccountManager();
				weeklyAccountManager.setActualCredit(String.valueOf(mapCountApply.get(accountManagerParameterForm.getUserId())));
				weeklyAccountManager.setActualNumber(mapCountApplication.get(accountManagerParameterForm.getUserId()));
				weeklyAccountManager.setActualNumberCustomers(mapCountCustomer.get(accountManagerParameterForm.getUserId()));
				weeklyAccountManager.setActualNumberVisit(mapCountNumber.get(accountManagerParameterForm.getUserId()));
				weeklyAccountManager.setGroupCredit(String.valueOf(groupApply));
				weeklyAccountManager.setGroupNumber(groupApplication);
				weeklyAccountManager.setMonthlyCredit(String.valueOf(totalMonthApplyQuota/Float.parseFloat(customerManagerTarget.getTargetCredit())));
				weeklyAccountManager.setMonthlyNumber(String.valueOf(totalMonthApplicationNumber/customerManagerTarget.getTargetNumber()));
				weeklyAccountManager.setId(weeklyAccountManagerForm.getId());
				weeklyAccountService.updateWeeklyAccount(weeklyAccountManager);
			}
			}
			//台账
			System.out.println("台帐：");
			if(isLedger(ManagerTargetType.weekly)){
			LedgerAccountManager  ledgerAccountManager=new LedgerAccountManager();
			 List<CustomerInfor> customerList=customerInforService.findCustomerByManagerId(accountManagerParameterForm.getUserId());
			ledgerAccountManager.setTubeNumber(String.valueOf(customerList.size())); //管户数
			ledgerAccountManager.setManagementCycle(ManagerTargetType.weekly.name());
			//客户拜访数 (营销、维护、催收)
			Integer countCustomer=statisticsManagerDao.findCountVisitCustomer(DateHelper.getDateFormat(startTime,"yyyy-MM-dd HH:mm:ss"),DateHelper.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),accountManagerParameterForm.getUserId());
			ledgerAccountManager.setCustomerVisitNumber(String.valueOf(countCustomer));
			
			//客户维护数 (营销、维护)
			Integer countCustomerYw=statisticsManagerDao.findCountVisitCustomerYw(DateHelper.getDateFormat(startTime,"yyyy-MM-dd HH:mm:ss"),DateHelper.getDateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),accountManagerParameterForm.getUserId());
			ledgerAccountManager.setCustomerMaintenanceNumber(String.valueOf(countCustomerYw));
			
			ledgerAccountManager.setCreatedTime(DateHelper.normalizeDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			ledgerAccountManagerService.insertLedgerAccountManager(ledgerAccountManager);
			
			}
		
			}catch(Exception e){
				System.out.println("=======================2"+accountManagerParameterForm.getUserId());
				e.printStackTrace();
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	public void insertMonthSchedule(){
		//台账
		if(isLedger(ManagerTargetType.month)){
		String startTime=null;
		String endTime=null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMinimum(Calendar.DAY_OF_MONTH));  
		String startTimeStr=DateHelper.getDateFormat(cal.getTime(), "yyyy-MM-dd");
		startTime=startTimeStr+" 00:00:00";
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
		endTime=DateHelper.getDateFormat(cal.getTime(), "yyyy-MM-dd")+" 23:59:59";
		//查找所有客户经理
		List<AccountManagerParameterForm> managerList=accountManagerParameterService.findAccountManagerParameterAll();
		for(AccountManagerParameterForm accountManagerParameterForm:managerList){
			LedgerAccountManager  ledgerAccountManager=new LedgerAccountManager();
			 List<CustomerInfor> customerList=customerInforService.findCustomerByManagerId(accountManagerParameterForm.getUserId());
			ledgerAccountManager.setTubeNumber(String.valueOf(customerList.size())); //管户数
			ledgerAccountManager.setManagementCycle(ManagerTargetType.month.name());
			//客户拜访数 (营销、维护、催收)
			Integer countCustomer=statisticsManagerDao.findCountVisitCustomer(startTime,endTime,accountManagerParameterForm.getUserId());
			ledgerAccountManager.setCustomerVisitNumber(String.valueOf(countCustomer));
			
			//客户维护数 (营销、维护)
			Integer countCustomerYw=statisticsManagerDao.findCountVisitCustomerYw(startTime,endTime,accountManagerParameterForm.getUserId());
			ledgerAccountManager.setCustomerMaintenanceNumber(String.valueOf(countCustomerYw));
			
			ledgerAccountManager.setCreatedTime(DateHelper.normalizeDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			ledgerAccountManagerService.insertLedgerAccountManager(ledgerAccountManager);
		}
		}
	}
	
	public void insertYearSchedule(){
		//台账
		if(isLedger(ManagerTargetType.year)){
		String startTime=null;
		String endTime=null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.YEAR, -1);
		startTime=cal.get(Calendar.YEAR)+"-01-01 00:00:00";
		endTime=cal.get(Calendar.YEAR)+"-12-31 23:59:59";
		
		endTime=DateHelper.getDateFormat(cal.getTime(), "yyyy-MM-dd")+" 23:59:59";
		//查找所有客户经理
		List<AccountManagerParameterForm> managerList=accountManagerParameterService.findAccountManagerParameterAll();
		for(AccountManagerParameterForm accountManagerParameterForm:managerList){
			LedgerAccountManager  ledgerAccountManager=new LedgerAccountManager();
			 List<CustomerInfor> customerList=customerInforService.findCustomerByManagerId(accountManagerParameterForm.getUserId());
			ledgerAccountManager.setTubeNumber(String.valueOf(customerList.size())); //管户数
			ledgerAccountManager.setManagementCycle(ManagerTargetType.year.name());
			//客户拜访数 (营销、维护、催收)
			Integer countCustomer=statisticsManagerDao.findCountVisitCustomer(startTime,endTime,accountManagerParameterForm.getUserId());
			ledgerAccountManager.setCustomerVisitNumber(String.valueOf(countCustomer));
			
			//客户维护数 (营销、维护)
			Integer countCustomerYw=statisticsManagerDao.findCountVisitCustomerYw(startTime,endTime,accountManagerParameterForm.getUserId());
			ledgerAccountManager.setCustomerMaintenanceNumber(String.valueOf(countCustomerYw));
			
			ledgerAccountManager.setCreatedTime(DateHelper.normalizeDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			
			ledgerAccountManagerService.insertLedgerAccountManager(ledgerAccountManager);
		}
		}
	}
	public Integer findCustomerApplyQuota(String customerManagerId){
		return statisticsManagerDao.findCustomerApplyQuota(null, null, customerManagerId);
	}
}
