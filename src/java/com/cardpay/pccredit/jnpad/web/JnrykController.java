package com.cardpay.pccredit.jnpad.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.PgUser;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcessForm;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.IntoPiecesFilters;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.jnpad.model.CustomerInfo;
import com.cardpay.pccredit.jnpad.model.CustomerSpUser;
import com.cardpay.pccredit.jnpad.model.JnpadCsSdModel;
import com.cardpay.pccredit.jnpad.service.JnIpadLocalExcelService;
import com.cardpay.pccredit.jnpad.service.JnpadCustormerSdwUserService;
import com.cardpay.pccredit.jnpad.service.JnpadSpUserService;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.rongyaoka.service.ryIntoPiecesService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.web.RequestHelper;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import sun.misc.BASE64Decoder;
@Controller
public class JnrykController {
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	@Autowired
	private JnIpadLocalExcelService LocalExcelService;
	@Autowired
	private ryIntoPiecesService ryipseservice;
	
	@Autowired
	private JnpadSpUserService UserService;
	@Autowired
	private ryIntoPiecesService rpservice;
	@Autowired
	private JnpadCustormerSdwUserService SdwUserService;
	
	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private IntoPiecesService intoPiecesService;


	@Autowired
	private CustomerApplicationInfoService customerApplicationInfoService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MaintenanceService maintenanceService;
	
	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	
	
