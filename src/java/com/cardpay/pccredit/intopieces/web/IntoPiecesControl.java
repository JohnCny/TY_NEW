package com.cardpay.pccredit.intopieces.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateBalanceSheet;
import com.cardpay.pccredit.customer.model.CustomerInforUpdateCrossExamination;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.intopieces.constant.CardStatus;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.MakeCardFilter;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLog;
import com.cardpay.pccredit.intopieces.model.AppManagerAuditLogForm;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContactVo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantorVo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcessForm;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecomVo;
import com.cardpay.pccredit.intopieces.model.CustomerCreditInfo;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.intopieces.model.PicPojo;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentBatch;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentDetail;
import com.cardpay.pccredit.intopieces.model.QzApplnAttachmentList;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.manager.constant.ManagerLevelAdjustmentConstant;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.system.model.Dict;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.DataBindHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

@Controller
@RequestMapping("/intopieces/intopiecesquery/*")
@JRadModule("intopieces.intopiecesquery")
public class IntoPiecesControl extends BaseController {
	public static final Logger logger = Logger.getLogger(IntoPiecesControl.class);
	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private MaintenanceService maintenanceService;

	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private CustomerInforDao customerInforDao;
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
	public AbstractModelAndView browse(@ModelAttribute IntoPiecesFilter filter,
			HttpServletRequest request) {
	/*	filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		//查询客户经理
		List<AccountManagerParameterForm> forms = maintenanceService.findSubListManagerByManagerId(user);
		if(forms != null && forms.size() > 0){
			StringBuffer userIds = new StringBuffer();
			userIds.append("(");
			for(AccountManagerParameterForm form : forms){
				userIds.append("'").append(form.getUserId()).append("'").append(",");
			}
			userIds = userIds.deleteCharAt(userIds.length() - 1);
			userIds.append(")");
			filter.setCustManagerIds(userIds.toString());
		}else{
			filter.setUserId(userId);
		}
		
		QueryResult<IntoPieces> result = intoPiecesService.findintoPiecesByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);*/
		
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String startAmt = request.getParameter("startAmt");
		String endAmt = request.getParameter("endAmt");
		if(startAmt ==null||startAmt==""){
			filter.setStartAmt("0");
		}
		if(endAmt ==null||endAmt==""){
			filter.setEndAmt(request.getParameter("endAmt"));
		}
		
		QueryResult<IntoPieces> result=null;
		String userId = user.getId();
		//客户经理
		if(user.getUserType() ==1){
			filter.setUserId(userId);
		}
		result = intoPiecesService.findintoPiecesByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(
				filter, result);

		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/intopieces_customer_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);


