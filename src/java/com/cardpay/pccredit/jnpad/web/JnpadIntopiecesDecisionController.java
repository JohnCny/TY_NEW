package com.cardpay.pccredit.jnpad.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcessForm;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.ManagerInfoForm;
import com.cardpay.pccredit.jnpad.model.ProductAttributes;
import com.cardpay.pccredit.jnpad.service.JnpadIntopiecesDecisionService;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadIntopiecesDecisionController extends BaseController{
	
	@Autowired
	private JnpadIntopiecesDecisionService jnpadIntopiecesDecisionService;
	@Autowired
	private IntoPiecesService intoPiecesService;
	@Autowired
	private ProductService productService;
	
	//审核信息查询
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/csBrowse.json", method = { RequestMethod.GET })
	public String csBrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		
		
		filter.setNextNodeName(request.getParameter("nextNodeName"));
		filter.setUserId(request.getParameter("userId"));
		QueryResult<CustomerApplicationIntopieceWaitForm> result = jnpadIntopiecesDecisionService.findCustomerApplicationIntopieceDecison(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
		
	}
	
	
	//显示初审信息
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/csInfo.json", method = { RequestMethod.GET })
	public String csInfo(HttpServletRequest request){
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
//		List<ManagerInfoForm> result = jnpadIntopiecesDecisionService.findManagerInfo();
//		StringBuffer s=new StringBuffer();
//		Iterator<ManagerInfoForm> it = result.iterator(); 
//        while(it.hasNext()){  
//        ManagerInfoForm manager = it.next();
//        s.append("<option value = '"+manager.getID()+"'>"+manager.getEXTERNAL_ID()+manager.getDISPLAY_NAME()+"</option>"); 
//        } 
//        String ss = s.toString();
//        int size = result.size();  
        String appId = request.getParameter("appId");
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		map.put("customerApplicationInfo", customerApplicationInfo);
		map.put("producAttribute", producAttribute);
		map.put("customerInfor",customerInfor);
//		map.put("managerInfo", result);
		map.put("prodCreditRange",producAttribute.getProdCreditRange());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
		
	}
	
	//下拉框选择客户经理信息
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/managerInfoi.json", method = { RequestMethod.GET })
	public String managerInfo(HttpServletRequest request){
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ManagerInfoForm> result = jnpadIntopiecesDecisionService.findManagerInfo();
		Iterator<ManagerInfoForm> it = result.iterator(); 
		int i = 1;
		int j = 1;
		String  s="";
	        while(it.hasNext()){  
	        	ManagerInfoForm mana = it.next();
	        	s =s+"<option value = '"+mana.getID()+"'>"+mana.getEXTERNAL_ID()+mana.getDISPLAY_NAME()
	        	+"</option>";
	        	
	        }
	       map.put("manager", s);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map,jsonConfig);
		return json.toString();
	}
	
	//下拉框选择审贷老师信息
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/teacherInfo.json", method = { RequestMethod.GET })
	public String teacherInfo(HttpServletRequest request){
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ManagerInfoForm> result = jnpadIntopiecesDecisionService.findteacherInfo();
		Iterator<ManagerInfoForm> it = result.iterator(); 
		int i = 1;
		int j = 1;
		String  s="";
		while(it.hasNext()){  
			ManagerInfoForm mana = it.next();
			s =s+"<option value = '"+mana.getID()+"'>"+mana.getDISPLAY_NAME()
			+"</option>";
			
		}
		map.put("manager", s);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map,jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 提交信息
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/updateAll.json")
	@JRadOperation(JRadOperation.APPROVE)
	public String update(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		if (returnMap.isSuccess()) {
			try {
				jnpadIntopiecesDecisionService.updateCustomerApplicationProcessBySerialNumber(request);
				returnMap.put("message","提交成功");
			} catch (Exception e) {
				returnMap.put("message","提交失败");
			}
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();
	}
	
	
//	//审贷决议
//	@ResponseBody
//	@RequestMapping(value = "/ipad/intopieces/sdBrowse.json", method = { RequestMethod.GET })
//	public String sdbrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
//		filter.setRequest(request);
//		String userId = request.getParameter("userId");
//		filter.setNextNodeName("审贷决议");
//		filter.setUserId(userId);
//		QueryResult<CustomerApplicationIntopieceWaitForm> result = jnpadIntopiecesDecisionService.findCustomerApplicationIntopieceDecison(filter);
//		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
//		JSONObject json = JSONObject.fromObject(result, jsonConfig);
//		return json.toString();
//	}
//	
	//显示审议决议
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/sdjy.json", method = { RequestMethod.GET })
	public String input_decision(HttpServletRequest request) {
		
//		List<ManagerInfoForm> result = jnpadIntopiecesDecisionService.findManagerInfo();
//		StringBuffer s=new StringBuffer();
//		Iterator<ManagerInfoForm> it = result.iterator(); 
//        while(it.hasNext()){  
//        ManagerInfoForm manager = it.next();
//        s.append("<option value = '"+manager.getID()+"'>"+manager.getEXTERNAL_ID()+manager.getDISPLAY_NAME()+"</option>"); 
//        } 
//        String ss = s.toString();
//		int size = result.size();
		String appId = request.getParameter("appId");
		List<ProductAttributes> productList = jnpadIntopiecesDecisionService.findProductList();
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
//		CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		AppManagerAuditLog appManagerAuditLog = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"1");
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("customerApplicationInfo", customerApplicationInfo);
		map.put("producAttribute", producAttribute);
//		map.put("customerApplicationProcess", processForm);
		map.put("appManagerAuditLog", appManagerAuditLog);
		map.put("custManagerId", customerInfor.getUserId());
		map.put("prodCreditRange",producAttribute.getProdCreditRange());
		map.put("productList",productList);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	

	
	
	//显示部门审批
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/bzsp.json", method = { RequestMethod.GET })
	public String bumen_decision(HttpServletRequest request) {
		String appId = request.getParameter("appId");
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
//		CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		AppManagerAuditLog appManagerAuditLog1 = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"1");
		AppManagerAuditLog appManagerAuditLog2 = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"2");
		List<ProductAttributes> productList = jnpadIntopiecesDecisionService.findProductList();
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("customerApplicationInfo", customerApplicationInfo);
		map.put("productList",productList);
		map.put("producAttribute", producAttribute);
//		map.put("customerApplicationProcess", processForm);
		map.put("appManagerAuditLog1", appManagerAuditLog1);
		map.put("appManagerAuditLog2", appManagerAuditLog2);
		map.put("custManagerId", customerInfor.getUserId());
		map.put("prodCreditRange",producAttribute.getProdCreditRange());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	//显示零售部业务负责人审批
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/lsbfzr.json", method = { RequestMethod.GET })
	public String lingshou_decision(HttpServletRequest request) {
		String appId = request.getParameter("appId");
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
//		CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		AppManagerAuditLog appManagerAuditLog1 = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"1");
		AppManagerAuditLog appManagerAuditLog2 = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"2");
		AppManagerAuditLog appManagerAuditLog3 = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"3");
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		List<ProductAttributes> productList = jnpadIntopiecesDecisionService.findProductList();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("productList",productList);
		map.put("customerApplicationInfo", customerApplicationInfo);
		map.put("producAttribute", producAttribute);
//		map.put("customerApplicationProcess", processForm);
		map.put("appManagerAuditLog1", appManagerAuditLog1);
		map.put("appManagerAuditLog2", appManagerAuditLog2);
		map.put("appManagerAuditLog3", appManagerAuditLog3);
		map.put("custManagerId", customerInfor.getUserId());
		map.put("prodCreditRange",producAttribute.getProdCreditRange());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	//显示行长审批
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/hzspjl.json", method = { RequestMethod.GET })
	public String hangzhang_decision(HttpServletRequest request) {
		String appId = request.getParameter("appId");
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
//		CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		AppManagerAuditLog appManagerAuditLog1 = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"1");
		AppManagerAuditLog appManagerAuditLog2 = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"2");
		AppManagerAuditLog appManagerAuditLog3 = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"3");
		AppManagerAuditLog appManagerAuditLog4 = jnpadIntopiecesDecisionService.findAppManagerAuditLog(appId,"4");
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		List<ProductAttributes> productList = jnpadIntopiecesDecisionService.findProductList();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("productList",productList);
		map.put("customerApplicationInfo", customerApplicationInfo);
		map.put("producAttribute", producAttribute);
//		map.put("customerApplicationProcess", processForm);
		map.put("appManagerAuditLog1", appManagerAuditLog1);
		map.put("appManagerAuditLog2", appManagerAuditLog2);
		map.put("appManagerAuditLog3", appManagerAuditLog3);
		map.put("appManagerAuditLog4", appManagerAuditLog4);
		map.put("custManagerId", customerInfor.getUserId());
		map.put("prodCreditRange",producAttribute.getProdCreditRange());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
//	//提交审贷决议
//	@ResponseBody
//	@RequestMapping(value = "update.json", method = { RequestMethod.GET })
//	public JRadReturnMap update(@ModelAttribute CustomerApplicationInfo customerApplicationInfo,HttpServletRequest request) {
//		JRadReturnMap returnMap = new JRadReturnMap();
//		try {
//			customerApplicationInfo.setActualQuote(customerApplicationInfo.getDecisionAmount());
//			jnpadIntopiecesDecisionService.update(customerApplicationInfo,request);
//			returnMap.addGlobalMessage(CHANGE_SUCCESS);
//		} catch (Exception e) {
//			return WebRequestHelper.processException(e);
//		}
//
//		return returnMap;
//	}

	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/productInfo.json", method = { RequestMethod.GET })
	public String productInfo(HttpServletRequest request) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ProductAttributes> result = jnpadIntopiecesDecisionService.findProductList();
		map.put("result", result);
		map.put("size", result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
		
	}
}
