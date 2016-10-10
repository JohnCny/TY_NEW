package com.cardpay.pccredit.dimensional.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.dimensional.filter.DimensionalFilter;
import com.cardpay.pccredit.dimensional.model.Dimensional;
import com.cardpay.pccredit.dimensional.service.DimensionalService;
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

/**
 * Description of DimensionalModel
 * 
 * @author 王海东
 * @created on 2014-10-17
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("customer/dimensional/*")
@JRadModule("customer.dimensional")
public class DimensionalModelController extends BaseController {

	@Autowired
	private DimensionalService dimensionalService;

	@Autowired
	private CustomerInforService customerInforService;

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
	public AbstractModelAndView browse(@ModelAttribute DimensionalFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<Dimensional> result = dimensionalService.findDimensionalByFilter(filter);
		JRadPagedQueryResult<Dimensional> pagedResult = new JRadPagedQueryResult<Dimensional>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
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
	@JRadOperation(JRadOperation.BROWSE)
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
	 * 显示四维评估页面 (进件提交的快照信息)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "swpgclone.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView swpgClone(HttpServletRequest request) {
		String applicationId = request.getParameter("applicationId");
		Dimensional dimensional = dimensionalService.findDimensionalByAppId(applicationId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforDisplay/customerinfoupdate_swpg", request);
		mv.addObject("dimensional", dimensional);
		mv.addObject("applicationId", applicationId);
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
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
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
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap update(@ModelAttribute DimensionalForm dimensionalForm, HttpServletRequest request) {
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

}