   	/**
   	 * 荣耀卡上传调查资料
   	 * @param request
   	 * @return
   	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/user/scrykdczl.json")
	public Map<String, Object> reportImport_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}
			String fileName =request.getParameter("fileName");
			String productId = request.getParameter("productId");
			String customerId = request.getParameter("customerId");
			addIntoPiecesService.importExcel(file,productId,customerId);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, "上传失败:"+e.getMessage());
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
   	
   
	/**
	 * 添加初审记录
	 * @param AppManagerAuditLog
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/customer/csryk.json" ,method = { RequestMethod.GET })
	public String customerBrower(@ModelAttribute AppManagerAuditLog AppManagerAuditLog,HttpServletRequest request){
		String userId =request.getParameter("userId");
		String cid=request.getParameter("cid");
		String pid=request.getParameter("pid");
		AppManagerAuditLog result=SdwUserService.selectaId(cid,pid);
		AppManagerAuditLog.setId(IDGenerator.generateID());
		AppManagerAuditLog.setApplicationId(result.getApplicationId());
		AppManagerAuditLog.setAuditType("1");
		AppManagerAuditLog.setUserId_4(userId);
		//导入审批记录（审批客户经理、辅调客户经理记录）T_APP_MANAGER_AUDIT_LOG
		SdwUserService.insertCsJl(AppManagerAuditLog);
		//初审通过状态
		String applicationId=result.getApplicationId();
		Date times=new Date();
		String money=request.getParameter("decisionAmount");
		//导入初审使用表WF_STATUS_QUEUE_RECORD 
		SdwUserService.updateCSZT(userId,times,money,applicationId);
		//改变CUSTOMER_APPLICATION_PROCESS 的NEXT_NODE_ID字段
		String nodeName="受理岗初审";
		ryipseservice.updatecapnextnodeid(nodeName,applicationId);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("message", "进件已到复审阶段!!");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	/**
	 * 查询复审记录
	 * @param AppManagerAuditLog
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/customer/fsryk.json" ,method = { RequestMethod.GET })
	public String fsryk(@ModelAttribute AppManagerAuditLog AppManagerAuditLog,HttpServletRequest request){
		String userId =request.getParameter("userId");
		List<CustomerApplicationIntopieceWaitForm> result=rpservice.findfsCustomer(userId, null, null, "报审岗复审");
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("result", result);
		map.put("size", result.size());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}
	
	/**
	 * 复审界面显示
	 * @param AppManagerAuditLog
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ipad/customer/fsrykcx.json" ,method = { RequestMethod.GET })
	public String fsrykcx(@ModelAttribute AppManagerAuditLog AppManagerAuditLog,HttpServletRequest request){
		String appId = request.getParameter("appId");
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		map.put("customerApplicationInfo", customerApplicationInfo);
		map.put("producAttribute", producAttribute);
		map.put("custManagerId", customerInfor.getUserId());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json.toString();
	}

/**
 * 添加复审结论
 * @param AppManagerAuditLog
 * @param request
 * @return
 */
@ResponseBody
@RequestMapping(value="/ipad/customer/fsrykadd.json" ,method = { RequestMethod.GET })
public String fsrykadd(@ModelAttribute AppManagerAuditLog AppManagerAuditLog,@ModelAttribute CustomerSpUser CustomerSpUser,HttpServletRequest request){
	List<CustomerSpUser> list=new ArrayList<CustomerSpUser>();
	String cid=request.getParameter("cid");
	String pid=request.getParameter("pid");
	String userId=request.getParameter("userId");
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
	AppManagerAuditLog.setUserId_4(userId);
	//SdwUserService.insertCsJl(AppManagerAuditLog);
	ryipseservice.updateCsjl(AppManagerAuditLog);
	//初审通过状态
	String applicationId=result.getApplicationId();
	Date times=new Date();
	String money=request.getParameter("decisionAmount");
//	SdwUserService.updateCSZT(userId,times,money,applicationId);
	ryipseservice.updateCSZT(userId,times,money,applicationId);
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
		/*c2.setSpuserid(request.getParameter("user_Id3"));
		list.add(2, c2);*/
		for(int sd=0;sd<list.size();sd++){
			CustomerSpUser.setSpuserid(list.get(sd).getSpuserid());
			UserService.addSpUser(CustomerSpUser);
		}
		String nodeName="报审岗复审";
		ryipseservice.updatecapnextnodeid(nodeName,applicationId);
	Map<String,Object> map = new LinkedHashMap<String,Object>();
	map.put("message", "进件已经提交到审贷岗");
	JsonConfig jsonConfig = new JsonConfig();
	jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	JSONObject json = JSONObject.fromObject(map, jsonConfig);
	return json.toString();
}



/**
 * 荣耀卡终审
 * @param AppManagerAuditLog
 * @param request
 * @return
 */
@ResponseBody
@RequestMapping(value="/ipad/customer/rykzs.json" ,method = { RequestMethod.GET })
public String rykzs(@ModelAttribute IntoPiecesFilters filter,HttpServletRequest request){
	filter.setProductName("融耀卡");
	filter.setUserId(request.getParameter("userId"));
	 List<IntoPiecesFilters> result=customerApplicationIntopieceWaitService.findCustomerApplicationIntopieceDecisons1(filter);
	Map<String,Object> map = new LinkedHashMap<String,Object>();
	map.put("result", result);
	map.put("size", result.size());
	JsonConfig jsonConfig = new JsonConfig();
	jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	JSONObject json = JSONObject.fromObject(map, jsonConfig);
	return json.toString();
}




/**
 * 终审界面
 * @param filter
 * @param request
 * @return
 */
@ResponseBody
@RequestMapping(value="/ipad/customer/zsjm.json" ,method = { RequestMethod.GET })
public String zsjm(@ModelAttribute IntoPiecesFilters filter,HttpServletRequest request){
	Map<String,Object> map = new LinkedHashMap<String,Object>();
	String appId = request.getParameter("appId");
	String uId=request.getParameter("userId");
	CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
	CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
	ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
	List<AppManagerAuditLog> appManagerAuditLog = productService.findAppManagerAuditLog(appId,"1");
	CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
	AppManagerAuditLog result=SdwUserService.selectCSJLAPC(appId,uId);
	JnpadCsSdModel sdwinfo=SdwUserService.findCsSds(appId,uId);
	/*//拒绝
	JnpadCsSdModel jjyj=SdwUserService.findCsSdRefuses(appId);
	//回退
	JnpadCsSdModel htyj=SdwUserService.findCsSdBlacks(appId);
	*/
	JRadModelAndView mv = new JRadModelAndView("/ryintopieces/input_decision", request);
	map.put("customerApplicationInfo", customerApplicationInfo);
	map.put("producAttribute", producAttribute);
	map.put("customerApplicationProcess", processForm);
	map.put("appManagerAuditLog", appManagerAuditLog.get(0));
	map.put("custManagerId", customerInfor.getUserId());
	map.put("result", result);
	map.put("sdwinfo", sdwinfo);
	JsonConfig jsonConfig = new JsonConfig();
	jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	JSONObject json = JSONObject.fromObject(map, jsonConfig);
	return json.toString();
}



/**
 * 当前审贷委审批
 * @param filter
 * @param request
 * @return
 */
@ResponseBody
@RequestMapping(value="/ipad/customer/dqsp.json" ,method = { RequestMethod.GET })
public String dqsp(@ModelAttribute CustomerSpUser CustomerSpUser,HttpServletRequest request){
	Map<String,Object> map = new LinkedHashMap<String,Object>();
	IntoPieces IntoPieces=new IntoPieces();
	String userId=request.getParameter("userId");
	CustomerSpUser.setId(IDGenerator.generateID());
	CustomerSpUser.setSpje(request.getParameter("decisionAmount"));
	CustomerSpUser.setSptime(new Date());
	CustomerSpUser.setSpqx(request.getParameter("qx"));
	CustomerSpUser.setSpuserid(userId);
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
	
	//如果当前审贷委提出意见为拒绝
	if(CustomerSpUser.getStatus().equals("2")){
		int a=UserService.addSpUser1(CustomerSpUser);
		UserService.updateSpBh("2", request.getParameter("id"));
		IntoPieces.setStatus("refuse");
		IntoPieces.setCreatime(new Date());
		IntoPieces.setId(request.getParameter("id"));
		IntoPieces.setUserId(request.getParameter(userId));
		IntoPieces.setREFUSAL_REASON(request.getParameter("decisionRefusereason"));
		int c=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
		if(c>0){
			int d=SdwUserService.updateCustormerProSdwUser(IntoPieces);
			if(d>0){
				RiskCustomer RiskCustomer=new RiskCustomer();
				RiskCustomer.setCustomerId(request.getParameter("customerId"));
				RiskCustomer.setProductId(request.getParameter("productId"));
				RiskCustomer.setRiskCreateType("manual");
				RiskCustomer.setRefuseReason(request.getParameter("decisionRefusereason"));
				RiskCustomer.setCREATED_TIME(new Date());
				RiskCustomer.setCustManagerId(request.getParameter("custManagerId"));
				RiskCustomer.setId(IDGenerator.generateID());
				int e=SdwUserService.insertRiskSdwUser(RiskCustomer);
				if(e>0){
					map.put("message", "提交成功,进件已被拒绝");
				}else{
					map.put("message", "提交失败");
				}
			}else{
				map.put("message", "提交失败");
			}
		}else{
			map.put("message", "提交失败");
		}
	}else if(CustomerSpUser.getStatus().equals("3")){
		int a=UserService.addSpUser1(CustomerSpUser);
		UserService.updateSpBh("3", request.getParameter("id"));
		IntoPieces.setStatus("returnedToFirst");
		IntoPieces.setId(request.getParameter("id"));
		IntoPieces.setFallBackReason(request.getParameter("decisionRefusereason"));
		IntoPieces.setUserId(userId);
		IntoPieces.setCreatime(new Date());
		int c=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
		if(c>0){
			int d=SdwUserService.updateCustormerProSdwUser(IntoPieces);
			if(d>0){
				map.put("message", "提交成功,进件已被退回");
			}else{
				map.put("message", "提交失败");
			}
		}
	}else if(CustomerSpUser.getStatus().equals("1")){
		//查询当前审贷委是否为第一个审贷的人
		List<CustomerSpUser> splists=UserService.findsplistsbycapid(request.getParameter("id"),userId);
		//如果不是，比较利息，金额，期限
		if(splists.size()>0){
			int a=UserService.addSpUser1(CustomerSpUser);
		//如果都相同，进件直接通过
			 if(splists.get(0).getSpje().equals(request.getParameter("decisionAmount")) && 
					 splists.get(0).getSplv().equals(request.getParameter("decisionRate")) &&
					 splists.get(0).getSpqx().equals(request.getParameter("qx"))){
					IntoPieces.setFinal_approval(request.getParameter("decisionAmount"));
					IntoPieces.setStatus("approved");
					IntoPieces.setId(request.getParameter("id"));
					IntoPieces.setCreatime(new Date());
					int b=SdwUserService.updateCustormerInfoSdwUser(IntoPieces);
					if(b>0){
						SdwUserService.updateCSZTs(userId,new Date(),request.getParameter("decisionAmount"),request.getParameter("id"));  //修改进件初审节点
						map.put("message", "提交成功,进件已经通过");
					}else{
						map.put("message", "提交失败");
					}
			 }else{
				 //如果不同删除前面审贷委的审贷
				 CustomerSpUser spuser=new CustomerSpUser();
				 spuser.setCapid(request.getParameter("id"));
				 spuser.setSpuserid(splists.get(0).getSpuserid());
				 spuser.setBeizhu("");
				 spuser.setSptime(null);
				 spuser.setJlyys("");
				 spuser.setSpje("");
				 spuser.setSplv("");
				 spuser.setStatus("0");
				 spuser.setSpqx("");
				 UserService.addSpUser1(spuser);
				 map.put("message", "你的审批与前审贷委不同，需要重新审批");
			 }
		}else{
			int a=UserService.addSpUser1(CustomerSpUser);
			if(a>0){
				map.put("message", "提交成功,等待下个审贷委审批");
			}else{
				map.put("message", "提交失败");
			}
		}
	}
	
	
	
	
	
	
	
	
	
	JsonConfig jsonConfig = new JsonConfig();
	jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	JSONObject json = JSONObject.fromObject(map, jsonConfig);
	return json.toString();
}

}