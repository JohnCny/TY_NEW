/**
 * 
 */
package com.cardpay.pccredit.customer.web;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.MaintenanceConstant;
import com.cardpay.pccredit.customer.constant.MaintenanceEndResultEnum;
import com.cardpay.pccredit.customer.constant.MarketingCreateWayEnum;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.Maintenance;
import com.cardpay.pccredit.customer.model.MaintenanceAction;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.SysOrganizationForm;
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
 * 2014年11月11日   下午2:27:27
 */
@Controller
@RequestMapping("/customer/maintenance/*")
@JRadModule("customer.maintenance")
public class MaintenanceController extends BaseController{
	@Autowired
	private MaintenanceService maintenanceService;

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
	public AbstractModelAndView browse(@ModelAttribute MaintenanceFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_browse", request);
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		//查询客户经理
		List<AccountManagerParameterForm> forms = maintenanceService.findSubListManagerByManagerId(user);
		String customerManagerId = filter.getCustomerManagerId();
		QueryResult<MaintenanceForm> result = null;
		if(customerManagerId!=null && !customerManagerId.equals("")){
			result = maintenanceService.findMaintenancePlansList(filter);
		}else{
			if(forms.size()>0){
				filter.setCustomerManagerIds(forms);
				result = maintenanceService.findMaintenancePlansList(filter);
			}else{
				//直接返回页面
				return mv;
			}
		}
		JRadPagedQueryResult<MaintenanceForm> pagedResult = new JRadPagedQueryResult<MaintenanceForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
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
		String str [] = RequestHelper.getStringValue(request, ID).split("/");
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_create", request);
	    MaintenanceWeb m = new MaintenanceWeb();
	    m.setAppId(str[0]);
		MaintenanceForm maintenance = maintenanceService.findMaintenAndAppInfo(m);
		mv.addObject("maintenance",maintenance);
		mv.addObject("appId", str[0]);
		mv.addObject("userId",userId);
		mv.addObject("customerManagerId",str[1]);
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
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_change", request);
		String str [] = RequestHelper.getStringValue(request, ID).split("/");
		if (StringUtils.isNotEmpty(str[0])) {
			MaintenanceForm maintenance = maintenanceService.findMaintenanceById(str[0]);
			mv.addObject("maintenance", maintenance);
			mv.addObject("appId",str[1]);
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
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_display", request);
		String [] str = RequestHelper.getStringValue(request, ID).split("/");
		String maintenanceId = str[0];
		String appId = str[1];
		if (StringUtils.isNotEmpty(maintenanceId)) {
			MaintenanceWeb m = new MaintenanceWeb();
			m.setId(maintenanceId);
			m.setAppId(appId);
			MaintenanceForm maintenance = maintenanceService.findMaintenance(m);
			List<MaintenanceWeb> maintenanceActions = maintenanceService.findMaintenanceActionByMaintenanceId(maintenanceId);
			mv.addObject("maintenanceWeb", m);
			mv.addObject("maintenance", maintenance);
			mv.addObject("maintenanceActions",maintenanceActions);
			mv.addObject("appId",appId);
		}

		return mv;
	}
	
	/**
	 * 显示维护计划列表页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "displayInfo.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView displayInfo(@ModelAttribute MaintenanceFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_displayInfo", request);
		String [] str = RequestHelper.getStringValue(request, ID).split("/");
		String appId = str[0];
		filter.setAppId(appId);
		filter.setRequest(request);
		QueryResult<MaintenanceWeb> result = maintenanceService.findMaintenanceWebPlansByFilter(filter);
		
		for(int i=0;i<result.getItems().size();i++){
			MaintenanceWeb web = (MaintenanceWeb)result.getItems().get(i);
			if(web!=null){
				if("nextmaintain".equals(web.getEndResult())){
					result.getItems().get(i).setEndResult("继续维护");
				}else if("maintaining".equals(web.getEndResult())){
					result.getItems().get(i).setEndResult("维护中");
				}else if("complete".equals(web.getEndResult())){
					result.getItems().get(i).setEndResult("维护完成");
				}else{
					result.getItems().get(i).setEndResult("维护中");
				}
			}
		}
		JRadPagedQueryResult<MaintenanceWeb> pagedResult = new JRadPagedQueryResult<MaintenanceWeb>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("appId", appId);
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
	public JRadReturnMap insert(@ModelAttribute MaintenanceForm form, HttpServletRequest request) {
		//boolean flag = maintenanceService.checkRepeat(form.getCustomerId(),MaintenanceEndResultEnum.maintaining);
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
//		if(!flag){
			if (returnMap.isSuccess()) {
				try {
					Maintenance maintenance = form.createModel(Maintenance.class);
					IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
					String createdBy = user.getId();
					maintenance.setCreatedBy(createdBy);
					String customerManagerId = maintenance.getCustomerManagerId();

					if(customerManagerId!=null && customerManagerId.equals(createdBy)){
						maintenance.setCreateWay(MarketingCreateWayEnum.myself.toString());;
					}else{
						maintenance.setCreateWay(MarketingCreateWayEnum.manager.toString());;
					}

					String id = maintenanceService.insertMaintenance(maintenance);
					returnMap.put(RECORD_ID, id);
					returnMap.addGlobalMessage(CREATE_SUCCESS);
				}catch (Exception e) {
					returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
					returnMap.put(JRadConstants.SUCCESS, false);
					return WebRequestHelper.processException(e);
				}
			}
//		}else{
//			returnMap.setSuccess(false);
//			returnMap.addGlobalError(MaintenanceConstant.alreadyExists);
//		}
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
		String appId = RequestHelper.getStringValue(request, "appId");
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_action_create", request);
		mv.addObject("maintenanceId",maintenanceId);
		mv.addObject("appId",appId);
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
	public JRadReturnMap insertAction(@ModelAttribute MaintenanceForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				Maintenance maintenance = new Maintenance();
				String endResult= form.getEndResult();
				String maintenanceId = form.getMaintenancePlanId();
				String appId = form.getAppId();
				maintenance.setId(maintenanceId);
				maintenance.setEndResult(endResult);
				/*修改催收计划最终结果*/
				boolean flag = maintenanceService.updateMaintenancePassive(maintenance);
				if(flag){
					/*在当前计划下添加一条新的实施信息记录*/
					MaintenanceAction maintenanceAction = new MaintenanceAction();
					IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
					String createdBy = user.getId();
					String maintenanceEndTime = form.getMaintenanceEndTime();
					String maintenanceStartTime = form.getMaintenanceStartTime();
					maintenanceAction.setCreatedBy(createdBy);
					if(maintenanceEndTime!=null && !maintenanceEndTime.equals("")){
						maintenanceAction.setMaintenanceEndTime(DateHelper.getDateFormat(maintenanceEndTime, "yyyy-MM-dd HH:mm:ss"));
					}
					if(maintenanceStartTime!=null && !maintenanceStartTime.equals("")){
						maintenanceAction.setMaintenanceStartTime(DateHelper.getDateFormat(maintenanceStartTime,"yyyy-MM-dd HH:mm:ss"));
					}
					maintenanceAction.setMaintenancePlanId(form.getMaintenancePlanId());
					maintenanceAction.setMaintenanceResult(form.getMaintenanceResult());
					maintenanceAction.setMaintenanceWay(form.getMaintenanceWay());
					String id = maintenanceService.insertMaintenanceAction(maintenanceAction);
					/*若最终结果为继续维护*/
					String maintenancePlanId = "";
					if(endResult.equals(MaintenanceEndResultEnum.nextmaintain.toString())){
						/*新增一条新的计划，计划内容与当前计划相同,最终结果为维护中*/
						maintenancePlanId = maintenanceService.copyMaintenancePlan(maintenanceId, MaintenanceEndResultEnum.maintaining, createdBy);
						/*返回最初维护计划页面*/
						returnMap.put(ID, maintenanceId+"/"+appId);
						returnMap.put(RECORD_ID,maintenancePlanId);
					}else{
						/*返回维护计划详细信息页面*/
						returnMap.put(ID, maintenanceId+"/"+appId);
						returnMap.put(RECORD_ID, id);
					}
					returnMap.put(JRadConstants.MESSAGE, endResult);
					returnMap.addGlobalMessage(CREATE_SUCCESS);
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
		String appId = RequestHelper.getStringValue(request, "appId");
		MaintenanceAction maintenanceAction = maintenanceService.findMaintenanceActionById(id);
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_action_change", request);
		mv.addObject("maintenanceAction",maintenanceAction);
		mv.addObject("appId",appId);
		return mv;
	}
	
	/**
	 * 修改催收实施计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "displayAction.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView displayAction(HttpServletRequest request) {
		String id = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		MaintenanceAction maintenanceAction = maintenanceService.findMaintenanceActionById(id);
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_action_browe", request);
		mv.addObject("maintenanceAction",maintenanceAction);
		mv.addObject("appId",appId);
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
				String appId = form.getAppId();
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
					returnMap.put(ID, maintenanceAction.getMaintenancePlanId()+"/"+appId);
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
	@RequestMapping(value = "getManager.json",method = RequestMethod.GET)
	public void getManager(HttpServletRequest request,PrintWriter printWriter){
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			List<AccountManagerParameterForm> accountManagerParameterForms = managerBelongMapService.findSubManagerBelongMapByManagerId(userId);
			JSONArray json = new JSONArray();
			json = JSONArray.fromObject(accountManagerParameterForms);
			printWriter.write(json.toString());
			printWriter.flush();
			printWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "getCustomer.json",method = RequestMethod.GET)
	public void getCustomer(HttpServletRequest request,PrintWriter printWriter){
		try {
			String userId = RequestHelper.getStringValue(request, ID);
			List<CustomerInfor> customers = customerInforService.findCustomerByManagerId(userId);
			JSONArray json = new JSONArray();
			json = JSONArray.fromObject(customers);
			printWriter.write(json.toString());
			printWriter.flush();
			printWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
/*	*//**
	 * 浏览维护计划日志页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "log.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView log(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		//查询下属客户经理
		List<AccountManagerParameterForm> forms = maintenanceService.findSubListManagerByManagerId(user);
		if(forms.size()>0){
			filter.setCustomerManagerIds(forms);		
		}else{
			filter.setCustomerManagerIds(null);		
		}

		QueryResult<MaintenanceLog> result = customerInforService.findCustomerByFilter(filter);
		JRadPagedQueryResult<MaintenanceLog> pagedResult = new JRadPagedQueryResult<MaintenanceLog>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_log", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		return mv;

	}*/
	
	
	/**
	 * 浏览维护计划日志页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "log.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView log(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		JRadModelAndView mv;
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			filter.setUserId(user.getId());
			QueryResult<CustomerInforFilter> result = customerInforService.findUpdateCustormer(filter);
			JRadPagedQueryResult<CustomerInforFilter> pagedResult = new JRadPagedQueryResult<CustomerInforFilter>(filter, result);
			mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_log", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
	}
	
	
	/**
	 * 浏览查找维护计划日志页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "log1.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView log1(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		JRadModelAndView mv;
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			filter.setUserId(user.getId());
			filter.setCardId(request.getParameter("cardId")+"%");
			filter.setChineseName(request.getParameter("name")+"%");
			QueryResult<CustomerInforFilter> result = customerInforService.findUpdateCustormer1(filter);
			JRadPagedQueryResult<CustomerInforFilter> pagedResult = new JRadPagedQueryResult<CustomerInforFilter>(filter, result);
			mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_log", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
	}
	
	/**
	 * 维护计划列表
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "log_info.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView log_info(@ModelAttribute MaintenanceFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_log_info", request);
		String ids = RequestHelper.getStringValue(request, ID);
		String customerId = ids.split("@")[0];
		String productId =  ids.split("@")[1];
		filter.setRequest(request);
//		filter.setCustomerId(customerId);
//		filter.setProductId(productId);
		String appId = maintenanceService.getAppId(customerId, productId);
		filter.setAppId(appId);
		QueryResult<MaintenanceWeb> result = maintenanceService.findMaintenanceWebPlansByFilter(filter);
		JRadPagedQueryResult<MaintenanceWeb> pagedResult = new JRadPagedQueryResult<MaintenanceWeb>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("appId", appId);
		return mv;
	}
	
	
	/**
	 * 显示维护计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "log_display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView log_display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_log_display", request);
		String [] str = RequestHelper.getStringValue(request, ID).split("/");
		String maintenanceId = str[0];
		String appId = str[1];
		if (StringUtils.isNotEmpty(maintenanceId)) {
			MaintenanceWeb m = new MaintenanceWeb();
			m.setAppId(appId);
			m.setId(maintenanceId);
			MaintenanceForm maintenance = maintenanceService.findMaintenance(m);
			List<MaintenanceWeb> maintenanceActions = maintenanceService.findMaintenanceActionByMaintenanceId(maintenanceId);
			mv.addObject("maintenanceWeb", m);
			mv.addObject("maintenance", maintenance);
			mv.addObject("maintenanceActions",maintenanceActions);
		}

		return mv;
	}
	/**
	 * 增加催收实施计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "log_createAction.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView log_createAction(HttpServletRequest request) {
		String maintenanceId = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_log_action_create", request);
		mv.addObject("maintenanceId",maintenanceId);
		mv.addObject("appId",appId);
		return mv;
	}
	/**
	 * 修改催收实施计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "log_changeAction.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView log_changeAction(HttpServletRequest request) {
		String id = RequestHelper.getStringValue(request, ID);
		String appId = RequestHelper.getStringValue(request, "appId");
		MaintenanceAction maintenanceAction = maintenanceService.findMaintenanceActionById(id);
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_log_action_change", request);
		mv.addObject("maintenanceAction",maintenanceAction);
		mv.addObject("appId",appId);
		return mv;
	}
	/**
	 * 增加维护计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "log_create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView log_create(HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId(); 
		String appId= RequestHelper.getStringValue(request, ID);
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_log_create", request);
	    MaintenanceWeb m = new MaintenanceWeb();
	    m.setAppId(appId);
		MaintenanceForm maintenance = maintenanceService.findMaintenAndAppInfo(m);
		mv.addObject("maintenance",maintenance);
		mv.addObject("appId", appId);
		mv.addObject("userId",userId);
		return mv;
	}

	/**
	 * 修改维护计划信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "log_change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView log_change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_log_change", request);
		String str [] = RequestHelper.getStringValue(request, ID).split("/");
		if (StringUtils.isNotEmpty(str[0])) {
			MaintenanceForm maintenance = maintenanceService.findMaintenanceById(str[0]);
			String productId = maintenanceService.getProductId(str[1]);
			mv.addObject("maintenance", maintenance);
			mv.addObject("productId", productId);
		}
		return mv;
	}
}
