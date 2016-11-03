package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.Maintenance;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.customer.web.MaintenanceWeb;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.filter.JnpadMaintenanceFilter;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.service.JnpadMaintenanceService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.riskControl.constant.RiskCustomerCollectionEnum;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 维护日志
 * @author sealy
 *
 */
@Controller
public class JnpadMaintenanceController extends BaseController{
	@Autowired
	private JnpadMaintenanceService jnpadMaintenanceService;
	
	/**
	 * 获取维护计划列表
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/TygetMaintenanceList1.json", method = { RequestMethod.GET })
	public String getMaintenanceList1(@ModelAttribute MaintenanceFilter filter, HttpServletRequest request) {

		String id =RequestHelper.getStringValue(request, "userId");
		int userType = RequestHelper.getIntValue(request, "userType");
		filter.setCustomerManagerId(RequestHelper.getStringValue(request, "userId"));
		List<AccountManagerParameterForm> forms = jnpadMaintenanceService.selectSubListManagerByManagerId(id,userType);
		String customerManagerId = filter.getCustomerManagerId();
		List<MaintenanceForm> result = null;
		if(customerManagerId!=null && !customerManagerId.equals("")){
			result = jnpadMaintenanceService.findMaintenancePlans1(filter);
		}else{
			if(forms.size()>0){
				filter.setCustomerManagerIds(forms);
				result = jnpadMaintenanceService.findMaintenancePlans1(filter);
			}else{
				String mesg = "暂无符合条件信息";
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(mesg, jsonConfig);
				return json.toString();

			}
		}
		Map<String,Object> query = new LinkedHashMap<String,Object>();
		query.put("result", result);
		query.put("size", result.size());
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(query, jsonConfig);
		return json.toString();
	}
	/**
	 * 查询客户经理
	 * @param request
	 * @param filter
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectSubListManagerByManagerId.json", method = { RequestMethod.GET })
	public String selectSubListManagerByManagerId(HttpServletRequest request){
		
		String id =RequestHelper.getStringValue(request, "id");
		int userType = RequestHelper.getIntValue(request, "userType");
		
		List<AccountManagerParameterForm> forms = jnpadMaintenanceService.selectSubListManagerByManagerId(id,userType);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(forms, jsonConfig);
		return json.toString();	

	}
	
	
	/**
	 * 获取指定客户经理的客户列表/客户维护日志
	 * @param filter
	 * @param request
	 * @return
	 */
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/product/getMaintenance.json", method = { RequestMethod.GET })
	public String getMaintenance( @ModelAttribute CustomerInforFilter filter,HttpServletRequest request) {
		String id =RequestHelper.getStringValue(request, "userId");
		int userType = RequestHelper.getIntValue(request, "userType");
		//查询下属客户经理
		List<AccountManagerParameterForm> forms = jnpadMaintenanceService.selectSubListManagerByManagerId(id,userType);
		
		if(forms.size()>0){
			filter.setCustomerManagerIds(forms);		
		}else{
			filter.setCustomerManagerIds(null);		
		}

		QueryResult<MaintenanceLog> result = jnpadMaintenanceService.findCustomerByFilter(filter);
//		JRadPagedQueryResult<MaintenanceLog> pagedResult = new JRadPagedQueryResult<MaintenanceLog>(filter, result);
//		JRadModelAndView mv = new JRadModelAndView("/customer/maintenance/maintenance_plan_log", request);
//		mv.addObject(PAGED_RESULT, pagedResult);
//		mv.addObject("forms", forms);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	
	}
		/**
		 * 获取维护计划列表
		 */
		@ResponseBody
		@RequestMapping(value = "/ipad/product/getMaintenanceList.json", method = { RequestMethod.GET })
		public String getMaintenanceList(@ModelAttribute MaintenanceFilter filter, HttpServletRequest request) {

			String id =RequestHelper.getStringValue(request, "userId");
			int userType = RequestHelper.getIntValue(request, "userType");
			filter.setCustomerManagerId(RequestHelper.getStringValue(request, "userId"));
			List<AccountManagerParameterForm> forms = jnpadMaintenanceService.selectSubListManagerByManagerId(id,userType);
			String customerManagerId = filter.getCustomerManagerId();
			List<MaintenanceForm> result = null;
			if(customerManagerId!=null && !customerManagerId.equals("")){
				result = jnpadMaintenanceService.findMaintenancePlansList(filter);
			}else{
				if(forms.size()>0){
					filter.setCustomerManagerIds(forms);
					result = jnpadMaintenanceService.findMaintenancePlansList(filter);
				}else{
					String mesg = "暂无符合条件信息";
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					JSONObject json = JSONObject.fromObject(mesg, jsonConfig);
					return json.toString();

				}
			}
			Map<String,Object> query = new LinkedHashMap<String,Object>();
//			Iterator<MaintenanceForm> it = result.iterator(); 
//			int i = 1;
//			int j = 1;
//			String  s="";
//		        while(it.hasNext()){  
//		        	MaintenanceForm maintenance = it.next();
//		       
//	                s+="<tr onclick='check(this)'>"+
//	                "<td><span class='radio'> <input type='radio' name='checkbox' value='"+maintenance.getChineseName()+"@"+
//	                maintenance.getProductName()+"@"+maintenance.getCardId()+
//	                "@"+maintenance.getCustomerId()+"@"+maintenance.getAppId()+"'"+"/>"+"</span></td>"+
//					"<td>"+i+"</td>"+
//					"<td>"+maintenance.getChineseName()+"</td>"+
//					"<td>"+maintenance.getCardId()+"</td>"+
//					"<td>"+maintenance.getProductName()+"</td>"+
//					"<td>"+maintenance.getUserName()+"</td>"+
//					"</tr>";   
//	                if(i%5==0){
//	                String m = Integer.toString(j);
//	                query.put(m, s);
//	                j++;
//	                s="";
//	                }
//	                i++;
//		        } 
//		        String m = Integer.toString(j);
//                query.put(m, s);
//			JRadPagedQueryResult<MaintenanceForm> pagedResult = new JRadPagedQueryResult<MaintenanceForm>(filter, result);
			query.put("result", result);
			query.put("size", result.size());
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(query, jsonConfig);
			return json.toString();
		}

