package com.cardpay.pccredit.manager.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cardpay.pccredit.manager.filter.ManagerSalaryFilter;
import com.cardpay.pccredit.manager.model.ManagerSalary;
import com.cardpay.pccredit.manager.service.ManagerSalaryService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * @author songchen
 *
 * 2016-08-29下午5:56:37
 */
@Controller
@RequestMapping("/manager/managersalary/*")
@JRadModule("manager.managersalary")
public class ManagerSalaryController extends BaseController {
	@Autowired
	private ManagerSalaryService managerSalaryService;

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
	public AbstractModelAndView browse(@ModelAttribute ManagerSalaryFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<ManagerSalary> result = managerSalaryService.findManagerSalaryByFilter(filter);
		JRadPagedQueryResult<ManagerSalary> pagedResult = new JRadPagedQueryResult<ManagerSalary>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/manager/managersalary/managersalary_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	/**
	 * 生成月度数据
	 * 
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "generateData.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap generateData(@ModelAttribute ManagerSalaryForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		returnMap.setSuccess(true);
		if (returnMap.isSuccess()) {
			try {
//				managerSalaryService.calculateMonthlySalary(Integer.valueOf(form.getYear()), Integer.valueOf(form.getMonth()));
				//太原工资计算（泉州）
				managerSalaryService.calculateMonthlySalaryTy(Integer.valueOf(form.getYear()), Integer.valueOf(form.getMonth()));
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	
	/**
	 * 导出excel
	 * 
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "exportData.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap exportData(@ModelAttribute ManagerSalaryFilter filter, HttpServletRequest request,HttpServletResponse response) {
		JRadReturnMap returnMap = new JRadReturnMap();
		filter.setRequest(request);
		returnMap.setSuccess(true);
		if (returnMap.isSuccess()) {
			try {
			    managerSalaryService.getExportData(filter,response);
				
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
}
