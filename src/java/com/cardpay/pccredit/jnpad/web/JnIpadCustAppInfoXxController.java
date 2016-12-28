package com.cardpay.pccredit.jnpad.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CIPERSONBASINFOCOPY;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.filter.CustomerApprovedFilter;
import com.cardpay.pccredit.jnpad.filter.NotificationMessageFilter;
import com.cardpay.pccredit.jnpad.model.AppInfoListVo;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.JnpadCustomerBianGeng;
import com.cardpay.pccredit.jnpad.model.NODEAUDIT;
import com.cardpay.pccredit.jnpad.model.NotifyMsgListVo;
import com.cardpay.pccredit.jnpad.model.RetrainUserVo;
import com.cardpay.pccredit.jnpad.model.RetrainingVo;
import com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService;
import com.cardpay.pccredit.jnpad.service.JnipadNodeService;
import com.cardpay.pccredit.manager.filter.RetrainingFilter;
import com.cardpay.pccredit.manager.model.AccountManagerRetraining;
import com.cardpay.pccredit.manager.model.Retraining;
import com.cardpay.pccredit.notification.model.NotificationMessage;
import com.cardpay.pccredit.report.model.CustomerHmd;
import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.CUSTOMERBLACKLIST;
import com.cardpay.pccredit.riskControl.service.CustormerBlackListService;
import com.cardpay.pccredit.system.model.SystemUser;

/**
 * ipad interface
 * 3.1.2 客户进件信息
 * 3.1.3 客户运营状况
 * 3.1.5 通知
 * 3.1.6 奖励激励信息
 * songchen
 * 2016年05月09日   下午1:54:18
 */
@Controller
public class JnIpadCustAppInfoXxController {
	private List list1=new ArrayList();


