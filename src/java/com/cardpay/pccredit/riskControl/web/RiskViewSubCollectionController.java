/**
 * 
 */
package com.cardpay.pccredit.riskControl.web;

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

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerCollectionPlanFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlan;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlansAction;
import com.cardpay.pccredit.riskControl.service.RiskCustomerCollectionService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 催收计划信息(下属)
 * @author shaoming
 *
 * 2014年11月17日   下午5:51:21
 */
@Controller
@RequestMapping("/riskcontrol/riskviewsubcollection/*")
@JRadModule("riskcontrol.riskviewsubcollection")
public class RiskViewSubCollectionController extends BaseController{
	@Autowired
	private RiskCustomerCollectionService riskCustomerCollectionService;
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	
	/**
	 * 浏览催收计划信息页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute RiskCustomerCollectionPlanFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskViewSubCollection/collection_plan_browse", request);
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId(); 
		/**
		 * 得到当前客户经理的下属经理By userId
		 */
		List<AccountManagerParameterForm> forms = managerBelongMapService.findSubListManagerByManagerId(userId);
		String customerManagerId = filter.getCustomerManagerId();
		QueryResult<RiskCustomerCollectionPlanForm> result = null;
		if(customerManagerId!=null && !customerManagerId.equals("")){
			result = riskCustomerCollectionService.findRiskCustomerCollectionPlansByFilter(filter);
		}else{
			/*当前客户经理存在下属经理,得到所有下属经理名下逾期客户催收信息*/
			if(forms.size()>0){
				filter.setCustomerManagerIds(forms);
				result = riskCustomerCollectionService.findRiskViewSubCollectionPlansByFilter(filter);
			}else{
				/*直接返回页面*/
				return mv;
			}
		}
		JRadPagedQueryResult<RiskCustomerCollectionPlanForm> pagedResult = new JRadPagedQueryResult<RiskCustomerCollectionPlanForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		return mv;
	}
	/**
	 * 修改催收计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskViewSubCollection/collection_plan_change", request);

		String collectionPlanId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(collectionPlanId)) {
			RiskCustomerCollectionPlanForm collectionplan = riskCustomerCollectionService.findRiskCustomerCollectionPlanById(collectionPlanId);
			mv.addObject("collectionplan", collectionplan);
		}
		return mv;
	}
	/**
	 * 显示催收计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskViewSubCollection/collection_plan_display", request);
		String collectionPlanId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(collectionPlanId)) {
			RiskCustomerCollectionPlanForm collectionplan = riskCustomerCollectionService.findRiskCustomerCollectionPlanById(collectionPlanId);
			List<RiskCustomerCollectionPlansAction> collectionActions = riskCustomerCollectionService.findRiskCustomerCollectionPlansActionByCollectionPlanId(collectionPlanId);
			mv.addObject("collectionPlan", collectionplan);
			mv.addObject("collectionActions",collectionActions);
		}
		return mv;
	}
	/**
	 * 修改催收计划
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute RiskCustomerCollectionPlanForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				String createWay = riskCustomerCollectionService.findRiskCustomerCollectionPlanById(form.getId()).getCreateWay();
				if(createWay!=null && createWay.equals(RiskCustomerCollectionEnum.system.toString())){
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
					return returnMap;
				}
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId();
				
				RiskCustomerCollectionPlan riskCustomerCollectionPlan = form.createModel(RiskCustomerCollectionPlan.class);
				riskCustomerCollectionPlan.setModifiedBy(modifiedBy);
				boolean flag=riskCustomerCollectionService.updateRiskCustomerCollectionPlan(riskCustomerCollectionPlan);
				if(flag){
					returnMap.put(RECORD_ID, riskCustomerCollectionPlan.getId());
					returnMap.addGlobalMessage(CHANGE_SUCCESS);
				}else{
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
				}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	/**
	 * 修改催收实施计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeAction.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changeAction(HttpServletRequest request) {
		String id = RequestHelper.getStringValue(request, ID);
		RiskCustomerCollectionPlansAction riskCustomerCollectionPlansAction = riskCustomerCollectionService.findRiskCustomerCollectionPlansActionById(id);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskViewSubCollection/collection_plan_action_change", request);
		mv.addObject("collectionPlansAction",riskCustomerCollectionPlansAction);
		return mv;
	}
	/**
	 * 修改催收计划实施信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateAction.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updateAction(@ModelAttribute RiskCustomerCollectionPlansActionForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId(); 
				RiskCustomerCollectionPlansAction riskCustomerCollectionPlansAction = new RiskCustomerCollectionPlansAction();
				String collectionStartTime = form.getCollectionStartTime();
				String collectionEndTime = form.getCollectionEndTime();
				if(collectionStartTime!=null && !collectionStartTime.equals("")){
					Date start = DateHelper.getDateFormat(collectionStartTime, "yyyy-MM-dd HH:mm:ss");
					riskCustomerCollectionPlansAction.setCollectionStartTime(start);
				}
				if(collectionEndTime!=null && !collectionEndTime.equals("")){
				    Date end = DateHelper.getDateFormat(collectionEndTime, "yyyy-MM-dd HH:mm:ss");
				    riskCustomerCollectionPlansAction.setCollectionEndTime(end);
				}
				riskCustomerCollectionPlansAction.setId(form.getId());
				riskCustomerCollectionPlansAction.setCollectionPlanId(form.getCollectionPlanId());
				riskCustomerCollectionPlansAction.setCollection(form.getCollection());
				riskCustomerCollectionPlansAction.setCollectionResult(form.getCollectionResult());
				riskCustomerCollectionPlansAction.setModifiedBy(modifiedBy);
				boolean flag=riskCustomerCollectionService.updateRiskCustomerCollectionPlansAction(riskCustomerCollectionPlansAction);
				if(flag){
					returnMap.put(ID, riskCustomerCollectionPlansAction.getCollectionPlanId());
					returnMap.put(RECORD_ID, riskCustomerCollectionPlansAction.getId());
					returnMap.addGlobalMessage(CHANGE_SUCCESS);
				}else{
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
				}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
}
