package com.cardpay.pccredit.jnpad.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.IntoPiecesFilters;
import com.cardpay.pccredit.intopieces.web.ApproveHistoryForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CustomerSpUser;
import com.cardpay.pccredit.jnpad.model.CustormerSdwUser;
import com.cardpay.pccredit.jnpad.model.JnpadCsSdModel;
import com.cardpay.pccredit.jnpad.service.JnpadCustormerSdwUserService; 
import com.cardpay.pccredit.jnpad.service.JnpadSpUserService;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
@Controller 
@RequestMapping("/jnpad/CustormerSdwUserPc/*")
@JRadModule("jnpad.CustormerSdwUserPc")
public class CustormerSdwUserPcController extends BaseController{
	@Autowired
	private JnpadCustormerSdwUserService SdwUserService;
	@Autowired
	private JnpadSpUserService UserService;
	/**
	 * PC初审成功添加
	 * @param CustormerSdwUser
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertCSPC.json", method = { RequestMethod.GET })
	public JRadReturnMap insertCSPC(@ModelAttribute CustomerSpUser CustomerSpUser,@ModelAttribute AppManagerAuditLog AppManagerAuditLog,@ModelAttribute CustormerSdwUser CustormerSdwUser,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		List<CustomerSpUser> list=new ArrayList<CustomerSpUser>();
		String cid=request.getParameter("customerId");
		String pid=request.getParameter("productId");
		AppManagerAuditLog result=SdwUserService.selectaId(cid,pid);
		AppManagerAuditLog.setId(IDGenerator.generateID());
		AppManagerAuditLog.setApplicationId(result.getApplicationId());
		AppManagerAuditLog.setAuditType("1");
		AppManagerAuditLog.setExamineLv(request.getParameter("decisionRate"));
		AppManagerAuditLog.setQx(request.getParameter("qx"));
		AppManagerAuditLog.setExamineAmount(request.getParameter("decisionAmount"));
		AppManagerAuditLog.setUserId_1(request.getParameter("cyUser1"));
		AppManagerAuditLog.setUserId_2(request.getParameter("cyUser2"));
		AppManagerAuditLog.setUserId_3(request.getParameter("fdUser"));
		AppManagerAuditLog.setUserId_4(user.getId());
		SdwUserService.insertCsJl(AppManagerAuditLog);
		//初审通过状态
		String applicationId=result.getApplicationId();
		String userId=user.getId();
		Date times=new Date();
		String money=request.getParameter("decisionAmount");
		SdwUserService.updateCSZT(userId,times,money,applicationId);
			CustomerSpUser.setId(IDGenerator.generateID());
			CustomerSpUser.setCid(cid);
			CustomerSpUser.setPid(pid);
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
				UserService.addSpUser(CustomerSpUser);
			}
		return returnMap;
	
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
	@RequestMapping(value="insertsdjycs.json", method = { RequestMethod.GET })
	public  JRadReturnMap insertsdjyPCcs(@ModelAttribute CustomerSpUser CustomerSpUser,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		IntoPieces IntoPieces=new IntoPieces();
		String userId=user.getId();
		CustomerSpUser.setSpje(request.getParameter("decisionAmount"));
		CustomerSpUser.setSptime(new Date());
		CustomerSpUser.setSpqx(request.getParameter("qx"));
		CustomerSpUser.setSpuserid(user.getId());
		CustomerSpUser.setBeizhu(request.getParameter("SDWUSER1YJ"));
		CustomerSpUser.setSplv(request.getParameter("decisionRate"));
		CustomerSpUser.setCapid(request.getParameter("id"));
		CustomerSpUser.setJlyys(request.getParameter("decisionRefusereason"));
		if(request.getParameter("status").equals("approved")){
			CustomerSpUser.setStatus("1");
		}else if(request.getParameter("status").equals("refuse")){
			CustomerSpUser.setStatus("2");
		}else if(request.getParameter("status").equals("returnedToFirst")){
			CustomerSpUser.setStatus("3");
		}
		System.out.println(CustomerSpUser.getStatus());
		int a=UserService.addSpUser1(CustomerSpUser);
		if(a>0){
			returnMap.put("message", "提交成功");
			String capid=request.getParameter("id");
			//判断如果三个sp中都有相关金额则说明三个审贷委都已经审批完成
			List<CustomerSpUser>splists=UserService.findsplistsbycapid(capid);
			String[]spje=new String[3];
			String[]sysuserid=new String[3];
			for (int i = 0; i < splists.size(); i++) {
				spje[i]=splists.get(i).getSpje();
				sysuserid[i]=splists.get(i).getSpuserid();
			}
			if(spje[0]!=null&&spje[0]!=""
					&&spje[1]!=null&&spje[1]!=""
						&&spje[2]!=null&&spje[2]!=""){ 
				
				String sp1=splists.get(0).getStatus();
				String sp2=splists.get(1).getStatus();
				String sp3=splists.get(2).getStatus();
				if("1".equals(sp1)&&
						"1".equals(sp2)&&"1".equals(sp3)){ 
					sp1=splists.get(0).getSpje();
					sp2=splists.get(1).getSpje();
					sp3=splists.get(2).getSpje();
 					if(sp1.equals(sp2)&&sp1.equals(sp3)){    //金额相同
 						sp1=splists.get(0).getSplv();
 						sp2=splists.get(1).getSplv();
 						sp3=splists.get(2).getSplv();
 						if(sp1.equals(sp2)&&sp1.equals(sp3)){   //利率相同
 							IntoPieces.setFinal_approval(request.getParameter("decisionAmount"));
 							IntoPieces.setStatus("approved");
 							IntoPieces.setId(request.getParameter("id"));
 							IntoPieces.setCreatime(new Date());
 							int b=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);   //修改进件表信息
 							String applicationId=request.getParameter("id");
 							Date times=new Date();
 							String money=request.getParameter("decisionAmount");
 							SdwUserService.updateCSZTs(userId,times,money,applicationId);  //修改进件初审节点
 							if(b>0){
 								returnMap.put("message", "提交成功");  //如果成功添加sp,修改进件结论,修改初审结论
 							}else{
 								returnMap.put("message", "提交失败");
 							}
 						}else{
 							//利率不同删除三个人的审贷结论
 							//CustomerSpUser CustomerSpUser=new CustomerSpUser();
 							CustomerSpUser.setBeizhu("");
 							CustomerSpUser.setSptime(null);
 							CustomerSpUser.setJlyys("");
 							CustomerSpUser.setSpje("");
 							CustomerSpUser.setSplv("");
 							CustomerSpUser.setStatus("0");
 							CustomerSpUser.setSpqx("");
 							/*CustomerSpUser.setCapid(appId);
 							CustomerSpUser.setSpuserid(uId);*/
 							for (String sysuserid2 : sysuserid) {
 								CustomerSpUser.setSpuserid(sysuserid2);
 								UserService.addSpUser1(CustomerSpUser);
 							}
 							IntoPieces.setFinal_approval("");
 							IntoPieces.setStatus("audit");
 							IntoPieces.setId(request.getParameter("id"));
 							IntoPieces.setCreatime(new Date());
 							SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
 							returnMap.put("message", "利率不同,删除三个人的审贷结论重新审批!");
 						}
 					}else{
 						//金额不同删除三个人的审贷结论
 						CustomerSpUser.setBeizhu("");
							CustomerSpUser.setSptime(null);
							CustomerSpUser.setJlyys("");
							CustomerSpUser.setSpje("");
							CustomerSpUser.setSplv("");
							CustomerSpUser.setStatus("0");
							CustomerSpUser.setSpqx("");
							/*CustomerSpUser.setCapid(appId);
							CustomerSpUser.setSpuserid(uId);*/  
							for (String sysuserid2 : sysuserid) {
								CustomerSpUser.setSpuserid(sysuserid2);
								UserService.addSpUser1(CustomerSpUser);
							}
							IntoPieces.setFinal_approval("");
							IntoPieces.setStatus("audit");
							IntoPieces.setId(request.getParameter("id"));
							IntoPieces.setCreatime(new Date());
							SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
							returnMap.put("message", "金额不同,删除三个人的审贷结论重新审批!");
 					}  
				}
			}else  if("2".equals(CustomerSpUser.getStatus())){
				//因为进件被拒绝之后不用重新审批 所以自己存一个再给其他两个审贷委修改一下 
				//退回有所不同  确认有退回情况后自己存一个 三个审贷委必须都重新审贷所以包括自己也得算在其中
				//修改sp，info表
				IntoPieces.setStatus("refuse");
				IntoPieces.setCreatime(new Date());
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setUserId(userId);
				IntoPieces.setREFUSAL_REASON(request.getParameter("decisionRefusereason"));
				int c=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				//因为一人被拒绝则其他两位审贷委也不用审l 所以也要改其他两个审贷委的sp表
				CustomerSpUser.setBeizhu("已经有审贷委审批拒绝");
				CustomerSpUser.setSptime(null);
				CustomerSpUser.setJlyys("已经有审贷委审批拒绝");
				CustomerSpUser.setSpje("");
				CustomerSpUser.setSplv("");
				CustomerSpUser.setStatus("2");
				CustomerSpUser.setSpqx("");
				/*CustomerSpUser.setSpuserid(uId);*/
				for (String sysuserid1 : sysuserid) {
					if(!userId.equals(sysuserid1)){
						CustomerSpUser.setSpuserid(sysuserid1);
						UserService.addSpUser1(CustomerSpUser);
					}
				}
				if(c>0){
					int d=SdwUserService.updateCustormerProSdwUser(IntoPieces);
					if(d>0){
						RiskCustomer RiskCustomer=new RiskCustomer();
						RiskCustomer.setCustomerId(request.getParameter("customerId"));
						RiskCustomer.setProductId(request.getParameter("productId"));
						RiskCustomer.setRiskCreateType("manual");
						RiskCustomer.setRefuseReason(request.getParameter("decisionRefusereason"));
						RiskCustomer.setCREATED_TIME(new Date());
						RiskCustomer.setUserId(userId);
						RiskCustomer.setCustManagerId(request.getParameter("custManagerId"));
						String pid=null;
						if(null==pid){
							pid=UUID.randomUUID().toString();
						}
						RiskCustomer.setId(pid);
						int e=SdwUserService.insertRiskSdwUser(RiskCustomer);
						//拒绝时修改节点状态
						String applicationId=request.getParameter("id");
						Date times=new Date();
						SdwUserService.updateHistorys(userId,times,applicationId);
						if(e>0){
							returnMap.put("message", "提交成功");
						}else{
							returnMap.put("message", "提交失败");
						}
					}
				}
			}else if("3".equals(CustomerSpUser.getStatus())){
				//退回有所不同  确认有退回情况后自己存一个 三个审贷委必须都重新审贷所以包括自己也得算在其中
				//修改sp，info表
				//删除三个人的审贷结论
				CustomerSpUser.setBeizhu("");
				CustomerSpUser.setSptime(null);
				CustomerSpUser.setJlyys("");
				CustomerSpUser.setSpje("");
				CustomerSpUser.setSplv("");
				CustomerSpUser.setStatus("0");
				CustomerSpUser.setSpqx("");
				/*CustomerSpUser.setCapid(appId);
				CustomerSpUser.setSpuserid(uId);*/  
				for (String sysuserid2 : sysuserid) {
					CustomerSpUser.setSpuserid(sysuserid2);
					UserService.addSpUser1(CustomerSpUser);
				}
				UserService.addSpUser1(CustomerSpUser);
				IntoPieces.setFinal_approval("");
				IntoPieces.setStatus("audit");
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setCreatime(new Date());
				SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				returnMap.put("message", "有退回情况发生，删除三个人的审贷结论重新审批!");
				/*IntoPieces.setStatus("returnedToFirst");
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setFallBackReason(request.getParameter("decisionRefusereason"));
				IntoPieces.setUserId(userId);
				IntoPieces.setCreatime(new Date());
				int e=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				//退回时修改节点状态
				String applicationId=request.getParameter("id");
				Date times=new Date();
				SdwUserService.updateHistory(userId,times,applicationId);
				if(e>0){
					int f=SdwUserService.updateCustormerProSdwUser(IntoPieces);
					if(f>0){
						returnMap.put("message", "提交成功");	
					}else{
						returnMap.put("message", "提交失败");
					}
				}*/
			}
			}
			
		return returnMap;
}
			
