package com.cardpay.pccredit.product.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.cardpay.pccredit.common.PccOrganizationService;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.model.CIPERSONBASINFOCOPY;
import com.cardpay.pccredit.customer.model.CIPERSONBASINFOCOPYFORM;
import com.cardpay.pccredit.customer.model.CUSTORMERINFOUPDATE;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.TyProductType;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.web.CustomerInforForm;
import com.cardpay.pccredit.datapri.web.FlatTreeNode;
import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.AccessoriesList;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.FilterDict;
import com.cardpay.pccredit.product.model.ManagerProductsConfiguration;
import com.cardpay.pccredit.product.model.ProductAccountability;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.model.ProductCollectionRules;
import com.cardpay.pccredit.product.model.ProductFilterMap;
import com.cardpay.pccredit.product.model.ProductMaintain;
import com.cardpay.pccredit.product.model.ProductMarketingRules;
import com.cardpay.pccredit.product.model.ProductRewardIncentive;
import com.cardpay.pccredit.product.model.ProductsAgencyAssociation;
import com.cardpay.pccredit.product.service.ProductFilterService;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.dictionary.DictionaryManager;
import com.wicresoft.jrad.modules.dictionary.model.Dictionary;
import com.wicresoft.jrad.modules.dictionary.model.DictionaryItem;
import com.wicresoft.jrad.modules.privilege.constant.PrivilegeConstants;
import com.wicresoft.util.date.DateHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * Description of ProductController
 * 
 * @author 王海东
 * @created on 2014-10-11
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/product/productinformation/*")
@JRadModule("product.productinformation")
public class ProductController extends BaseController {
	@Autowired
	private CustomerInforService InforService;
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductFilterService productFilterService;

	@Autowired
	private PccOrganizationService pccOrganizationService;

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
	public AbstractModelAndView browse(@ModelAttribute ProductFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<ProductAttribute> result = productService.findProductsFilter(filter);
		JRadPagedQueryResult<ProductAttribute> pagedResult = new JRadPagedQueryResult<ProductAttribute>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/product/product_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}

	/**
	 * 跳转到产品增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_create", request);
		List<TyProductType> list = productService.getProductType();
		mv.addObject("productList", list);
		return mv;
	}

	/**
	 * 读取产品图片
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "getPicture.page", method = { RequestMethod.GET })
	
	public void getpicture(HttpServletRequest request, HttpServletResponse response) {
		String productId = request.getParameter(ID);
		ProductAttribute productAttribute = productService.findProductAttributeById(productId);
		String pictureUrl = productAttribute.getPictureUrl();
		UploadFileTool u = new UploadFileTool();
		u.showPicture(response, pictureUrl);
	}

	/**
	 * 跳转到产品附件清单增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_cpfjqd.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createFJQD(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_cpfjqd", request);
		String productId = request.getParameter("productId");
		List<AppendixDict> list = productService.findAllAppendix();
		mv.addObject("accessorie", list);
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 跳转到产品附件清单增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_cpsxgz.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_cpsxgz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_cpsxgz", request);
		String productId = request.getParameter("productId");
		List<FilterDict> list = productFilterService.findAllFilterDict();
		mv.addObject("accessorie", list);
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 跳转到产品营销规则增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_cpyxgz.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createCPYXJH(HttpServletRequest request) {
		String productId = request.getParameter("productId");
		DictionaryManager dictMgr = Beans.get(DictionaryManager.class);
		// 根据指定名得到字典值列表
		Dictionary dictionary = dictMgr.getDictionaryByName("CUSTOMERMANAGERLEVEL");
		List<DictionaryItem> dictItems = dictionary.getItems();
		JRadModelAndView mv = new JRadModelAndView("/product/product_yxgz", request);
		mv.addObject("productId", productId);
		mv.addObject("dictItems", dictItems);
		return mv;
	}

	/**
	 * 跳转到产品催收计划增加页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_cpcsjh.page")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView createCPCSJH(HttpServletRequest request) {
		String productId = request.getParameter("productId");
		JRadModelAndView mv = new JRadModelAndView("/product/product_reminder", request);
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 执行产品属性添加
	 * 
	 * @param ProductAttributes
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert( @ModelAttribute ProductAttributeForm productAttributeForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), productAttributeForm);
		if (returnMap.isSuccess()) {
			try {

				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String prodLimitTime = RequestHelper.getStringValue(request, "prodLimitTime");
				String loginId = user.getId();
				//新增验证产品名称是否重复
				int i = productService.findProductCountByName(productAttributeForm.getProductName(),null);
				if(i > 0){
					returnMap.setSuccess(false);
					returnMap.addGlobalError("该产品名称已经存在："+productAttributeForm.getProductName());
					return returnMap;
				}
				ProductAttribute productAttribute = productAttributeForm.createModel(ProductAttribute.class);
				productAttribute.setCreatedBy(loginId);
				productAttribute.setProdLimitTime(DateHelper.getDateFormat(prodLimitTime, "yyyy-MM-dd HH:mm:ss"));
				String id = productService.insertProduct(productAttribute);
				String productId = productAttribute.getId();
				returnMap.put("productId", productId);
				returnMap.put(RECORD_ID, id);
				returnMap.put(MESSAGE, "添加成功");
			} catch (Exception e) {
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
		}
		return returnMap;
	}

	/**
	 * 显示产品属性和所属机构页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_display", request);
		List<AppendixDict> list = productService.findAllAppendix();
		mv.addObject("accessorie", list);
		String productId = RequestHelper.getStringValue(request, ID);
		List<FlatTreeNode> listOrg = pccOrganizationService.queryAllOrgTreeList(PrivilegeConstants.INIT_ID);
		List<ProductsAgencyAssociation> paa = productService.findInstitutionByProductId(productId);
		mv.addObject("paa", paa);
		List<String> pOrganizationId = new ArrayList<String>();
		for (ProductsAgencyAssociation productsAgencyAssociation : paa) {
			String organizationId = productsAgencyAssociation.getInstitution();
			pOrganizationId.add(organizationId);
		}
		if (StringUtils.isNotEmpty(productId)) {
			ProductAttribute productAttribute = productService.findProductAttributeById(productId);
			List<TyProductType> productList = productService.getProductType();
			mv.addObject("productList", productList);
			mv.addObject("organizationId", pOrganizationId);
			mv.addObject("organization", listOrg);
			mv.addObject("product", productAttribute);
			mv.addObject("productId", productId);

		}

		return mv;
	}

	/**
	 * 显示产品附件清单页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display_cpfjqd.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display_cpgjqd(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_display_cpfjqd", request);
		List<AppendixDict> list = productService.findAllAppendix();
		mv.addObject("accessorie", list);
		String productId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(productId)) {
			List<AccessoriesList> args = productService.findAppendixTypeCodeByProductId(productId);
			List<String> ls = new ArrayList<String>();
			for (AccessoriesList arg : args) {
				ls.add(arg.getAppendixTypeCode());
			}
			mv.addObject("args", ls);
		}
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 显示产品规则页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display_cpgz.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display_cpgz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_display_cpgz", request);
		String productId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(productId)) {
			ProductMarketingRules marketingRules = productService.findProductMarketingRulesByProductId(productId);
			List<ManagerProductsConfiguration> managerProductsConfigurationlist = productService.findManagerProductsConfigurationByProductId(productId);
			ProductRewardIncentive productRewardIncentive = productService.findProductRewardIncentiveByProductId(productId);
			ProductAccountability productAccountability = productService.findProductAccountabilityByProductId(productId);
			ProductMaintain productMaintain = productService.findProductMaintainByProductId(productId);
			mv.addObject("marketingrules", marketingRules);
			mv.addObject("managerProductsConfigurationlist", managerProductsConfigurationlist);
			mv.addObject("productRewardIncentive", productRewardIncentive);
			mv.addObject("productAccountability", productAccountability);
			mv.addObject("productMaintain", productMaintain);
		}
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 显示催收规则页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display_csgz.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display_csgz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_display_csgz", request);
		String productId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(productId)) {
			ProductCollectionRulesForm pcrf = new ProductCollectionRulesForm();
			List<ProductCollectionRules> productCollectionRules = productService.findProductCollectionRulesByProductId(productId);
			for (ProductCollectionRules productCollectionRules2 : productCollectionRules) {
				String collectionType = productCollectionRules2.getCollectionType();
				if (StringUtils.isNotEmpty(productCollectionRules2.getAging())) {
					if (productCollectionRules2.getAging().equals("1")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("2")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney2(productCollectionRules2.getMoney());
							pcrf.setCollectionTime2(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay2(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime22(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay22(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("3")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney3(productCollectionRules2.getMoney());
							pcrf.setCollectionTime3(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay3(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime33(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay33(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("4")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney4(productCollectionRules2.getMoney());
							pcrf.setCollectionTime4(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay4(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime44(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay44(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("5")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney5(productCollectionRules2.getMoney());
							pcrf.setCollectionTime5(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay5(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime55(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay55(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("6")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney6(productCollectionRules2.getMoney());
							pcrf.setCollectionTime6(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay6(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime66(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay66(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("7")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney7(productCollectionRules2.getMoney());
							pcrf.setCollectionTime7(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay7(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime77(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay77(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("8")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney8(productCollectionRules2.getMoney());
							pcrf.setCollectionTime8(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay8(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime88(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay88(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("9")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney9(productCollectionRules2.getMoney());
							pcrf.setCollectionTime9(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay9(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime99(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay99(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("10")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney10(productCollectionRules2.getMoney());
							pcrf.setCollectionTime10(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay10(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime110(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay110(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("13")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney13(productCollectionRules2.getMoney());
							pcrf.setCollectionTime13(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay13(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime113(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay113(productCollectionRules2.getCollectionWay());
						}
					}

				} else {
					if (productCollectionRules2.getOverdueDayStart().equals("1")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1111(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1111(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1111(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11111(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11111(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("31")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1112(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1112(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1112(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11112(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11112(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("61")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1113(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1113(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1113(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11113(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11113(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("91")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1114(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1114(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1114(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11114(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11114(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("121")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1115(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1115(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1115(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11115(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11115(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("151")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1116(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1116(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1116(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11116(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11116(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("181")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1117(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1117(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1117(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11117(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11117(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("211")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1118(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1118(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1118(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11118(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11118(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("241")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1119(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1119(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1119(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11119(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11119(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("271")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney11110(productCollectionRules2.getMoney());
							pcrf.setCollectionTime11110(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11110(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime111110(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay111110(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("360")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney111114(productCollectionRules2.getMoney());
							pcrf.setCollectionTime111114(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay111114(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime1111114(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1111114(productCollectionRules2.getCollectionWay());
						}
					}

				}
				mv.addObject("collectionType", collectionType);
			}
			mv.addObject("pcrf", pcrf);
		}
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 显示产品筛选规则页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display_cpsxgz.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display_cpsxgz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_display_cpsxgz", request);
		List<FilterDict> list = productFilterService.findAllFilterDict();
		mv.addObject("accessorie", list);
		String productId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(productId)) {
			List<ProductFilterMap> args = productFilterService.findProductFilterMapByProductId(productId);
			List<String> ls = new ArrayList<String>();
			for (ProductFilterMap arg : args) {
				ls.add(arg.getFilterDictId());
			}
			mv.addObject("args", ls);
		}
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 修改产品属性页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_update", request);
		String productId = RequestHelper.getStringValue(request, ID);

		if (StringUtils.isNotEmpty(productId)) {
			ProductAttribute productAttribute = productService.findProductAttributeById(productId);
			List<TyProductType> list = productService.getProductType();
			mv.addObject("productList", list);
			mv.addObject("product", productAttribute);
			mv.addObject("productId", productId);
		}

		return mv;
	}

	/**
	 * 修改产品附件清单页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change_cpfjqd.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change_cpfjqd(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_update_cpfjqd", request);
		List<AppendixDict> list = productService.findAllAppendix();
		mv.addObject("accessorie", list);
		String productId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(productId)) {
			List<AccessoriesList> args = productService.findAppendixTypeCodeByProductId(productId);
			List<String> ls = new ArrayList<String>();
			for (AccessoriesList arg : args) {
				ls.add(arg.getAppendixTypeCode());
			}
			mv.addObject("args", ls);
		}
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 修改产品规则页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change_cpgz.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change_cpgz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_update_cpgz", request);
		String productId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(productId)) {

			ProductMarketingRules marketingRules = productService.findProductMarketingRulesByProductId(productId);
			List<ManagerProductsConfiguration> managerProductsConfigurationlist = productService.findManagerProductsConfigurationByProductId(productId);
			ProductRewardIncentive productRewardIncentive = productService.findProductRewardIncentiveByProductId(productId);
			ProductAccountability productAccountability = productService.findProductAccountabilityByProductId(productId);
			ProductMaintain productMaintain = productService.findProductMaintainByProductId(productId);
			mv.addObject("marketingrules", marketingRules);
			mv.addObject("managerProductsConfigurationlist", managerProductsConfigurationlist);
			mv.addObject("productRewardIncentive", productRewardIncentive);
			mv.addObject("productAccountability", productAccountability);
			mv.addObject("productMaintain", productMaintain);
		}
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 修改催收规则页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change_csgz.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change_csgz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_update_csgz", request);
		String productId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(productId)) {
			ProductCollectionRulesForm pcrf = new ProductCollectionRulesForm();
			List<ProductCollectionRules> productCollectionRules = productService.findProductCollectionRulesByProductId(productId);
			for (ProductCollectionRules productCollectionRules2 : productCollectionRules) {
				String collectionType = productCollectionRules2.getCollectionType();
				if (StringUtils.isNotEmpty(productCollectionRules2.getAging())) {
					if (productCollectionRules2.getAging().equals("1")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("2")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney2(productCollectionRules2.getMoney());
							pcrf.setCollectionTime2(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay2(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime22(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay22(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("3")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney3(productCollectionRules2.getMoney());
							pcrf.setCollectionTime3(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay3(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime33(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay33(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("4")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney4(productCollectionRules2.getMoney());
							pcrf.setCollectionTime4(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay4(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime44(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay44(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("5")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney5(productCollectionRules2.getMoney());
							pcrf.setCollectionTime5(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay5(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime55(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay55(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("6")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney6(productCollectionRules2.getMoney());
							pcrf.setCollectionTime6(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay6(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime66(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay66(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("7")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney7(productCollectionRules2.getMoney());
							pcrf.setCollectionTime7(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay7(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime77(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay77(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("8")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney8(productCollectionRules2.getMoney());
							pcrf.setCollectionTime8(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay8(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime88(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay88(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("9")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney9(productCollectionRules2.getMoney());
							pcrf.setCollectionTime9(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay9(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime99(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay99(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("10")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney10(productCollectionRules2.getMoney());
							pcrf.setCollectionTime10(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay10(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime110(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay110(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getAging().equals("13")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney13(productCollectionRules2.getMoney());
							pcrf.setCollectionTime13(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay13(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime113(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay113(productCollectionRules2.getCollectionWay());
						}
					}

				} else {
					if (productCollectionRules2.getOverdueDayStart().equals("1")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1111(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1111(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1111(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11111(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11111(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("31")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1112(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1112(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1112(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11112(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11112(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("61")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1113(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1113(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1113(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11113(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11113(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("91")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1114(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1114(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1114(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11114(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11114(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("121")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1115(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1115(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1115(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11115(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11115(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("151")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1116(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1116(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1116(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11116(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11116(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("181")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1117(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1117(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1117(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11117(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11117(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("211")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1118(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1118(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1118(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11118(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11118(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("241")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney1119(productCollectionRules2.getMoney());
							pcrf.setCollectionTime1119(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1119(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime11119(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11119(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("271")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney11110(productCollectionRules2.getMoney());
							pcrf.setCollectionTime11110(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay11110(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime111110(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay111110(productCollectionRules2.getCollectionWay());
						}
					}
					if (productCollectionRules2.getOverdueDayStart().equals("360")) {
						if (productCollectionRules2.getOperation().equals("<=")) {
							pcrf.setMoney111114(productCollectionRules2.getMoney());
							pcrf.setCollectionTime111114(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay111114(productCollectionRules2.getCollectionWay());
						} else {
							pcrf.setCollectionTime1111114(productCollectionRules2.getCollectionTime());
							pcrf.setCollectionWay1111114(productCollectionRules2.getCollectionWay());
						}
					}

				}
				mv.addObject("collectionType", collectionType);
			}
			mv.addObject("pcrf", pcrf);
		}
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 修改产品筛选规则页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "change_cpsxgz.page")
	@JRadOperation(JRadOperation.CHANGE)
	public AbstractModelAndView change_cpsxgz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/product/product_update_cpsxgz", request);
		List<FilterDict> list = productFilterService.findAllFilterDict();
		mv.addObject("accessorie", list);
		String productId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(productId)) {
			List<ProductFilterMap> args = productFilterService.findProductFilterMapByProductId(productId);
			List<String> ls = new ArrayList<String>();
			for (ProductFilterMap arg : args) {
				ls.add(arg.getFilterDictId());
			}
			mv.addObject("args", ls);
		}
		mv.addObject("productId", productId);
		return mv;
	}

	/**
	 * 执行修改产品属性
	 * 
	 * @param productAttributeForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json", method = { RequestMethod.POST })
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute ProductAttributeForm productAttributeForm, HttpServletRequest request) {

		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), productAttributeForm);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				ProductAttribute productAttribute = productAttributeForm.createModel(ProductAttribute.class);
				productAttribute.setModifiedBy(loginId);
				String prodLimitTime = RequestHelper.getStringValue(request, "prodLimitTime");
				productAttribute.setProdLimitTime(DateHelper.getDateFormat(prodLimitTime, "yyyy-MM-dd HH:mm:ss"));
			
				int n = productService.findProductCountByName(productAttributeForm.getProductName(),productAttributeForm.getId());
				if(n > 0){
					returnMap.setSuccess(false);
					returnMap.addGlobalError("该产品名称已经存在："+productAttributeForm.getProductName());
					return returnMap;
				}

				int i = productService.updateProductAttribute(productAttribute);
				returnMap.put(MESSAGE, "修改成功");
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

	/**
	 * 执行修改产品附件清单
	 * 
	 * @param productAttributeForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update_cpfjqd.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update_capjqd(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String productId = RequestHelper.getStringValue(request, ID);
				productService.deleteAccessoriesListByProductId(productId);
				String accessorieIds = request.getParameter("accessorieIds");
				if (StringUtils.trimToNull(accessorieIds) != null) {
					String[] temp = accessorieIds.split(",");
					if (temp != null && temp.length > 0) {
						for (String aid : temp) {
							if (StringUtils.trimToNull(aid) != null) {
								AccessoriesList accessoriesList = new AccessoriesList();
								accessoriesList.setAppendixTypeCode(aid);
								accessoriesList.setProductId(productId);
								productService.insertAccessoriesList(accessoriesList);
							}
						}
					}
				}
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

	/**
	 * 执行修改产品规则
	 * 
	 * @param productAttributeForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update_cpgz.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update_cpgz(HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String productId = request.getParameter("id");

				// 更新产品营销计划
				productService.deleteProductMarketingRulesByProductId(productId);
				String marketingMethod = request.getParameter("marketingMethod");
				String marketingTime = request.getParameter("marketingTime");
				ProductMarketingRules pmr = new ProductMarketingRules();
				pmr.setMarketingMethod(marketingMethod);
				pmr.setMarketingTime(marketingTime);
				pmr.setProductId(productId);
				productService.insertProductMarketingRules(pmr);

				// 更新客户经理-产品-参数配置
				// productService.deleteManagerProductsConfigurationByProductId(productId);

				productService.updateManagerProductsConfigurationDT(request);
				// 更新产品奖励激励参数表
				productService.deleteProductRewardIncentiveByProductId(productId);
				ProductRewardIncentive pri = new ProductRewardIncentive();
				String deductionsParameters = request.getParameter("deductionsParameters");
				String earningsParameters = request.getParameter("earningsParameters");
				String feeSparameter = request.getParameter("feeSparameter");
				String nterestIncomeParameter = request.getParameter("nterestIncomeParameter");
				String parametersAnnualFee = request.getParameter("parametersAnnualFee");
				String parametersCost = request.getParameter("parametersCost");
				String parametersFine = request.getParameter("parametersFine");
				String parametersRiskCost = request.getParameter("parametersRiskCost");
				String rewardIncentivesProportion = request.getParameter("rewardIncentivesProportion");
				String rewardSheet = request.getParameter("rewardSheet");

				pri.setDeductionsParameters(deductionsParameters);
				pri.setEarningsParameters(earningsParameters);
				pri.setFeeSparameter(feeSparameter);
				pri.setNterestIncomeParameter(nterestIncomeParameter);
				pri.setParametersAnnualFee(parametersAnnualFee);
				pri.setParametersCost(parametersCost);
				pri.setParametersFine(parametersFine);
				pri.setParametersRiskCost(parametersRiskCost);
				pri.setRewardIncentivesProportion(rewardIncentivesProportion);
				pri.setRewardSheet(rewardSheet);
				pri.setProductId(productId);
				productService.insertProductRewardIncentive(pri);
				// 更新产品维护规则
				productService.deleteProductMaintainByProductId(productId);
				String day = request.getParameter("day");
				String maintenanceWay = request.getParameter("maintenanceWay");
				ProductMaintain pm = new ProductMaintain();
				pm.setProductId(productId);
				pm.setDay(day);
				pm.setMaintenanceWay(maintenanceWay);
				productService.insertProductMaintain(pm);
				// 更新产品问责规则
				productService.deleteProductAccountabilityByProductId(productId);
				String aging = request.getParameter("aging");
				String overdueDay = request.getParameter("overdueDay");
				String overdueAmount = request.getParameter("overdueAmount");
				String overdueTotalTime = request.getParameter("overdueTotalTime");
				String overdueTotalAmount = request.getParameter("overdueTotalAmount");
				ProductAccountability pa = new ProductAccountability();
				pa.setAging(aging);
				pa.setOverdueDay(overdueDay);
				pa.setOverdueAmount(overdueAmount);
				pa.setOverdueTotalAmount(overdueTotalAmount);
				pa.setOverdueTotalTime(overdueTotalTime);
				pa.setProductId(productId);
				productService.insertProductAccountability(pa);

				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

	/**
	 * 执行修改产品催收规则
	 * 
	 * @param productAttributeForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update_csgz.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update_csgz(@ModelAttribute ProductCollectionRulesForm productCollectionRulesForm, HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String productId = request.getParameter("id");
				List<ProductCollectionRules> list = new ArrayList<ProductCollectionRules>();
				// 更新产品催收规则
				productService.deleteProductCollectionRulesByProductId(productId);
				String collectionType = request.getParameter("reminderClass");
				if (collectionType.equals("age")) {
					/*
					 * 账龄1期
					 */
					String aging1 = productCollectionRulesForm.getAging1();
					String collectionWay1 = productCollectionRulesForm.getCollectionWay1();
					String money1 = productCollectionRulesForm.getMoney1();
					String operation1 = productCollectionRulesForm.getOperation1();
					String collectionTime1 = productCollectionRulesForm.getCollectionTime1();
					String operation11 = productCollectionRulesForm.getOperation11();
					String collectionTime11 = productCollectionRulesForm.getCollectionTime11();
					String collectionWay11 = productCollectionRulesForm.getCollectionWay11();
					ProductCollectionRules productCollectionRules1 = new ProductCollectionRules();
					productCollectionRules1.setAging(aging1);
					productCollectionRules1.setCollectionTime(collectionTime1);
					productCollectionRules1.setMoney(money1);
					productCollectionRules1.setOperation(operation1);
					productCollectionRules1.setCollectionWay(collectionWay1);
					productCollectionRules1.setProductId(productId);
					productCollectionRules1.setCollectionType(collectionType);
					ProductCollectionRules productCollectionRules11 = new ProductCollectionRules();
					productCollectionRules11.setAging(aging1);
					productCollectionRules11.setCollectionTime(collectionTime11);
					productCollectionRules11.setMoney(money1);
					productCollectionRules11.setOperation(operation11);
					productCollectionRules11.setCollectionWay(collectionWay11);
					productCollectionRules11.setProductId(productId);
					productCollectionRules11.setCollectionType(collectionType);
					/*
					 * 账龄2期
					 */
					String aging2 = productCollectionRulesForm.getAging2();
					String collectionWay2 = productCollectionRulesForm.getCollectionWay2();
					String money2 = productCollectionRulesForm.getMoney2();
					String operation2 = productCollectionRulesForm.getOperation2();
					String collectionTime2 = productCollectionRulesForm.getCollectionTime2();
					String operation22 = productCollectionRulesForm.getOperation22();
					String collectionTime22 = productCollectionRulesForm.getCollectionTime22();
					String collectionWay22 = productCollectionRulesForm.getCollectionWay22();
					ProductCollectionRules productCollectionRules2 = new ProductCollectionRules();
					productCollectionRules2.setAging(aging2);
					productCollectionRules2.setCollectionTime(collectionTime2);
					productCollectionRules2.setMoney(money2);
					productCollectionRules2.setOperation(operation2);
					productCollectionRules2.setCollectionWay(collectionWay2);
					productCollectionRules2.setProductId(productId);
					productCollectionRules2.setCollectionType(collectionType);
					ProductCollectionRules productCollectionRules22 = new ProductCollectionRules();
					productCollectionRules22.setAging(aging2);
					productCollectionRules22.setCollectionTime(collectionTime22);
					productCollectionRules22.setMoney(money2);
					productCollectionRules22.setOperation(operation22);
					productCollectionRules22.setCollectionWay(collectionWay22);
					productCollectionRules22.setProductId(productId);
					productCollectionRules22.setCollectionType(collectionType);
					/*
					 * 账龄3期
					 */
					String aging3 = productCollectionRulesForm.getAging3();
					String collectionWay3 = productCollectionRulesForm.getCollectionWay3();
					String money3 = productCollectionRulesForm.getMoney3();
					String operation3 = productCollectionRulesForm.getOperation3();
					String collectionTime3 = productCollectionRulesForm.getCollectionTime3();
					String operation33 = productCollectionRulesForm.getOperation33();
					String collectionTime33 = productCollectionRulesForm.getCollectionTime33();
					String collectionWay33 = productCollectionRulesForm.getCollectionWay33();
					ProductCollectionRules productCollectionRules3 = new ProductCollectionRules();
					productCollectionRules3.setAging(aging3);
					productCollectionRules3.setCollectionTime(collectionTime3);
					productCollectionRules3.setMoney(money3);
					productCollectionRules3.setOperation(operation3);
					productCollectionRules3.setCollectionWay(collectionWay3);
					productCollectionRules3.setProductId(productId);
					productCollectionRules3.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules33 = new ProductCollectionRules();
					productCollectionRules33.setAging(aging3);
					productCollectionRules33.setCollectionTime(collectionTime33);
					productCollectionRules33.setMoney(money3);
					productCollectionRules33.setOperation(operation33);
					productCollectionRules33.setCollectionWay(collectionWay33);
					productCollectionRules33.setProductId(productId);
					productCollectionRules33.setCollectionType(collectionType);

					/*
					 * 账龄4期
					 */
					String aging4 = productCollectionRulesForm.getAging4();
					String collectionWay4 = productCollectionRulesForm.getCollectionWay4();
					String money4 = productCollectionRulesForm.getMoney4();
					String operation4 = productCollectionRulesForm.getOperation4();
					String collectionTime4 = productCollectionRulesForm.getCollectionTime4();
					String operation44 = productCollectionRulesForm.getOperation44();
					String collectionTime44 = productCollectionRulesForm.getCollectionTime44();
					String collectionWay44 = productCollectionRulesForm.getCollectionWay44();
					ProductCollectionRules productCollectionRules4 = new ProductCollectionRules();
					productCollectionRules4.setAging(aging4);
					productCollectionRules4.setCollectionTime(collectionTime4);
					productCollectionRules4.setMoney(money4);
					productCollectionRules4.setOperation(operation4);
					productCollectionRules4.setCollectionWay(collectionWay4);
					productCollectionRules4.setProductId(productId);
					productCollectionRules4.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules44 = new ProductCollectionRules();
					productCollectionRules44.setAging(aging4);
					productCollectionRules44.setCollectionTime(collectionTime44);
					productCollectionRules44.setMoney(money4);
					productCollectionRules44.setOperation(operation44);
					productCollectionRules44.setCollectionWay(collectionWay44);
					productCollectionRules44.setProductId(productId);
					productCollectionRules44.setCollectionType(collectionType);

					/*
					 * 账龄5期
					 */
					String aging5 = productCollectionRulesForm.getAging5();
					String collectionWay5 = productCollectionRulesForm.getCollectionWay5();
					String money5 = productCollectionRulesForm.getMoney5();
					String operation5 = productCollectionRulesForm.getOperation5();
					String collectionTime5 = productCollectionRulesForm.getCollectionTime5();
					String operation55 = productCollectionRulesForm.getOperation55();
					String collectionTime55 = productCollectionRulesForm.getCollectionTime55();
					String collectionWay55 = productCollectionRulesForm.getCollectionWay55();
					ProductCollectionRules productCollectionRules5 = new ProductCollectionRules();
					productCollectionRules5.setAging(aging5);
					productCollectionRules5.setCollectionTime(collectionTime5);
					productCollectionRules5.setMoney(money5);
					productCollectionRules5.setOperation(operation5);
					productCollectionRules5.setCollectionWay(collectionWay5);
					productCollectionRules5.setProductId(productId);
					productCollectionRules5.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules55 = new ProductCollectionRules();
					productCollectionRules55.setAging(aging5);
					productCollectionRules55.setCollectionTime(collectionTime55);
					productCollectionRules55.setMoney(money5);
					productCollectionRules55.setOperation(operation55);
					productCollectionRules55.setCollectionWay(collectionWay55);
					productCollectionRules55.setProductId(productId);
					productCollectionRules55.setCollectionType(collectionType);

					/*
					 * 账龄6期
					 */
					String aging6 = productCollectionRulesForm.getAging6();
					String collectionWay6 = productCollectionRulesForm.getCollectionWay6();
					String money6 = productCollectionRulesForm.getMoney6();
					String operation6 = productCollectionRulesForm.getOperation6();
					String collectionTime6 = productCollectionRulesForm.getCollectionTime6();
					String operation66 = productCollectionRulesForm.getOperation66();
					String collectionTime66 = productCollectionRulesForm.getCollectionTime66();
					String collectionWay66 = productCollectionRulesForm.getCollectionWay66();
					ProductCollectionRules productCollectionRules6 = new ProductCollectionRules();
					productCollectionRules6.setAging(aging6);
					productCollectionRules6.setCollectionTime(collectionTime6);
					productCollectionRules6.setMoney(money6);
					productCollectionRules6.setOperation(operation6);
					productCollectionRules6.setCollectionWay(collectionWay6);
					productCollectionRules6.setProductId(productId);
					productCollectionRules6.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules66 = new ProductCollectionRules();
					productCollectionRules66.setAging(aging6);
					productCollectionRules66.setCollectionTime(collectionTime66);
					productCollectionRules66.setMoney(money6);
					productCollectionRules66.setOperation(operation66);
					productCollectionRules66.setCollectionWay(collectionWay66);
					productCollectionRules66.setProductId(productId);
					productCollectionRules66.setCollectionType(collectionType);

					/*
					 * 账龄7期
					 */
					String aging7 = productCollectionRulesForm.getAging7();
					String collectionWay7 = productCollectionRulesForm.getCollectionWay7();
					String money7 = productCollectionRulesForm.getMoney7();
					String operation7 = productCollectionRulesForm.getOperation7();
					String collectionTime7 = productCollectionRulesForm.getCollectionTime7();
					String operation77 = productCollectionRulesForm.getOperation77();
					String collectionTime77 = productCollectionRulesForm.getCollectionTime77();
					String collectionWay77 = productCollectionRulesForm.getCollectionWay77();
					ProductCollectionRules productCollectionRules7 = new ProductCollectionRules();
					productCollectionRules7.setAging(aging7);
					productCollectionRules7.setCollectionTime(collectionTime7);
					productCollectionRules7.setMoney(money7);
					productCollectionRules7.setOperation(operation7);
					productCollectionRules7.setCollectionWay(collectionWay7);
					productCollectionRules7.setProductId(productId);
					productCollectionRules7.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules77 = new ProductCollectionRules();
					productCollectionRules77.setAging(aging7);
					productCollectionRules77.setCollectionTime(collectionTime77);
					productCollectionRules77.setMoney(money7);
					productCollectionRules77.setOperation(operation77);
					productCollectionRules77.setCollectionWay(collectionWay77);
					productCollectionRules77.setProductId(productId);
					productCollectionRules77.setCollectionType(collectionType);

					/*
					 * 账龄8期
					 */
					String aging8 = productCollectionRulesForm.getAging8();
					String collectionWay8 = productCollectionRulesForm.getCollectionWay8();
					String money8 = productCollectionRulesForm.getMoney8();
					String operation8 = productCollectionRulesForm.getOperation8();
					String collectionTime8 = productCollectionRulesForm.getCollectionTime8();
					String operation88 = productCollectionRulesForm.getOperation88();
					String collectionTime88 = productCollectionRulesForm.getCollectionTime88();
					String collectionWay88 = productCollectionRulesForm.getCollectionWay88();
					ProductCollectionRules productCollectionRules8 = new ProductCollectionRules();
					productCollectionRules8.setAging(aging8);
					productCollectionRules8.setCollectionTime(collectionTime8);
					productCollectionRules8.setMoney(money8);
					productCollectionRules8.setOperation(operation8);
					productCollectionRules8.setCollectionWay(collectionWay8);
					productCollectionRules8.setProductId(productId);
					productCollectionRules8.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules88 = new ProductCollectionRules();
					productCollectionRules88.setAging(aging8);
					productCollectionRules88.setCollectionTime(collectionTime88);
					productCollectionRules88.setMoney(money8);
					productCollectionRules88.setOperation(operation88);
					productCollectionRules88.setCollectionWay(collectionWay88);
					productCollectionRules88.setProductId(productId);
					productCollectionRules88.setCollectionType(collectionType);

					/*
					 * 账龄9期
					 */
					String aging9 = productCollectionRulesForm.getAging9();
					String collectionWay9 = productCollectionRulesForm.getCollectionWay9();
					String money9 = productCollectionRulesForm.getMoney9();
					String operation9 = productCollectionRulesForm.getOperation9();
					String collectionTime9 = productCollectionRulesForm.getCollectionTime9();
					String operation99 = productCollectionRulesForm.getOperation99();
					String collectionTime99 = productCollectionRulesForm.getCollectionTime99();
					String collectionWay99 = productCollectionRulesForm.getCollectionWay99();
					ProductCollectionRules productCollectionRules9 = new ProductCollectionRules();
					productCollectionRules9.setAging(aging9);
					productCollectionRules9.setCollectionTime(collectionTime9);
					productCollectionRules9.setMoney(money9);
					productCollectionRules9.setOperation(operation9);
					productCollectionRules9.setCollectionWay(collectionWay9);
					productCollectionRules9.setProductId(productId);
					productCollectionRules9.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules99 = new ProductCollectionRules();
					productCollectionRules99.setAging(aging9);
					productCollectionRules99.setCollectionTime(collectionTime99);
					productCollectionRules99.setMoney(money9);
					productCollectionRules99.setOperation(operation99);
					productCollectionRules99.setCollectionWay(collectionWay99);
					productCollectionRules99.setProductId(productId);
					productCollectionRules99.setCollectionType(collectionType);

					/*
					 * 账龄10期~12期
					 */
					String aging10 = productCollectionRulesForm.getAging10();
					String collectionWay10 = productCollectionRulesForm.getCollectionWay10();
					String money10 = productCollectionRulesForm.getMoney10();
					String operation10 = productCollectionRulesForm.getOperation10();
					String collectionTime10 = productCollectionRulesForm.getCollectionTime10();
					String operation110 = productCollectionRulesForm.getOperation110();
					String collectionTime110 = productCollectionRulesForm.getCollectionTime110();
					String collectionWay110 = productCollectionRulesForm.getCollectionWay110();
					ProductCollectionRules productCollectionRules10 = new ProductCollectionRules();
					productCollectionRules10.setAging(aging10);
					productCollectionRules10.setCollectionTime(collectionTime10);
					productCollectionRules10.setMoney(money10);
					productCollectionRules10.setOperation(operation10);
					productCollectionRules10.setCollectionWay(collectionWay10);
					productCollectionRules10.setProductId(productId);
					productCollectionRules10.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules110 = new ProductCollectionRules();
					productCollectionRules110.setAging(aging10);
					productCollectionRules110.setCollectionTime(collectionTime110);
					productCollectionRules110.setMoney(money10);
					productCollectionRules110.setOperation(operation110);
					productCollectionRules110.setCollectionWay(collectionWay110);
					productCollectionRules110.setProductId(productId);
					productCollectionRules110.setCollectionType(collectionType);

					/*
					 * 账龄12期以上
					 */
					String aging13 = productCollectionRulesForm.getAging13();
					String collectionWay13 = productCollectionRulesForm.getCollectionWay13();
					String money13 = productCollectionRulesForm.getMoney13();
					String operation13 = productCollectionRulesForm.getOperation13();
					String collectionTime13 = productCollectionRulesForm.getCollectionTime13();
					String operation113 = productCollectionRulesForm.getOperation113();
					String collectionTime113 = productCollectionRulesForm.getCollectionTime113();
					String collectionWay113 = productCollectionRulesForm.getCollectionWay113();
					ProductCollectionRules productCollectionRules13 = new ProductCollectionRules();
					productCollectionRules13.setAging(aging13);
					productCollectionRules13.setCollectionTime(collectionTime13);
					productCollectionRules13.setMoney(money13);
					productCollectionRules13.setOperation(operation13);
					productCollectionRules13.setCollectionWay(collectionWay13);
					productCollectionRules13.setProductId(productId);
					productCollectionRules13.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules113 = new ProductCollectionRules();
					productCollectionRules113.setAging(aging13);
					productCollectionRules113.setCollectionTime(collectionTime113);
					productCollectionRules113.setMoney(money13);
					productCollectionRules113.setOperation(operation113);
					productCollectionRules113.setCollectionWay(collectionWay113);
					productCollectionRules113.setProductId(productId);
					productCollectionRules113.setCollectionType(collectionType);

					list.add(productCollectionRules1);
					list.add(productCollectionRules11);
					list.add(productCollectionRules2);
					list.add(productCollectionRules22);
					list.add(productCollectionRules3);
					list.add(productCollectionRules33);
					list.add(productCollectionRules4);
					list.add(productCollectionRules44);
					list.add(productCollectionRules5);
					list.add(productCollectionRules55);
					list.add(productCollectionRules6);
					list.add(productCollectionRules66);
					list.add(productCollectionRules7);
					list.add(productCollectionRules77);
					list.add(productCollectionRules8);
					list.add(productCollectionRules88);
					list.add(productCollectionRules9);
					list.add(productCollectionRules99);
					list.add(productCollectionRules10);
					list.add(productCollectionRules110);
					list.add(productCollectionRules13);
					list.add(productCollectionRules113);
					productService.insertProductCollectionRules(list);

				} else if (collectionType.equals("date")) {
					/*
					 * 逾期天数1~30天
					 */
					String overdueDayStart1 = productCollectionRulesForm.getOverdueDayStart1();
					String overdueDayEnd1 = productCollectionRulesForm.getOverdueDayEnd1();
					String collectionWay1111 = productCollectionRulesForm.getCollectionWay1111();
					String money1111 = productCollectionRulesForm.getMoney1111();
					String operation1111 = productCollectionRulesForm.getOperation1111();
					String collectionTime1111 = productCollectionRulesForm.getCollectionTime1111();
					String operation11111 = productCollectionRulesForm.getOperation11111();
					String collectionTime11111 = productCollectionRulesForm.getCollectionTime11111();
					String collectionWay11111 = productCollectionRulesForm.getCollectionWay11111();
					ProductCollectionRules productCollectionRules1111 = new ProductCollectionRules();
					productCollectionRules1111.setOverdueDayStart(overdueDayStart1);
					productCollectionRules1111.setOverdueDayEnd(overdueDayEnd1);
					productCollectionRules1111.setMoney(money1111);
					productCollectionRules1111.setCollectionWay(collectionWay1111);
					productCollectionRules1111.setCollectionTime(collectionTime1111);
					productCollectionRules1111.setOperation(operation1111);
					productCollectionRules1111.setProductId(productId);
					productCollectionRules1111.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules11111 = new ProductCollectionRules();
					productCollectionRules11111.setOverdueDayStart(overdueDayStart1);
					productCollectionRules11111.setOverdueDayEnd(overdueDayEnd1);
					productCollectionRules11111.setMoney(money1111);
					productCollectionRules11111.setCollectionWay(collectionWay11111);
					productCollectionRules11111.setCollectionTime(collectionTime11111);
					productCollectionRules11111.setOperation(operation11111);
					productCollectionRules11111.setProductId(productId);
					productCollectionRules11111.setCollectionType(collectionType);

					/*
					 * 逾期天数31~60天
					 */
					String overdueDayStart2 = productCollectionRulesForm.getOverdueDayStart2();
					String overdueDayEnd2 = productCollectionRulesForm.getOverdueDayEnd2();
					String collectionWay1112 = productCollectionRulesForm.getCollectionWay1112();
					String money1112 = productCollectionRulesForm.getMoney1112();
					String operation1112 = productCollectionRulesForm.getOperation1112();
					String collectionTime1112 = productCollectionRulesForm.getCollectionTime1112();
					String operation11112 = productCollectionRulesForm.getOperation11112();
					String collectionTime11112 = productCollectionRulesForm.getCollectionTime11112();
					String collectionWay11112 = productCollectionRulesForm.getCollectionWay11112();
					ProductCollectionRules productCollectionRules1112 = new ProductCollectionRules();
					productCollectionRules1112.setOverdueDayStart(overdueDayStart2);
					productCollectionRules1112.setOverdueDayEnd(overdueDayEnd2);
					productCollectionRules1112.setMoney(money1112);
					productCollectionRules1112.setCollectionWay(collectionWay1112);
					productCollectionRules1112.setCollectionTime(collectionTime1112);
					productCollectionRules1112.setOperation(operation1112);
					productCollectionRules1112.setProductId(productId);
					productCollectionRules1112.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules11112 = new ProductCollectionRules();
					productCollectionRules11112.setOverdueDayStart(overdueDayStart2);
					productCollectionRules11112.setOverdueDayEnd(overdueDayEnd2);
					productCollectionRules11112.setMoney(money1112);
					productCollectionRules11112.setCollectionWay(collectionWay11112);
					productCollectionRules11112.setCollectionTime(collectionTime11112);
					productCollectionRules11112.setOperation(operation11112);
					productCollectionRules11112.setProductId(productId);
					productCollectionRules11112.setCollectionType(collectionType);

					/*
					 * 逾期天数61~90天
					 */
					String overdueDayStart3 = productCollectionRulesForm.getOverdueDayStart3();
					String overdueDayEnd3 = productCollectionRulesForm.getOverdueDayEnd3();
					String collectionWay1113 = productCollectionRulesForm.getCollectionWay1113();
					String money1113 = productCollectionRulesForm.getMoney1113();
					String operation1113 = productCollectionRulesForm.getOperation1113();
					String collectionTime1113 = productCollectionRulesForm.getCollectionTime1113();
					String operation11113 = productCollectionRulesForm.getOperation11113();
					String collectionTime11113 = productCollectionRulesForm.getCollectionTime11113();
					String collectionWay11113 = productCollectionRulesForm.getCollectionWay11113();
					ProductCollectionRules productCollectionRules1113 = new ProductCollectionRules();
					productCollectionRules1113.setOverdueDayStart(overdueDayStart3);
					productCollectionRules1113.setOverdueDayEnd(overdueDayEnd3);
					productCollectionRules1113.setMoney(money1113);
					productCollectionRules1113.setCollectionWay(collectionWay1113);
					productCollectionRules1113.setCollectionTime(collectionTime1113);
					productCollectionRules1113.setOperation(operation1113);
					productCollectionRules1113.setProductId(productId);
					productCollectionRules1113.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules11113 = new ProductCollectionRules();
					productCollectionRules11113.setOverdueDayStart(overdueDayStart3);
					productCollectionRules11113.setOverdueDayEnd(overdueDayEnd3);
					productCollectionRules11113.setMoney(money1113);
					productCollectionRules11113.setCollectionWay(collectionWay11113);
					productCollectionRules11113.setCollectionTime(collectionTime11113);
					productCollectionRules11113.setOperation(operation11113);
					productCollectionRules11113.setProductId(productId);
					productCollectionRules11113.setCollectionType(collectionType);

					/*
					 * 逾期天数91~120天
					 */
					String overdueDayStart4 = productCollectionRulesForm.getOverdueDayStart4();
					String overdueDayEnd4 = productCollectionRulesForm.getOverdueDayEnd4();
					String collectionWay1114 = productCollectionRulesForm.getCollectionWay1114();
					String money1114 = productCollectionRulesForm.getMoney1114();
					String operation1114 = productCollectionRulesForm.getOperation1114();
					String collectionTime1114 = productCollectionRulesForm.getCollectionTime1114();
					String operation11114 = productCollectionRulesForm.getOperation11114();
					String collectionTime11114 = productCollectionRulesForm.getCollectionTime11114();
					String collectionWay11114 = productCollectionRulesForm.getCollectionWay11114();
					ProductCollectionRules productCollectionRules1114 = new ProductCollectionRules();
					productCollectionRules1114.setOverdueDayStart(overdueDayStart4);
					productCollectionRules1114.setOverdueDayEnd(overdueDayEnd4);
					productCollectionRules1114.setMoney(money1114);
					productCollectionRules1114.setCollectionWay(collectionWay1114);
					productCollectionRules1114.setCollectionTime(collectionTime1114);
					productCollectionRules1114.setOperation(operation1114);
					productCollectionRules1114.setProductId(productId);
					productCollectionRules1114.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules11114 = new ProductCollectionRules();
					productCollectionRules11114.setOverdueDayStart(overdueDayStart4);
					productCollectionRules11114.setOverdueDayEnd(overdueDayEnd4);
					productCollectionRules11114.setMoney(money1114);
					productCollectionRules11114.setCollectionWay(collectionWay11114);
					productCollectionRules11114.setCollectionTime(collectionTime11114);
					productCollectionRules11114.setOperation(operation11114);
					productCollectionRules11114.setProductId(productId);
					productCollectionRules11114.setCollectionType(collectionType);

					/*
					 * 逾期天数121~150天
					 */
					String overdueDayStart5 = productCollectionRulesForm.getOverdueDayStart5();
					String overdueDayEnd5 = productCollectionRulesForm.getOverdueDayEnd5();
					String collectionWay1115 = productCollectionRulesForm.getCollectionWay1115();
					String money1115 = productCollectionRulesForm.getMoney1115();
					String operation1115 = productCollectionRulesForm.getOperation1115();
					String collectionTime1115 = productCollectionRulesForm.getCollectionTime1115();
					String operation11115 = productCollectionRulesForm.getOperation11115();
					String collectionTime11115 = productCollectionRulesForm.getCollectionTime11115();
					String collectionWay11115 = productCollectionRulesForm.getCollectionWay11115();
					ProductCollectionRules productCollectionRules1115 = new ProductCollectionRules();
					productCollectionRules1115.setOverdueDayStart(overdueDayStart5);
					productCollectionRules1115.setOverdueDayEnd(overdueDayEnd5);
					productCollectionRules1115.setMoney(money1115);
					productCollectionRules1115.setCollectionWay(collectionWay1115);
					productCollectionRules1115.setCollectionTime(collectionTime1115);
					productCollectionRules1115.setOperation(operation1115);
					productCollectionRules1115.setProductId(productId);
					productCollectionRules1115.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules11115 = new ProductCollectionRules();
					productCollectionRules11115.setOverdueDayStart(overdueDayStart5);
					productCollectionRules11115.setOverdueDayEnd(overdueDayEnd5);
					productCollectionRules11115.setMoney(money1115);
					productCollectionRules11115.setCollectionWay(collectionWay11115);
					productCollectionRules11115.setCollectionTime(collectionTime11115);
					productCollectionRules11115.setOperation(operation11115);
					productCollectionRules11115.setProductId(productId);
					productCollectionRules11115.setCollectionType(collectionType);

					/*
					 * 逾期天数151~180天
					 */
					String overdueDayStart6 = productCollectionRulesForm.getOverdueDayStart6();
					String overdueDayEnd6 = productCollectionRulesForm.getOverdueDayEnd6();
					String collectionWay1116 = productCollectionRulesForm.getCollectionWay1116();
					String money1116 = productCollectionRulesForm.getMoney1116();
					String operation1116 = productCollectionRulesForm.getOperation1116();
					String collectionTime1116 = productCollectionRulesForm.getCollectionTime1116();
					String operation11116 = productCollectionRulesForm.getOperation11116();
					String collectionTime11116 = productCollectionRulesForm.getCollectionTime11116();
					String collectionWay11116 = productCollectionRulesForm.getCollectionWay11116();
					ProductCollectionRules productCollectionRules1116 = new ProductCollectionRules();
					productCollectionRules1116.setOverdueDayStart(overdueDayStart6);
					productCollectionRules1116.setOverdueDayEnd(overdueDayEnd6);
					productCollectionRules1116.setMoney(money1116);
					productCollectionRules1116.setCollectionWay(collectionWay1116);
					productCollectionRules1116.setCollectionTime(collectionTime1116);
					productCollectionRules1116.setOperation(operation1116);
					productCollectionRules1116.setProductId(productId);
					productCollectionRules1116.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules11116 = new ProductCollectionRules();
					productCollectionRules11116.setOverdueDayStart(overdueDayStart6);
					productCollectionRules11116.setOverdueDayEnd(overdueDayEnd6);
					productCollectionRules11116.setMoney(money1116);
					productCollectionRules11116.setCollectionWay(collectionWay11116);
					productCollectionRules11116.setCollectionTime(collectionTime11116);
					productCollectionRules11116.setOperation(operation11116);
					productCollectionRules11116.setProductId(productId);
					productCollectionRules11116.setCollectionType(collectionType);

					/*
					 * 逾期天数181~210天
					 */
					String overdueDayStart7 = productCollectionRulesForm.getOverdueDayStart7();
					String overdueDayEnd7 = productCollectionRulesForm.getOverdueDayEnd7();
					String collectionWay1117 = productCollectionRulesForm.getCollectionWay1117();
					String money1117 = productCollectionRulesForm.getMoney1117();
					String operation1117 = productCollectionRulesForm.getOperation1117();
					String collectionTime1117 = productCollectionRulesForm.getCollectionTime1117();
					String operation11117 = productCollectionRulesForm.getOperation11117();
					String collectionTime11117 = productCollectionRulesForm.getCollectionTime11117();
					String collectionWay11117 = productCollectionRulesForm.getCollectionWay11117();
					ProductCollectionRules productCollectionRules1117 = new ProductCollectionRules();
					productCollectionRules1117.setOverdueDayStart(overdueDayStart7);
					productCollectionRules1117.setOverdueDayEnd(overdueDayEnd7);
					productCollectionRules1117.setMoney(money1117);
					productCollectionRules1117.setCollectionWay(collectionWay1117);
					productCollectionRules1117.setCollectionTime(collectionTime1117);
					productCollectionRules1117.setOperation(operation1117);
					productCollectionRules1117.setProductId(productId);
					productCollectionRules1117.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules11117 = new ProductCollectionRules();
					productCollectionRules11117.setOverdueDayStart(overdueDayStart7);
					productCollectionRules11117.setOverdueDayEnd(overdueDayEnd7);
					productCollectionRules11117.setMoney(money1117);
					productCollectionRules11117.setCollectionWay(collectionWay11117);
					productCollectionRules11117.setCollectionTime(collectionTime11117);
					productCollectionRules11117.setOperation(operation11117);
					productCollectionRules11117.setProductId(productId);
					productCollectionRules11117.setCollectionType(collectionType);

					/*
					 * 逾期天数211~240天
					 */
					String overdueDayStart8 = productCollectionRulesForm.getOverdueDayStart8();
					String overdueDayEnd8 = productCollectionRulesForm.getOverdueDayEnd8();
					String collectionWay1118 = productCollectionRulesForm.getCollectionWay1118();
					String money1118 = productCollectionRulesForm.getMoney1118();
					String operation1118 = productCollectionRulesForm.getOperation1118();
					String collectionTime1118 = productCollectionRulesForm.getCollectionTime1118();
					String operation11118 = productCollectionRulesForm.getOperation11118();
					String collectionTime11118 = productCollectionRulesForm.getCollectionTime11118();
					String collectionWay11118 = productCollectionRulesForm.getCollectionWay11118();
					ProductCollectionRules productCollectionRules1118 = new ProductCollectionRules();
					productCollectionRules1118.setOverdueDayStart(overdueDayStart8);
					productCollectionRules1118.setOverdueDayEnd(overdueDayEnd8);
					productCollectionRules1118.setMoney(money1118);
					productCollectionRules1118.setCollectionWay(collectionWay1118);
					productCollectionRules1118.setCollectionTime(collectionTime1118);
					productCollectionRules1118.setOperation(operation1118);
					productCollectionRules1118.setProductId(productId);
					productCollectionRules1118.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules11118 = new ProductCollectionRules();
					productCollectionRules11118.setOverdueDayStart(overdueDayStart8);
					productCollectionRules11118.setOverdueDayEnd(overdueDayEnd8);
					productCollectionRules11118.setMoney(money1118);
					productCollectionRules11118.setCollectionWay(collectionWay11118);
					productCollectionRules11118.setCollectionTime(collectionTime11118);
					productCollectionRules11118.setOperation(operation11118);
					productCollectionRules11118.setProductId(productId);
					productCollectionRules11118.setCollectionType(collectionType);

					/*
					 * 逾期天数241~270天
					 */
					String overdueDayStart9 = productCollectionRulesForm.getOverdueDayStart9();
					String overdueDayEnd9 = productCollectionRulesForm.getOverdueDayEnd9();
					String collectionWay1119 = productCollectionRulesForm.getCollectionWay1119();
					String money1119 = productCollectionRulesForm.getMoney1119();
					String operation1119 = productCollectionRulesForm.getOperation1119();
					String collectionTime1119 = productCollectionRulesForm.getCollectionTime1119();
					String operation11119 = productCollectionRulesForm.getOperation11119();
					String collectionTime11119 = productCollectionRulesForm.getCollectionTime11119();
					String collectionWay11119 = productCollectionRulesForm.getCollectionWay11119();
					ProductCollectionRules productCollectionRules1119 = new ProductCollectionRules();
					productCollectionRules1119.setOverdueDayStart(overdueDayStart9);
					productCollectionRules1119.setOverdueDayEnd(overdueDayEnd9);
					productCollectionRules1119.setMoney(money1119);
					productCollectionRules1119.setCollectionWay(collectionWay1119);
					productCollectionRules1119.setCollectionTime(collectionTime1119);
					productCollectionRules1119.setOperation(operation1119);
					productCollectionRules1119.setProductId(productId);
					productCollectionRules1119.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules11119 = new ProductCollectionRules();
					productCollectionRules11119.setOverdueDayStart(overdueDayStart9);
					productCollectionRules11119.setOverdueDayEnd(overdueDayEnd9);
					productCollectionRules11119.setMoney(money1119);
					productCollectionRules11119.setCollectionWay(collectionWay11119);
					productCollectionRules11119.setCollectionTime(collectionTime11119);
					productCollectionRules11119.setOperation(operation11119);
					productCollectionRules11119.setProductId(productId);
					productCollectionRules11119.setCollectionType(collectionType);

					/*
					 * 逾期天数271~360天
					 */
					String overdueDayStart10 = productCollectionRulesForm.getOverdueDayStart10();
					String overdueDayEnd10 = productCollectionRulesForm.getOverdueDayEnd10();
					String collectionWay11110 = productCollectionRulesForm.getCollectionWay11110();
					String money11110 = productCollectionRulesForm.getMoney11110();
					String operation11110 = productCollectionRulesForm.getOperation11110();
					String collectionTime11110 = productCollectionRulesForm.getCollectionTime11110();
					String operation111110 = productCollectionRulesForm.getOperation111110();
					String collectionTime111110 = productCollectionRulesForm.getCollectionTime111110();
					String collectionWay111110 = productCollectionRulesForm.getCollectionWay111110();
					ProductCollectionRules productCollectionRules11110 = new ProductCollectionRules();
					productCollectionRules11110.setOverdueDayStart(overdueDayStart10);
					productCollectionRules11110.setOverdueDayEnd(overdueDayEnd10);
					productCollectionRules11110.setMoney(money11110);
					productCollectionRules11110.setCollectionWay(collectionWay11110);
					productCollectionRules11110.setCollectionTime(collectionTime11110);
					productCollectionRules11110.setOperation(operation11110);
					productCollectionRules11110.setProductId(productId);
					productCollectionRules11110.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules111110 = new ProductCollectionRules();
					productCollectionRules111110.setOverdueDayStart(overdueDayStart10);
					productCollectionRules111110.setOverdueDayEnd(overdueDayEnd10);
					productCollectionRules111110.setMoney(money11110);
					productCollectionRules111110.setCollectionWay(collectionWay111110);
					productCollectionRules111110.setCollectionTime(collectionTime111110);
					productCollectionRules111110.setOperation(operation111110);
					productCollectionRules111110.setProductId(productId);
					productCollectionRules111110.setCollectionType(collectionType);

					/*
					 * 逾期天数大于360天
					 */
					String overdueDayStart14 = productCollectionRulesForm.getOverdueDayStart14();
					String overdueDayEnd14 = productCollectionRulesForm.getOverdueDayEnd14();
					String collectionWay111114 = productCollectionRulesForm.getCollectionWay111114();
					String money111114 = productCollectionRulesForm.getMoney111114();
					String operation111114 = productCollectionRulesForm.getOperation111114();
					String collectionTime111114 = productCollectionRulesForm.getCollectionTime111114();
					String operation1111114 = productCollectionRulesForm.getOperation1111114();
					String collectionTime1111114 = productCollectionRulesForm.getCollectionTime1111114();
					String collectionWay1111114 = productCollectionRulesForm.getCollectionWay1111114();
					ProductCollectionRules productCollectionRules111114 = new ProductCollectionRules();
					productCollectionRules111114.setOverdueDayStart(overdueDayStart14);
					productCollectionRules111114.setOverdueDayEnd(overdueDayEnd14);
					productCollectionRules111114.setMoney(money111114);
					productCollectionRules111114.setCollectionWay(collectionWay111114);
					productCollectionRules111114.setCollectionTime(collectionTime111114);
					productCollectionRules111114.setOperation(operation111114);
					productCollectionRules111114.setProductId(productId);
					productCollectionRules111114.setCollectionType(collectionType);

					ProductCollectionRules productCollectionRules1111114 = new ProductCollectionRules();
					productCollectionRules1111114.setOverdueDayStart(overdueDayStart14);
					productCollectionRules1111114.setOverdueDayEnd(overdueDayEnd14);
					productCollectionRules1111114.setMoney(money111114);
					productCollectionRules1111114.setCollectionWay(collectionWay1111114);
					productCollectionRules1111114.setCollectionTime(collectionTime1111114);
					productCollectionRules1111114.setOperation(operation1111114);
					productCollectionRules1111114.setProductId(productId);
					productCollectionRules1111114.setCollectionType(collectionType);

					list.add(productCollectionRules1111);
					list.add(productCollectionRules11111);
					list.add(productCollectionRules1112);
					list.add(productCollectionRules11112);
					list.add(productCollectionRules1113);
					list.add(productCollectionRules11113);
					list.add(productCollectionRules1114);
					list.add(productCollectionRules11114);
					list.add(productCollectionRules1115);
					list.add(productCollectionRules11115);
					list.add(productCollectionRules1116);
					list.add(productCollectionRules11116);
					list.add(productCollectionRules1117);
					list.add(productCollectionRules11117);
					list.add(productCollectionRules1118);
					list.add(productCollectionRules11118);
					list.add(productCollectionRules1119);
					list.add(productCollectionRules11119);
					list.add(productCollectionRules11110);
					list.add(productCollectionRules111110);
					list.add(productCollectionRules111114);
					list.add(productCollectionRules1111114);
					productService.insertProductCollectionRules(list);

				}
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}

	/**
	 * 执行修改产品筛选准入规则清单
	 * 
	 * @param productAttributeForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update_cpsxgz.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update_cpsxgz(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String productId = RequestHelper.getStringValue(request, ID);
				productFilterService.deleteProductFilterMapByProductId(productId);
				String accessorieIds = request.getParameter("accessorieIds");
				if (StringUtils.trimToNull(accessorieIds) != null) {
					String[] temp = accessorieIds.split(",");
					if (temp != null && temp.length > 0) {
						for (String aid : temp) {
							if (StringUtils.trimToNull(aid) != null) {
								ProductFilterMap productFilterMap = new ProductFilterMap();
								productFilterMap.setFilterDictId(aid);
								productFilterMap.setProductId(productId);
								productFilterMap.setModifiedBy(loginId);
								productFilterMap.setModifiedTime(new Date());
								productFilterService.insertProductFilterMap(productFilterMap);
							}
						}
					}
				}
				returnMap.addGlobalMessage(CHANGE_SUCCESS);
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}
	
	/*@ResponseBody
	@RequestMapping(value = "updateBaseCustomer.json", method = { RequestMethod.POST })
	//@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updateBaseCustomer(@ModelAttribute CIPERSONBASINFOCOPYFORM from, HttpServletRequest request) {

		JRadReturnMap returnMap = new JRadReturnMap();//WebRequestHelper.requestValidation(getModuleName(), from);
		if (returnMap.isSuccess()) {
			try {
				CIPERSONBASINFOCOPY copy = from.createModel(CIPERSONBASINFOCOPY.class);
				copy.setIslook("1");
				int i = productService.updateCustomerBase(copy);
				returnMap.put(MESSAGE, "修改成功");
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}

		return returnMap;
	}*/
	
	@ResponseBody
	@RequestMapping(value = "updateBaseCustomer.json", method = { RequestMethod.GET })
	//@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap updateBaseCustomer(@ModelAttribute CustomerFirsthendBase from, HttpServletRequest request) {
		CUSTORMERINFOUPDATE c=new CUSTORMERINFOUPDATE();
		JRadReturnMap returnMap = new JRadReturnMap();
		from.setId(request.getParameter("id"));
		from.setXb(request.getParameter("xb"));
		from.setMz(request.getParameter("mz"));
		from.setHyzk(request.getParameter("hyzk"));
		from.setXxdz(request.getParameter("xxdz"));
		from.setGtspmc(request.getParameter("gtspmc"));
		from.setHjdz(request.getParameter("hjdz"));
		from.setSj(request.getParameter("sj"));
		from.setJtdh(request.getParameter("jtdh"));
		from.setXl(request.getParameter("xl"));
		from.setXw(request.getParameter("xw"));
		from.setUpdatetime(new Date());
		int a=InforService.updateCustormerBytyId(from);
		c.setId(IDGenerator.generateID());
		c.setCardid(request.getParameter("cardid"));
		c.setChinesename(request.getParameter("chinesename"));
		if(!request.getParameter("hyzk").equals(request.getParameter("hyzk1")) && request.getParameter("hyzk")!=null && request.getParameter("hyzk1")!="00"){
			c.setZdname("婚姻状况");
			
			if(request.getParameter("hyzk").equals("0")){
				c.setModifiedvalue("未婚");
			}else if(request.getParameter("hyzk").equals("1")){
				c.setModifiedvalue("已婚");
			}else if(request.getParameter("hyzk").equals("2")){
				c.setModifiedvalue("丧偶");
			}else if(request.getParameter("hyzk").equals("3")){
				c.setModifiedvalue("离婚");
			}
			
			if(request.getParameter("hyzk1").equals("0")){
				c.setOriginalvalue("未婚");
			}else if(request.getParameter("hyzk1").equals("1")){
				c.setOriginalvalue("已婚");
			}else if(request.getParameter("hyzk1").equals("2")){
				c.setOriginalvalue("丧偶");
			}else if(request.getParameter("hyzk1").equals("3")){
				c.setOriginalvalue("离婚");
			}
			c.setUpdatetime(new Date());
			int b=InforService.insertCustormerUpdate(c);
		}
		if(!request.getParameter("xxdz").equals(request.getParameter("xxdz1") )  && request.getParameter("xxdz1")!="00" && request.getParameter("xxdz")!=null){
			c.setZdname("家庭地址");
			c.setModifiedvalue(request.getParameter("xxdz"));
			c.setOriginalvalue(request.getParameter("xxdz1"));
			c.setUpdatetime(new Date());
			int b=InforService.insertCustormerUpdate(c);
		}
		if(!request.getParameter("gtspmc").equals(request.getParameter("gtspmc1")) && request.getParameter("gtspmc1")!="00" && request.getParameter("gtspmc")!=null){
			c.setZdname("个体商户名称");
			c.setModifiedvalue(request.getParameter("gtspmc"));
			c.setOriginalvalue(request.getParameter("gtspmc1"));
			c.setUpdatetime(new Date());
			int b=InforService.insertCustormerUpdate(c);
		}	
		if(!request.getParameter("hjdz").equals(request.getParameter("hjdz1")) && request.getParameter("hjdz1")!="00" && request.getParameter("hjdz")!=null){
			c.setZdname("户籍地址");
			c.setModifiedvalue(request.getParameter("hjdz"));
			c.setOriginalvalue(request.getParameter("hjdz1"));
			c.setUpdatetime(new Date());
			int b=InforService.insertCustormerUpdate(c);
		}
		if(!request.getParameter("sj").equals(request.getParameter("sj1")) && request.getParameter("sj1")!="00" && request.getParameter("sj")!=null){
			c.setZdname("手机");
			c.setModifiedvalue(request.getParameter("sj"));
			c.setOriginalvalue(request.getParameter("sj1"));
			c.setUpdatetime(new Date());
			int b=InforService.insertCustormerUpdate(c);
		}
		if(!request.getParameter("jtdh").equals(request.getParameter("jtdh1")) && request.getParameter("jtdh1")!="00" && request.getParameter("jtdh")!=null){
			c.setZdname("家庭电话");
			c.setModifiedvalue(request.getParameter("jtdh"));
			c.setOriginalvalue(request.getParameter("jtdh1"));
			c.setUpdatetime(new Date());
			int b=InforService.insertCustormerUpdate(c);
		}
		if(!request.getParameter("xl").equals(request.getParameter("xl1"))&& request.getParameter("xl1")!="00" && request.getParameter("xl")!=null){
			c.setZdname("学历");
			if(request.getParameter("xl").equals("4")){
				c.setModifiedvalue("中等专业或技术学校");
			}else if(request.getParameter("xl").equals("1")){
				c.setModifiedvalue("研究生");
			}else if(request.getParameter("xl").equals("2")){
				c.setModifiedvalue("大学本科");
			}else if(request.getParameter("xl").equals("3")){
				c.setModifiedvalue("大学本科或者专科学校");
			}else if(request.getParameter("xl").equals("5")){
				c.setModifiedvalue("高中");
			}else if(request.getParameter("xl").equals("6")){
				c.setModifiedvalue("初中");
			}else if(request.getParameter("xl").equals("7")){
				c.setModifiedvalue("小学");
			}else if(request.getParameter("xl").equals("8")){
				c.setModifiedvalue("文盲或半文盲");
			}else if(request.getParameter("xl").equals("9")){
				c.setModifiedvalue("未知");
			}
			
			
			
			if(request.getParameter("xl1").equals("4")){
				c.setOriginalvalue("中等专业或技术学校");
			}else if(request.getParameter("xl1").equals("1")){
				c.setOriginalvalue("研究生");
			}else if(request.getParameter("xl1").equals("2")){
				c.setOriginalvalue("大学本科");
			}else if(request.getParameter("xl1").equals("3")){
				c.setOriginalvalue("大学本科或者专科学校");
			}else if(request.getParameter("xl1").equals("5")){
				c.setOriginalvalue("高中");
			}else if(request.getParameter("xl1").equals("6")){
				c.setOriginalvalue("初中");
			}else if(request.getParameter("xl1").equals("7")){
				c.setOriginalvalue("小学");
			}else if(request.getParameter("xl1").equals("8")){
				c.setOriginalvalue("文盲或半文盲");
			}else if(request.getParameter("xl1").equals("9")){
				c.setOriginalvalue("未知");
			}
			
			
			
			c.setUpdatetime(new Date());
			int b=InforService.insertCustormerUpdate(c);
		}
		if(!request.getParameter("xw").equals(request.getParameter("xw1"))&& request.getParameter("xw1")!="00" && request.getParameter("xw")!=null){
			c.setZdname("学位");
			if(request.getParameter("xw1").equals("0")){
				c.setOriginalvalue("博士");
			}else if(request.getParameter("xw1").equals("1")){
				c.setOriginalvalue("名誉博士");
			}else if(request.getParameter("xw1").equals("2")){
				c.setOriginalvalue("硕士");
			}else if(request.getParameter("xw1").equals("3")){
				c.setOriginalvalue("学士");
			}else if(request.getParameter("xw1").equals("4")){
				c.setOriginalvalue("其他");
			}else if(request.getParameter("xw1").equals("5")){
				c.setOriginalvalue("无");
			}else if(request.getParameter("xw1").equals("6")){
				c.setOriginalvalue("未知");
			}
			if(request.getParameter("xw").equals("0")){
				c.setModifiedvalue("博士");
			}else if(request.getParameter("xw").equals("1")){
				c.setModifiedvalue("名誉博士");
			}else if(request.getParameter("xw").equals("2")){
				c.setModifiedvalue("硕士");
			}else if(request.getParameter("xw").equals("3")){
				c.setModifiedvalue("学士");
			}else if(request.getParameter("xw").equals("4")){
				c.setModifiedvalue("其他");
			}else if(request.getParameter("xw").equals("5")){
				c.setModifiedvalue("无");
			}else if(request.getParameter("xw").equals("6")){
				c.setModifiedvalue("未知");
			}
			c.setUpdatetime(new Date());
			int b=InforService.insertCustormerUpdate(c);
		}
		returnMap.put("message", "操作成功");
		/*if (returnMap.isSuccess()) {
			try {
				CustomerInfor copy = from.createModel(CustomerInfor.class);
				copy.setIslook("1");
				copy.setModifiedTime(new Date());
				int i = productService.updateCustomerInfor(copy);
				returnMap.put(MESSAGE, "修改成功");
			} catch (Exception e) {
				return WebRequestHelper.processException(e);
			}
		}*/

		return returnMap;
	}
	
	
	
	/**
	 * 查询修改字段
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findCustormerUpdate.json", method = { RequestMethod.GET })
	public AbstractModelAndView findCustormerUpdate(HttpServletRequest request) {
		String cardid=request.getParameter("cardid");
		List<CUSTORMERINFOUPDATE> result = InforService.findCustormerUpdate(cardid);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int a=0;a<result.size();a++){
			String time1=sdf.format(result.get(a).getUpdatetime());
			result.get(a).setUpdatetime1(time1);
		}
		CUSTORMERINFOUPDATE CUSTORMERINFOUPDATE=new CUSTORMERINFOUPDATE();
		CUSTORMERINFOUPDATE.setChinesename(result.get(0).getChinesename());
		CUSTORMERINFOUPDATE.setCardid(result.get(0).getCardid());
		JRadModelAndView mv = new JRadModelAndView("/customer/customerFirsthend/customer_iframe_13", request);
		mv.addObject("result", result);
		mv.addObject("result1", CUSTORMERINFOUPDATE);

		return mv;
	}
	

}
