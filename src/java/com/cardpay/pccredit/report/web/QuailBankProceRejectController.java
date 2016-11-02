package com.cardpay.pccredit.report.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.report.filter.StatisticalFilter;
import com.cardpay.pccredit.report.model.QuailBankRejectMonitor;
import com.cardpay.pccredit.report.model.QuailBankReturnMonitor;
import com.cardpay.pccredit.report.model.QuailManaRejectMonitor;
import com.cardpay.pccredit.report.model.QuailManaReturnMonitor;
import com.cardpay.pccredit.report.model.manaProceMonitor;
import com.cardpay.pccredit.report.service.ProceMonitorService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * ProceMonitorController类的描述
 * 
 * @author 王海东
 * @created on 2014-12-19
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/report/cardqualityrejectbank/*")
@JRadModule("report.cardqualityrejectbank")
public class QuailBankProceRejectController extends BaseController {

	@Autowired
	private ProceMonitorService proceMonitorService;

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
	public AbstractModelAndView browse(@ModelAttribute StatisticalFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		List<QuailBankRejectMonitor> result = proceMonitorService.getQuailBankRejectMonitorStatistical(filter);
		JRadModelAndView mv = new JRadModelAndView("/report/cardquality/bank_quality_reject_browse", request);
		mv.addObject(PAGED_RESULT, result);

		return mv;
	}

	
}