/**
	 * 当前审贷委审批
	 * @param RiskCustomer
	 * @param CustormerSdwUser
	 * @param IntoPieces
	 * @param request
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value="insertsdjycs.json", method = { RequestMethod.GET })
	public  JRadReturnMap insertsdjyPCcs(@ModelAttribute RiskCustomer RiskCustomer,@ModelAttribute CustormerSdwUser CustormerSdwUser,@ModelAttribute IntoPieces IntoPieces,HttpServletRequest request) {
		CustormerSdwUser CustormerSdwUser1=new CustormerSdwUser();
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
			 List<CustomerSpUser> result=UserService.findspUser2(request.getParameter("id"));
			 //如果有
			 if(result.size()>0 ){
				 //如果只有一位审贷委审批，对比参数
				 if(result.size()==1){
					 //如果都相同不处理
					 if(result.get(0).getSpje().equals(request.getParameter("sxed")) && 
							 result.get(0).getSplv().equals(request.getParameter("lv")) &&
							 result.get(0).getSpqx().equals(request.getParameter("qx"))){
					 }else{
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
						
					 }
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
				 }
				 
			 }
		}
		if(a>0){
			map.put("message", "提交成功");	
		}else{
			map.put("message", "提交失败");
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
}*/
	
	
	
	
	
	/**
	 * 审贷决议
	 * @param CustormerSdwUser
	 * @param request
	 * @return
	 */
