package com.cardpay.pccredit.riskControl.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.CustomerAccountInforFilter;
import com.cardpay.pccredit.customer.service.CustomerAccountInfoService;
import com.cardpay.pccredit.customer.web.CustomerAccountInfoForm;
import com.cardpay.pccredit.riskControl.constant.RiskConstants;
import com.cardpay.pccredit.riskControl.filter.NplsInfomationFilter;
import com.cardpay.pccredit.riskControl.model.NplsInfomation;
import com.cardpay.pccredit.riskControl.service.NplsInfomationService;
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
 * 
 * 描述 ：不良资产controller
 * @author 张石树
 *
 * 2014-11-4 上午10:16:24
 */
@Controller
@RequestMapping("/riskcontrol/nplsinfomation/*")
@JRadModule("riskcontrol.nplsinfomation")
public class NplsInfomationController extends BaseController {
	
	@Autowired
	private NplsInfomationService nplsInfomationService;
	
	@Autowired
	private CustomerAccountInfoService customerAccountInfoService;
	
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
	public AbstractModelAndView browse(@ModelAttribute NplsInfomationFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
		QueryResult<NplsInfomationForm> result = nplsInfomationService.findNplsInfomationByFilter(filter);
		JRadPagedQueryResult<NplsInfomationForm> pagedResult = new JRadPagedQueryResult<NplsInfomationForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/nplsinfomation/nplsinfomation_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 增加需要的账户页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "nplsaccountbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createTo(@ModelAttribute CustomerAccountInforFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<CustomerAccountInfoForm> result = customerAccountInfoService.findCustomerAccountsByFilter(filter);
		JRadPagedQueryResult<CustomerAccountInfoForm> pagedResult = new JRadPagedQueryResult<CustomerAccountInfoForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/nplsinfomation/nplsinfomation_account_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 增加数据页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "nplsinfocreate.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/nplsinfomation/nplsinfomation_create", request);
		String customerAccountId = request.getParameter("id");
		CustomerAccountInfoForm accountInfoForm = customerAccountInfoService.findCustomerAccountById(customerAccountId);
		mv.addObject("accountInfo", accountInfoForm);
		return mv;
	}
	
	/**
	 * 执行添加
	 * @param nplsInfomationForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute NplsInfomationForm nplsInfomationForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), nplsInfomationForm);
		if (returnMap.isSuccess()) {
			try {
				NplsInfomation nplsInfomation = nplsInfomationForm.createModel(NplsInfomation.class);
		        User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		        nplsInfomation.setCreateMethod(RiskConstants.NPLS_CREATE_METHOD_MANUAL);
		        nplsInfomation.setReviewResults(RiskConstants.NPLS_INFO_NEW);
		        nplsInfomation.setCreatedBy(user.getId());
		        nplsInfomation.setModifiedBy(user.getId());
		        nplsInfomation.setCreatedTime(new Date());
		        nplsInfomation.setModifiedTime(new Date());
				nplsInfomationService.insertNplsInfomaton(nplsInfomation);
				returnMap.put(RECORD_ID, nplsInfomation.getId());
				returnMap.addGlobalMessage(CREATE_SUCCESS);
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
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/nplsinfomation/nplsinfomation_change", request);
		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			NplsInfomationForm nplsInfomation = nplsInfomationService.findNplsInfomationById(id);
			mv.addObject("nplsInfomation", nplsInfomation);
		}
		return mv;
	}


	/**
	 * 执行修改
	 * @param riskConsiderationsForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute NplsInfomationForm nplsInfomationForm, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), nplsInfomationForm);
		if (returnMap.isSuccess()) {
			try {
				NplsInfomation nplsInfomation = nplsInfomationForm.createModel(NplsInfomation.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				nplsInfomation.setModifiedBy(user.getId());
				nplsInfomation.setModifiedTime(new Date());
				nplsInfomationService.updateNplsInfomation(nplsInfomation);
				returnMap.put(RECORD_ID, nplsInfomation.getId());
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
			String id = RequestHelper.getStringValue(request, ID);
			nplsInfomationService.deleteNplsInfomation(id);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	/**
	 * 执行 确认
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "confirm.json")
	public JRadReturnMap confirm(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = RequestHelper.getStringValue(request, ID);
			String verificationAccount = request.getParameter("verificationAccount");
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			nplsInfomationService.confirm(id, user, verificationAccount);
			returnMap.addGlobalMessage(RiskConstants.NPLS_CONFIRM_MESSAGE);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	/**
	 * 执行拒绝
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "refuse.json")
	public JRadReturnMap refuse(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		try {
			String id = RequestHelper.getStringValue(request, ID);
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			nplsInfomationService.refuse(id, user);
			returnMap.addGlobalMessage(RiskConstants.NPLS_CONFIRM_REFUSE);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
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
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/nplsinfomation/nplsinfomation_display", request);
		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			NplsInfomationForm nplsInfomation = nplsInfomationService.findNplsInfomationById(id);
			mv.addObject("nplsInfomation", nplsInfomation);
		}
		return mv;
	}

}
