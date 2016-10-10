package com.cardpay.pccredit.manager.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.filter.AccountManagerParameterFilter;
import com.cardpay.pccredit.manager.model.AccountManagerParameter;
import com.cardpay.pccredit.manager.service.AccountManagerParameterService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * AccountManagerParameterController类的描述
 * 
 * @author 王海东
 * @created on 2014-11-7
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/manager/managercreate/*")
@JRadModule("manager.managercreate")
public class AccountManagerParameterController extends BaseController {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private AccountManagerParameterService accountManagerParameterService;
	

	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute AccountManagerParameterFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<AccountManagerParameterForm> result = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
		JRadPagedQueryResult<AccountManagerParameterForm> pagedResult = new JRadPagedQueryResult<AccountManagerParameterForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("manager/account_manager/manager_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}

	/**
	 * 增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {

		JRadModelAndView mv = new JRadModelAndView("manager/account_manager/manager_create", request);

		return mv;
	}

	/**
	 * 执行添加
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute AccountManagerParameterForm accountManagerParameterForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), accountManagerParameterForm);
		if (returnMap.isSuccess()) {
			try {
				AccountManagerParameter accountManagerParameter = accountManagerParameterForm.createModel(AccountManagerParameter.class);
				String id = accountManagerParameterService.insertAccountManagerParameter(accountManagerParameter);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	/**
	 * 客户经理岗位配置插入
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "save.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView maintenanceAccountManagersave(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("manager/account_manager/manager_browse", request);
		AccountManagerParameterFilter filter = new AccountManagerParameterFilter();
		filter.setRequest(request);
		try {
			 accountManagerParameterService.insertAccountManagerParameter(request);
				QueryResult<AccountManagerParameterForm> result = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
				JRadPagedQueryResult<AccountManagerParameterForm> pagedResult = new JRadPagedQueryResult<AccountManagerParameterForm>(filter, result);
				mv.addObject(PAGED_RESULT, pagedResult);

		} catch (Exception e) {
			e.printStackTrace();
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("客户经理参数配置保存"+e.getMessage());
			
		}
		return mv;
	 }
	
	/**
	 * 客户经理岗位配置修改
	 * 
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "saveupdate.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView maintenanceAccountManagerchange(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("manager/account_manager/manager_browse", request);
		AccountManagerParameterFilter filter = new AccountManagerParameterFilter();
		filter.setRequest(request);
		try {
			 accountManagerParameterService.updateAccountManagerParameter(request);
				QueryResult<AccountManagerParameterForm> result = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
				JRadPagedQueryResult<AccountManagerParameterForm> pagedResult = new JRadPagedQueryResult<AccountManagerParameterForm>(filter, result);
				mv.addObject(PAGED_RESULT, pagedResult);

		} catch (Exception e) {
			e.printStackTrace();
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("客户经理参数配置保存"+e.getMessage());
			
		}
		return mv;
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
	public AbstractModelAndView display(@ModelAttribute AccountManagerParameterFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/account_manager/manager_display", request);
		String managerId = RequestHelper.getStringValue(request, ID);
		QueryResult<AccountManagerParameterForm> result = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
		List<AccountManagerParameterForm> apf = result.getItems();
		for (AccountManagerParameterForm accountManagerParameterForm : apf) {
			if (accountManagerParameterForm.getId().equals(managerId)) {
				String displayName = accountManagerParameterForm.getDisplayName();
				mv.addObject("displayName", displayName);
			}
		}

		if (StringUtils.isNotEmpty(managerId)) {
			AccountManagerParameter accountManagerParameter = accountManagerParameterService.findAccountManagerParameterById(managerId);
			mv.addObject("accountManagerParameter", accountManagerParameter);

		}
	
		return mv;
	}

	/**
	 * 修改页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(@ModelAttribute AccountManagerParameterFilter filter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/account_manager/manager_change", request);

		String managerId = RequestHelper.getStringValue(request, ID);
		QueryResult<AccountManagerParameterForm> result = accountManagerParameterService.findAccountManagerParametersByFilter(filter);
		List<AccountManagerParameterForm> apf = result.getItems();
		for (AccountManagerParameterForm accountManagerParameterForm : apf) {
			if (accountManagerParameterForm.getId().equals(managerId)) {
				String displayName = accountManagerParameterForm.getDisplayName();
				mv.addObject("displayName", displayName);
			}
		}

		if (StringUtils.isNotEmpty(managerId)) {
			AccountManagerParameter accountManagerParameter = accountManagerParameterService.findAccountManagerParameterById(managerId);
			mv.addObject("accountManagerParameter", accountManagerParameter);
		}
		return mv;
	}
	
	/**
	 * 执行修改
	 * 
	 * @param accountManagerParameterForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute AccountManagerParameterForm accountManagerParameterForm, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), accountManagerParameterForm);
		if (returnMap.isSuccess()) {
			try {
				AccountManagerParameter accountManagerParameter = accountManagerParameterForm.createModel(AccountManagerParameter.class);
				accountManagerParameterService.updateAccountManagerParameter(accountManagerParameter);
				returnMap.put(RECORD_ID, accountManagerParameter.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	
	/**
	 * 执行删除
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete.json")
	@JRadOperation(JRadOperation.DELETE)
	public JRadReturnMap delete(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		try {
			String managerId = RequestHelper.getStringValue(request, ID);
			accountManagerParameterService.deleteAccountManagerParameter(managerId);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}

}
