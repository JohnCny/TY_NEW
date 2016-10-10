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
@RequestMapping("/intopieces/orgnization/*")
@JRadModule("intopieces.orgnization")
public class IntoPiecesOrgnizationControl extends BaseController {

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerInforService customerInforservice;

	
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
	@JRadOperation(JRadOperation.ISSUEDCUSTOMER)
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
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView viewCard(HttpServletRequest request) {
		MakeCard makeCard = intoPiecesService.findMakeCardById(request.getParameter(ID));
		JRadModelAndView mv = new JRadModelAndView(
				"/intopieces/card_view", request);
		mv.addObject("makeCard", makeCard);
		return mv;
	}


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DataBindHelper.initStandardBinder(binder);
	}

}
