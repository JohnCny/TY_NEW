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
@RequestMapping("/accountabilitycontrol/accountability/*")
@JRadModule("riskcontrol.accountabilityrecordcfcc")
public class AccountabilityController extends BaseController {
	
	@Autowired
	AccountabilityService accountabilityService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private SystemUserService systemUserService;
	
	@Autowired
	private CardInfomationService cardInfomationService;
	

	
	
	/**
	 * 问责记录浏览页面（卡中心）
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "cfccbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView cfccbrowse(@ModelAttribute AccountabilityRecordFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		QueryResult<AccountabilityRecord> result = accountabilityService.findAccountabilityRecordByFilter(filter);		
		JRadPagedQueryResult<AccountabilityRecord> pagedResult = new JRadPagedQueryResult<AccountabilityRecord>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/accountability/cfcc_accountabilityRecord_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 根据id显示问责记录详情(卡中心)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cfccdisplay.page")
	@JRadOperation(JRadOperation.BROWSE)
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
	
	/**
	 * 修改问责记录(卡中心)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cfccupdate.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView cfccupdate( HttpServletRequest request) {
		String accountabilityRecordId = request.getParameter("id");
		AccountabilityRecord accountabilityRecord =
				accountabilityService.findAccountabilityRecordByID(accountabilityRecordId);
		String  customerId = accountabilityRecord.getCustomerId();
		String  customerManagerId = accountabilityRecord.getCustomerManagerId();
		CustomerInfor customer = customerInforservice.findCustomerInforById(customerId);
		SystemUser customerManager = systemUserService.findCustomerManagerInforById(customerManagerId);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/accountability/cfcc_accountabilityRecord_update",request);
		mv.addObject("accountabilityRecord",accountabilityRecord);
		mv.addObject("customer",customer);
		mv.addObject("customerManager",customerManager);
		return mv;
	}
	/**
	 * 确认问责记录(卡中心)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cfccconfirm.page")
	@JRadOperation(JRadOperation.CONFIRMACCOUNTABILITY)
	public AbstractModelAndView cfccconfirm( HttpServletRequest request) {
		String accountabilityRecordId = request.getParameter("id");
		AccountabilityRecord accountabilityRecord =
				accountabilityService.findAccountabilityRecordByID(accountabilityRecordId);
		String  customerId = accountabilityRecord.getCustomerId();
		String  customerManagerId = accountabilityRecord.getCustomerManagerId();
		CustomerInfor customer = customerInforservice.findCustomerInforById(customerId);
		SystemUser customerManager = systemUserService.findCustomerManagerInforById(customerManagerId);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/accountability/cfcc_accountabilityRecord_update",request);
		mv.addObject("accountabilityRecord",accountabilityRecord);
		mv.addObject("customer",customer);
		mv.addObject("customerManager",customerManager);
		return mv;
	}
	/**
	 * 确认复议问责记录(卡中心)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cfccconfirmfy.page")
	@JRadOperation(JRadOperation.CONFIRMRECONSIDER)
	public AbstractModelAndView cfccconfirmfy( HttpServletRequest request) {
		String accountabilityRecordId = request.getParameter("id");
		AccountabilityRecord accountabilityRecord =
				accountabilityService.findAccountabilityRecordByID(accountabilityRecordId);
		String  customerId = accountabilityRecord.getCustomerId();
		String  customerManagerId = accountabilityRecord.getCustomerManagerId();
		CustomerInfor customer = customerInforservice.findCustomerInforById(customerId);
		SystemUser customerManager = systemUserService.findCustomerManagerInforById(customerManagerId);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/accountability/cfcc_accountabilityRecord_fyupdate",request);
		mv.addObject("accountabilityRecord",accountabilityRecord);
		mv.addObject("customer",customer);
		mv.addObject("customerManager",customerManager);
		return mv;
	}
	
	
	/**
	 * 执行问责记录修改(卡中心)
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CONFIRMRECONSIDER)
	public JRadReturnMap update( HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		String money = request.getParameter("money");
		String id = request.getParameter("id");
		String createReason = request.getParameter("createReason");
		String auditStatus = request.getParameter("auditStatus");
		String reconsiderationConclusion = request.getParameter("reconsiderationConclusion");
		String accountabilityConclusion = request.getParameter("accountabilityConclusion");
		if (returnMap.isSuccess()) {
			try {
				AccountabilityRecord accountabilityRecord =new AccountabilityRecord();
				accountabilityRecord.setMoney(money);
				accountabilityRecord.setCreateReason(createReason);
				accountabilityRecord.setAccountabilityConclusion(accountabilityConclusion);
				accountabilityRecord.setId(id);
				accountabilityRecord.setReconsiderationConclusion(reconsiderationConclusion);
				if(auditStatus != null){
					accountabilityRecord.setAuditStatus(auditStatus);
				}
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
	 * 执行问责记录取消
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cancel.json")
	@JRadOperation(JRadOperation.DELETE)
	public JRadReturnMap cancel( HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		String id = request.getParameter("id");
		if (returnMap.isSuccess()) {
			try {
				accountabilityService.deleteAccountabilityRecord(id);
				returnMap.addGlobalMessage(DELETE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	/**
	 * 问责记录增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {

		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/accountability/cfcc_accountabilityRecord_insert", request);

		return mv;
	}

	/**
	 * 执行问责记录添加
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute AccountabilityForm accountabilityForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), accountabilityForm);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		if (returnMap.isSuccess()) {
			try {
				AccountabilityRecord accountabilityRecord = accountabilityForm.createModel(AccountabilityRecord.class);
				accountabilityRecord.setCreatedBy(userId);
				accountabilityRecord.setModifiedBy(userId);
				accountabilityRecord.setAuditStatus(AuditStatusEnum.notaudit.name());
				String customerId = accountabilityRecord.getCustomerId();
				String productId = accountabilityRecord.getProductId();
				String customerManagerId = accountabilityRecord.getCustomerManagerId();
				AccountabilityRecord findaccountabilityRecord = accountabilityService.findAccountabilityRecordByIds(customerId, productId, customerManagerId);
				if(findaccountabilityRecord == null ){
				String id = accountabilityService.insertAccountabilityRecord(accountabilityRecord);
				returnMap.put(RECORD_ID, id);
				}else{
					
					returnMap.setSuccess(false);
				}
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/**
	 * 确认问责
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "confirm.json")
	@JRadOperation(JRadOperation.CONFIRMACCOUNTABILITY)
	public JRadReturnMap confirm(@ModelAttribute AccountabilityForm accountabilityForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), accountabilityForm);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		if (returnMap.isSuccess()) {
			try {
				AccountabilityRecord accountabilityRecord = accountabilityForm.createModel(AccountabilityRecord.class);
				accountabilityRecord.setCreatedBy(userId);
				accountabilityRecord.setModifiedBy(userId);
				accountabilityRecord.setAuditStatus(AuditStatusEnum.cfccaudit.name());
				String id = accountabilityService.insertAccountabilityRecord(accountabilityRecord);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	
	/**
	 * 输入客户经理的名字模糊匹配
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectByLike.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView selectByLike(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			systemUserService.selectLikeByDisplayName(response,
					StringUtils.trim(request.getParameter("q")));
		} catch (Exception e) {
			// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			e.printStackTrace();
		}
		// TODO 该方法永远return null??前端有ajax调用应将ajax调用方法封装，另外，返回null会带来风险
		return null;
	}
	/**
	 * 很据客户经理Id查询下面的客户
	 * 
	 *
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectDisplayNameById.json")
	@JRadOperation(JRadOperation.DISPLAY)
	public JRadReturnMap selectDisplayNameById(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String customerManagerId = request.getParameter("customerManagerId");
		if (returnMap.isSuccess()) {
			try {
				List<CustomerInfor> result = customerInforservice.findCustomerByManagerId(customerManagerId);	
				returnMap.put("results", result);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	/**
	 * 很据客户Id查询产品
	 * 
	 *
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectProductDisplayNameById.json")
	@JRadOperation(JRadOperation.DISPLAY)
	public JRadReturnMap selectProductDisplayNameById(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String customerId = request.getParameter("customerId");
		if (returnMap.isSuccess()) {
			try {
				List<CardInfomationFrom> results = cardInfomationService.findCardsByCustomerId(customerId);	
				returnMap.put("results", results);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	

}
