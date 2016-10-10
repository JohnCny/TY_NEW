package com.cardpay.pccredit.riskControl.web;

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

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CardInfomationService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.web.CardInfomationFrom;
import com.cardpay.pccredit.riskControl.constant.AuditStatusEnum;
import com.cardpay.pccredit.riskControl.filter.AccountabilityRecordFilter;
import com.cardpay.pccredit.riskControl.model.AccountabilityRecord;
import com.cardpay.pccredit.riskControl.service.AccountabilityService;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.service.SystemUserService;
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
 * 
 * @author 季东晓
 *
 * 2014-11-5 上午11:52:13
 */
@Controller
@RequestMapping("/accountabilitycontrol/accountabilitymanager/*")
@JRadModule("riskcontrol.accountabilityrecordmanager")
public class ManagerAccountabilityController extends BaseController {
	
	@Autowired
	AccountabilityService accountabilityService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private CardInfomationService cardInfomationService;
	
	
	/**
	 * 问责记录浏览页面（客户经理）
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute AccountabilityRecordFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		filter.setCustomerManagerId(userId);
		filter.setAuditStatus(AuditStatusEnum.notaudit.name());
		QueryResult<AccountabilityRecord> result = accountabilityService.findAccountabilityRecordByFilter(filter);		
		JRadPagedQueryResult<AccountabilityRecord> pagedResult = new JRadPagedQueryResult<AccountabilityRecord>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/accountability/accountability_record_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	/**
	 * 问责记录复议(客户经理)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "managerupdate.page")
	@JRadOperation(JRadOperation.RECONSIDER)
	public AbstractModelAndView managerupdate( HttpServletRequest request) {
		String accountabilityRecordId = request.getParameter("id");
		AccountabilityRecord accountabilityRecord =
				accountabilityService.findAccountabilityRecordByID(accountabilityRecordId);
		String  customerId = accountabilityRecord.getCustomerId();
		String  customerManagerId = accountabilityRecord.getCustomerManagerId();
		CustomerInfor customer = customerInforservice.findCustomerInforById(customerId);
		SystemUser customerManager = systemUserService.findCustomerManagerInforById(customerManagerId);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/accountability/manager_accountabilityRecord_update",request);
		mv.addObject("accountabilityRecord",accountabilityRecord);
		mv.addObject("customer",customer);
		mv.addObject("customerManager",customerManager);
		return mv;
	}
	
	
	
	/**
	 * 执行问责记录复议(经理)
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "managerupdate.json")
	@JRadOperation(JRadOperation.RECONSIDER)
	public JRadReturnMap managerreconsideration( HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		String id = request.getParameter("id");
		String reconsideration = request.getParameter("reconsideration");
		String accountManagerComment = request.getParameter("accountManagerComment");
		if (returnMap.isSuccess()) {
			try {
				AccountabilityRecord accountabilityRecord =new AccountabilityRecord();
				accountabilityRecord.setReconsideration(reconsideration);
                if(Integer.parseInt(reconsideration) == 0){
                	accountabilityRecord.setAuditStatus(AuditStatusEnum.endaudit.name());
				}
				accountabilityRecord.setAccountManagerComment(accountManagerComment);
				accountabilityRecord.setId(id);
				accountabilityService.updateAccountabilityRecord(accountabilityRecord,userId);
				returnMap.put(RECORD_ID, accountabilityRecord.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	/**
	 * 根据id显示问责记录详情
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cfccdisplay.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView cfccdisplay( HttpServletRequest request) {
		String accountabilityRecordId = RequestHelper.getStringValue(request, ID);
		AccountabilityRecord accountabilityRecord =
				accountabilityService.findAccountabilityRecordByID(accountabilityRecordId);
		String  customerId = accountabilityRecord.getCustomerId();
		String  customerManagerId = accountabilityRecord.getCustomerManagerId();
		CustomerInfor customer = customerInforservice.findCustomerInforById(customerId);
		SystemUser customerManager = systemUserService.findCustomerManagerInforById(customerManagerId);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/accountability/cfcc_accountabilityRecord_display",request);
		mv.addObject("accountabilityRecord",accountabilityRecord);
		mv.addObject("customer",customer);
		mv.addObject("customerManager",customerManager);
		return mv;
	}

}