/*	@ResponseBody
	@RequestMapping(value="insertsdjy.json", method = { RequestMethod.GET })
	public JRadReturnMap insertsdjy(@ModelAttribute RiskCustomer RiskCustomer,@ModelAttribute CustormerSdwUser CustormerSdwUser,@ModelAttribute IntoPieces IntoPieces,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId=user.getId();
		CustormerSdwUser.setSDJE(request.getParameter("decisionAmount"));
		CustormerSdwUser.setSDTIME(new Date());
		CustormerSdwUser.setSDQX(request.getParameter("qx"));
		CustormerSdwUser.setSDWJLY(userId);
		CustormerSdwUser.setSDWUSER1YJ(request.getParameter("SDWUSER1YJ"));
		CustormerSdwUser.setLV(request.getParameter("decisionRate"));
		CustormerSdwUser.setCAPID(request.getParameter("id"));
		CustormerSdwUser.setSDWUSER2YJ(request.getParameter("decisionRefusereason"));
		CustormerSdwUser.setPID(request.getParameter("productId"));
		CustormerSdwUser.setCAPID(request.getParameter("id"));
		CustormerSdwUser.setCID(request.getParameter("customerId"));
		CustormerSdwUser.setSDWUSER1(request.getParameter("SdwUser1"));
		int a=SdwUserService.insertCustormerSdwUser1(CustormerSdwUser);
		if(a>0){
			if(request.getParameter("status").equals("approved")){
				IntoPieces.setFinal_approval(request.getParameter("decisionAmount"));
				IntoPieces.setStatus("approved");
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setCreatime(new Date());
				int b=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				//通过状态
				String applicationId=request.getParameter("id");
				Date times=new Date();
				String money=request.getParameter("decisionAmount");
				SdwUserService.updateCSZTs(userId,times,money,applicationId);
				if(b>0){
					returnMap.put("message", "提交成功");
				}else{
					returnMap.put("message", "提交失败");
				}
			}else if(request.getParameter("status").equals("refuse")){
				int a1=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
				IntoPieces.setStatus("refuse");
				IntoPieces.setCreatime(new Date());
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setUserId(userId);
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
						RiskCustomer.setUserId(userId);
						RiskCustomer.setCustManagerId(request.getParameter("custManagerId"));
						String pid=null;
						if(null==pid){
							pid=UUID.randomUUID().toString();
						}
						RiskCustomer.setId(pid);
						int e=SdwUserService.insertRiskSdwUser(RiskCustomer);
						//拒绝时修改节点状态
						String applicationId=request.getParameter("id");
						Date times=new Date();
						SdwUserService.updateHistorys(userId,times,applicationId);
						if(e>0){
							returnMap.put("message", "提交成功");
						}else{
							returnMap.put("message", "提交失败");
						}
					}else{
						returnMap.put("message", "提交失败");
					}
				}else{
					returnMap.put("message", "提交失败");
				}
			}else if(request.getParameter("status").equals("returnedToFirst")){
				int a2=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
				IntoPieces.setStatus("returnedToFirst");
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setFallBackReason(request.getParameter("decisionRefusereason"));
				IntoPieces.setUserId(userId);
				IntoPieces.setCreatime(new Date());
				int c=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				//退回时修改节点状态
				String applicationId=request.getParameter("id");
				Date times=new Date();
				SdwUserService.updateHistory(userId,times,applicationId);
				if(c>0){
					int d=SdwUserService.updateCustormerProSdwUser(IntoPieces);
					if(d>0){
						returnMap.put("message", "提交成功");	
					}else{
						returnMap.put("message", "提交失败");
					}
				}
			}
		}else{
			returnMap.put("message", "提交失败");
		}
		return returnMap;
}
	
	*/
	
}
