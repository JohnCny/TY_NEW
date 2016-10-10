package com.cardpay.pccredit.customer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.filter.CustomerInfoLszFilter;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.TyRepayLsz;
import com.cardpay.pccredit.customer.model.TyRepayYehz;
import com.cardpay.pccredit.customer.model.TyRepayYehzVo;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
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
 * 客户余额信息
 * @author songchen
 *
 */
@Controller
@RequestMapping("/customer/customerInfor_yexx/*")
@JRadModule("customer.customerInfor_yexx")
public class CustomerInfo_yexx_Controller extends BaseController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CustomerInforService customerInforService;

	
	
	//显示余额信息
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		QueryResult<TyRepayYehzVo> result = customerInforService.findCustomerYexxByFilter(filter);
		JRadPagedQueryResult<TyRepayYehzVo> pagedResult = new JRadPagedQueryResult<TyRepayYehzVo>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/CustomerInfo_yexx/customer_yexx_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	/**
	 * 显示流水计划列表页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "displayInfo.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView displayInfo(@ModelAttribute CustomerInfoLszFilter filter,HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/CustomerInfo_yexx/customer_yexx_displayInfo", request);
		String jjh = RequestHelper.getStringValue(request, ID);
		filter.setJjh(jjh);
		filter.setRequest(request);
		QueryResult<TyRepayLsz> result = customerInforService.findRepayLszByFilter(filter);
		JRadPagedQueryResult<TyRepayLsz> pagedResult = new JRadPagedQueryResult<TyRepayLsz>(filter, result);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	
}

	