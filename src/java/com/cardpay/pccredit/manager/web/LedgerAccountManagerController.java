package com.cardpay.pccredit.manager.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.manager.filter.LedgerAccountManagerFilter;
import com.cardpay.pccredit.manager.model.LedgerAccountManager;
import com.cardpay.pccredit.manager.service.LedgerAccountManagerService;
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
 * 客户经理台账类的描述
 *
 * @author 王海东
 * @created on 2014-11-19
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/manager/ledgeraccount/*")
@JRadModule("manager.ledgeraccount")
public class LedgerAccountManagerController extends BaseController {
	
	@Autowired
	private LedgerAccountManagerService ledgerAccountManagerService;
	
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
	public AbstractModelAndView browse(@ModelAttribute LedgerAccountManagerFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setLoginId(loginId);
		QueryResult<LedgerAccountManagerForm> result = ledgerAccountManagerService.findLedgerAccountManagersByFilter(filter);
		JRadPagedQueryResult<LedgerAccountManagerForm> pagedResult = new JRadPagedQueryResult<LedgerAccountManagerForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/manager_edgerAccount/manager_edgerAccount_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
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
		JRadModelAndView mv = new JRadModelAndView("/manager/manager_edgerAccount/manager_edgerAccount_change", request);
		String displayName = request.getParameter("display");
		String ledgerId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(ledgerId)) {
			LedgerAccountManager ledgerAccountManager = ledgerAccountManagerService.findLedgerAccountManagerById(ledgerId);
			mv.addObject("ledgerAccountManager", ledgerAccountManager);
		}
		mv.addObject("ledgerId",ledgerId);
		mv.addObject("displayName",displayName);
		return mv;
	}

	/**
	 * 执行修改
	 * 
	 * @param ledgerAccountManagerForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute LedgerAccountManagerForm ledgerAccountManagerForm, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), ledgerAccountManagerForm);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				LedgerAccountManager ledgerAccountManager = ledgerAccountManagerForm.createModel(LedgerAccountManager.class);
				ledgerAccountManager.setModifiedBy(loginId);
				ledgerAccountManagerService.updateLedgerAccountManager(ledgerAccountManager);
				returnMap.put(RECORD_ID, ledgerAccountManager.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
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
		JRadModelAndView mv = new JRadModelAndView("/manager/manager_edgerAccount/manager_edgerAccount_display", request);
		String displayName = request.getParameter("display");
		String ledgerId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(ledgerId)) {
			LedgerAccountManager ledgerAccountManager = ledgerAccountManagerService.findLedgerAccountManagerById(ledgerId);
			mv.addObject("ledgerAccountManager", ledgerAccountManager);
		}
		mv.addObject("ledgerId",ledgerId);
		mv.addObject("displayName",displayName);
		return mv;
	}

}