		return mv;
	}
	
	/**
	 * 审核通过进件查询
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "applyIntopiecesQuery.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView applyIntopiecesQuery(@ModelAttribute IntoPiecesFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<IntoPieces> result = intoPiecesService.findintoApplayPiecesByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/apply_intopieces_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 制卡中心查询制卡
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryCard.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView queryCard(@ModelAttribute MakeCardFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<MakeCard> result = intoPiecesService.findCardByFilter(filter);
		JRadPagedQueryResult<MakeCard> pagedResult = new JRadPagedQueryResult<MakeCard>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/card_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 客户经理查询制卡
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "managerQueryCard.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView managerQueryCard(@ModelAttribute MakeCardFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		filter.setCardOrganization(Beans.get(LoginManager.class).getLoggedInUser(request).getOrganization().getId());
		QueryResult<MakeCard> result = intoPiecesService.findCardByFilter(filter);
		JRadPagedQueryResult<MakeCard> pagedResult = new JRadPagedQueryResult<MakeCard>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/card_manager_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 卡片录入界面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertCard.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView insertCard(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/card_input", request);
		return mv;
	}
	
	/**
	 * 卡号保存
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value = "saveCardData.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public Map<String, Object> saveCardData(@ModelAttribute MakeCard makeCard,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		makeCard.setId(IDGenerator.generateID());
		makeCard.setCreatedTime(new Date());
		makeCard.setCreatedBy(Beans.get(LoginManager.class)
				.getLoggedInUser(request).getId());
		makeCard.setCardOrganizationStatus(CardStatus.ORGANIZATION_UNISSUED.toString());
		makeCard.setCardCustomerStatus(CardStatus.CUSTOMER_UNISSUED.toString());
		try {
			intoPiecesService.saveCard(makeCard);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class)
					.getMessageNotNull(Constant.SUCCESS_MESSAGE));
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class)
					.getMessageNotNull(Constant.FAIL_MESSAGE));
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
	
	/**
	 * 机构下发
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "organizationIssuedCard.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public Map<String, Object> organizationIssuedCard(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(Boolean.parseBoolean(request.getParameter("flag"))){
				intoPiecesService.organizationIssuedCard(request.getParameter(ID),null,CardStatus.CUSTOMER_ISSUED.toString());
			}else{
				intoPiecesService.organizationIssuedCard(request.getParameter(ID),CardStatus.ORGANIZATION_ISSUED.toString(),null);
			}
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class)
					.getMessageNotNull(Constant.SUCCESS_MESSAGE));
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class)
					.getMessageNotNull(Constant.FAIL_MESSAGE));
		}
		return map;
	}
	
	
	/**
	 * 查看卡片信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "viewCard.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView viewCard(HttpServletRequest request) {
		MakeCard makeCard = intoPiecesService.findMakeCardById(request.getParameter(ID));
		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/card_view", request);
		mv.addObject("makeCard", makeCard);
		return mv;
	}

	
	

	/**
	 * 选择客户信息
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "chooseCustomerName.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView chooseCustomerName(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO q?
		String userId = Beans.get(LoginManager.class)
				.getLoggedInUser(request).getId();
		String chineseName = request.getParameter("q");
		List<CustomerInfor> list = customerInforService
				.findCustomerInforByName(userId,chineseName);
		String json = JSONArray.fromObject(list).toString();
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 选择客户信息
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	// TODO 选择使用JRadOperation.CREATE？
	@ResponseBody
	@RequestMapping(value = "chooseCustomer.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView chooseCustomer(
			@ModelAttribute CustomerInforFilter filter,
			HttpServletRequest request) {
		filter.setRequest(request);
		filter.setUserId(Beans.get(LoginManager.class)
				.getLoggedInUser(request).getId());
		QueryResult<CustomerInfor> result = customerInforService
				.findCustomerInforByFilter(filter);
		JRadPagedQueryResult<CustomerInfor> pagedResult = new JRadPagedQueryResult<CustomerInfor>(
				filter, result);
		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/customer_choose", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		mv.addObject("filter", filter);
		return mv;
	}

	/**
	 * 批量添加进件
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "chooseCustomerById.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView chooseCustomerById(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/customer_choose_by_id", request);
		return mv;
	}

	/**
	 * 根据客户信息新增进件信息，将客户相关的信息带到后面进件新增页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "input.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView input(HttpServletRequest request) {
		/* 客户信息 */
		CustomerInfor customerInfor = null;
		/* 职业信息 */
		CustomerCareersInformation customerCareersInformation = null;
		/* 客户联系人 */
		List<CustomerApplicationContact> customerApplicationContactList = null;
		/* 客户担保人 */
		List<CustomerApplicationGuarantor> customerApplicationGuarantorList = null;
		/* 客户推荐人 */
		List<CustomerApplicationRecom> customerApplicationRecomList = null;
		/* 客户申请主表信息 */
		CustomerApplicationInfo customerApplicationInfo = null;
		/* 其他资料 */
		CustomerApplicationOther customerApplicationOther = null;
		/* 行社专栏 */
		CustomerApplicationCom customerApplicationCom = null;
		/* 行社专栏 */
		CustomerAccountData customerAccountData = null;
		/* 产品要上传的附件资料 */
		List<AppendixDict> appendixDictList = null;
		/* 实际上传的附件资料 */
		List<AddressAccessories> addressAccessoriesList = new ArrayList<AddressAccessories>();
		String tempParam = request.getParameter("intoPicesidAndCustorId");
		String viewFlag = request.getParameter("viewFlag");
		/*修改进件首先查询出进件信息*/
		if (StringUtils.trimToNull(tempParam) != null) {
			String[] tempArray = tempParam.split("_");
			customerInfor = customerInforService
					.findCustomerInforById(tempArray[1]);
			customerApplicationInfo = intoPiecesService
					.findCustomerApplicationInfoById(tempArray[0]);
			if (customerInfor != null) {
				customerCareersInformation = intoPiecesService
						.findCustomerCareersInformationByCustomerId(customerInfor
								.getId());
			}
			if (customerApplicationInfo != null) {
				customerApplicationContactList = intoPiecesService
						.findCustomerApplicationContactsByApplicationId(customerApplicationInfo
								.getId());
				customerApplicationGuarantorList = intoPiecesService
						.findCustomerApplicationGuarantorsByApplicationId(customerApplicationInfo
								.getId());
				customerApplicationRecomList = intoPiecesService
						.findCustomerApplicationRecomsByApplicationId(customerApplicationInfo
								.getId());
				customerApplicationOther = intoPiecesService
						.findCustomerApplicationOtherByApplicationId(customerApplicationInfo
								.getId());
				customerApplicationCom = intoPiecesService
						.findCustomerApplicationComByApplicationId(customerApplicationInfo
								.getId());
				customerAccountData = intoPiecesService
						.findCustomerAccountDataByApplicationId(customerApplicationInfo
								.getId());
				if (customerApplicationInfo != null
						&& customerApplicationInfo.getProductId() != null) {
					appendixDictList = productService
							.findAppendixByProductId(customerApplicationInfo
									.getProductId());
					addressAccessoriesList = intoPiecesService
							.findAddressAccessories(
									customerApplicationInfo.getId(),
									customerApplicationInfo.getProductId());
				}
			}
		} else {// 客户第一次填写进件信息
			if (StringUtils.trimToNull(request.getParameter("customerId")) != null) {
				customerInfor = customerInforService
						.findCustomerInforById(request
								.getParameter("customerId"));
			} else if (StringUtils.trimToNull(request
					.getParameter("customerCardId")) != null) {
				customerInfor = customerInforService
						.findCustomerInforByCardId(StringUtils.trim(request
								.getParameter("customerCardId")));
			}
			if (customerInfor != null) {
				customerCareersInformation = intoPiecesService
						.findCustomerCareersInformationByCustomerId(customerInfor
								.getId());
			}
		}
		Map<String, String> productAttributeMap = new LinkedHashMap<String, String>();
		List<ProductAttribute> list = productService.findProductAttributeByuserId(Beans.get(LoginManager.class).getLoggedInUser(request).getId());
		if(list!=null&&!list.isEmpty()){
			for (int i=0; i<list.size();i++) {
				ProductAttribute productAttribute = list.get(i);
				if (productAttribute != null) {
					productAttributeMap.put(productAttribute.getId(),
							productAttribute.getProductName());
				}
			}
			if(customerApplicationInfo == null &&productAttributeMap!=null&& !productAttributeMap.isEmpty()){
					appendixDictList = productService.findAppendixByProductId(list.get(0).getId());
			}
		}
		JRadModelAndView mv = null;
		if(StringUtils.trimToNull(viewFlag)!=null){
			mv = new JRadModelAndView("/intopieces/customer_view", request);
			if(Constant.APPROVE_INTOPICES.equals(customerApplicationInfo.getStatus())){
				customerInfor = customerInforService.findCloneCustomerInforByAppId(customerApplicationInfo.getId());
				if(customerInfor!=null){
					customerCareersInformation = customerInforService.findCustomerCareersByCustomerId(customerInfor.getId(), customerApplicationInfo.getId());
				}else{
					customerCareersInformation = null;
				}
			}
		}else{
			mv = new JRadModelAndView("/intopieces/customer_input", request);
		}
		if(customerApplicationInfo!=null&&StringUtils.trimToNull(customerApplicationInfo.getApplyQuota())!=null){
			customerApplicationInfo.setApplyQuota((Double.valueOf(customerApplicationInfo.getApplyQuota())/100)+"");
		}
		if(customerApplicationInfo!=null&&StringUtils.trimToNull(customerApplicationInfo.getFinalApproval())!=null){
			customerApplicationInfo.setFinalApproval((Double.valueOf(customerApplicationInfo.getFinalApproval())/100)+"");
		}
		mv.addObject("customer", customerInfor);
		mv.addObject("customerCareers", customerCareersInformation);
		mv.addObject("customerApplicationContactList",
				customerApplicationContactList);
		mv.addObject("customerApplicationGuarantorList",
				customerApplicationGuarantorList);
		mv.addObject("customerApplicationRecomList",
				customerApplicationRecomList);
		mv.addObject("customerApplicationInfo", customerApplicationInfo);
		mv.addObject("customerApplicationOther", customerApplicationOther);
		mv.addObject("customerApplicationCom", customerApplicationCom);
		mv.addObject("appendixDictList", appendixDictList);
		mv.addObject("addressAccessoriesList", addressAccessoriesList);
		mv.addObject("productAttributeMap", productAttributeMap);
		mv.addObject("customerAccountData", customerAccountData);
		return mv;
	}

	/**
	 * 根据客户信息新增进件信息，将客户相关的信息带到后面进件新增页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		/* 客户信息 */
		CustomerInfor customerInfor = null;
		/* 职业信息 */
		CustomerCareersInformation customerCareersInformation = null;
		/* 客户联系人 */
		List<CustomerApplicationContact> customerApplicationContactList = null;
		/* 客户担保人 */
		List<CustomerApplicationGuarantor> customerApplicationGuarantorList = null;
		/* 客户推荐人 */
		List<CustomerApplicationRecom> customerApplicationRecomList = null;
		/* 客户申请主表信息 */
		CustomerApplicationInfo customerApplicationInfo = null;
		/* 其他资料 */
		CustomerApplicationOther customerApplicationOther = null;
		/* 行社专栏 */
		CustomerApplicationCom customerApplicationCom = null;
		/* 行社专栏 */
		CustomerAccountData customerAccountData = null;
		/* 产品要上传的附件资料 */
		List<AppendixDict> appendixDictList = null;
		/* 实际上传的附件资料 */
		List<AddressAccessories> addressAccessoriesList = null;
		String tempParam = request.getParameter("intoPicesidAndCustorId");
		String viewFlag = request.getParameter("viewFlag");
		if (StringUtils.trimToNull(tempParam) != null) {// 修改进件首先查询出进件信息
			String[] tempArray = tempParam.split("_");
			customerInfor = customerInforService
					.findCustomerInforById(tempArray[1]);
			customerApplicationInfo = intoPiecesService
					.findCustomerApplicationInfoById(tempArray[0]);
			if (customerInfor != null) {
				customerCareersInformation = intoPiecesService
						.findCustomerCareersInformationByCustomerId(customerInfor
								.getId());
			}
			if (customerApplicationInfo != null) {
				customerApplicationContactList = intoPiecesService
						.findCustomerApplicationContactsByApplicationId(customerApplicationInfo
								.getId());
				customerApplicationGuarantorList = intoPiecesService
						.findCustomerApplicationGuarantorsByApplicationId(customerApplicationInfo
								.getId());
				customerApplicationRecomList = intoPiecesService
						.findCustomerApplicationRecomsByApplicationId(customerApplicationInfo
								.getId());
				customerApplicationOther = intoPiecesService
						.findCustomerApplicationOtherByApplicationId(customerApplicationInfo
								.getId());
				customerApplicationCom = intoPiecesService
						.findCustomerApplicationComByApplicationId(customerApplicationInfo
								.getId());
				customerAccountData = intoPiecesService
						.findCustomerAccountDataByApplicationId(customerApplicationInfo
								.getId());
				if (customerApplicationInfo != null
						&& customerApplicationInfo.getProductId() != null) {
					appendixDictList = productService
							.findAppendixByProductId(customerApplicationInfo
									.getProductId());
					addressAccessoriesList = intoPiecesService
							.findAddressAccessories(
									customerApplicationInfo.getId(),
									customerApplicationInfo.getProductId());
				}
			}
		} else {// 客户第一次填写进件信息
			if (StringUtils.trimToNull(request.getParameter("customerId")) != null) {
				customerInfor = customerInforService
						.findCustomerInforById(request
								.getParameter("customerId"));
			} else if (StringUtils.trimToNull(request
					.getParameter("customerCardId")) != null) {
				customerInfor = customerInforService
						.findCustomerInforByCardId(StringUtils.trim(request
								.getParameter("customerCardId")));
			}
			if (customerInfor != null) {
				customerCareersInformation = intoPiecesService
						.findCustomerCareersInformationByCustomerId(customerInfor
								.getId());
			}
		}
		Map<String, String> productAttributeMap = new HashMap<String, String>();
		List<ProductAttribute> list = productService
				.findProductAttributeByuserId(Beans.get(LoginManager.class)
						.getLoggedInUser(request).getId());
		for (ProductAttribute productAttribute : list) {
			if (productAttribute != null) {
				productAttributeMap.put(productAttribute.getId(),
						productAttribute.getProductName());
			}
		}
		JRadModelAndView mv = null;
		if (StringUtils.trimToNull(viewFlag) != null) {
			mv = new JRadModelAndView("/intopieces/customer_view", request);
			if (!Constant.SAVE_INTOPICES.equals(customerApplicationInfo.getStatus())) {
				customerInfor = customerInforService.findCloneCustomerInforByAppId(customerApplicationInfo.getId());
				if (customerInfor != null) {
					customerCareersInformation = customerInforService.findCustomerCareersByCustomerId(customerInfor.getId(),customerApplicationInfo.getId());
				} else {
					customerCareersInformation = null;
				}
			}
		} else {
			mv = new JRadModelAndView("/intopieces/customer_input", request);
		}
		if(customerApplicationInfo!=null&&StringUtils.trimToNull(customerApplicationInfo.getApplyQuota())!=null){
			customerApplicationInfo.setApplyQuota((Double.valueOf(customerApplicationInfo.getApplyQuota())/100)+"");
		}
		
		if(customerApplicationInfo!=null&&StringUtils.trimToNull(customerApplicationInfo.getFinalApproval())!=null){
			customerApplicationInfo.setFinalApproval((Double.valueOf(customerApplicationInfo.getFinalApproval())/100)+"");
		}
		
		mv.addObject("customer", customerInfor);
		mv.addObject("customerCareers", customerCareersInformation);
		mv.addObject("customerApplicationContactList",
				customerApplicationContactList);
		mv.addObject("customerApplicationGuarantorList",
				customerApplicationGuarantorList);
		mv.addObject("customerApplicationRecomList",
				customerApplicationRecomList);
		mv.addObject("customerApplicationInfo", customerApplicationInfo);
		mv.addObject("customerApplicationOther", customerApplicationOther);
		mv.addObject("customerApplicationCom", customerApplicationCom);
		mv.addObject("appendixDictList", appendixDictList);
		mv.addObject("addressAccessoriesList", addressAccessoriesList);
		mv.addObject("productAttributeMap", productAttributeMap);
		mv.addObject("customerAccountData", customerAccountData);
		return mv;
	}
	
	
	/**
	 * 添加进件信息(保存)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public void save(
			@ModelAttribute CustomerApplicationCom customerApplicationCom,
			@ModelAttribute CustomerApplicationContactVo customerApplicationContactVo,
			@ModelAttribute CustomerApplicationGuarantorVo customerApplicationGuarantorVo,
			@ModelAttribute CustomerApplicationInfo customerApplicationInfo,
			@ModelAttribute CustomerApplicationOther customerApplicationOther,
			@ModelAttribute CustomerApplicationRecomVo customerApplicationRecomVo,
			@ModelAttribute CustomerCareersInformation customerCareersInformation,
			@ModelAttribute CustomerInfor customerInfor,
			@ModelAttribute AddressAccessories addressAccessories,
			@ModelAttribute CustomerAccountData customerAccountData,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.saveOrUpdate(customerApplicationCom, customerApplicationContactVo,
				customerApplicationGuarantorVo, customerApplicationInfo,
				customerApplicationOther, customerApplicationRecomVo,
				customerCareersInformation, customerInfor, addressAccessories,
				customerAccountData, request, response);
	}
	
	/**
	 * 添加进件信息(修改)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public void update(
			@ModelAttribute CustomerApplicationCom customerApplicationCom,
			@ModelAttribute CustomerApplicationContactVo customerApplicationContactVo,
			@ModelAttribute CustomerApplicationGuarantorVo customerApplicationGuarantorVo,
			@ModelAttribute CustomerApplicationInfo customerApplicationInfo,
			@ModelAttribute CustomerApplicationOther customerApplicationOther,
			@ModelAttribute CustomerApplicationRecomVo customerApplicationRecomVo,
			@ModelAttribute CustomerCareersInformation customerCareersInformation,
			@ModelAttribute CustomerInfor customerInfor,
			@ModelAttribute AddressAccessories addressAccessories,
			@ModelAttribute CustomerAccountData customerAccountData,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.saveOrUpdate(customerApplicationCom, customerApplicationContactVo,
				customerApplicationGuarantorVo, customerApplicationInfo,
				customerApplicationOther, customerApplicationRecomVo,
				customerCareersInformation, customerInfor, addressAccessories,
				customerAccountData, request, response);
	}
	
	
	/*进件保存或者更新公共方法*/
	public void saveOrUpdate(
			CustomerApplicationCom customerApplicationCom,
			CustomerApplicationContactVo customerApplicationContactVo,
			CustomerApplicationGuarantorVo customerApplicationGuarantorVo,
			CustomerApplicationInfo customerApplicationInfo,
			CustomerApplicationOther customerApplicationOther,
			CustomerApplicationRecomVo customerApplicationRecomVo,
			CustomerCareersInformation customerCareersInformation,
			CustomerInfor customerInfor,
			AddressAccessories addressAccessories,
			CustomerAccountData customerAccountData,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String userId = Beans.get(LoginManager.class).getLoggedInUser(request).getId();
		try {
			boolean checkFlag = Boolean.valueOf(request.getParameter("checkFlag"));
			/* 保存或者是申请 */
			customerApplicationInfo.setStatus(StringUtils.trim(request.getParameter("operationFlag")));
			/* 如果各部分信息都是新增的这里就插入进数据库 */
			List<BusinessModel> list = new ArrayList<BusinessModel>();
			/* 如果各部分信息已经存在就更新数据 */
			List<BusinessModel> updateList = new ArrayList<BusinessModel>();
			if (StringUtils.trimToNull(request
					.getParameter("customerApplicationInfoId")) == null) {
				customerApplicationInfo.setId(IDGenerator.generateID());
				customerApplicationInfo.setApplyQuota((Integer.valueOf(customerApplicationInfo.getApplyQuota())*100)+"");
				list.add(customerApplicationInfo);
			} else {
				customerApplicationInfo.setId(StringUtils.trim(request
						.getParameter("customerApplicationInfoId")));
				double temp = Double.valueOf(customerApplicationInfo.getApplyQuota());
				customerApplicationInfo.setApplyQuota((temp*100)+"");
				updateList.add(customerApplicationInfo);
			}
			if (StringUtils.trimToNull(request
					.getParameter("customerApplicationComId")) != null) {
				customerApplicationCom.setId(StringUtils.trim(request
						.getParameter("customerApplicationComId")));
				updateList.add(customerApplicationCom);
			} else {
				list.add(customerApplicationCom);
			}
			if (StringUtils.trimToNull(request
					.getParameter("customerApplicationOtherId")) != null) {
				customerApplicationOther.setId(StringUtils.trim(request
						.getParameter("customerApplicationOtherId")));
				updateList.add(customerApplicationOther);
			} else {
				list.add(customerApplicationOther);
			}
			if (StringUtils.trimToNull(addressAccessories
					.getProductAccessoryName()) != null) {
				String[] productAccessoryNameArray = addressAccessories
						.getProductAccessoryName().split(",");
				String[] appendixTypeCodeArray = addressAccessories
						.getAppendixTypeCode().split(",");
				if (StringUtils.trimToNull(addressAccessories
						.getAddressAccessoriesId()) != null) {
					String[] addressAccessoriesArray = addressAccessories
							.getAddressAccessoriesId().split(",");
					for (int i = 0; i < productAccessoryNameArray.length; i++) {
						int flag = 0;
						for (int j = 0; j < addressAccessoriesArray.length; j++) {
							if (i == j) {
								AddressAccessories addressAccessoriesTemp = new AddressAccessories();
								addressAccessoriesTemp
										.setId(addressAccessoriesArray[i]);
								addressAccessoriesTemp
										.setAppendixTypeCode(appendixTypeCodeArray[i]);
								addressAccessoriesTemp
										.setApplicationId(customerApplicationInfo
												.getId());
								addressAccessoriesTemp
										.setProductAccessoryName(productAccessoryNameArray[i]);
								addressAccessoriesTemp
										.setProductId(customerApplicationInfo
												.getProductId());
								updateList.add(addressAccessoriesTemp);
							} else {
								flag++;
							}

						}
						if (flag == addressAccessoriesArray.length) {
							AddressAccessories addressAccessoriesTemp = new AddressAccessories();
							addressAccessoriesTemp.setId(IDGenerator
									.generateID());
							addressAccessoriesTemp
									.setAppendixTypeCode(appendixTypeCodeArray[i]);
							addressAccessoriesTemp
									.setApplicationId(customerApplicationInfo
											.getId());
							addressAccessoriesTemp
									.setProductAccessoryName(productAccessoryNameArray[i]);
							addressAccessoriesTemp
									.setProductId(customerApplicationInfo
											.getProductId());
							list.add(addressAccessoriesTemp);
						}
					}
				} else {
					for (int m = 0; m < productAccessoryNameArray.length; m++) {
						AddressAccessories addressAccessoriesTemp = new AddressAccessories();
						addressAccessoriesTemp.setId(IDGenerator.generateID());
						addressAccessoriesTemp
								.setAppendixTypeCode(appendixTypeCodeArray[m]);
						addressAccessoriesTemp
								.setApplicationId(customerApplicationInfo
										.getId());
						addressAccessoriesTemp
								.setProductAccessoryName(productAccessoryNameArray[m]);
						addressAccessoriesTemp
								.setProductId(customerApplicationInfo
										.getProductId());
						list.add(addressAccessoriesTemp);
					}
				}

			}
			if (StringUtils.trimToNull(request.getParameter("customerId")) == null) {
				customerInfor.setId(IDGenerator.generateID());
				customerInfor.setZipCode(StringUtils.trim(request
						.getParameter("customerZipCode")));
				customerCareersInformation.setCustomerId(customerInfor.getId());
				customerApplicationInfo.setCustomerId(customerInfor.getId());
				list.add(customerInfor);
			} else {
				customerInfor.setId(StringUtils.trim(request
						.getParameter("customerId")));
				customerInfor.setZipCode(StringUtils.trim(request
						.getParameter("customerZipCode")));
				customerCareersInformation.setCustomerId(customerInfor.getId());
				customerApplicationInfo.setCustomerId(customerInfor.getId());
				updateList.add(customerInfor);
			}
			if (StringUtils.trimToNull(request
					.getParameter("customerCareersId")) != null) {
				customerCareersInformation.setId(StringUtils.trim(request
						.getParameter("customerCareersId")));
				updateList.add(customerCareersInformation);
			} else {
				list.add(customerCareersInformation);
			}
			if(StringUtils.trimToNull(request
					.getParameter("customerAccountDataId")) != null){
				customerAccountData.setId(StringUtils.trim(request
						.getParameter("customerAccountDataId")));
				updateList.add(customerAccountData);
			}else{
				list.add(customerAccountData);
			}
			customerAccountData.setMainApplicationFormId(customerApplicationInfo.getId());
			customerApplicationCom.setMainApplicationFormId(customerApplicationInfo.getId());
			customerApplicationOther.setMainApplicationFormId(customerApplicationInfo.getId());
			/* 添加联系人 */
			if (customerApplicationContactVo != null&& StringUtils.trimToNull(customerApplicationContactVo.getContactName()) != null) {
				String[] contactIdArray = null;
				/* 以联系人姓名个数为单位计算添加联系人个数 */
				String[] contactNameArray = customerApplicationContactVo.getContactName().split(",");
				if (customerApplicationContactVo != null&& StringUtils.trimToNull(customerApplicationContactVo.getContactId()) != null) {
					contactIdArray = customerApplicationContactVo.getContactId().split(",");
				} else {
					contactIdArray = new String[contactNameArray.length];
				}
				String[] relationshipWithApplicantArray = this.tempString(customerApplicationContactVo.getContactRelationshipWithApplicant(), contactNameArray);
				String[] unitNameArray = this.tempString(customerApplicationContactVo.getContactUnitName(), contactNameArray);
				String[] departmentArray = this.tempString(customerApplicationContactVo.getContactDepartment(), contactNameArray);
				String[] contactPhoneArray = this.tempString(customerApplicationContactVo.getContactPhone(), contactNameArray);
				String[] cellPhoneArray = this.tempString(customerApplicationContactVo.getCellPhone(), contactNameArray);
				for (int i = 0; i <= contactNameArray.length - 1; i++) {
					CustomerApplicationContact customerApplicationContact = new CustomerApplicationContact();
					if (i < contactIdArray.length) {
						customerApplicationContact.setId(StringUtils.trimToNull(contactIdArray[i]) != null ? StringUtils.trim(contactIdArray[i]) : "");
					}
					/* 建立表之间的主外键关联关系 */
					customerApplicationContact.setMainApplicationFormId(customerApplicationInfo.getId());
					customerApplicationContact.setContactName(StringUtils.trimToNull(contactNameArray[i]) != null ? StringUtils.trim(contactNameArray[i]) : "");
					customerApplicationContact.setSex(StringUtils.trimToNull(request.getParameter("contactSex" + i)) != null ? StringUtils.trim(request.getParameter("contactSex" + i)): "");
					customerApplicationContact.setRelationshipWithApplicant(StringUtils.trimToNull(relationshipWithApplicantArray[i]) != null ? StringUtils.trim(relationshipWithApplicantArray[i]): "");
					customerApplicationContact.setUnitName(StringUtils.trimToNull(unitNameArray[i]) != null ? StringUtils.trim(unitNameArray[i]) : "");
					customerApplicationContact.setDepartment(StringUtils.trimToNull(departmentArray[i]) != null ? StringUtils.trim(departmentArray[i]) : "");
					customerApplicationContact.setContactPhone(StringUtils.trimToNull(contactPhoneArray[i]) != null ? StringUtils.trim(contactPhoneArray[i]) : "");
					customerApplicationContact.setCellPhone(StringUtils.trimToNull(cellPhoneArray[i]) != null ? StringUtils.trim(cellPhoneArray[i]) : "");
					if (StringUtils.trimToNull(customerApplicationContact.getId()) == null) {
						list.add(customerApplicationContact);
					} else {
						updateList.add(customerApplicationContact);
					}
				}
			}

			/* 添加担保人 */
			if (customerApplicationGuarantorVo != null&& StringUtils.trimToNull(customerApplicationGuarantorVo.getGuarantorMortgagorPledge()) != null) {
				String[] guarantorIdArray = null;
				/* 以 添加担保人姓名个数为单位计算添加联系人个数 */
				String[] guarantorMortgagorPledgeArray = customerApplicationGuarantorVo.getGuarantorMortgagorPledge().split(",");
				if (customerApplicationGuarantorVo != null&& customerApplicationGuarantorVo.getGuarantorId() != null) {
					guarantorIdArray = customerApplicationGuarantorVo.getGuarantorId().split(",");
				} else {
					guarantorIdArray = new String[guarantorMortgagorPledgeArray.length];
				}
				String[] relationshipWithApplicantArray = this.tempString(customerApplicationGuarantorVo.getGuarantorRelationshipWithApplicant(), guarantorMortgagorPledgeArray);
				String[] unitNameArray = this.tempString(customerApplicationGuarantorVo.getUnitName(), guarantorMortgagorPledgeArray);
				String[] departmentArray = this.tempString(customerApplicationGuarantorVo.getDepartment(), guarantorMortgagorPledgeArray);
				String[] contactPhoneArray = this.tempString(customerApplicationGuarantorVo.getGuarantorContactPhone(), guarantorMortgagorPledgeArray);
				String[] cellPhoneArray = this.tempString(customerApplicationGuarantorVo.getGuarantorCellPhone(), guarantorMortgagorPledgeArray);
				String[] documentNumberArray = this.tempString(customerApplicationGuarantorVo.getDocumentNumber(), guarantorMortgagorPledgeArray);
				for (int i = 0; i <= guarantorMortgagorPledgeArray.length - 1; i++) {
					CustomerApplicationGuarantor customerApplicationGuarantor = new CustomerApplicationGuarantor();
					if (i < guarantorIdArray.length) {
						customerApplicationGuarantor.setId(StringUtils.trimToNull(guarantorIdArray[i]) != null ? StringUtils.trim(guarantorIdArray[i]) : "");
					}
					/* 建立表之间的主外键关联关系 */
					customerApplicationGuarantor.setMainApplicationFormId(customerApplicationInfo.getId());
					customerApplicationGuarantor.setGuarantorMortgagorPledge(StringUtils.trimToNull(guarantorMortgagorPledgeArray[i]) != null ? StringUtils.trim(guarantorMortgagorPledgeArray[i]): "");
					customerApplicationGuarantor.setSex(StringUtils.trimToNull(request.getParameter("guarantorSex" + i)) != null ? StringUtils.trim(request.getParameter("guarantorSex"+ i)) : "");
					customerApplicationGuarantor.setRelationshipWithApplicant(StringUtils.trimToNull(relationshipWithApplicantArray[i]) != null ? StringUtils.trim(relationshipWithApplicantArray[i]): "");
					customerApplicationGuarantor.setUnitName(StringUtils.trimToNull(unitNameArray[i]) != null ? StringUtils.trim(unitNameArray[i]) : "");
					customerApplicationGuarantor.setDepartment(StringUtils.trimToNull(departmentArray[i]) != null ? StringUtils.trim(departmentArray[i]) : "");
					customerApplicationGuarantor.setContactPhone(StringUtils.trimToNull(contactPhoneArray[i]) != null ? StringUtils.trim(contactPhoneArray[i]) : "");
					customerApplicationGuarantor.setCellPhone(StringUtils.trimToNull(cellPhoneArray[i]) != null ? StringUtils.trim(cellPhoneArray[i]) : "");
					customerApplicationGuarantor.setDocumentNumber(StringUtils.trimToNull(documentNumberArray[i]) != null ? StringUtils.trim(documentNumberArray[i]) : "");
					if (StringUtils.trimToNull(customerApplicationGuarantor.getId()) == null) {
						list.add(customerApplicationGuarantor);
					} else {
						updateList.add(customerApplicationGuarantor);
					}
				}
			}
			/* 添加推荐人 */
			if (customerApplicationRecomVo != null&& StringUtils.trimToNull(customerApplicationRecomVo.getName()) != null) {
				String[] recommendIdArray = null;
				/* 以联系人姓名个数为单位计算添加联系人个数 */
				String[] nameArray = customerApplicationRecomVo.getName().split(",");
				if (customerApplicationRecomVo != null&& customerApplicationRecomVo.getRecommendId() != null) {
					recommendIdArray = customerApplicationRecomVo.getRecommendId().split(",");
				} else {
					recommendIdArray = new String[nameArray.length];
				}
				String[] outletArray =this.tempString(customerApplicationRecomVo.getOutlet(), nameArray);
				String[] recommendedContactPhoneArray = this.tempString(customerApplicationRecomVo.getRecommendedContactPhone(), nameArray);
				String[] recommendedIdentityCardNumbArray = this.tempString(customerApplicationRecomVo.getRecommendedIdentityCardNumb(), nameArray);
				for (int i = 0; i <= nameArray.length - 1; i++) {
					CustomerApplicationRecom customerApplicationRecom = new CustomerApplicationRecom();
					if (i < recommendIdArray.length) {customerApplicationRecom.setId(StringUtils.trimToNull(recommendIdArray[i]) != null ? StringUtils.trim(recommendIdArray[i]) : "");
					}
					/* 建立表之间的主外键关联关系 */
					customerApplicationRecom.setMainApplicationFormId(customerApplicationInfo.getId());
					customerApplicationRecom.setName(StringUtils.trimToNull(nameArray[i]) != null ? StringUtils.trim(nameArray[i]) : "");
					customerApplicationRecom.setOutlet(StringUtils.trimToNull(outletArray[i]) != null ? StringUtils.trim(outletArray[i]) : "");
					customerApplicationRecom.setContactPhone(StringUtils.trimToNull(recommendedContactPhoneArray[i]) != null ? StringUtils.trim(recommendedContactPhoneArray[i]) : "");
					customerApplicationRecom.setRecommendedIdentityCardNumb(StringUtils.trimToNull(recommendedIdentityCardNumbArray[i]) != null ? StringUtils.trim(recommendedIdentityCardNumbArray[i]): "");
					if (StringUtils.trimToNull(customerApplicationRecom.getId()) == null) {
						list.add(customerApplicationRecom);
					} else {
						updateList.add(customerApplicationRecom);
					}
				}
			}
			customerInfor.setUserId(userId);
			paramMap.put("customerId", customerInfor.getId());
			paramMap.put("applicationId", customerApplicationInfo.getId());
			paramMap.put("productId", customerApplicationInfo.getProductId());
			paramMap.put("flag", true);
			paramMap.put("customerBusinessAptitude", customerInfor.getCustomerBusinessAptitude());
			paramMap.put("userId", userId);
			paramMap.put("checkFlag", checkFlag);
			if(!list.isEmpty()){
				/* 第一次保存时做插入操作 */
				intoPiecesService.saveAllInfo(list, request,userId,customerApplicationInfo.getStatus(),paramMap);
			}
			if(!updateList.isEmpty()){
				/* 第二次保存时做更新操作 */
				intoPiecesService.updateAllInfo(updateList, request,userId,customerApplicationInfo.getStatus(),paramMap);
			}
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class)
					.getMessageNotNull(JRadConstants.CREATE_SUCCESS));
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			if(e instanceof IntoPiecesException){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, e.getMessage());
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}else{
				// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
				e.printStackTrace();
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, Constant.FAIL_MESSAGE);
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}
		}
	}
	

	/**
	 * 输入用户名模糊匹配
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectByLike.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView selectByLike(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			intoPiecesService.selectLikeByCardId(response,
					StringUtils.trim(request.getParameter("q")));
		} catch (Exception e) {
			// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			e.printStackTrace();
		}
		// TODO 该方法永远return null??前端有ajax调用应将ajax调用方法封装，另外，返回null会带来风险
		return null;
	}
	
	
	/**
	 * 删除联系人，担保人，推荐人
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delete.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.BROWSE)
	public Map<String,Object> delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		Map<String,Object> map = null;
		try {
			if(StringUtils.trimToNull(request.getParameter("value"))!=null){
				intoPiecesService.delete(StringUtils.trim(request.getParameter("name")),StringUtils.trim(request.getParameter("value")));
			}
		    map = new HashMap<String,Object>();
		    map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Constant.SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			map = new HashMap<String,Object>();
		    map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Constant.FAIL_MESSAGE);
		}
		return map;
	}

	/**
	 * 查询产品附件列表
	 * 
	 * @param filter
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "selectProductAppendix.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView selectProductAppendix(
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<AppendixDict> list = null;
		List<AddressAccessories> addressAccessoriesList = null;
		Map<String, Object> map = null;
		try {
			list = productService.findAppendixByProductId(StringUtils
					.trim(request.getParameter("productId")));
			addressAccessoriesList = intoPiecesService.findAddressAccessories(
					StringUtils.trim(request.getParameter("applicationId")),
					StringUtils.trim(request.getParameter("productId")));
			map = new HashMap<String, Object>();
			map.put(JRadConstants.SUCCESS, true);
			map.put(Constant.RESULT_LIST1, list);
			map.put(Constant.RESULT_LIST2, addressAccessoriesList);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			e.printStackTrace();
			map = new HashMap<String, Object>();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Constant.FAIL_MESSAGE);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		// TODO 该方法永远return null??前端有ajax调用应将ajax调用方法封装，另外，返回null会带来风险
		return null;
	}
	
	/**
	 * 导出文本格式数据的接口
	 * 
	 * @param filter
	 * @param request，response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "importData.page")
	@JRadOperation(JRadOperation.EXPORTUPLOAD)
	public Map<String,Object>  importData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> map = null;
		try {
			String[] intoPicesidAndCustorId = request.getParameter("intoPicesidAndCustorId").split("_");
			intoPiecesService.exportData(intoPicesidAndCustorId[0],intoPicesidAndCustorId[1],response);
			map = new HashMap<String,Object>();
			map.put(MESSAGE, Constant.UPLOAD_SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	

	/**
	 * 查看审批历史
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findApproveHistoryById.page", method = { RequestMethod.GET })
	public AbstractModelAndView findApproveHistoryById(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/approve_history_browse", request);
		String id = request.getParameter("id");
		String dataType = request.getParameter("dataType");
		String ifHideUser = request.getParameter("ifHideUser");
		if(StringUtils.isNotEmpty(id)){
			List<ApproveHistoryForm> historyForms = intoPiecesService.findApplicationDataImport(id, dataType);
			mv.addObject("historyForms", historyForms);
		}
		mv.addObject("ifHideUser", ifHideUser);
		return mv;
	}
	
	/**
	 * 查询审审议结论
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findAuditConfigureById.page", method = { RequestMethod.GET })
	public AbstractModelAndView findAuditConfigureById(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/approve_history_configure", request);
		String id = request.getParameter("id");
		List<AppManagerAuditLog> historyForms = productService.findAppManagerAuditLog(id,"");
		mv.addObject("historyForms", historyForms);
		return mv;
	}
	
	/**
	 * 查询审审议结论form 页面
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findAuditConfigureFormById.page", method = { RequestMethod.GET })
	public AbstractModelAndView findAuditConfigureFormById(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/input_decision_Form", request);
		String id = request.getParameter("id");
		List<AppManagerAuditLog> historyForms = productService.findAppManagerAuditLogByAppId(id);
		mv.addObject("historyForms", historyForms);
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(id);
		ProductAttribute producAttribute =  productService.findProductAttributeById(customerApplicationInfo.getProductId());
		CustomerInfor  customerInfor  = intoPiecesService.findCustomerManager(customerApplicationInfo.getCustomerId());
		mv.addObject("customerApplicationInfo", customerApplicationInfo);
		mv.addObject("producAttribute", producAttribute);
		mv.addObject("custManagerId", customerInfor.getUserId());
		
		/*boolean lock = false;
		String sql = "select * from dict where dict_type = 'CTRL_STATUS_PARAM' ";
		String PARAM = (String) commonDao.queryBySql(sql, null).get(0).get("TYPE_CODE");
		if("1".equals(PARAM)){
			lock = true;
		}
		mv.addObject("lock", lock);*/
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "updateExamination.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap updateExamination(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				//修改进件表
				String id = request.getParameter("id");
				String applyQuota = request.getParameter("applyQuota");
				String createdTime = request.getParameter("createdTime");//修改申请时间
				CustomerApplicationInfo info = new CustomerApplicationInfo();
				info.setApplyQuota(applyQuota);
				info.setId(id);
				if(createdTime!=null && !createdTime.equals("")){
					info.setCreatedTime(DateHelper.getDateFormat(createdTime, "yyyy-MM-dd HH:mm:ss"));
				}
				commonDao.updateObject(info);
				//修改local_excel表 申请金额
				intoPiecesService.updateLocalExcel(applyQuota,id);
				//修改初审结论
				 customerInforDao.updateAppAuditLog(id,
													"1",
													request.getParameter("chu_cyUser1"),
													request.getParameter("chu_cyUser2"),
													request.getParameter("chu_fdUser"),
													request.getParameter("csAmount"),
													request.getParameter("csexamineLv"),
													"",
													"",
													"",
													"");
				//修改审贷结论
				  customerInforDao.updateAppAuditLog(id,
													 "2",
													 request.getParameter("sd_cyUser1"),
													 request.getParameter("sd_cyUser2"),
													 request.getParameter("sd_fdUser"),
													 request.getParameter("sdAmount"),
													 request.getParameter("sdexamineLv"),
													 request.getParameter("sddecisionTerm"),
													 request.getParameter("sd_sdUser"),
													 request.getParameter("sdhkfs"),
													 request.getParameter("sd_beizhu"));
				//修改小微贷负责人结论
				customerInforDao.updateAppAuditLog(id,
													"3",
													"",
													"",
													"",
													request.getParameter("bzAmount"),
													request.getParameter("bzexamineLv"),
													request.getParameter("bzdecisionTerm"),
													"",
													"",
													request.getParameter("bz_beiZhu"));
				//修改总经理结论
				customerInforDao.updateAppAuditLog(id,
													"4",
													"",
													"",
													"",
													request.getParameter("zjlAmount"),
													request.getParameter("zjlexamineLv"),
													request.getParameter("zjdecisionTerm"),
													"",
													"",
													request.getParameter("zj_beiZhu"));
				//修改行长结论
				customerInforDao.updateAppAuditLog(id,
													"5",
													"",
													"",
													"",
													request.getParameter("hzdecisionAmount"),
													request.getParameter("hzdecisionRate"),
													request.getParameter("hzdecisionTerm"),
													"",
													"",
													request.getParameter("hzbeiZhu"));
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}
		return returnMap;
	}


	/**
	 * 验证客户商业类型
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkValue.json")
	@JRadOperation(JRadOperation.BROWSE)
	public Map<String, Object> checkValue(HttpServletRequest request) {
		boolean flag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		String currentUserId = Beans.get(LoginManager.class).getLoggedInUser(request).getId();
		float quota = Float.valueOf(request.getParameter("applyQuota"));
		String productId = request.getParameter("productId");
		String value = request.getParameter("value");
		try {
			int i = intoPiecesService.checkValue(currentUserId,value);
			if(i>0){
				flag = true;
				/*检查申请金额*/
				if (!intoPiecesService.checkApplyQuota(quota*100, currentUserId, productId)) {
					map.put(JRadConstants.SUCCESS, false);
					map.put(JRadConstants.MESSAGE, CustomerInforConstant.MAX_VALUE);
					return map;
				}
			}
			if(!flag){
				map.put(SUCCESS, false);
				map.put(MESSAGE, CustomerInforConstant.NO_CHOICE);
			}else{
				map.put(SUCCESS, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 批量导入客户基本信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "importCustomerInfo.page")
	@JRadOperation(JRadOperation.IMPORT)
	public AbstractModelAndView importCustomerInfo(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/intopieces/customer_import", request);
		return mv;
	}
	 
	/**
	 * 批量导入客户进件信息
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	*/
	@ResponseBody
	@RequestMapping(value = "importSubmit.json")
	@JRadOperation(JRadOperation.IMPORT)
	public Map<String, Object> importSubmit(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws IOException {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			customerInforservice.importBatchCustomerInfoByExcel(file,Beans.get(LoginManager.class).getLoggedInUser(request).getId());
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			if(e instanceof  IntoPiecesException){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, e.getMessage());
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}else{
				e.printStackTrace();
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTERROR);
				JSONObject obj = JSONObject.fromObject(map);
				response.getWriter().print(obj.toString());
			}
		}
		return null;
	}
	
	
	/**
	 * 检查申请额度
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	*/
	@ResponseBody
	@RequestMapping(value = "checkApplyQuota.json")
	@JRadOperation(JRadOperation.BROWSE)
	public Map<String, Object> checkApplyQuota(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		float quota = Float.valueOf(request.getParameter("applyQuota"));
		String productId = request.getParameter("productId");
		String userId = Beans.get(LoginManager.class).getLoggedInUser(request).getId();
		try {
			if (!intoPiecesService.checkApplyQuota(quota*100, userId, productId)) {
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.MAX_VALUE);
			}else{
				map.put(JRadConstants.SUCCESS, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/*数组特殊处理*/
	private String[] tempString(String string, String[] stringArray) {
		String[] resultArray = null;
		if (StringUtils.trimToNull(string) == null) {
			resultArray = new String[stringArray.length];
		} else {
			resultArray = string.split(",");
			if (resultArray.length <= stringArray.length) {
				String[] temp = new String[stringArray.length];
				for (int i = 0; i < temp.length; i++) {
					if (i < resultArray.length) {
						temp[i] = resultArray[i];
					}
				}
				resultArray = temp;
			}
		}
		return resultArray;
	}
	


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DataBindHelper.initStandardBinder(binder);
	}
	
	@ResponseBody
	@RequestMapping(value = "browe.page", method = { RequestMethod.GET })
	public AbstractModelAndView browe(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/input_letter", request);
		String id = request.getParameter("appId");
		CustomerCreditInfo info =  intoPiecesService.findCustCreditInfomation(id);
		mv.addObject("info",info);
		return mv;
	}
	
	
	//补充调查模板界面
		@ResponseBody
		@RequestMapping(value = "reportImport.page", method = { RequestMethod.GET })
		@JRadOperation(JRadOperation.BROWSE)
		public AbstractModelAndView reportImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
			filter.setRequest(request);
			QueryResult<LocalExcelForm> result = addIntoPiecesService.findLocalExcelByProductAndCustomer(filter);
			JRadPagedQueryResult<LocalExcelForm> pagedResult = new JRadPagedQueryResult<LocalExcelForm>(filter, result);
			JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_decision/report_import_supple",request);
			mv.addObject(PAGED_RESULT, pagedResult);
			
			return mv;
		}
		
		//导入调查报告
		@ResponseBody
		@RequestMapping(value = "reportImport.json")
		public Map<String, Object> reportImport_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
			response.setContentType("text/html;charset=utf-8");
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				if(file==null||file.isEmpty()){
					map.put(JRadConstants.SUCCESS, false);
					map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
					return map;
				}
				String productId = request.getParameter("productId");
				String customerId = request.getParameter("customerId");
				String appId = request.getParameter("applicationId");
				addIntoPiecesService.importExcelSupple(file,productId,customerId,appId);
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
		
		@ResponseBody
		@RequestMapping(value = "applyInfo.json")
		public JRadReturnMap removeRisk(HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			try {
				String appId = RequestHelper.getStringValue(request, ID);
				addIntoPiecesService.doMethod(appId);
				returnMap.addGlobalMessage("提交成功");
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}

			return returnMap;
		}
		
/////////////////////////////////////////=================影像资料补扫===================/////////////////////////////////////		
		//影像
		@ResponseBody
		@RequestMapping(value = "sunds_ocx.page")
		public AbstractModelAndView sunds_ocx(HttpServletRequest request) {
			JRadModelAndView mv = new JRadModelAndView("/intopieces/sunds_ocx", request);
			String appId = RequestHelper.getStringValue(request, "appId");
			String custId = RequestHelper.getStringValue(request, "custId");
			mv.addObject("appId", appId);
			
			QzApplnAttachmentList att = addIntoPiecesService.findAttachmentListByAppId(appId);
			if(att==null){
				QzApplnAttachmentList attlist = new QzApplnAttachmentList();
				attlist.setApplicationId(appId);
				attlist.setCustomerId(custId);
				attlist.setChkValue("59");
				commonDao.insertObject(attlist);
			}
			//查找sunds_ocx信息
			List<QzApplnAttachmentBatch> batch_ls = addIntoPiecesService.findAttachmentBatchByAppId(appId);
			//如果batch_ls为空 说明这是以前录得件 根据chk_value增加batch记录
			if(batch_ls == null || batch_ls.size() == 0){
				addIntoPiecesService.addBatchInfo(appId,custId);
				batch_ls = addIntoPiecesService.findAttachmentBatchByAppId(appId);
			}
			//查询客户信息
			CustomerInfor vo = addIntoPiecesService.findBasicCustomerInfor(custId);
			mv.addObject("batch_ls", batch_ls);
			mv.addObject("customerInfor",vo);
			return mv;
		}
		
		//跳转到选择图片页面
		@ResponseBody
		@RequestMapping(value = "browse_folder.page")
		public AbstractModelAndView browse_folder_page(HttpServletRequest request) {
			JRadModelAndView mv = new JRadModelAndView("/intopieces/sunds_browse_folder", request);
			String batch_id = RequestHelper.getStringValue(request, "batch_id");
			String custId = RequestHelper.getStringValue(request, "custId");
			mv.addObject("batch_id", batch_id);
			mv.addObject("custId", custId);
			mv.addObject("bussType", RequestHelper.getStringValue(request, "bussType"));
			String appId = addIntoPiecesService.findBatchId(batch_id);
			mv.addObject("appId", appId);
			return mv;
		}	
		
		//浏览图片
		@ResponseBody
		@RequestMapping(value = "browse_folder.json")
		public void browse_folder_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
			String batch_id = RequestHelper.getStringValue(request, "batch_id");
			//更新batch
			addIntoPiecesService.browse_folder(file,batch_id);
			response.getWriter().write("true");
		}
		
		
		//浏览图片完毕  开始通知后台上传影像平台
		@ResponseBody
		@RequestMapping(value = "browse_folder_complete.json")
		public JRadReturnMap browse_folder_complete(HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			try {
				String batch_id = RequestHelper.getStringValue(request, "batch_id");
				
				addIntoPiecesService.browse_folder_complete(batch_id,request);
				
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				returnMap.addGlobalMessage("保存失败");
				returnMap.put(JRadConstants.SUCCESS, false);
				e.printStackTrace();
			}
			return returnMap;
			
		}	
		
		
		//查看缓存的图片列表
		@ResponseBody
		@RequestMapping(value = "display_detail.page")
		public AbstractModelAndView diaplsy_detail(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
			filter.setRequest(request);
			JRadModelAndView mv = new JRadModelAndView("/intopieces/sunds_display_detail", request);
			QueryResult<QzApplnAttachmentDetail> result = addIntoPiecesService.display_detail(filter);
			JRadPagedQueryResult<QzApplnAttachmentDetail> pagedResult = new JRadPagedQueryResult<QzApplnAttachmentDetail>(filter, result);
			mv.addObject(PAGED_RESULT, pagedResult);
			
			return mv;
		}	
			
		//查看已上传的图片列表
		@ResponseBody
		@RequestMapping(value = "display_server.page")
		public AbstractModelAndView display_server(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
			filter.setRequest(request);
			filter.setIsUpload("1");
			String batchId = request.getParameter("batchId");
			String currentPage=request.getParameter("currentPage");
			String pageSize=request.getParameter("pageSize");
			int page = 0;//rowNum
			int limit = 1;//每页显示图片数
			if(StringUtils.isNotEmpty(currentPage)){
				page = Integer.parseInt(currentPage);
			}
			if(StringUtils.isNotEmpty(pageSize)){
				limit = Integer.parseInt(pageSize);
			}
			List<QzApplnAttachmentDetail> detaillist = addIntoPiecesService.findQzApplnDetail(page,limit,batchId);
			int totalCount = addIntoPiecesService.findQzApplnDetailCount(batchId);
			
			JRadModelAndView mv = null;
			mv = new JRadModelAndView("/intopieces/sunds_display_server_page", request);
	
			mv.addObject("Id",detaillist.get(0).getId());
			mv.addObject("rowNum", page);
			mv.addObject("totalCount",totalCount);
			mv.addObject("batchId", batchId);
			return mv;
		}
		
		
		//删除影像平台上的文件
		@ResponseBody
		@RequestMapping(value = "delete_batch.json")
		public JRadReturnMap delete_batch(HttpServletRequest request) {
			JRadReturnMap returnMap = new JRadReturnMap();
			try {
				String batchId = RequestHelper.getStringValue(request, "batchId");
				
				addIntoPiecesService.delete_batch(batchId,request);
				
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				returnMap.addGlobalMessage("删除失败");
				returnMap.put(JRadConstants.SUCCESS, false);
				e.printStackTrace();
			}
			return returnMap;
			
		}
}
