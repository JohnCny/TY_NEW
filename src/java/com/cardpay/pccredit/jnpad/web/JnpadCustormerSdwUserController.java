package com.cardpay.pccredit.jnpad.web;

import java.text.SimpleDateFormat;
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
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
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
import com.cardpay.pccredit.rongyaoka.model.rymodel;
import com.cardpay.pccredit.rongyaoka.service.ryServer;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.database.id.IDGenerator;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class JnpadCustormerSdwUserController {

	@Autowired
	private ProcessService processService;
	@Autowired
	private JnpadCustormerSdwUserService SdwUserService;
	@Autowired
	private JnpadSpUserService UserService;
	@Autowired
	private ryServer ryserver;
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
			String auditType = request.getParameter("auditType");
			String productId = request.getParameter("productId");


			CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
			CustomerApplicationProcess customerApplicationProcess = new CustomerApplicationProcess();
			String loginId = request.getParameter("userId");
			String serialNumber = request.getParameter("serialNumber");
			String examineAmount = request.getParameter("decisionAmount");
			String applicationStatus = "通过";
			
			String applicationId = request.getParameter("id");
			UserService.examine(applicationId,serialNumber, loginId, applicationStatus, examineAmount,productId,auditType);
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
		List<IntoPieces> IntoPieces =new ArrayList<IntoPieces>();
		List<CustomerSpUser>sp=UserService.findUserResult(userId);
		if(sp.size()>0){
			for(int i=sp.size()-1;i>=0;i--){
				List<CustomerSpUser> sdw=UserService.findUserResult1(sp.get(i).getCapid());
				if(sdw.size()>0){
				if(sdw.size()==3){
					if(sdw.get(0).getStatus().equals("2") || sdw.get(0).getStatus().equals("3") || sdw.get(1).getStatus().equals("2") || sdw.get(1).getStatus().equals("3")|| sdw.get(2).getStatus().equals("2") || sdw.get(2).getStatus().equals("3")){
						sp.remove(i);
					}
				}else 	if(sdw.size()==2){
					if(sdw.get(0).getStatus().equals("2") || sdw.get(0).getStatus().equals("3") || sdw.get(1).getStatus().equals("2") || sdw.get(1).getStatus().equals("3")){
						sp.remove(i);
					}
				}else if(sdw.size()==1){
					if(sdw.get(0).getStatus().equals("2") || sdw.get(0).getStatus().equals("3")){
						sp.remove(i);
					}
				}
				}
				
			}
		for(int a=0;a<sp.size();a++){
			IntoPieces result=SdwUserService.selectSDH(userId,sp.get(a).getCapid());
			IntoPieces.add(a, result);
		}
		}
		map.put("result", IntoPieces);
		map.put("size", IntoPieces.size());
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
		CustormerSdwUser.setSDWUSER2YJ(request.getParameter("decisionRefusereason"));
		CustormerSdwUser.setPID(request.getParameter("productId"));
		CustormerSdwUser.setCAPID(request.getParameter("id"));
		CustormerSdwUser.setCID(request.getParameter("customerId"));
		int a=SdwUserService.insertCustormerSdwUser1(CustormerSdwUser);
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
						RiskCustomer.setCustManagerId(request.getParameter("user_id"));
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
	 * 当前审贷委审批
	 * @param RiskCustomer
	 * @param CustormerSdwUser
	 * @param IntoPieces
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/insertsdjy1.json", method = { RequestMethod.GET })
	public String insertsdjy1(@ModelAttribute CustomerSpUser CustomerSpUser,@ModelAttribute RiskCustomer RiskCustomer,@ModelAttribute IntoPieces IntoPieces,HttpServletRequest request) {
		CustormerSdwUser CustormerSdwUser=new CustormerSdwUser();
		CustormerSdwUser.setSDJE(request.getParameter("sxed"));
		CustormerSdwUser.setSDTIME(new Date());
		CustormerSdwUser.setSDQX(request.getParameter("qx"));
		CustormerSdwUser.setSDWJLY(request.getParameter("userId"));
		CustormerSdwUser.setSDWUSER1YJ(request.getParameter("cyUser1"));
		CustormerSdwUser.setLV(request.getParameter("lv"));
		CustormerSdwUser.setCAPID(request.getParameter("id"));
		CustormerSdwUser.setSDWUSER2YJ(request.getParameter("decisionRefusereason"));
		CustormerSdwUser.setPID(request.getParameter("productId"));
		CustormerSdwUser.setCAPID(request.getParameter("id"));
		CustormerSdwUser.setCID(request.getParameter("customerId"));
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		CustomerSpUser.setSpje(request.getParameter("sxed"));
		CustomerSpUser.setSptime(new Date());
		CustomerSpUser.setSpqx(request.getParameter("qx"));
		CustomerSpUser.setSpuserid(request.getParameter("userId"));
		CustomerSpUser.setBeizhu(request.getParameter("cyUser1"));
		CustomerSpUser.setSplv(request.getParameter("lv"));
		CustomerSpUser.setCapid(request.getParameter("id"));
		CustomerSpUser.setJlyys(request.getParameter("decisionRefusereason"));
		if(request.getParameter("status").equals("APPROVE")){
			CustomerSpUser.setStatus("1");
		}else if(request.getParameter("status").equals("REJECTAPPROVE")){
			CustomerSpUser.setStatus("2");
		}else if(request.getParameter("status").equals("RETURNAPPROVE")){
			CustomerSpUser.setStatus("3");
		}
		 int a=UserService.addSpUser1(CustomerSpUser);
		//如果进件审贷为拒绝
		 if(request.getParameter("status").equals("REJECTAPPROVE")){
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
					RiskCustomer.setCustManagerId(request.getParameter("user_id"));
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
		}
		 //如果审贷为退回
		 else if(request.getParameter("status").equals("RETURNAPPROVE")){
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
		}else if(request.getParameter("status").equals("APPROVE")){
			 //如果审贷为通过
			//a.查询是否有审贷委审批过，并且核对审贷委审贷的金额，利息，期限 是否与当前审贷委相同
			 List<CustomerSpUser> result=UserService.findSpHjy(request.getParameter("id"),request.getParameter("userId"));
			 //如果有
			 if(result.size()>0 ){
				 //如果只有一位审贷委审批，对比参数
				 if(result.size()==1){
					 //如果都相同不处理
					 if(result.get(0).getSpje().equals(request.getParameter("sxed")) && 
							 result.get(0).getSplv().equals(request.getParameter("lv")) &&
							 result.get(0).getSpqx().equals(request.getParameter("qx"))){
							map.put("message", "提交成功");	
					 }else{
						 //如果不相同则删除审贷会记录
						 UserService.deleteSpUser(request.getParameter("id"));
							map.put("message", "你审批的金额利息之类与前审贷委不同，需要重新审批");	
						 /*
						 //如果不相同，默认为退回
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
						
					 */}
				 }
				 else if(result.size()==2){
					//如果有两个审贷委审贷了
					 //如果都相同进件通过
					 if(result.get(0).getSpje().equals(request.getParameter("sxed")) && 
							 result.get(0).getSplv().equals(request.getParameter("lv")) &&
							 result.get(0).getSpqx().equals(request.getParameter("qx"))){
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
					 }else{
						 //如果不相同，默认为退回
						/* int a2=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
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
							}*/
						 //如果不相同则删除审贷会记录
						 UserService.deleteSpUser(request.getParameter("id"));
							map.put("message", "你审批的金额利息之类与前审贷委不同，需要重新审批");	
						
					 }
				 }
				 
			 }else{
				 map.put("message", "提交成功");	
			 }
			
			
			
			
			
			
			
			
		}
		
		
		
	
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}
	
	
	/**
	 * 查询荣耀卡审贷会纪要
	 * @param JnpadCsSdModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/findRyCsSd.json", method = {RequestMethod.GET })
	public String findRyCsSd(@ModelAttribute JnpadCsSdModel JnpadCsSdModel,HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String id=request.getParameter("id");
		rymodel model=ryserver.selectryCs(id);
		String td="";
		Integer sfsp=0;
		Integer sftg=0;
		Integer fssfsp=0;
		Integer sfzs=0;
		Integer fssftg=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//查询初审是否审批,默认为没审批
		if(model!=null){
			sfsp=1;
		//查询审批是否通过,默认为没通过
			if(!model.getStatus().equals("1")){
				map.put("model", model);
			}else{
				sftg=1;
				map.put("model", model);
				//查询是否复审批，默认没审批
				rymodel model1=ryserver.selectryFs(id);
				if(model1!=null){
					fssfsp=1;
				//查询审批是否通过,默认为没通过
				if(!model.getStatus().equals("1")){
					map.put("model1", model1);
				}else{
					fssftg=1;
					map.put("model1", model1);
					//查询终审结论
					List<CustomerSpUser> result=UserService.selectSpJyB(id);
					//查询是否有终身结论 默认没审批
					if(result.size()>0){
						sfzs=1;
					for(int i=0;i<result.size();i++){
						Date date=new Date();
						long time=date.getTime()-result.get(i).getTime().getTime();
						if(result.get(i).getStatus().equals("1")){
							td=td+	"<table class='cpTable khjbxx' style='margin-top:5px;'>"+"<tr>"+
									"<th colspan='4'>终审审批结论</th>"+ 
									"</tr>"+
									"<tr>"+
									"<th>终审结论：</th>"+
									"<td><input type ='text' value='通过' readonly = 'true'>"+
									"</td>"+
									"<th>终审人：</th>"+
									"<td><input type ='text' value='"+result.get(i).getName1()+"' readonly = 'true'>"+
									"</td>"+
									"</tr>"+
									"<tr>"+
									"<th>终审金额：</th>"+
									"<td><input type ='text' value='"+result.get(i).getSpje()+"' readonly = 'true'>"+
									"</td>"+
									"<th>终审利率：</th>"+
									"<td><input type='text' id='sxqj' value='"+result.get(i).getSplv()+"' readonly = 'true'/>"+
									"</td>"+
									"</tr>"+
									"<tr>"+
									"<th>终审期限：</th>"+
									"<td><input type = 'text' value='"+result.get(i).getSpqx()+"' readonly = 'true'></td>"+
									"<th>终审备注：</th>"+
									"<td><textarea name='decisionRefusereason' id='sdw1' readonly = 'true'>"+result.get(i).getBeizhu()+"</textarea>" +
									"</td>" +
									
									"</tr>"+
									"<tr>"+
									"<th>终审时间：</th>"+
									"<td ><input type='text' id='sxqj' value='"+sdf.format(result.get(i).getSptime())+"' readonly = 'true'/>"+
									"</td>"+
									"<th>审批效率：</th>"+
									"<td><textarea name='decisionRefusereason' id='sdw1' readonly = 'true'>"+(int)time/(1000*60*60)+"时(注:0时为复审申请不到一小时后审批)</textarea>" +
									"</td>" +
									"</tr>"+"</table>";
						}else if(result.get(i).getStatus().equals("2")){
							td=td+	"<table class='cpTable khjbxx' style='margin-top:5px;'>"+"<tr>"+"<tr>"+
									"<th colspan='4'>终审审批结论</th>"+ 
									"</tr>"+
									"<tr>"+
									"<th>终审结论：</th>"+
									"<td><input type ='text' value='拒绝' readonly = 'true'>"+
									"</td>"+
									"<th>终审人：</th>"+
									"<td><input type ='text' value='"+result.get(i).getName1()+"' readonly = 'true'>"+
									"</td>"+
									"</tr>"+
									"<tr>"+
									"<th>拒绝原因：</th>"+
									"<td><textarea name='decisionRefusereason' id='sdw1' readonly = 'true'>"+result.get(i).getJlyys()+"</textarea>" +
									"</td>" +
									"<th>终审备注：</th>"+
									"<td><textarea name='decisionRefusereason' id='sdw1' readonly = 'true'>"+result.get(i).getBeizhu()+"</textarea>" +
									"</tr>"+
									"<tr>"+
									"<th>终审时间：</th>"+
									"<td ><input type='text' id='sxqj' value='"+sdf.format(result.get(i).getSptime())+"' readonly = 'true'/>"+
									"</td>"+
									"<th>审批效率：</th>"+
									"<td><textarea name='decisionRefusereason' id='sdw1' readonly = 'true'>"+(int)time/(1000*60*60)+"时(注:0时为距离复审不到一小时后审批)</textarea>" +
									"</td>" +
									"</tr>"+"</table>";
						}else if(result.get(i).getStatus().equals("3")){
							td=td+	"<table class='cpTable khjbxx' style='margin-top:5px;'>"+"<tr>"+
									"<th colspan='4'>终审审批结论</th>"+ 
									"</tr>"+
									"<tr>"+
									"<th>终审结论：</th>"+
									"<td><input type ='text' value='退回' readonly = 'true'>"+
									"</td>"+
									"<th>终审人：</th>"+
									"<td><input type ='text' value='"+result.get(i).getName1()+"' readonly = 'true'>"+
									"</td>"+
									"</tr>"+
									"<tr>"+
									"<th>退回原因：</th>"+
									"<td><textarea name='decisionRefusereason' id='sdw1' readonly = 'true'>"+result.get(i).getJlyys()+"</textarea>" +
									"</td>" +
									"<th>终审备注：</th>"+
									"<td><textarea name='decisionRefusereason' id='sdw1' readonly = 'true'>"+result.get(i).getBeizhu()+"</textarea>" +
									"</tr>"+
									"<tr>"+
									"<th>终审时间：</th>"+
									"<td ><input type='text' id='sxqj' value='"+sdf.format(result.get(i).getSptime())+"' readonly = 'true'/>"+
									"</td>"+
									"<th>审批效率：</th>"+
									"<td><textarea name='decisionRefusereason' id='sdw1' readonly = 'true'>"+(int)time/(1000*60*60)+"时(注:0时为距离复审不到一小时后审批)</textarea>" +
									"</td>" +
									"</tr>"+"</table>";
						}
					}
					}
					map.put("td", td);
				}}
			}
		}
		map.put("sfsp", sfsp);
		map.put("sftg", sftg);
		map.put("fssfsp", fssfsp);
		map.put("sfzs", sfzs);
		map.put("fssftg", fssftg);
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
		AppManagerAuditLog result=SdwUserService.selectCSJLA(id);
		map.put("result1", result);
		List<CustomerSpUser> result1=UserService.findspUser3(request.getParameter("id"));
		for(int a=0;a<result1.size();a++){
			CustomerSpUser name=UserService.selectUser(result1.get(a).getSpuserid());
			result1.get(a).setName1(name.getName1());
		}
		map.put("result",result1);
		/*
		CustormerSdwUser result3=SdwUserService.selectSpJy(id);
		map.put("result3",result3);*/
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
		if(result!=null){
			for(int a=0;a<result.size();a++){
				List<CustomerSpUser> result1=UserService.selectUser1(result.get(a).getId());
					CustomerSpUser name=UserService.selectUser(result1.get(0).getSpuserid());
					result.get(a).setName1(name.getName1());
					CustomerSpUser name1=UserService.selectUser(result1.get(1).getSpuserid());
					result.get(a).setName2(name1.getName1());
					CustomerSpUser name2=UserService.selectUser(result1.get(2).getSpuserid());
					result.get(a).setName3(name2.getName1());
			}
		}
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
		List<String> c=new ArrayList<String>();
		List<AppManagerAuditLog> d=new ArrayList<AppManagerAuditLog>();
		List<CustomerSpUser> result1=null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<CustomerSpUser>result=UserService.findspUser();
				for(int a=0;a<result.size();a++){
				 result1=UserService.findspUser2(result.get(a).getCapid());
					if(result1.size()!=0){
						if(!result1.get(0).getStatus().equals("0") && !result1.get(1).getStatus().equals("0") && !result1.get(2).getStatus().equals("0")){
							c.add(result.get(a).getCapid());
						}
					}
				}
				if(c.size()>0){
					for(int b=0;b<c.size();b++){
						AppManagerAuditLog result2=SdwUserService.selectCSJLA(c.get(b));
						if(result2!=null){
							d.add(b, result2);
						}
					}
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