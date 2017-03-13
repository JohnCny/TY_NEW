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
	 * 审贷决议
	 * @param CustormerSdwUser
	 * @param request
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value="insertsdjy.json", method = { RequestMethod.GET })
	public JRadReturnMap insertsdjyPC(@ModelAttribute RiskCustomer RiskCustomer,@ModelAttribute CustormerSdwUser CustormerSdwUser,@ModelAttribute IntoPieces IntoPieces,
			@ModelAttribute ApproveHistoryForm ahistory, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		CustormerSdwUser.setSDJE(request.getParameter("decisionAmount"));
		CustormerSdwUser.setSDTIME(new Date());
		CustormerSdwUser.setSDQX(request.getParameter("qx"));
		CustormerSdwUser.setSDWJLY(user.getId());
		CustormerSdwUser.setSDWUSER1YJ(request.getParameter("SDWUSER1YJ"));
		CustormerSdwUser.setLV(request.getParameter("decisionRate"));
		CustormerSdwUser.setCAPID(request.getParameter("id"));
		int a=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
		if(a>0){
			if(request.getParameter("status").equals("approved")){
				IntoPieces.setFinal_approval(request.getParameter("decisionAmount"));
				IntoPieces.setStatus("approved");
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setCreatime(new Date());
				int b=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				//通过状态
				String applicationId=request.getParameter("id");
				String userId=user.getId();
				Date times=new Date();
				String money=request.getParameter("decisionAmount");
				SdwUserService.updateCSZTs(userId,times,money,applicationId);
				if(b>0){
					returnMap.put("MESSAGE", "提交成功");
					return returnMap;
				}else{
					returnMap.put("MESSAGE", "提交失败");
					return returnMap;
				}
			}else if(request.getParameter("status").equals("refuse")){
				int a1=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
				IntoPieces.setStatus("refuse");
				IntoPieces.setCreatime(new Date());
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setUserId(user.getId());
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
						//拒绝时修改节点状态
						String applicationId=request.getParameter("id");
						String userId=user.getId();
						Date times=new Date();
						SdwUserService.updateHistorys(userId,times,applicationId);
						if(e>0){
							returnMap.put("MESSAGE", "提交成功");
							return returnMap;
						}else{
							returnMap.put("MESSAGE", "提交失败");
							return returnMap;
						}
					}else{
						returnMap.put("MESSAGE", "提交失败");
						return returnMap;
					}
				}else{
					returnMap.put("MESSAGE", "提交失败");
					return returnMap;
				}
			}else if(request.getParameter("status").equals("returnedToFirst")){
				int a2=SdwUserService.updateCustormerSdwUser(CustormerSdwUser);
				IntoPieces.setStatus("returnedToFirst");
				IntoPieces.setId(request.getParameter("id"));
				IntoPieces.setFallBackReason(request.getParameter("decisionRefusereason"));
				IntoPieces.setUserId(user.getId());
				IntoPieces.setCreatime(new Date());
				int c=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
				if(c>0){
					int d=SdwUserService.updateCustormerProSdwUser(IntoPieces);
					//退回时修改节点状态
					String applicationId=request.getParameter("id");
					String userId=user.getId();
					Date times=new Date();
					SdwUserService.updateHistory(userId,times,applicationId);
					if(d>0){
						returnMap.put("MESSAGE", "提交成功");
						return returnMap;
					}else{
						returnMap.put("MESSAGE", "提交失败");
						return returnMap;
					}
				}
			}
		}else{
			returnMap.put("MESSAGE", "提交失败");
			return returnMap;
		}
		return returnMap;
}
	*/
	
	
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
		}else{
			returnMap.put("message", "提交失败");
		}
		return returnMap;
}
	
	
	/**
	 * 审贷决议
	 * @param CustormerSdwUser
	 * @param request
	 * @return
	 */
	@ResponseBody
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
	
	
	
}
