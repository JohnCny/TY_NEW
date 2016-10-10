package com.cardpay.pccredit.riskControl.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.CustomerOverdueFilter;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.riskControl.service.CustomerOverdueService;
import com.cardpay.pccredit.sample2.model.Sample2;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;


/**
 * CustomerOverdueController类的描述
 *
 * @author 王海东
 * @created on 2014-11-4
 * 
 * @version $Id:$
 */

@Controller
@RequestMapping("/riskcontrol/riskcustomeroverdue/*")
@JRadModule("riskcontrol.riskcustomeroverdue")
public class CustomerOverdueController extends BaseController {
	
	@Autowired
	private CustomerOverdueService customerOverdueService;
	
	@Autowired
	private DivisionalService divisionalService;
	
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
	public AbstractModelAndView browse(@ModelAttribute CustomerOverdueFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setUserId(loginId);
		QueryResult<CustomerOverdueForm> result = customerOverdueService.findCustomerOverdue(filter);
		JRadPagedQueryResult<CustomerOverdueForm> pagedResult = new JRadPagedQueryResult<CustomerOverdueForm>(filter, (QueryResult<CustomerOverdueForm>) result);
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/riskcustomer_overdue", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 催收客戶移交
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.COLLECTIONTRANSFER)
	public AbstractModelAndView change(HttpServletRequest request) {
		String customerId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerId)) {
			divisionalService.insertDivisionalCustomer(customerId,DivisionalTypeEnum.initiative,DivisionalProgressEnum.charge);
		}
		return null;

	}

}
