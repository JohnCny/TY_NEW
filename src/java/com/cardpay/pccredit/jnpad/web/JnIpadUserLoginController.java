package com.cardpay.pccredit.jnpad.web;

import java.awt.geom.Arc2D.Float;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.cardpay.pccredit.jnpad.model.CustYunyinVo;
import com.cardpay.pccredit.jnpad.model.CustomerManagerVo;
import com.cardpay.pccredit.jnpad.model.JnUserLoginIpad;
import com.cardpay.pccredit.jnpad.model.JnUserLoginResult;
import com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService;
import com.cardpay.pccredit.jnpad.service.JnIpadUserLoginService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
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
	@Autowired
	private JnIpadUserLoginService userService;
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	@Autowired
	private CustomerInforForIpadService customerInforService;
	
	@Autowired
	private JnIpadCustAppInfoXxService appInfoXxService;
	@Autowired
	private MaintenanceService maintenanceService;
	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/user/JnLogin.json")
	public String login(HttpServletRequest request) {
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		String login = RequestHelper.getStringValue(request, "login");
		String passwd = RequestHelper.getStringValue(request, "password");
		HttpSession session=request.getSession();
		String time= (String) session.getAttribute("loginTime");
		JnUserLoginIpad user = null;
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
		}
		
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
		}
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
		addIntoPiecesService.doMethod(appId);
		result.put("result", "重新申请成功");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	
}
