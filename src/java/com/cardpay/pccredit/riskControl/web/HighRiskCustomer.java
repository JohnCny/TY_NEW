package com.cardpay.pccredit.riskControl.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * 
 * @author 季东晓
 *
 * 2014-12-8 下午3:19:46
 */
@Controller
@RequestMapping("/riskcontrol/highriskcustomer/*")
@JRadModule("riskcontrol.highriskcustomer")
public class HighRiskCustomer extends BaseController{

	@Autowired
	private RiskCustomerService riskCustomerService;
	

	/**
	 * 高风险名单浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute RiskCustomerFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<RiskCustomer>  result = riskCustomerService.findRiskListByFilter(filter);
		JRadPagedQueryResult<RiskCustomer> pagedResult = new JRadPagedQueryResult<RiskCustomer>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/highRiskCustomer/high_riskcustomer_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 显示详情
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView display(HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		List<RiskCustomer>  result = riskCustomerService.findRiskCustomerBycustomerId(customerId);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/highRiskCustomer/high_riskcustomer_display", request);
		if(result.size()>0){
			RiskCustomer riskCustomer =	result.get(0);
			mv.addObject("cardId",riskCustomer.getCardId());
			mv.addObject("cardType",riskCustomer.getCardType());
			mv.addObject("customerName",riskCustomer.getCustomerName());
		}
		
		mv.addObject("result", result);
		return mv;
	}
}
