package com.cardpay.pccredit.manager.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.filter.WeeklyAccountManagerFilter;
import com.cardpay.pccredit.manager.model.WeeklyAccountManager;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.service.WeeklyAccountService;
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
 * WeeklyAccountManagerController类的描述
 * 
 * @author 王海东
 * @created on 2014-11-19
 * 
 * @version $Id:$
 */

@Controller
@RequestMapping("/manager/weeklyaccountzuzhang/*")
@JRadModule("manager.weeklyaccountzuzhang")
public class WeeklyAccountManagerZuzhangController extends BaseController {

	@Autowired
	private WeeklyAccountService weeklyAccountService;
	
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;

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
	public AbstractModelAndView browse(@ModelAttribute WeeklyAccountManagerFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setLoginId(loginId);
		List<AccountManagerParameterForm> apf = managerBelongMapService.findSubListManagerByManagerId(loginId);
		JRadPagedQueryResult<WeeklyAccountManagerForm> pagedResult = null;
		if(apf.size() > 0){
			List<String> subManagerIds = new ArrayList<String>();
			for(AccountManagerParameterForm managerParameterForm : apf){
				subManagerIds.add(managerParameterForm.getUserId());
			}
			filter.setSubManagerIds(subManagerIds);
			QueryResult<WeeklyAccountManagerForm> result = weeklyAccountService.findSubWeeklyAccountManagersByFilter(filter);
			pagedResult = new JRadPagedQueryResult<WeeklyAccountManagerForm>(filter, result);
		}else{
			QueryResult<WeeklyAccountManagerForm> result = weeklyAccountService.findSubWeeklyAccountManagersByFilter(filter);
			pagedResult = new JRadPagedQueryResult<WeeklyAccountManagerForm>(filter, result);
				
		}
		
		
		JRadModelAndView mv = new JRadModelAndView("/manager/weekreport/week_browse_zuzhang", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("apf", apf);
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
		JRadModelAndView mv = new JRadModelAndView("/manager/weekreport/week_change_zuzhang", request);

		String weekId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(weekId)) {
			WeeklyAccountManager weeklyAccountManager = weeklyAccountService.findWeeklyAccountManagerById(weekId);
			mv.addObject("weeklyAccountManager", weeklyAccountManager);
		}
		return mv;
	}

	/**
	 * 执行修改
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute WeeklyAccountManagerForm weeklyAccountManagerForm, HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();//WebRequestHelper.requestValidation(getModuleName(), weeklyAccountManagerForm);
		//if (returnMap.isSuccess()) {
			try {
				WeeklyAccountManager weeklyAccountManager = weeklyAccountManagerForm.createModel(WeeklyAccountManager.class);
				weeklyAccountService.updateWeeklyAccount(weeklyAccountManager);
				returnMap.put(RECORD_ID, weeklyAccountManager.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		//}

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
		JRadModelAndView mv = new JRadModelAndView("/manager/weekreport/week_display_zuzhang", request);
		String weekId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(weekId)) {
			WeeklyAccountManager weeklyAccountManager = weeklyAccountService.findWeeklyAccountManagerById(weekId);
			mv.addObject("weeklyAccountManager", weeklyAccountManager);
		}
		return mv;
	}

}
