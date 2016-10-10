/**
 * 
 */
package com.cardpay.pccredit.manager.web;

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
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.manager.filter.InformationMaintenanceFilter;
import com.cardpay.pccredit.manager.model.InformationMaintenance;
import com.cardpay.pccredit.manager.model.InformationPlansAction;
import com.cardpay.pccredit.manager.service.InformationMaintenanceService;
import com.cardpay.pccredit.manager.service.InformationOfficerService;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.system.model.Dict;
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
 * 信息员渠道维护计划
 * @author shaoming
 *
 * 2014年11月24日   下午3:05:47
 */
@Controller
@RequestMapping("/manager/informationmaintenance/*")
@JRadModule("manager.informationmaintenance")
public class InformationMaintenanceController extends BaseController{

	@Autowired
	private InformationMaintenanceService informationMaintenanceService;
	
	@Autowired
	private InformationOfficerService informationOfficerService;
	@Autowired
	private ManagerBelongMapService managerBelongMapService;

	@Autowired
	private CustomerInforService customerInforService;
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
	public AbstractModelAndView browse(@ModelAttribute InformationMaintenanceFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId(); 
		filter.setCustomerManagerId(userId);
		QueryResult<InformationMaintenanceForm> result = informationMaintenanceService.findInformationMaintenanceByFilter(filter);
		JRadPagedQueryResult<InformationMaintenanceForm> pagedResult = new JRadPagedQueryResult<InformationMaintenanceForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/informationmaintenance/informationmaintenance_plan_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}

	/**
	 * 增加维护计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId(); 
		List<Dict> dicts = informationOfficerService.findInformationOfficerByUserId(userId);
		JRadModelAndView mv = new JRadModelAndView("/manager/informationmaintenance/informationmaintenance_plan_create", request);
		mv.addObject("officers", dicts);
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
		JRadModelAndView mv = new JRadModelAndView("/manager/informationmaintenance/informationmaintenance_plan_change", request);
		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			InformationMaintenanceForm maintenance = informationMaintenanceService.findInformationMaintenanceFormById(id);
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
		JRadModelAndView mv = new JRadModelAndView("/manager/informationmaintenance/informationmaintenance_plan_display", request);
		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			InformationMaintenanceForm maintenance = informationMaintenanceService.findInformationMaintenanceFormById(id);
			List<InformationPlansAction> maintenanceActions = informationMaintenanceService.findMaintenanceActionByMaintenanceId(id);
			mv.addObject("maintenance", maintenance);
			mv.addObject("maintenanceActions",maintenanceActions);
		}

		return mv;
	}
	/**
	 * 添加催收计划
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute InformationMaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				String maintenanceTime = RequestHelper.getStringValue(request, "maintenanceTime");
				if(StringUtils.isNotEmpty(maintenanceTime)){
					form.setMaintenanceTime(DateHelper.getDateFormat(maintenanceTime, "yyyy-MM-dd HH:mm:ss"));
				}
				InformationMaintenance informationMaintenance = form.createModel(InformationMaintenance.class);
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String createdBy = user.getId();
				informationMaintenance.setCreatedBy(createdBy);
				informationMaintenance.setCustomerManagerId(createdBy);
				String id = informationMaintenanceService.insertInformationMaintenance(informationMaintenance);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
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
	public JRadReturnMap update(@ModelAttribute InformationMaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				String maintenanceTime = RequestHelper.getStringValue(request, "maintenanceTime");
				if(StringUtils.isNotEmpty(maintenanceTime)){
					form.setMaintenanceTime(DateHelper.getDateFormat(maintenanceTime, "yyyy-MM-dd HH:mm:ss"));
				}
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId(); 
				InformationMaintenance maintenance = form.createModel(InformationMaintenance.class);
				maintenance.setModifiedBy(modifiedBy);
				boolean flag=informationMaintenanceService.updateInformationMaintenance(maintenance);
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
	 * 增加催收实施计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "createAction.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createAction(HttpServletRequest request) {
		String maintenanceId = RequestHelper.getStringValue(request, ID);
		JRadModelAndView mv = new JRadModelAndView("/manager/informationmaintenance/informationmaintenance_plan_action_create", request);
		mv.addObject("maintenanceId",maintenanceId);
		return mv;
	}
	/**
	 * 添加维护计划实施信息
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertAction.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insertAction(@ModelAttribute InformationMaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				String maintenanceId = form.getInformationPlanId();
				String informationStartTime = RequestHelper.getStringValue(request, "informationStartTime");
				String informationEndTime = RequestHelper.getStringValue(request, "informationEndTime");
				if(StringUtils.isNotEmpty(informationStartTime)){
					form.setInformationStartTime(DateHelper.getDateFormat(informationStartTime, "yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtils.isNotEmpty(informationEndTime)){
					form.setInformationEndTime(DateHelper.getDateFormat(informationEndTime, "yyyy-MM-dd HH:mm:ss"));
				}
				InformationPlansAction action = form.createModel(InformationPlansAction.class);
				/*在当前计划下添加一条新的实施信息记录*/
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String createdBy = user.getId();
				action.setCreatedBy(createdBy);
				String id = informationMaintenanceService.insertInformationPlansAction(action);
				/*返回维护计划详细信息页面*/
				returnMap.put(ID, maintenanceId);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
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
		InformationPlansAction maintenanceAction = informationMaintenanceService.findInformationPlansActionById(id);
		JRadModelAndView mv = new JRadModelAndView("/manager/informationmaintenance/informationmaintenance_plan_action_change", request);
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
	public JRadReturnMap updateAction(@ModelAttribute InformationMaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String modifiedBy = user.getId(); 
				String informationStartTime = RequestHelper.getStringValue(request, "informationStartTime");
				String informationEndTime = RequestHelper.getStringValue(request, "informationEndTime");
				if(StringUtils.isNotEmpty(informationStartTime)){
					form.setInformationStartTime(DateHelper.getDateFormat(informationStartTime, "yyyy-MM-dd HH:mm:ss"));
				}
				if(StringUtils.isNotEmpty(informationEndTime)){
					form.setInformationEndTime(DateHelper.getDateFormat(informationEndTime, "yyyy-MM-dd HH:mm:ss"));
				}
				InformationPlansAction action = form.createModel(InformationPlansAction.class);
				action.setModifiedBy(modifiedBy);
				boolean flag= informationMaintenanceService.updateInformationPlansAction(action);
				if(flag){
					returnMap.put(ID, action.getInformationPlanId());
					returnMap.put(RECORD_ID, action.getId());
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
