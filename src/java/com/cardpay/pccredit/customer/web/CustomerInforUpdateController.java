package com.cardpay.pccredit.customer.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.QuestionConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.CustomerQuestionInfoFilter;
import com.cardpay.pccredit.customer.filter.VideoAccessoriesFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerCareersWeb;
import com.cardpay.pccredit.customer.model.CustomerCreditEvaluation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateBalanceSheet;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCashFlow;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCrossExamination;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateIncomeStatement;
import com.cardpay.pccredit.customer.model.CustomerMainManager;
import com.cardpay.pccredit.customer.model.CustomerQuestionInfo;
import com.cardpay.pccredit.customer.model.CustomerinforUpdateWorship;
import com.cardpay.pccredit.customer.service.CustomerCareersService;
import com.cardpay.pccredit.customer.service.CustomerCreditEvaluationService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.CustomerInforUpdateService;
import com.cardpay.pccredit.customer.service.CustomerMainManagerService;
import com.cardpay.pccredit.customer.service.CustomerQuestionService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.dimensional.model.Dimensional;
import com.cardpay.pccredit.dimensional.service.DimensionalService;
import com.cardpay.pccredit.dimensional.web.DimensionalForm;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.riskControl.constant.RiskAttributeEnum;
import com.cardpay.pccredit.riskControl.constant.RiskAttributeTypeEnum;
import com.cardpay.pccredit.riskControl.filter.RiskAttributeFilter;
import com.cardpay.pccredit.riskControl.filter.RiskCustomerFilter;
import com.cardpay.pccredit.riskControl.model.RiskAttribute;
import com.cardpay.pccredit.riskControl.service.RiskAttributeService;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.service.DictService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.DataBindHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.UserService;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

@Controller
@RequestMapping("/customer/basiccustomerinforUpdate/*")
@JRadModule("customer.basiccustomerinforUpdate")
public class CustomerInforUpdateController extends BaseController {
	
	final public static String AREA_SEPARATOR  = "_";

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CustomerInforService customerInforservice;

	@Autowired
	private CustomerInforUpdateService customerInforUpdateService;
	
	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private RiskAttributeService riskAttributeService;
	
	@Autowired
	private DivisionalService divisionalservice;
	
	@Autowired
	private DimensionalService dimensionalService;
	
	@Autowired
	private CustomerCareersService customerCareersService;
	
	@Autowired
	private CustomerQuestionService customerQuestionService;


	@Autowired
	private DictService dictService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;


