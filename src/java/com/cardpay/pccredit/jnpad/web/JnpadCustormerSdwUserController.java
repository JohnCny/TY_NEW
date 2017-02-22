package com.cardpay.pccredit.jnpad.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.web.AddIntoPiecesForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.dao.JnpadCustormerSdwUserDao;
import com.cardpay.pccredit.jnpad.model.CustomerSpUser;
import com.cardpay.pccredit.jnpad.model.CustormerSdwUser;
import com.cardpay.pccredit.jnpad.model.JnpadCsSdModel;
import com.cardpay.pccredit.jnpad.service.JnpadCustormerSdwUserService;
import com.cardpay.pccredit.jnpad.service.JnpadSpUserService;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.jrad.base.database.id.IDGenerator;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadCustormerSdwUserController {
	
	@Autowired
	private JnpadCustormerSdwUserService SdwUserService;
	@Autowired
	private JnpadSpUserService UserService;
	/**
	 * 添加审贷会成员
	 * @param CustormerSdwUser
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/CustormerSdwUser/insert.json", method = { RequestMethod.GET })
	public String addIntopieces(@ModelAttribute CustormerSdwUser CustormerSdwUser,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		CustormerSdwUser.setSDWUSER1(request.getParameter("userId1"));
		CustormerSdwUser.setSDWUSER2(request.getParameter("userId2"));
		CustormerSdwUser.setSDWUSER3(request.getParameter("userId3"));
		CustormerSdwUser.setPID(request.getParameter("pid"));
		CustormerSdwUser.setCID(request.getParameter("cid"));
		CustormerSdwUser.setTIME(new Date());
		String cid=null;
		if(null==cid){
			cid=UUID.randomUUID().toString();
		}
		CustormerSdwUser.setId(cid);
		int a=SdwUserService.insertCustormerSdwUser(CustormerSdwUser);
		if(a>0){
			map.put("message", "添加审贷委成功");
		}else{
			map.put("message", "添加审贷委失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 初审成功添加
	 * @param CustormerSdwUser
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/CustormerSdwUser/insertCS.json", method = { RequestMethod.GET })
	public String insertCS(@ModelAttribute CustomerSpUser CustomerSpUser,@ModelAttribute AppManagerAuditLog AppManagerAuditLog,@ModelAttribute CustormerSdwUser CustormerSdwUser,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		List<CustomerSpUser> list=new ArrayList<CustomerSpUser>();
		AppManagerAuditLog result=SdwUserService.selectaId(request.getParameter("cid"),request.getParameter("pid"));
		AppManagerAuditLog.setId(IDGenerator.generateID());
		AppManagerAuditLog.setApplicationId(result.getApplicationId());
		AppManagerAuditLog.setAuditType("1");
		AppManagerAuditLog.setExamineLv(request.getParameter("lv"));
		AppManagerAuditLog.setQx(request.getParameter("qx"));
		AppManagerAuditLog.setExamineAmount(request.getParameter("decisionAmount"));
		AppManagerAuditLog.setUserId_1(request.getParameter("userId1"));
		AppManagerAuditLog.setUserId_2(request.getParameter("userId2"));
		AppManagerAuditLog.setUserId_3(request.getParameter("userId3"));
		AppManagerAuditLog.setUserId_4(request.getParameter("userId"));
		int a=SdwUserService.insertCsJl(AppManagerAuditLog);
		if(a>0){
			CustomerSpUser.setId(IDGenerator.generateID());
			CustomerSpUser.setCid(request.getParameter("cid"));
			CustomerSpUser.setPid(request.getParameter("pid"));
			CustomerSpUser.setCapid(result.getApplicationId());
			CustomerSpUser.setTime(new Date());
			CustomerSpUser.setStatus("0");
			CustomerSpUser c=new CustomerSpUser();
			c.setSpuserid(request.getParameter("user_Id1"));
			list.add(0, c);
			CustomerSpUser c1=new CustomerSpUser();
			c1.setSpuserid(request.getParameter("user_Id2"));
			list.add(1, c1);
			CustomerSpUser c2=new CustomerSpUser();
			c2.setSpuserid(request.getParameter("user_Id3"));
			list.add(2, c2);
			for(int sd=0;sd<list.size();sd++){
				CustomerSpUser.setSpuserid(list.get(sd).getSpuserid());
				int b=UserService.addSpUser(CustomerSpUser);
				if(b>0 && sd==2){
					map.put("message", "提交成功");
				}else if(b<=0){
					map.put("message", "提交失败");
				}
			}
			/*CustormerSdwUser.setSDWUSER1(request.getParameter("user_Id1"));
			CustormerSdwUser.setSDWUSER2(request.getParameter("user_Id2"));
			CustormerSdwUser.setSDWUSER3(request.getParameter("user_Id3"));
			CustormerSdwUser.setTIME(new Date());
			CustormerSdwUser.setCAPID(result.getApplicationId());
			CustormerSdwUser.setCID(request.getParameter("cid"));
			CustormerSdwUser.setPID(request.getParameter("pid"));
			String pid=null;
			if(null==pid){
				pid=UUID.randomUUID().toString();
			}
			CustormerSdwUser.setId(pid);
			int b=SdwUserService.insertCustormerSdwUser(CustormerSdwUser);
			if(b>0){
				map.put("message", "提交成功");
			}else{
				map.put("message", "提交失败");
			}*/
		}else{
			map.put("message", "提交失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 当前审贷委的审贷
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/selectSDHSP.json", method = { RequestMethod.GET })
	public String selectSDHSP(@ModelAttribute CustormerSdwUser CustormerSdwUser,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String userId=request.getParameter("userId");
		List<IntoPieces> result=SdwUserService.selectSDH(userId);
		map.put("result", result);
		map.put("size", result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}
	
	/**
	 * 初审信息
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/selectCsXx.json", method = { RequestMethod.GET })
	public String selectCsXx(@ModelAttribute CustormerSdwUser CustormerSdwUser,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String id=request.getParameter("id");
		AppManagerAuditLog result=SdwUserService.selectCSJLA(id);
		map.put("result", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}
	
	
	/**
	 * 审贷决议
	 * @param CustormerSdwUser
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/insertsdjy.json", method = { RequestMethod.GET })
	public String insertsdjy(@ModelAttribute RiskCustomer RiskCustomer,@ModelAttribute CustormerSdwUser CustormerSdwUser,@ModelAttribute IntoPieces IntoPieces,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		CustormerSdwUser.setSDJE(request.getParameter("sxed"));
		CustormerSdwUser.setSDTIME(new Date());
		CustormerSdwUser.setSDQX(request.getParameter("qx"));
		CustormerSdwUser.setSDWJLY(request.getParameter("userId"));
		CustormerSdwUser.setSDWUSER1YJ(request.getParameter("cyUser1"));
/*		CustormerSdwUser.setSDWUSER2YJ(request.getParameter("cyUser2"));
		CustormerSdwUser.setSDWUSER3YJ(request.getParameter("fdUser"));*/
		CustormerSdwUser.setLV(request.getParameter("lv"));
		CustormerSdwUser.setCAPID(request.getParameter("id"));
	/*	int a=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
		if(a>0){*/
			if(request.getParameter("status").equals("APPROVE")){
				IntoPieces.setFinal_approval(request.getParameter("sxed"));
				IntoPieces.setStatus("approved");
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setCreatime(new Date());
				int b=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				if(b>0){
					map.put("message", "提交成功");
				}else{
					map.put("message", "提交失败");
				}
			}else if(request.getParameter("status").equals("REJECTAPPROVE")){
				int a1=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
				IntoPieces.setStatus("refuse");
				IntoPieces.setCreatime(new Date());
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setUserId(request.getParameter("userId"));
				IntoPieces.setREFUSAL_REASON(request.getParameter("decisionRefusereason"));
				int c=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				if(c>0){
					int d=SdwUserService.updateCustormerProSdwUser(IntoPieces);
					if(d>0){
						RiskCustomer.setCustomerId(request.getParameter("customerId"));
						RiskCustomer.setProductId(request.getParameter("productId"));
						RiskCustomer.setRiskCreateType("manual");
						RiskCustomer.setRefuseReason(request.getParameter("decisionRefusereason"));
						RiskCustomer.setCREATED_TIME(new Date());
						RiskCustomer.setCustManagerId(request.getParameter("userId"));
						String pid=null;
						if(null==pid){
							pid=UUID.randomUUID().toString();
						}
						RiskCustomer.setId(pid);
						int e=SdwUserService.insertRiskSdwUser(RiskCustomer);
						if(e>0){
							map.put("message", "提交成功");
						}else{
							map.put("message", "提交失败");
						}
					}else{
						map.put("message", "提交失败");
					}
				}else{
					map.put("message", "提交失败");
				}
			}else if(request.getParameter("status").equals("RETURNAPPROVE")){
				int a2=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
				IntoPieces.setStatus("nopass_replenish");
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setFallBackReason(request.getParameter("decisionRefusereason"));
				IntoPieces.setUserId(request.getParameter("userId"));
				IntoPieces.setCreatime(new Date());
				int c=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				if(c>0){
					int d=SdwUserService.updateCustormerProSdwUser(IntoPieces);
					if(d>0){
						map.put("message", "提交成功");	
					}else{
						map.put("message", "提交失败");
					}
				}
			}
	/*	}else{
			map.put("message", "提交失败");
		}*/
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}
	
	
	/**
	 * 当前审贷委审批
	 * @param RiskCustomer
	 * @param CustormerSdwUser
	 * @param IntoPieces
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/insertsdjy1.json", method = { RequestMethod.GET })
	public String insertsdjy1(@ModelAttribute CustomerSpUser CustomerSpUser,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		CustomerSpUser.setSpje(request.getParameter("sxed"));
		CustomerSpUser.setSptime(new Date());
		CustomerSpUser.setSpqx(request.getParameter("qx"));
		CustomerSpUser.setSpuserid(request.getParameter("userId"));
		CustomerSpUser.setBeizhu(request.getParameter("cyUser1"));
		CustomerSpUser.setSplv(request.getParameter("lv"));
		CustomerSpUser.setCapid(request.getParameter("id"));
		if(request.getParameter("status").equals("APPROVE")){
			CustomerSpUser.setStatus("1");
		}else if(request.getParameter("status").equals("REJECTAPPROVE")){
			CustomerSpUser.setStatus("2");
		}else if(request.getParameter("status").equals("RETURNAPPROVE")){
			CustomerSpUser.setStatus("3");
		}
		int a=UserService.addSpUser1(CustomerSpUser);
		if(a>0){
			map.put("message", "提交成功");	
		}else{
			map.put("message", "提交失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 查询初审审贷纪要
	 * @param JnpadCsSdModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/findCsSd.json", method = {RequestMethod.GET })
	public String findCsSd(@ModelAttribute JnpadCsSdModel JnpadCsSdModel,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String id=request.getParameter("id");
		JnpadCsSdModel result=SdwUserService.findCsSd(id);
		map.put("result", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}
	/**
	 * 拒绝审贷纪要
	 * @param JnpadCsSdModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/findCsSdRefuse.json", method = {RequestMethod.GET })
	public String findCsSdRefuse(@ModelAttribute JnpadCsSdModel JnpadCsSdModel,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String id=request.getParameter("id");
		JnpadCsSdModel result=SdwUserService.findCsSdRefuse(id);
		JnpadCsSdModel result1=SdwUserService.findUser(id);
		map.put("result", result);
		map.put("result1", result1);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}
	
	/**
	 * 回退审贷纪要
	 * @param JnpadCsSdModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/findCsSdBlack.json", method = {RequestMethod.GET })
	public String findCsSdBlack(@ModelAttribute JnpadCsSdModel JnpadCsSdModel,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String id=request.getParameter("id");
		JnpadCsSdModel result=SdwUserService.findCsSdBlack(id);
		JnpadCsSdModel result1=SdwUserService.findUser(id);
		map.put("result", result);
		map.put("result1", result1);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}
	/**
	 *进件审贷通知
	 * @param JnpadCsSdModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/selectSDH1.json", method = {RequestMethod.GET })
	public String selectSDH1(@ModelAttribute JnpadCsSdModel JnpadCsSdModel,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String id=request.getParameter("userId");
		List<IntoPieces> result=SdwUserService.selectSDH1(id);
		map.put("result", result);
		map.put("size", result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}
	
	/**
	 * 最终审批
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/selectSpUser.json", method = { RequestMethod.GET })
	public String selectSpUser( HttpServletRequest request) {
		List<CustomerSpUser> c=new ArrayList<CustomerSpUser>();
		List<AppManagerAuditLog> d=new ArrayList<AppManagerAuditLog>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<CustomerSpUser>result=UserService.findspUser();
		try {
			if(result!=null){
				for(int a=0;a<result.size();a++){
					List<CustomerSpUser> result1=UserService.findspUser2(result.get(a).getCapid());
					try {
						if(result1==null){
							
						}
						else{
							try {
								if(result1.get(0).getStatus().equals("0") && result1.get(1).getStatus().equals("0") && result1.get(2).getStatus().equals("0")){
									CustomerSpUser CustomerSpUser=new CustomerSpUser();
									CustomerSpUser.setCapid(result.get(a).getCapid());
									c.add(a, CustomerSpUser);
								}else{
									result1.remove(a);
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(int b=0;b<c.size();b++){
					AppManagerAuditLog result2=SdwUserService.selectCSJLA(c.get(b).getCapid());
					if(result2!=null){
						d.add(b, result2);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("size", d.size());
		map.put("result", d);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		c.clear();
		d.clear();
		return json.toString();
	}
	
	/**
	 * 查找当前进件的审贷会信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/selectSpUser1.json", method = { RequestMethod.GET })
	public String selectSpUser1( HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CustomerSpUser> result=UserService.findspUser2(request.getParameter("id"));
		for(int a=0;a<result.size();a++){
			CustomerSpUser name=UserService.selectUser(result.get(a).getSpuserid());
			result.get(a).setName1(name.getName1());
		}
		map.put("size", result.size());
		map.put("result",result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}

}