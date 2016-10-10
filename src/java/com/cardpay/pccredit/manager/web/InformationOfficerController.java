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
import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.manager.constant.InformationOfficerConstant;
import com.cardpay.pccredit.manager.filter.InformationOfficerFilter;
import com.cardpay.pccredit.manager.filter.ManagerInformationClientFilter;
import com.cardpay.pccredit.manager.model.InformationOfficer;
import com.cardpay.pccredit.manager.model.InformationOfficerEvaluate;
import com.cardpay.pccredit.manager.model.ManagerInformationClient;
import com.cardpay.pccredit.manager.service.InformationOfficerService;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
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
 * 信息员
 * @author shaoming
 *
 * 2014年10月31日   下午5:04:35
 */
@Controller
@RequestMapping("/manager/information/*")
@JRadModule("manager.information")
public class InformationOfficerController extends BaseController{
	
	@Autowired
	private InformationOfficerService informationOfficerService;
	
	@Autowired
	private CustomerInforService customerInforService;
	/**
	 * 浏览信息员页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute InformationOfficerFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String customerManagerId = user.getId();
		filter.setCustomerManagerId(customerManagerId);
		QueryResult<InformationOfficer> result = informationOfficerService.findInformationOfficersByFilter(filter);
		JRadPagedQueryResult<InformationOfficer> pagedResult = new JRadPagedQueryResult<InformationOfficer>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/manager/information_officer/information_officer_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 显示信息员详细信息页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.RELEVANCECUSTOMER)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/information_officer/information_officer_display", request);
		String informationOfficerId = RequestHelper.getStringValue(request, ID);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		ManagerInformationClientFilter filter = new ManagerInformationClientFilter();
		filter.setCustomerManagerId(loginId);
		filter.setMessengerId(informationOfficerId);
		List<CustomerInforWeb> result=informationOfficerService.findCustomerInforWebByFilter(filter);
		mv.addObject(PAGED_RESULT, result);
		if (StringUtils.isNotEmpty(informationOfficerId)) {
			InformationOfficer informationOfficer = informationOfficerService.findInformationOfficerById(informationOfficerId);
			mv.addObject(InformationOfficerConstant.INFORMATIONOFFICER, informationOfficer);
		}
		return mv;
	}
	
	/**
	 * 信息员信息新增页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/information_officer/information_officer_create", request);
		return mv;
	}
	/**
	 * 信息员修改页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeInfor.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changeInfor(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/manager/information_officer/information_officer_changeInfor", request);
		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			InformationOfficer officer = informationOfficerService.findInformationOfficerById(id);
			mv.addObject("officer", officer);
		}
		return mv;
	}
	/**
	 * 信息员修改
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateInfor.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updateInfor(@ModelAttribute InformationOfficerForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try{
			InformationOfficer informationOfficer = form.createModel(InformationOfficer.class);
			informationOfficerService.updateInformationOfficer(informationOfficer);
			returnMap.put(RECORD_ID, informationOfficer.getId());
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}catch(Exception e){
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	/**
	 * 执行信息员添加
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute InformationOfficerForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				InformationOfficer informationOfficer = form.createModel(InformationOfficer.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				ManagerInformationClient managerInformationClient = new ManagerInformationClient();
				managerInformationClient.setCustomerManagerId(userId);
				managerInformationClient.setCreatedBy(userId);
				informationOfficer.setCreatedBy(userId);
				String id = informationOfficerService.insertInformationOfficer(informationOfficer,managerInformationClient);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
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
		JRadModelAndView mv = new JRadModelAndView("/manager/information_officer/information_officer_change", request);
		String messengerId = RequestHelper.getStringValue(request, ID);
		
		User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		if (StringUtils.isNotEmpty(messengerId)) {
			List<Dict> selected = informationOfficerService.findCustomerInforsById(messengerId, loginId);
			List<Dict> unselected= informationOfficerService.findCustomerInfors(messengerId, loginId);
			mv.addObject("messengerId",messengerId);
			mv.addObject("selected",selected);
			mv.addObject("unselected",unselected);
		}
		return mv;
	}
	
	/**
	 * 更新信息员下客户
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute ManagerInformationClientForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				form.setCustomerManagerId(user.getId());
				List<ManagerInformationClient> managerInformationClients = form.StringToList();
				boolean flag = informationOfficerService.updateManagerInformationClient(managerInformationClients);
				if(flag){
					returnMap.put(JRadConstants.SUCCESS, true);
					returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
				}else{
					returnMap.put(JRadConstants.SUCCESS, false);
					returnMap.put(JRadConstants.MESSAGE, CustomerInforConstant.UPDATEERROR);
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
	 * 信息员评价页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "evaluate.page")
	@JRadOperation(JRadOperation.ASSESS)
	public AbstractModelAndView evaluate(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/information_officer/information_officer_evaluate", request);
		String messengerId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(messengerId)) {
			InformationOfficerEvaluateForm officer= informationOfficerService.findInformationOfficerEvaluateFormById(messengerId);
			mv.addObject("officer",officer);
		}
		return mv;
	}
	/**
	 * 更新信息员评价
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "evaluate.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap evaluate(@ModelAttribute InformationOfficerEvaluateForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				InformationOfficerEvaluate informationOfficerEvaluate = form.createModel(InformationOfficerEvaluate.class);
				informationOfficerEvaluate.setEvaluationPeople(userId);
				informationOfficerEvaluate.setCreatedBy(userId);
				informationOfficerEvaluate.setModifiedBy(userId);
				
				boolean flag = informationOfficerService.updateInformationOfficerEvaluate(informationOfficerEvaluate);
				if(flag){
					returnMap.put(RECORD_ID, informationOfficerEvaluate.getMessengerId());
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
