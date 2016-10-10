package com.cardpay.pccredit.customer.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerMainManager;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.CustomerMainManagerService;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.service.UserService;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

/**
 * CustomerMainManagerController类的描述
 * 
 * @author 王海东
 * @created on 2014-10-23
 * 
 * @version $Id:$
 */
@Controller
@RequestMapping("/customer/customermainManager/*")
@JRadModule("customer.customermainManager")
public class CustomerMainManagerController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private CustomerMainManagerService customerMainManagerService;

	/**
	 * 浏览客户台账页面
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_khtz.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(HttpServletRequest request) {
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
	 * 客户台账快照
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "khtzclone.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView khtzclone(HttpServletRequest request) {
		String customerId = request.getParameter("id");
		String applicationId = request.getParameter("applicationId");
		CustomerInfor customer = customerInforService.findCustomerInforById(customerId);
		CustomerMainManager customerMainManager = customerMainManagerService.findCustomerMainManagerByApplicationId(applicationId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforDisplay/customerMainManager", request);
		mv.addObject("customerMainManager", customerMainManager);
		mv.addObject("customer", customer);
		return mv;
	}

	/**
	 * 执行添加
	 * 
	 * @param
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute CustomerMainManagerForm customerMainManagerForm, HttpServletRequest request) {
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

}
