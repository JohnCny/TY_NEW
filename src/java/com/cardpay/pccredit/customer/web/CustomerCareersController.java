/**
 * 
 */
package com.cardpay.pccredit.customer.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerCareersWeb;
import com.cardpay.pccredit.customer.service.CustomerCareersService;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 客户职业信息
 * @author shaoming
 *
 * 2014年10月30日   下午3:32:11
 */
@Controller
@RequestMapping("/customer/customercareer/*")
@JRadModule("customer.customercareer")
public class CustomerCareersController extends BaseController{

	@Autowired
	private CustomerCareersService customerCareersService;
	
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
	@RequestMapping(value = "create_zyxx.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView browse(HttpServletRequest request) {
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
	 * 浏览页面 (进件快照信息)
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "zyxxclone.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView browseClone(HttpServletRequest request) {
        String applicationId = request.getParameter("applicationId");
        CustomerCareersWeb careers = customerCareersService.findCustomerCareersByAppId(applicationId);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforDisplay/customerinfoupdate_zyxx",request);
		mv.addObject("careers",careers);
		mv.addObject("applicationId",applicationId);
		return mv;
	}
	
	/**
	 * 执行修改
	 * 
	 * @param sample2
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update.json")
	@JRadOperation(JRadOperation.CHANGE)
	public JRadReturnMap update(@ModelAttribute CustomerCareersForm form, HttpServletRequest request) {
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
}
