/*
 * Copyright 2013 Wicresoft, Inc. All rights reserved.
 */

package com.wicresoft.jrad.base.web;

import javax.servlet.http.HttpServletRequest;

import com.cardpay.pccredit.common.Dictionary;
import com.cardpay.pccredit.common.FormatTool;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.enviroment.GlobalSetting;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * Description of JRadModelAndView
 * 
 * @author Vincent
 * @created on Dec 25, 2013
 * 
 * @version $Id: JRadModelAndView.java 802 2014-06-16 09:46:44Z vincent $
 */
public class JRadModelAndView extends AbstractModelAndView {

	public static String UI_CASE = "uicase";

	public static String UI_CASE_PATH = "uicasePath";

	public static String USER_LOGGEDIN = "user";
	
	public static String FORMAT_TOOL = "format";

	public JRadModelAndView(String viewName, HttpServletRequest request) {
		super(viewName, request);

		// 增加一些扩展对象
		String uiCaseName = Beans.get(GlobalSetting.class).getUiCaseName();
		this.addObject(UI_CASE, uiCaseName);
		
		this.addObject(FORMAT_TOOL, new FormatTool());

		this.addObject(UI_CASE_PATH, request.getContextPath() + "/" + uiCaseName);

		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		this.addObject(USER_LOGGEDIN, user);
		request.setAttribute("nationalityList", Dictionary.nationalityList);
		request.setAttribute("cardTypeList", Dictionary.cardTypeList);
		request.setAttribute("maritalStatusList", Dictionary.maritalStatusList);
		request.setAttribute("residentialPropertieList", Dictionary.residentialPropertieList);
		request.setAttribute("orgList", Dictionary.orgList);
		request.setAttribute("unitPropertisList",Dictionary.unitPropertisList);
		request.setAttribute("collectionMethodList",Dictionary.collectionMethodList);
		request.setAttribute("industryTypeList",Dictionary.industryTypeList);
		request.setAttribute("positioList",Dictionary.positioList);
		request.setAttribute("titleList",Dictionary.titleList);
		request.setAttribute("oaccountMybankList",Dictionary.oaccountMybankList);
		request.setAttribute("creditCardList",Dictionary.creditCardList);
		request.setAttribute("payMybankList",Dictionary.payMybankList);
		request.setAttribute("degreeeducationList",Dictionary.degreeeducationList);
	}
}
