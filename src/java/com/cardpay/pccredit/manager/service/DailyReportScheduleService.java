package com.cardpay.pccredit.manager.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.cardpay.pccredit.manager.model.DailyAccountManager;
import com.cardpay.pccredit.manager.model.WeeklyAccountManager;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.util.date.DateHelper;

/**
 * 客户经理日报
 * 2016-03-18 下午2:09:26
 */
@Service
public class DailyReportScheduleService {
	public Logger log = Logger.getLogger(DailyReportScheduleService.class);
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;

	@Autowired
	private WeeklyAccountService weeklyAccountService;
	
	@Autowired
	private DailyAccountService dailyAccountService;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	PlatformTransactionManager txManager;
	
	
	
	/**
	 * 客户经理日报
	 * 周六生成下周的日报
	 * 系统自动生成
	 */
	public void insertWeekSchedule(){
	    log.info("【客户经理日报生成start】"+new Date()+"***********************************************");
	    DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
	    //record job
	    insBtachtask("rb","日报");
		try{
			  Calendar nextdate=Calendar.getInstance(Locale.CHINA);
			  nextdate.setTime(new Date());
			  nextdate.add(Calendar.WEEK_OF_MONTH, 1);
			  nextdate.set(Calendar.DAY_OF_WEEK, nextdate.getActualMinimum(Calendar.DAY_OF_WEEK));
			  nextdate.add(Calendar.DATE, 1);
			  //startDate 
			  String startDate=DateHelper.getDateFormat(nextdate.getTime(), "yyyy-MM-dd");
			  nextdate.add(Calendar.DATE, 4);
			  //endDate
			  String endDate=DateHelper.getDateFormat(nextdate.getTime(), "yyyy-MM-dd");
			/**
			 * 查找所有客户经理
			 */
			List<AccountManagerParameterForm> managerList=accountManagerParameterService.findAccountManagerParameterAll();
			for(AccountManagerParameterForm accountManagerParameterForm:managerList){
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
			}
			//upd task
			accountManagerParameterService.updBatchTaskFlow("100","rb",dateString);
		}catch(Exception e){
			e.printStackTrace();
			this.updBtachtask("001","rb",dateString);
			throw new RuntimeException(e);
		}
		log.info("【客户经理日报生成end】"+new Date()+"***********************************************");
	}
	
	
	/**
	 * 客户经理日报
	 * 系统手动调度重跑
	 */
	public void insertWeekScheduleByDate(String dateString){
	    log.info("【客户经理日报生成start】"+dateString+"***********************************************");
		try{
			  Calendar nextdate=Calendar.getInstance(Locale.CHINA);
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			  Date date = sdf.parse(dateString);
			  nextdate.setTime(date);
			  nextdate.add(Calendar.WEEK_OF_MONTH, 1);
			  nextdate.set(Calendar.DAY_OF_WEEK, nextdate.getActualMinimum(Calendar.DAY_OF_WEEK));
			  nextdate.add(Calendar.DATE, 1);
			  //startDate 
			  String startDate=DateHelper.getDateFormat(nextdate.getTime(), "yyyy-MM-dd");
			  nextdate.add(Calendar.DATE, 4);
			  //endDate
			  String endDate=DateHelper.getDateFormat(nextdate.getTime(), "yyyy-MM-dd");
			/**
			 * 查找所有客户经理
			 */
			List<AccountManagerParameterForm> managerList=accountManagerParameterService.findAccountManagerParameterAll();
			for(AccountManagerParameterForm accountManagerParameterForm:managerList){
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
			}
			//upd task
			accountManagerParameterService.updBatchTaskFlow("100","rb",dateString);
		}catch(Exception e){
			e.printStackTrace();
			this.updBtachtask("001","rb",dateString);
			throw new RuntimeException(e);
		}
		log.info("【客户经理日报生成end】"+dateString+"***********************************************");
	}
	
	//upd
	public void updBtachtask(String status,String batchCode,String dateString){
		DefaultTransactionDefinition  transStatus  = new DefaultTransactionDefinition();
		transStatus.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus one = txManager.getTransaction(transStatus);
		try{
			//upd task
			accountManagerParameterService.updBatchTaskFlow(status,batchCode,dateString);
			txManager.commit(one);
		}catch (Exception e){
			txManager.rollback(one);
		}
	}
		
	//insert
	public void insBtachtask(String status,String batchCode){
		DefaultTransactionDefinition  transStatus  = new DefaultTransactionDefinition();
		transStatus.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus one = txManager.getTransaction(transStatus);
		try{
			//insert task
			accountManagerParameterService.insertBatchTaskFlow(status,batchCode);
			txManager.commit(one);
		}catch (Exception e){
			txManager.rollback(one);
		}
	}
	
	
	
	/**
	 * 每日生成task,日终将数据移到历史表里
	 * 
	 */
	public void doTransferData(){
		log.info("【批处理task生成start】"+new Date()+"***********************************************");
		//将[status=100]数据移到历史表t_batch_task_log 
		//accountManagerParameterService.insertBatchTaskLogFlow();
		//task
		//日报批处理周六未执行 使用管理系统手工导入功能
		accountManagerParameterService.insertBatchTaskFlow("downLoad","下载和解压数据");//初始
		accountManagerParameterService.insertBatchTaskFlow("incre","增量数据");//初始
		//accountManagerParameterService.insertBatchTaskFlow("whole","全量数据");//初始
		
		log.info("【批处理task生成end】"+new Date()+"***********************************************");
	}
	
	public String findServer1(){
		String sql = "select * from dict where dict_type = '61.34.0.31' ";
		String PARAM = (String) commonDao.queryBySql(sql, null).get(0).get("TYPE_CODE");
		return PARAM;
	}
	
	public String findServer2(){
		String sql = "select * from dict where dict_type = '61.34.0.32' ";
		String PARAM = (String) commonDao.queryBySql(sql, null).get(0).get("TYPE_CODE");	
		return PARAM;
    }
	
	public String findServer3(){
		String sql = "select * from dict where dict_type = '61.34.0.33' ";
		String PARAM = (String) commonDao.queryBySql(sql, null).get(0).get("TYPE_CODE");
		return PARAM;
	}
	
	public String findServer4(){
		String sql = "select * from dict where dict_type = '61.34.0.34' ";
		String PARAM = (String) commonDao.queryBySql(sql, null).get(0).get("TYPE_CODE");
		return PARAM;
	}
}
