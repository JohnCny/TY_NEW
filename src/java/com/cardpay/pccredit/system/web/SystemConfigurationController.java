package com.cardpay.pccredit.system.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.system.filter.SystemConfigurationFilter;
import com.cardpay.pccredit.system.model.SystemConfiguration;
import com.cardpay.pccredit.system.service.SystemConfigurationService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;


/**
 * SystemConfigurationController类的描述
 *
 * @author 王海东
 * @created on 2014-11-26
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/system/parameter/*")
@JRadModule("system.parameter")
public class SystemConfigurationController extends BaseController{
	
	@Autowired
	private SystemConfigurationService systemConfigurationService;
	
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute SystemConfigurationFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<SystemConfiguration> result = systemConfigurationService.findSystemConfigurationsByFilter(filter);
		JRadPagedQueryResult<SystemConfiguration> pagedResult = new JRadPagedQueryResult<SystemConfiguration>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/system/systemconfiguration/configuration_browse", request);
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
		JRadModelAndView mv = new JRadModelAndView("/system/systemconfiguration/configuration_change", request);

		String paraId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(paraId)) {
			SystemConfiguration systemConfiguration = systemConfigurationService.findSystemConfigurationById(paraId);
			mv.addObject("systemConfiguration", systemConfiguration);
		}
		return mv;
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
	public JRadReturnMap update(@ModelAttribute SystemConfigurationForm systemConfigurationForm, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), systemConfigurationForm);
		if (returnMap.isSuccess()) {
			try {
				SystemConfiguration systemConfiguration = systemConfigurationForm.createModel(SystemConfiguration.class);
				systemConfigurationService.updateSystemConfiguration(systemConfiguration);
				returnMap.put(RECORD_ID, systemConfiguration.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
}
