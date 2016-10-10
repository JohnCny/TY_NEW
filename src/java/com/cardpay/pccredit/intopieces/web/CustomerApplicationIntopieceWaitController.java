package com.cardpay.pccredit.intopieces.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.filter.CustomerApplicationProcessFilter;
import com.cardpay.pccredit.intopieces.model.BasicCustomerInformationS;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContactVo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantorVo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecomVo;
import com.cardpay.pccredit.intopieces.model.CustomerCareersInformationS;
import com.cardpay.pccredit.intopieces.service.CustomerApplicationIntopieceWaitService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.model.SystemConfiguration;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.pccredit.system.service.SystemConfigurationService;
import com.cardpay.pccredit.system.web.NodeAuditForm;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.jrad.modules.privilege.service.UserService;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * CustomerApplicationIntopieceWaitController类的描述
 * 
 * @author 王海东
 * @created on 2014-11-28
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/intopieces/applyintopiecewait/*")
@JRadModule("intopieces.applyintopiecewait")
public class CustomerApplicationIntopieceWaitController extends BaseController {
	@Autowired
	private CustomerApplicationIntopieceWaitService customerApplicationIntopieceWaitService;
	@Autowired
	private CustomerInforService customerInforService;
	@Autowired
	private UserService userService;

	@Autowired
	private ProcessService processService;

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private NodeAuditService nodeAuditService;
	
	@Autowired
	private SystemConfigurationService systemConfigurationService;

	/**
	 * 浏览页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute CustomerApplicationProcessFilter filter, HttpServletRequest request) throws SQLException {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String loginId = user.getId();
		filter.setLoginId(loginId);
		QueryResult<CustomerApplicationIntopieceWaitForm> result = customerApplicationIntopieceWaitService.findCustomerApplicationIntopieceWaitForm(filter);
		JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm> pagedResult = new JRadPagedQueryResult<CustomerApplicationIntopieceWaitForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_wait/intopiecesApprove_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}

	/**
	 * 
	 * 进件审核页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.APPROVE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/intopieces_wait/intopiecesApprove_approve", request);

		String waitId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(waitId)) {
			String[] ss = waitId.split("@");
			String customerId = ss[1];
			String serialNumber = ss[0];
			String applicationId = ss[2];
			String applyQuota = "";
			if(ss.length > 3){
				applyQuota = ss[3];
			}
			CustomerInfor ci = customerInforService.findCustomerInforById(customerId);
			CustomerApplicationIntopieceWaitForm customerApplicationProcess = customerApplicationIntopieceWaitService.findCustomerApplicationProcessBySerialNumber(serialNumber);
			
			String currentNodeId = customerApplicationProcess.getNextNodeId();
			NodeAuditForm nodeAudit = nodeAuditService.findNodeAuditById(currentNodeId);
			mv.addObject("nodeAuditOperationType", nodeAudit.getType());
			List<NodeControl> nodeControls = nodeAuditService.findNodeControlByCurrentNodeId(currentNodeId);
			//有异议的节点
			if(nodeControls.size() > 1){
				mv.addObject("objection","true");
			} else {
				mv.addObject("objection","false");
			}
			
			List<SystemConfiguration> systemConfigurations = systemConfigurationService.findSystemConfigurationByCode("approveObjectionAmount");
			if(systemConfigurations != null && systemConfigurations.size() > 0){
				SystemConfiguration configuration = systemConfigurations.get(0);
				mv.addObject("objectionAmount",configuration.getSysValues());
			}
			
			String userId = ci.getUserId();
			User user = userService.getUserById(userId);
			mv.addObject("applyQuota", applyQuota);
			mv.addObject("applicationId", applicationId);
			mv.addObject("customerId", customerId);
			mv.addObject("serialNumber", serialNumber);
			mv.addObject("user", user);
			mv.addObject("ci", ci);
			mv.addObject("customerApplicationProcess", customerApplicationProcess);
		}else{
			String serialNumberJump = request.getParameter("serialNumber");
			String customerIdJump =request.getParameter("customerId");
			String applicationIdJump =request.getParameter("applicationId");
			String applyQuotaJump =request.getParameter("applyQuota");
			CustomerInfor ci = customerInforService.findCustomerInforById(customerIdJump);
			CustomerApplicationIntopieceWaitForm customerApplicationProcess = customerApplicationIntopieceWaitService.findCustomerApplicationProcessBySerialNumber(serialNumberJump);
			
			String currentNodeId = customerApplicationProcess.getNextNodeId();
			NodeAuditForm nodeAudit = nodeAuditService.findNodeAuditById(currentNodeId);
			mv.addObject("nodeAuditOperationType", nodeAudit.getType());
			List<NodeControl> nodeControls = nodeAuditService.findNodeControlByCurrentNodeId(currentNodeId);
			//有异议的节点
			if(nodeControls.size() > 1){
				mv.addObject("objection","true");
			} else {
				mv.addObject("objection","false");
			}
			List<SystemConfiguration> systemConfigurations = systemConfigurationService.findSystemConfigurationByCode("approveObjectionAmount");
			if(systemConfigurations != null && systemConfigurations.size() > 0){
				SystemConfiguration configuration = systemConfigurations.get(0);
				mv.addObject("objectionAmount",configuration.getSysValues());
			}
			
			String userId = ci.getUserId();
			User user = userService.getUserById(userId);
			mv.addObject("applyQuota", applyQuotaJump);
			mv.addObject("applicationId", applicationIdJump);
			mv.addObject("customerId", customerIdJump);
			mv.addObject("serialNumber", serialNumberJump);
			mv.addObject("user", user);
			mv.addObject("ci", ci);
			mv.addObject("customerApplicationProcess", customerApplicationProcess);
		}

		return mv;
	}

	/**
	 * 执行提交
	 * 
	 * @param customerApplicationProcess
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.APPROVE)
	public JRadReturnMap update(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();

		if (returnMap.isSuccess()) {
			try {
				customerApplicationIntopieceWaitService.updateCustomerApplicationProcessBySerialNumberApplicationInfo(request);
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

	/**
	 * 申请审核进件
	 */
	@ResponseBody
	@RequestMapping(value = "apply.json")
	@JRadOperation(JRadOperation.APPLYAPPROVE)
	public JRadReturnMap apply(@ModelAttribute CustomerApplicationProcessFilter filter, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			int i = customerApplicationIntopieceWaitService.updateCustomerApplicationProcess(loginId);
			if(i == 0){
				returnMap.setSuccess(false);
				returnMap.addGlobalError("请确认已申请审核先完成或没有需要待审核的进件信息");
			} else {
				returnMap.addGlobalMessage("执行申请审核成功");
			}
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}

	/**
	 * 根据客户信息新增进件信息，将客户相关的信息带到后面进件新增页面 维护进件
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "input.page", method = { RequestMethod.GET })
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
		List<AddressAccessories> addressAccessoriesList = null;
		// String tempParam = request.getParameter("intoPicesidAndCustorId");
		String customerId = request.getParameter("customerId");
		String applicationId = request.getParameter("applicationId");
		String serialNumber = request.getParameter("serialNumber");
		String applyQuota = request.getParameter("applyQuota");
		String viewFlag = request.getParameter("viewFlag");
		if (StringUtils.trimToNull(customerId) != null && StringUtils.trimToNull(applicationId) != null) {// 修改进件首先查询出进件信息
			// String[] tempArray = tempParam.split("_");
			customerInfor = customerInforService.findCustomerInforById(customerId);
			customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(applicationId);
			if (customerInfor != null) {
				customerCareersInformation = customerInforService.findCustomerCareersByCustomerId(customerInfor.getId(),applicationId);
			}
			if (customerApplicationInfo != null) {
				customerApplicationContactList = intoPiecesService.findCustomerApplicationContactsByApplicationId(customerApplicationInfo.getId());
				customerApplicationGuarantorList = intoPiecesService.findCustomerApplicationGuarantorsByApplicationId(customerApplicationInfo.getId());
				customerApplicationRecomList = intoPiecesService.findCustomerApplicationRecomsByApplicationId(customerApplicationInfo.getId());
				customerApplicationOther = intoPiecesService.findCustomerApplicationOtherByApplicationId(customerApplicationInfo.getId());
				customerApplicationCom = intoPiecesService.findCustomerApplicationComByApplicationId(customerApplicationInfo.getId());
				customerAccountData = intoPiecesService.findCustomerAccountDataByApplicationId(customerApplicationInfo.getId());
				if (customerApplicationInfo != null && customerApplicationInfo.getProductId() != null) {
					appendixDictList = productService.findAppendixByProductId(customerApplicationInfo.getProductId());
					addressAccessoriesList = intoPiecesService.findAddressAccessories(customerApplicationInfo.getId(), customerApplicationInfo.getProductId());
				}
			}
		} else {// 客户第一次填写进件信息
			if (StringUtils.trimToNull(request.getParameter("customerId")) != null) {
				customerInfor = customerInforService.findCustomerInforById(request.getParameter("customerId"));
			} else if (StringUtils.trimToNull(request.getParameter("customerCardId")) != null) {
				customerInfor = customerInforService.findCustomerInforByCardId(StringUtils.trim(request.getParameter("customerCardId")));
			}
			if (customerInfor != null) {
				customerCareersInformation = intoPiecesService.findCustomerCareersInformationByCustomerId(customerInfor.getId());
			}
		}
		Map<String, String> productAttributeMap = new HashMap<String, String>();
		List<ProductAttribute> list = productService.findProductAttributeByuserId(Beans.get(LoginManager.class).getLoggedInUser(request).getId());
		for (ProductAttribute productAttribute : list) {
			if (productAttribute != null) {
				productAttributeMap.put(productAttribute.getId(), productAttribute.getProductName());
			}
		}
		JRadModelAndView mv = null;
		if (StringUtils.trimToNull(viewFlag) != null) {
			mv = new JRadModelAndView("/intopieces/customer_view", request);
			if (Constant.APPROVE_INTOPICES.equals(customerApplicationInfo.getStatus())) {
				customerInfor = customerInforService.findCloneCustomerInforByAppId(customerApplicationInfo.getId());
				if (customerInfor != null) {
					customerCareersInformation = customerInforService.findCustomerCareersByCustomerId(customerInfor.getId(), customerApplicationInfo.getId());
				} else {
					customerCareersInformation = null;
				}
			}
		} else {
			mv = new JRadModelAndView("/intopieces/intopieces_wait/intopiectes_maintain", request);
		}
		if(customerApplicationInfo!=null&&StringUtils.trimToNull(customerApplicationInfo.getApplyQuota())!=null){
			customerApplicationInfo.setApplyQuota((Double.valueOf(customerApplicationInfo.getApplyQuota())/100)+"");
		}
		mv.addObject("customerId",customerId);
		mv.addObject("applicationId",applicationId);
		mv.addObject("serialNumber",serialNumber);
		mv.addObject("applyQuota",applyQuota);
		mv.addObject("customer", customerInfor);
		mv.addObject("customerCareers", customerCareersInformation);
		mv.addObject("customerApplicationContactList", customerApplicationContactList);
		mv.addObject("customerApplicationGuarantorList", customerApplicationGuarantorList);
		mv.addObject("customerApplicationRecomList", customerApplicationRecomList);
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
	 * 根据客户信息新增进件信息，将客户相关的信息带到后面进件新增页面 维护进件
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page", method = { RequestMethod.GET })
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
		// String tempParam = request.getParameter("intoPicesidAndCustorId");
		String customerId = request.getParameter("customerId");
		String applicationId = request.getParameter("applicationId");
		String serialNumber = request.getParameter("serialNumber");
		String applyQuota = request.getParameter("applyQuota");
		String viewFlag = request.getParameter("viewFlag");
		if (StringUtils.trimToNull(customerId) != null && StringUtils.trimToNull(applicationId) != null) {// 修改进件首先查询出进件信息
			// String[] tempArray = tempParam.split("_");
			customerInfor = customerInforService.findCustomerInforsById(applicationId);
			customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(applicationId);
			if (customerInfor != null) {
				customerCareersInformation = intoPiecesService.findCustomerCareersInformationByCustomerId(customerInfor.getId());
			}
			if (customerApplicationInfo != null) {
				customerApplicationContactList = intoPiecesService.findCustomerApplicationContactsByApplicationId(customerApplicationInfo.getId());
				customerApplicationGuarantorList = intoPiecesService.findCustomerApplicationGuarantorsByApplicationId(customerApplicationInfo.getId());
				customerApplicationRecomList = intoPiecesService.findCustomerApplicationRecomsByApplicationId(customerApplicationInfo.getId());
				customerApplicationOther = intoPiecesService.findCustomerApplicationOtherByApplicationId(customerApplicationInfo.getId());
				customerApplicationCom = intoPiecesService.findCustomerApplicationComByApplicationId(customerApplicationInfo.getId());
				customerAccountData = intoPiecesService.findCustomerAccountDataByApplicationId(customerApplicationInfo.getId());
				if (customerApplicationInfo != null && customerApplicationInfo.getProductId() != null) {
					appendixDictList = productService.findAppendixByProductId(customerApplicationInfo.getProductId());
					addressAccessoriesList = intoPiecesService.findAddressAccessories(customerApplicationInfo.getId(), customerApplicationInfo.getProductId());
				}
			}
		} else {// 客户第一次填写进件信息
			if (StringUtils.trimToNull(request.getParameter("customerId")) != null) {
				customerInfor = customerInforService.findCustomerInforById(request.getParameter("customerId"));
			} else if (StringUtils.trimToNull(request.getParameter("customerCardId")) != null) {
				customerInfor = customerInforService.findCustomerInforByCardId(StringUtils.trim(request.getParameter("customerCardId")));
			}
			if (customerInfor != null) {
				customerCareersInformation = intoPiecesService.findCustomerCareersInformationByCustomerId(customerInfor.getId());
			}
		}
		Map<String, String> productAttributeMap = new HashMap<String, String>();
		List<ProductAttribute> list = productService.findProductAttributeByuserId(Beans.get(LoginManager.class).getLoggedInUser(request).getId());
		for (ProductAttribute productAttribute : list) {
			if (productAttribute != null) {
				productAttributeMap.put(productAttribute.getId(), productAttribute.getProductName());
			}
		}
		JRadModelAndView mv = null;
		if (StringUtils.trimToNull(viewFlag) != null) {
			mv = new JRadModelAndView("/intopieces/customer_view", request);
			if (!Constant.SAVE_INTOPICES.equals(customerApplicationInfo.getStatus())) {
				customerInfor = customerInforService.findCloneCustomerInforByAppId(customerApplicationInfo.getId());
				if (customerInfor != null) {
					customerCareersInformation = customerInforService.findCustomerCareersByCustomerId(customerInfor.getId(), customerApplicationInfo.getId());
				} else {
					customerCareersInformation = null;
				}
			}
		} else {
			mv = new JRadModelAndView("/intopieces/intopieces_wait/intopiectes_maintain", request);
		}
		if(customerApplicationInfo!=null&&StringUtils.trimToNull(customerApplicationInfo.getApplyQuota())!=null){
			customerApplicationInfo.setApplyQuota((Double.valueOf(customerApplicationInfo.getApplyQuota())/100)+"");
		}
		mv.addObject("customerId",customerId);
		mv.addObject("applicationId",applicationId);
		mv.addObject("serialNumber",serialNumber);
		mv.addObject("applyQuota",applyQuota);
		mv.addObject("customer", customerInfor);
		mv.addObject("customerCareers", customerCareersInformation);
		mv.addObject("customerApplicationContactList", customerApplicationContactList);
		mv.addObject("customerApplicationGuarantorList", customerApplicationGuarantorList);
		mv.addObject("customerApplicationRecomList", customerApplicationRecomList);
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
	 * 添加进件信息(保存，修改) 
	 * 修改 客户基本信息与客户职业信息的快照表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save.json", method = { RequestMethod.POST })
	public AbstractModelAndView save(@ModelAttribute CustomerApplicationCom customerApplicationCom, @ModelAttribute CustomerApplicationContactVo customerApplicationContactVo, @ModelAttribute CustomerApplicationGuarantorVo customerApplicationGuarantorVo,
			@ModelAttribute CustomerApplicationInfo customerApplicationInfo, @ModelAttribute CustomerApplicationOther customerApplicationOther, @ModelAttribute CustomerApplicationRecomVo customerApplicationRecomVo, @ModelAttribute CustomerCareersInformationS customerCareersInformation,
			@ModelAttribute BasicCustomerInformationS customerInfor, @ModelAttribute AddressAccessories addressAccessories, @ModelAttribute CustomerAccountData customerAccountData, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = null;
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
			if (StringUtils.trimToNull(request.getParameter("customerApplicationInfoId")) == null) {
				customerApplicationInfo.setId(IDGenerator.generateID());
				list.add(customerApplicationInfo);
			} else {
				customerApplicationInfo.setId(StringUtils.trim(request.getParameter("customerApplicationInfoId")));
				double temp = Double.valueOf(customerApplicationInfo.getApplyQuota());
				customerApplicationInfo.setApplyQuota((temp*100)+"");
				updateList.add(customerApplicationInfo);
			}
			if (StringUtils.trimToNull(request.getParameter("customerApplicationComId")) != null) {
				customerApplicationCom.setId(StringUtils.trim(request.getParameter("customerApplicationComId")));
				updateList.add(customerApplicationCom);
			} else {
				list.add(customerApplicationCom);
			}
			if (StringUtils.trimToNull(request.getParameter("customerApplicationOtherId")) != null) {
				customerApplicationOther.setId(StringUtils.trim(request.getParameter("customerApplicationOtherId")));
				updateList.add(customerApplicationOther);
			} else {
				list.add(customerApplicationOther);
			}
			if (StringUtils.trimToNull(addressAccessories.getProductAccessoryName()) != null) {
				String[] productAccessoryNameArray = addressAccessories.getProductAccessoryName().split(",");
				String[] appendixTypeCodeArray = addressAccessories.getAppendixTypeCode().split(",");
				if (StringUtils.trimToNull(addressAccessories.getAddressAccessoriesId()) != null) {
					String[] addressAccessoriesArray = addressAccessories.getAddressAccessoriesId().split(",");
					for (int i = 0; i < productAccessoryNameArray.length; i++) {
						int flag = 0;
						for (int j = 0; j < addressAccessoriesArray.length; j++) {
							if (i == j) {
								AddressAccessories addressAccessoriesTemp = new AddressAccessories();
								addressAccessoriesTemp.setId(addressAccessoriesArray[i]);
								addressAccessoriesTemp.setAppendixTypeCode(appendixTypeCodeArray[i]);
								addressAccessoriesTemp.setApplicationId(customerApplicationInfo.getId());
								addressAccessoriesTemp.setProductAccessoryName(productAccessoryNameArray[i]);
								addressAccessoriesTemp.setProductId(customerApplicationInfo.getProductId());
								updateList.add(addressAccessoriesTemp);
							} else {
								flag++;
							}

						}
						if (flag == addressAccessoriesArray.length) {
							AddressAccessories addressAccessoriesTemp = new AddressAccessories();
							addressAccessoriesTemp.setId(IDGenerator.generateID());
							addressAccessoriesTemp.setAppendixTypeCode(appendixTypeCodeArray[i]);
							addressAccessoriesTemp.setApplicationId(customerApplicationInfo.getId());
							addressAccessoriesTemp.setProductAccessoryName(productAccessoryNameArray[i]);
							addressAccessoriesTemp.setProductId(customerApplicationInfo.getProductId());
							list.add(addressAccessoriesTemp);
						}
					}
				} else {
					for (int m = 0; m < productAccessoryNameArray.length; m++) {
						AddressAccessories addressAccessoriesTemp = new AddressAccessories();
						addressAccessoriesTemp.setId(IDGenerator.generateID());
						addressAccessoriesTemp.setAppendixTypeCode(appendixTypeCodeArray[m]);
						addressAccessoriesTemp.setApplicationId(customerApplicationInfo.getId());
						addressAccessoriesTemp.setProductAccessoryName(productAccessoryNameArray[m]);
						addressAccessoriesTemp.setProductId(customerApplicationInfo.getProductId());
						list.add(addressAccessoriesTemp);
					}
				}

			}
			if (StringUtils.trimToNull(request.getParameter("customerId")) == null) {
				customerInfor.setId(IDGenerator.generateID());
				customerInfor.setZipCode(StringUtils.trim(request.getParameter("customerZipCode")));
				customerCareersInformation.setCustomerId(customerInfor.getId());
				list.add(customerInfor);
			} else {
				customerInfor.setId(StringUtils.trim(request.getParameter("customerId")));
				customerInfor.setZipCode(StringUtils.trim(request.getParameter("customerZipCode")));
				customerCareersInformation.setCustomerId(customerInfor.getId());
				updateList.add(customerInfor);
			}
			if (StringUtils.trimToNull(request.getParameter("customerCareersId")) != null) {
				customerCareersInformation.setId(StringUtils.trim(request.getParameter("customerCareersId")));
				updateList.add(customerCareersInformation);
			} else {
				list.add(customerCareersInformation);
			}
			if (StringUtils.trimToNull(request.getParameter("customerAccountDataId")) != null) {
				customerAccountData.setId(StringUtils.trim(request.getParameter("customerAccountDataId")));
				updateList.add(customerAccountData);
			} else {
				list.add(customerAccountData);
			}
			customerAccountData.setMainApplicationFormId(customerApplicationInfo.getId());
			customerApplicationCom.setMainApplicationFormId(customerApplicationInfo.getId());
			customerApplicationOther.setMainApplicationFormId(customerApplicationInfo.getId());
			/* 添加联系人 */
			if (customerApplicationContactVo != null && StringUtils.trimToNull(customerApplicationContactVo.getContactName()) != null) {
				String[] contactIdArray = null;
				/* 以联系人姓名个数为单位计算添加联系人个数 */
				String[] contactNameArray = customerApplicationContactVo.getContactName().split(",");
				if (customerApplicationContactVo != null && customerApplicationContactVo.getContactId() != null) {
					contactIdArray = customerApplicationContactVo.getContactId().split(",");
				} else {
					contactIdArray = new String[contactNameArray.length];
				}
				String[] relationshipWithApplicantArray = customerApplicationContactVo.getContactRelationshipWithApplicant().split(",");
				String[] unitNameArray = customerApplicationContactVo.getContactUnitName().split(",");
				String[] departmentArray = customerApplicationContactVo.getContactDepartment().split(",");
				String[] contactPhoneArray = customerApplicationContactVo.getContactPhone().split(",");
				String[] cellPhoneArray = customerApplicationContactVo.getCellPhone().split(",");
				for (int i = 0; i <= contactNameArray.length - 1; i++) {
					CustomerApplicationContact customerApplicationContact = new CustomerApplicationContact();
					if (i < contactIdArray.length) {
						customerApplicationContact.setId(StringUtils.trimToNull(contactIdArray[i]) != null ? StringUtils.trim(contactIdArray[i]) : "");
					}
					/* 建立表之间的主外键关联关系 */
					customerApplicationContact.setMainApplicationFormId(customerApplicationInfo.getId());
					customerApplicationContact.setContactName(StringUtils.trimToNull(contactNameArray[i]) != null ? StringUtils.trim(contactNameArray[i]) : "");
					customerApplicationContact.setSex(StringUtils.trimToNull(request.getParameter("contactSex" + i)) != null ? StringUtils.trim(request.getParameter("contactSex" + i)) : "");
					customerApplicationContact.setRelationshipWithApplicant(StringUtils.trimToNull(relationshipWithApplicantArray[i]) != null ? StringUtils.trim(relationshipWithApplicantArray[i]) : "");
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
			if (customerApplicationGuarantorVo != null && StringUtils.trimToNull(customerApplicationGuarantorVo.getGuarantorMortgagorPledge()) != null) {
				String[] guarantorIdArray = null;
				/* 以 添加担保人姓名个数为单位计算添加联系人个数 */
				String[] guarantorMortgagorPledgeArray = customerApplicationGuarantorVo.getGuarantorMortgagorPledge().split(",");
				if (customerApplicationGuarantorVo != null && customerApplicationGuarantorVo.getGuarantorId() != null) {
					guarantorIdArray = customerApplicationGuarantorVo.getGuarantorId().split(",");
				} else {
					guarantorIdArray = new String[guarantorMortgagorPledgeArray.length];
				}
				String[] relationshipWithApplicantArray = customerApplicationGuarantorVo.getGuarantorRelationshipWithApplicant().split(",");
				String[] unitNameArray = customerApplicationGuarantorVo.getUnitName().split(",");
				String[] departmentArray = customerApplicationGuarantorVo.getDepartment().split(",");
				String[] contactPhoneArray = customerApplicationGuarantorVo.getGuarantorContactPhone().split(",");
				String[] cellPhoneArray = customerApplicationGuarantorVo.getGuarantorCellPhone().split(",");
				String[] documentNumberArray = customerApplicationGuarantorVo.getDocumentNumber().split(",");
				for (int i = 0; i <= guarantorMortgagorPledgeArray.length - 1; i++) {
					CustomerApplicationGuarantor customerApplicationGuarantor = new CustomerApplicationGuarantor();
					if (i < guarantorIdArray.length) {
						customerApplicationGuarantor.setId(StringUtils.trimToNull(guarantorIdArray[i]) != null ? StringUtils.trim(guarantorIdArray[i]) : "");
					}
					/* 建立表之间的主外键关联关系 */
					customerApplicationGuarantor.setMainApplicationFormId(customerApplicationInfo.getId());
					customerApplicationGuarantor.setGuarantorMortgagorPledge(StringUtils.trimToNull(guarantorMortgagorPledgeArray[i]) != null ? StringUtils.trim(guarantorMortgagorPledgeArray[i]) : "");
					customerApplicationGuarantor.setSex(StringUtils.trimToNull(request.getParameter("guarantorSex" + i)) != null ? StringUtils.trim(request.getParameter("guarantorSex" + i)) : "");
					customerApplicationGuarantor.setRelationshipWithApplicant(StringUtils.trimToNull(relationshipWithApplicantArray[i]) != null ? StringUtils.trim(relationshipWithApplicantArray[i]) : "");
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
			if (customerApplicationRecomVo != null && StringUtils.trimToNull(customerApplicationRecomVo.getName()) != null) {
				String[] recommendIdArray = null;
				/* 以联系人姓名个数为单位计算添加联系人个数 */
				String[] nameArray = customerApplicationRecomVo.getName().split(",");
				if (customerApplicationRecomVo != null && customerApplicationRecomVo.getRecommendId() != null) {
					recommendIdArray = customerApplicationRecomVo.getRecommendId().split(",");
				} else {
					recommendIdArray = new String[nameArray.length];
				}
				String[] outletArray = customerApplicationRecomVo.getOutlet().split(",");
				String[] recommendedContactPhoneArray = customerApplicationRecomVo.getRecommendedContactPhone().split(",");
				String[] recommendedIdentityCardNumbArray = customerApplicationRecomVo.getRecommendedIdentityCardNumb().split(",");
				for (int i = 0; i <= nameArray.length - 1; i++) {
					CustomerApplicationRecom customerApplicationRecom = new CustomerApplicationRecom();
					if (i < recommendIdArray.length) {
						customerApplicationRecom.setId(StringUtils.trimToNull(recommendIdArray[i]) != null ? StringUtils.trim(recommendIdArray[i]) : "");
					}
					/* 建立表之间的主外键关联关系 */
					customerApplicationRecom.setMainApplicationFormId(customerApplicationInfo.getId());
					customerApplicationRecom.setName(StringUtils.trimToNull(nameArray[i]) != null ? StringUtils.trim(nameArray[i]) : "");
					customerApplicationRecom.setOutlet(StringUtils.trimToNull(outletArray[i]) != null ? StringUtils.trim(outletArray[i]) : "");
					customerApplicationRecom.setContactPhone(StringUtils.trimToNull(recommendedContactPhoneArray[i]) != null ? StringUtils.trim(recommendedContactPhoneArray[i]) : "");
					customerApplicationRecom.setRecommendedIdentityCardNumb(StringUtils.trimToNull(recommendedIdentityCardNumbArray[i]) != null ? StringUtils.trim(recommendedIdentityCardNumbArray[i]) : "");
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
			if (!updateList.isEmpty()) {
				/* 第二次保存时做更新操作 */
				intoPiecesService.updateAllInfoWait(updateList, request, userId, customerApplicationInfo.getStatus(), paramMap);
			}
			if (!list.isEmpty()) {
				/* 第一次保存时做插入操作 */
				intoPiecesService.saveAllInfo(list, request, userId, customerApplicationInfo.getStatus(), paramMap);
			}
			map = new HashMap<String, Object>();
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, Beans.get(I18nHelper.class).getMessageNotNull(JRadConstants.CHANGE_SUCCESS));
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, Constant.FAIL_MESSAGE);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		// 此处response的流对象已经写入到前台了，所以这里可以直接返回一个null
		return null;
	}

}
