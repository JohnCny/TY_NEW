package com.cardpay.pccredit.customer.web;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.CustomerMaintenanceLogFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerMaintenanceLog;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.CustomerMaintenanceLogService;
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
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
@Controller
@RequestMapping("/customer/customermaintenancelog/*")
@JRadModule("customer.customermaintenancelog")
public class CustomerMaintenanceLogController extends BaseController {
	
	@Autowired
	private CustomerMaintenanceLogService customerMaintenanceLogService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	/**
	 * 客户维护日志浏览页面
	 * 
	 * @param CustomerInforFilter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute CustomerInforFilter filter,HttpServletRequest request) {

        filter.setRequest(request);
        IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		QueryResult<CustomerInfor> result = customerInforservice.findCustomerInforByFilter(filter);
		JRadPagedQueryResult<CustomerInfor> pagedResult = new JRadPagedQueryResult<CustomerInfor>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMaintenanceLog/customermaintenancelog_browse",
                                                    request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	/**
	 * 客户基本信息维护日志显示页面
	 * 
	 * @param CustomerMaintenanceLogFilter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "customerinfobrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView customerinfobrowse(@ModelAttribute CustomerMaintenanceLogFilter filter,HttpServletRequest request) {

        filter.setRequest(request);
        String customerId=request.getParameter("id");
    	filter.setCustomerId(customerId);
    	filter.setModifyTableName("basic_customer_information");
		QueryResult<CustomerMaintenanceLog> result = customerMaintenanceLogService.findCustomerInforLogByFilter(filter);
		JRadPagedQueryResult<CustomerMaintenanceLog> pagedResult = new JRadPagedQueryResult<CustomerMaintenanceLog>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMaintenanceLog/customermaintenancelog_jbxx",
                                                    request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("customerId", customerId);
		mv.addObject("modifyTableName", "basic_customer_information");

		return mv;
	}
	
	/**
	 * 客户四维评估维护日志显示页面
	 * @param CustomerMaintenanceLogFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "customerswpgbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView customerswpgbrowse(@ModelAttribute CustomerMaintenanceLogFilter filter,HttpServletRequest request) {

        filter.setRequest(request);
        String customerId=request.getParameter("id");
    	filter.setCustomerId(customerId);
    	filter.setModifyTableName("dimensional_model_credit");
		QueryResult<CustomerMaintenanceLog> result = customerMaintenanceLogService.findCustomerInforLogByFilter(filter);
		JRadPagedQueryResult<CustomerMaintenanceLog> pagedResult = new JRadPagedQueryResult<CustomerMaintenanceLog>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMaintenanceLog/customermaintenancelog_swpg",
                                                    request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("customerId", customerId);
		mv.addObject("modifyTableName", "dimensional_model_credit");

		return mv;
	}
	/**
	 *  客户职业维护日志显示页面
	 * @param CustomerMaintenanceLogFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "customerkhzybrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView customerkhzybrowse(@ModelAttribute CustomerMaintenanceLogFilter filter,HttpServletRequest request) {

        filter.setRequest(request);
        String customerId=request.getParameter("id");
    	filter.setCustomerId(customerId);
    	filter.setModifyTableName("customer_careers_information");
		QueryResult<CustomerMaintenanceLog> result = customerMaintenanceLogService.findCustomerInforLogByFilter(filter);
		JRadPagedQueryResult<CustomerMaintenanceLog> pagedResult = new JRadPagedQueryResult<CustomerMaintenanceLog>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMaintenanceLog/customermaintenancelog_khzy",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("customerId", customerId);
		mv.addObject("modifyTableName", "customer_careers_information");

		return mv;
	}
	/**
	 *  陌拜维护日志显示页面
	 * @param CustomerMaintenanceLogFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "customermbxxbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView customermbxxbrowse(@ModelAttribute CustomerMaintenanceLogFilter filter,HttpServletRequest request) {

        filter.setRequest(request);
        String customerId=request.getParameter("id");
    	filter.setCustomerId(customerId);
    	filter.setModifyTableName("customer_worship_information");
		QueryResult<CustomerMaintenanceLog> result = customerMaintenanceLogService.findCustomerInforLogByFilter(filter);
		JRadPagedQueryResult<CustomerMaintenanceLog> pagedResult = new JRadPagedQueryResult<CustomerMaintenanceLog>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMaintenanceLog/customermaintenancelog_mbxx",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("customerId", customerId);
		mv.addObject("modifyTableName", "customer_worship_information");

		return mv;
	}
	
	/**
	 *  影像资料维护日志显示页面
	 * @param CustomerMaintenanceLogFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "customeryxzlbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView customeryxzlbrowse(@ModelAttribute CustomerMaintenanceLogFilter filter,HttpServletRequest request) {

        filter.setRequest(request);
        String customerId=request.getParameter("id");
    	filter.setCustomerId(customerId);
    	filter.setModifyTableName("video_accessories");
		QueryResult<CustomerMaintenanceLog> result = customerMaintenanceLogService.findCustomerInforLogByFilter(filter);
		JRadPagedQueryResult<CustomerMaintenanceLog> pagedResult = new JRadPagedQueryResult<CustomerMaintenanceLog>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMaintenanceLog/customermaintenancelog_yxzl",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("customerId", customerId);
		mv.addObject("modifyTableName", "video_accessories");

		return mv;
	}
	/**
	 *  调查大纲维护日志显示页面
	 * @param CustomerMaintenanceLogFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "customerdcdgbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView customerdcdgbrowse(@ModelAttribute CustomerMaintenanceLogFilter filter,HttpServletRequest request) {

        filter.setRequest(request);
        String customerId=request.getParameter("id");
    	filter.setCustomerId(customerId);
    	filter.setModifyTableName("customer_question_info");
		List<CustomerMaintenanceLog> customerMaintenanceLogresult = customerMaintenanceLogService.findCustomerInforLogMbxxByFilter(filter);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMaintenanceLog/customermaintenancelog_dcdg",request);
		mv.addObject("customerMaintenanceLogresult", customerMaintenanceLogresult);
		mv.addObject("customerId", customerId);
		mv.addObject("modifyTableName", "customer_question_info");

		return mv;
	}
	/**
	 *  台账信息维护日志显示页面
	 * @param CustomerMaintenanceLogFilter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "customertzxxbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView customertzxxbrowse(@ModelAttribute CustomerMaintenanceLogFilter filter,HttpServletRequest request) {

        filter.setRequest(request);
        String customerId=request.getParameter("id");
    	filter.setCustomerId(customerId);
    	filter.setModifyTableName("customer_main_manager");
		QueryResult<CustomerMaintenanceLog> result = customerMaintenanceLogService.findCustomerInforLogByFilter(filter);
		JRadPagedQueryResult<CustomerMaintenanceLog> pagedResult = new JRadPagedQueryResult<CustomerMaintenanceLog>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMaintenanceLog/customermaintenancelog_tzxx",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("customerId", customerId);
		mv.addObject("modifyTableName", "customer_main_manager");

		return mv;
	}
	/**
	 * 保存维护日志
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(HttpServletRequest request) {
		
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		Calendar calendar = Calendar.getInstance();
		String modified_time = DateHelper.getDateFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String modifyTableName = request.getParameter("modifyTableName");
				String customerId=request.getParameter("customerId");
				String modifyTablevalue=request.getParameter("modifyTablevalue");
				modifyTablevalue =	modifyTablevalue + "modified_by = '" + loginId +"',modified_time = to_date('" + modified_time +"','yyyy-MM-dd  HH24:mi:SS')";
				customerMaintenanceLogService.updateCustomerinforUpdateLog(modifyTableName,customerId,modifyTablevalue);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
				returnMap.put("customerId",customerId);
				returnMap.put("modifyTableName",modifyTableName);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/**
	 * 基本信息维护日志保存
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatejbxx.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updatejbxx(HttpServletRequest request) {
		
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		Calendar calendar = Calendar.getInstance();
		String modified_time = DateHelper.getDateFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String modifyTableName = request.getParameter("modifyTableName");
				String customerId=request.getParameter("customerId");
				String modifyTablevalue=request.getParameter("modifyTablevalue");
				modifyTablevalue =	modifyTablevalue + "modified_by = '" + loginId +"',modified_time = to_date('" + modified_time +"','yyyy-MM-dd  HH24:mi:SS')";
				customerMaintenanceLogService.updateCustomerinforUpdatejbxxLog(modifyTableName,customerId,modifyTablevalue);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
				returnMap.put("customerId",customerId);
				returnMap.put("modifyTableName",modifyTableName);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/**
	 * 調查大綱维护日志保存
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatedcdg.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updatedcdg(HttpServletRequest request) {
		
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		Calendar calendar = Calendar.getInstance();
		String modified_time = DateHelper.getDateFormat(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				String modifyTableName = request.getParameter("modifyTableName");
				String customerId=request.getParameter("customerId");
				String modifyTablevalue=request.getParameter("modifyTablevalue");
				  String[] strarray=modifyTablevalue.split(":"); 
			      for (int i = 0; i < strarray.length - 1; ) 
			      {
			      String  questionCode =  strarray[i];
				  modifyTablevalue = "question_answer = '" + strarray[i+1] + "',modified_by = '" + loginId +"',modified_time = to_date('" + modified_time +"','yyyy-MM-dd  HH24:mi:SS')";
				  customerMaintenanceLogService.updateCustomerinforUpdatedcdgLog(modifyTableName,customerId,modifyTablevalue,questionCode);
			        i = i + 2;
			      }
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
				returnMap.put("customerId",customerId);
				returnMap.put("modifyTableName",modifyTableName);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	


}
