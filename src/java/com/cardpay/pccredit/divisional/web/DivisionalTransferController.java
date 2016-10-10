package com.cardpay.pccredit.divisional.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.cardpay.pccredit.divisional.constant.DivisionalProgressEnum;
import com.cardpay.pccredit.divisional.constant.DivisionalTypeEnum;
import com.cardpay.pccredit.divisional.filter.DivisionalFilter;
import com.cardpay.pccredit.divisional.model.DivisionalTransfer;
import com.cardpay.pccredit.divisional.service.DivisionalService;
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
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;
/**
 * 
 * @author 姚绍明
 *
 * 2014年10月24日 上午10:25:05
 */
@Controller
@RequestMapping("/divisional/customeraltransfer/*")
@JRadModule("divisional.customeraltransfer")
public class DivisionalTransferController extends BaseController{
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private DivisionalService divisionalservice;
	
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
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setOriginalManagerOld(user.getId());
		QueryResult<DivisionalTransfer> result = divisionalservice.findDivisionalTransfer(filter);
		JRadPagedQueryResult<DivisionalTransfer> pagedResult = new JRadPagedQueryResult<DivisionalTransfer>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/divisional/customeraltransfer/divisional_transfer_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	/**
	 * 移交客户
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "transfer.json",method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public Map<String,Object> transfer(HttpServletRequest request) {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		try {
			String customerId = RequestHelper.getStringValue(request, ID);
			Boolean flag = divisionalservice.insertDivisionalCustomer(customerId,DivisionalTypeEnum.initiative,DivisionalProgressEnum.charge);
			if(flag){
				returnMap.put(JRadConstants.SUCCESS,true);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERSUCCESS);
			}else{
				returnMap.put(JRadConstants.SUCCESS,false);
				returnMap.put(JRadConstants.MESSAGE,CustomerInforConstant.TRANSFERERROR);
			}
		}catch (Exception e) {
			returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
			returnMap.put(JRadConstants.SUCCESS, false);
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
}
