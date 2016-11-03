package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerManagerTarget;
import com.cardpay.pccredit.manager.constant.ManagerTargetType;
import com.cardpay.pccredit.manager.dao.AccountManagerParameterDao;
import com.cardpay.pccredit.manager.dao.comdao.AccountManagerParameterComdao;
import com.cardpay.pccredit.manager.filter.AccountManagerParameterFilter;
import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.model.BatchTask;
import com.cardpay.pccredit.manager.model.TJxParameters;
import com.cardpay.pccredit.manager.model.TJxSpecificParameters;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;


/**
 * AccountManagerParameterService类的描述
 *
 * @author 王海东
 * @created on 2014-11-7
 * 
 * @version $Id:$
 */
@Service
public class AccountManagerParameterService {
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private AccountManagerParameterDao accountManagerParameterDao;
	
	@Autowired
	private AccountManagerParameterComdao accountManagerParameterComdao;
	

	public String insertAccountManagerParameter(AccountManagerParameter accountManagerParameter) {
		accountManagerParameter.setCreatedTime(Calendar.getInstance().getTime());
		if(accountManagerParameter != null){
			String basePay = accountManagerParameter.getBasePay();
			if(basePay !=null && basePay !=""){
				Double basePayDouble = Double.parseDouble(basePay) * 100;
				String basePayValue = basePayDouble.toString();
				accountManagerParameter.setBasePay(basePayValue);
			   }
			}
		commonDao.insertObject(accountManagerParameter);
		return accountManagerParameter.getId();
	}
	/**
	 * 客户经理参数配置新增
	 * @param request
	 */
	public void insertAccountManagerParameter(HttpServletRequest request) {
		
		Calendar calendar = Calendar.getInstance();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		
		String customerManagerId= request.getParameter("userId");
		String accountManagerParameterId= request.getParameter("accountManagerParameterId");
		String levelInformation= request.getParameter("levelInformation");
		String entryTime= request.getParameter("entryTime");
		Date entryTimedate = DateHelper.getDateFormat(entryTime, "yyyy-MM-dd");
		String basePay= request.getParameter("basePay");
		AccountManagerParameter accountManagerParameter = new AccountManagerParameter();
	
		accountManagerParameter.setLevelInformation(levelInformation);
		accountManagerParameter.setEntryTime(entryTimedate);
		accountManagerParameter.setBasePay(basePay);
		
		accountManagerParameter.setModifiedBy(userId);
		accountManagerParameter.setModifiedTime(calendar.getTime());
		if(accountManagerParameterId == "" || accountManagerParameterId == null){
		accountManagerParameter.setUserId(customerManagerId);
		accountManagerParameter.setCreatedBy(userId);
		accountManagerParameter.setCreatedTime(calendar.getTime());
		commonDao.insertObject(accountManagerParameter);
		}else{
		accountManagerParameter.setId(accountManagerParameterId);
		commonDao.updateObject(accountManagerParameter);	
			
		}
//		String[] id= request.getParameterValues("id");
//		String[] targetDate= request.getParameterValues("targetDate");
//		String[] targetCredit= request.getParameterValues("targetCredit");
//		String[] targetNumber= request.getParameterValues("targetNumber");
//		String[] targetNumberVisit= request.getParameterValues("targetNumberVisit");
//		String[] targetNumberCustomers= request.getParameterValues("targetNumberCustomers");
//		String[] activeNumber= request.getParameterValues("activeNumber");
//		String[] activationNumber= request.getParameterValues("activationNumber");
//		
//		for(int i=0;i<targetDate.length;i++){
//			CustomerManagerTarget customerManagerTarget = new CustomerManagerTarget();
//			String targetDateValue= targetDate[i];
//			String targetCreditValue=targetCredit[i];
//			String idValue=id[i];
//			if(targetNumber[i] !="" && targetNumber[i] !=null){
//			int targetNumberValue=Integer.parseInt(targetNumber[i]);
//			customerManagerTarget.setTargetNumber(targetNumberValue);
//			}
//			if(targetNumberVisit[i] !="" && targetNumberVisit[i] !=null){
//			int targetNumberVisitValue=Integer.parseInt(targetNumberVisit[i]);
//			customerManagerTarget.setTargetNumberVisit(targetNumberVisitValue);
//			
//			}
//			if(targetNumberCustomers[i] !="" && targetNumberCustomers[i] !=null){
//			int targetNumberCustomersValue=Integer.parseInt(targetNumberCustomers[i]);
//			customerManagerTarget.setTargetNumberCustomers(targetNumberCustomersValue);
//			}
//			if(activeNumber[i] !="" && activeNumber[i] !=null){
//			int activeNumberValue=Integer.parseInt(activeNumber[i]);
//			customerManagerTarget.setActiveNumber(activeNumberValue);
//			}
//			if(activationNumber[i] !="" && activationNumber[i] !=null){
//			int activationNumberValue=Integer.parseInt(activationNumber[i]);
//			customerManagerTarget.setActivationNumber(activationNumberValue);
//			}
//			customerManagerTarget.setTargetDate(targetDateValue);
//			customerManagerTarget.setTargetCredit(targetCreditValue);
//			
//		
//			customerManagerTarget.setModifiedBy(userId);
//			customerManagerTarget.setModifiedTime(calendar.getTime());
//			customerManagerTarget.setCustomerManagerId(customerManagerId);
//			if(idValue == "" || idValue == null){
//				customerManagerTarget.setCreatedBy(userId);
//				customerManagerTarget.setCreatedTime(calendar.getTime());
//				commonDao.insertObject(customerManagerTarget);	
//				
//			}else{
//				customerManagerTarget.setId(idValue);
//				commonDao.updateObject(customerManagerTarget);
//			}	
//					
//			}
//	
		}
		
