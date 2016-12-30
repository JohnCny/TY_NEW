package com.cardpay.pccredit.jnpad.web;

import java.util.ArrayList;
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
import com.cardpay.pccredit.jnpad.model.JBUser;
import com.cardpay.pccredit.jnpad.model.NODEAUDIT;
import com.cardpay.pccredit.jnpad.service.JnIpadJBUserService;
import com.cardpay.pccredit.jnpad.service.JnipadNodeService;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerSelectService;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
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
	@Autowired
	private JnIpadJBUserService JnIpadJBUser;
	@Autowired
	private com.cardpay.pccredit.manager.service.ManagerBelongMapService ManagerBelongMapService;
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
	
	/**
	 * 查询所有区域经理的Id
	 * @param CUSTOMERBLACKLIST
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customer/selectqujl.json", method = { RequestMethod.GET })
    public String selectqujl(HttpServletRequest request ){
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		List list=new ArrayList();
		String ID="";
		Integer b=0;
		String userId=request.getParameter("userId");
		String parentId="100000";
		//确认当前客户经理是否为区域经理
		List<ManagerBelongMapForm> result=ManagerBelongMapService.findAllqyjl(parentId);
		for(int a=0;a<result.size();a++){
			if(result.get(a).getId().equals(userId)){
				b=1;
				ID=result.get(a).getId();
			}
		}
		if(b==1){
			List<JBUser>depart=JnIpadJBUser.selectDepartUser(ID);
			map.put("depart", depart);
			map.put("size", depart.size());
		}else{
			//确认当前客户经理是否为小组长
			for(int i=0;i<result.size();i++){
				List<ManagerBelongMapForm> result1=ManagerBelongMapService.findxzz(result.get(i).getId());
				for(int c=0;c<result1.size();c++){
					list.add(result1.get(c).getId());
				}
			}
			for(int d=0;d<list.size();d++){
				if(list.get(d).equals(userId)){
					b=2;
					ID=list.get(d).toString();
				}
			}
			if(b==2){
				List<JBUser>depart1=JnIpadJBUser.selectDepart1(ID);
				map.put("result", depart1);
				map.put("size", depart1.size());
			}
		}
		map.put("b", b);
	 	JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	
	/**
	 * 根据小组Id查询该组成员
	 * @param CUSTOMERBLACKLIST
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customer/selectDUser.json", method = { RequestMethod.GET })
    public String selectDUser(HttpServletRequest request ){
		String ID=request.getParameter("id");
		List<JBUser>result=JnIpadJBUser.findDe(ID);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("result", result);
		map.put("size", result.size());
	 	JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 查询选中组员的所有进件详情
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customer/selectAllcustormerId.json", method = { RequestMethod.GET })
    public String selectAllcustormerId(HttpServletRequest request ){
		//拒绝数量
		Integer resufeCount=0;
		//未申请数量
		Integer Nosq=0;
		//成功数量
		Integer successCount=0;
		//待审批 数量
		Integer NospCount=0;
		Integer BackCount=0;
		String userId=request.getParameter("userId");
		List<CustomerInfo>result=customerSelectSercice.selectAllcustormerId(userId);
		for(int a=0;a<result.size();a++){
			resufeCount+=customerSelectSercice.findCount(result.get(a).getId(), "refuse","nopss");
			successCount+=customerSelectSercice.findCount(result.get(a).getId(), "approved","end");
			NospCount+=customerSelectSercice.findCount(result.get(a).getId(), "audit","");
			BackCount+=customerSelectSercice.findCount(result.get(a).getId(), "returnedToFirst","nopass_replenish");
		}
		Nosq=customerSelectSercice.findNoSQCount(userId);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("resufeCount", resufeCount);
		map.put("Nosq", Nosq);
		map.put("successCount", successCount);
		map.put("NospCount", NospCount);
		map.put("BackCount", BackCount);
	 	JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
}

