package com.cardpay.pccredit.customer.web;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

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
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerMarketing;
import com.cardpay.pccredit.customer.model.CustomerMarketingPlan;
import com.cardpay.pccredit.customer.model.CustomerMarketingWeb;
import com.cardpay.pccredit.customer.model.MarketingPlanWeb;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.CustomerMarketingService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
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
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 
 * @author 姚绍明
 *
 */
@Controller
@RequestMapping("/customer/customermarketingplan/*")
@JRadModule("customer.customermarketingplan")
public class CustomerMarketingController extends BaseController{
	
	@Autowired
	private CustomerMarketingService customerMarketingService;
	
	@Autowired
	private ManagerBelongMapService managerBelongMapService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerInforService customerInforService;
	/**
	 * 浏览营销计划页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute CustomerMarketingFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String customerManagerId = user.getId();
		filter.setCustomerManagerId(customerManagerId);
//		List<AccountManagerParameterForm> forms =
//				managerBelongMapService.findSubManagerBelongMapByManagerId(customerManagerId);
		QueryResult<MarketingPlanWeb> result = customerMarketingService.findMarketingPlansByFilter(filter);
		JRadPagedQueryResult<MarketingPlanWeb> pagedResult = new JRadPagedQueryResult<MarketingPlanWeb>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketing_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
//		if(forms.size()<=1){
//			mv.addObject(CustomerInforConstant.ISEMPTY,true);
//		}
		return mv;
	}
	
	/**
	 * 修改营销计划页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "changePlan.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView changePlan(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketingPlan_change", request);
		String marketingId = RequestHelper.getStringValue(request, ID);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		if (StringUtils.isNotEmpty(marketingId)) {
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
	@RequestMapping(value = "updatePlan.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updatePlan(@ModelAttribute CustomerMarketingForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		String createWay = customerMarketingService.findMarketingPlanById(form.getId()).getCreateWay();
		if(createWay!=null && !createWay.equals(MarketingCreateWayEnum.myself.toString())){
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.MAKETINGUPDATEERROR);
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
	 * 显示营销计划详细页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView display(HttpServletRequest request) { 
		String id = RequestHelper.getStringValue(request, ID);
		CustomerMarketingWeb customerMarketing = customerMarketingService.findCustomerMarketingById(id);
		List<CustomerMarketingPlan> marketingPlans =
                customerMarketingService.findCustomerMarketingPlans(customerMarketing.getId());
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketing_display",request);
		mv.addObject("marketing",customerMarketing);
		mv.addObject("marketingplans",marketingPlans);
		return mv;
	}
	
	/**
	 * 新增营销计划页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "createPlan.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		List<AccountManagerParameterForm> accountManagerParameterForms = managerBelongMapService.findSubManagerBelongMapByManagerId(userId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketingPlan_create", request);
		mv.addObject("users",accountManagerParameterForms);
		return mv;
	}
	/**
	 * 执行营销计划添加
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertPlan.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute CustomerMarketingForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				CustomerMarketing customerMarketing = form.createModel(CustomerMarketing.class);
				User user=(User)Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				String customerManagerId = customerMarketing.getCustomerManagerId();
				if(userId!=null && userId.equals(customerManagerId)){
					customerMarketing.setCreateWay(MarketingCreateWayEnum.myself.toString());
				}else{
					customerMarketing.setCreateWay(MarketingCreateWayEnum.manager.toString());
				}
				customerMarketing.setCreatedBy(userId);
				String id = customerMarketingService.insertCustomerMarketing(customerMarketing);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	/**
	 * 新增营销实施页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createbrowse(HttpServletRequest request) {
		String id = RequestHelper.getStringValue(request, ID);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketing_create", request);
		mv.addObject("ID",id);
		return mv;
	}
	
	/**
	 * 执行营销计划实施记录添加
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute CustomerMarketingPlanForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				//修改营销计划表最终结果
				CustomerMarketing customerMarketing = new CustomerMarketing();
				String endResult = RequestHelper.getStringValue(request, "endResult");
				String marketingPlanId = RequestHelper.getStringValue(request, "marketingPlanId");
				customerMarketing.setEndResult(endResult);
				customerMarketing.setId(marketingPlanId);
				customerMarketingService.updateCustomerMarketing(customerMarketing);
				//添加一条营销实施记录
				CustomerMarketingPlan customerMarketingPlan = new CustomerMarketingPlan();
				String marketingEndTime = form.getMarketingEndTime();
				String marketingStartTime = form.getMarketingStartTime();
				Date end = DateHelper.getDateFormat(marketingEndTime, "yyyy-MM-dd HH:mm:ss");
				Date start = DateHelper.getDateFormat(marketingStartTime, "yyyy-MM-dd HH:mm:ss");
				User user=(User)Beans.get(LoginManager.class).getLoggedInUser(request);
				String userId = user.getId();
				
				customerMarketingPlan.setCreatedBy(userId);
				customerMarketingPlan.setMarketingEndTime(end);
				customerMarketingPlan.setMarketingStartTime(start);
				customerMarketingPlan.setMarketingPlanId(marketingPlanId);
				customerMarketingPlan.setMarketingResult(form.getMarketingResult());
				customerMarketingPlan.setMarketing(form.getMarketing());
				customerMarketingPlan.setWhetherImplement(form.getWhetherImplement());
				String id = customerMarketingService.insertCustomerMarketingPlan(customerMarketingPlan);
				//若最终结果为继续营销时,新建一条内容相同的营销计划
				if(endResult.equals(CustomerMarketingEndResultEnum.continuemarketing.toString())){
					String customerMarketingId = customerMarketingService.copyCustomerMarketing(userId,CustomerMarketingEndResultEnum.marketing,marketingPlanId);
					returnMap.put(RECORD_ID, customerMarketingId);
				}else{
					returnMap.put(RECORD_ID, id);
				}
				returnMap.put(JRadConstants.MESSAGE, endResult);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	/**
	 * 修改营销实施页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMarketing/customerMarketing_change", request);
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
					returnMap.put(JRadConstants.MESSAGE, endResult);
				}else{
					returnMap.put(RECORD_ID, customerMarketingPlan.getId());
				}
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.SUCCESS,false);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.UPDATEERROR);
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	@RequestMapping(value = "getCustomer.json",method = RequestMethod.GET)
	public void getCustomer(HttpServletRequest request,PrintWriter printWriter){
		String userId = RequestHelper.getStringValue(request, ID);
		if(StringUtils.isNotEmpty(userId) && userId!="null"){
			List<CustomerInfor> customers = customerInforService.findCustomerByManagerId(userId);
			ListToJson(customers,printWriter);
		}
	}
	@RequestMapping(value = "getProduct.json",method = RequestMethod.GET)
	public void getProduct(HttpServletRequest request,PrintWriter printWriter){
		String userId = RequestHelper.getStringValue(request, ID);
		if(StringUtils.isNotEmpty(userId) && userId!="null"){
			List<ProductAttribute> products = productService.findProductAttributeByuserId(userId);
			ListToJson(products,printWriter);
		}
	}
	public static void ListToJson(List<?> list,PrintWriter printWriter){
		JSONArray json = new JSONArray();
		json = JSONArray.fromObject(list);
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
	}
}
