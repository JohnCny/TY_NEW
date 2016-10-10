/*
 * Copyright 2014 Wicresoft, Inc. All rights reserved.
 */

package com.wicresoft.jrad.modules.privilege.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.constants.ModulesConstants;
import com.wicresoft.jrad.modules.dao.modulesComdao;
import com.wicresoft.jrad.modules.privilege.business.DepartmentManager;
import com.wicresoft.jrad.modules.privilege.business.DeptUserManager;
import com.wicresoft.jrad.modules.privilege.business.RoleManager;
import com.wicresoft.jrad.modules.privilege.business.UserManager;
import com.wicresoft.jrad.modules.privilege.filter.DepartmentFilter;
import com.wicresoft.jrad.modules.privilege.filter.UserFilter;
import com.wicresoft.jrad.modules.privilege.model.DeptUser;
import com.wicresoft.jrad.modules.privilege.model.Role;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.encrypt.EncryptHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * Description of PersonController
 * 
 * @author Administrator
 * @created on Jul 8, 2014
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/system/user/*")
@JRadModule("system.user")
public class UserController extends BaseController {

	@Autowired
	private UserManager userManager;

	@Autowired
	private DeptUserManager deptUserManager;

	@Autowired
	private RoleManager roleManager;

	@Autowired
	private DepartmentManager departmentManager;
	
	@Autowired
	private modulesComdao modulesComdao;

	final public static String REQUEST_DEPT_ID = "deptId";
	final public static String SESSION_DEPT_ID = "$$deptId$$";

	/**
	 * 跳转浏览组织架构和部门
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView query(@ModelAttribute DepartmentFilter filter, HttpServletRequest request) {
		String departmentJson = departmentManager.getAllOrganizationAndDepartment(filter);
		JRadModelAndView mv = new JRadModelAndView("/modules/privilege/user/department_user", request);
		mv.addObject("children", departmentJson);
		return mv;
	}

	/**
	 * 浏览用户
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "displayUserList.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView displayUserList(@ModelAttribute UserFilter userFilter, HttpServletRequest request) {
		userFilter.setRequest(request);
		String deptId = request.getParameter("deptId");
		if (deptId==null) {
			JRadModelAndView mv = new JRadModelAndView("/modules/privilege/user/user_block", request);
			return mv;
		}
		userFilter.setManagerId(deptId);
		QueryResult<User> result = userManager.findUserByFilter(userFilter);
		JRadPagedQueryResult<User> pagedResult = new JRadPagedQueryResult<User>(userFilter, result);
		JRadModelAndView mv = new JRadModelAndView("/modules/privilege/user/user_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("user", userFilter);
		mv.addObject("deptId", deptId);
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
	public AbstractModelAndView create(@ModelAttribute UserFilter userFilter, HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/modules/privilege/user/user_create", request);
		List<Role> list = roleManager.getAllRoles();
		mv.addObject("person", userFilter);
		mv.addObject("roles", list);
		mv.addObject("deptId", request.getParameter("deptId"));
		return mv;
	}

	/**
	 * 执行添加
	 * 
	 * @param sample
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute User user, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), user);
		if (returnMap.isSuccess()) {
			try {
				List<User> tempList = modulesComdao.checkLoginDupInsert(user.getLogin());
				if(tempList != null && tempList.size() > 0){
					returnMap.setSuccess(false);
					returnMap.addGlobalError(ModulesConstants.USER_DUP_LOGIN);
					return returnMap;
				}
				
				tempList = modulesComdao.checkExternalIdDupInsert(user.getExternalId());
				if(tempList != null && tempList.size() > 0){
					returnMap.setSuccess(false);
					returnMap.addGlobalError(ModulesConstants.USER_DUP_EXTENDID);
					return returnMap;
				}
				DeptUser du= new DeptUser();
				String deptId = request.getParameter("deptId");
				String userId = userManager.insertUser(user, request.getParameter("roleIds"));
				du.setDeptId(deptId);
				du.setUserId(userId);
				int j = userManager.insertDeptUser(du);
				returnMap.put("deptId", deptId);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
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
		JRadModelAndView mv = new JRadModelAndView("/modules/privilege/user/user_change", request);

		String userId = RequestHelper.getStringValue(request, ID);

		if (StringUtils.isNotEmpty(userId)) {
			List<Role> roleChecked = roleManager.getRoleByUserId(userId);
			List<Role> list = roleManager.getAllRoles();
			User user = userManager.getUserById(userId);
			mv.addObject("person", user);
			mv.addObject("roleChecked", roleChecked);
			mv.addObject("roles", list);
			mv.addObject("deptId", request.getParameter("deptId"));
		}
		return mv;
	}

	/**
	 * 执行修改
	 * 
	 * @param sample
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute User user, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), user);
		if (returnMap.isSuccess()) {
			try {
				String userId = RequestHelper.getStringValue(request, ID);
				//判断是否员工编号重复
				List<User> tempList = modulesComdao.checkExternalIdDupUpdate(userId, user.getExternalId());
				if(tempList != null && tempList.size() > 0){
					returnMap.setSuccess(false);
					returnMap.addGlobalError(ModulesConstants.USER_DUP_EXTENDID);
					return returnMap;
				}
				
				user.setId(userId);
				userManager.updateUser(user, request.getParameter("roleIds"));
				String deptId = request.getParameter("deptId");
				returnMap.put("deptId", deptId);
				returnMap.put(RECORD_ID, user.getId());
				returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/**
	 * 重置密码
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "config.json")
	@JRadOperation(JRadOperation.CONFIG)
	public JRadReturnMap config(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String userId = RequestHelper.getStringValue(request, ID);
			userManager.setUserPwd(userId, null);
			returnMap.setSuccess(true);
			returnMap.addGlobalMessage(ModulesConstants.USER_PWD_RESET_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
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
			String userId = RequestHelper.getStringValue(request, ID);
			userManager.deleteUser(userId);
			returnMap.put("deptId", request.getParameter("deptId"));
			returnMap.addGlobalMessage(JRadConstants.DELETE_SUCCESS);
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
		JRadModelAndView mv = new JRadModelAndView("/modules/privilege/user/user_display", request);
		String userId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(userId)) {
			User user = userManager.getUserById(userId);
			mv.addObject("person", user);
		}

		return mv;
	}
	
	/**
	 * 系统登录用户自己修改自己密码时提的提交
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyOnwerPasswordSubmit.json", method = { RequestMethod.POST })
	//@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap modifyOnwerPasswordSubmit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			userManager.setUserPwd(user.getId(), request.getParameter("password2"));
			returnMap.addGlobalMessage(CREATE_SUCCESS);
			returnMap.setSuccess(true);
			return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.setSuccess(false);
			return returnMap;
		}
	}
	
	/**
	 * 系统登录用户自己修改自己密码时提的先检查自己原来的密码
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "checkOnwerOldPassword.json", method = { RequestMethod.POST })
	//@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap checkOnwerOldPassword(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
			String oldPassword = EncryptHelper.md5(StringUtils.trim(request.getParameter("oldPassword")));
			if(oldPassword.equals(user.getPassword()))
			{
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.setSuccess(true);
			}else
			{
				returnMap.setSuccess(false);
			}
			return returnMap;
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.setSuccess(false);
			return returnMap;
		}
	}

}
