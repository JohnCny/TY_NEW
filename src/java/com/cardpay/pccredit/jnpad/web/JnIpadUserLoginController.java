package com.cardpay.pccredit.jnpad.web;

import java.awt.geom.Arc2D.Float;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CommonConstant;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.ipad.constant.IpadConstant;
import com.cardpay.pccredit.ipad.model.Result;
import com.cardpay.pccredit.ipad.service.CustomerInforForIpadService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.filter.NotificationMessageFilter;
import com.cardpay.pccredit.jnpad.model.CustYunyinVo;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.CustomerManagerVo;
import com.cardpay.pccredit.jnpad.model.JBUser;
import com.cardpay.pccredit.jnpad.model.JnUserLoginIpad;
import com.cardpay.pccredit.jnpad.model.JnUserLoginResult;
import com.cardpay.pccredit.jnpad.model.LoginInfo;
import com.cardpay.pccredit.jnpad.model.NODEAUDIT;
import com.cardpay.pccredit.jnpad.model.NotifyMsgListVo;
import com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService;
import com.cardpay.pccredit.jnpad.service.JnIpadJBUserService;
import com.cardpay.pccredit.jnpad.service.JnIpadUserLoginService;
import com.cardpay.pccredit.jnpad.service.JnIpadXDService;
import com.cardpay.pccredit.jnpad.service.JnpadCustormerSdwUserService;
import com.cardpay.pccredit.jnpad.service.JnpadLoginService;
import com.cardpay.pccredit.jnpad.service.JnpadZongBaoCustomerInsertService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.cardpay.pccredit.report.model.CustomerHmd;
import com.cardpay.pccredit.riskControl.constant.RiskControlRole;
import com.cardpay.pccredit.riskControl.constant.RiskCreateTypeEnum;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.service.CustormerBlackListService;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.web.RequestHelper;

/**
 * ipad interface
 * pad用户登录
 * songchen
 * 2016年04月16日   下午1:54:18
 */
@Controller
public class JnIpadUserLoginController {
	private Integer yxed=0;
	private java.lang.Float dkye;
	private java.lang.Float bnqx;
	private java.lang.Float bwqx;
	private Integer qyjl=0;
	@Autowired
	private JnpadZongBaoCustomerInsertService jnpadZongBaoCustomerInsertService;
	@Autowired
	private JnIpadUserLoginService userService;
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	@Autowired
	private CustomerInforForIpadService customerInforService;
	@Autowired
	private JnIpadJBUserService JnIpadJBUser;
	@Autowired
	private JnIpadCustAppInfoXxService appInfoXxService;
	@Autowired
	private MaintenanceService maintenanceService;
	@Autowired
	private JnIpadXDService XDService;
	@Autowired
	private com.cardpay.pccredit.manager.service.ManagerBelongMapService ManagerBelongMapService;
	
