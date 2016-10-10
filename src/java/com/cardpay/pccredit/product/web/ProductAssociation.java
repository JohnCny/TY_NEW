package com.cardpay.pccredit.product.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.product.model.AccessoriesList;
import com.cardpay.pccredit.product.model.ProductAccountability;
import com.cardpay.pccredit.product.model.ProductCollectionRules;
import com.cardpay.pccredit.product.model.ProductFilterMap;
import com.cardpay.pccredit.product.model.ProductMaintain;
import com.cardpay.pccredit.product.model.ProductMarketingRules;
import com.cardpay.pccredit.product.model.ProductRewardIncentive;
import com.cardpay.pccredit.product.service.ProductFilterService;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.web.RequestHelper;

/**
 * Description of ProductAssociation
 * 
 * @author 王海东
 * @created on 2014-10-17
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/product/association/*")
@JRadModule("products.productAssociation")
public class ProductAssociation extends BaseController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductFilterService productFilterService;

	/**
	 * 执行产品附件清单添加
	 * 
	 * @param acceList
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertCpfjqd.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insertCpfjqd(@ModelAttribute AccessoriesList acceList, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), acceList);
		if (returnMap.isSuccess()) {
			try {
				String productId = RequestHelper.getStringValue(request, "productId");
				String accessorieIds = request.getParameter("accessorieIds");
				if (StringUtils.trimToNull(accessorieIds) != null) {
					String[] temp = accessorieIds.split(",");
					if (temp != null && temp.length > 0) {
						// TODO 使用iterator较合适，无兼容考量可保留
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
				returnMap.put("productId", productId);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			} catch (Exception e) {
				// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
		}
		return returnMap;
	}
	/**
	 * 执行产品筛选准入规则添加
	 * 
	 * @param productFilterMap
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert_cpsxgz.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert_cpsxgz(@ModelAttribute ProductFilterMap productFilterMap, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), productFilterMap);
		if (returnMap.isSuccess()) {
			try {
				IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
				String loginId = user.getId();
				String productId = RequestHelper.getStringValue(request, "productId");
				String accessorieIds = request.getParameter("accessorieIds");
				if (StringUtils.trimToNull(accessorieIds) != null) {
					String[] temp = accessorieIds.split(",");
					if (temp != null && temp.length > 0) {
						// TODO 使用iterator较合适，无兼容考量可保留
						for (String aid : temp) {
							if (StringUtils.trimToNull(aid) != null) {
								ProductFilterMap productFilMap = new ProductFilterMap();
								productFilMap.setFilterDictId(aid);
								productFilMap.setProductId(productId);
								productFilMap.setCreatedBy(loginId);
								productFilterService.insertProductFilterMap(productFilMap);
							}
						}
					}
				}
				returnMap.put("productId", productId);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			} catch (Exception e) {
				// TODO 有日志功能，在这一步应保持返回统一，出错以后查看日志
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
		}
		return returnMap;
	}
	/**
	 * 执行产品规则添加
	 * 
	 * @param productMarketingRulesForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "marketing_insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap marketing_insert(@ModelAttribute ProductMarketingRulesForm productMarketingRulesForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), productMarketingRulesForm);
		if (returnMap.isSuccess()) {
			try {
				String productId = RequestHelper.getStringValue(request, "productId");
				/*
				 * 产品营销规则
				 */
				ProductMarketingRules productMarketingRules = productMarketingRulesForm.createModel(ProductMarketingRules.class);
				/*
				 * 奖励激励配置
				 */
				ProductRewardIncentive productRewardIncentive = productMarketingRulesForm.createModel(ProductRewardIncentive.class);
				/*
				 * 产品维护规则
				 */
				ProductAccountability productAccountability = productMarketingRulesForm.createModel(ProductAccountability.class);
				/*
				 * 产品问责规则
				 */
				ProductMaintain productMaintain = productMarketingRulesForm.createModel(ProductMaintain.class);
				productAccountability.setProductId(productId);
				productMaintain.setProductId(productId);
				productMarketingRules.setProductId(productId);
				// managerProductsConfiguration.setProductId(productId);
				productRewardIncentive.setProductId(productId);

				String id = productService.insertRules(productMarketingRules, productRewardIncentive, productAccountability, productMaintain, request);
				returnMap.put("productId", productId);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
		}
		return returnMap;
	}

	/**
	 * 执行产品催收计划添加
	 * 
	 * @param productMarketingRulesForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "collection_rules_insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap collection_rules_insert(@ModelAttribute ProductCollectionRulesForm productCollectionRulesForm, HttpServletRequest request) {
		JRadReturnMap returnMap = WebRequestHelper.requestValidation(getModuleName(), productCollectionRulesForm);
		if (returnMap.isSuccess()) {
			try {
				String productId = RequestHelper.getStringValue(request, "productId");
				String collectionType = request.getParameter("reminderClass");
				List<ProductCollectionRules> list = new ArrayList<ProductCollectionRules>();

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
				returnMap.addGlobalMessage(CREATE_SUCCESS);
				returnMap.put("productId", productId);
			} catch (Exception e) {
				e.printStackTrace();
				return WebRequestHelper.processException(e);

			}
		}
		return returnMap;
	}

}