	/**
	 * 客户经理参数配置修改
	 * @param request
	 */
	public void updateAccountManagerParameter(HttpServletRequest request) {
		
		Calendar calendar = Calendar.getInstance();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		
		String customerManagerId= request.getParameter("userId");
		String accountManagerParameterId= request.getParameter("accountManagerParameterId");
		String levelInformation= request.getParameter("levelInformation");
		String entryTime= request.getParameter("entryTime");
		Date entryTimedate = DateHelper.getDateFormat(entryTime, "yyyy-MM-dd");
		String basePay= request.getParameter("basePay");
		AccountManagerParameter accountManagerParameter = new AccountManagerParameter();
	
		accountManagerParameter.setLevelInformation(levelInformation);
		accountManagerParameter.setEntryTime(entryTimedate);
		accountManagerParameter.setBasePay(basePay);
		
		accountManagerParameter.setModifiedBy(userId);
		accountManagerParameter.setModifiedTime(calendar.getTime());
		if(accountManagerParameterId == "" || accountManagerParameterId == null){
		accountManagerParameter.setUserId(customerManagerId);
		accountManagerParameter.setCreatedBy(userId);
		accountManagerParameter.setCreatedTime(calendar.getTime());
		commonDao.insertObject(accountManagerParameter);
		}else{
		accountManagerParameter.setId(accountManagerParameterId);
		commonDao.updateObject(accountManagerParameter);	
			
		}
//		String[] id= request.getParameterValues("id");
//		String[] targetDate= request.getParameterValues("targetDate");
//		String[] targetCredit= request.getParameterValues("targetCredit");
//		String[] targetNumber= request.getParameterValues("targetNumber");
//		String[] targetNumberVisit= request.getParameterValues("targetNumberVisit");
//		String[] targetNumberCustomers= request.getParameterValues("targetNumberCustomers");
//		String[] activeNumber= request.getParameterValues("activeNumber");
//		String[] activationNumber= request.getParameterValues("activationNumber");
//		
//		for(int i=0;i<targetDate.length;i++){
//			CustomerManagerTarget customerManagerTarget = new CustomerManagerTarget();
//			String targetDateValue= targetDate[i];
//			String targetCreditValue=targetCredit[i];
//			String idValue=id[i];
//			if(targetNumber[i] !="" && targetNumber[i] !=null){
//			int targetNumberValue=Integer.parseInt(targetNumber[i]);
//			customerManagerTarget.setTargetNumber(targetNumberValue);
//			}
//			if(targetNumberVisit[i] !="" && targetNumberVisit[i] !=null){
//			int targetNumberVisitValue=Integer.parseInt(targetNumberVisit[i]);
//			customerManagerTarget.setTargetNumberVisit(targetNumberVisitValue);
//			
//			}
//			if(targetNumberCustomers[i] !="" && targetNumberCustomers[i] !=null){
//			int targetNumberCustomersValue=Integer.parseInt(targetNumberCustomers[i]);
//			customerManagerTarget.setTargetNumberCustomers(targetNumberCustomersValue);
//			}
//			if(activeNumber[i] !="" && activeNumber[i] !=null){
//			int activeNumberValue=Integer.parseInt(activeNumber[i]);
//			customerManagerTarget.setActiveNumber(activeNumberValue);
//			}
//			if(activationNumber[i] !="" && activationNumber[i] !=null){
//			int activationNumberValue=Integer.parseInt(activationNumber[i]);
//			customerManagerTarget.setActivationNumber(activationNumberValue);
//			}
//			customerManagerTarget.setTargetDate(targetDateValue);
//			customerManagerTarget.setTargetCredit(targetCreditValue);
//			
//		
//			customerManagerTarget.setModifiedBy(userId);
//			customerManagerTarget.setModifiedTime(calendar.getTime());
//			customerManagerTarget.setCustomerManagerId(customerManagerId);
//			if(idValue == "" || idValue == null){
//				customerManagerTarget.setCreatedBy(userId);
//				customerManagerTarget.setCreatedTime(calendar.getTime());
//				commonDao.insertObject(customerManagerTarget);	
//				
//			}else{
//				customerManagerTarget.setId(idValue);
//				commonDao.updateObject(customerManagerTarget);
//			}	
					
			//}
	
		}
		