	@Autowired
	private CustormerBlackListService cblservice;
	@Autowired
	private JnpadCustormerSdwUserService SdwUserService;
	@Autowired
	private JnpadLoginService LoginService;
	
	
	
	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/user/JnLogin.json")
	public String login(@ModelAttribute NODEAUDIT NODEAUDIT,HttpServletRequest request) {
		qyjl=0;
		JnUserLoginIpad user = null;
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String login = RequestHelper.getStringValue(request, "login");
		String passwd = RequestHelper.getStringValue(request, "password");
	/*	HttpSession session=request.getSession();
		String time= (String) session.getAttribute("loginTime");
		JnUserLoginIpad user = null;
		//上次登录时间
		if(time==null){
			Date loginTime =new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time1=sdf.format(loginTime);
			session.setAttribute("loginTime", time1);
			session.setMaxInactiveInterval(1440*60);
			String noTime="无";
			map.put("noTime", noTime);
		}else{
			Date loginTime1 =new Date();
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time2=sdf1.format(loginTime1);
			session.setAttribute("loginTime", time2);
			session.setMaxInactiveInterval(1440*60);
			String noTime="有";
			map.put("noTime", noTime);
			map.put("time", time);
		}*/
		
		Result result = null;
		JnUserLoginResult loginResult = null;
		if(StringUtils.isEmpty(login) || StringUtils.isEmpty(passwd)){
			result = new Result();
			result.setStatus(IpadConstant.FAIL);
			result.setReason(IpadConstant.LOGINNOTNULL);
			map.put("result",result);
		}else{
			loginResult = new JnUserLoginResult();
			 user = userService.login(login, passwd);
			if(user!=null){
				loginResult.setUser(user);
				loginResult.setStatus(IpadConstant.SUCCESS);
				loginResult.setReason(IpadConstant.LOGINSUCCESS);
			}else{
				loginResult.setStatus(IpadConstant.FAIL);
				loginResult.setReason(IpadConstant.LOGINFAIL);
			}
			map.put("result",loginResult);
			LoginInfo Time=LoginService.selecTime(login);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			String str1=sdf.format(Time.getACTION_TIME()); 
			map.put("Time", str1);
		LoginInfo LoginInfo=new LoginInfo();
		LoginInfo.setACTION_TIME(new Date());
		LoginInfo.setLOGIN(login);
		String id=null;
		if(null==id){
			id=UUID.randomUUID().toString();
		}
		LoginInfo.setID(id);
		int a=LoginService.insetTime(LoginInfo);
			//查询是否为区域经理或者小组长，以及小组成员
			List list1=cxke(user);
			map.put("list", list1);
			map.put("listsize", list1.size());
			if(qyjl==1){
				map.put("zw", "区域经理");	
			}else if(qyjl==2){
				map.put("zw", "客户经理主管");	
			}else if(qyjl==3){
				map.put("zw", "客户经理");	
			}
		if(user!=null){
			if(CommonConstant.USER_TYPE.USER_TYPE_1 == user.getUserType()){
				List<AccountManagerParameterForm> forms = maintenanceService.findSubListManagerByManagerId1(user.getId(),user.getUserType());
				if(forms != null && forms.size() > 0){
					StringBuffer userIds = new StringBuffer();
					userIds.append("(");
					for(AccountManagerParameterForm form : forms){
						userIds.append("'").append(form.getUserId()).append("'").append(",");
					}
					userIds = userIds.deleteCharAt(userIds.length() - 1);
					userIds.append(")");
					String str = maintenanceService.getActiveList(userIds.toString());
					map.put("repay", str);
				}
			}
		}
		/*	NotifyMsgListVo vo=jjzkCount(user.getId());
			map.put("vo", vo);*/
		}
		JSONObject json = JSONObject.fromObject(map);
		return String.valueOf(json);
	}
	
	
	/**
	 * 产品查询
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/product/prodBrowse.json", method = { RequestMethod.GET })
	public String browse(HttpServletRequest request) {
		String currentPage=request.getParameter("currentPage");
		String pageSize=request.getParameter("pageSize");
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		int page = 1;
		int limit = 10;
		if(StringUtils.isNotEmpty(currentPage)){
			page = Integer.parseInt(currentPage);
		}
		if(StringUtils.isNotEmpty(pageSize)){
			limit = Integer.parseInt(pageSize);
		}
		List<com.cardpay.pccredit.ipad.model.ProductAttribute> products = userService.findProducts(page,limit);
		int totalCount = userService.findProductsCount();
		result.put("totalCount", totalCount);
		result.put("result", products);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 客户新增
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerInfor/customerInsert.json")
	public String insert(HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String name = request.getParameter("name");
		String cardId = request.getParameter("cardId");
		String cardType = request.getParameter("cardType");
		String userId = request.getParameter("userId");
		map = customerInforService.addCustomer(name,cardId,cardType,userId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 查看当前客户登录信息
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/user/findSysUserMsg.json")
	public String findSysUserMsg(HttpServletRequest request) {
		String loginId = RequestHelper.getStringValue(request, "userId");
		//查询登录用户
		SystemUser user = userService.findUser(loginId);
		//查询机构
		String orgName = userService.findOrg(loginId);
		CustomerManagerVo  customerManagerVo = new CustomerManagerVo();
		customerManagerVo.setName(user.getDisplayName());//姓名
		customerManagerVo.setSex(user.getGender());//性别
		customerManagerVo.setAge(user.getAge()+"");//年龄
		customerManagerVo.setOrg(orgName);//所属银行
		customerManagerVo.setExternalId(user.getExternalId());//客户经理编号
		customerManagerVo.setSxqx("");//授信权限
		customerManagerVo.setFkze("");//放款总额
		
		//response
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		result.put("result", customerManagerVo);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	/**
	 * 客户运营状况查询
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/user/findYunyinstatus.json")
	public String findYunyinstatus(HttpServletRequest request) {
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		String zje;
		String userId = RequestHelper.getStringValue(request, "userId");
		//查询运营状况
		String Money=appInfoXxService.findAlledByUser(userId);
		if(Money==null){
			result.put("SXmoney", "0.00");
		}else{
			result.put("SXmoney", Money);
		}
		String yxed1=appInfoXxService.findyxByUser(userId);
		if(yxed1==null){
			result.put("yxed", "0.00");
		}else{
			result.put("yxed",yxed1);
		}
		zje=appInfoXxService.findyqzeByUser(userId);
		if(zje==null){
			result.put("yqye", "0.00");
		}else{
			result.put("yqye", zje);
		}
		int yqrs=appInfoXxService.findyqByUser(userId);
		result.put("yqrs", yqrs);
		Money=null;
		yxed=0;
		zje=null;
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 退回客户重新申请
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/user/updateJj.json")
	public String updateJj(HttpServletRequest request) {
		String appId= request.getParameter("userId");
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		int a=SdwUserService.selectCount(appId);
		if(a>0){
			int b=SdwUserService.deleteCsJl(appId);
			int c=SdwUserService.deleteCustormerSdwUser(appId);
		}
		addIntoPiecesService.doMethod(appId);
		result.put("result", "重新申请成功");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
	public List cxke(JnUserLoginIpad user) {
		Integer b=0;
		Integer c1=0;
		String parentId="100000";
		List list=new ArrayList();
		List list1=new ArrayList();
		if(user!=null){
			String userId=user.getId();
			//确认当前客户经理是否为区域经理
			List<ManagerBelongMapForm> result1=ManagerBelongMapService.findAllqyjl(parentId);
			for(int a=0;a<result1.size();a++){
				if(result1.get(a).getId().equals(userId)){
					b=1;
				}
			}
			if(b==1){
				qyjl=1;
				List<JBUser> depart=JnIpadJBUser.selectDepartUser(userId);
				for(int i=0;i<depart.size();i++){
					List<JBUser> findxzcy=JnIpadJBUser.selectUserByDid(depart.get(i).getId());
					for(int a=0;a<findxzcy.size();a++){
						list.add(findxzcy.get(a));	
					}
				
			}}else{
				//确认当前客户经理是否为小组长
				for(int i=0;i<result1.size();i++){
					List<ManagerBelongMapForm> result2=ManagerBelongMapService.findxzz(result1.get(i).getId());
					for(int c=0;c<result2.size();c++){
						list1.add(result2.get(c).getId());
					}
				}
				for(int d=0;d<list1.size();d++){
					if(list1.get(d).equals(userId)){
						c1=1;
					}
				}
				if(c1>0){
					qyjl=2;
				}else{
					qyjl=3;
				}
				List<JBUser> findxzcy=JnIpadJBUser.selectUserByDid1(userId);
				for(int a=0;a<findxzcy.size();a++){
					list.add(findxzcy.get(a));	
				}
			}
			return list;
		}
		return list;
	}
	
	
	
	public NotifyMsgListVo jjzkCount(String userId) {
	/*	List <CustomerInfo> result1=cblservice.selectCusByUser(userId);
		Integer i=0;
		CustomerHmd list=null;
		for(int a=0;a<result1.size();a++){
				list=cblservice.findCustormerBlackList(result1.get(a).getCardId());
				if(list!=null){
					i=i+1;
					list=null;
				}
		}*/
		int JrefuseCount=0;
		int JRiskCount=0;
		int JblackCount=0;
		int JpassCount=0;
		int count=0;
		List<CustomerInfo> customerList = jnpadZongBaoCustomerInsertService.selectCustomerInfo(userId);
		if(customerList!=null){
			for(int a=0;a<customerList.size();a++){
				if(!customerList.get(a).getCreatedBy().equals(userId)){
					count+=1;
				}
			}
		}
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time1=sdf.format(date);
		String[] time=time1.split("-");
		String logntime=time[0]+time[1]+time[2].substring(0, 2);
		NotificationMessageFilter filter = new NotificationMessageFilter();
		filter.setUserId(userId);
		//拒绝进件数量
		filter.setNoticeType("refuse");
		List<IntoPieces> refusecount= appInfoXxService.findTZByFilter(filter);
		for(int i=0;i<refusecount.size();i++){
			String refuseTime=sdf.format(refusecount.get(i).getCreatime());
			String[] refusetime=refuseTime.split("-");
			String refusetime1=refusetime[0]+refusetime[1]+refusetime[2].substring(0, 2);
			if(refusetime1.equals(logntime)){
				JrefuseCount+=1;
			}
		}
		//补充调查通知
		List<IntoPieces> balckcount= appInfoXxService.findTZBlackCount(userId);
		for(int i=0;i<balckcount.size();i++){
			String refuseTime=sdf.format(balckcount.get(i).getCreatime());
			String[] blacktime=refuseTime.split("-");
			String blacktime1=blacktime[0]+blacktime[1]+blacktime[2].substring(0, 2);
			if(blacktime1.equals(logntime)){
				JblackCount+=1;
			}
		}
		
		
		//申款成功
		filter.setNoticeType("approved");
		List<IntoPieces> passcount= appInfoXxService.findTZByFilter(filter);
		for(int i=0;i<passcount.size();i++){
			String passTime=sdf.format(passcount.get(i).getCreatime());
			String[] passtime=passTime.split("-");
			String passtime1=passtime[0]+passtime[1]+passtime[2].substring(0, 2);
			if(passtime1.equals(logntime)){
				JpassCount+=1;
			}
		}
				
		//风险客户通知
		RiskCustomerFilter filters = new RiskCustomerFilter();
		filters.setCustManagerId(userId);
		filters.setRiskCreateType(RiskCreateTypeEnum.manual.toString());
	    filters.setRole(RiskControlRole.manager.toString());
		//int risk = appInfoXxService.findRiskNoticeCountByFilter(filters);
		List<RiskCustomer> fxcount=appInfoXxService.findRiskTZCountByFilter(filters);
		for(int i=0;i<fxcount.size();i++){
			String passTime=sdf.format(fxcount.get(i).getCREATED_TIME());
			String[] passtime=passTime.split("-");
			String passtime1=passtime[0]+passtime[1]+passtime[2].substring(0, 2);
			if(passtime1.equals(logntime)){
				JRiskCount+=1;
			}
		}
		NotifyMsgListVo vo  = new NotifyMsgListVo();
		vo.setQita(count);
		vo.setRefuseCount(JrefuseCount);
		vo.setReturnCount(JblackCount);
		vo.setRisk(JRiskCount);
		vo.setPassCount(JpassCount);
		vo.setKaocha(JrefuseCount+JRiskCount+JblackCount+JpassCount);
		return vo;
	}
}
