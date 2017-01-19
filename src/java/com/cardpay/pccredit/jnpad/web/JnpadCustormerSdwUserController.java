package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
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
import com.cardpay.pccredit.jnpad.model.CustormerSdwUser;
import com.cardpay.pccredit.jnpad.model.JnpadCsSdModel;
import com.cardpay.pccredit.jnpad.service.JnpadCustormerSdwUserService;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadCustormerSdwUserController {
	
	@Autowired
	private JnpadCustormerSdwUserService SdwUserService;
	
	
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
	public String insertCS(@ModelAttribute AppManagerAuditLog AppManagerAuditLog,@ModelAttribute CustormerSdwUser CustormerSdwUser,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		AppManagerAuditLog result=SdwUserService.selectaId(request.getParameter("cid"),request.getParameter("pid"));
		String cid=null;
		if(null==cid){
			cid=UUID.randomUUID().toString();
		}
		AppManagerAuditLog.setId(cid);
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
			CustormerSdwUser.setSDWUSER1(request.getParameter("user_Id1"));
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
			}
		}else{
			map.put("message", "提交成功");
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
		CustormerSdwUser.setSDWUSER2YJ(request.getParameter("cyUser2"));
		CustormerSdwUser.setSDWUSER3YJ(request.getParameter("fdUser"));
		CustormerSdwUser.setLV(request.getParameter("lv"));
		CustormerSdwUser.setCAPID(request.getParameter("id"));
		int a=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
		if(a>0){
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
}