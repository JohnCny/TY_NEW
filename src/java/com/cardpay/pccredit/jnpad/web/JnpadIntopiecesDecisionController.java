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
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.IntoPiecesFilters;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CustomerSpUser;
import com.cardpay.pccredit.jnpad.model.ManagerInfoForm;
import com.cardpay.pccredit.jnpad.model.ProductAttributes;
import com.cardpay.pccredit.jnpad.service.JnpadCustormerSdwUserService;
import com.cardpay.pccredit.jnpad.service.JnpadIntopiecesDecisionService;
import com.cardpay.pccredit.jnpad.service.JnpadSpUserService;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.rongyaoka.service.ryIntoPiecesService;
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
	private JnpadCustormerSdwUserService SdwUserService;
	@Autowired
	private JnpadIntopiecesDecisionService jnpadIntopiecesDecisionService;
	@Autowired
	private IntoPiecesService intoPiecesService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ryIntoPiecesService rpservice;
	@Autowired
	private JnpadSpUserService UserService;
	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	//审核信息查询
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/csBrowse.json", method = { RequestMethod.GET })
	public String csBrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		
		filter.setChineseName(request.getParameter("name"));
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
	
	//初审下拉框选择客户经理信息
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/managerInfoi.json", method = { RequestMethod.GET })
	public String managerInfo(HttpServletRequest request){
		/*String userId=request.getParameter("userId");
		int x;
		int y;
		int z;
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ManagerInfoForm> result = jnpadIntopiecesDecisionService.findManagerInfo();
		for(int b=0;b<(result.size()-1);b++){
			if(result.get(b).getID().equals(userId)){
				
			}
		}
		x=(int)(Math.random()*(result.size()-1));
		y=(int)(Math.random()*(result.size()-1));
		z=(int)(Math.random()*(result.size()-1));
		for(int a=0;a<(result.size()-1);a++){
			if(x!=y && x!=z && y!=z){
				x=x;
				y=y;
				z=z;
			}else{
				x=(int)(Math.random()*(result.size()-1));
				y=(int)(Math.random()*(result.size()-1));
				z=(int)(Math.random()*(result.size()-1));
			}
		}
		String s="<option value = '"+result.get(x).getID()+"'>"+result.get(x).getDISPLAY_NAME()+"</option>";
		String s1="<option value = '"+result.get(y).getID()+"'>"+result.get(y).getDISPLAY_NAME()+"</option>";
		String s2="<option value = '"+result.get(z).getID()+"'>"+result.get(z).getDISPLAY_NAME()+"</option>";
	       map.put("manager", s);
	       map.put("manager1", s1);
	       map.put("manager2", s2);*/
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<ManagerInfoForm> result = jnpadIntopiecesDecisionService.findManagerInfo();
		Iterator<ManagerInfoForm> it = result.iterator(); 
		int i = 1;
		int j = 1;
		String  s="";
	        while(it.hasNext()){  
	        	ManagerInfoForm mana = it.next();
	        	if(mana.getEXTERNAL_ID()!=null ){
	        		s =s+"<option value = '"+mana.getID()+"'>"+mana.getEXTERNAL_ID()+mana.getDISPLAY_NAME()
		        	+"</option>";
	        	}else{
	        		s =s+"<option value = '"+mana.getID()+"'>"+mana.getDISPLAY_NAME()
		        	+"</option>";
	        	}
	        	
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
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/intopieces/SDHTZ.json", method = { RequestMethod.GET })
	public String SDHTZ(@ModelAttribute IntoPiecesFilters filter1,@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setNextNodeName(request.getParameter("进件初审"));
		filter.setUserId(request.getParameter("userId"));
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		List<IntoPieces> list=new ArrayList<IntoPieces>();
		//进件初审

		List<CustomerApplicationIntopieceWaitForm> result = jnpadIntopiecesDecisionService.findCustomerApplicationIntopieceDecison1(filter);
		for(int i=0;i<result.size();i++){
			IntoPieces IntoPieces=new IntoPieces();
			IntoPieces.setChineseName(result.get(i).getChineseName());
			IntoPieces.setCardId(result.get(i).getCardId());
			IntoPieces.setProductName(result.get(i).getProductName());
			IntoPieces.setApplyQuota(result.get(i).getApplyQuota());
			IntoPieces.setDisplayName(result.get(i).getDelayAuditUser());
			IntoPieces.setName1(request.getParameter("name"));
			IntoPieces.setName2("普通进件初审");
			list.add(i, IntoPieces);
		}
		
		//审贷决议
		List<IntoPieces> result1=SdwUserService.selectSDH1(request.getParameter("userId"));
		if(result1!=null){
		
				for(int a=0;a<result1.size();a++){
					List<CustomerSpUser> re1=UserService.selectUser1(result1.get(a).getId());
					if(re1.size()>2){
						CustomerSpUser name=UserService.selectUser(re1.get(0).getSpuserid());
						result1.get(a).setName1(name.getName1());
						CustomerSpUser name1=UserService.selectUser(re1.get(1).getSpuserid());
						result1.get(a).setName2(name1.getName1());
						CustomerSpUser name2=UserService.selectUser(re1.get(2).getSpuserid());
						result1.get(a).setName3(name2.getName1());
					}else{
						CustomerSpUser name=UserService.selectUser(re1.get(0).getSpuserid());
						result1.get(a).setName1(name.getName1());
						CustomerSpUser name1=UserService.selectUser(re1.get(1).getSpuserid());
						result1.get(a).setName2(name1.getName1());
					}
						
				}
	
			
			
			
		for(int i=0;i<result1.size();i++){
			result1.get(i).setName1(result1.get(i).getName1()+","+result1.get(i).getName2()+","+result1.get(i).getName3());
			result1.get(i).setName2("普通进件审贷决议");
			list.add(list.size(), result1.get(i));
		}}
		
		//荣耀卡初审
		filter.setNextNodeName("受理岗初审");
		List<CustomerApplicationIntopieceWaitForm> result2 = jnpadIntopiecesDecisionService.findCustomerApplicationIntopieceDecison1(filter);
		for(int i=0;i<result2.size();i++){
			IntoPieces IntoPieces=new IntoPieces();
			IntoPieces.setChineseName(result2.get(i).getChineseName());
			IntoPieces.setCardId(result2.get(i).getCardId());
			IntoPieces.setProductName(result2.get(i).getProductName());
			IntoPieces.setApplyQuota(result2.get(i).getApplyQuota());
			IntoPieces.setDisplayName(result2.get(i).getDelayAuditUser());
			IntoPieces.setName1(request.getParameter("name"));
			IntoPieces.setName2("受理岗初审");
			list.add(list.size(), IntoPieces);
		}
		//荣耀卡复审
		List<CustomerApplicationIntopieceWaitForm> result3=rpservice.findfsCustomer(request.getParameter("userId"), null, null, "报审岗复审");	
		for(int i=0;i<result3.size();i++){
			IntoPieces IntoPieces=new IntoPieces();
			IntoPieces.setChineseName(result3.get(i).getChineseName());
			IntoPieces.setCardId(result3.get(i).getCardId());
			IntoPieces.setProductName(result3.get(i).getProductName());
			IntoPieces.setApplyQuota(result3.get(i).getApplyQuota());
			IntoPieces.setDisplayName(result3.get(i).getDelayAuditUser());
			IntoPieces.setName1(request.getParameter("name"));
			IntoPieces.setName2("报审岗复审");
			list.add(list.size(), IntoPieces);
		}
		//荣耀卡终审
		filter1.setProductName("融耀卡");
		filter1.setUserId(request.getParameter("userId"));
		 List<IntoPiecesFilters> result4=customerApplicationIntopieceWaitService.findCustomerApplicationIntopieceDecisons1(filter1);
			for(int i=0;i<result4.size();i++){
				List<CustomerApplicationIntopieceWaitForm> spname=customerApplicationIntopieceWaitService.selectSpName(result4.get(i).getId(),request.getParameter("userId"));
				IntoPieces IntoPieces=new IntoPieces();
				IntoPieces.setChineseName(result4.get(i).getChineseName());
				IntoPieces.setCardId(result4.get(i).getCardId());
				IntoPieces.setProductName(result4.get(i).getProductName());
				IntoPieces.setApplyQuota(result4.get(i).getApplyQuota());
				IntoPieces.setDisplayName(result4.get(i).getDisplayName());
				IntoPieces.setName1(request.getParameter("name")+","+spname.get(0).getDelayAuditUser());
				IntoPieces.setName2("融耀卡终审");
				list.add(list.size(), IntoPieces);
			}
		 
		 
		 
		 
		 map.put("result", list);
		map.put("size", list.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
		
	}
	
	
	
	
}
