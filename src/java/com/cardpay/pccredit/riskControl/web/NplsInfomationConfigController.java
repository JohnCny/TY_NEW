package com.cardpay.pccredit.riskControl.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.riskControl.model.NplsInfomationConfiguration;
import com.cardpay.pccredit.riskControl.service.NplsInfomationConfigService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
@Controller
@RequestMapping("/riskcontrol/nplsinfomationconfig/*")
@JRadModule("riskcontrol.nplsinfomationconfig")
public class NplsInfomationConfigController  extends BaseController{
	
	@Autowired
	private NplsInfomationConfigService nplsInfomationConfigService;
	
	/**
	 * 不良资产规则基本参数设置
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) {
		NplsInfomationConfiguration nplsInfomationConfiguration = nplsInfomationConfigService.getNplsInfomationConfig();
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/nplsinfomation/nplsinfomation_config", request);
		mv.addObject("nplsInfomationConfiguration",nplsInfomationConfiguration);
		return mv;
	}
	/**
	 * 不良资产规则基本参数保存
	 * 
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "save.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute NplsInfomationConfiguration nplsInfomationConfiguration, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), nplsInfomationConfiguration);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		if (returnMap.isSuccess()) {
			try {
				nplsInfomationConfigService.updateNplsInfomationConfig(nplsInfomationConfiguration, userId);
				 returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

}
