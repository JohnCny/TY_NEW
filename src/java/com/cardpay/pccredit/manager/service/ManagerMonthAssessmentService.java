package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.comdao.ManagerMonthAssessmentComdao;
import com.cardpay.pccredit.manager.model.MangerMonthAssessment;
import com.cardpay.pccredit.manager.model.TyManagerAssessment;
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
public class ManagerMonthAssessmentService {
	
	
	@Autowired
	private CommonDao commonDao;
	
	
	@Autowired
	private ManagerMonthAssessmentComdao managerMonthAssessmentComdao;
	
	/**
	 * 客户经理层级月度考核指标配置
	 * @return
	 */
	public List<MangerMonthAssessment> getMangerMonthAssessment(){
	
		return managerMonthAssessmentComdao.getMangerMonthAssessment();
		
	}
	
	/**
	 * 客户经理层级月度考核指标配置
	 * @param request
	 */
	public void updateMangerMonthAssessment(HttpServletRequest request) {
		
		Calendar calendar = Calendar.getInstance();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		String[] id= request.getParameterValues("id");
		String[] customerManagerLevel= request.getParameterValues("customerManagerLevel");
		String[] creditlineAccount= request.getParameterValues("creditlineAccount");
		String[] monthdayAvgCreditline= request.getParameterValues("monthdayAvgCreditline");
		String[] monthdayTeamAvgCreditline= request.getParameterValues("monthdayTeamAvgCreditline");
		String[] tubeNumber= request.getParameterValues("tubeNumber");
		String[] activeRate= request.getParameterValues("activeRate");
		String[] activationRate= request.getParameterValues("activationRate");
		
		for(int i=0;i<id.length;i++){
			MangerMonthAssessment mangerMonthAssessment = new MangerMonthAssessment();
			String idValue= id[i];
			String customerManagerLevelValue=customerManagerLevel[i];
			//String creditlineAccountValue=creditlineAccount[i];
			//String monthdayAvgCreditlineValue=monthdayAvgCreditline[i];
			//String monthdayTeamAvgCreditlineValue=monthdayTeamAvgCreditline[i];
			String activeRateValue=activeRate[i];
			String activationRateValue=activationRate[i];

			
			if(creditlineAccount[i] !=null && creditlineAccount[i] !=""){
				Double creditlineAccountDouble = Double.parseDouble(creditlineAccount[i]) * 1000000;
				String creditlineAccountValue = creditlineAccountDouble.toString();
				mangerMonthAssessment.setCreditlineAccount(creditlineAccountValue);
				
			}
			
			if(monthdayAvgCreditline[i] !=null && monthdayAvgCreditline[i] !=""){
				Double monthdayAvgCreditlineDouble = Double.parseDouble(monthdayAvgCreditline[i]) * 1000000;
				String monthdayAvgCreditlineValue = monthdayAvgCreditlineDouble.toString();
				mangerMonthAssessment.setMonthdayAvgCreditline(monthdayAvgCreditlineValue);
			}
			
			if(monthdayTeamAvgCreditline[i] !=null && monthdayTeamAvgCreditline[i] !=""){
				Double monthdayTeamAvgCreditlineDouble = Double.parseDouble(monthdayTeamAvgCreditline[i]) * 1000000;
				String monthdayTeamAvgCreditlineValue = monthdayTeamAvgCreditlineDouble.toString();
				mangerMonthAssessment.setMonthdayTeamAvgCreditline(monthdayTeamAvgCreditlineValue);
			}
			mangerMonthAssessment.setCustomerManagerLevel(customerManagerLevelValue);
			
			if(tubeNumber[i] !="" && tubeNumber[i] !=null){
				int tubeNumberVal=Integer.parseInt(tubeNumber[i]);
				mangerMonthAssessment.setTubeNumber(tubeNumberVal);
			}
			mangerMonthAssessment.setActiveRate(activeRateValue);
			mangerMonthAssessment.setActivationRate(activationRateValue);
			mangerMonthAssessment.setModifiedBy(userId);
			mangerMonthAssessment.setModifiedTime(calendar.getTime());
			
			if(idValue != null && idValue !=""){
				mangerMonthAssessment.setId(idValue);
				commonDao.updateObject(mangerMonthAssessment);
			}else{
				mangerMonthAssessment.setCreatedBy(userId);
				mangerMonthAssessment.setCreatedTime(calendar.getTime());
				commonDao.insertObject(mangerMonthAssessment);		
					
			}
	
		}
		
		
	}
	
}
