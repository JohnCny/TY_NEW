package com.cardpay.pccredit.customer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.MarketingPlanTypeEnum;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.service.CustomerFilterService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.CustomerMarketingService;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.service.UserService;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * CustomerFilterController类的描述
 * 
 * @author 王海东
 * @created on 2014-10-23
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/customer/customerfilter/*")
@JRadModule("customer.customerfilter")
public class CustomerFilterController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private CustomerFilterService customerFilterService;

	@Autowired
	private CustomerMarketingService customerMarketingService;

	@Autowired
	private DivisionalService divisionalService;

	/**
	 * 浏览客户筛选首页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute ProductFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<ProductAttribute> result = productService.findPublishedProductsByFilter(filter);
		JRadPagedQueryResult<ProductAttribute> pagedResult = new JRadPagedQueryResult<ProductAttribute>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/customer/customerFilter/customerfilter_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}

	/**
	 * 浏览筛选后页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "filter.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		String productId = request.getParameter("id");
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		// 调用筛选接口，通过productId查找 筛选规则。匹配客户。
		productService.insertScreeningResults(productId, loginId);
		// 根据productId查询SCREENING_RESULTS（筛选结果）表中Customer信息。
		List<CustomerInforForm> customerInforForm = customerFilterService.findScreeningResultsCustomerByProductId(productId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerFilter/customerfilter_second_browser", request);
		mv.addObject("customerInforForm", customerInforForm);
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 
	 * 没有客户经理生成营销计划，有客户经理走分案
	 * 
	 * @param customerMarketingForm
	 * @param request
	 * @return
	 */
    
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "createMarketingPlan.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute CustomerMarketingForm customerMarketingForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), customerMarketingForm);
		if (returnMap.isSuccess()) {
			try {

				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String ids = request.getParameter("id"); //前台传来数据格式为 [customerId=displayName:customerId=displayName...]
				String productId = request.getParameter("productId");

				if (StringUtils.trimToNull(ids) != null) {
					String[] custoManager = ids.split(":");
					if (custoManager != null && custoManager.length > 0) {
						for (int b = 0; b < custoManager.length; b++) {
							String cm = custoManager[b];
							String[] equal = cm.split("=");
							for (int v = 0; v < equal.length; v++) {
								String customerId = equal[v];
								//长度为1则为无客户经理用户
								if (equal.length == 1) {
									Boolean d = divisionalService.insertDivisionalCustomer(customerId, DivisionalTypeEnum.compulsive, DivisionalProgressEnum.manager);// 没有客户经理调用分案接口
									break;
								} else {
									int u = customerMarketingService.insertMarketingPlans(customerId, productId, MarketingPlanTypeEnum.system, loginId); // 有客户经理调用新建营销计划接口
									break;
								}
							}

						}
					}

				}

				returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
		}
		return returnMap;
	}
}