		/**
		 * 查询维护日志列表
		 * @param filter
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ipad/product/MaintenanceLogInfo.json", method = { RequestMethod.GET })
		public String log_info(@ModelAttribute MaintenanceFilter filter, HttpServletRequest request) {
			
			String customerId = RequestHelper.getStringValue(request, "customerId");
			String productId =  RequestHelper.getStringValue(request, "productId");
//			filter.setRequest(request);
			filter.setCustomerId(customerId);
			filter.setProductId(productId);
			String appId = jnpadMaintenanceService.getAppId(customerId, productId);
			filter.setAppId(appId);
			QueryResult<MaintenanceWeb> result = jnpadMaintenanceService.findMaintenanceWebPlansByFilter(filter);
//			JRadPagedQueryResult<MaintenanceWeb> pagedResult = new JRadPagedQueryResult<MaintenanceWeb>(filter, result);
			Map<String, Object> map =new LinkedHashMap<String, Object>();
			map.put("result", result);
			map.put("filter", filter);
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(map, jsonConfig);
			return json.toString();
		}
		
		/**
		 * 维护信息查看
		 * 
		 */
		
		@ResponseBody
		@RequestMapping(value = "/ipad/product/MaintenanceLogInfo_brower.json")
		@JRadOperation(JRadOperation.DISPLAY)
		public String log_display(HttpServletRequest request) {
			Map<String, Object> map =new LinkedHashMap<String, Object>();
			String maintenanceId = request.getParameter("maintenanceId");
			String appId = request.getParameter("appId");
			if (StringUtils.isNotEmpty(maintenanceId)) {
				MaintenanceWeb m = new MaintenanceWeb();
				m.setAppId(appId);
				m.setId(maintenanceId);
				MaintenanceForm maintenance = jnpadMaintenanceService.findMaintenance(m);
				if(maintenance.getRepayStatus()=="0"||maintenance.getRepayStatus().equals("0")){
					maintenance.setRepayStatus("否");
				}else if(maintenance.getRepayStatus()=="1"||maintenance.getRepayStatus().equals("1")){
					maintenance.setRepayStatus("是");
				}
				if(maintenance.getMaintenanceWay()=="01"||maintenance.getMaintenanceWay().equals("01")){
					maintenance.setMaintenanceWay("电话");
				}else if(maintenance.getMaintenanceWay()=="02"||maintenance.getMaintenanceWay().equals("02")){
					maintenance.setMaintenanceWay("短信");
				}else if(maintenance.getMaintenanceWay()=="03"||maintenance.getMaintenanceWay().equals("03")){
					maintenance.setMaintenanceWay("上门");
				}
				List<MaintenanceWeb> maintenanceActions = jnpadMaintenanceService.findMaintenanceActionByMaintenanceId(maintenanceId);
				map.put("maintenanceWeb", m);
				map.put("maintenance", maintenance);
				map.put("maintenanceActions",maintenanceActions);
			}

			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(map, jsonConfig);
			return json.toString();
		}
		
		
		/**
		 * 修改维护计划
		 * @param form
		 * @param request
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/ipad/product/Maintenanceupdate.json")
		@JRadOperation(JRadOperation.CHANGE)
		public String update(@ModelAttribute MaintenanceForm form, HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			String maintenanceId= request.getParameter("maintenanceId");
				try {
					String createWay = jnpadMaintenanceService.findMaintenanceById(maintenanceId).getCreateWay();
					if(createWay!=null && createWay.equals(RiskCustomerCollectionEnum.system.toString())){
						returnMap.setSuccess(false);
						returnMap.addGlobalMessage(CustomerInforConstant.UPDATEERROR);
						JsonConfig jsonConfig = new JsonConfig();
						jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
						JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
						return json.toString();
						
					}
					
					String modifiedBy =request.getParameter("customerManagerId"); 
					Maintenance maintenance = new Maintenance();
					maintenance.setCustomerManagerId(request.getParameter("customerManagerId"));
					maintenance.setId(maintenanceId);
					maintenance.setCustomerId(request.getParameter("customerId"));
					maintenance.setAppId(request.getParameter("appId"));
					maintenance.setMaintenanceGoal(request.getParameter("maintenanceGoal"));
					maintenance.setMaintenanceWay(request.getParameter("createWay"));
					maintenance.setMaintenanceDay(request.getParameter("maintenanceDay"));
					maintenance.setModifiedBy(modifiedBy);
					maintenance.setId(maintenanceId);;
					boolean flag=jnpadMaintenanceService.updateMaintenance(maintenance);
					if(flag){
						returnMap.put(RECORD_ID, maintenance.getId());
						returnMap.addGlobalMessage(CHANGE_SUCCESS);
					}else{
						returnMap.setSuccess(false);
						returnMap.addGlobalMessage(CustomerInforConstant.UPDATEERROR);
					}
				}catch (Exception e) {
					returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
					returnMap.put(JRadConstants.SUCCESS, false);
				}
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
				return json.toString();
		}
}
