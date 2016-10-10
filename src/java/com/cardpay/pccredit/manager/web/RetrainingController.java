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

import com.cardpay.pccredit.common.PccOrganizationService;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.cardpay.pccredit.manager.filter.AccountManagerRetrainingFilter;
import com.cardpay.pccredit.manager.filter.RetrainingFilter;
import com.cardpay.pccredit.manager.model.AccountManagerRetraining;
import com.cardpay.pccredit.manager.model.Retraining;
import com.cardpay.pccredit.manager.service.AccountManagerRetrainingService;
import com.cardpay.pccredit.manager.service.RetrainingService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * @author chenzhifang
 *
 * 2014-11-12上午10:44:46
 */
@Controller
@RequestMapping("/manager/retraining/*")
@JRadModule("manager.retraining")
public class RetrainingController extends BaseController {
	@Autowired
	private AccountManagerRetrainingService accountManagerRetrainingService;
	
	@Autowired
	private PccOrganizationService pccOrganizationService;
	
	@Autowired
	private RetrainingService retrainingService;

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
	public AbstractModelAndView browse(@ModelAttribute RetrainingFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<Retraining> result = retrainingService.findRetrainingByFilter(filter);
		JRadPagedQueryResult<Retraining> pagedResult = new JRadPagedQueryResult<Retraining>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/manager/retraining/retraining_browse", request);
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

		JRadModelAndView mv = new JRadModelAndView("/manager/retraining/retraining_create", request);

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
		JRadModelAndView mv = new JRadModelAndView("/manager/retraining/retraining_change", request);

		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			Retraining retraining = retrainingService.findRetrainingById(id);
			mv.addObject("retraining", retraining);
			mv.addObject(RECORD_ID, id);
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
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/retraining/retraining_display", request);

		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			Retraining retraining = retrainingService.findRetrainingById(id);
			mv.addObject("retraining", retraining);
		}

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
	public JRadReturnMap insert(@ModelAttribute RetrainingForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				Retraining retraining = form.createModel(Retraining.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				retraining.setCreatedBy(user.getId());
				retraining.setModifiedBy(user.getId());
				String id = retrainingService.insertRetraining(retraining);
				returnMap.put(RECORD_ID, id);
				//returnMap.addGlobalMessage(CREATE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
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
	public JRadReturnMap update(@ModelAttribute RetrainingForm form, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				Retraining retraining = form.createModel(Retraining.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				retraining.setModifiedBy(user.getId());
				retrainingService.updateRetraining(retraining);
				returnMap.put(RECORD_ID, retraining.getId());
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
			String retrainId = RequestHelper.getStringValue(request, ID);
			retrainingService.deleteRetraining(retrainId);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	/**
	 * 浏览页面(培训客户经理)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browseRetrainingMananger.page", method = { RequestMethod.GET })
	public AbstractModelAndView browseRetrainingMananger(@ModelAttribute AccountManagerRetrainingFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<AccountManagerRetraining> result = accountManagerRetrainingService.findAccountManagerRetrainingByFilter(filter);
		JRadPagedQueryResult<AccountManagerRetraining> pagedResult = new JRadPagedQueryResult<AccountManagerRetraining>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/manager/retraining/accountmanagerretraining_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/**
	 * 浏览选择客户经理页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "choosemanager.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView choosemanager(@ModelAttribute AccountManagerRetrainingFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		filter.setLimit(Integer.MAX_VALUE);
		List<User> userList = accountManagerRetrainingService.findUserListByRetrainId(filter.getRetrainId());

		JRadModelAndView mv = new JRadModelAndView("/manager/retraining/retraining_choose_manage", request);
		
		List<FlatTreeNode> orgList = pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
		mv.addObject("orgList", orgList);
		mv.addObject("managerList", new ArrayList<User>());
		mv.addObject("userList", userList);
		mv.addObject("retrainId", filter.getRetrainId());
		return mv;
	}
	
	/**
	 * 增加页面(培训客户经理)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "createRetrainingMananger.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createRetrainingMananger(HttpServletRequest request) {

		JRadModelAndView mv = new JRadModelAndView("/manager/retraining/accountmanagerretraining_create", request);

		return mv;
	}
	
	/**
	 * 修改页面(培训客户经理)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changeRetrainingMananger.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changeRetrainingMananger(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/retraining/accountmanagerretraining_change", request);

		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			AccountManagerRetraining accountManagerRetraining = accountManagerRetrainingService.findAccountManagerRetrainingById(id);
			mv.addObject("accountManagerRetraining", accountManagerRetraining);
			mv.addObject(RECORD_ID, id);
		}

		return mv;
	}
	
	/**
	 * 显示页面(培训客户经理)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "displayRetrainingMananger.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView displayRetrainingMananger(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/manager/accountmanagerretraining/accountmanagerretraining_display", request);

		String id = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(id)) {
			AccountManagerRetraining accountManagerRetraining = accountManagerRetrainingService.findAccountManagerRetrainingById(id);
			mv.addObject("accountManagerRetraining", accountManagerRetraining);
		}

		return mv;
	}
	
	/**
	 * 执行添加(培训客户经理)
	 * 
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertRetrainingMananger.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insertRetrainingMananger(@ModelAttribute AccountManagerRetrainingForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				AccountManagerRetraining accountManagerRetraining = form.createModel(AccountManagerRetraining.class);
				String id = accountManagerRetrainingService.insertAccountManagerRetraining(accountManagerRetraining);
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
	 * 执行修改(培训客户经理)
	 * 
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateRetrainingMananger.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updateRetrainingMananger(@ModelAttribute AccountManagerRetrainingForm form, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation("manager.accountmanagerretraining", form);
		if (returnMap.isSuccess()) {
			try {
				AccountManagerRetraining accountManagerRetraining = form.createModel(AccountManagerRetraining.class);
				accountManagerRetrainingService.updateAccountManagerRetraining(accountManagerRetraining);
				returnMap.put(RECORD_ID, accountManagerRetraining.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/**
	 * 执行删除(培训客户经理)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteRetrainingMananger.json")
	@JRadOperation(JRadOperation.DELETE)
	public JRadReturnMap deleteRetrainingMananger(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		try {
			String managerRetrainId = RequestHelper.getStringValue(request, ID);
			accountManagerRetrainingService.deleteAccountManagerRetraining(managerRetrainId);
			returnMap.addGlobalMessage(DELETE_SUCCESS);
		}
		catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	/**
	 * 通过机构id查询客户经理
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findManagersByorgId.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap findManagersByorgId(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String orgId = "";
		if (returnMap.isSuccess()) {
			try {
				if(!"000000".equals(RequestHelper.getStringValue(request, "orgId"))){
					 orgId = RequestHelper.getStringValue(request, "orgId");
				}
				
				List<User> users = accountManagerRetrainingService.findUserListByOrgId(orgId);
				returnMap.put("datas", users);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	
	/**
	 * 保存客户经理
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveManagers.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap saveManagers(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		User u = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId= u.getId();
		if (returnMap.isSuccess()) {
			try {
				String deleteManagerIds = RequestHelper.getStringValue(request, "deleteManagerIds");
				String newAddManagerIds = RequestHelper.getStringValue(request, "newAddManagerIds");
				String retrainId = RequestHelper.getStringValue(request, "retrainId");
				accountManagerRetrainingService.saveManagers(retrainId, deleteManagerIds, newAddManagerIds,userId);
				//returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
}
