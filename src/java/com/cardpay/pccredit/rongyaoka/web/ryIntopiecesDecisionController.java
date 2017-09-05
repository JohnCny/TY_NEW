package com.cardpay.pccredit.rongyaoka.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;










import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerMarketing;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLogForm;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcessForm;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.IntoPiecesFilters;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationInfoService;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.intopieces.web.AddIntoPiecesForm;
import com.cardpay.pccredit.intopieces.web.CustomerApplicationIntopieceWaitForm;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.cardpay.pccredit.jnpad.model.CustomerSpUser;
import com.cardpay.pccredit.jnpad.model.JnpadCsSdModel;
import com.cardpay.pccredit.jnpad.service.JnpadCustormerSdwUserService;
import com.cardpay.pccredit.jnpad.service.JnpadSpUserService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.rongyaoka.service.ryIntoPiecesService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

@Controller
@RequestMapping("/rongyaoka/ryIntopiecesDecisionController/*")
@JRadModule("rongyaoka.ryIntopiecesDecisionController")
public class ryIntopiecesDecisionController extends BaseController {
	
	final public static String AREA_SEPARATOR  = "_";

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private JnpadCustormerSdwUserService SdwUserService;
	
	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private AddIntoPiecesService addIntoPiecesService;

	@Autowired
	private CustomerApplicationInfoService customerApplicationInfoService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private MaintenanceService maintenanceService;
	
	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	
	@Autowired
	private JnpadSpUserService UserService;
	@Autowired
	private ryIntoPiecesService rpservice;
	
	
	
