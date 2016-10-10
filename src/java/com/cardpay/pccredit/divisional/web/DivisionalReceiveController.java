package com.cardpay.pccredit.divisional.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.CustomerInforDStatusEnum;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.divisional.constant.DivisionalConstant;
import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.Divisional;
import com.cardpay.pccredit.divisional.model.DivisionalWeb;
import com.cardpay.pccredit.divisional.service.DivisionalReceiveService;
import com.cardpay.pccredit.divisional.service.DivisionalService;
import com.cardpay.pccredit.notification.constant.NotificationEnum;
import com.cardpay.pccredit.notification.service.NotificationService;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * @author 姚绍明
 *
 * 2014年10月23日 上午11:03:29
 */
@Controller
@RequestMapping("/divisional/customeralreceive/*")
@JRadModule("divisional.customeralreceive")
public class DivisionalReceiveController extends BaseController{

	@Autowired
	private DivisionalReceiveService divisionalreceiveService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private DivisionalService divisionalService;
	
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
	public AbstractModelAndView browse(@ModelAttribute DivisionalFilter filter, HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = (User)Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setCustomerManagerId(user.getId());
		filter.setDivisionalProgress((DivisionalConstant.CFCC));
		QueryResult<DivisionalWeb> result = divisionalreceiveService.findDivisionalByCustomerManager(filter);
		JRadPagedQueryResult<DivisionalWeb> pagedResult = new JRadPagedQueryResult<DivisionalWeb>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/divisional/customerreceive/customer_receive_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	/**
	 * 接受客户
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "receive.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CHANGE)
	public Map<String,Object> receive(HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		String id = RequestHelper.getStringValue(request, ID);
		String customerId = RequestHelper.getStringValue(request, DivisionalConstant.CUSTOMERID);
		User user = (User)Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		try {
			boolean flag = divisionalreceiveService.updateCustomerInforAndDivisional(customerId, userId, CustomerInforDStatusEnum.complete,id,DivisionalConstant.RECEIVED,DivisionalConstant.MANAGER);
			if(flag){
				Divisional divisional = divisionalService.findDivisinalById(id);
				String oldManagerName = divisionalreceiveService.findUserNameByUserId(divisional.getOriginalManagerOld());
			    String ManagerName = divisionalreceiveService.findUserNameByUserId(divisional.getCustomerManagerId());
				notificationService.insertNotification(NotificationEnum.qita, divisional.getOriginalManagerOld(), DivisionalConstant.RECEIVESUCCESS, oldManagerName+"向"+ManagerName+DivisionalConstant.RECEIVESUCCESS, user.getId());
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.RECEIVESUCCESS);
			}else{
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.RECEIVEERROR);
			}
		} catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	/**
	 * 显示页面
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "display.page")
	@JRadOperation(JRadOperation.DISPLAY)
	public AbstractModelAndView display(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/divisional/customerreceive/customer_receive_display", request);

		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforService.findCustomerInforById(customerInforId);
			mv.addObject(CustomerInforConstant.CUSTOMERINFOR, customerInfor);
		}

		return mv;
	}
	/**
	 * 拒绝客户
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "reject.json", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CHANGE)
	public Map<String,Object> reject(HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			String id = RequestHelper.getStringValue(request, ID);
			boolean flag = divisionalreceiveService.updateDivisionalResult(id,DivisionalConstant.REJECT);
			if(flag){
				returnMap.put(JRadConstants.SUCCESS, true);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.REJECTSUCCESS);
			}else{
				returnMap.put(JRadConstants.SUCCESS, false);
				returnMap.put(JRadConstants.MESSAGE,DivisionalConstant.REJECTERROR);
			}
		} catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
}
