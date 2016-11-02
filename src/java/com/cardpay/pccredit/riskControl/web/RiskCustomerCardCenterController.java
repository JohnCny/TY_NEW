package com.cardpay.pccredit.riskControl.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.service.RiskCustomerService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * @author chenzhifang
 *
 * 2014-11-4上午10:19:06
 */
@Controller
@RequestMapping("/riskcontrol/riskcustomercardcenter/*")
@JRadModule("riskcontrol.riskcustomercardcenter")
public class RiskCustomerCardCenterController extends BaseController {

	@Autowired
	private RiskCustomerService riskCustomerService;
	
	/**
	 * 卡中心人员浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cardcenterbrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView cardCenterBrowse(@ModelAttribute RiskCustomerFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        // 风险类型
        //filter.setRiskCreateType(RiskCreateTypeEnum.MANUAL.toString());
        // 卡中心人员
        filter.setRole(RiskControlRole.cardcenter.toString());
		QueryResult<RiskCustomer> result = riskCustomerService.findRiskCustomersByFilter(filter);
		JRadPagedQueryResult<RiskCustomer> pagedResult = new JRadPagedQueryResult<RiskCustomer>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/riskcustomer_cardcenter_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
}
