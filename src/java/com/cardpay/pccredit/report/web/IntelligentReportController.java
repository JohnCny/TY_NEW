/**
 * 
 */
package com.cardpay.pccredit.report.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.report.model.IntelligentAccountReport;
import com.cardpay.pccredit.report.model.IntelligentCustomerReport;
import com.cardpay.pccredit.report.model.PostLoanManagementData;
import com.cardpay.pccredit.report.service.IntelligentReportService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 智能报表
 * @author shaoming
 *
 * 2014年12月22日   下午2:05:33
 */
@Controller
@JRadModule("report.intelligentcustomerreport")
public class IntelligentReportController extends BaseController {

	@Autowired
	private IntelligentReportService intelligentReportService;
	/**
	 * 客户信息智能报表
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/report/intelligentcustomerreport/browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/report/intelligentreport/intelligentcustomerreport_browse", request);
		List<IntelligentCustomerReport> result = intelligentReportService.findIntelligentCustomerReport();
		mv.addObject("result", result);
		return mv;
	}
	/**
	 * 客户账户智能报表
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/report/intelligentaccountreport/browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView AccountBrowse(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/report/intelligentreport/intelligentaccountreport_browse", request);
		List<IntelligentAccountReport> result = intelligentReportService.findIntelligentAccountReport();
		mv.addObject("result", result);
		return mv;
	}
	/**
	 * 贷后管理数据
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/report/postloanmanagementdata/browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView PostLoanManagementData(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/report/intelligentreport/postloanmanagementdata_browse", request);
		PostLoanManagementData result = intelligentReportService.findPostLoanManagementData();
		mv.addObject("result", result);
		return mv;
	}
}