	@Autowired
	private JnIpadCustAppInfoXxService appInfoXxService;
	@Autowired
	private JnipadNodeService nodeservice;
	@Autowired
	private CustormerBlackListService cblservice;
	/**
	 * 进件信息查询 
	 * 【查询进件数量/拒绝进件数量/申请通过进件数量/补充调查进件数量】
	 * null|refuse|approved|null
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/browse.json", method = { RequestMethod.GET })
	public String browse(HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		
		//获取请求参数
		//null
		String status1=request.getParameter("status1");
		//refuse
		String status2=request.getParameter("status2");
		//approved
		String status3=request.getParameter("status3");
		//nopass_replenish
		String status4=request.getParameter("status4");
		int i = appInfoXxService.findCustAppInfoXxCount(userId,status1,status2, status3,status4);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("num", i);//统计数量
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/browse1.json", method = { RequestMethod.GET })
	public String browse1(HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		String userType=request.getParameter("userType");
		//refuse
//		String status2=request.getParameter("status2");
		String status2="refuse";
		//approved
//		String status3=request.getParameter("status3");
		String status3="approved";
		Integer s =new Integer(userType);
		if(s!=1){
			userId="";
		}
		int refuse = appInfoXxService.findCustAppInfoXxCount(userId,null,status2, null,null);
		int approved = appInfoXxService.findCustAppInfoXxCount(userId,null,null, status3,null);
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		
		AppInfoListVo vo = new AppInfoListVo();
		vo.setApprovedNum(approved);
		vo.setRefuseNum(refuse);
		
		result.put("result", vo);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 查询审核通过的进件
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findintoApprovedPieces.json", method = { RequestMethod.GET })
	public String findintoApprovedPieces(HttpServletRequest request,CustomerApprovedFilter filter) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		//分页参数
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		
		int page = 0;
		int limit = 10;
		if(StringUtils.isNotEmpty(currentPage)){
			page = Integer.parseInt(currentPage);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			limit = Integer.parseInt(pageSize);
		}
		filter.setPage(page);
		filter.setLimit(limit);
		
		//审核通过
		filter.setStatus("approved");
		List<IntoPieces> list = appInfoXxService.findCustomerApprovedList(filter);
		int totalCount = appInfoXxService.findCustomerApprovedCountList(filter);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("totalCount", totalCount);
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 根据条件查询进件
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findAppintoPiecesByParams.json", method = { RequestMethod.GET })
	public String findAppintoPiecesByParams(HttpServletRequest request,CustomerApprovedFilter filter) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		//分页参数
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		
		int page = 0;
		int limit = 10;
		if(StringUtils.isNotEmpty(currentPage)){
			page = Integer.parseInt(currentPage);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			limit = Integer.parseInt(pageSize);
		}
		
		filter.setPage(page);
		filter.setLimit(limit);
		List<IntoPieces> list = appInfoXxService.findCustomerApprovedList(filter);
		int totalCount = appInfoXxService.findCustomerApprovedCountList(filter);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("totalCount", totalCount);
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 更新进件状态
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/updateCustomerApplicationInfo.json")
	public String updateCustomerApplicationInfo(HttpServletRequest request,CustomerApprovedFilter filter) {
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		
		filter.setId(id);
		filter.setStatus(status);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map = appInfoXxService.updateCustomerApplicationInfo(filter);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 统计已授信额度
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/calCreditAmt.json", method = { RequestMethod.GET })
	public String calCreditAmt(HttpServletRequest request,CustomerApprovedFilter filter) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		
		String amt = appInfoXxService.calCreditAmt(filter);
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		
		//统计数量
		result.put("amt", amt);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 逾期客户数
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/overdueCustomerCount.json", method = { RequestMethod.GET })
	public String overdueCustomerCount(HttpServletRequest request,CustomerApprovedFilter filter) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		int num =  appInfoXxService.overdueCustomerCount(filter);
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("num", num);//逾期客户数
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 核销客户数
	 * ???暂时没记录
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/cavCustomerCount.json", method = { RequestMethod.GET })
	public String cavCustomerCount(HttpServletRequest request,CustomerApprovedFilter filter) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		filter.setUserId(userId);
		
		//TODO
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("num", 0);//核销客户数
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 客户资料变更通知
	 * 根据参数NotificationMessageFilter的noticeType属性的不同来实现查询不同的通知
	 * //审贷会通知shendaihui//客户原始资料变更通知yuanshiziliao
	 * //培训通知peixun//考察成绩通知kaocha//其他通知qita
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/notifiyMessage.json", method = { RequestMethod.GET })
	public String notifiyMessage(HttpServletRequest request,NotificationMessageFilter filter) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		String noticeType=request.getParameter("noticeType");
		filter.setUserId(userId);
		filter.setNoticeType(noticeType);
		
		List<NotificationMessage> list = appInfoXxService.findNotificationMessageByFilter(filter);
		int totalCount = appInfoXxService.findNotificationCountMessageByFilter(filter);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("totalCount", totalCount);
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/notifiyMessageNum.json", method = { RequestMethod.GET })
	public String notifiyMessageNum(@ModelAttribute NODEAUDIT NODEAUDIT,@ModelAttribute CUSTOMERBLACKLIST cl,HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		List <CustomerInfo> result=cblservice.selectCusByUser(userId);
		Integer i=0;
		CustomerHmd list=null;
		for(int a=0;a<result.size();a++){
			
				list=cblservice.findCustormerBlackList(result.get(a).getCardId());
				if(list!=null){
					i=i+1;
					list=null;
				}
		}
		NODEAUDIT.setUser_id(request.getParameter("userId"));
		NotificationMessageFilter filter = new NotificationMessageFilter();
		filter.setUserId(userId);
		cl.setUserid(userId);
		filter.setNoticeType("shendaihui");
		int count1 = appInfoXxService.findNotificationCountMessageByFilter(filter);
		filter.setNoticeType("yuanshiziliao");
		int count2 = appInfoXxService.findNotificationCountMessageByFilter(filter);
		filter.setNoticeType("peixun");
		int count3 = appInfoXxService.findNotificationCountMessageByFilter(filter);
		filter.setNoticeType("kaocha");
		int count4 = appInfoXxService.findNotificationCountMessageByFilter(filter);
		filter.setNoticeType("qita");
		int count5 = appInfoXxService.findNotificationCountMessageByFilter(filter);
		String STATUS="audit";
		/*int  Pcount=appInfoXxService.selectNoSCount(STATUS);*/
		//拒绝进件数量
		filter.setNoticeType("refuse");
		int refuseCount= appInfoXxService.findNoticeCountByFilter(filter);
		//补充调查通知
		filter.setNoticeType("returnedToFirst");
		int returnCount= appInfoXxService.findNoticeCountByFilter(filter);
		
		//申款成功
		filter.setNoticeType("approved");
		int passCount= appInfoXxService.findNoticeCountByFilter(filter);
				
		//风险客户通知
		RiskCustomerFilter filters = new RiskCustomerFilter();
		filters.setCustManagerId(userId);
		filters.setRiskCreateType(RiskCreateTypeEnum.manual.toString());
	    filters.setRole(RiskControlRole.manager.toString());
		int risk = appInfoXxService.findRiskNoticeCountByFilter(filters);
		//黑名单通知
	
