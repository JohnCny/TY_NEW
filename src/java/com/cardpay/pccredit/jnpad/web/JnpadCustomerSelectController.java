package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CIPERSONBASINFO;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.NODEAUDIT;
import com.cardpay.pccredit.jnpad.service.JnipadNodeService;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerSelectService;
import com.cardpay.pccredit.riskControl.model.CUSTOMERBLACKLIST;
import com.cardpay.pccredit.riskControl.service.CustormerBlackListService;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 客户信息查询
 * @author sealy
 *
 */
@Controller	
public class JnpadCustomerSelectController {
	@Autowired
	private JnipadNodeService nodeservice;
	@Autowired
	private JnpadCustomerSelectService customerSelectSercice;
	@Autowired
	private CustormerBlackListService cblservice;
	/**
	 * 根据证件号码查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectCustomerInfoByCardId.json", method = { RequestMethod.GET })
public String selectCustomerInfoByCardId(HttpServletRequest request){
	String chineseName = request.getParameter("chineseName");	
	String cardId = request.getParameter("cardId");
	String userId = request.getParameter("userId");
		List<CustomerInfo> customerList = customerSelectSercice.selectCustomerInfo(cardId,chineseName,userId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		Iterator<CustomerInfo> it = customerList.iterator(); 
		
		StringBuffer s=new StringBuffer();
	        while(it.hasNext()){  
	        CustomerInfo cus = it.next();
	        String CardType =cus.getCardType();
	        CardType =CardType.replace("0", "身份证");
	        CardType =CardType.replace("1", "军官证");
	        CardType =CardType.replace("2", "护照");
	        CardType =CardType.replace("3", "香港身份证");
	        CardType =CardType.replace("4", "澳门身份证");
	        CardType =CardType.replace("5", "台湾身份证");
	        s.append("<tr>"+"<td>"+cus.getChineseName()+"</td>"+
	        		"<td>"+CardType+"</td>"+
	        		"<td>"+cus.getCardId()+"</td>"+
                    "<td>"+cus.getTelephone()+"</td>");
	        } 
	     String ss = s.toString();
	     ss=ss.replace("null", "");
	     result.put("custInfo", ss);
	    JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	
	}
	
	/**
	 * 
	 * 客户原始信息查询
	 */
	 
		@ResponseBody
		@RequestMapping(value = "/ipad/product/selectAllCustomerInfoByCardId.json", method = { RequestMethod.GET })
		public String selectCustomerAllInfoByCardId(HttpServletRequest request){
			String customerInforId = RequestHelper.getStringValue(request, "customerInforId");
			CustomerFirsthendBase base = customerSelectSercice.findCustomerFirsthendById(customerInforId);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
			JSONObject json = JSONObject.fromObject(base, jsonConfig);
			return json.toString();
		}
	
	
	/**
	 * 
	 * 按id查找相应的客户基本信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectCustomerById.json", method = { RequestMethod.GET })
	public String selectCustomerFirsthendById(HttpServletRequest request){

		String id = RequestHelper.getStringValue(request, "id");
		CIPERSONBASINFO customer = customerSelectSercice.selectCustomerInfoById(id);
		
		
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(customer, jsonConfig);
		
		
		return json.toString();
	}
	
	
	/**
	 * 
	 * 按客户内码id查找相应的客户基本信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectCustomerByNm.json", method = { RequestMethod.GET })
	public String findCustomerFirsthendByNm(HttpServletRequest request){
		
		String custid = RequestHelper.getStringValue(request, "custid");
		CIPERSONBASINFO customer = customerSelectSercice.selectCustomerByNm(custid);
		
		
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(customer, jsonConfig);
		
		
		return json.toString();
		
		
		
	}
	
	/**
	 * 查询当前客户经理未申请荣耀卡的用户
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectByserIdOnRy.json", method = { RequestMethod.GET })
	public String selectByserIdOnRy(HttpServletRequest request){
		String ProductName="融耀卡";
		String userId=request.getParameter("userId");
		 List<CustomerInfo> result=customerSelectSercice.selectByserIdOnRy(userId,ProductName);
		 //int size=customerSelectSercice.selectByserIdOnRyCount(userId,ProductName);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("result", result);
		map.put("size", result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 查看当前客户经理负责产品审批的详情
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectProductUser.json", method = { RequestMethod.GET })
    public String selectProductUser(@ModelAttribute NODEAUDIT NODEAUDIT,HttpServletRequest request ){
		NODEAUDIT.setUser_id(request.getParameter("userId"));
		List<NODEAUDIT> result = nodeservice.selectProductUser(NODEAUDIT);
		int size =nodeservice.selectProductUserCount(NODEAUDIT);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("result", result);
		map.put("size", size);
	 	JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 查看当前产品的审核流程
	 * @param NODEAUDIT
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/selectAllProductUser.json", method = { RequestMethod.GET })
    public String selectAllProductUser(@ModelAttribute NODEAUDIT NODEAUDIT,HttpServletRequest request ){
		NODEAUDIT.setProduct_id(request.getParameter("productId"));
		List<NODEAUDIT> result = nodeservice.selectAllProductUser(NODEAUDIT);
		int size =nodeservice.selectAllProductUserCount(NODEAUDIT);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("result", result);
		map.put("size", size);
	 	JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 查询当前客户是否是黑名单客户
	 * @param CUSTOMERBLACKLIST
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customer/selectIsBlackList.json", method = { RequestMethod.GET })
    public String selectIsBlackList(@ModelAttribute CUSTOMERBLACKLIST CUSTOMERBLACKLIST,HttpServletRequest request ){
		String customerId=request.getParameter("id");
		int a=cblservice.selectCount(customerId);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("a", a);
	 	JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
}