	/**
	 * 客户经理层级业绩目标修改
	 * @param request
	 */
	public void updatecustomerManagerTarget(HttpServletRequest request) {
		
		Calendar calendar = Calendar.getInstance();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		

		String[] id_year= request.getParameterValues("id_year");
		String[] hierarchy_year= request.getParameterValues("hierarchy_year");
		String[] targetCredit_year= request.getParameterValues("targetCredit_year");
		String[] targetNumber_year= request.getParameterValues("targetNumber_year");
		String[] targetNumberVisit_year= request.getParameterValues("targetNumberVisit_year");
		String[] targetNumberCustomers_year= request.getParameterValues("targetNumberCustomers_year");
		String[] activeNumber_year= request.getParameterValues("activeNumber_year");
		String[] activationNumber_year= request.getParameterValues("activationNumber_year");
		String[] tubeNumber_year= request.getParameterValues("tubeNumber_year");
		
		for(int i=0;i<hierarchy_year.length;i++){
			CustomerManagerTarget customerManagerTarget = new CustomerManagerTarget();
			String hierarchyValue= hierarchy_year[i];
			//String targetCreditValue=targetCredit_year[i];
			String idValue=id_year[i];
			
			if(targetCredit_year[i] !=null && targetCredit_year[i] !=""){
				Double targetCredit_yearDouble = Double.parseDouble(targetCredit_year[i]) * 100;
				String targetCreditValue = targetCredit_yearDouble.toString();
				customerManagerTarget.setTargetCredit(targetCreditValue);
			}
			if(targetNumber_year[i] !="" && targetNumber_year[i] !=null){
			int targetNumberValue=Integer.parseInt(targetNumber_year[i]);
			customerManagerTarget.setTargetNumber(targetNumberValue);
			}
			if(targetNumberVisit_year[i] !="" && targetNumberVisit_year[i] !=null){
			int targetNumberVisitValue=Integer.parseInt(targetNumberVisit_year[i]);
			customerManagerTarget.setTargetNumberVisit(targetNumberVisitValue);
			
			}
			if(targetNumberCustomers_year[i] !="" && targetNumberCustomers_year[i] !=null){
			int targetNumberCustomersValue=Integer.parseInt(targetNumberCustomers_year[i]);
			customerManagerTarget.setTargetNumberCustomers(targetNumberCustomersValue);
			}
			if(activeNumber_year[i] !="" && activeNumber_year[i] !=null){
			int activeNumberValue=Integer.parseInt(activeNumber_year[i]);
			customerManagerTarget.setActiveNumber(activeNumberValue);
			}
			if(activationNumber_year[i] !="" && activationNumber_year[i] !=null){
			int activationNumberValue=Integer.parseInt(activationNumber_year[i]);
			customerManagerTarget.setActivationNumber(activationNumberValue);
			}
			if(tubeNumber_year[i] !="" && tubeNumber_year[i] !=null){
				int activationNumberValue=Integer.parseInt(tubeNumber_year[i]);
				customerManagerTarget.setTubeNumber(activationNumberValue);
			}
			customerManagerTarget.setHierarchy(hierarchyValue);
			customerManagerTarget.setModifiedBy(userId);
			customerManagerTarget.setModifiedTime(calendar.getTime());
			customerManagerTarget.setTargetDate(ManagerTargetType.year.name());
			if(idValue == "" || idValue == null){
				customerManagerTarget.setCreatedBy(userId);
				customerManagerTarget.setCreatedTime(calendar.getTime());
				commonDao.insertObject(customerManagerTarget);	
				
			}else{
				customerManagerTarget.setId(idValue);
				commonDao.updateObject(customerManagerTarget);
			}	
					
			}
		
		String[] id_month= request.getParameterValues("id_month");
		String[] hierarchy_month= request.getParameterValues("hierarchy_month");
		String[] targetCredit_month= request.getParameterValues("targetCredit_month");
		String[] targetNumber_month= request.getParameterValues("targetNumber_month");
		String[] targetNumberVisit_month= request.getParameterValues("targetNumberVisit_month");
		String[] targetNumberCustomers_month= request.getParameterValues("targetNumberCustomers_month");
		String[] activeNumber_month= request.getParameterValues("activeNumber_month");
		String[] activationNumber_month= request.getParameterValues("activationNumber_month");
		String[] tubeNumber_month= request.getParameterValues("tubeNumber_month");
		
		for(int i=0;i<hierarchy_month.length;i++){
			CustomerManagerTarget customerManagerTarget = new CustomerManagerTarget();
			String hierarchyValue= hierarchy_month[i];
			//String targetCreditValue=targetCredit_month[i];
			String idValue=id_month[i];
			
			if(targetCredit_month[i] !=null && targetCredit_month[i] !=""){
				Double targetCredit_monthDouble = Double.parseDouble(targetCredit_month[i]) * 100;
				String targetCreditValue = targetCredit_monthDouble.toString();
				customerManagerTarget.setTargetCredit(targetCreditValue);
			}
			if(targetNumber_month[i] !="" && targetNumber_month[i] !=null){
			int targetNumberValue=Integer.parseInt(targetNumber_month[i]);
			customerManagerTarget.setTargetNumber(targetNumberValue);
			}
			if(targetNumberVisit_month[i] !="" && targetNumberVisit_month[i] !=null){
			int targetNumberVisitValue=Integer.parseInt(targetNumberVisit_month[i]);
			customerManagerTarget.setTargetNumberVisit(targetNumberVisitValue);
			
			}
			if(targetNumberCustomers_month[i] !="" && targetNumberCustomers_month[i] !=null){
			int targetNumberCustomersValue=Integer.parseInt(targetNumberCustomers_month[i]);
			customerManagerTarget.setTargetNumberCustomers(targetNumberCustomersValue);
			}
			if(activeNumber_month[i] !="" && activeNumber_month[i] !=null){
			int activeNumberValue=Integer.parseInt(activeNumber_month[i]);
			customerManagerTarget.setActiveNumber(activeNumberValue);
			}
			if(activationNumber_month[i] !="" && activationNumber_month[i] !=null){
			int activationNumberValue=Integer.parseInt(activationNumber_month[i]);
			customerManagerTarget.setActivationNumber(activationNumberValue);
			}
			if(tubeNumber_month[i] !="" && tubeNumber_month[i] !=null){
				int activationNumberValue=Integer.parseInt(tubeNumber_month[i]);
				customerManagerTarget.setTubeNumber(activationNumberValue);
				}
			customerManagerTarget.setHierarchy(hierarchyValue);
			

			customerManagerTarget.setModifiedBy(userId);
			customerManagerTarget.setModifiedTime(calendar.getTime());
			customerManagerTarget.setTargetDate(ManagerTargetType.month.name());
			if(idValue == "" || idValue == null){
				customerManagerTarget.setCreatedBy(userId);
				customerManagerTarget.setCreatedTime(calendar.getTime());
				commonDao.insertObject(customerManagerTarget);	
				
			}else{
				customerManagerTarget.setId(idValue);
				commonDao.updateObject(customerManagerTarget);
			}	
					
			}
	
		
		String[] id_weekly= request.getParameterValues("id_weekly");
		String[] hierarchy_weekly= request.getParameterValues("hierarchy_weekly");
		String[] targetCredit_weekly= request.getParameterValues("targetCredit_weekly");
		String[] targetNumber_weekly= request.getParameterValues("targetNumber_weekly");
		String[] targetNumberVisit_weekly= request.getParameterValues("targetNumberVisit_weekly");
		String[] targetNumberCustomers_weekly= request.getParameterValues("targetNumberCustomers_weekly");
		String[] activeNumber_weekly= request.getParameterValues("activeNumber_weekly");
		String[] activationNumber_weekly= request.getParameterValues("activationNumber_weekly");
		String[] tubeNumber_weekly= request.getParameterValues("tubeNumber_weekly");
		
		
		for(int i=0;i<hierarchy_weekly.length;i++){
			CustomerManagerTarget customerManagerTarget = new CustomerManagerTarget();
			String hierarchyValue= hierarchy_weekly[i];
			//String targetCreditValue=targetCredit_weekly[i];
			String idValue=id_weekly[i];
			
			if(targetCredit_weekly[i] !=null && targetCredit_weekly[i] !=""){
				Double targetCredit_weeklyDouble = Double.parseDouble(targetCredit_weekly[i]) * 100;
				String targetCreditValue = targetCredit_weeklyDouble.toString();
				customerManagerTarget.setTargetCredit(targetCreditValue);
			}
			if(targetNumber_weekly[i] !="" && targetNumber_weekly[i] !=null){
			int targetNumberValue=Integer.parseInt(targetNumber_weekly[i]);
			customerManagerTarget.setTargetNumber(targetNumberValue);
			}
			if(targetNumberVisit_weekly[i] !="" && targetNumberVisit_weekly[i] !=null){
			int targetNumberVisitValue=Integer.parseInt(targetNumberVisit_weekly[i]);
			customerManagerTarget.setTargetNumberVisit(targetNumberVisitValue);
			
			}
			if(targetNumberCustomers_weekly[i] !="" && targetNumberCustomers_weekly[i] !=null){
			int targetNumberCustomersValue=Integer.parseInt(targetNumberCustomers_weekly[i]);
			customerManagerTarget.setTargetNumberCustomers(targetNumberCustomersValue);
			}
			if(activeNumber_weekly[i] !="" && activeNumber_weekly[i] !=null){
			int activeNumberValue=Integer.parseInt(activeNumber_weekly[i]);
			customerManagerTarget.setActiveNumber(activeNumberValue);
			}
			if(activationNumber_weekly[i] !="" && activationNumber_weekly[i] !=null){
			int activationNumberValue=Integer.parseInt(activationNumber_weekly[i]);
			customerManagerTarget.setActivationNumber(activationNumberValue);
			}
			if(tubeNumber_weekly[i] !="" && tubeNumber_weekly[i] !=null){
				int activationNumberValue=Integer.parseInt(tubeNumber_weekly[i]);
				customerManagerTarget.setTubeNumber(activationNumberValue);
				}
			customerManagerTarget.setHierarchy(hierarchyValue);
			customerManagerTarget.setModifiedBy(userId);
			customerManagerTarget.setModifiedTime(calendar.getTime());
			customerManagerTarget.setTargetDate(ManagerTargetType.weekly.name());
			if(idValue == "" || idValue == null){
				customerManagerTarget.setCreatedBy(userId);
				customerManagerTarget.setCreatedTime(calendar.getTime());
				commonDao.insertObject(customerManagerTarget);	
				
			}else{
				customerManagerTarget.setId(idValue);
				commonDao.updateObject(customerManagerTarget);
			}	
					
			}
	
	
  }
		
		

