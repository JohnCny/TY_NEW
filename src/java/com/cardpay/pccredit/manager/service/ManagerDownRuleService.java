package com.cardpay.pccredit.manager.service;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.dao.comdao.ManagerDownRuleComdao;
import com.cardpay.pccredit.manager.model.DownGradeRule;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-21 下午4:27:11
 */
@Service
public class ManagerDownRuleService {
	
	
	@Autowired
	private CommonDao commonDao;
	
	
	@Autowired
	private ManagerDownRuleComdao managerDownRuleComdao;
	
	/**
	 * 查询客户经理降级规则
	 * @return
	 */
	public List<DownGradeRule> getDownGradeRule(){
	
		return managerDownRuleComdao.getDownGradeRule();
		
	}
	
	/**
	 * 修改客户经理降级规则
	 * @param request
	 */
	public void updateDownGradeRule(HttpServletRequest request) {
		
		Calendar calendar = Calendar.getInstance();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		String[] id= request.getParameterValues("id");
		String[] currentLevel= request.getParameterValues("currentLevel");
		String[] endingPerformanceIndicator= request.getParameterValues("endingPerformanceIndicator");
		String[] evaluationScore= request.getParameterValues("evaluationScore");
		String[] indicatorCompletionRate= request.getParameterValues("indicatorCompletionRate");
		String[] teamSize= request.getParameterValues("teamSize");
		String[] quarterPerformanceIndicator= request.getParameterValues("quarterPerformanceIndicator");
		String[] yearEvaluationScore= request.getParameterValues("yearEvaluationScore");
		String[] downgradeLevel= request.getParameterValues("downgradeLevel");
		
		for(int i=0;i<id.length;i++){
			DownGradeRule downGradeRule = new DownGradeRule();
			String idValue= id[i];
			String currentLevelValue=currentLevel[i];
			String endingPerformanceIndicatorValue=endingPerformanceIndicator[i];
			String evaluationScoreValue=evaluationScore[i];
			String indicatorCompletionRateValue=indicatorCompletionRate[i];
		    String quarterPerformanceIndicatorValue=quarterPerformanceIndicator[i];
			String teamSizeValue=teamSize[i];
			String yearEvaluationScoreValue=yearEvaluationScore[i];
			String downgradeLevelValue=downgradeLevel[i];
			downGradeRule.setCurrentLevel(currentLevelValue);
			downGradeRule.setEndingPerformanceIndicator(endingPerformanceIndicatorValue);
			downGradeRule.setEvaluationScore(evaluationScoreValue);
			downGradeRule.setIndicatorCompletionRate(indicatorCompletionRateValue);
			downGradeRule.setQuarterPerformanceIndicator(quarterPerformanceIndicatorValue);
			downGradeRule.setTeamSize(teamSizeValue);
			downGradeRule.setYearEvaluationScore(yearEvaluationScoreValue);
			downGradeRule.setDowngradeLevel(downgradeLevelValue);
		
			downGradeRule.setModifiedBy(userId);
			downGradeRule.setModifiedTime(calendar.getTime());
			
			if(idValue != null && idValue !=""){
				downGradeRule.setId(idValue);
				commonDao.updateObject(downGradeRule);
			}else{
				downGradeRule.setCreatedBy(userId);
				downGradeRule.setCreatedTime(calendar.getTime());
				commonDao.insertObject(downGradeRule);		
					
			}
	
		}
		
		
	}
	
}
