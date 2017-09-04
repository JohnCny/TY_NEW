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
	System.out.println(CustomerSpUser.getStatus());
	int a=UserService.addSpUser1(CustomerSpUser);
	if(a>0){
		map.put("message", "提交成功");
		String capid=request.getParameter("id");
		//判断如果三个sp中都有相关金额则说明三个审贷委都已经审批完成
		List<CustomerSpUser>splists=UserService.findsplistsbycapid(capid);
		String[]spje=new String[2];
		String[]sysuserid=new String[2];
		for (int i = 0; i < splists.size(); i++) {
			spje[i]=splists.get(i).getSpje();
			sysuserid[i]=splists.get(i).getSpuserid();
		}
		if(spje[0]!=null&&spje[0]!=""
				&&spje[1]!=null&&spje[1]!=""){ 
			
			String sp1=splists.get(0).getStatus();
			String sp2=splists.get(1).getStatus();
			if("1".equals(sp1)&&
					"1".equals(sp2)){ 
				sp1=splists.get(0).getSpje();
				sp2=splists.get(1).getSpje();
					if(sp1.equals(sp2)){    //金额相同
						sp1=splists.get(0).getSplv();
						sp2=splists.get(1).getSplv();
						if(sp1.equals(sp2)){   //利率相同
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
								map.put("message", "提交成功");  //如果成功添加sp,修改进件结论,修改初审结论
							}else{
								map.put("message", "提交失败");
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
							map.put("message", "利率不同,删除二人的审贷结论重新审批!");
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
						map.put("message", "金额不同,删除三个人的审贷结论重新审批!");
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
						map.put("message", "提交成功");
					}else{
						map.put("message", "提交失败");
					}
				}
			}
		}else if("3".equals(CustomerSpUser.getStatus())){
			CustomerSpUser.setBeizhu("已退回至客户经理处，请到查看进件处点击该客户补充资料并重新递交！");
			CustomerSpUser.setSptime(null);
			CustomerSpUser.setJlyys("已退回至客户经理处，请到查看进件处点击该客户补充资料并重新递交！");
			CustomerSpUser.setSpje("");
			CustomerSpUser.setSplv("");
			CustomerSpUser.setStatus("0");
			CustomerSpUser.setSpqx("");
			for (String sysuserid2 : sysuserid) {
				if(!userId.equals(sysuserid2)){
					CustomerSpUser.setSpuserid(sysuserid2);
					UserService.addSpUser1(CustomerSpUser);
				}
			}
			IntoPieces.setStatus("returnedToFirst");
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
					map.put("message", "提交成功");	
				}else{
					map.put("message", "提交失败");
				}
			}
		}
		}
	JsonConfig jsonConfig = new JsonConfig();
	jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	JSONObject json = JSONObject.fromObject(map, jsonConfig);
	return json.toString();
}

}