	public int updateAccountManagerParameter(AccountManagerParameter accountManagerParameter) {
		accountManagerParameter.setModifiedTime(Calendar.getInstance().getTime());
		if(accountManagerParameter != null){
		String basePay = accountManagerParameter.getBasePay();
		if(basePay !=null && basePay !=""){
			//Double basePayDouble = Double.parseDouble(basePay) * 100;
			//String basePayValue = basePayDouble.toString();
			accountManagerParameter.setBasePay(basePay);
		   }
		}
		return commonDao.updateObject(accountManagerParameter);
	}

	public int deleteAccountManagerParameter(String managerId) {
		return commonDao.deleteObject(AccountManagerParameter.class, managerId);
	}

	public AccountManagerParameter findAccountManagerParameterById(String managerId) {
		return commonDao.findObjectById(AccountManagerParameter.class, managerId);
	}

	public QueryResult<AccountManagerParameterForm> findAccountManagerParametersByFilter(AccountManagerParameterFilter filter) {
		List<AccountManagerParameterForm> accountManagerParameterForm = accountManagerParameterDao.findAccountManagerParametersByFilter(filter);
		int size = accountManagerParameterDao.findAccountManagerParametersCountByFilter(filter);
		QueryResult<AccountManagerParameterForm> qs = new QueryResult<AccountManagerParameterForm>(size , accountManagerParameterForm);
		return qs;
	}
	
