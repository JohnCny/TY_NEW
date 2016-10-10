/**
 * 
 */
package com.cardpay.pccredit.customer.web;

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
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.Maintenance;
import com.cardpay.pccredit.customer.model.MaintenanceAction;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEnum;
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
 * @author shaoming
 *
 * 2014年11月18日   下午1:45:58
 */
@Controller
@RequestMapping("/customer/viewsubmaintenance/*")
@JRadModule("customer.viewsubmaintenance")
public class SubMaintenanceController extends BaseController{
	@Autowired
	private MaintenanceService maintenanceService;

	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	/**
	 * 浏览维护计划信息页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute MaintenanceFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/viewsubmaintenance/submaintenance_plan_browse", request);
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId(); 
		/**
		 * 得到当前客户经理的下属经理By userId
		 */
		List<AccountManagerParameterForm> forms = managerBelongMapService.findSubListManagerByManagerId(userId);
		String customerManagerId = filter.getCustomerManagerId();
		QueryResult<MaintenanceForm> result = null;
		if(customerManagerId!=null && !customerManagerId.equals("")){
			result = maintenanceService.findMaintenancePlansByFilter(filter);
		}else{
			if(forms.size()>0){
				filter.setCustomerManagerIds(forms);
				result = maintenanceService.findSubMaintenancePlansByFilter(filter);
			}else{
				/*直接返回页面*/
				return mv;
			}
		}
		JRadPagedQueryResult<MaintenanceForm> pagedResult = new JRadPagedQueryResult<MaintenanceForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		return mv;
	}
	/**
	 * 修改维护计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/viewsubmaintenance/submaintenance_plan_change", request);
		String maintenanceId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(maintenanceId)) {
			MaintenanceForm maintenance = maintenanceService.findMaintenanceById(maintenanceId);
			mv.addObject("maintenance", maintenance);
		}
		return mv;
	}

	/**
	 * 显示维护计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/viewsubmaintenance/submaintenance_plan_display", request);
		String maintenanceId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(maintenanceId)) {
			MaintenanceForm maintenance = maintenanceService.findMaintenanceById(maintenanceId);
			List<MaintenanceWeb> maintenanceActions = maintenanceService.findMaintenanceActionByMaintenanceId(maintenanceId);
			mv.addObject("maintenance", maintenance);
			mv.addObject("maintenanceActions",maintenanceActions);
		}
		return mv;
	}
	/**
	 * 修改维护计划
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute MaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				String createWay = maintenanceService.findMaintenanceById(form.getId()).getCreateWay();
				if(createWay!=null && createWay.equals(RiskCustomerCollectionEnum.system.toString())){
					returnMap.setSuccess(false);
					returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
					return returnMap;
				}
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId(); 
				Maintenance maintenance = form.createModel(Maintenance.class);
				maintenance.setModifiedBy(modifiedBy);
				boolean flag=maintenanceService.updateMaintenance(maintenance);
				if(flag){
					returnMap.put(RECORD_ID, maintenance.getId());
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
		MaintenanceAction maintenanceAction = maintenanceService.findMaintenanceActionById(id);
		JRadModelAndView mv = new JRadModelAndView("/customer/viewsubmaintenance/submaintenance_plan_action_change", request);
		mv.addObject("maintenanceAction",maintenanceAction);
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
	public JRadReturnMap updateAction(@ModelAttribute MaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId(); 
				String maintenanceStartTime = form.getMaintenanceStartTime();
				String maintenanceEndTime = form.getMaintenanceEndTime();
				MaintenanceAction maintenanceAction = new MaintenanceAction();
				maintenanceAction.setModifiedBy(modifiedBy);
				maintenanceAction.setId(form.getId());
				maintenanceAction.setMaintenancePlanId(form.getMaintenancePlanId());
				maintenanceAction.setMaintenanceWay(form.getMaintenanceWay());
				maintenanceAction.setMaintenanceResult(form.getMaintenanceResult());
				maintenanceAction.setMaintenanceStartTime(DateHelper.getDateFormat(maintenanceStartTime, "yyyy-MM-dd HH:mm:ss"));
				maintenanceAction.setMaintenanceEndTime(DateHelper.getDateFormat(maintenanceEndTime, "yyyy-MM-dd HH:mm:ss"));
				boolean flag=maintenanceService.updateMaintenanceAction(maintenanceAction);
				if(flag){
					returnMap.put(ID, maintenanceAction.getMaintenancePlanId());
					returnMap.put(RECORD_ID, maintenanceAction.getId());
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
