package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnpadRiskCustomerCollectionService;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerCollectionPlanFilter;
import com.cardpay.pccredit.riskControl.web.RiskCustomerCollectionPlanForm;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.util.web.RequestHelper;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadRiskCustomerCollectionController extends BaseController{
	@Autowired
	private JnpadRiskCustomerCollectionService riskCustomerCollectionService;
	
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

}