	public AccountManagerParameterForm findAccountManagerParameterByUserId(String userId) {
		return accountManagerParameterDao.findAccountManagerParameterByUserId(userId);
	}
	
	/**
	 * 获取所有客户经理
	 */
	public List<AccountManagerParameterForm> findAccountManagerParameterAll(){
		return accountManagerParameterDao.findAccountManagerParametersAll();
	}
	/**
	 * 获取客户经理的属性参数根据Id
	 * @return
	 */
	public List<CustomerManagerTarget> getcustomerManagerTargetBymanagerId(String managerId){
		
		AccountManagerParameterForm accountManagerParameter =findAccountManagerParameterByUserId(managerId);
		String hierarchy = accountManagerParameter.getLevelInformation();
		
		return accountManagerParameterComdao.getcustomerManagerTargetBymanagerId(hierarchy);
	}
	/**
	 * 获取客户经理的属性参数根据周期
	 * @return
	 */
	public List<CustomerManagerTarget> getcustomerManagerTargetBytargetDate(String targetDate){
		
		return accountManagerParameterComdao.getcustomerManagerTargetBytargetDate(targetDate);
	}
	/**
	 * 获取所有客户经理的属性参数
	 * @return
	 */
	public List<CustomerManagerTarget> getcustomerManagerTarget(){
		
		return accountManagerParameterComdao.getcustomerManagerTarget();
	}
	