		//客户资料变更
		List<JnpadCustomerBianGeng> cuslist=appInfoXxService.findbiangengCountByManagerId(userId);
		int count6 = cuslist.size();
		int sum=count1+count2+count3+count4+count5+refuseCount+returnCount+risk+count6;
		NotifyMsgListVo vo  = new NotifyMsgListVo();
		vo.setShendaihui(count1);
		vo.setYuanshiziliao(count2);
		vo.setPeixun(count3);
		vo.setKaocha(count4);
		vo.setQita(count5);
		vo.setRefuseCount(refuseCount);
		vo.setReturnCount(returnCount);
		vo.setRisk(risk);
		vo.setSum(sum);
		vo.setZiliaobiangeng(count6);
		vo.setBianggeng(cuslist);
		vo.setBlackcount(i);
		vo.setPassCount(passCount);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(vo, jsonConfig);
		return json.toString();
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/findRetraining.json", method = { RequestMethod.GET })
	public String findRetraining(HttpServletRequest request,RetrainingFilter filter) {
		//分页参数
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		
		int page = 0;
		int limit = 10;
		if(StringUtils.isNotEmpty(currentPage)){
			page = Integer.parseInt(currentPage);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			limit = Integer.parseInt(pageSize);
		}
		filter.setPage(page);
		filter.setLimit(limit);
		
		
		List<RetrainingVo> list = appInfoXxService.findRetrainingsVoByFilter(filter);
		for(RetrainingVo vo :list){
			List<String> userList = appInfoXxService.findAccountManagerRetraining(vo.getId());
			SystemUser user = appInfoXxService.findSysUserById(vo.getCreatedBy());
			vo.setCreatedBy(user.getDisplayName());
			vo.setUserList(userList);
		}
		
		int totalCount = appInfoXxService.findRetrainingsCountByFilter(filter);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("totalCount", totalCount);
		result.put("result", list);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 奖励激励信息
	 * 上个月奖励激励金额
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/rewardIncentive.json", method = { RequestMethod.GET })
	public String rewardIncentive(HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		
		String  reward_incentive = appInfoXxService.getRewardIncentiveInformation(Integer.parseInt(year),
																				  Integer.parseInt(month),
																				  userId);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("reward_incentive",reward_incentive);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	/**
	 * 奖励激励信息
	 * 风险保证
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/returnPrepareAmount.json", method = { RequestMethod.GET })
	public String returnPrepareAmount(HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		String year=request.getParameter("year");
		String month=request.getParameter("month");
		
		String  return_prepare_amount = appInfoXxService.getReturnPrepareAmountById(Integer.parseInt(year),
																				  	Integer.parseInt(month),
																				    userId);
		
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("return_prepare_amount",return_prepare_amount);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	@ResponseBody
	@RequestMapping(value = "/ipad/custAppInfo/changestate.json", method = { RequestMethod.GET })
	public String change(HttpServletRequest request) {
		//当前登录用户ID
		String id=request.getParameter("id");
		String cardId=request.getParameter("cardId");
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		try {
			appInfoXxService.changeIsLook(id,cardId);
			result.put("mess","操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			result.put("mess","操作失败");
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 查看当前客户经理的黑名单客户
	 * @param cl
	 * @param request
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "/ipad/customer/custormerblacklist.json", method = { RequestMethod.GET })
	public String custormerblacklist(@ModelAttribute CustomerHmd CustomerHmd,HttpServletRequest request) {
		//当前登录用户ID
		String cardId=request.getParameter("cardId");
		List <CustomerHmd> result=cblservice.findCustormerBlackList(cardId);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("result",result);
		map.put("size",result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}*/
	
	/**
	 * 移除黑名单客户
	 * @param cl
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customer/deleteByCoustorId.json", method = { RequestMethod.GET })
	public String deleteByCoustorId(@ModelAttribute CUSTOMERBLACKLIST cl,HttpServletRequest request) {
		//当前登录用户ID
	
		String cardId=request.getParameter("cardId");
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		int a=cblservice.deleteByCoustorId(cardId);
		if(a>0){
		map.put("message", "移除成功")	;
		}else{
			map.put("message", "移除失败")	;
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 查询当前客户经理的所有客户的ID
	 * @param cl
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/ipad/customer/selectByCardId.json", method = { RequestMethod.GET })
	public String selectByCardId(@ModelAttribute CustomerHmd CustomerHmd,HttpServletRequest request) {
		//当前登录用户ID
		String userId=request.getParameter("userId");
		List <CustomerInfo> result=cblservice.selectCusByUser(userId);
		Integer i=0;
		CustomerHmd list=null;
		for(int a=0;a<result.size();a++){
			
				list=cblservice.findCustormerBlackList(result.get(a).getCardId());
				if(list!=null){
					list1.add(list);
			
				}
		}
		

		Map<String,Object> map = new LinkedHashMap<String,Object>();

		map.put("result", null);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);

		return json.toString();
	}
	/**
	 * 返回值
	 * @param cl
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customer/selectByCardId1.json", method = { RequestMethod.GET })
	public String selectByCardId1(@ModelAttribute CustomerHmd CustomerHmd,HttpServletRequest request) {
		
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("result",list1);
		if(list1==null){
			map.put("size",0);
		}else{
			map.put("size",list1.size());
		}
	
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		list1.clear();
		return json.toString();
	}
	/**
	 * 查询当前客户是否是黑名单客户
	 * @param CustomerHmd
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customer/selectByCardId3.json", method = { RequestMethod.GET })
	public String selectByCardId3(@ModelAttribute CustomerHmd CustomerHmd,HttpServletRequest request) {
		String cardId=request.getParameter("cardId");
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		CustomerHmd list=cblservice.findCustormerBlackList(cardId);
		if(list!=null){
			map.put("size", 1);
		}else{
			map.put("size", 0);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);

		return json.toString();
	}
	/**
	 * 审贷会通知
	 * @param CustomerHmd
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customer/sdhtz.json", method = { RequestMethod.GET })
	public String sdhtz(@ModelAttribute CustomerHmd CustomerHmd,HttpServletRequest request) {
		String STATUS="audit";
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		List<IntoPieces> result=appInfoXxService.selectNoS(STATUS);
		map.put("result", result);
		map.put("size", result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);

		return json.toString();
	}
}
