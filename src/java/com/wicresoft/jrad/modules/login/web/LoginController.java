/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.wicresoft.jrad.modules.login.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wicresoft.jrad.base.auth.AuthResult;
import com.wicresoft.jrad.base.auth.AuthResult.AuthResultType;
import com.wicresoft.jrad.base.auth.IAuthMgr;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.enviroment.GlobalSetting;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.message.Errors;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LDAPConfig;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.log.business.LoginLogManager;

/**
 * Description of LoginController
 * 
 * @author Vincent
 * @created on Dec 28, 2013
 * 
 * @version $Id: LoginController.java 2181 2014-03-28 05:31:19Z tonyxc $
 */
@Controller
public class LoginController implements JRadConstants {

	@Autowired
	private LoginManager loginManager;

	@Autowired
	private LoginLogManager loginLogManager;

	@Autowired
	private IAuthMgr authMgr;

	@Autowired
	private GlobalSetting globalSetting;

	@Autowired
	private I18nHelper i18nHelper;

	@RequestMapping(value = { "/login.html" })
	public JRadModelAndView login(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("login", request);
		LDAPConfig ldapConfig = LDAPConfig.getInstance();
		mv.addObject("redirectToCas",
				ldapConfig.getCasServerLoginUrl() + "?service=" + ldapConfig.getLoginSuccessToUrl());
		return mv;
	}

	@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adminLogin(HttpServletRequest request) {
		Map<String, Object> map = innerAdminLogin(request);

		return map;
	}

	public JRadReturnMap innerAdminLogin(HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ipAddress = request.getRemoteAddr();

		try {
			AuthResult authResult = authMgr.authUserByLogin(username, password);
			AuthResultType authResultType = authResult.getResultType();

			Errors errors = new Errors();
			String errorCode = null;
			if (AuthResultType.AUTH_ACCOUNT_NOT_EXIST.equals(authResultType)) {
				errorCode = "system.auth.accountNotExist";
			}
			else if (AuthResultType.AUTH_ACCOUNT_PASSWORD_ERROR.equals(authResultType)) {
				errorCode = "system.auth.passwordError";
			}
			else if (AuthResultType.AUTH_ACCOUNT_NOT_ACTIVE.equals(authResultType)) {
				errorCode = "system.auth.accountNotActive";
			}
			else if (AuthResultType.AUTH_ACCOUNT_NOT_LOCAL.equals(authResultType)) {
//				errorCode = "system.auth.accountNotLocal";
			}

			String signInMsg = null;
			if (errorCode != null) {
				signInMsg = i18nHelper.getMessage(errorCode);
				errors.addGlobalError(errorCode);
				returnMap.setSuccess(false);
				returnMap.setErrors(errors);
			}
			else {
				IUser user = authResult.getUser();
				loginManager.login(user, request);
				signInMsg = i18nHelper.getMessage("system.auth.success");
			}

			loginLogManager.addSignInLog(username, LoginManager.LOCAL, ipAddress, signInMsg);
		}
		catch (Exception ex) {
			return WebRequestHelper.processException(ex);
		}

		return returnMap;
	}

	@RequestMapping(value = { "/logout.page" })
	public String logout(HttpServletRequest request) {

		IUser user = loginManager.getLoggedInUser(request);
		String login = user.getLogin();
		String type = user.getUserType() == IUser.USER_TYPE_LOCAL ? LoginManager.LOCAL : LoginManager.LDAP;
		String ipAddress = request.getRemoteAddr();
		String signOutMsg = i18nHelper.getMessage("system.auth.success");

		loginManager.logout(request);
		loginLogManager.addSignOutLog(login, type, ipAddress, signOutMsg);

		return "redirect:login.html";

	}
}