	/**
	 * 获取客户经理的属性参数根据Id和周期
	 * @return
	 */
	public CustomerManagerTarget getcustomerManagerTargetBymanagerIdDate(String managerId,String targetDate){
		AccountManagerParameterForm accountManagerParameter =findAccountManagerParameterByUserId(managerId);
		String hierarchy = accountManagerParameter.getLevelInformation();
		return accountManagerParameterComdao.getcustomerManagerTargetBymanagerIdDate(hierarchy, targetDate);
	}
	
	/**
	 * 记录批出日志流水
	 */
	public void insertBatchTaskFlow(String batch_code,String batch_name){
		BatchTask task = new BatchTask();
		task.setBatchCode(batch_code);
		task.setBatchName(batch_name);
		task.setStatus("000");
		task.setCreatedBy("");
		task.setCreatedTime(new Date());
		commonDao.insertObject(task);
	}
	
	public void updBatchTaskFlow(String status,String batchCode,String dateTime){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", status);
		params.put("batchCode", batchCode);
		params.put("dateTime", dateTime);
		String sql = "update t_batch_task set status = #{status},MODIFIED_TIME = sysdate where batch_code = #{batchCode} and to_char(created_time,'yyyyMMdd') = #{dateTime} ";
		commonDao.queryBySql(BatchTask.class, sql, params);
	}
	
	/**
	 * 历史移交
	 * @param batch_code
	 * @param batch_name
	 * @param status
	 */
	public void insertBatchTaskLogFlow(){
		accountManagerParameterComdao.selectInfoTaskLog();
	}
	
	
	//---------------------------------------------------------济南绩效 start------------------------------------------------//
	public QueryResult<AccountManagerParameterForm> findAccountManagerParametersByFilterForJx(AccountManagerParameterFilter filter) {
		List<AccountManagerParameterForm> accountManagerParameterForm = accountManagerParameterDao.findAccountManagerParametersByFilterForJx(filter);
		int size = accountManagerParameterDao.findAccountManagerParametersCountByFilterForJx(filter);
		QueryResult<AccountManagerParameterForm> qs = new QueryResult<AccountManagerParameterForm>(size , accountManagerParameterForm);
		return qs;
	}
	
	public QueryResult<TJxParameters> findMonthJx(ManagerSalaryFilter filter) {
		List<TJxParameters> param = accountManagerParameterDao.findMonthJx(filter);
		int size = accountManagerParameterDao.findCountMonthJx(filter);
		QueryResult<TJxParameters> qs = new QueryResult<TJxParameters>(size,param);
		return qs;
	}
	
	public QueryResult<TJxSpecificParameters> findCustDayBalamt(ManagerSalaryFilter filter) {
		List<TJxSpecificParameters> jxSpecificParameters = accountManagerParameterDao.findCustDayBalamt(filter);
		int size = accountManagerParameterDao.findCountCustDayBalamt(filter);
		QueryResult<TJxSpecificParameters> qs = new QueryResult<TJxSpecificParameters>(size ,jxSpecificParameters);
		return qs;
	}
	
	
	//---------------------------------------------------------济南绩效 end------------------------------------------------//
	
}
