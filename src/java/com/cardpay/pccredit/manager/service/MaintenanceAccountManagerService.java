package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.ManagerCustomerTypeDao;
import com.cardpay.pccredit.manager.dao.comdao.MaintenanceAccountManagerComdao;
import com.cardpay.pccredit.manager.model.MaintenanceAccountManager;
import com.cardpay.pccredit.riskControl.model.ManagerCustomerType;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-17 下午4:38:43
 */
@Service
public class MaintenanceAccountManagerService {
	
	final public static String SEPARATOR  = ",";
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private ManagerCustomerTypeDao managerCustomerTypedao;
	@Autowired
	private MaintenanceAccountManagerComdao maintenanceAccountManagerComdao;
	
	/**
	 * 查询客户经理参数维护
	 * @return
	 */
	public List<MaintenanceAccountManager> getMaintenanceAccountManager(){
	
		return maintenanceAccountManagerComdao.getMaintenanceAccountManager();
		
	}
	/**
	 * 查询客户层级对应的类型
	 * @return
	 */
	public List<ManagerCustomerType> getManagerCustomerType(){
	
		return maintenanceAccountManagerComdao.getManagerCustomerType();
		
	}
	/**
	 * 修改客户经理参数维护
	 * @param request
	 */
	public void updateMaintenanceAccountManager(HttpServletRequest request) {
		
		Calendar calendar = Calendar.getInstance();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		String[] id= request.getParameterValues("id");
		String[] hierarchy= request.getParameterValues("hierarchy");
		String[] weightedNumber= request.getParameterValues("weightedNumber");
		String[] customerNumberAward= request.getParameterValues("customerNumberAward");
		String[] teamAward= request.getParameterValues("teamAward");
		String[] allowance= request.getParameterValues("allowance");
		String[] insertCustomerAward= request.getParameterValues("insertCustomerAward");
		String[] sleepRate= request.getParameterValues("sleepRate");
		String[] validRate= request.getParameterValues("validRate");
		String[] activeRate= request.getParameterValues("activeRate");
		String[] activateRate= request.getParameterValues("activateRate");
		String[] dutySalary= request.getParameterValues("dutySalary");
		String[] customerTypeCode= request.getParameterValues("customerTypeCode");
		for(int i=0;i<id.length;i++){
			MaintenanceAccountManager maintenanceAccountManager = new MaintenanceAccountManager();
			ManagerCustomerType managerCustomerType = new ManagerCustomerType();
			String idValue= id[i];
			String hierarchyValue=hierarchy[i];
			String weightedNumberValue=weightedNumber[i];
			//String customerNumberAwardValue=customerNumberAward[i];
			String teamAwardValue=teamAward[i];
			//String allowanceValue=allowance[i];
			//String insertCustomerAwardValue=insertCustomerAward[i];
			String sleepRateValue=sleepRate[i];
			String validRateValue=validRate[i];
			String activeRateValue=activeRate[i];
			String activateRateValue=activateRate[i];
			//String dutySalaryValue=dutySalary[i];
			String customerTypeCodeValue=customerTypeCode[i];
			
			if(customerNumberAward[i] !=null && customerNumberAward[i] !=""){
				Double customerNumberAwardDouble = Double.parseDouble(customerNumberAward[i]) * 100;
				String customerNumberAwardValue = customerNumberAwardDouble.toString();
				maintenanceAccountManager.setCustomerNumberAward(customerNumberAwardValue);
			}
			
			if(allowance[i] !=null && allowance[i] !=""){
				Double allowanceDouble = Double.parseDouble(allowance[i]) * 100;
				String allowanceValue = allowanceDouble.toString();
				maintenanceAccountManager.setAllowance(allowanceValue);
			}
			if(insertCustomerAward[i] !=null && insertCustomerAward[i] !=""){
				Double insertCustomerAwardDouble = Double.parseDouble(insertCustomerAward[i]) * 100;
				String insertCustomerAwardValue = insertCustomerAwardDouble.toString();
				maintenanceAccountManager.setInsertCustomerAward(insertCustomerAwardValue);
			}
			
			if(dutySalary[i] !=null && dutySalary[i] !=""){
				Double dutySalaryDouble = Double.parseDouble(dutySalary[i]) * 100;
				String dutySalaryValue = dutySalaryDouble.toString();
				maintenanceAccountManager.setDutySalary(dutySalaryValue);
			}
			maintenanceAccountManager.setHierarchy(hierarchyValue);
			maintenanceAccountManager.setWeightedNumber(weightedNumberValue);
			maintenanceAccountManager.setTeamAward(teamAwardValue);
			maintenanceAccountManager.setSleepRate(sleepRateValue);
			maintenanceAccountManager.setValidRate(validRateValue);
			maintenanceAccountManager.setActivateRate(activateRateValue);
			maintenanceAccountManager.setActiveRate(activeRateValue);
			
			maintenanceAccountManager.setModifiedBy(userId);
			maintenanceAccountManager.setModifiedTime(calendar.getTime());
			managerCustomerType.setLevelId(hierarchyValue);
			//managerCustomerType.setCustomerType(customerTypeCodeValue);
			if(idValue != null && idValue !=""){
				maintenanceAccountManager.setId(idValue);
				commonDao.updateObject(maintenanceAccountManager);
				
				managerCustomerTypedao.deleteManagerCustomerType(hierarchyValue);
			
				 if(customerTypeCodeValue !="" && customerTypeCodeValue != null ){
					   String[] result = customerTypeCodeValue.split(SEPARATOR);
					    for(int j=0;j < result.length ;j++){
					    	managerCustomerType.setCustomerType(result[j]);
					    	commonDao.insertObject(managerCustomerType);	
						
					}
					}
			}else{
				maintenanceAccountManager.setCreatedBy(userId);
				maintenanceAccountManager.setCreatedTime(calendar.getTime());
				commonDao.insertObject(maintenanceAccountManager);	
				   if(customerTypeCodeValue !="" && customerTypeCodeValue != null){
					   String[] result = customerTypeCodeValue.split(SEPARATOR);
					    for(int j=0;j < result.length ;j++){
					    	managerCustomerType.setCustomerType(result[j]);
					    	commonDao.insertObject(managerCustomerType);	
						
					}
					}
					
					
			}
	
		}
		
		
	}
	
}
