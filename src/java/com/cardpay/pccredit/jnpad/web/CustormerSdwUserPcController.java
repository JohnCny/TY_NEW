package com.cardpay.pccredit.jnpad.web;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.IntoPiecesFilters;
import com.cardpay.pccredit.jnpad.model.CustormerSdwUser;
import com.cardpay.pccredit.jnpad.service.JnpadCustormerSdwUserService; 
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
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

	/**
	 * PC初审成功添加
	 * @param CustormerSdwUser
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertCSPC.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insertCSPC(@ModelAttribute AppManagerAuditLog AppManagerAuditLog,@ModelAttribute CustormerSdwUser CustormerSdwUser,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		AppManagerAuditLog result=SdwUserService.selectaId(request.getParameter("customerId"),request.getParameter("productId"));
		String cid=null;
		if(null==cid){
			cid=UUID.randomUUID().toString();
		}
		AppManagerAuditLog.setId(cid);
		AppManagerAuditLog.setApplicationId(result.getApplicationId());
		AppManagerAuditLog.setAuditType("1");
		AppManagerAuditLog.setExamineLv(request.getParameter("decisionRate"));
		AppManagerAuditLog.setQx(request.getParameter("qx"));
		AppManagerAuditLog.setExamineAmount(request.getParameter("decisionAmount"));
		AppManagerAuditLog.setUserId_1(request.getParameter("cyUser1"));
		AppManagerAuditLog.setUserId_2(request.getParameter("cyUser2"));
		AppManagerAuditLog.setUserId_3(request.getParameter("fdUser"));
		AppManagerAuditLog.setUserId_4(user.getId());
		int a=SdwUserService.insertCsJl(AppManagerAuditLog);
		if(a>0){
			CustormerSdwUser.setSDWUSER1(request.getParameter("user_Id1"));
			CustormerSdwUser.setSDWUSER2(request.getParameter("user_Id2"));
			CustormerSdwUser.setSDWUSER3(request.getParameter("user_Id3"));
			CustormerSdwUser.setTIME(new Date());
			CustormerSdwUser.setCAPID(result.getApplicationId());
			CustormerSdwUser.setCID(request.getParameter("customerId"));
			CustormerSdwUser.setPID(request.getParameter("productId"));
			String pid=null;
			if(null==pid){
				pid=UUID.randomUUID().toString();
			}
			CustormerSdwUser.setId(pid);
			int b=SdwUserService.insertCustormerSdwUser(CustormerSdwUser);
			if(b>0){
				returnMap.put("MESSAGE", "提交成功");
				return returnMap;
			}else{
				returnMap.put("MESSAGE", "提交失败");
				return returnMap;
			}
		}else{
			returnMap.put("MESSAGE", "提交成功");
			return returnMap;
		}
	}
	
	/**
	 * 当前审贷委的审贷PC
	 */
	@ResponseBody
	@RequestMapping(value="SDHbowser.page", method = { RequestMethod.GET })
	public AbstractModelAndView change(@ModelAttribute IntoPiecesFilters filter,HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		QueryResult<IntoPiecesFilters> result =SdwUserService.selectSDHs(filter);
		JRadPagedQueryResult<IntoPiecesFilters> pagedResult = new JRadPagedQueryResult<IntoPiecesFilters>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/intopieces_sdh", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
}
}