	@Autowired
	private CustomerMainManagerService customerMainManagerService;
	
	
	@Autowired
	private CustomerCreditEvaluationService customerCreditEvaluationService; //授信评估模型

	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute CustomerInforFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		QueryResult<CustomerInfor> result = customerInforservice.findCustomerInforByFilter(filter);
		JRadPagedQueryResult<CustomerInfor> pagedResult = new JRadPagedQueryResult<CustomerInfor>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_browse",
                                                    request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}

	

	/**
	 * 执行添加 资产负债
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "balanceSheetInsert.json")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView balanceSheetInsert(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_zcfz",request);
		try {
			String balanceSheet = request.getParameter("balanceSheet");
			String customerId=request.getParameter("customerId");
			customerInforUpdateService.insertCustomerInforUpdateBalanceSheet(customerId, balanceSheet);

			List<CustomerInforUpdateBalanceSheet> customerInforUpdateBalanceSheet =
                    customerInforUpdateService.getCustomerInforUpdateBalanceSheetById(customerId);

			mv.addObject("customerInforUpdateBalanceSheet",	customerInforUpdateBalanceSheet);
			mv.addObject("customerId",customerId);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行添加资产负债错误"+e.getMessage());
		}
		return mv;
	}
	
	
	/**
	 * 执行添加 损益表
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "incomeupdateInsert.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView incomeupdateInsert(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_syb",request);
		try {
			String customerId=request.getParameter("customerId");
			customerInforUpdateService.insertCustomerInforUpdateIncomeStatement(customerId, request);

			List<CustomerInforUpdateIncomeStatement> customerInforUpdateIncomeStatement =
                    customerInforUpdateService.getCustomerInforUpdateIncomeStatementById(customerId);

			mv.addObject("customerInforUpdateIncomeStatement",	customerInforUpdateIncomeStatement);
			mv.addObject("customerId",customerId);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行添加损益表错误"+e.getMessage());
		}
		return mv;
	}
	
	/**
	 * 执行添加现金流
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "cashFlowInsert.page", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView cashFlowInsert(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_xjl",request);
		try {
			String customerId=request.getParameter("customerId");
			customerInforUpdateService.insertCustomerInforUpdateCashFlow(customerId, request);

			List<CustomerInforUpdateCashFlow> customerInforUpdateCashFlow =
                    customerInforUpdateService.getCustomerInforUpdateCashFlowById(customerId);

			mv.addObject("customerInforUpdateCashFlow",customerInforUpdateCashFlow);
			mv.addObject("customerId",customerId);
		} catch (Exception e) {
            //TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			logger.error("执行添加现金流错误"+e.getMessage());
		}
		return mv;
	}
	
	/**
	 * 显示资产负债
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_zcfz.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_zcfz(HttpServletRequest request) {
		String customerId = request.getParameter(ID);

		List<CustomerInforUpdateBalanceSheet> customerInforUpdateBalanceSheet =
                customerInforUpdateService.getCustomerInforUpdateBalanceSheetById(customerId);

		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_zcfz",request);
		mv.addObject("customerInforUpdateBalanceSheet",	customerInforUpdateBalanceSheet);
		mv.addObject("customerId",customerId);
		return mv;
	}

    //TODO 以下几个并不执行实际操作的使用JRadOperation.CREATE方法？
	/**
	 * 浏览损益表
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "create_syb.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_syb( HttpServletRequest request) {
		String customerId = request.getParameter(ID);

		List<CustomerInforUpdateIncomeStatement> customerInforUpdateIncomeStatement =
                customerInforUpdateService.getCustomerInforUpdateIncomeStatementById(customerId);

		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_syb",request);
		mv.addObject("customerInforUpdateIncomeStatement",	customerInforUpdateIncomeStatement);
		mv.addObject("customerId",customerId);
		return mv;

	}

	/**
	 * 显示现金流表
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_xjl.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_xjl( HttpServletRequest request) {
		String customerId = request.getParameter(ID);

		List<CustomerInforUpdateCashFlow> customerInforUpdateCashFlow =
                customerInforUpdateService.getCustomerInforUpdateCashFlowById(customerId);

		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_xjl",request);
		mv.addObject("customerInforUpdateCashFlow",	customerInforUpdateCashFlow);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	/**
	 * 显示交叉检验
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_jcjy.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_jcjy(@ModelAttribute CustomerInforFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		String customerId = request.getParameter(ID);

		List<CustomerInforUpdateCrossExamination> crossExaminationList =
                customerInforUpdateService.getCustomerInforUpdateCrossExaminationById(customerId);

		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_jcjy",request);
		if(crossExaminationList.size() != 0){
			mv.setViewName("/customer/customerInforUpdate/customerinfoupdate_jcjy_update");
		}
		// 获取资产负载表的“所有者权益”
		CustomerInforUpdateBalanceSheet balanceSheet = customerInforUpdateService.findOwnershipFilter(customerId);

		mv.addObject("sjsy", balanceSheet != null ? balanceSheet.getContentsTextNumbers() : "");
		mv.addObject("crossExaminationList",	crossExaminationList);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	/**
	 * 执行添加交叉检验
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "saveCrossExamination.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView saveCrossExamination(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_jcjy_update",
                                                    request);
		try{
		String jsonStr = RequestHelper.getStringValue(request, "crossExaminationInfo");
		String customerId = RequestHelper.getStringValue(request, "customerId");
        IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		customerInforUpdateService.insertCustomerInforUpdateCrossExamination(customerId, user.getId(), jsonStr);

		List<CustomerInforUpdateCrossExamination> crossExaminationList =
                customerInforUpdateService.getCustomerInforUpdateCrossExaminationById(customerId);
		mv.addObject("customerId",customerId);
		mv.addObject("crossExaminationList",	crossExaminationList);
		// 获取资产负载表的“所有者权益”
		CustomerInforUpdateBalanceSheet balanceSheet = customerInforUpdateService.findOwnershipFilter(customerId);

		mv.addObject("sjsy", balanceSheet != null ? balanceSheet.getContentsTextNumbers() : "");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("执行添加交叉检验错误"+e.getMessage());
		}
		return mv;
	}
	
	/**
	 * 显示陌拜信息
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_mbxx.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_mbxx( HttpServletRequest request) {
		String customerId = request.getParameter("id");
		CustomerInfor customer = customerInforService.findCustomerInforById(customerId);
		CustomerinforUpdateWorship customerinforUpdateWorship =
                customerInforUpdateService.getCustomerinforUpdateWorshipById(customerId);
		String province ="";
		String city ="";
		String country ="";
		if(customerinforUpdateWorship != null){
			
			String area = customerinforUpdateWorship.getArea();
			if( area != null){
				String[] result = area.split(AREA_SEPARATOR);
			    province = result[0];
                city = result[1];
                country =result[2];
			}
			
			
		}
		
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_mbxx",request);
		mv.addObject("customerinforUpdateWorship",	customerinforUpdateWorship);
		mv.addObject("customerId", customerId);
		mv.addObject("customer", customer);
		mv.addObject("province", province);
		mv.addObject("city", city);
		mv.addObject("country", country);
		
		return mv;
	}
	/**
	 * 显示陌拜信息快照
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "mbxxclone.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display_mbxx( HttpServletRequest request) {
		String applicationId = request.getParameter("applicationId");
		String customerId = request.getParameter("customerId");
		CustomerInfor customer = customerInforService.findCustomerInforById(customerId);
		CustomerinforUpdateWorship customerinforUpdateWorship =
                customerInforUpdateService.getCustomerinforUpdateWorshipByIntoId(applicationId);
		String province ="";
		String city ="";
		String country ="";
		if(customerinforUpdateWorship != null){
			
			String area = customerinforUpdateWorship.getArea();
			if( area != null){
				String[] result = area.split(AREA_SEPARATOR);
			    province = result[0];
                city = result[1];
                country =result[2];
			}
			
			
		}
		
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforDisplay/customerinfoupdate_mbxx",request);
		mv.addObject("customerinforUpdateWorship",	customerinforUpdateWorship);
		mv.addObject("customerId", customerId);
		mv.addObject("applicationId", applicationId);
		mv.addObject("customer", customer);
		mv.addObject("province", province);
		mv.addObject("city", city);
		mv.addObject("country", country);
		
		return mv;
	}
	
	/**
	 * 执行修改陌拜信息
	 * 
	 * @param CustomerInforUpdateWorshipForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWoeship.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute CustomerInforUpdateWorshipForm customerInforUpdateWorshipForm, HttpServletRequest request) {
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		String province = request.getParameter("Select1");
		String city = request.getParameter("Select2");
		String country = request.getParameter("Select3");
		String suggestTimeAgain = request.getParameter("suggestMarketingTimeAgain");
		Date suggestMarketingTimeAgain = DateHelper.getDateFormat(suggestTimeAgain, "yyyy-MM-dd HH:mm:ss");
		String marketingTimestr = request.getParameter("marketingTime");
		Date marketingTime = DateHelper.getDateFormat(marketingTimestr, "yyyy-MM-dd HH:mm:ss");
		String area = province + AREA_SEPARATOR +city + AREA_SEPARATOR + country;
		customerInforUpdateWorshipForm.setArea(area);
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), customerInforUpdateWorshipForm);
		if (returnMap.isSuccess()) {
			try {
				CustomerinforUpdateWorship customerinforUpdateWorship = customerInforUpdateWorshipForm.createModel(CustomerinforUpdateWorship.class);
				customerinforUpdateWorship.setSuggestMarketingTimeAgain(suggestMarketingTimeAgain);
				customerinforUpdateWorship.setMarketingTime(marketingTime);
				String customerId =  customerinforUpdateWorship.getCustomerId();
				customerInforUpdateService.updateCustomerinforUpdateWorship(customerinforUpdateWorship,loginId);
				returnMap.put("customerId",customerId);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}
			catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DataBindHelper.initStandardBinder(binder);
	}
	
	
	/**
	 * 跳转到增加客户信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor/customerinfor_create", request);
		return mv;
	}
	
	/**
	 * 执行添加客户信息
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap insert(@ModelAttribute CustomerInforForm customerinfoForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), customerinfoForm);
		if (returnMap.isSuccess()) {
			try {
				CustomerInfor customerinfor = customerinfoForm.createModel(CustomerInfor.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				customerinfor.setCreatedBy(user.getId());
				customerinfor.setUserId(user.getId());
				String id = customerInforservice.insertCustomerInfor(customerinfor);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}

	/**
	 * 加入黑名单页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "blacklistcreate.page")
	@JRadOperation(JRadOperation.ADDBLACKLIST)
	public AbstractModelAndView blacklistCreate(@ModelAttribute RiskCustomerFilter filter, HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/riskcontrol/riskCustomer/riskcustomer_create", request);
		RiskAttributeFilter rafilter = new RiskAttributeFilter();
		// 查询线下风险属性
		rafilter.setType(RiskAttributeTypeEnum.OFFLINE.toString());
		List<RiskAttribute> ralist = riskAttributeService.findRiskAttributeListByFilter(rafilter);
		RiskAttribute riskAttribute = null;
		Iterator<RiskAttribute> iterator = ralist.iterator();
		while(iterator.hasNext()){
			riskAttribute = iterator.next();
			if(!(RiskAttributeEnum.QZ.toString().equals(riskAttribute.getRiskAttribute())
					|| RiskAttributeEnum.FYQZZX.toString().equals(riskAttribute.getRiskAttribute()))){
				iterator.remove();
			}
		}
		mv.addObject("ralist", ralist);
		return mv;
	}
	
	/**
	 * 移交客户
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "transfer.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.TRANSFER)
	public Map<String,Object> transfer(HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			String customerId = RequestHelper.getStringValue(request, ID);
			boolean flag = divisionalservice.insertDivisionalCustomer(customerId,DivisionalTypeEnum.initiative,DivisionalProgressEnum.charge);
			if(flag){
				returnMap.put(JRadConstants.SUCCESS,true);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERSUCCESS);
			}else{
				returnMap.put(JRadConstants.SUCCESS,false);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERERROR);
			}
		}catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG+ e.getMessage());
			returnMap.put(JRadConstants.SUCCESS, false);
			return returnMap;
		}
		return returnMap;
	}
	
	/**
	 * 添加客户基本资料附件信息
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "changewh.page")
	@JRadOperation(JRadOperation.MAINTENANCE)
	public AbstractModelAndView changewh(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfor_change", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
		}
		return mv;
	}
	
	
	/**
	 * 执行修改客户基本资料
	 * 
	 * @param sample2
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute CustomerInforForm form, HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				CustomerInfor customerInfor = form.createModel(CustomerInfor.class);
				customerInforservice.updateCustomerInfor(customerInfor);
				returnMap.put(RECORD_ID, customerInfor.getId());
				returnMap.put("customerId",customerInfor.getId());
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			}catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	/**
	 * 职业信息浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "create_zyxx.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView browsezyxx(HttpServletRequest request) {
        String customerId = RequestHelper.getStringValue(request, ID);
        String customerName = customerInforService.findCustomerInforById(customerId).getChineseName();
        CustomerCareersWeb careers = customerCareersService.findCustomerCareersByCustomerId(customerId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_zyxx",request);
        if(careers==null){
        	mv.addObject("customerName",customerName);
        }else{
        	mv.addObject("careers",careers);
        }
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	/**
	 * 职业信息执行修改
	 * 
	 * @param CustomerCareersForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatezyxx.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updatezyxx(@ModelAttribute CustomerCareersForm form, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), form);
		if (returnMap.isSuccess()) {
			try {
				CustomerCareersInformation customerCareersInformation = form.createModel(CustomerCareersInformation.class);
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String createdBy = user.getId();
				customerCareersInformation.setCreatedBy(createdBy);
				customerCareersInformation.setModifiedBy(createdBy);
				boolean flag = customerCareersService.updateCustomerCareersInformation(customerCareersInformation);
				if(flag){
					returnMap.put(JRadConstants.SUCCESS, true);
					returnMap.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
				}else{
					returnMap.put(JRadConstants.SUCCESS, false);
					returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.UPDATEERROR);
				}
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	/**
	 * 跳转到创建四维评估页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_swpg.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_swpg(HttpServletRequest request) { 
		String customerId = request.getParameter("id");
		CustomerInfor customer = customerInforService.findCustomerInforById(customerId);
		Dimensional dimensional = dimensionalService.findDimensionalByCustomerId(customerId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_swpg", request);
		mv.addObject("customerId", customerId);
		mv.addObject("customer", customer);
		mv.addObject("dimensional", dimensional);
		return mv;
	} 
	/**
	 * 插入四维评估表
	 * 
	 * @param dimensionalForm
	 * @param request
	 * @return
	 */

	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "insertswpg.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap insert(@ModelAttribute DimensionalForm dimensionalForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), dimensionalForm);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String customerId = request.getParameter("customerId");
				Dimensional dimensional = dimensionalForm.createModel(Dimensional.class);
				String chineseName = request.getParameter("chineseName");
				String sex = request.getParameter("sex");
				String cardId = request.getParameter("cardId");
				dimensional.setCustomerName(chineseName);
				dimensional.setCustomerId(customerId);
				dimensional.setGender(sex);
				dimensional.setIdCard(cardId);
				dimensional.setCreatedBy(loginId);
				String id = dimensionalService.insertDimensional(dimensional);
				returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);

			} catch (Exception e) {
				// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

	/**
	 * 更新四维评估表
	 * 
	 * @param dimensionalForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "updateswpg.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updateswpg(@ModelAttribute DimensionalForm dimensionalForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), dimensionalForm);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String customerId = request.getParameter("customerId");
				Dimensional dimensional = dimensionalForm.createModel(Dimensional.class);
				String chineseName = request.getParameter("chineseName");
				String sex = request.getParameter("sex");
				String cardId = request.getParameter("cardId");
				dimensional.setCustomerName(chineseName);
				dimensional.setCustomerId(customerId);
				dimensional.setGender(sex);
				dimensional.setIdCard(cardId);
				dimensional.setCreatedBy(loginId);
				dimensionalService.updateDimensionalByCustomerId(dimensional);
				returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);

			} catch (Exception e) {
				// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/**
	 * 跳转到创建授信额度页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_sxpg.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_sxpg(HttpServletRequest request) { 
		String customerId = request.getParameter("id");
		CustomerInfor customer = customerInforService.findCustomerInforById(customerId);
		CustomerCreditEvaluation customerCreidtEvaluation = customerCreditEvaluationService.findCustomerCreidtEvaluationByCustomerId(customerId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_sxpg", request);
		mv.addObject("customerId", customerId);
		mv.addObject("customer", customer);
		mv.addObject("customerCreidtEvaluation", customerCreidtEvaluation);
		return mv;
	}
	
	/**
	 * 插入授信评估表
	 * 
	 * @param CustomerCreidtEvaluationForm
	 * @param request
	 * @return
	 */

	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "insertsxpg.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap insertsxpg(@ModelAttribute CustomerCreidtEvaluationForm customerCreidtEvaluationForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), customerCreidtEvaluationForm);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String customerId = request.getParameter("customerId");
				CustomerCreditEvaluation customerCreditEvaluation = new CustomerCreditEvaluation();
				String model = request.getParameter("model");
				if (StringUtils.isNotEmpty(model) && model.equals("selfHouse")) {
					String selfHouseValue = request.getParameter("selfHouseValue");
					String selfDebitRemainingValue = request.getParameter("selfDebitRemainingValue");
					String selfCarValue = request.getParameter("selfCarValue");
					String selfTotalUsedAmountMax = request.getParameter("selfTotalUsedAmountMax");
					String selfOtherValue = request.getParameter("selfOtherValue");
					String selfTotalCreditAmount = request.getParameter("selfTotalCreditAmount");
					String selfApplicantExternalAmount = request.getParameter("selfApplicantExternalAmount");
					//String selfWetValue = request.getParameter("selfWetValue");
					String selfOtherDebitValue = request.getParameter("selfOtherDebitValue");
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setHouseValue(selfHouseValue);
					customerCreditEvaluation.setDebitRemainingValue(selfDebitRemainingValue);
					customerCreditEvaluation.setCarValue(selfCarValue);
					customerCreditEvaluation.setTotalUsedAmountMax(selfTotalUsedAmountMax);
					customerCreditEvaluation.setOtherValue(selfOtherValue);
					customerCreditEvaluation.setTotalCreditAmount(selfTotalCreditAmount);
					customerCreditEvaluation.setApplicantExternalAmount(selfApplicantExternalAmount);
					//customerCreditEvaluation.setWetValue(selfWetValue);
					customerCreditEvaluation.setOtherDebitValue(selfOtherDebitValue);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
				}else if(StringUtils.isNotEmpty(model) && model.equals("familyHouse")) {
				
					String familyHouseValue = request.getParameter("familyHouseValue");
					String familyDebitRemainingValue = request.getParameter("familyDebitRemainingValue");
					String familyCarValue = request.getParameter("familyCarValue");
					String familyTotalUsedAmountMax = request.getParameter("familyTotalUsedAmountMax");
					String familyOtherValue = request.getParameter("familyOtherValue");
					String familyTotalCreditAmount = request.getParameter("familyTotalCreditAmount");
					String familyApplicantExternalAmount = request.getParameter("familyApplicantExternalAmount");
					//String familyWetValue = request.getParameter("familyWetValue");
					String familyOtherDebitValue = request.getParameter("familyOtherDebitValue");
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setHouseValue(familyHouseValue);
					customerCreditEvaluation.setDebitRemainingValue(familyDebitRemainingValue);
					customerCreditEvaluation.setCarValue(familyCarValue);
					customerCreditEvaluation.setTotalUsedAmountMax(familyTotalUsedAmountMax);
					customerCreditEvaluation.setOtherValue(familyOtherValue);
					customerCreditEvaluation.setTotalCreditAmount(familyTotalCreditAmount);
					customerCreditEvaluation.setApplicantExternalAmount(familyApplicantExternalAmount);
					//customerCreditEvaluation.setWetValue(familyWetValue);
					customerCreditEvaluation.setOtherDebitValue(familyOtherDebitValue);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
					
				}else if(StringUtils.isNotEmpty(model) && model.equals("sanjian")) {
					String dailyFlowOver10000 = request.getParameter("dailyFlowOver10000"); 
					String age3550 = request.getParameter("age3550"); 
					String inland = request.getParameter("inland"); 
					String married = request.getParameter("married"); 
					String haveRecord = request.getParameter("haveRecord"); 
					String creidtBelow70per = request.getParameter("creidtBelow70per"); 
					String sexFemale = request.getParameter("sexFemale"); 
					String livelihood = request.getParameter("livelihood"); 
					String ourBusiness = request.getParameter("ourBusiness"); 
					String haveLoans = request.getParameter("haveLoans"); 
					String carValueOver5 = request.getParameter("carValueOver5"); 
					String selfBusinessLifeOver1 = request.getParameter("selfBusinessLifeOver1"); 
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setDailyFlowOver10000(dailyFlowOver10000);
					customerCreditEvaluation.setAge3550(age3550);
					customerCreditEvaluation.setInland(inland);
					customerCreditEvaluation.setMarried(married);
					customerCreditEvaluation.setHaveRecord(haveRecord);
					customerCreditEvaluation.setCreidtBelow70per(creidtBelow70per);
					customerCreditEvaluation.setSexFemale(sexFemale);
					customerCreditEvaluation.setLivelihood(livelihood);
					customerCreditEvaluation.setOurBusiness(ourBusiness);
					customerCreditEvaluation.setHaveLoans(haveLoans);
					customerCreditEvaluation.setCarValueOver5(carValueOver5);
					customerCreditEvaluation.setSelfBusinessLifeOver1(selfBusinessLifeOver1);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
				}else if(StringUtils.isNotEmpty(model) && model.equals("cunkuan")) {
					String averageDailyFlow = request.getParameter("averageDailyFlow"); 
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setAverageDailyFlow(averageDailyFlow);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
				}else if(StringUtils.isNotEmpty(model) && model.equals("gujin")) {
					String capitalValue = request.getParameter("capitalValue"); 
					String familyLineCredit = request.getParameter("familyLineCredit"); 
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setCapitalValue(capitalValue);
					customerCreditEvaluation.setFamilyLineCredit(familyLineCredit);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
				}else if(StringUtils.isNotEmpty(model) && model.equals("needGuaraLocal")) {
					customerCreditEvaluation.setModelType(model);
					String guaranteeType = request.getParameter("guaranteeType");
					if (StringUtils.isNotEmpty(guaranteeType) && guaranteeType.equals("haveHouseGuarantee")) {
						String guarantorPropertyValue = request.getParameter("guarantorPropertyValue"); 
						String guarantorCreditLoan = request.getParameter("guarantorCreditLoan"); 
						String guarantorCarValue = request.getParameter("guarantorCarValue"); 
						String guarantorCreditUsedMax = request.getParameter("guarantorCreditUsedMax"); 
						String guarantorOtherAssets = request.getParameter("guarantorOtherAssets"); 
						String guarantorCreditTotalLimit = request.getParameter("guarantorCreditTotalLimit"); 
						String applicantAssets = request.getParameter("applicantAssets"); 
						String guarantorExternalAmount = request.getParameter("guarantorExternalAmount"); 
						String applicantCreditLoanSum = request.getParameter("applicantCreditLoanSum"); 
						String applicantCreditUsedMax = request.getParameter("applicantCreditUsedMax"); 
						String applicantCreditTotalQuota = request.getParameter("applicantCreditTotalQuota"); 
						String applicantExternalAmount = request.getParameter("applicantExternalAmount"); 
						
						customerCreditEvaluation.setGuaranteeType(guaranteeType);
						customerCreditEvaluation.setGuarantorPropertyValue(guarantorPropertyValue);
						customerCreditEvaluation.setGuarantorCreditLoan(guarantorCreditLoan);
						customerCreditEvaluation.setGuarantorCarValue(guarantorCarValue);
						customerCreditEvaluation.setGuarantorCreditUsedMax(guarantorCreditUsedMax);
						customerCreditEvaluation.setGuarantorOtherAssets(guarantorOtherAssets);
						customerCreditEvaluation.setGuarantorCreditTotalLimit(guarantorCreditTotalLimit);
						customerCreditEvaluation.setApplicantAssets(applicantAssets);
						customerCreditEvaluation.setGuarantorExternalAmount(guarantorExternalAmount);
						customerCreditEvaluation.setApplicantCreditLoanSum(applicantCreditLoanSum);
						customerCreditEvaluation.setApplicantCreditUsedMax(applicantCreditUsedMax);
						customerCreditEvaluation.setApplicantCreditTotalQuota(applicantCreditTotalQuota);
						customerCreditEvaluation.setApplicantExternalAmount(applicantExternalAmount);
						String chineseName = request.getParameter("chineseName");
						String sex = request.getParameter("sex");
						String cardId = request.getParameter("cardId");
						customerCreditEvaluation.setCustomerName(chineseName);
						customerCreditEvaluation.setCustomerId(customerId);
						customerCreditEvaluation.setGender(sex);
						customerCreditEvaluation.setIdCard(cardId);
						customerCreditEvaluation.setCreatedBy(loginId);
						String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
						
					}else{
						String guarantorAmount = request.getParameter("guarantorAmount");
						customerCreditEvaluation.setGuaranteeType(guaranteeType);
						customerCreditEvaluation.setGuarantorAmount(guarantorAmount);
						customerCreditEvaluation.setModelType(model);
						String chineseName = request.getParameter("chineseName");
						String sex = request.getParameter("sex");
						String cardId = request.getParameter("cardId");
						customerCreditEvaluation.setCustomerName(chineseName);
						customerCreditEvaluation.setCustomerId(customerId);
						customerCreditEvaluation.setGender(sex);
						customerCreditEvaluation.setIdCard(cardId);
						customerCreditEvaluation.setCreatedBy(loginId);
						String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
					}
					
				}else if(StringUtils.isNotEmpty(model) && model.equals("needGuarnExarl")){
					String guaranteeType = request.getParameter("guaranteeType_1");
					if (StringUtils.isNotEmpty(guaranteeType) && guaranteeType.equals("haveHouseGuarantee")) {
						String guarantorPropertyValue = request.getParameter("guarantorPropertyValue_1"); 
						String guarantorCreditLoan = request.getParameter("guarantorCreditLoan_1"); 
						String guarantorCarValue = request.getParameter("guarantorCarValue_1"); 
						String guarantorCreditUsedMax = request.getParameter("guarantorCreditUsedMax_1"); 
						String guarantorOtherAssets = request.getParameter("guarantorOtherAssets_1"); 
						String guarantorCreditTotalLimit = request.getParameter("guarantorCreditTotalLimit_1"); 
						String applicantAssets = request.getParameter("applicantAssets_1"); 
						String guarantorExternalAmount = request.getParameter("guarantorExternalAmount_1"); 
						String applicantCreditLoanSum = request.getParameter("applicantCreditLoanSum_1"); 
						String applicantCreditUsedMax = request.getParameter("applicantCreditUsedMax_1"); 
						String applicantCreditTotalQuota = request.getParameter("applicantCreditTotalQuota_1"); 
						String applicantExternalAmount = request.getParameter("applicantExternalAmount_1"); 
						String chineseName = request.getParameter("chineseName");
						String sex = request.getParameter("sex");
						String cardId = request.getParameter("cardId");
						customerCreditEvaluation.setCustomerName(chineseName);
						customerCreditEvaluation.setCustomerId(customerId);
						customerCreditEvaluation.setGender(sex);
						customerCreditEvaluation.setIdCard(cardId);
						customerCreditEvaluation.setCreatedBy(loginId);
						
						customerCreditEvaluation.setGuaranteeType(guaranteeType);
						customerCreditEvaluation.setModelType(model);
						customerCreditEvaluation.setGuarantorPropertyValue(guarantorPropertyValue);
						customerCreditEvaluation.setGuarantorCreditLoan(guarantorCreditLoan);
						customerCreditEvaluation.setGuarantorCarValue(guarantorCarValue);
						customerCreditEvaluation.setGuarantorCreditUsedMax(guarantorCreditUsedMax);
						customerCreditEvaluation.setGuarantorOtherAssets(guarantorOtherAssets);
						customerCreditEvaluation.setGuarantorCreditTotalLimit(guarantorCreditTotalLimit);
						customerCreditEvaluation.setApplicantAssets(applicantAssets);
						customerCreditEvaluation.setGuarantorExternalAmount(guarantorExternalAmount);
						customerCreditEvaluation.setApplicantCreditLoanSum(applicantCreditLoanSum);
						customerCreditEvaluation.setApplicantCreditUsedMax(applicantCreditUsedMax);
						customerCreditEvaluation.setApplicantCreditTotalQuota(applicantCreditTotalQuota);
						customerCreditEvaluation.setApplicantExternalAmount(applicantExternalAmount);
						String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
						
					}else{
						String guarantorAmount = request.getParameter("guarantorAmount_1");
						customerCreditEvaluation.setModelType(model);
						customerCreditEvaluation.setGuaranteeType(guaranteeType);
						customerCreditEvaluation.setGuarantorAmount(guarantorAmount);
						String chineseName = request.getParameter("chineseName");
						String sex = request.getParameter("sex");
						String cardId = request.getParameter("cardId");
						customerCreditEvaluation.setCustomerName(chineseName);
						customerCreditEvaluation.setCustomerId(customerId);
						customerCreditEvaluation.setGender(sex);
						customerCreditEvaluation.setIdCard(cardId);
						customerCreditEvaluation.setCreatedBy(loginId);
						String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
					}
					
				}else if(StringUtils.isNotEmpty(model) && model.equals("highQuailty")) {
						String highQuailtyIndustry = request.getParameter("highQuailtyIndustry");
						customerCreditEvaluation.setHighQuailtyIndustry(highQuailtyIndustry);
						customerCreditEvaluation.setModelType(model);
						String id = customerCreditEvaluationService.insertCustomerCreditEvaluation(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CREATE_SUCCESS);
				}

			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/**
	 * 更新授信评估表
	 * 
	 * @param CustomerCreidtEvaluationForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "updatesxpg.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updatesxpg(@ModelAttribute CustomerCreidtEvaluationForm customerCreidtEvaluationForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), customerCreidtEvaluationForm);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String customerId = request.getParameter("customerId");
				//CustomerCreditEvaluation customerCreditEvaluation = customerCreidtEvaluationForm.createModel(CustomerCreditEvaluation.class);
				CustomerCreditEvaluation customerCreditEvaluation = new CustomerCreditEvaluation();
				String model = request.getParameter("model");
				if (StringUtils.isNotEmpty(model) && model.equals("selfHouse")) {
					String selfHouseValue = request.getParameter("selfHouseValue");
					String selfDebitRemainingValue = request.getParameter("selfDebitRemainingValue");
					String selfCarValue = request.getParameter("selfCarValue");
					String selfTotalUsedAmountMax = request.getParameter("selfTotalUsedAmountMax");
					String selfOtherValue = request.getParameter("selfOtherValue");
					String selfTotalCreditAmount = request.getParameter("selfTotalCreditAmount");
					String selfApplicantExternalAmount = request.getParameter("selfApplicantExternalAmount");
					//String selfWetValue = request.getParameter("selfWetValue");
					String selfOtherDebitValue = request.getParameter("selfOtherDebitValue");
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setHouseValue(selfHouseValue);
					customerCreditEvaluation.setDebitRemainingValue(selfDebitRemainingValue);
					customerCreditEvaluation.setCarValue(selfCarValue);
					customerCreditEvaluation.setTotalUsedAmountMax(selfTotalUsedAmountMax);
					customerCreditEvaluation.setOtherValue(selfOtherValue);
					customerCreditEvaluation.setTotalCreditAmount(selfTotalCreditAmount);
					customerCreditEvaluation.setApplicantExternalAmount(selfApplicantExternalAmount);
					//customerCreditEvaluation.setWetValue(selfWetValue);
					customerCreditEvaluation.setOtherDebitValue(selfOtherDebitValue);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
				}else if(StringUtils.isNotEmpty(model) && model.equals("familyHouse")) {
				
					String familyHouseValue = request.getParameter("familyHouseValue");
					String familyDebitRemainingValue = request.getParameter("familyDebitRemainingValue");
					String familyCarValue = request.getParameter("familyCarValue");
					String familyTotalUsedAmountMax = request.getParameter("familyTotalUsedAmountMax");
					String familyOtherValue = request.getParameter("familyOtherValue");
					String familyTotalCreditAmount = request.getParameter("familyTotalCreditAmount");
					String familyApplicantExternalAmount = request.getParameter("familyApplicantExternalAmount");
					//String familyWetValue = request.getParameter("familyWetValue");
					String familyOtherDebitValue = request.getParameter("familyOtherDebitValue");
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setHouseValue(familyHouseValue);
					customerCreditEvaluation.setDebitRemainingValue(familyDebitRemainingValue);
					customerCreditEvaluation.setCarValue(familyCarValue);
					customerCreditEvaluation.setTotalUsedAmountMax(familyTotalUsedAmountMax);
					customerCreditEvaluation.setOtherValue(familyOtherValue);
					customerCreditEvaluation.setTotalCreditAmount(familyTotalCreditAmount);
					customerCreditEvaluation.setApplicantExternalAmount(familyApplicantExternalAmount);
					//customerCreditEvaluation.setWetValue(familyWetValue);
					customerCreditEvaluation.setOtherDebitValue(familyOtherDebitValue);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
					
				}else if(StringUtils.isNotEmpty(model) && model.equals("sanjian")) {
					String dailyFlowOver10000 = request.getParameter("dailyFlowOver10000"); 
					String age3550 = request.getParameter("age3550"); 
					String inland = request.getParameter("inland"); 
					String married = request.getParameter("married"); 
					String haveRecord = request.getParameter("haveRecord"); 
					String creidtBelow70per = request.getParameter("creidtBelow70per"); 
					String sexFemale = request.getParameter("sexFemale"); 
					String livelihood = request.getParameter("livelihood"); 
					String ourBusiness = request.getParameter("ourBusiness"); 
					String haveLoans = request.getParameter("haveLoans"); 
					String carValueOver5 = request.getParameter("carValueOver5"); 
					String selfBusinessLifeOver1 = request.getParameter("selfBusinessLifeOver1"); 
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setDailyFlowOver10000(dailyFlowOver10000);
					customerCreditEvaluation.setAge3550(age3550);
					customerCreditEvaluation.setInland(inland);
					customerCreditEvaluation.setMarried(married);
					customerCreditEvaluation.setHaveRecord(haveRecord);
					customerCreditEvaluation.setCreidtBelow70per(creidtBelow70per);
					customerCreditEvaluation.setSexFemale(sexFemale);
					customerCreditEvaluation.setLivelihood(livelihood);
					customerCreditEvaluation.setOurBusiness(ourBusiness);
					customerCreditEvaluation.setHaveLoans(haveLoans);
					customerCreditEvaluation.setCarValueOver5(carValueOver5);
					customerCreditEvaluation.setSelfBusinessLifeOver1(selfBusinessLifeOver1);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
				}else if(StringUtils.isNotEmpty(model) && model.equals("cunkuan")) {
					String averageDailyFlow = request.getParameter("averageDailyFlow"); 
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setAverageDailyFlow(averageDailyFlow);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
				}else if(StringUtils.isNotEmpty(model) && model.equals("gujin")) {
					String capitalValue = request.getParameter("capitalValue"); 
					String familyLineCredit = request.getParameter("familyLineCredit"); 
					customerCreditEvaluation.setModelType(model);
					customerCreditEvaluation.setCapitalValue(capitalValue);
					customerCreditEvaluation.setFamilyLineCredit(familyLineCredit);
					String chineseName = request.getParameter("chineseName");
					String sex = request.getParameter("sex");
					String cardId = request.getParameter("cardId");
					customerCreditEvaluation.setCustomerName(chineseName);
					customerCreditEvaluation.setCustomerId(customerId);
					customerCreditEvaluation.setGender(sex);
					customerCreditEvaluation.setIdCard(cardId);
					customerCreditEvaluation.setCreatedBy(loginId);
					customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
					returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
				}else if(StringUtils.isNotEmpty(model) && model.equals("needGuaraLocal")) {
					customerCreditEvaluation.setModelType(model);
					String guaranteeType = request.getParameter("guaranteeType");
					if (StringUtils.isNotEmpty(guaranteeType) && guaranteeType.equals("haveHouseGuarantee")) {
						String guarantorPropertyValue = request.getParameter("guarantorPropertyValue"); 
						String guarantorCreditLoan = request.getParameter("guarantorCreditLoan"); 
						String guarantorCarValue = request.getParameter("guarantorCarValue"); 
						String guarantorCreditUsedMax = request.getParameter("guarantorCreditUsedMax"); 
						String guarantorOtherAssets = request.getParameter("guarantorOtherAssets"); 
						String guarantorCreditTotalLimit = request.getParameter("guarantorCreditTotalLimit"); 
						String applicantAssets = request.getParameter("applicantAssets"); 
						String guarantorExternalAmount = request.getParameter("guarantorExternalAmount"); 
						String applicantCreditLoanSum = request.getParameter("applicantCreditLoanSum"); 
						String applicantCreditUsedMax = request.getParameter("applicantCreditUsedMax"); 
						String applicantCreditTotalQuota = request.getParameter("applicantCreditTotalQuota"); 
						String applicantExternalAmount = request.getParameter("applicantExternalAmount"); 
						
						customerCreditEvaluation.setGuaranteeType(guaranteeType);
						customerCreditEvaluation.setGuarantorPropertyValue(guarantorPropertyValue);
						customerCreditEvaluation.setGuarantorCreditLoan(guarantorCreditLoan);
						customerCreditEvaluation.setGuarantorCarValue(guarantorCarValue);
						customerCreditEvaluation.setGuarantorCreditUsedMax(guarantorCreditUsedMax);
						customerCreditEvaluation.setGuarantorOtherAssets(guarantorOtherAssets);
						customerCreditEvaluation.setGuarantorCreditTotalLimit(guarantorCreditTotalLimit);
						customerCreditEvaluation.setApplicantAssets(applicantAssets);
						customerCreditEvaluation.setGuarantorExternalAmount(guarantorExternalAmount);
						customerCreditEvaluation.setApplicantCreditLoanSum(applicantCreditLoanSum);
						customerCreditEvaluation.setApplicantCreditUsedMax(applicantCreditUsedMax);
						customerCreditEvaluation.setApplicantCreditTotalQuota(applicantCreditTotalQuota);
						customerCreditEvaluation.setApplicantExternalAmount(applicantExternalAmount);
						String chineseName = request.getParameter("chineseName");
						String sex = request.getParameter("sex");
						String cardId = request.getParameter("cardId");
						customerCreditEvaluation.setCustomerName(chineseName);
						customerCreditEvaluation.setCustomerId(customerId);
						customerCreditEvaluation.setGender(sex);
						customerCreditEvaluation.setIdCard(cardId);
						customerCreditEvaluation.setCreatedBy(loginId);
						customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
						
					}else{
						String guarantorAmount = request.getParameter("guarantorAmount");
						customerCreditEvaluation.setGuaranteeType(guaranteeType);
						customerCreditEvaluation.setGuarantorAmount(guarantorAmount);
						customerCreditEvaluation.setModelType(model);
						customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
					}
					
				}else if(StringUtils.isNotEmpty(model) && model.equals("needGuarnExarl")){
					String guaranteeType = request.getParameter("guaranteeType_1");
					if (StringUtils.isNotEmpty(guaranteeType) && guaranteeType.equals("haveHouseGuarantee")) {
						String guarantorPropertyValue = request.getParameter("guarantorPropertyValue_1"); 
						String guarantorCreditLoan = request.getParameter("guarantorCreditLoan_1"); 
						String guarantorCarValue = request.getParameter("guarantorCarValue_1"); 
						String guarantorCreditUsedMax = request.getParameter("guarantorCreditUsedMax_1"); 
						String guarantorOtherAssets = request.getParameter("guarantorOtherAssets_1"); 
						String guarantorCreditTotalLimit = request.getParameter("guarantorCreditTotalLimit_1"); 
						String applicantAssets = request.getParameter("applicantAssets_1"); 
						String guarantorExternalAmount = request.getParameter("guarantorExternalAmount_1"); 
						String applicantCreditLoanSum = request.getParameter("applicantCreditLoanSum_1"); 
						String applicantCreditUsedMax = request.getParameter("applicantCreditUsedMax_1"); 
						String applicantCreditTotalQuota = request.getParameter("applicantCreditTotalQuota_1"); 
						String applicantExternalAmount = request.getParameter("applicantExternalAmount_1"); 
						String chineseName = request.getParameter("chineseName");
						String sex = request.getParameter("sex");
						String cardId = request.getParameter("cardId");
						customerCreditEvaluation.setCustomerName(chineseName);
						customerCreditEvaluation.setCustomerId(customerId);
						customerCreditEvaluation.setGender(sex);
						customerCreditEvaluation.setIdCard(cardId);
						customerCreditEvaluation.setCreatedBy(loginId);
						
						customerCreditEvaluation.setGuaranteeType(guaranteeType);
						customerCreditEvaluation.setModelType(model);
						customerCreditEvaluation.setGuarantorPropertyValue(guarantorPropertyValue);
						customerCreditEvaluation.setGuarantorCreditLoan(guarantorCreditLoan);
						customerCreditEvaluation.setGuarantorCarValue(guarantorCarValue);
						customerCreditEvaluation.setGuarantorCreditUsedMax(guarantorCreditUsedMax);
						customerCreditEvaluation.setGuarantorOtherAssets(guarantorOtherAssets);
						customerCreditEvaluation.setGuarantorCreditTotalLimit(guarantorCreditTotalLimit);
						customerCreditEvaluation.setApplicantAssets(applicantAssets);
						customerCreditEvaluation.setGuarantorExternalAmount(guarantorExternalAmount);
						customerCreditEvaluation.setApplicantCreditLoanSum(applicantCreditLoanSum);
						customerCreditEvaluation.setApplicantCreditUsedMax(applicantCreditUsedMax);
						customerCreditEvaluation.setApplicantCreditTotalQuota(applicantCreditTotalQuota);
						customerCreditEvaluation.setApplicantExternalAmount(applicantExternalAmount);
						customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
						
					}else{
						String guarantorAmount = request.getParameter("guarantorAmount_1");
						customerCreditEvaluation.setModelType(model);
						customerCreditEvaluation.setGuaranteeType(guaranteeType);
						customerCreditEvaluation.setGuarantorAmount(guarantorAmount);
						customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
					}
					
				}else if(StringUtils.isNotEmpty(model) && model.equals("highQuailty")) {
						String highQuailtyIndustry = request.getParameter("highQuailtyIndustry");
						customerCreditEvaluation.setHighQuailtyIndustry(highQuailtyIndustry);
						customerCreditEvaluation.setModelType(model);
					    customerCreditEvaluationService.updateCustomerCreidtEvaluationByCustomerId(customerCreditEvaluation);
						returnMap.addGlobalMessage(JRadConstants.CHANGE_SUCCESS);
				}
				
			} catch (Exception e) {
				// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

	/**
	 * 浏览问卷调查页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_wjdc.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView browsewjdc(@ModelAttribute CustomerQuestionInfoFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		String customerId = request.getParameter("id");
		CustomerInfor customer = customerInforService.findCustomerInforById(customerId);
		List<Dict> questionDict = dictService.findDictByDictType(QuestionConstant.QuestionType);
		List<CustomerQuestionInfoForm> customerQuestionInfo = customerQuestionService.findCustomerQuestionByCustomerId(customerId);
		Map<String, String> codeAnswer = new HashMap<String, String>();
		for (Dict qd : questionDict) {
			codeAnswer.put(qd.getTypeCode(), qd.getTypeName());
		}
        if (customerQuestionInfo !=null && customerQuestionInfo.size()>0) {
        	for (CustomerQuestionInfoForm cq : customerQuestionInfo) {
				cq.setQuestionName(codeAnswer.get(cq.getQuestionCode()));
			}
		}else{
			 customerQuestionInfo = new ArrayList<CustomerQuestionInfoForm>();
			 for (Dict qD : questionDict) {
				CustomerQuestionInfoForm cqi = new CustomerQuestionInfoForm();
				cqi.setQuestionName(qD.getTypeName());
				cqi.setQuestionCode(qD.getTypeCode());
				customerQuestionInfo.add(cqi);
			}
			
		}
		JRadModelAndView mv = new JRadModelAndView("/customer/customerQuestion/customerQuestion", request);
		mv.addObject("customerQuestionInfo", customerQuestionInfo);
		mv.addObject("customer", customer);
		mv.addObject("customerId", customerId);
		return mv;
	}
	/**
	 * 问卷调查修改或增加
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertwjdc.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap insertwjdc(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			String customerId = request.getParameter("customerId");
			Map<String, String[]> paraMap = request.getParameterMap();
			Iterator<String> it = paraMap.keySet().iterator();
			List<String> qsCodeList = new ArrayList<String>();
			Map<String, String> codeAnswerMap = new HashMap<String, String>();
			while (it.hasNext()) {
				String key = it.next();
				if (key.contains("questionCode_")) {
					String[] quCodes = paraMap.get(key);
					if (quCodes.length > 0) {
						qsCodeList.add(quCodes[0]);
					}
				}
				if (key.contains("_questionAnswer")) {
					String quCode = key.split("_")[0];
					String[] avArr = paraMap.get(key);
					if (avArr.length > 0) {
						codeAnswerMap.put(quCode, avArr[0]);
					}
				}
			}

			List<CustomerQuestionInfo> customerQuestionInfos = new ArrayList<CustomerQuestionInfo>();
			for (String qcode : qsCodeList) {
				CustomerQuestionInfo info = new CustomerQuestionInfo();
				info.setCustomerId(customerId);
				info.setQuestionCode(qcode);
				info.setQuestionAnswer(codeAnswerMap.get(qcode));
				customerQuestionInfos.add(info);

			}
			customerQuestionService.insertOrUpdateCustomerQusetion(customerQuestionInfos, loginId);

			returnMap.addGlobalMessage("修改成功");
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}

	/**
	 * 浏览客户台账页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_khtz.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView browsekhtz(HttpServletRequest request) {
		String customerId = request.getParameter("id");
		CustomerInfor customer = customerInforService.findCustomerInforById(customerId);
		CustomerMainManager customerMainManager = customerMainManagerService.findCustomerMainManagerByCustomerId(customerId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerMainManager/customerMainManager", request);
		mv.addObject("customerMainManager", customerMainManager);
		mv.addObject("customer", customer);
		mv.addObject("customerId", customerId);
		return mv;
	}
	
	/**
	 * 客户台账执行添加
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertkhtz.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap insertkhtz(@ModelAttribute CustomerMainManagerForm customerMainManagerForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), customerMainManagerForm);
		if (returnMap.isSuccess()) {
			try {
				CustomerMainManager customerMainManager = customerMainManagerForm.createModel(CustomerMainManager.class);
				CustomerMainManager customerMainManagerExist = customerMainManagerService.findCustomerMainManagerByCustomerId(customerMainManager.getCustomerId());
				if (customerMainManagerExist != null) {
					customerMainManagerService.updateCustomerMainManager(customerMainManager);
				} else {
					String id = customerMainManagerService.insertCustomerMainManager(customerMainManager);
					returnMap.put(RECORD_ID, id);
				}
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	/**
	 * 链接到影像资料上传页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_yxzl.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createYxzl(@ModelAttribute VideoAccessoriesFilter filter,HttpServletRequest request) {
		filter.setCustomerId(request.getParameter(ID));
		filter.setRequest(request);
		QueryResult<VideoAccessories> result = customerInforservice.findCustomerVideoAccessoriesByFilter(filter);		
		JRadPagedQueryResult<VideoAccessories> pagedResult = new JRadPagedQueryResult<VideoAccessories>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforUpdate/customerinfoupdate_yxzl", request);
		mv.addObject("customerId",filter.getCustomerId());
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	/**
	 * 影像资料保存
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "saveYxzl.json",method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public Map<String,Object> saveYxzl(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, Constant.FILE_EMPTY);
				return map;
			}
			customerInforservice.saveYxzlByCustomerId(request.getParameter("customerId"),request.getParameter("remark"),file);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Constant.FAIL_MESSAGE);
			return map;
		}
		return map;
	}
	
	/**
	 * 删除影像资料
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	*/
	@ResponseBody
	@RequestMapping(value = "deleteYxzl.json",method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.DELETE)
	public Map<String,Object> deleteYxzlById(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			customerInforservice.deleteYxzlById(request.getParameter(ID));
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Constant.FAIL_MESSAGE);
			return map;
		}
		return map;
	}
	 
	/**
	 * 下载影像资料
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	*/
	@ResponseBody
	@RequestMapping(value = "downLoadYxzl.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView downLoadYxzlById(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			customerInforservice.downLoadYxzlById(response,request.getParameter(ID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	 
	/**
	 * 下载影像资料
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	
	 */
	@ResponseBody
	@RequestMapping(value = "viewPicture.page",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView viewPicture(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			File file = null;
			Map<String, String> map = customerInforservice
					.createThumbnail(request.getParameter(ID));
			if (Boolean.parseBoolean(request.getParameter("flag"))) {
				// 输出原始图片 
				file = new File(map.get("old"));
			} else {
				// 输出缩略图 
				file = new File(map.get("new"));
			}
			if (file.exists()) {
				response.setContentType("image/*");
				InputStream is = new FileInputStream(file);
				OutputStream os = response.getOutputStream();
				IOUtils.copy(is, os);
				is.close();
				os.flush();
				os.close();
			} else {
				response.setStatus(304);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

	