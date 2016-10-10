package com.cardpay.pccredit.jnpad.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.MarketingCreateWayEnum;
import com.cardpay.pccredit.customer.model.Maintenance;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService;
import com.cardpay.pccredit.manager.filter.RetrainingFilter;
import com.cardpay.pccredit.manager.model.Retraining;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionConstant;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEndResultEnum;
import com.cardpay.pccredit.riskControl.model.RiskCustomerCollectionPlan;
import com.cardpay.pccredit.riskControl.service.RiskCustomerCollectionService;
import com.cardpay.pccredit.riskControl.web.RiskCustomerCollectionPlanForm;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;

/**
 * ipad interface
 * 3.4.1客户维护计划
 * songchen
 * 2016年05月09日   下午1:54:18
 */
@Controller
public class JnIpadCustMaintenanceController {
	
	@Autowired
	private JnIpadCustAppInfoXxService appInfoXxService;

	@Autowired
	private MaintenanceService maintenanceService;
	
	@Autowired
	private RiskCustomerCollectionService riskCustomerCollectionService;
	/**
	 * 添加客户维护计划
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/insertMaintenance.json", method = { RequestMethod.GET })
	public String insertMaintenance( HttpServletRequest request) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		try{
			Maintenance maintenance =new Maintenance();
//			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			
			String createdBy = request.getParameter("customerManagerId");
			maintenance.setCustomerManagerId(request.getParameter("customerManagerId"));
			maintenance.setCreatedBy(createdBy);
			maintenance.setCustomerId(request.getParameter("customerId"));
			maintenance.setAppId(request.getParameter("appId"));
			maintenance.setMaintenanceGoal(request.getParameter("maintenanceGoal"));
			maintenance.setMaintenanceWay(request.getParameter("createWay"));
			maintenance.setMaintenanceDay(request.getParameter("maintenanceDay"));
			String customerManagerId = maintenance.getCustomerManagerId();

			if(customerManagerId!=null && customerManagerId.equals(createdBy)){
				maintenance.setCreateWay(MarketingCreateWayEnum.myself.toString());;
			}else{
				maintenance.setCreateWay(MarketingCreateWayEnum.manager.toString());;
			}
			String id = maintenanceService.insertMaintenance(maintenance);
			result.put("result", "增加成功");
		}catch (Exception e){
			result.put("result", "增加失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 添加催收计划
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/insertRiskCustomerCollectionPlan.json", method = { RequestMethod.GET })
	public String insertRiskCustomerCollectionPlan(@ModelAttribute RiskCustomerCollectionPlanForm form, HttpServletRequest request) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		boolean flag = riskCustomerCollectionService.checkCollectionPlan(form.getCustomerId(),
																		 form.getProductId(),
																		 RiskCustomerCollectionEndResultEnum.collection,
																		 RiskCustomerCollectionEndResultEnum.repaymentcommitments);
		if(!flag){
				try {
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
					String id = riskCustomerCollectionService.insertRiskCustomerCollectionPlan(riskCustomerCollectionPlan);
					result.put("result", "success");
				}catch (Exception e) {
					e.printStackTrace();
					result.put("result", "fail");
				}
		}else{
			result.put("result", RiskCustomerCollectionConstant.alreadyExists);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 查询培训计划 
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findRetrainPlan.json", method = { RequestMethod.GET })
	public String findRetrainPlan(HttpServletRequest request,RetrainingFilter filter) {
		//分页参数
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		
		int page = 0;
		int limit = 10;
		if(StringUtils.isNotEmpty(currentPage)){
			page = Integer.parseInt(currentPage);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			limit = Integer.parseInt(pageSize);
		}
		filter.setPage(page);
		filter.setLimit(limit);
		
		
		List<Retraining> list = appInfoXxService.findRetrainingsByFilter(filter);
		int totalCount = appInfoXxService.findRetrainingsCountByFilter(filter);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("totalCount", totalCount);
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	/**
	 * 添加工作计划 
	 * ??
	 */
	
	
}
