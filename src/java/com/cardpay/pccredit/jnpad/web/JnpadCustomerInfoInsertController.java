package com.cardpay.pccredit.jnpad.web;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.IdCardValidate;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.CustomerInfoForm;
import com.cardpay.pccredit.jnpad.model.JBUser;
import com.cardpay.pccredit.jnpad.model.JnUserLoginIpad;
import com.cardpay.pccredit.jnpad.service.JnIpadJBUserService;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerInfoInsertServ‎ice;
import com.cardpay.pccredit.jnpad.service.JnpadCustomerSelectService;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.system.model.SystemUser;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 
 * 新建客户
 * @author sealy
 *
 */
@Controller
public class JnpadCustomerInfoInsertController extends BaseController {
	@Autowired
	private JnIpadJBUserService JnIpadJBUser;
	@Autowired
	private MaintenanceService maintenanceService;
	@Autowired
	private JnpadCustomerInfoInsertServ‎ice JnpadCustomerInfoInsertServ‎ice;
	@Autowired
	private JnpadCustomerSelectService jnpadCustomerSelectService; 
	@Autowired
	private IntoPiecesService intoPiecesService;
	@Autowired
	private com.cardpay.pccredit.jnpad.service.JnIpadCustAppInfoXxService JnIpadCustAppInfoXxService;
	@Autowired
	private com.cardpay.pccredit.manager.service.ManagerBelongMapService ManagerBelongMapService;
	private Integer qyjl=0;
	@ResponseBody
	@RequestMapping(value="/ipad/product/customerInsert.json" ,method = { RequestMethod.GET })
	public String customerInsert(@ModelAttribute CustomerInfoForm customerinfoForm ,HttpServletRequest request){
		
		customerinfoForm.setChineseName(request.getParameter("chineseName"));
		customerinfoForm.setCardType(request.getParameter("cardType"));
		customerinfoForm.setCardId(request.getParameter("cardId"));
		customerinfoForm.setSpmc(request.getParameter("spmc"));
		customerinfoForm.setTelephone(request.getParameter("phoneNumber"));
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				CustomerInforFilter filter = new CustomerInforFilter();
				filter.setCardId(customerinfoForm.getCardId());
				//身份证号码验证
				if(customerinfoForm.getCardType()=="0"||customerinfoForm.getCardType().equals("0")){
				String msg = IdCardValidate.IDCardValidate(customerinfoForm.getCardId());
				if(msg !="" && msg != null){
					returnMap.put(JRadConstants.MESSAGE, msg);
					returnMap.put(JRadConstants.SUCCESS, false);
					
					JsonConfig jsonConfig = new JsonConfig();
					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
					JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
					return json.toString();
				}
				}
				CustomerInfo ls = jnpadCustomerSelectService.selectCustomerInfoByCardId(filter.getCardId());
				if(ls != null ){
					String message = "";
					String gId = ls.getUserId();
					String managerName = jnpadCustomerSelectService.selectManagerNameById(gId);
					if(gId==null){
						message = "保存失败，此客户已存在，请线下及时联系!";
					}else{
						message = "保存失败，此客户已挂在客户经理【"+managerName+"】名下!";
					}
					returnMap.put(JRadConstants.MESSAGE, message);
					//查询是否为风险名单
//					List<RiskCustomer> riskCustomers = customerInforService.findRiskByCardId(customerinfoForm.getCardId());
//					if(riskCustomers.size()>0){
//						SystemUser user = customerInforService.getUseById(riskCustomers.get(0).getCreatedBy());
//						DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						String dateString = format.format(riskCustomers.get(0).getCreatedTime());
//						message+="此客户于"+dateString+"被"+user.getDisplayName()+"拒绝，原因为"+riskCustomers.get(0).getRefuseReason();
//					}
//					returnMap.put(JRadConstants.MESSAGE, message);
//					returnMap.put(JRadConstants.SUCCESS, false);
//					
//					JsonConfig jsonConfig = new JsonConfig();
//					jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
//					JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
//					return json.toString();				
					}else{
					returnMap.put(JRadConstants.SUCCESS, true);

				
				CustomerInfo customerinfor = customerinfoForm.createModel(CustomerInfo.class);
//				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				User user = new User();
				
				user.setId(request.getParameter("userId"));
				customerinfor.setCreatedBy(user.getId());
				customerinfor.setUserId(user.getId());
				String id =  JnpadCustomerInfoInsertServ‎ice.customerInforInsert(customerinfor);
				returnMap.put(RECORD_ID, id);
				returnMap.put(JRadConstants.MESSAGE, "添加成功");
					}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
				JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
				return json.toString();			
				}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(returnMap, jsonConfig);
		return json.toString();

	}
	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/browse.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String browse( HttpServletRequest request) {
		qyjl=0;
		IntoPiecesFilter filter=new IntoPiecesFilter();
//		filter.setRequest(request);
		String userId = request.getParameter("userId");
		String userType = request.getParameter("userType");
		String name = request.getParameter("name");
		Integer s =new Integer(userType);
		List<IntoPieces> list=new ArrayList();
		QueryResult<IntoPieces> result=null;
		if(name!=null && name!=""){
			filter.setChineseName(name);
		}
		if(s!=0){
			//查询是否为客户经理或者小组长/区域经理
			List<JBUser> list1=cxke(userId);
			if(qyjl==3){
				filter.setUserId(userId);
			}else if(qyjl==2 || qyjl==1){
				  StringBuffer belongChildIds = new StringBuffer();
					belongChildIds.append("(");
					for(int i=0;i<list1.size();i++){
						belongChildIds.append("'").append(list1.get(i).getUserId()).append("'").append(",");
					}
					belongChildIds = belongChildIds.deleteCharAt(belongChildIds.length() - 1);
					belongChildIds.append(")");
					filter.setCustManagerIds(belongChildIds.toString());
			}	
		}
		result = JnpadCustomerInfoInsertServ‎ice.findintoPiecesByFilter(filter);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 退回客户列表
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/browse2.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String browse2( HttpServletRequest request){
		/*IntoPiecesFilter filter=new IntoPiecesFilter();
//		filter.setRequest(request);
		String userId = request.getParameter("userId");
		String userType = request.getParameter("userType");
		filter.setStatus(request.getParameter("status"));
		Integer s =new Integer(userType);
		List<IntoPieces> list=new ArrayList();
		QueryResult<IntoPieces> result=null;
		//客户经理
		if(s==1){
			filter.setUserId(userId);
		}
		result = JnpadCustomerInfoInsertServ‎ice.findintoPiecesByFilter(filter);
		for(int a=0;a<result.getItems().size();a++){
			
			if(result.getItems().get(a).getStatus().equals("returnedToFirst") ){
				list.add(result.getItems().get(a));
			}
		}
		filter.setStatus("nopass_replenish");
		result = JnpadCustomerInfoInsertServ‎ice.findintoPiecesByFilter(filter);
		for(int a=0;a<result.getItems().size();a++){
			
			if(result.getItems().get(a).getStatus().equals("nopass_replenish")){
				list.add(result.getItems().get(a));
			}
		}*/
		String userId=request.getParameter("userId");
		List<IntoPieces> result=JnIpadCustAppInfoXxService.findCustomerBack(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", result);
		map.put("size", result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 
	 * 拒绝客户列表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/browse1.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public String browse1( HttpServletRequest request) {
	/*	IntoPiecesFilter filter=new IntoPiecesFilter();
//		filter.setRequest(request);
		String userId = request.getParameter("userId");
		String userType = request.getParameter("userType");
		Integer s =new Integer(userType);
		List<IntoPieces> list=new ArrayList();
		QueryResult<IntoPieces> result=null;
		//客户经理
		if(s==1){
			filter.setUserId(userId);
		}
		result = JnpadCustomerInfoInsertServ‎ice.findintoPiecesByFilter(filter);
		for(int a=0;a<result.getItems().size();a++){
			
			if(result.getItems().get(a).getStatus().equals("refuse")){
				list.add(result.getItems().get(a));
			}
		}*/
		String userId = request.getParameter("userId");
		List<IntoPieces> list=JnIpadCustAppInfoXxService.findCustomerResulf(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("size", list.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 
	 * 通过客户列表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/browse3.json", method = { RequestMethod.GET })
	public String browse3( HttpServletRequest request) {
		String userId = request.getParameter("userId");
		List<IntoPieces> list=JnIpadCustAppInfoXxService.findCustomersuccess(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("size", list.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 添加合同金额
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/customerIntopiece/insertFin.json", method = { RequestMethod.GET })
	public String insertFin( HttpServletRequest request) {
		String productId = request.getParameter("productId");
		String customerId = request.getParameter("customerId");
		String applyQuota= request.getParameter("htje");
		System.out.println(applyQuota);
		int a=JnIpadCustAppInfoXxService.insertFin(productId, applyQuota, customerId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("size", a);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	public List cxke(String userId) {
		Integer b=0;
		Integer c1=0;
		String parentId="100000";
		List<JBUser> list=new ArrayList<JBUser>();
		List list1=new ArrayList();
		if(userId!=null){
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
}
