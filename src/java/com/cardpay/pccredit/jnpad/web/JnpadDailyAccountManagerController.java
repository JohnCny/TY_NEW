package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnpadDailyAccountManagerService;
import com.cardpay.pccredit.manager.filter.DailyAccountManagerFilter;
import com.cardpay.pccredit.manager.model.DailyAccountManager;
import com.cardpay.pccredit.manager.service.DailyAccountService;
import com.cardpay.pccredit.manager.web.DailyAccountManagerForm;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@Controller
public class JnpadDailyAccountManagerController {

	@Autowired
	private JnpadDailyAccountManagerService jnpadDailyAccountManagerService;
	
	@Autowired
	private DailyAccountService dailyAccountService;
	
	/**
	 * 客户经理日报浏览页面
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/dailyAccount/browse.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String browse(@ModelAttribute DailyAccountManagerFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		String loginId = request.getParameter("userId");
		filter.setLoginId(loginId);
		QueryResult<DailyAccountManagerForm> result = jnpadDailyAccountManagerService.findDailyAccountManagersByFilter(filter);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 日报修改
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/dailyAccount/update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public String update(@ModelAttribute DailyAccountManagerForm dailyAccountManagerForm, HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
			try {
				String loginId = request.getParameter("userId");
				DailyAccountManager dailyAccountManager = new DailyAccountManager();
				dailyAccountManager.setModifiedBy(loginId);
				dailyAccountManager.setTodayplan(request.getParameter("todayplan"));;
				dailyAccountManager.setTomorrowplan(request.getParameter("tomorrowplan"));
				dailyAccountManager.setId(request.getParameter("id"));
				dailyAccountService.updateAccountManager(dailyAccountManager);
				map.put("message", "提交成功");
				map.put("success", "true");
				
			} catch (Exception e) {
				map.put("message", "提交失败");
				map.put("success", "false");
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(map, jsonConfig);
				return json.toString();
			}

				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(map, jsonConfig);
				return json.toString();
	}

	
	
}
