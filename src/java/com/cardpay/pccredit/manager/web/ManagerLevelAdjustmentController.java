package com.cardpay.pccredit.manager.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.manager.filter.AccountManagerParameterFilter;
import com.cardpay.pccredit.manager.filter.ManagerLevelAdjustmentFilter;
import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.model.ManagerMonthTargetData;
import com.cardpay.pccredit.manager.model.ManagerSalary;
import com.cardpay.pccredit.manager.model.TJxParameters;
import com.cardpay.pccredit.manager.model.TJxSpecificParameters;
import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.service.ManagerLevelAdjustmentService;
import com.cardpay.pccredit.manager.service.ManagerSalaryService;
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
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;


/**
 * 描述 ：客户经理级别调整controller
 * @author songchen
 */
@Controller
@RequestMapping("/manager/leveladjustment/*")
@JRadModule("manager.leveladjustment")
public class ManagerLevelAdjustmentController extends BaseController{
	
	@Autowired
	private ManagerLevelAdjustmentService managerLevelAdjustmentService;
	
	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	
	@Autowired
	private ManagerSalaryService managerSalaryService;
	
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
	public AbstractModelAndView browse(@ModelAttribute ManagerLevelAdjustmentFilter filter,
		HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<ManagerLevelAdjustmentForm> result = managerLevelAdjustmentService.findManagerLevelAdjustmentByFilter(filter);
		JRadPagedQueryResult<ManagerLevelAdjustmentForm> pagedResult = new JRadPagedQueryResult<ManagerLevelAdjustmentForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/level_adjustment/manager_leveladjustment_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 查看客户经理每月指标信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display_assessment_browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView displayOrganization(@ModelAttribute ManagerLevelAdjustmentFilter filter,
		HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<ManagerMonthTargetData> result = managerLevelAdjustmentService.findManagerMonthTargetDataByFilter(filter);
		JRadPagedQueryResult<ManagerMonthTargetData> pagedResult = new JRadPagedQueryResult<ManagerMonthTargetData>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/level_adjustment/manager_assessment_target_data_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 执行 确认
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "handleAdjustmentLevel.json")
	public JRadReturnMap handleAdjustmentLevel(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = RequestHelper.getStringValue(request, ID);
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			managerLevelAdjustmentService.handleAdjustmentLevel(id, user);
			returnMap.addGlobalMessage(ManagerLevelAdjustmentConstant.IF_HANDLE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	
	
	
	
	
	//-------------------------------------------20160823济南绩效相关---------------------------------------------------------//
	
	/**
	 * 查询客户经理基本薪酬
	 */
	@ResponseBody
	@RequestMapping(value = "browsejnjbxc.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute AccountManagerParameterFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<AccountManagerParameterForm> result = accountManagerParameterService.findAccountManagerParametersByFilterForJx(filter);
		JRadPagedQueryResult<AccountManagerParameterForm> pagedResult = new JRadPagedQueryResult<AccountManagerParameterForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/jxxc/manager_jbxc_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/**
	 * 跳转到客户经理薪酬的修改界面
	 * 管理岗
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(@ModelAttribute AccountManagerParameterFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/jxxc/manager_jx_change", request);

		String str [] = RequestHelper.getStringValue(request, ID).split("@");
		mv.addObject("displayName", str[1]);
		
		if (StringUtils.isNotEmpty(str[0])) {
			AccountManagerParameter accountManagerParameter = accountManagerParameterService.findAccountManagerParameterById(str[0]);
			mv.addObject("accountManagerParameter", accountManagerParameter);
		}
		return mv;
	}
	
	/**
	 * 修改客户经理薪酬
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute AccountManagerParameterForm accountManagerParameterForm, HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				AccountManagerParameter accountManagerParameter = accountManagerParameterForm.createModel(AccountManagerParameter.class);
				accountManagerParameterService.updateAccountManagerParameter(accountManagerParameter);
				returnMap.put(RECORD_ID, accountManagerParameter.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
				returnMap.setSuccess(true);
			}
			catch (Exception e) {
				returnMap.setSuccess(false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	
	
	/**
	 * 客户经理绩效每月绩效参数
	 */
	@ResponseBody
	@RequestMapping(value = "browseMonthJx.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browseMonthJx(@ModelAttribute ManagerSalaryFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		if(user.getUserType() == 1){
			filter.setCustomerId(userId);
		}
		
		String dateStr = RequestHelper.getStringValue(request, "date");
		if(!StringUtils.isEmpty(dateStr)){
			filter.setYear(dateStr.substring(0, 4));
			filter.setMonth( dateStr.substring(5, 7));
		}
		
		QueryResult<TJxParameters> result = accountManagerParameterService.findMonthJx(filter);
		JRadPagedQueryResult<TJxParameters> pagedResult = new JRadPagedQueryResult<TJxParameters>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/jxxc/manager_jx_param_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/**
	 * 客户每月日均贷款余额
	 */
	@ResponseBody
	@RequestMapping(value = "browseCustDayBalamt.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browseCustDayBalamt(@ModelAttribute ManagerSalaryFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		if(user.getUserType() == 1){
			filter.setCustomerId(userId);
		}
		
		String dateStr = RequestHelper.getStringValue(request, "date");
		if(!StringUtils.isEmpty(dateStr)){
			filter.setYear(dateStr.substring(0, 4));
			filter.setMonth( dateStr.substring(5, 7));
		}
		QueryResult<TJxSpecificParameters> result = accountManagerParameterService.findCustDayBalamt(filter);
		JRadPagedQueryResult<TJxSpecificParameters> pagedResult = new JRadPagedQueryResult<TJxSpecificParameters>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/jxxc/manager_jx_balamt_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	
	
	/**
	 * 客户经理工资查询
	 */
	@ResponseBody
	@RequestMapping(value = "jnJxbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute ManagerSalaryFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		JRadModelAndView mv = new JRadModelAndView("/jxxc/manager_salary_jx_browse", request);
		String chineseName = request.getParameter("userId");
		
		String date = request.getParameter("date");
		if(StringUtils.isNotEmpty(date)){
			filter.setYear(date.substring(0, 4));
			filter.setMonth(date.substring(5, 7));
		}
		
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		List<AccountManagerParameterForm> forms = managerBelongMapService.findSubListManagerByManagerId(user);
		String customerManagerId = filter.getCustomerManagerId();
		
		QueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm> result = null;
		if(customerManagerId!=null && !customerManagerId.equals("")){
			forms =  managerBelongMapService.findAllManager();
			filter.setCustomerManagerIds(forms);
			result = managerSalaryService.findManagerSalaryByFilterJn(filter);
		}else{
			if(forms.size()>0){
				filter.setCustomerManagerIds(forms);
				result = managerSalaryService.findManagerSalaryByFilterJn(filter);
			}else{
				forms =  managerBelongMapService.findAllManager();
				filter.setCustomerManagerIds(forms);
				result = managerSalaryService.findManagerSalaryByFilterJn(filter);
			}
		}
		filter.setManagerName(chineseName);
		JRadPagedQueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm> pagedResult =
				new JRadPagedQueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("forms", forms);
		return mv;
	}
	
	/**
	 * 生成月度数据
	 */
	@ResponseBody
	@RequestMapping(value = "generateData.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap generateData(@ModelAttribute ManagerSalaryForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String date = request.getParameter("date");
			managerSalaryService.docalculateMonthlySalaryTy(date.substring(0, 4),date.substring(5, 7));
			returnMap.setSuccess(true);
		}
		catch (Exception e) {
			returnMap.setSuccess(false);
			returnMap.addGlobalError(e.getMessage());
			return returnMap;
			
		}
		return returnMap;
	}
	
	/**
	 * 跳转到打印页面
	 */
	@ResponseBody
	@RequestMapping(value = "print.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView print(@ModelAttribute ManagerSalaryFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		int limit = 20;
		filter.setLimit(limit);
		JRadModelAndView mv = new JRadModelAndView("/jxxc/manager_salary_print", request);
		String chineseName = request.getParameter("userId");
		
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		List<AccountManagerParameterForm> forms = managerBelongMapService.findSubListManagerByManagerId(user);
		String customerManagerId = filter.getCustomerManagerId();
		
		QueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm> result = null;
		if(customerManagerId!=null && !customerManagerId.equals("")){
			forms =  managerBelongMapService.findAllManager();
			filter.setCustomerManagerIds(forms);
			result = managerSalaryService.findManagerSalaryByFilterJn(filter);
		}else{
			if(forms.size()>0){
				filter.setCustomerManagerIds(forms);
				result = managerSalaryService.findManagerSalaryByFilterJn(filter);
			}else{
				forms =  managerBelongMapService.findAllManager();
				filter.setCustomerManagerIds(forms);
				result = managerSalaryService.findManagerSalaryByFilterJn(filter);
			}
		}
		filter.setManagerName(chineseName);
		JRadPagedQueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm> pagedResult = 
				new JRadPagedQueryResult<com.cardpay.pccredit.manager.model.ManagerSalaryForm>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "changeSalary.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changeSalary(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/jxxc/manager_adjust_update", request);
		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			ManagerSalary salary = managerSalaryService.findManagerSalaryById(id);
			mv.addObject("salary", salary);
			mv.addObject("id", id);
		}
		return mv;
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "updateManagerSalary.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updateManagerSalary(@ModelAttribute ManagerSalaryForm from, HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				ManagerSalary salary = from.createModel(ManagerSalary.class);
				int i = managerSalaryService.updateManagerSalary(salary);
				returnMap.put(MESSAGE, "修改成功");
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/**
	 * 导出excel
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "exportData.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap exportData(@ModelAttribute ManagerSalaryFilter filter, HttpServletRequest request,HttpServletResponse response) {
		JRadReturnMap returnMap = new JRadReturnMap();
		filter.setRequest(request);
		String date = request.getParameter("date");
		filter.setYear(date.substring(0, 4));
		filter.setMonth(date.substring(5, 7));
		returnMap.setSuccess(true);
		if (returnMap.isSuccess()) {
			try {
			    managerSalaryService.getExportWageData(filter,response);
				
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "exportDatas.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap exportDatas(@ModelAttribute ManagerSalaryFilter filter, HttpServletRequest request,HttpServletResponse response) {
		JRadReturnMap returnMap = new JRadReturnMap();
		filter.setRequest(request);
		String date = request.getParameter("date");
		filter.setYear(date.substring(0, 4));
		filter.setMonth(date.substring(5, 7));
		returnMap.setSuccess(true);
		if (returnMap.isSuccess()) {
			try {
			    managerSalaryService.getExportWageDatas(filter,response);
				
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}

}