	//审批岗终审
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute IntoPiecesFilters filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		/*filter.setStatus(Constant.APPROVE_INTOPICES);
		QueryResult<IntoPieces> result = intoPiecesService.findintoPiecesByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/intopieces_browse", request);*/
		filter.setProductName("融耀卡");
		filter.setUserId(userId);
		QueryResult<IntoPiecesFilters> result = customerApplicationIntopieceWaitService.findCustomerApplicationIntopieceDecisons(filter);
		JRadPagedQueryResult<IntoPiecesFilters> pagedResult = new JRadPagedQueryResult<IntoPiecesFilters>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/ryintopieces/intopieces_browse3", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	//最终审贷决议
		@ResponseBody
		@RequestMapping(value = "zzbrowse.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView zzbrowse(@ModelAttribute IntoPiecesFilters filter,HttpServletRequest request) {
			filter.setRequest(request);
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			filter.setUserId(userId);
			QueryResult<IntoPiecesFilters> result = customerApplicationIntopieceWaitService.findCustomerApplicationIntopieceDecisones(filter,request);
			JRadPagedQueryResult<IntoPiecesFilters> pagedResult = new JRadPagedQueryResult<IntoPiecesFilters>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/intopieces_browse2s", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}
	
	
	
	//受理岗初审进件zjBrowse.page
	@ResponseBody
	@RequestMapping(value = "csBrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView csBrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		filter.setNextNodeName("受理岗初审");
		filter.setUserId(userId);
		//修改注意和标微用的是一个查询方法 注意不要影响到标微
		QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.findCustomerApplicationIntopieceDecison(filter);
		JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/ryintopieces/intopieces_browse1", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	//报审岗复审进件zjBrowse.page
	@ResponseBody
	@RequestMapping(value = "bsBrowse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView bsBrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		filter.setNextNodeName("报审岗复审");
		filter.setUserId(userId);
		QueryResult<CustomerApplicationIntopieceWaitForm> result = rpservice.findCustomerApplicationIntopieceDecison(filter.getUserId(),filter.getCardId(),filter.getChineseName(),filter.getNextNodeName());
		JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/ryintopieces/intopieces_browse2", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	//显示报审岗复审 界面
		@ResponseBody
		@RequestMapping(value = "input_decision_baosheng.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView input_decision_baosheng(HttpServletRequest request) {
			String nodeName=request.getParameter("nodeName");
			String appId = request.getParameter("appId");
			CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
			ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
			CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
			JRadModelAndView mv = new JRadModelAndView("/ryintopieces/input_decision_baoshengs", request);
			mv.addObject("customerApplicationInfo", customerApplicationInfo);
			mv.addObject("producAttribute", producAttribute);
			mv.addObject("custManagerId", customerInfor.getUserId());
			mv.addObject("nodeName",nodeName);
			return mv; 
		}
	
	//显示审批岗终审页面
	@ResponseBody
	@RequestMapping(value = "input_decision.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView input_decision(HttpServletRequest request) {
		String appId = request.getParameter("appId");
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String uId=user.getId();
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
		CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		List<AppManagerAuditLog> appManagerAuditLog = productService.findAppManagerAuditLog(appId,"1");
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		AppManagerAuditLog result=SdwUserService.selectCSJLAPC(appId,uId);
		JnpadCsSdModel sdwinfo=SdwUserService.findCsSds(appId,uId);
		JRadModelAndView mv = new JRadModelAndView("/ryintopieces/input_decision", request);
		mv.addObject("customerApplicationInfo", customerApplicationInfo);
		mv.addObject("producAttribute", producAttribute);
		mv.addObject("customerApplicationProcess", processForm);
		mv.addObject("appManagerAuditLog", appManagerAuditLog.get(0));
		mv.addObject("custManagerId", customerInfor.getUserId());
		mv.addObject("result", result);
		mv.addObject("sdwinfo", sdwinfo);
		return mv;
	}
	
	//显示审批岗终审(最终)
	@ResponseBody
	@RequestMapping(value = "input_decisiones.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView input_decisiones(HttpServletRequest request) {
		String appId = request.getParameter("appId");
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String uId=user.getId();
		String names=user.getFirstName()+user.getLastName();
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
		CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		List<AppManagerAuditLog> appManagerAuditLog = productService.findAppManagerAuditLog(appId,"1");
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		AppManagerAuditLog result=SdwUserService.selectCSJLAPC(appId,uId);
		JnpadCsSdModel sdwinfo=SdwUserService.findZzCsSds(appId);
		List<JnpadCsSdModel> sdwinfos=SdwUserService.findCsSdId(appId);
		String sdwId1 = null;
		String sdwId2 = null;
		String sdwId3 = null;
		for(int i=0; i<sdwinfos.size();i++){ 
			sdwId1=sdwinfos.get(0).getSpuserid();
			sdwId2=sdwinfos.get(1).getSpuserid();
			sdwId3=sdwinfos.get(2).getSpuserid();
		}
		JnpadCsSdModel sdwinfo1=SdwUserService.findBySdwId(sdwId1,appId);
		JnpadCsSdModel sdwinfo2=SdwUserService.findBySdwId(sdwId2,appId);
		JnpadCsSdModel sdwinfo3=SdwUserService.findBySdwId(sdwId3,appId);
		
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/input_decisiones", request);
		mv.addObject("customerApplicationInfo", customerApplicationInfo);
		mv.addObject("producAttribute", producAttribute);
		mv.addObject("customerApplicationProcess", processForm);
		mv.addObject("appManagerAuditLog", appManagerAuditLog.get(0));
		mv.addObject("custManagerId", customerInfor.getUserId());
		mv.addObject("result", result);
		mv.addObject("sdwinfo1", sdwinfo1);
		mv.addObject("sdwinfo2", sdwinfo2);
		mv.addObject("sdwinfo3", sdwinfo3);
		mv.addObject("sdwinfo", sdwinfo);
		mv.addObject("names",names);
		return mv;
	}
	
	
	
	//显示进件初审 界面
	@ResponseBody
	@RequestMapping(value = "input_decision_chusheng.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView input_decision_chusheng(HttpServletRequest request) {
		String nodeName=request.getParameter("nodeName");
		String appId = request.getParameter("appId");
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		JRadModelAndView mv = new JRadModelAndView("/ryintopieces/input_decision_slgchusheng", request);
		mv.addObject("customerApplicationInfo", customerApplicationInfo);
		mv.addObject("producAttribute", producAttribute);
		mv.addObject("custManagerId", customerInfor.getUserId());
		mv.addObject("nodeName",nodeName);
		return mv; 
	}
	
	//保存审议决议
	@ResponseBody
	@RequestMapping(value = "update.json", method = { RequestMethod.GET })
	public JRadReturnMap update(@ModelAttribute CustomerApplicationInfo customerApplicationInfo,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			customerApplicationInfo.setActualQuote(customerApplicationInfo.getDecisionAmount());
			customerApplicationInfoService.update(customerApplicationInfo,request);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//显示用信信息
	@ResponseBody
	@RequestMapping(value = "input_letter.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView input_letter(HttpServletRequest request) {
		String appId = request.getParameter("appId");
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/input_letter", request);
		mv.addObject("customerApplicationInfo", customerApplicationInfo);

		return mv;
	}
	
	
	/**
	 * 执行提交
	 */
	@ResponseBody
	@RequestMapping(value = "updateAll.json")
	@JRadOperation(JRadOperation.APPROVE)
	public JRadReturnMap update(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		if (returnMap.isSuccess()) {
			try {
				customerApplicationIntopieceWaitService.updateCustomerApplicationProcessBySerialNumber(request);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}
	
	
	// 查看进件
	@ResponseBody
	@RequestMapping(value = "turn_iframe_tab.page")
	public AbstractModelAndView turn_iframe_tab(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/iframe_tab",request);
		String appId = RequestHelper.getStringValue(request, "appId");
		mv.addObject("appId", appId);
		return mv;
	}
	
	
	
	
	
		//显示部门审批
		@ResponseBody
		@RequestMapping(value = "input_decision_bumeng.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView input_decision_bumeng(HttpServletRequest request) {
			String appId = request.getParameter("appId");
			CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
			CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
			ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
			List<AppManagerAuditLog> appManagerAuditLog1 = productService.findAppManagerAuditLog(appId,"1");
			List<AppManagerAuditLog> appManagerAuditLog2 = productService.findAppManagerAuditLog(appId,"2");
			CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
			
			JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/input_decision_bumeng", request);
			mv.addObject("customerApplicationInfo", customerApplicationInfo);
			mv.addObject("producAttribute", producAttribute);
			mv.addObject("customerApplicationProcess", processForm);
			mv.addObject("appManagerAuditLog1", appManagerAuditLog1.get(0));
			mv.addObject("appManagerAuditLog2", appManagerAuditLog2.get(0));
			mv.addObject("custManagerId", customerInfor.getUserId());
			return mv;
		}
		
		
		//显示总经理审批
		@ResponseBody
		@RequestMapping(value = "input_decision_zjl.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView input_decision_zjl(HttpServletRequest request) {
			String appId = request.getParameter("appId");
			CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
			CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
			ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
			List<AppManagerAuditLog> appManagerAuditLog1 = productService.findAppManagerAuditLog(appId,"1");
			List<AppManagerAuditLog> appManagerAuditLog2 = productService.findAppManagerAuditLog(appId,"2");
			List<AppManagerAuditLog> appManagerAuditLog3 = productService.findAppManagerAuditLog(appId,"3");
			CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
			
			JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/input_decision_zjl", request);
			mv.addObject("customerApplicationInfo", customerApplicationInfo);
			mv.addObject("producAttribute", producAttribute);
			mv.addObject("customerApplicationProcess", processForm);
			mv.addObject("appManagerAuditLog1", appManagerAuditLog1.get(0));
			mv.addObject("appManagerAuditLog2", appManagerAuditLog2.get(0));
			mv.addObject("appManagerAuditLog3", appManagerAuditLog3.get(0));
			mv.addObject("custManagerId", customerInfor.getUserId());
			return mv;
		}
		
		//显示总经理审批
		@ResponseBody
		@RequestMapping(value = "input_decision_hz.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView input_decision_hz(HttpServletRequest request) {
			String appId = request.getParameter("appId");
			CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(appId);
			CustomerApplicationProcessForm  processForm  = intoPiecesService.findCustomerApplicationProcessById(appId);
			ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
			List<AppManagerAuditLog> appManagerAuditLog1 = productService.findAppManagerAuditLog(appId,"1");
			List<AppManagerAuditLog> appManagerAuditLog2 = productService.findAppManagerAuditLog(appId,"2");
			List<AppManagerAuditLog> appManagerAuditLog3 = productService.findAppManagerAuditLog(appId,"3");
			List<AppManagerAuditLog> appManagerAuditLog4 = productService.findAppManagerAuditLog(appId,"4");
			CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
			
			JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/input_decision_hz", request);
			mv.addObject("customerApplicationInfo", customerApplicationInfo);
			mv.addObject("producAttribute", producAttribute);
			mv.addObject("customerApplicationProcess", processForm);
			mv.addObject("appManagerAuditLog1", appManagerAuditLog1.get(0));
			mv.addObject("appManagerAuditLog2", appManagerAuditLog2.get(0));
			mv.addObject("appManagerAuditLog3", appManagerAuditLog3.get(0));
			mv.addObject("appManagerAuditLog4", appManagerAuditLog4.get(0));
			mv.addObject("custManagerId", customerInfor.getUserId());
			return mv;
		}
	
	
	
		//小微负责人审批
		@ResponseBody
		@RequestMapping(value = "bzBrowse.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView bzBrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
			filter.setRequest(request);
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			filter.setNextNodeName("小微负责人审批");
			filter.setUserId(userId);
			QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.findCustomerApplicationIntopieceDecison(filter);
			JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/intopieces_browse3", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}
	
	
		//零售业务部总经理审批
		@ResponseBody
		@RequestMapping(value = "zjBrowse.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView zjBrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
			filter.setRequest(request);
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			filter.setNextNodeName("零售业务部负责人审批");
			filter.setUserId(userId);
			QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.findCustomerApplicationIntopieceDecison(filter);
			JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/intopieces_browse4", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}
		
		
		//行长审批
		@ResponseBody
		@RequestMapping(value = "hzBrowse.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView hzBrowse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
			filter.setRequest(request);
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			filter.setNextNodeName("行长审批");
			filter.setUserId(userId);
			QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.findCustomerApplicationIntopieceDecison(filter);
			JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/intopieces_browse5", request);
			mv.addObject(PAGED_RESULT, pagedResult);
			return mv;
		}
}

	