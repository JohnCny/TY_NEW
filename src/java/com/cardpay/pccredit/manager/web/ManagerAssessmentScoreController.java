package com.cardpay.pccredit.manager.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.constant.ManagerBelongMapConstants;
import com.cardpay.pccredit.manager.filter.ManagerAssessmentScoreFilter;
import com.cardpay.pccredit.manager.model.ManagerAssessmentScore;
import com.cardpay.pccredit.manager.model.TyManagerAssessment;
import com.cardpay.pccredit.manager.service.ManagerAssessmentScoreService;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.UserService;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * 描述 ：客户经理评估信息Controller
 * @author 张石树
 *
 * 2014-11-13 下午2:26:19
 */
@Controller
@RequestMapping("/manager/assessmentscore/*")
@JRadModule("manager.assessmentscore")
public class ManagerAssessmentScoreController extends BaseController{
	
	@Autowired
	private ManagerAssessmentScoreService managerAssessmentScoreService;
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;

	/**
	 * 查看客户经理下属的客户评估信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView displayOrganization(@ModelAttribute ManagerAssessmentScoreFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		List<AccountManagerParameterForm> managerParameterForms = managerBelongMapService.findSubListManagerByManagerId(user.getId());
		JRadPagedQueryResult<TyManagerAssessment> pagedResult = null;
		if(managerParameterForms.size() > 0){
			List<String> subManagerIds = new ArrayList<String>();
			for(AccountManagerParameterForm managerParameterForm : managerParameterForms){
				subManagerIds.add(managerParameterForm.getUserId());
			}
			filter.setSubManagerIds(subManagerIds);
			QueryResult<TyManagerAssessment> result = managerAssessmentScoreService.findManagerAssessmentScoreByFilter(filter);
			pagedResult = new JRadPagedQueryResult<TyManagerAssessment>(filter, result);
		} else {
			QueryResult<TyManagerAssessment> result = new QueryResult<TyManagerAssessment>(0, new ArrayList<TyManagerAssessment>());
			pagedResult = new JRadPagedQueryResult<TyManagerAssessment>(filter, result);
		}
		JRadModelAndView mv = new JRadModelAndView("/manager/assessmentscore/manager_assessmentscore_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}

	/**
	 * 修改显示页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/assessmentscore/manager_assessmentscore_change", request);
		String id = RequestHelper.getStringValue(request, ID);
//		if (StringUtils.isNotEmpty(id)) {
//			TyManagerAssessment managerAssessmentScoreForm = managerAssessmentScoreService.findManagerAssessmentScoreById(id);
//			if(StringUtils.isNotEmpty(managerAssessmentScoreForm.getAssessor())){
//				User user = Beans.get(UserService.class).getUserById(managerAssessmentScoreForm.getAssessor());
//			}else {
//				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
//			}
//			
//			this.dealWithView(managerAssessmentScoreForm);
//			
//			mv.addObject("assessmentScoreForm", managerAssessmentScoreForm);
//		}
		return mv;
	}

	/**
	 * 执行修改
	 * @param managerAssessmentScoreForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute ManagerAssessmentScoreForm managerAssessmentScoreForm, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), managerAssessmentScoreForm);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				ManagerAssessmentScore managerAssessmentScore = managerAssessmentScoreForm.createModel(ManagerAssessmentScore.class);
				managerAssessmentScore.setModifiedBy(user.getId());
				managerAssessmentScore.setModifiedTime(new Date());
				managerAssessmentScoreService.updateManagerAssessmentScore(managerAssessmentScore);
				returnMap.put(RECORD_ID, managerAssessmentScore.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/**
	 * 显示页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/assessmentscore/manager_assessmentscore_display", request);
		String id = RequestHelper.getStringValue(request, ID);
//		if (StringUtils.isNotEmpty(id)) {
//			ManagerAssessmentScoreForm managerAssessmentScoreForm = managerAssessmentScoreService.findManagerAssessmentScoreById(id);
//			if(StringUtils.isNotEmpty(managerAssessmentScoreForm.getAssessor())){
//				User user = Beans.get(UserService.class).getUserById(managerAssessmentScoreForm.getAssessor());
//				managerAssessmentScoreForm.setAssessor(user.getId());
//				managerAssessmentScoreForm.setAssessorName(user.getDisplayName());
//			}
//			
//			this.dealWithView(managerAssessmentScoreForm);
//			
//			mv.addObject("assessmentScoreForm", managerAssessmentScoreForm);
//		}
		return mv;
	}
	
	/**
	 * 处理页面显示
	 * @param managerAssessmentScoreForm
	 */
	private void dealWithView(ManagerAssessmentScoreForm managerAssessmentScoreForm) {
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		Dictionary dictionary = dictMgr.getDictionaryByName(ManagerBelongMapConstants.DICT_TYPE_DKSYL);
		List<DictionaryItem> dictItems = dictionary.getItems();
		//贷款使用率
		Double creditUtilizationRate = managerAssessmentScoreForm.getCreditUtilizationRate();
		for(DictionaryItem item : dictItems){
			String pgbz = item.getName();
			String betScore = item.getTitle();
			if(pgbz.contains("-")){
				String[] betPgbzArr = pgbz.split("-");
				Double minPgbz = Double.parseDouble(betPgbzArr[0].replace("%", ""));
				Double maxPgbz = Double.parseDouble(betPgbzArr[1].replace("%", ""));
				if(creditUtilizationRate >= minPgbz && creditUtilizationRate < maxPgbz){
					managerAssessmentScoreForm.setCreditUtilizationRatePgbz(pgbz);
					managerAssessmentScoreForm.setCreditUtilizationRateBetScore(betScore);
					break;
				}
			} else {
				if(creditUtilizationRate == Double.parseDouble(pgbz.replace("%", ""))){
					managerAssessmentScoreForm.setCreditUtilizationRatePgbz(pgbz);
					managerAssessmentScoreForm.setCreditUtilizationRateBetScore(betScore);
					break;
				}
			}
		}
		//利息收回率
		dictionary = dictMgr.getDictionaryByName(ManagerBelongMapConstants.DICT_TYPE_LSHSL);
		dictItems = dictionary.getItems();
		Double numbererestRecoveryRate = managerAssessmentScoreForm.getNumbererestRecoveryRate();
		for(DictionaryItem item : dictItems){
			String pgbz = item.getName();
			String betScore = item.getTitle();
			if(pgbz.contains("-")){
				String[] betPgbzArr = pgbz.split("-");
				Double minPgbz = Double.parseDouble(betPgbzArr[0].replace("%", ""));
				Double maxPgbz = Double.parseDouble(betPgbzArr[1].replace("%", ""));
				if(numbererestRecoveryRate >= minPgbz && numbererestRecoveryRate < maxPgbz){
					managerAssessmentScoreForm.setNumbererestRecoveryRatePgbz(pgbz);
					managerAssessmentScoreForm.setNumbererestRecoveryRateBetScore(betScore);
					break;
				}
			} else {
				if(numbererestRecoveryRate == Double.parseDouble(pgbz.replace("%", ""))){
					managerAssessmentScoreForm.setNumbererestRecoveryRatePgbz(pgbz);
					managerAssessmentScoreForm.setNumbererestRecoveryRateBetScore(betScore);
					break;
				}
			}
		}
		
		//逾期率
		dictionary = dictMgr.getDictionaryByName(ManagerBelongMapConstants.DICT_TYPE_YQL);
		dictItems = dictionary.getItems();
		Double overdueRate = managerAssessmentScoreForm.getOverdueRate();
		for(DictionaryItem item : dictItems){
			String pgbz = item.getName();
			String betScore = item.getTitle();
			if(pgbz.contains("-")){
				String[] betPgbzArr = pgbz.split("-");
				if(betPgbzArr.length == 1){ //如：1.5%以上  字典值为 1.5%-
					Double minPgbz = Double.parseDouble(betPgbzArr[0].replace("%", ""));
					if(overdueRate >= minPgbz){
						managerAssessmentScoreForm.setOverdueRatePgbz(pgbz + "及以上");
						managerAssessmentScoreForm.setOverdueRateBetScore(betScore);
						break;
					}
				} else {
					Double minPgbz = Double.parseDouble(betPgbzArr[0].replace("%", ""));
					Double maxPgbz = Double.parseDouble(betPgbzArr[1].replace("%", ""));
					if(overdueRate >= minPgbz && overdueRate <= maxPgbz){
						managerAssessmentScoreForm.setOverdueRatePgbz(pgbz);
						managerAssessmentScoreForm.setOverdueRateBetScore(betScore);
						break;
					}
				}
			} else {
				if(overdueRate == Double.parseDouble(pgbz.replace("%", ""))){
					managerAssessmentScoreForm.setOverdueRatePgbz(pgbz);
					managerAssessmentScoreForm.setOverdueRateBetScore(betScore);
					break;
				}
			}
		}
		
		//逾期回收率
		dictionary = dictMgr.getDictionaryByName(ManagerBelongMapConstants.DICT_TYPE_YQHSL);
		dictItems = dictionary.getItems();
		Double lateRecoveryRate = managerAssessmentScoreForm.getLateRecoveryRate();
		for(DictionaryItem item : dictItems){
			String pgbz = item.getName();
			String betScore = item.getTitle();
			if(pgbz.contains("-")){
				String[] betPgbzArr = pgbz.split("-");
				Double minPgbz = Double.parseDouble(betPgbzArr[0].replace("%", ""));
				Double maxPgbz = Double.parseDouble(betPgbzArr[1].replace("%", ""));
				if(lateRecoveryRate >= minPgbz && lateRecoveryRate < maxPgbz){
					managerAssessmentScoreForm.setLateRecoveryRatePgbz(pgbz);
					managerAssessmentScoreForm.setLateRecoveryRateBetScore(betScore);
					break;
				}
			} else {
				if(lateRecoveryRate == Double.parseDouble(pgbz.replace("%", ""))){
					managerAssessmentScoreForm.setLateRecoveryRatePgbz(pgbz);
					managerAssessmentScoreForm.setLateRecoveryRateBetScore(betScore);
					break;
				}
			}
		}
		
		//逾期余额
		dictionary = dictMgr.getDictionaryByName(ManagerBelongMapConstants.DICT_TYPE_YQYE);
		dictItems = dictionary.getItems();
		Double overdueBalanceRate = managerAssessmentScoreForm.getOverdueBalanceRate();
		for(DictionaryItem item : dictItems){
			String pgbz = item.getName();
			String betScore = item.getTitle();
			if(pgbz.contains("-")){
				String[] betPgbzArr = pgbz.split("-");
				if(betPgbzArr.length == 1){ //如：1.5%以上  字典值为 1.5%-
					Double minPgbz = Double.parseDouble(betPgbzArr[0]);
					if(overdueBalanceRate >= minPgbz){
						managerAssessmentScoreForm.setOverdueBalanceRatePgbz(pgbz + "及以上");
						managerAssessmentScoreForm.setOverdueBalanceRateBetScore(betScore);
						break;
					}
				} else {
					Double minPgbz = Double.parseDouble(betPgbzArr[0]);
					Double maxPgbz = Double.parseDouble(betPgbzArr[1]);
					if(overdueBalanceRate > minPgbz && overdueBalanceRate <= maxPgbz){
						managerAssessmentScoreForm.setOverdueBalanceRatePgbz(pgbz);
						managerAssessmentScoreForm.setOverdueBalanceRateBetScore(betScore);
						break;
					}
				}
			} else {
				if(overdueBalanceRate == Double.parseDouble(pgbz)){
					managerAssessmentScoreForm.setOverdueBalanceRatePgbz(pgbz);
					managerAssessmentScoreForm.setOverdueBalanceRateBetScore(betScore);
					break;
				}
			}
		}
		
		//瑕疵贷款率
		dictionary = dictMgr.getDictionaryByName(ManagerBelongMapConstants.DICT_TYPE_XCDKL);
		dictItems = dictionary.getItems();
		Double defectiveLoansRate = managerAssessmentScoreForm.getDefectiveLoansRate();
		for(DictionaryItem item : dictItems){
			String pgbz = item.getName();
			String betScore = item.getTitle();
			if(pgbz.contains("-")){
				String[] betPgbzArr = pgbz.split("-");
				if(betPgbzArr.length == 1){ //如：1.5%以上  字典值为 1.5%-
					Double minPgbz = Double.parseDouble(betPgbzArr[0].replace("%", ""));
					if(defectiveLoansRate > minPgbz){
						managerAssessmentScoreForm.setDefectiveLoansRatePgbz(pgbz + "及以上");
						managerAssessmentScoreForm.setDefectiveLoansRateBetScore(betScore);
						break;
					}
				} else {
					Double minPgbz = Double.parseDouble(betPgbzArr[0].replace("%", ""));
					Double maxPgbz = Double.parseDouble(betPgbzArr[1].replace("%", ""));
					if(defectiveLoansRate >= minPgbz && defectiveLoansRate <= maxPgbz){
						managerAssessmentScoreForm.setDefectiveLoansRatePgbz(pgbz);
						managerAssessmentScoreForm.setDefectiveLoansRateBetScore(betScore);
						break;
					}
				}
			} else {
				if(defectiveLoansRate == Double.parseDouble(pgbz.replace("%", ""))){
					managerAssessmentScoreForm.setDefectiveLoansRatePgbz(pgbz);
					managerAssessmentScoreForm.setDefectiveLoansRateBetScore(betScore);
					break;
				}
			}
		}
	}
}
