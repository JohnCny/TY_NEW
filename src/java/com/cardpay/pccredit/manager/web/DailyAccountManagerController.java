package com.cardpay.pccredit.manager.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.filter.DailyAccountManagerFilter;
import com.cardpay.pccredit.manager.model.DailyAccountManager;
import com.cardpay.pccredit.manager.service.DailyAccountService;
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
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * DailyAccountManagerController类的描述
 * 
 * @author 王海东
 * @created on 2014-11-19
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/manager/dailyaccount/*")
@JRadModule("manager.dailyaccount")
public class DailyAccountManagerController extends BaseController {

	@Autowired
	private DailyAccountService dailyAccountService;

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
	public AbstractModelAndView browse(@ModelAttribute DailyAccountManagerFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		boolean lock = false;
		if(user.getUserType() == 1){
			String loginId = user.getId();
			filter.setLoginId(loginId);
			lock = true;
		}
		filter.setDisplayName(request.getParameter("displayName"));
		QueryResult<DailyAccountManagerForm> result = dailyAccountService.findDailyAccountManagersByFilter(filter);
		JRadPagedQueryResult<DailyAccountManagerForm> pagedResult = new JRadPagedQueryResult<DailyAccountManagerForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/dailyreport/daily_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("lock", lock);
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
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/dailyreport/daily_change", request);

		String dailyId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(dailyId)) {
			DailyAccountManager dailyAccountManager = dailyAccountService.findDailyAccountManagerById(dailyId);
			mv.addObject("dailyAccountManager", dailyAccountManager);
		}
		mv.addObject("dailyId",dailyId);
		return mv;
	}

	/**
	 * 执行修改
	 * 
	 * @param dailyAccountManagerForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute DailyAccountManagerForm dailyAccountManagerForm, HttpServletRequest request) {
//		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), dailyAccountManagerForm);
		JRadReturnMap returnMap =  new JRadReturnMap();
//		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				DailyAccountManager dailyAccountManager = dailyAccountManagerForm.createModel(DailyAccountManager.class);
				dailyAccountManager.setModifiedBy(loginId);
				dailyAccountService.updateAccountManager(dailyAccountManager);
				returnMap.put(RECORD_ID, dailyAccountManager.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
//		}

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
		JRadModelAndView mv = new JRadModelAndView("/manager/dailyreport/daily_display", request);

		String dailyId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(dailyId)) {
			DailyAccountManager dailyAccountManager = dailyAccountService.findDailyAccountManagerById(dailyId);
			mv.addObject("dailyAccountManager", dailyAccountManager);
		}
		return mv;
	}

}
