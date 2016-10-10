package com.cardpay.pccredit.intopieces.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.constant.CardStatus;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.MakeCardFilter;
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
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.DataBindHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/intopieces/applyintopiecesquery/*")
@JRadModule("intopieces.applyintopiecesquery")
public class IntoPiecesUploadFtpControl extends BaseController {

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	


	
	
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
			if (Constant.APPROVE_INTOPICES.equals(customerApplicationInfo
					.getStatus())) {
				customerInfor = customerInforService
						.findCloneCustomerInforByAppId(customerApplicationInfo
								.getId());
				if (customerInfor != null) {
					customerCareersInformation = customerInforService
							.findCustomerCareersByCustomerId(
									customerInfor.getId(),
									customerApplicationInfo.getId());
				} else {
					customerCareersInformation = null;
				}
			}
		} else {
			mv = new JRadModelAndView("/intopieces/customer_input", request);
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
	


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DataBindHelper.initStandardBinder(binder);
	}

}
