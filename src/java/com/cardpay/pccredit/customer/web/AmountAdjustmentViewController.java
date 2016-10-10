/**
 * 
 */
package com.cardpay.pccredit.customer.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.AmountAdjustmentFilter;
import com.cardpay.pccredit.customer.service.AmountAdjustmentService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * 
 * 描述 ：
 * @author 张石树
 *
 * 2014-12-3 下午1:32:13
 */
@Controller
@RequestMapping("/customer/amountadjustmentview/*")
@JRadModule("customer.amountadjustmentview")
public class AmountAdjustmentViewController extends BaseController{
	
	@Autowired
	private AmountAdjustmentService amountAdjustmentService;

	/**
	 * 浏览调额信息信息页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute AmountAdjustmentFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<AmountAdjustmentForm> result = amountAdjustmentService.findAmountAdjustmentFilter(filter);
		JRadPagedQueryResult<AmountAdjustmentForm> pagedResult = new JRadPagedQueryResult<AmountAdjustmentForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/amountadjustmentview/amountadjustmentview_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
}
