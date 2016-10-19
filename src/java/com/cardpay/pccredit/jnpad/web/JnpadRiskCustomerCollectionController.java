package com.cardpay.pccredit.jnpad.web;

import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.MarketingCreateWayEnum;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnpadRiskCustomerCollectionService;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionConstant;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEndResultEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerCollectionPlanFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlan;
import com.cardpay.pccredit.riskControl.service.RiskCustomerCollectionService;
import com.cardpay.pccredit.riskControl.web.RiskCustomerCollectionPlanForm;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadRiskCustomerCollectionController extends BaseController{
	@Autowired
	private JnpadRiskCustomerCollectionService riskCustomerCollectionService;
	

	@Autowired
	private RiskCustomerCollectionService riskCustomerCollectionService1;
	/**
	 * 通过id得到逾期客户催收计划
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/findRiskCustomerCollectionPlanById.json")
	public String findRiskCustomerCollectionPlanById(HttpServletRequest request){
		String collectionPlanId = RequestHelper.getStringValue(request, "id");
		
		RiskCustomerCollectionPlanForm collectionplan = riskCustomerCollectionService.findRiskCustomerCollectionPlanById(collectionPlanId);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(collectionplan, jsonConfig);
		return json.toString();
	}
	
	
	
	/**
	 * 过滤查询逾期催收客户
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/findRiskCustomerCollectionPlansByFilter.json")
	public String findRiskCustomerCollectionPlansByFilter(@ModelAttribute RiskCustomerCollectionPlanFilter filter,HttpServletRequest request){
		
		String id = RequestHelper.getStringValue(request, "userId");
		filter.setCustomerManagerId(id);
 
		
		QueryResult<RiskCustomerCollectionPlanForm> result = riskCustomerCollectionService.findRiskCustomerCollectionPlansByFilter(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 得到当前客户经理下属经理名下的逾期客户催收信息数量
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/findRiskViewSubCollectionPlansCountByFilter.json")
	public String findRiskViewSubCollectionPlansCountByFilter(@ModelAttribute RiskCustomerCollectionPlanFilter filter,HttpServletRequest request){
		String id = RequestHelper.getStringValue(request, "id");
		filter.setCustomerManagerId(id);
		int result=riskCustomerCollectionService.findRiskViewSubCollectionPlansCountByFilter(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 显示催收计划详细信息
	 * 
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/product/browerRiskcustomer.json")
	public String display(HttpServletRequest request) {
		Map<String, Object> map =new LinkedHashMap<String, Object>();
		String collectionPlanId = request.getParameter("collectionPlanId");
		RiskCustomerCollectionPlanForm collectionplan = new RiskCustomerCollectionPlanForm();
//		if (StringUtils.isNotEmpty(collectionPlanId)) {
			collectionplan = riskCustomerCollectionService.findRiskCustomerCollectionPlanById(collectionPlanId);
//			List<RiskCustomerCollectionPlansAction> collectionActions = riskCustomerCollectionService.findRiskCustomerCollectionPlansActionByCollectionPlanId(collectionPlanId);
			map.put("collectionPlan", collectionplan);
//			map.put("collectionActions",collectionActions);
//		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 得到当前客户经理的逾期客户
	 * @param request
	 * @param printWriter
	 * @return 
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/getCustomer.json")
	public String getCustomer(HttpServletRequest request){
		Map<String, Object> map =new LinkedHashMap<String, Object>();
		String userId = RequestHelper.getStringValue(request, ID);
		List<Dict> riskCustomers = riskCustomerCollectionService.findCustomerIdAndName(userId);
		int size =riskCustomerCollectionService.getCustomerIdAndNameCount(userId);
		map.put("result", riskCustomers);
		map.put("size", size);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 添加催收计划
	 * @param form
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/insert.json")
	public String insert(@ModelAttribute RiskCustomerCollectionPlanForm form, HttpServletRequest request) {
		Map<String, Object> map =new LinkedHashMap<String, Object>();
		form.setCustomerId(request.getParameter("customerId"));
		form.setProductId(request.getParameter("prodcctId"));
		form.setCollectionMethod(request.getParameter("way"));
		form.setImplementationObjective("csmb");
		form.setCollectionTime(request.getParameter("csts"));
		form.setCustomerManagerId(request.getParameter("userId"));
		boolean flag = riskCustomerCollectionService1.checkCollectionPlan(form.getCustomerId(),form.getProductId(),RiskCustomerCollectionEndResultEnum.collection,RiskCustomerCollectionEndResultEnum.repaymentcommitments);
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if(!flag){
			if (returnMap.isSuccess()) {
					RiskCustomerCollectionPlan riskCustomerCollectionPlan = form.createModel(RiskCustomerCollectionPlan.class);
					IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
					String createdBy = user.getId();
					String customerManagerId = riskCustomerCollectionPlan.getCustomerManagerId();
					if(createdBy!=null && createdBy.equals(customerManagerId)){
						riskCustomerCollectionPlan.setCreateWay(MarketingCreateWayEnum.myself.toString());
					}else{
						riskCustomerCollectionPlan.setCreateWay(MarketingCreateWayEnum.manager.toString());
					}
					riskCustomerCollectionPlan.setCreatedBy(createdBy);
					riskCustomerCollectionService1.insertRiskCustomerCollectionPlan(riskCustomerCollectionPlan);
					map.put("message", "添加成功");
			
	}else{
			map.put("message", "添加失败");
		}}
	else{
			map.put("message", "该客户已经添加逾期客户");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
		
	}
	
}
