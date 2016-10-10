package com.cardpay.pccredit.riskControl.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.filter.RiskReviewProcessFilter;
import com.cardpay.pccredit.riskControl.model.RiskReviewProcess;
import com.cardpay.pccredit.riskControl.service.RiskReviewProcessService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 风险事项审核流程(机构主管)
 * @author chenzhifang
 *
 * 2014-11-5下午4:49:14
 */
@Controller
@RequestMapping("/riskcontrol/riskreviewprocesssupervisor/*")
@JRadModule("riskcontrol.riskreviewprocesssupervisor")
public class RiskReviewProcSupervisorController extends BaseController {

	@Autowired
	private RiskReviewProcessService riskReviewProcessService;
	
	/**
	 * 客户经理浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "supervisorbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView supervisorBrowse(@ModelAttribute RiskReviewProcessFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        // 阶段
        filter.setPhase(RiskControlRole.supervisor.toString());
		QueryResult<RiskReviewProcess> result = riskReviewProcessService.findRiskReviewProcessByFilter(filter);
		JRadPagedQueryResult<RiskReviewProcess> pagedResult = new JRadPagedQueryResult<RiskReviewProcess>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskreviewprocess/riskreviewprocess_supervisor_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
}
