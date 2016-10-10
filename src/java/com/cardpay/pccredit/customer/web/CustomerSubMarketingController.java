/**
 * 
 */
package com.cardpay.pccredit.customer.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.CustomerMarketingEndResultEnum;
import com.cardpay.pccredit.customer.constant.MarketingCreateWayEnum;
import com.cardpay.pccredit.customer.filter.CustomerMarketingFilter;
import com.cardpay.pccredit.customer.model.CustomerMarketing;
import com.cardpay.pccredit.customer.model.CustomerMarketingPlan;
import com.cardpay.pccredit.customer.model.CustomerMarketingWeb;
import com.cardpay.pccredit.customer.model.MarketingPlanWeb;
import com.cardpay.pccredit.customer.service.CustomerMarketingService;
import com.cardpay.pccredit.manager.service.ManagerBelongMapService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
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
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * @author shaoming
 *
 * 2014年11月17日   下午4:12:01
 */
@Controller
@RequestMapping("/customer/customersubmarketingplan/*")
@JRadModule("customer.customersubmarketingplan")
public class CustomerSubMarketingController extends BaseController{

	@Autowired
	private CustomerMarketingService customerMarketingService;
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	
	@Autowired
	private ProductService productService;
	/**
	 * 浏览下属营销计划页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView view(@ModelAttribute CustomerMarketingFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String customerManagerId = user.getId();
		List<AccountManagerParameterForm> forms =
				managerBelongMapService.findSubListManagerByManagerId(customerManagerId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketing_view_browse", request);;
		if(forms.size()>0){
			Iterator<AccountManagerParameterForm> it = forms.iterator();
			QueryResult<MarketingPlanWeb> result = null;
			if(filter.getCustomerManagerId()==null || filter.getCustomerManagerId().equals("")){
				List<String> userIds = new ArrayList<String>();
				while(it.hasNext()){
					String userId = it.next().getUserId();
					userIds.add(userId);
				}
				filter.setIds(userIds);
				result = customerMarketingService.findMarketingPlansByUserIds(filter);
			}else{
				result = customerMarketingService.findMarketingPlansByFilter(filter);
			}
			JRadPagedQueryResult<MarketingPlanWeb> pagedResult = new JRadPagedQueryResult<MarketingPlanWeb>(filter, result);
			mv.addObject(PAGED_RESULT, pagedResult);
			mv.addObject("forms",forms);
			return mv;
		}else{
			return mv;
		}
	}
	/**
	 * 修改营销计划页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "viewChangePlan.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView viewChangePlan(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketingPlan_view_change", request);
		String marketingId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(marketingId)) {
			String userId = customerMarketingService.findCustomerManagerIdByMarketingId(marketingId);
			CustomerMarketingWeb customerMarketingWeb = customerMarketingService.findCustomerMarketingById(marketingId);
			List<ProductAttribute> products = productService.findProductAttributeByuserId(userId);
			mv.addObject("customerMarketing", customerMarketingWeb);
			mv.addObject("products", products);
		}
		return mv;
	}
	/**
	 * 执行营销计划修改
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "viewUpdate.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap viewUpdate(@ModelAttribute CustomerMarketingForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		String createWay = customerMarketingService.findMarketingPlanById(form.getId()).getCreateWay();
		if(createWay!=null && createWay.equals(MarketingCreateWayEnum.system.toString())){
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.UPDATEERROR);
			return returnMap;
		}
		if (returnMap.isSuccess()) {
			try {
				CustomerMarketing customerMarketing = form.createModel(CustomerMarketing.class);
				customerMarketingService.updateCustomerMarketing(customerMarketing);
				returnMap.put(RECORD_ID, customerMarketing.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.SUCCESS,false);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.UPDATEERROR);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	/**
	 * 显示营销计划详细页面(下属)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "viewDisplay.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView ViewDisplay(HttpServletRequest request) { 
		String id = RequestHelper.getStringValue(request, ID);
		CustomerMarketingWeb customerMarketing = customerMarketingService.findCustomerMarketingById(id);
		List<CustomerMarketingPlan> marketingPlans =
                customerMarketingService.findCustomerMarketingPlans(customerMarketing.getId());
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketing_view_display",request);
		mv.addObject("marketing",customerMarketing);
		mv.addObject("marketingplans",marketingPlans);
		return mv;
	}
	/**
	 * 修改营销实施页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "ViewChange.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView viewChange(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketing_view_change", request);
		String marketingId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(marketingId)) {
			CustomerMarketingPlan customerMarketingPlan = customerMarketingService.findCustomerMarketingPlanById(marketingId);
			mv.addObject("marketingPlan", customerMarketingPlan);
		}
		return mv;
	}
	/**
	 * 执行营销实施修改
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute CustomerMarketingPlanForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				//修改营销计划
				CustomerMarketing customerMarketing = new CustomerMarketing();
				String endResult = RequestHelper.getStringValue(request, "endResult");
				String marketingPlanId = RequestHelper.getStringValue(request, "marketingPlanId");
				customerMarketing.setEndResult(endResult);
				customerMarketing.setId(marketingPlanId);
				customerMarketingService.updateCustomerMarketing(customerMarketing);
				//修改营销实施记录,营销计划最终结果
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				CustomerMarketingPlan customerMarketingPlan = new CustomerMarketingPlan();
				customerMarketingPlan.setId(form.getId());
				customerMarketingPlan.setMarketingPlanId(form.getMarketingPlanId());
				String marketingStartTime = form.getMarketingStartTime();
				String marketingEndTime = form.getMarketingEndTime();
				if(marketingStartTime!=null && !marketingStartTime.equals("")){
					customerMarketingPlan.setMarketingStartTime(DateHelper.getDateFormat(marketingStartTime, "yyyy-MM-dd HH:mm:ss"));
				}
				if(marketingEndTime!=null && !marketingEndTime.equals("")){
					customerMarketingPlan.setMarketingEndTime(DateHelper.getDateFormat(marketingEndTime, "yyyy-MM-dd HH:mm:ss"));
				}
				customerMarketingPlan.setModifiedBy(userId);
				customerMarketingPlan.setMarketingResult(form.getMarketingResult());
				customerMarketingPlan.setWhetherImplement(form.getWhetherImplement());
				customerMarketingService.updateCustomerMarketingPlan(customerMarketingPlan);
				if(endResult.equals(CustomerMarketingEndResultEnum.continuemarketing.toString())){
					String customerMarketingId = customerMarketingService.copyCustomerMarketing(userId,CustomerMarketingEndResultEnum.marketing,marketingPlanId);
					returnMap.put(RECORD_ID, customerMarketingId);
				}else{
					returnMap.put(RECORD_ID, customerMarketingPlan.getId());
				}
				returnMap.put(JRadConstants.MESSAGE, endResult);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.SUCCESS,false);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.UPDATEERROR);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
}
